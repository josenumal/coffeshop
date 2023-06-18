package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.exceptions.BadOrderException;
import com.inatlas.coffeeshop.models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceImplTest {

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    private Order order;
    private List<Product> productList;

    @BeforeEach
    void setUp() {

        productList = List.of(Product.builder().id(1).productName("Product 1").price(new BigDecimal("1.2")).build(),
                Product.builder().id(2).productName("Product 2").price(new BigDecimal("2.3")).build(),
                Product.builder().id(3).productName("Product 3").price(new BigDecimal("3.4")).build());
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

                var exception = assertThrows(BadOrderException.class, () -> receiptService.createReceipt(order, productList));

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

                var exception = assertThrows(BadOrderException.class, () -> receiptService.createReceipt(order, productList));

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

                var exception = assertThrows(BadOrderException.class, () -> receiptService.createReceipt(order, productList));

                assertEquals("Order is null or empty", exception.getMessage());

            }

        }

        @Nested
        @DisplayName("When item is not in product list")
        class OrderItemsIsNotInList {

            @BeforeEach
            void setUp() {
                order = new Order(Map.of(1, 3, 4, 2));
            }

            @Test
            @DisplayName("should throws BadOrderException")
            void test() {

                var exception = assertThrows(BadOrderException.class, () -> receiptService.createReceipt(order, productList));

                assertEquals("In product list there is none with id 4", exception.getMessage());

            }

        }

    }

    @Nested
    class OrderOk {

        @Nested
        @DisplayName("When items are in product list")
        class OrderItemsIsNotInList {

            List<Product> productList;

            @BeforeEach
            void setUp() {
                order = new Order(Map.of(1, 3, 2, 2));
                productList = List.of(Product.builder().id(1).productName("Product 1").price(new BigDecimal("1.2")).build(),
                        Product.builder().id(2).productName("Product 2").price(new BigDecimal("2.3")).build(),
                        Product.builder().id(3).productName("Product 3").price(new BigDecimal("3.4")).build());
            }

            @Test
            @DisplayName("should return a correct receipt")
            void test() {

                var result = receiptService.createReceipt(order, productList);

                assertNotNull(result);
                assertTrue(result.getFreeReceiptItemSet().isEmpty());

                //Product 1
                assertTrue(result.getPaidReceiptItemSet().stream().anyMatch(paidReceiptItem -> paidReceiptItem.getProductName().equals("Product 1")));
                var product1 = result.getPaidReceiptItemSet().stream().filter(paidReceiptItem -> paidReceiptItem.getProductName().equals("Product 1")).findFirst().orElseThrow();
                assertEquals(new BigDecimal("3.6"), product1.getTotal());

                //Product 2
                assertTrue(result.getPaidReceiptItemSet().stream().anyMatch(paidReceiptItem -> paidReceiptItem.getProductName().equals("Product 2")));
                var product2 = result.getPaidReceiptItemSet().stream().filter(paidReceiptItem -> paidReceiptItem.getProductName().equals("Product 2")).findFirst().orElseThrow();
                assertEquals(new BigDecimal("4.6"), product2.getTotal());

                //Product 3
                assertTrue(result.getPaidReceiptItemSet().stream().noneMatch(paidReceiptItem -> paidReceiptItem.getProductName().equals("Product 3")));

                assertEquals(new BigDecimal("8.2"), result.getTotal());

            }

        }

    }

}