package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.ProductType;
import com.inatlas.coffeeshop.models.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LotOfProductsPromotionTest {

    @InjectMocks
    private LotOfProductsPromotion lotOfProductsPromotion;

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
        @DisplayName("When there are 8 different products")
        class WhenThereAre8DifferentProducts {

            @BeforeEach
            void setUp() {

                order = new Order(Map.of(
                        1, 1,
                        2, 1,
                        3, 1,
                        4, 1,
                        5, 1,
                        6, 1,
                        7, 1,
                        8, 1));
            }

            @Test
            @DisplayName("should return false")
            void test() {

                var result = lotOfProductsPromotion.isPromotionApplicable(order, receipt, productList);

                assertFalse(result);
            }

        }

        @Nested
        @DisplayName("When there are 9 different products")
        class WhenThereAre9DifferentProducts {

            @BeforeEach
            void setUp() {

                order = new Order(Map.of(
                        1, 1,
                        2, 1,
                        3, 1,
                        4, 1,
                        5, 1,
                        6, 1,
                        8, 1,
                        9, 1,
                        7, 1));
            }

            @Test
            @DisplayName("should return true")
            void test() {

                var result = lotOfProductsPromotion.isPromotionApplicable(order, receipt, productList);

                assertTrue(result);
            }

        }

        @Nested
        @DisplayName("When there are 8 equal products")
        class WhenThereAre8EqualProducts {

            @BeforeEach
            void setUp() {

                order = new Order(Map.of(1, 8));
            }

            @Test
            @DisplayName("should return true")
            void test() {

                var result = lotOfProductsPromotion.isPromotionApplicable(order, receipt, productList);

                assertFalse(result);
            }

        }

        @Nested
        @DisplayName("When there are 9 equal products")
        class WhenThereAre9EqualProducts {

            @BeforeEach
            void setUp() {

                order = new Order(Map.of(1, 9));
            }

            @Test
            @DisplayName("should return true")
            void test() {

                var result = lotOfProductsPromotion.isPromotionApplicable(order, receipt, productList);

                assertTrue(result);
            }

        }
    }

    @Nested
    class BuildPromotionReceipt {

        @BeforeEach
        void setUp() {
            receipt = Receipt.builder().total(new BigDecimal(100)).promotionDescription("No promotion").build();
        }

        @Test
        void test() {

            var result = lotOfProductsPromotion.buildPromotionReceipt(order, receipt, productList);

            assertEquals(new BigDecimal("95.00"), result.getTotal());
            assertEquals(new BigDecimal("0.05"), result.getDiscountPercent());
            assertEquals("You have a 5% discount on the final price for ordering more than 8 products", result.getPromotionDescription());

        }
    }
}
