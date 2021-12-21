package main;

public class Conversion {
    /***
     * Performs the NOT operation on the
     * binary string.
     * @param binary String on which the not
     *               operation is performed.
     * @return String representing new binary.
     */
    private static String notOp(String binary) {
        StringBuilder notBinary = new StringBuilder();

        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '0') {
                notBinary.append('1');
            } else {
                notBinary.append('0');
            }
        }

        return notBinary.toString();
    }

    /***
     * Converts a character code to binary,
     * then reverses the binary string.
     * @param code int that is to be converted
     *             to binary and reversed.
     * @return String representing binary number.
     */
    private static String convertToBinary(int code) {
        StringBuilder binary = new StringBuilder();

        while (true) {
            int quotient = code / 2;
            int digit = code % 2;
            binary.append(digit);
            code = quotient;

            if (quotient == 0) {
                break;
            }
        }

        binary.reverse();

        return binary.toString();
    }

    /***
     * Converts a binary string back to a character.
     * @param binary String that is to be converted
     *               to a character.
     * @return int representing character encoding.
     */
    private static int convertToAscii(String binary) {
        int result = 0;

        if (binary.charAt(0) == '1') {
            result = 1;
        }

        for (int i = 1; i < binary.length(); i++) {
            if (binary.charAt(i) == '0') {
                result = result * 2;
            } else {
                result = result * 2 + 1;
            }
        }

        return result;
    }

    /***
     * Encodes a String message into encoded binary.
     * @param message to be encoded.
     * @return string of encoded binary, null if
     * error has occurred.
     */
    public static String encodeMessage(String message) {
        try {

            // encoded output is saved onto a StringBuilder
            // object
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < message.length(); i++) {
                // converts character to int code
                int code = message.charAt(i);

                // Step 2: convert int code to binary
                String binary = convertToBinary(code);

                // Step 3: perform NOT operation on binary
                String notOp = notOp(binary);

                // Step 4: add new code to string
                output.append(notOp);

                // Step 5: adds space for each binary number
                output.append(" ");
            }

            return output.toString();

        } catch (Exception e) {
            // returns null if error occurs
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Decodes an encoded binary message back to
     * a readable format.
     * @param message to be decoded.
     * @return String of decoded message, null if
     * error occurs.
     */
    public static String decodeMessage(String message) {
        try {
            // output string containing decoded message
            StringBuilder output = new StringBuilder();

            // splits message into separate binary numbers,
            // as each character is separated by space
            String[] data = message.split(" ");

            for (String code : data) {
                // converts the binary back to
                // character format
                String binary = notOp(code);

                int character = convertToAscii(binary);
                output.append((char) character);
            }

            return output.toString();
        } catch (Exception e) {
            // returns null if error occurs
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            // input message
            String input = "AWD*/AW*DWA*d/WAD*/WA*/AWD*/D*This is a testing message";

            // encode message
            String encoded = encodeMessage(input);

            // decode message
            String decoded = decodeMessage(encoded);

            // checks for any null outputs (error may have occurred)
            if (decoded == null) {
                throw new Exception("Error! Decoded string is " + "\null!\nInput String: " + input);
            }

            // if input message doesn't match output message, then
            // throw an exception
            if (!decoded.equals(input)) {
                throw new Exception(String.format("Error! Decoded string does " + "not match input! Input: %s\nOutput: %s", input, decoded));
            } else {
                String output = String.format("Success!\n" + "Input: %s\n" + "Output: %s", input, decoded);

                System.out.println("----------------------------------");
                System.out.println(output);
                System.out.println("----------------------------------");
            }

        } catch (Exception e) {
            System.out.println("----------------------------------");
            e.printStackTrace();
            System.out.println("----------------------------------");
        }
    }
}
