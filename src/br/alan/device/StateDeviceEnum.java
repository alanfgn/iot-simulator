package br.alan.device;

public enum StateDeviceEnum {
    ATIVADO("ativado"),
    DESATIVADO("desativado");

    private String name;

    private StateDeviceEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name.toUpperCase();
    }

    public static StateDeviceEnum valueOfName(String name) {
        for(StateDeviceEnum state : values()) {
            if(state.getName().equalsIgnoreCase(name))
                return state;
        }
        throw new IllegalArgumentException();
    }
}
