public class SubstitutionCipher {
    private int key; // Numeric key

    public SubstitutionCipher(int key) {
        this.key = key;
    }

    // Encrypt the plaintext by shifting each character by the key
    public String encrypt(String plaintext) {
        StringBuilder encrypted = new StringBuilder();

        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shifted = (c - base + key) % 26 + base; // Shift with wrap-around
                encrypted.append((char) shifted);
            } else {
                encrypted.append(c); // Leave non-letters as is
            }
        }

        return encrypted.toString();
    }

    // Decrypt the ciphertext by reversing the shift
    public String decrypt(String ciphertext) {
        StringBuilder decrypted = new StringBuilder();

        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shifted = (c - base - key + 26) % 26 + base; // Reverse shift with wrap-around
                decrypted.append((char) shifted);
            } else {
                decrypted.append(c); // Leave non-letters as is
            }
        }

        return decrypted.toString();
    }
}
