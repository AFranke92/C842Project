package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Part;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * @param part add part to allParts list
     */
    public static void addPart (Part part) {

        allParts.add(part);
    }

    /**
     * @param product add product to allProducts list
     */
    public static void addProduct (Product product) {

        allProducts.add(product);
    }

    /**
     * Searches through products by id
     * @param productId the product id part of search
     * @return all products matching search param
     */
    public static Product lookupProduct(int productId) {

        for (int i = 0, productSize = allProducts.size(); i < productSize; i++) {
            Product p = allProducts.get(i);
            if (p.getId() == productId)
                return p;
        }
        return null;
    }

    /**
     * Searches through products by name
     * @param productName the product name part of search
     * @return all products containing search param
     */
    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> products = FXCollections.observableArrayList();

        for (Product product : getAllProducts()){
            if(product.getName().contains(productName)){
                products.add(product);
            }
        }
        return products;
    }

    /**
     * Searches through parts by name
     * @param partName the part name part of search
     * @return all parts containing search param
     */
    public static ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for (Part part : getAllParts()) {
            if (part.getName().contains(partName)) {
                parts.add(part);
            }
        }
        return parts;
    }

    /**
     * Searches through parts by id
     * @param partId the part id part of search
     * @return all parts matching search param
     */
    public static Part lookupPart(int partId) {

        for (int i = 0, partsSize = allParts.size(); i < partsSize; i++) {
            Part p = allParts.get(i);
            if (p.getId() == partId)
                return p;
        }
        return null;
    }

    /**
     * Updates part after part is modified
     * @param index the index the part is stored at
     * @param selectedPart the part that is selected
     */
    public static void updatePart(int index, Part selectedPart) {
        Part tempPart = Inventory.lookupPart(index);
        Inventory.deletePart(tempPart);
        Inventory.addPart(selectedPart);
    }

    /**
     * Updates product after product is modified
     * @param index the index the product is stored at
     * @param newProduct the product that is selected
     */
    public static void updateProduct(int index, Product newProduct){
        Product tempProduct = Inventory.lookupProduct(index);
        Inventory.deleteProduct(tempProduct);
        Inventory.addProduct(newProduct);
    }

    /**
     * @return allParts list
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * @return allProducts list
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    /**
     * @param part deletes part from allParts list
     */
    public static void deletePart(Part part) {

        allParts.remove(part);
    }

    /**
     * @param product deletes product from allProducts list
     */
    public static void deleteProduct(Product product) {

        allProducts.remove(product);
    }

}
