package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.FreeReceiptItem;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.PromotionResponse;
import com.inatlas.coffeeshop.models.Receipt;
import com.inatlas.coffeeshop.services.promotions.Promotable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductService productService;
    private final ReceiptService receiptService;
    private final List<Promotable> promotableList;

    public OrderServiceImpl(final ProductService productService, final ReceiptService receiptService, final List<Promotable> promotableList) {
        this.productService = productService;
        this.receiptService = receiptService;
        this.promotableList = promotableList;
    }

    @Override
    public Receipt placeOrder(final Order order) {

        var productIdList = new ArrayList<>(order.getOrderItems().keySet());
        var productList = productService.findProductsByIds(productIdList);
        var noPromotionReceipt = receiptService.createReceipt(order, productList);

        var promotionReceiptList = getApplicablePromotionReceipt(order, noPromotionReceipt, productList);

        if (promotionReceiptList.isEmpty()) {
            return noPromotionReceipt;
        }

        return findBestPromotionReceipt(promotionReceiptList);
    }

    private List<Receipt> getApplicablePromotionReceipt(final Order order, final Receipt noPromotionReceipt, final List<Product> productList) {

        return promotableList.stream()
                .map(promotable -> promotable.getPromotionResponse(order, noPromotionReceipt, productList))
                .collect(Collectors.toList()).stream()
                .filter(PromotionResponse::isApplicable)
                .map(PromotionResponse::getReceipt)
                .collect(Collectors.toList());
    }

    private static List<Receipt> getReceipts(final List<Receipt> chepeastReceiptList, final FilterParams filterParams) {

        return chepeastReceiptList.stream()
                .collect(Collectors.groupingBy(filterParams.getReceiptFunction()))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByKey(filterParams.getNumberComparator()))
                .map(Map.Entry::getValue)
                .orElseThrow();
    }

    private Receipt findBestPromotionReceipt(final List<Receipt> receiptList) {

        List<Receipt> receiptFilterList = new ArrayList<>(receiptList);

        for (FilterParams filterParams : getFilterParams()) {

            receiptFilterList = getReceipts(receiptFilterList, filterParams);
            if (receiptFilterList.size() == 1) {
                return receiptFilterList.get(0);
            }

        }

        return receiptFilterList.get(0);

    }

    private List<FilterParams> getFilterParams() {

        return List.of(
                new FilterParams(Comparator.comparing(Number::floatValue),
                        Receipt::getTotal),
                new FilterParams(Comparator.comparing(Number::intValue),
                        receipt -> receipt.getFreeReceiptItemSet().stream().mapToInt(FreeReceiptItem::getAmount).sum()));
    }

    @Getter
    @AllArgsConstructor
    private static class FilterParams {

        private Comparator<Number> numberComparator;
        private Function<Receipt, Number> receiptFunction;

    }

}
