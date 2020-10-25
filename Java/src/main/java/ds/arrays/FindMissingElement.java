package ds.arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FindMissingElement {
    /**
     * Build an array with random unique numbers with in specified bound,
     * with a random missing number
     *
     * @param size the size of the array
     * @return the array
     */
    public static int[] buildArrayOfIntWithMissingElement(int size) {
        Random r = new Random();
        size = size - 2;
        int e = r.nextInt(size);

        System.out.println("Missing element is " + e);

        int[] arr = new int[size];
        Set<Integer> exists = new HashSet<Integer>(size);
        for (int i = 0; i < size; i++) {
            do {
                int n = getRandomWithExclusion(new Random(), 0, size, e);
                if (!exists.contains(n) && n != e) {
                    arr[i] = n;
                    exists.add(n);
                    break;
                }
            } while (true);
        }

        return arr;
    }

    /**
     * Finds and return the missing
     * @param array
     * @return
     */
    public static int find(int[] array) {
        for (int index = 0; index < array.length; index++) {
            if (index + 1 != array[index]) {
                return array[index] - 1;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        int[] arr = buildArrayOfIntWithMissingElement(100);
        System.out.println(Arrays.toString(arr));
//        System.out.println("Missing element is " + o.find(arr));
    }


    public static int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }
}
