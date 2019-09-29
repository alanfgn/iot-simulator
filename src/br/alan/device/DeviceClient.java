package br.alan.device;

import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class DeviceClient {

    private Device device;
    private Socket socket;
    private DeviceSettings deviceSettings;

    private PrintStream printStream;
    private Scanner scanner;

    public DeviceClient(Socket socket, Device device) {
        this.socket = socket;
        this.device = device;
    }

    public DeviceClient(String name, String host, Integer port, String role, Device device) throws IOException {
        this(new DeviceSettings(name, host, port, role), device);
    }

    public DeviceClient(DeviceSettings deviceSettings, Device device) throws IOException {
        this.socket = new Socket(deviceSettings.getHost(), deviceSettings.getPort());
        this.device = device;
        this.deviceSettings = deviceSettings;
    }

    public String getName() {
        return this.deviceSettings.getName();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }


    public PrintStream getPrintStream() throws IOException {
        if (this.printStream == null) {
            return new PrintStream(this.getOutputStream());
        }
        return this.printStream;
    }

    public Scanner getScanner() throws IOException {
        if (this.scanner == null) {
            return new Scanner(this.getInputStream());
        }
        return this.scanner;
    }

    public synchronized void sendEncryptedMessage(String message, String key, String algorithm)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException,
            IllegalBlockSizeException, InvalidKeyException {

        String messageEncryptedString = this.encrypt(message, key, algorithm);
        this.send(messageEncryptedString);
    }

    public String getNextEcryptedMessage(String key, String algorithm) throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        return this.decrypt(this.nextMessage(), key, algorithm);
    }

    public synchronized void send(String message) throws IOException {
        PrintStream printStream = getPrintStream();
        printStream.println(message);
        printStream.flush();
    }

    public void printAllMessages() throws IOException {
        Scanner sc = this.getScanner();

        while (sc.hasNextLine()) {
            this.device.print(deviceSettings.getName() + " >> " + sc.nextLine());
        }

        this.device.print(" Ending conection with " + deviceSettings.getName());
    }

    public String nextMessage() throws IOException {
        Scanner sc = this.getScanner();

        if (sc.hasNextLine()) {
            return sc.nextLine();
        }

        return null;
    }


    public Socket getSocket() {
        return socket;
    }

    public void die() throws IOException {
        this.socket.close();
    }


    public static String encrypt(String toEncrypt, String key, String algorithm) throws NoSuchAlgorithmException,
            NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, UnsupportedEncodingException {
        // create a binary key from the argument key (seed)
        SecureRandom sr = new SecureRandom(key.getBytes());
        KeyGenerator kg = KeyGenerator.getInstance(algorithm);
        kg.init(sr);
        SecretKey sk = kg.generateKey();

        // create an instance of cipher
        Cipher cipher = Cipher.getInstance(algorithm);

        // initialize the cipher with the key
        cipher.init(Cipher.ENCRYPT_MODE, sk);

        // enctypt!
        byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String toDecrypt, String key, String algorithm) throws NoSuchAlgorithmException,
            NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, UnsupportedEncodingException {
        // create a binary key from the argument key (seed)
        SecureRandom sr = new SecureRandom(key.getBytes());
        KeyGenerator kg = KeyGenerator.getInstance(algorithm);
        kg.init(sr);
        SecretKey sk = kg.generateKey();

        // do the decryption with that key
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, sk);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(toDecrypt));

        return new String(decrypted);
    }
}


