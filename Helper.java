// Helper-Klasse mit selbstgeschriebenen Hilfsmethoden zu Arrays und Lösungen aus H-/P-Aufgaben Lösungen 
// Geschrieben von Daniel Poock für eigene Nutzung während der Präsenzhausaufgabe am 15.11.2023
// Stand: 15.11.2023 (15:31)

import javax.crypto.spec.PSource;
import javax.swing.*;
import java.util.Arrays;

public class Helper {
    public static void main(String[] args) {
        
    }
    
    // Anfang selbstgeschriebene Helfer-Methoden (Manche könnten ähnlich zu Lösungen sein wegen ähnlicher Funktionalität)
    public static int[] reverseIntArray(int[] array) {
        int[] temp = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            temp[temp.length - 1 - i] = array[i];
        }
        return temp;
    }

    public static float avgOfArray(int[] array) {
        float avg = 0;
        for (int i : array) {
            avg += i;
        }
        return avg / array.length;
    }

    public static void copyArrayOfSameLength(int[] array1, int[] array2) {
        for (int i = 0; i < array1.length; i++) {
            array2[i] = array1[i];
        }
    }

    public static boolean isPalindrome(int[] array) {
        return Arrays.equals(array, reverseIntArray(array));
    }

    public static boolean isInArray(int item, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == item) {
                return true;
            }
        }
        return false;
    }

    public static int indexOfFirstItem(int item, int[] array) {
        if (isInArray(item, array)) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == item) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int indexOfNthItem(int item, int n, int[] array) {
        if (n > numberOfItemsInArray(item, array) || n < 1) {
            return -1;
        }
        int anzBisher = 1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == item) {
                if (anzBisher == n) {
                    return i;
                }
                anzBisher++;
            }
        }
        return -1;
    }

    public static int numberOfItemsInArray(int item, int[] array) {
        int erg = 0;
        if (!isInArray(item, array)) {
            return erg;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == item) {
                erg++;
            }
        }
        return erg;
    }

    public static int[] arrayWithoutDuplicates(int[] array) {
        int[] tempArray = new int[array.length];
        int nod = 0;
        for (int i = 0, j = 0; i < array.length; i++) {
            if (!isInArray(array[i], tempArray)) {
                tempArray[j] = array[i];
                nod++;
                j++;
            }
        }
        return Arrays.copyOf(tempArray, nod);
    }

    public static int numberOfDistinct(int[] array) {
        int erg = 0;
        int[] tempArray = new int[array.length];
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (!isInArray(array[i], tempArray)) {
                tempArray[index] = array[i];
                index++;
            }
        }
        return index;
    }

    public static float calculateFloatingPoint(int[] bitString) {
        if (bitString.length != 32 || numberOfItemsInArray(0, bitString) + numberOfItemsInArray(1, bitString) != bitString.length) {
            return -1;
        }
        int vorzeichen = bitString[0] == 1 ? -1 : 1;
        long exponent = binaryToDecimalUnsigned(Arrays.copyOfRange(bitString, 1, 9)) - 127;
        float mantisse = 1.0f + binaryToDecimalUnsignedFraction(Arrays.copyOfRange(bitString, 9, bitString.length));

        return mantisse * powOf2Long(exponent) * vorzeichen;
    }

    public static int binaryToDecimalUnsigned(int[] bitString) {
        int[] revBitString = reverseIntArray(bitString);
        if (bitString.length == 0) {
            return -1;
        }
        int erg = 0;
        for (int i = 0; i < revBitString.length; i++) {
            erg += revBitString[i] * powOf2(i);
        }
        return erg;
    }

    public static float binaryToDecimalUnsignedFraction(int[] bitString) {
        int[] revBitString = reverseIntArray(bitString);
        if (bitString.length == 0) {
            return -1.0f;
        }
        float erg = 0.0f;
        for (int i = 0; i < revBitString.length; i++) {
            erg += revBitString[i] * powOf2Fraction(-i - 1);
        }
        return erg;
    }

    // Funktioniert nicht
    public static int binaryToDecimalSigned(int[] bitString) {
        int[] revBitString = reverseIntArray(bitString);
        if (bitString.length == 0) {
            return 0;
        }
        int erg = 0;
        for (int i = 0; i < revBitString.length - 1; i++) {
            erg += revBitString[i] * powOf2(i);
        }
        int vorzeichen = revBitString[revBitString.length - 1] == 0 ? 1 : -1;
        return erg * vorzeichen;
    }

    public static int pow(int basis, int exponent) {
        int erg = 1;
        for (int i = 0; i < exponent; i++) {
            erg *= basis;
        }
        return erg;
    }

    public static int powOf2(int exponent) {
        int erg = 1;
        for (int i = 0; i < exponent; i++) {
            erg *= 2;
        }
        return erg;
    }

    public static long powOf2Long(long exponent) {
        long erg = 1;
        for (long i = 0; i < exponent; i++) {
            erg *= 2;
        }
        return erg;
    }

    public static float powOf2Fraction(int exponent) {
        float erg = 1;
        for (int i = 0; i > exponent; i--) {
            erg /= 2.0f;
        }
        return erg;
    }

    public static int lengthOf2DArray(int[][] array) {
        int length = 0;
        for (int i = 0; i < array.length; i++) {
            length += array[i].length;
        }
        return length;
    }

    public static int minLengthArrayIn2DArray(int[][] array) {
        if (array.length == 0) {
            return -1;
        }
        int minLength = array[0].length;
        for (int i = 1; i < array.length; i++) {
            if (array[i].length < minLength) {
                minLength = array[i].length;
            }
        }
        return minLength;
    }

    public static int maxLengthArrayIn2DArray(int[][] array) {
        if (array.length == 0) {
            return -1;
        }
        int maxLength = array[0].length;
        for (int i = 1; i < array.length; i++) {
            if (array[i].length > maxLength) {
                maxLength = array[i].length;
            }
        }
        return maxLength;
    }
    // Ende selbstgeschriebener Methoden

    // Von W02P02 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W02P02/repos/pgdp2324w02p02-solution/browse/src/pgdp/math/ControlStructuresI.java (Stand: 15.11.23)
    public static int calculateNumberOfDigits(int n) {
        int numberOfDigits = 0;
        while (n > 0) {
            n = n / 10;
            numberOfDigits = numberOfDigits + 1;
        }
        return numberOfDigits;
    }

    // Von W02P02 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W02P02/repos/pgdp2324w02p02-solution/browse/src/pgdp/math/ControlStructuresI.java (Stand: 15.11.23)
    public static int reverseNumber(int n) {
        int reversedNumber = 0;
        while (n > 0) {
            reversedNumber = reversedNumber * 10;
            reversedNumber = reversedNumber + n % 10;
            n = n / 10;
        }
        return reversedNumber;
    }
    

    // Von W03P01 Lösungen (abgeänderter Funktionsname): https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P01/repos/pgdp2324w03p01-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static void printArray(int[] a) {
        System.out.print("{");

        if (a.length > 0) {
            System.out.print(a[0]);
            for (int i = 1; i < a.length; i++) {
                System.out.print(", " + a[i]);
            }
        }

        System.out.print("}");
    }

    // Von W03P01 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P01/repos/pgdp2324w03p01-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static void minAndMax(int[] a) {
        if (a.length == 0) {
            return;
        }

        int min = a[0];
        int max = a[0];

        for (int i = 0; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
            }
            if (a[i] > max) {
                max = a[i];
            }
        }

        System.out.println("Minimum = " + min + ", Maximum = " + max);
    }

    // Von W03P01 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P01/repos/pgdp2324w03p01-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static boolean isOrderedAscendingly(int[] a) {
        boolean isOrdered = true;
        for (int i = 0; i < a.length - 1; i++) {
            isOrdered &= a[i] <= a[i + 1];
        }
        return isOrdered;
    }

    // Von W03P01 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P01/repos/pgdp2324w03p01-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static void invert(int[] a) {
        for (int i = 0; i < a.length / 2; i++) {
            int tmp = a[i];
            int symmetricalIndex = a.length - 1 - i;
            a[i] = a[symmetricalIndex];
            a[symmetricalIndex] = tmp;
        }
    }

    // Von W03P01 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P01/repos/pgdp2324w03p01-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static int[] resize(int[] a, int length) {
        if (length <= 0) {
            return new int[0];
        }

        int[] resized = new int[length];

        for (int i = 0; i < length && i < a.length; i++) {
            resized[i] = a[i];
        }

        return resized;
    }

    // Von W03P01 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P01/repos/pgdp2324w03p01-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static int[] filterEvenNumbersFrom(int[] a) {
        int numberOfEvenElements = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] % 2 == 0) {
                numberOfEvenElements++;
            }
        }

        int[] out = new int[numberOfEvenElements];
        for (int i = 0, indexOut = 0; i < a.length; i++) {
            if (a[i] % 2 == 0) {
                out[indexOut] = a[i];
                indexOut++;
            }
        }

        return out;
    }

    // Von W03P01 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P01/repos/pgdp2324w03p01-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static int[] distinct(int[] a) {
        int[] outArrayWithPadding = new int[a.length];
        int firstUnusedPosition = 0;

        // Copy everything into 'outArrayWithPadding' that isn't already in there
        for (int i = 0; i < a.length; i++) {
            if (!containsBeforeIndex(outArrayWithPadding, a[i], firstUnusedPosition)) {
                outArrayWithPadding[firstUnusedPosition] = a[i];
                firstUnusedPosition++;
            }
        }
        return resize(outArrayWithPadding, firstUnusedPosition);
    }

    // Von W03P01 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P01/repos/pgdp2324w03p01-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    private static boolean containsBeforeIndex(int[] a, int value, int index) {
        for (int i = 0; i < index; i++) {
            if (a[i] == value) {
                return true;
            }
        }
        return false;
    }


    // Von W03P02 Lösungen (Aufruf von 'minAndMax' zu 'minAndMax2' abgeändert): https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P02/repos/pgdp2324w03p02-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static int[][] minsAndMaxs(int[][] a) {
        int[][] out = new int[a.length][2];

        for (int i = 0; i < a.length; i++) {
            // 2D Arrays are arrays of arrays
            // so a[i] is an array
            out[i] = minAndMax2(a[i]);
        }

        return out;
    }

    // Von W03P02 Lösungen (Funktionsname geändert): https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P02/repos/pgdp2324w03p02-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static int[] minAndMax2(int[] a) {
        if (a.length == 0) {
            return null;
        }

        int min = a[0];
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
            }
            if (a[i] > max) {
                max = a[i];
            }
        }
        return new int[]{min, max};
    }

    // Von W03P02 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P02/repos/pgdp2324w03p02-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static int[][] transpose(int[][] a) {
        if (a.length == 0 || a[0].length == 0) {
            return null;
        }

        int[][] transposed = new int[a[0].length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                transposed[j][i] = a[i][j];
            }
        }

        return transposed;
    }

    // Von W03P02 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03P02/repos/pgdp2324w03p02-solution/browse/src/pgdp/array/Array.java (Stand: 15.11.23)
    public static int[] linearize(int[][] a) {
        int linearizedlength = 0;

        for (int i = 0; i < a.length; i++) {
            linearizedlength += a[i].length;
        }

        int[] linearized = new int[linearizedlength];
        int linearizedIndex = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                linearized[linearizedIndex++] = a[i][j];
            }
        }

        return linearized;
    }


    // Von W03H01 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03H01/repos/pgdp2324w03h01-solution/browse/src/pgdp/arrayfun/ArrayFunctions.java (Stand: 15.11.23)

    /**
     * Methode, die zwei Arrays zu einem verbindet, indem sie abwechselnd Einträge des ersten und des zweiten Input-
     * Arrays verwendet.
     *
     * @param a Ein beliebiges Integer-Array.
     * @param b Ein beliebiges Integer-Array.
     * @return 'a' und 'b' zusammengezipped.
     */
    public static int[] zip(int[] a, int[] b) {
        int[] zippedArray = new int[a.length + b.length];
        int minLength = Math.min(a.length, b.length);

        for (int i = 0; i < minLength; i++) {
            zippedArray[2 * i] = a[i];
            zippedArray[2 * i + 1] = b[i];
        }

        if (a.length < b.length) {
            for (int i = minLength; i < b.length; i++) {
                zippedArray[minLength + i] = b[i];
            }
        } else {
            for (int i = minLength; i < a.length; i++) {
                zippedArray[minLength + i] = a[i];
            }
        }

        return zippedArray;
    }


    // Von W03H01 Lösungen (mit Änderungen): https://bitbucket.ase.in.tum.de/projects/PGDP2324W03H01/repos/pgdp2324w03h01-solution/browse/src/pgdp/arrayfun/ArrayFunctions.java (Stand: 15.11.23)

    /**
     * Methode, die eine beliebige Zahl an Arrays (dargestellt als Array von Arrays) zu einem einzigen Array verbindet,
     * indem sie abwechselnd von jedem Array einen Eintrag nimmt, bis alle aufgebraucht sind.
     *
     * @param arrays Array von Integer-Arrays
     * @return Die Arrays in 'arrays' zusammengezipped
     */
    public static int[] zipMany(int[][] arrays) {
        int maxLength = maxLengthArrayIn2DArray(arrays);
        int[] zippedArray = new int[lengthOf2DArray(arrays)];

        int nextPosition = 0;
        for (int j = 0; j < maxLength; j++) {
            for (int i = 0; i < arrays.length; i++) {
                if (j < arrays[i].length) {
                    zippedArray[nextPosition] = arrays[i][j];
                    nextPosition++;
                }
            }
        }

        return zippedArray;
    }

    // Von W03H01 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03H01/repos/pgdp2324w03h01-solution/browse/src/pgdp/arrayfun/ArrayFunctions.java (Stand: 15.11.23)

    /**
     * Behält aus dem übergebenen Array nur die Einträge, die innerhalb der übergebenen Grenzen liegen.
     * Gibt das Ergebnis als neues Array zurück.
     *
     * @param array Ein beliebiges Integer-Array
     * @param min   Ein beliebiger Integer
     * @param max   Ein beliebiger Integer
     * @return Das gefilterte Array
     */
    public static int[] filter(int[] array, int min, int max) {
        int firstUnusedPosition = 0;
        int[] out = new int[array.length];
        for (int j = 0; j < array.length; j++) {
            if (array[j] >= min && array[j] <= max) {
                out[firstUnusedPosition] = array[j];
                firstUnusedPosition++;
            }
        }
        return Arrays.copyOf(out, firstUnusedPosition);
    }

    // Von W03H01 Lösungen (mit kleiner Änderung): https://bitbucket.ase.in.tum.de/projects/PGDP2324W03H01/repos/pgdp2324w03h01-solution/browse/src/pgdp/arrayfun/ArrayFunctions.java (Stand: 15.11.23)

    /**
     * Rotiert das übergebene Array um die übergebene Anzahl an Schritten nach rechts.
     * Das Array wird In-Place rotiert. Es gibt keine Rückgabe.
     *
     * @param array  Ein beliebiges Integer-Array
     * @param amount Ein beliebiger Integer
     */
    public static void rotate(int[] array, int amount) {
        int length = array.length;
        if (length == 0) {
            return;
        }
        amount = amount % length;
        if (amount < 0) {
            amount += length;
        }
        int[] rotated = new int[length];
        for (int i = 0; i < length; i++) {
            rotated[(i + amount) % length] = array[i];
        }
        copyArrayOfSameLength(rotated, array);
    }

    // Von W03H01 Lösungen: https://bitbucket.ase.in.tum.de/projects/PGDP2324W03H01/repos/pgdp2324w03h01-solution/browse/src/pgdp/arrayfun/ArrayFunctions.java (Stand: 15.11.23)

    /**
     * Zählt die Anzahl an Vorkommen jeder Zahl im übergebenen Array, die in diesem mindestens einmal vorkommt.
     * Die Rückgabe erfolgt über ein 2D-Array, bei dem jedes innere Array aus zwei Einträgen besteht: Einer Zahl,
     * die im übergebenen Array vorkommt sowie der Anzahl an Vorkommen dieser.
     * Für jede im übergebenen Array vorkommenden Zahl gibt es ein solches inneres Array.
     * Diese tauchen im Rückgabewert in der gleichen Reihenfolge auf, in der die jeweils ersten Vorkommen der Zahlen
     * im übergebenen Array auftauchen.
     *
     * @param array Ein beliebiges Integer-Array
     * @return Das Array mit den Vielfachheiten der einzelnen Zahlen, wiederum als Integer-Arrays mit zwei Einträgen dargestellt.
     */
    public static int[][] quantities(int[] array) {
        int[][] quantitiesArray = new int[array.length][2];
        int nextPosition = 0;

        for (int i = 0; i < array.length; i++) {
            boolean isInQuantitiesArray = false;
            for (int j = 0; j < nextPosition; j++) {
                if (quantitiesArray[j][0] == array[i]) {
                    quantitiesArray[j][1]++;
                    isInQuantitiesArray = true;
                    break;
                }
            }

            if (!isInQuantitiesArray) {
                quantitiesArray[nextPosition] = new int[]{array[i], 1};
                nextPosition++;
            }
        }

        // Cut of unnecessary parts
        int[][] reducedQuantitiesArray = new int[nextPosition][2];
        for (int i = 0; i < nextPosition; i++) {
            reducedQuantitiesArray[i] = quantitiesArray[i];
        }

        return reducedQuantitiesArray;
    }


}
