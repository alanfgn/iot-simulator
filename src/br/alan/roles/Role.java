package br.alan.roles;

import java.util.List;

public interface Role {

    String GARBAGE_TRUCK = "CR";
    String RECYCLING_TRUCK = "CA";
    String CONTAINER = "CO";


    List<Runnable> getProcesses();
}
