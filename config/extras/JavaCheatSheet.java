
// JavaCheatSheet.java
//
// SPDX-FileCopyrightText: 2023 Alexander Murphy <amsupernova@pm.me>
//
// SPDX-License-Identifier: Apache-2.0
//
// The new Java?


/**
 * JavaDoc Comment
 */

// Statements must end in a semi-colon
import java.util.Scanner; // Import the Scanner *class* from java.util *package*


import java.security.*; // Import everything from this package (not a good idea for visibility)


// One file <-> one class ()
public class JavaCheatSheet {

    // Always start with main() in Java, nice!
    public static void main(String[] args) {


        // Individual statements can be sent to the JShell without a class though
        System.out.println("Hello there world");


        // .println() is flexible
        System.out.println(
                "Integer: " + 10 +
                " Double: " + 3.14 +
                " Boolean: " + true);

        // .println() automatically appens a newline char, but
        System.out.print("doesn't"); // Semi colons aren't required in the shell


        // .printf() formats strings/chars/floats etc, this prints pi to 5 decimail places!
        System.out.printf("pi = %.5f%n", Math.PI);


        // Scanner is useful for input, imported earlier
        Scanner scanner = new Scanner(System.in);


        // gimmieInput will bind to keys followed by <Enter>
        String gimmieInput = scanner.next();


        // only next int (must be an integer that is in range or an exception is raised)
        int numInt = scanner.nextInt();


        float numFloat = scanner.nextFloat(); // etc...


        // Camel case is convention for variable names
        int justAnInteger; // Declaration


        // many variables can be declared at once
        int a, b, c; // Declaration


        justAnInteger = 1; // Initialization


        a = b = c = 5; // Initialization


        byte myByte = 100; // A byte is 8-bit signed two's complement


        // Short is 16-bit
        short myShort = 32767;
        myShort += 1; // ==> -32768     Overflows wrap


        // Ints are 32-bit
        int myInt = (int) myByte; // Cast using parens


        // Longs are 64-bit
        long myLong = 9999L; // The L indicates a long literal


        // Floats are single precision IEEE 754
        float myFloat = 123.4f; // A trailing f indicates a float literal


        double myDooble = 123.4; // Decimal points indicate doubles by default


        boolean myBool = true; // or false, no caps here!


        char myChar = 'L'; // 16-bit unsigned Unicode


        final int myFixedVar = 5;
        // myFixedVar = 7; will raise an error in a program (but jshell ignores final)


        // exceedingly large numbers can leverage the BIG data types
        // they are immutable


        // BigInts are stored as arrays of bytes
        // Notice it's a class and can be manipulated using its own methods
        // They are initialised using a string
        BigInteger myMassiveInt = new BigInteger("12345675464573652");


        // BigDecimals have arbitrary precision
        // BigDecimals avoid the dreaded floating point inequality problem:
        float a = 0.03f;
        float b = 0.04f;
        float c = b - a;
        System.out.println(c); // ==> 0.00999999999999999998
        // vs
        BigDecimal A = new BigDecimal("0.03");
        BigDecimal B = new BigDecimal("0.04");
        BigDecimal C = B.subtract(A);
        System.out.println(C); // ==> 0.01


        // WARNING: when creating BIG numbers, imprecision in the initialisation values will be copied!
        // use the string constructors (as shown above, and here)
        BigDecimal tenCents = new BigDecimal("0.1");


        String myString = "Strings are great and can contain\n\n new lines and \t tabs!";
        // escape characters are not evaluated in the repl (use .println())


        String concater = "strings can be" + " concatenated";


        // String manipulations can be non-performant, so try the StringBuilder and StringBuffer classes
        // here's an example:

        StringBuilder stringBuilder = new StringBuilder();
        String inefficientString = "";
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(i).append(" ");
            inefficientString += i + " ";
        }
        System.out.println(stringBuilder);      // ==> this is fast
        System.out.println(inefficientString);  // ==> this aint




        // Arrays are zero-indexed (booo!) and mutable
        // arrays are classes, initialised with square brackets containing the length of the array
        int[] intArray = new int[10];
        intArray[0]; // ==> 0 (because there are 10 0s)
        intArray[5]; // ==> 0 (because there are 10 0s!)

        intArray[1] = 100;
        intArray[1]; // ==> 100 (because arrays are mutable)



    }


}
