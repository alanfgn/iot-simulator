package br.alan.profiles;

import java.util.List;

public interface Profile {

    String GARBAGE_TRUCK = "garbage_truck";
    String RECYCLING_TRUCK = "garbage_truck";

    List<Runnable> getProcesses();
}
