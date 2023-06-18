package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.ProductType;
import com.inatlas.coffeeshop.models.Receipt;
import com.inatlas.coffeeshop.services.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodAndDrinksPromotionTest {

    @Mock
    private ReceiptService receiptService;

    @InjectMocks
    private FoodAndDrinksPromotion foodAndDrinksPromotion;

    @Mock
    private Order order;

    private Receipt receipt;

    private List<Product> productList;

    @BeforeEach
    void setUp() {
        productList = List.of(
                Product.builder().id(1).productType(ProductType.DRINKS).build(),
                Product.builder().id(2).productType(ProductType.DRINKS).build(),
                Product.builder().id(3).productType(ProductType.FOOD).build()
        );
    }

    @Nested
    class IsPromotionApplicable {

        @Nested
        @DisplayName("When total price is 49")
        class WhenTotalIs49 {

            @BeforeEach
            void setUp() {
                receipt = Receipt.builder().total(new BigDecimal("49")).build();
            }

            @Test
            @DisplayName("should return false")
            void test() {

                var result = foodAndDrinksPromotion.isPromotionApplicable(order, receipt, productList);

                assertFalse(result);
            }

        }

        @Nested
        @DisplayName("When total price is 30")
        class WhenTotalIs30 {

            @BeforeEach
            void setUp() {
                receipt = Receipt.builder().total(new BigDecimal("49")).build();
            }

            @Test
            @DisplayName("should return false")
            void test() {

                var result = foodAndDrinksPromotion.isPromotionApplicable(order, receipt, productList);

                assertFalse(result);
            }

        }

        @Nested
        @DisplayName("When total price is 51")
        class WhenTotalIs51 {

            @BeforeEach
            void setUp() {
                receipt = Receipt.builder().total(new BigDecimal("51")).build();
            }

            @Nested
            @DisplayName("and there is not product with id 1")
            class AndThereIsNotProductWithId1 {

                @BeforeEach
                void setUp() {
                    productList = List.of(
                            Product.builder().id(2).productType(ProductType.DRINKS).build(),
                            Product.builder().id(3).productType(ProductType.FOOD).build());
                }

                @Test
                @DisplayName("should return false")
                void test() {

                    var result = foodAndDrinksPromotion.isPromotionApplicable(order, receipt, productList);

                    assertFalse(result);
                }

            }

            @Nested
            @DisplayName("and there is a product with id 1")
            class AndThereIsAProductWithId1 {

                @Nested
                @DisplayName("and all products are drinks")
                class AndAllProductsAreDrinks {

                    @BeforeEach
                    void setUp() {
                        productList = List.of(
                                Product.builder().id(1).productType(ProductType.DRINKS).build(),
                                Product.builder().id(2).productType(ProductType.DRINKS).build(),
                                Product.builder().id(3).productType(ProductType.DRINKS).build());
                    }

                    @Test
                    @DisplayName("should return false")
                    void test() {

                        var result = foodAndDrinksPromotion.isPromotionApplicable(order, receipt, productList);

                        assertFalse(result);
                    }

                }

                @Nested
                @DisplayName("and all products are food")
                class AndAllProductsAreFood {

                    @BeforeEach
                    void setUp() {
                        productList = List.of(
                                Product.builder().id(1).productType(ProductType.FOOD).build(),
                                Product.builder().id(2).productType(ProductType.FOOD).build(),
                                Product.builder().id(3).productType(ProductType.FOOD).build());
                    }

                    @Test
                    @DisplayName("should return false")
                    void test() {

                        var result = foodAndDrinksPromotion.isPromotionApplicable(order, receipt, productList);

                        assertFalse(result);
                    }

                }

                @Nested
                @DisplayName("and there are food and drinks")
                class AndThereAreFoodAndDrinks {

                    @BeforeEach
                    void setUp() {
                        productList = List.of(
                                Product.builder().id(1).productType(ProductType.DRINKS).build(),
                                Product.builder().id(2).productType(ProductType.DRINKS).build(),
                                Product.builder().id(3).productType(ProductType.FOOD).build());
                    }

                    @Test
                    @DisplayName("should return false")
                    void test() {

                        var result = foodAndDrinksPromotion.isPromotionApplicable(order, receipt, productList);

                        assertTrue(result);
                    }

                }

            }

        }

    }

    @Nested
    class BuildPromotionReceipt {

        @Captor
        private ArgumentCaptor<Order> orderArgumentCaptor;

        @Captor
        private ArgumentCaptor<List<Product>> productListArgumentCaptor;

        @BeforeEach
        void setUp() {

            receipt = Receipt.builder().promotionDescription("No promotion").build();
            when(receiptService.createReceipt(any(Order.class), anyList())).thenReturn(receipt);
        }

        @Test
        void test() {

            var result = foodAndDrinksPromotion.buildPromotionReceipt(order, receipt, productList);

            verify(receiptService, times(1)).createReceipt(orderArgumentCaptor.capture(), productListArgumentCaptor.capture());
            assertEquals(order, orderArgumentCaptor.getValue());
            var productList = productListArgumentCaptor.getValue();

            assertEquals(1, productList.stream().filter(product -> product.getId() == 1).count());
            assertEquals(1, productList.stream().filter(product -> product.getId() == 2).count());
            assertEquals(1, productList.stream().filter(product -> product.getId() == 3).count());
            assertEquals(0, productList.stream().filter(product -> product.getId() == 4).count());

            assertEquals(new BigDecimal("3"), productList.stream().filter(product -> product.getId() == 1).findFirst().map(Product::getPrice).orElseThrow());

            assertEquals("Since your order is over $50 including food and drinks, each latte costs you $3", result.getPromotionDescription());

        }
    }
}