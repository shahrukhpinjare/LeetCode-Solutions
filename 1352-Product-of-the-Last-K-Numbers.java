
import java.util.*;

class ProductOfNumbers {

    private List<Integer> prefixProduct;

    public ProductOfNumbers() {
        prefixProduct = new ArrayList<>();
        prefixProduct.add(1);
    }

    public void add(int num) {
        if (num == 0) {
            // Reset prefix products after zero
            prefixProduct.clear();
            prefixProduct.add(1);
        } else {
            int lastProduct = prefixProduct.get(prefixProduct.size() - 1);
            prefixProduct.add(lastProduct * num);
        }
    }

    public int getProduct(int k) {
        int n = prefixProduct.size();

        // If k is greater than the number of
        // elements since the last zero
        if (k >= n) {
            return 0;
        }

        return prefixProduct.get(n - 1) /
               prefixProduct.get(n - 1 - k);
    }
}
