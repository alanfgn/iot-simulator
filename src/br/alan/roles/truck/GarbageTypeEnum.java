package br.alan.roles.truck;


import java.util.List;

public enum GarbageTypeEnum {

    METAL(0),
    GLASS(1),
    PLASTIC(2),
    PAPER(3);

    private Integer index;

    private GarbageTypeEnum(Integer index){
        this.index = index;
    }

    public Integer getIndex() {
        return this.index;
    }

    public static GarbageTypeEnum valueOfIndex(Integer index) {

        for(GarbageTypeEnum garbage : values()) {
            if(garbage.index == index)
                return garbage;
        }

        throw new IllegalArgumentException();
    }


}
