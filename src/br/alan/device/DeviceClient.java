package br.alan.device;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
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

    public String getName(){
        return this.deviceSettings.getName();
    }

    public  OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    public  InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }


    public  PrintStream getPrintStream() throws IOException {
//        if (this.printStream == null) {
            return new PrintStream(this.getOutputStream());
//        }
//        return this.printStream;
    }

    public  Scanner getScanner() throws IOException {
//        if (this.scanner == null) {
            return new Scanner(this.getInputStream());
//        }
//        return this.scanner;
    }

    public synchronized void send(String message) throws IOException {
        PrintStream printStream = getPrintStream();
        printStream.println(message);
        printStream.flush();
    }

    public  void printAllMessages() throws IOException {
        Scanner sc = this.getScanner();

        while (sc.hasNextLine()) {
            this.device.print(deviceSettings.getName() + " >> " + sc.nextLine());
        }

        this.device.print(" Ending conection with " + deviceSettings.getName());
    }

    public  String nextMessage() throws IOException {
        Scanner sc = this.getScanner();

        if (sc.hasNextLine()) {
            return sc.nextLine();
        }

        return null;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public  void die() throws IOException {
        this.socket.close();
    }
}
