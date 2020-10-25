package ds.arrays;

import java.util.Arrays;
import java.util.Random;

public class FindMissingElementOrderedArray {
    public int[] buildArrayOfIntWithMissingElement(int size) {
        Random r = new Random();
        size = size - 2;
        int e = r.nextInt(size);

        System.out.println("Missing element is " + e);

        int[] arr = new int[size];
        for (int i = 0; i < e - 1; i++) {
            arr[i] = i + 1;
        }

        System.out.println(Arrays.toString(arr));

        int a = e;
        for (int i = e - 1; i < size; i++) {
            arr[i] = ++a;
        }

        return arr;
    }

    public int find(int[] array) {
        for (int index = 0; index < array.length; index++) {
            if (index + 1 != array[index]) {
                return array[index] - 1;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        FindMissingElementOrderedArray o = new FindMissingElementOrderedArray();
        int[] arr = o.buildArrayOfIntWithMissingElement(100);
        System.out.println(Arrays.toString(arr));
        System.out.println("Missing element is " + o.find(arr));
    }
}
