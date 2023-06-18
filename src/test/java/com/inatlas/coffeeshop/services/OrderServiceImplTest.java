package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.exceptions.BadOrderException;
import com.inatlas.coffeeshop.models.FreeReceiptItem;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.PromotionResponse;
import com.inatlas.coffeeshop.models.Receipt;
import com.inatlas.coffeeshop.services.promotions.Promotable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private ProductService productService;

    @Mock
    private ReceiptService receiptService;

    @Mock
    private Promotable promotable1;

    @Mock
    private Promotable promotable2;

    @Mock
    private Promotable promotable3;

    private OrderServiceImpl orderService;

    private Order order;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<Product>> productListArgumentCaptor;

    @BeforeEach
    void setUp() {
        List<Promotable> promotableList = List.of(promotable1, promotable2, promotable3);
        orderService = new OrderServiceImpl(productService, receiptService, promotableList);
    }

    private void setReturningProductList(final List<Integer> productIdList) {
        var productList = List.of(Product.builder().id(1).productName("Product 1").price(new BigDecimal("1.2")).build(),
                Product.builder().id(2).productName("Product 2").price(new BigDecimal("2.3")).build(),
                Product.builder().id(3).productName("Product 3").price(new BigDecimal("3.4")).build(),
                Product.builder().id(4).productName("Product 4").price(new BigDecimal("4.5")).build());
        var filterProductList = productList.stream().filter(product -> productIdList.contains(product.getId())).collect(Collectors.toList());
        lenient().when(productService.getAvailableProductsByIds(anyList())).thenReturn(filterProductList);

    }

    @Nested
    class BadOrder {

        @Nested
        @DisplayName("When order is null")
        class OrderIsNull {

            @BeforeEach
            void setUp() {
                order = null;
            }

            @Test
            @DisplayName("should throws BadOrderException")
            void test() {

                var exception = assertThrows(BadOrderException.class, () -> orderService.placeOrder(order));

                assertEquals("Order is null or empty", exception.getMessage());

            }

        }

        @Nested
        @DisplayName("When order is empty")
        class OrderIsEmpty {

            @BeforeEach
            void setUp() {
                order = new Order();
            }

            @Test
            @DisplayName("should throws BadOrderException")
            void test() {

                var exception = assertThrows(BadOrderException.class, () -> orderService.placeOrder(order));

                assertEquals("Order is null or empty", exception.getMessage());

            }

        }

        @Nested
        @DisplayName("When orderItems is empty")
        class OrderItemsIsEmpty {

            @BeforeEach
            void setUp() {
                order = new Order(new HashMap<>());
            }

            @Test
            @DisplayName("should throws BadOrderException")
            void test() {

                var exception = assertThrows(BadOrderException.class, () -> orderService.placeOrder(order));

                assertEquals("Order is null or empty", exception.getMessage());

            }

        }

    }

    @Nested
    class OrderOk {

        private final Receipt noPromotionReceipt = Receipt.builder()
                .promotionDescription("No promotion")
                .total(new BigDecimal("80"))
                .discountPercent(new BigDecimal("0"))
                .build();

        private final Receipt promotionReceipt1 = Receipt.builder()
                .promotionDescription("Lower total value")
                .total(new BigDecimal("80"))
                .discountPercent(new BigDecimal("1"))
                .freeReceiptItemSet(Set.of(
                        FreeReceiptItem.builder().productName("p1").amount(3).build(),
                        FreeReceiptItem.builder().productName("p2").amount(3).build()
                ))
                .build();

        private final Receipt promotionReceipt2 = Receipt.builder()
                .promotionDescription("3 different free products")
                .total(new BigDecimal("90"))
                .discountPercent(new BigDecimal("2"))
                .freeReceiptItemSet(Set.of(
                        FreeReceiptItem.builder().productName("p1").amount(1).build(),
                        FreeReceiptItem.builder().productName("p2").amount(2).build(),
                        FreeReceiptItem.builder().productName("p3").amount(3).build()
                ))
                .build();

        private final Receipt promotionReceipt3 = Receipt.builder()
                .promotionDescription("4 free products")
                .total(new BigDecimal("90"))
                .discountPercent(new BigDecimal("3"))
                .freeReceiptItemSet(Set.of(
                        FreeReceiptItem.builder().productName("p1").amount(7).build()
                ))
                .build();

        @BeforeEach
        void setUp() {
            order = new Order(Map.of(1, 3, 2, 2, 4, 1));
            setReturningProductList(List.of(1, 2, 4));
            when(receiptService.createReceipt(any(Order.class), anyList())).thenReturn(noPromotionReceipt);
        }

        @Nested
        @DisplayName("When no promotion is applicable")
        class NoPromotionIsApplicable {

            @BeforeEach
            void setUp() {
                when(promotable1.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(false, promotionReceipt1));
                when(promotable2.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(false, promotionReceipt2));
                when(promotable3.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(false, promotionReceipt3));
            }

            @Test
            @DisplayName("should return noPromotionReceipt")
            void test() {

                var result = orderService.placeOrder(order);

                verify(receiptService, times(1)).createReceipt(orderArgumentCaptor.capture(), productListArgumentCaptor.capture());
                assertEquals(order, orderArgumentCaptor.getValue());
                var productList = productListArgumentCaptor.getValue();

                assertEquals(1, productList.stream().filter(product -> product.getId() == 1).count());
                assertEquals(1, productList.stream().filter(product -> product.getId() == 2).count());
                assertEquals(0, productList.stream().filter(product -> product.getId() == 3).count());
                assertEquals(1, productList.stream().filter(product -> product.getId() == 4).count());

                assertEquals(noPromotionReceipt, result);
            }

        }

        @Nested
        @DisplayName("When one promotion is applicable")
        class OnePromotionIsApplicable {

            @BeforeEach
            void setUp() {
                when(promotable1.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(false, promotionReceipt1));
                when(promotable2.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(true, promotionReceipt2));
                when(promotable3.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(false, promotionReceipt3));
            }

            @Test
            @DisplayName("should return promotion receipt")
            void test() {

                var result = orderService.placeOrder(order);

                verify(receiptService, times(1)).createReceipt(orderArgumentCaptor.capture(), productListArgumentCaptor.capture());
                assertEquals(order, orderArgumentCaptor.getValue());
                var productList = productListArgumentCaptor.getValue();

                assertEquals(1, productList.stream().filter(product -> product.getId() == 1).count());
                assertEquals(1, productList.stream().filter(product -> product.getId() == 2).count());
                assertEquals(0, productList.stream().filter(product -> product.getId() == 3).count());
                assertEquals(1, productList.stream().filter(product -> product.getId() == 4).count());

                assertEquals(promotionReceipt2, result);
            }

        }

        @Nested
        @DisplayName("When there are 3 applicable promotion")
        class WhenThereAre2ApplicablePromotion {

            @BeforeEach
            void setUp() {
                when(promotable1.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(true, promotionReceipt1));
                when(promotable2.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(true, promotionReceipt2));
                when(promotable3.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(false, promotionReceipt3));
            }

            @Test
            @DisplayName("should return promotion receipt with lower total price")
            void test() {

                var result = orderService.placeOrder(order);

                verify(receiptService, times(1)).createReceipt(orderArgumentCaptor.capture(), productListArgumentCaptor.capture());
                assertEquals(order, orderArgumentCaptor.getValue());
                var productList = productListArgumentCaptor.getValue();

                assertEquals(1, productList.stream().filter(product -> product.getId() == 1).count());
                assertEquals(1, productList.stream().filter(product -> product.getId() == 2).count());
                assertEquals(0, productList.stream().filter(product -> product.getId() == 3).count());
                assertEquals(1, productList.stream().filter(product -> product.getId() == 4).count());

                assertEquals(promotionReceipt1, result);
            }

        }

        @Nested
        @DisplayName("When there are 3 applicable promotion with lower total price")
        class WhenThereAre3LowerTotal {

            @BeforeEach
            void setUp() {

                promotionReceipt1.setTotal(new BigDecimal("90"));

                when(promotable1.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(true, promotionReceipt1));
                when(promotable2.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(true, promotionReceipt2));
                when(promotable3.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(true, promotionReceipt3));
            }

            @Test
            @DisplayName("should return promotion receipt with more free product")
            void test() {

                var result = orderService.placeOrder(order);

                verify(receiptService, times(1)).createReceipt(orderArgumentCaptor.capture(), productListArgumentCaptor.capture());
                assertEquals(order, orderArgumentCaptor.getValue());
                var productList = productListArgumentCaptor.getValue();

                assertEquals(1, productList.stream().filter(product -> product.getId() == 1).count());
                assertEquals(1, productList.stream().filter(product -> product.getId() == 2).count());
                assertEquals(0, productList.stream().filter(product -> product.getId() == 3).count());
                assertEquals(1, productList.stream().filter(product -> product.getId() == 4).count());

                assertEquals(promotionReceipt3, result);
            }

        }

        @Nested
        @DisplayName("When there are 3 applicable promotion with lower total price and same amount free products")
        class WhenThereAre3LowerTotalAndSameAmountFreeProducts {

            @BeforeEach
            void setUp() {

                promotionReceipt1.setTotal(new BigDecimal("90"));
                promotionReceipt3.setFreeReceiptItemSet(Set.of(FreeReceiptItem.builder().productName("p1").amount(6).build()));

                when(promotable1.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(true, promotionReceipt1));
                when(promotable2.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(true, promotionReceipt2));
                when(promotable3.getPromotionResponse(any(Order.class), any(Receipt.class), anyList())).thenReturn(new PromotionResponse(true, promotionReceipt3));
            }

            @Test
            @DisplayName("should return first promotion receipt")
            void test() {

                var result = orderService.placeOrder(order);

                verify(receiptService, times(1)).createReceipt(orderArgumentCaptor.capture(), productListArgumentCaptor.capture());
                assertEquals(order, orderArgumentCaptor.getValue());
                var productList = productListArgumentCaptor.getValue();

                assertEquals(1, productList.stream().filter(product -> product.getId() == 1).count());
                assertEquals(1, productList.stream().filter(product -> product.getId() == 2).count());
                assertEquals(0, productList.stream().filter(product -> product.getId() == 3).count());
                assertEquals(1, productList.stream().filter(product -> product.getId() == 4).count());

                assertEquals(promotionReceipt1, result);
            }

        }

    }

}