package br.alan.roles;

import java.util.List;

public interface Role {

    String GARBAGE_TRUCK = "CA";
    String RECYCLING_TRUCK = "CR";
    String CONTAINER = "CO";
    String TERMINAL = "TE";


    List<Runnable> getProcesses();
}
