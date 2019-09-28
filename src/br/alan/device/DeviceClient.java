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
    private PrintStream printStream;
    private DeviceSettings deviceSettings;

    public DeviceClient(Socket socket, Device device) {
        this.socket = socket;
        this.device = device;
    }

    public DeviceClient(String name, String host, Integer port, Device device) throws IOException {
        this(new DeviceSettings(name, host, port), device);
    }

    public DeviceClient(DeviceSettings deviceSettings, Device device) throws IOException {
        this.socket = new Socket(deviceSettings.getHost(), deviceSettings.getPort());
        this.device = device;
        this.deviceSettings = deviceSettings;
    }


    public PrintStream getPrintStream() throws IOException {
        if (printStream == null) {
            return new PrintStream(this.getOutputStream());
        }

        return printStream;
    }

    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public void send(String message) throws IOException {
        PrintStream printStream = getPrintStream();
        printStream.println(message);
        printStream.flush();
    }

    public void receive() throws IOException {
        Scanner sc = new Scanner(this.getInputStream());
        while (sc.hasNextLine()) {
            this.device.print(" client " + deviceSettings.getName() + " say >> " + sc.nextLine());
        }
        this.device.print("Ending conection with " + deviceSettings.getName());
    }

    public String receiveResponse() throws IOException {
        Scanner sc = new Scanner(this.getInputStream());
        if (sc.hasNextLine()) {
            return sc.nextLine();
        }
        return "";
    }
}
