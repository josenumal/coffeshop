package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.FreeReceiptItem;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SomeLattesPromotionTest {

    @InjectMocks
    private SomeLattesPromotion someLattesPromotion;

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
        @DisplayName("When there is not latte")
        class WhenThereIsNotLatte {

            @BeforeEach
            void setUp() {

                order = new Order(Map.of(
                        2, 1,
                        3, 1,
                        4, 1));
            }

            @Test
            @DisplayName("should return false")
            void test() {

                var result = someLattesPromotion.isPromotionApplicable(order, receipt, productList);

                assertFalse(result);
            }

        }

        @Nested
        @DisplayName("When there is 1 latte")
        class WhenThereIs1Latte {

            @BeforeEach
            void setUp() {

                order = new Order(Map.of(
                        1, 1,
                        2, 1,
                        3, 1,
                        4, 1));
            }

            @Test
            @DisplayName("should return false")
            void test() {

                var result = someLattesPromotion.isPromotionApplicable(order, receipt, productList);

                assertFalse(result);
            }

        }

        @Nested
        @DisplayName("When there are 2 latte")
        class WhenThereAre2Latte {

            @BeforeEach
            void setUp() {

                order = new Order(Map.of(
                        1, 2,
                        2, 1,
                        3, 1,
                        4, 1));
            }

            @Test
            @DisplayName("should return false")
            void test() {

                var result = someLattesPromotion.isPromotionApplicable(order, receipt, productList);

                assertTrue(result);
            }

        }

        @Nested
        @DisplayName("When there are 3 latte")
        class WhenThereAre3Latte {

            @BeforeEach
            void setUp() {

                order = new Order(Map.of(
                        1, 3,
                        2, 1,
                        3, 1,
                        4, 1));
            }

            @Test
            @DisplayName("should return false")
            void test() {

                var result = someLattesPromotion.isPromotionApplicable(order, receipt, productList);

                assertTrue(result);
            }

        }

    }

    @Nested
    class BuildPromotionReceipt {

        @Nested
        @DisplayName("When there are 2 latte")
        class WhenThereAre2Latte {

            @BeforeEach
            void setUp() {
                receipt = Receipt.builder().total(new BigDecimal("100.00")).discountPercent(BigDecimal.ZERO).promotionDescription("No promotion").build();
                ReflectionTestUtils.setField(someLattesPromotion, "latteAmount", 2);
            }

            @Test
            @DisplayName("should return 1 espresso free")
            void test() {

                var result = someLattesPromotion.buildPromotionReceipt(order, receipt, productList);

                assertEquals(new BigDecimal("100.00"), result.getTotal());
                assertEquals(BigDecimal.ZERO, result.getDiscountPercent());
                assertEquals(1, result.getFreeReceiptItemSet().stream()
                        .filter(freeReceiptItem -> freeReceiptItem.getProductName().equals("Espresso"))
                        .mapToInt(FreeReceiptItem::getAmount).sum());
                assertEquals(1, result.getFreeReceiptItemSet().stream()
                        .mapToInt(FreeReceiptItem::getAmount).sum());
                assertEquals("You have received a free espresso for every two lattes ordered", result.getPromotionDescription());

            }
        }

        @Nested
        @DisplayName("When there are 10 latte")
        class WhenThereAre10Latte {

            @BeforeEach
            void setUp() {
                receipt = Receipt.builder().total(new BigDecimal("100.00")).discountPercent(BigDecimal.ZERO).promotionDescription("No promotion").build();
                ReflectionTestUtils.setField(someLattesPromotion, "latteAmount", 10);
            }

            @Test
            @DisplayName("should return 5 espresso free")
            void test() {

                var result = someLattesPromotion.buildPromotionReceipt(order, receipt, productList);

                assertEquals(new BigDecimal("100.00"), result.getTotal());
                assertEquals(BigDecimal.ZERO, result.getDiscountPercent());
                assertEquals(5, result.getFreeReceiptItemSet().stream()
                        .filter(freeReceiptItem -> freeReceiptItem.getProductName().equals("Espresso"))
                        .mapToInt(FreeReceiptItem::getAmount).sum());
                assertEquals("You have received a free espresso for every two lattes ordered", result.getPromotionDescription());

            }
        }

        @Nested
        @DisplayName("When there are 9 latte")
        class WhenThereAre9Latte {

            @BeforeEach
            void setUp() {
                receipt = Receipt.builder().total(new BigDecimal("100.00")).discountPercent(BigDecimal.ZERO).promotionDescription("No promotion").build();
                ReflectionTestUtils.setField(someLattesPromotion, "latteAmount", 9);
            }

            @Test
            @DisplayName("should return 4 espresso free")
            void test() {

                var result = someLattesPromotion.buildPromotionReceipt(order, receipt, productList);

                assertEquals(new BigDecimal("100.00"), result.getTotal());
                assertEquals(BigDecimal.ZERO, result.getDiscountPercent());
                assertEquals(4, result.getFreeReceiptItemSet().stream()
                        .filter(freeReceiptItem -> freeReceiptItem.getProductName().equals("Espresso"))
                        .mapToInt(FreeReceiptItem::getAmount).sum());
                assertEquals("You have received a free espresso for every two lattes ordered", result.getPromotionDescription());

            }
        }
    }
}