package br.alan.roles.truck;

import java.util.ArrayList;
import java.util.List;

public class GarbageAmount {

    private double[] garbagesAmount = new double[GarbageTypeEnum.values().length]; // Paper, Metal, Glass, and Plastic

    public synchronized void sumGarbageAmount(GarbageTypeEnum garbageTypeEnum, double amount) throws GarbageOverFlowException {
        double value = this.garbagesAmount[garbageTypeEnum.getIndex()] + amount;

        if (value > 1d || value < 0d)
            throw new GarbageOverFlowException();

        this.garbagesAmount[garbageTypeEnum.getIndex()] = value;
    }

    public synchronized double[] getGarbagesAmount() {
        return garbagesAmount;
    }

    public synchronized void setGarbagesAmount(double[] garbagesAmount) {
        this.garbagesAmount = garbagesAmount;
    }

    public synchronized void setGarbagesAmount(String garbagesAmount, Integer step) throws GarbageOverFlowException{
        double[] amount = new double[this.garbagesAmount.length];

        for (int i = 0; i < this.garbagesAmount.length; i++) {

            double value = Double.valueOf(garbagesAmount.substring(step * i,(step * i) +1));

            if (value > 1d || value < 0d)
                throw new GarbageOverFlowException();

            amount[i] = value;
        }

        this.setGarbagesAmount(amount);
    }

    public synchronized String garbageAmountToString(){
        String amount = "";

        for (int i = 0; i < this.garbagesAmount.length; i++) {
            amount += this.garbagesAmount[i];
        }

        return amount;
    }

    public synchronized double totalValue() {
        double totalValue = 0d;
        for (int i = 0; i < this.garbagesAmount.length; i++) {
            totalValue += this.garbagesAmount[i];
        }
        return totalValue;
    }

    public synchronized List<GarbageTypeEnum> getAvailableGarbages() {
        List<GarbageTypeEnum> garbageTypeEnums = new ArrayList<>();
        for (int i = 0; i < this.garbagesAmount.length; i++) {
            if (this.garbagesAmount[i] < 1d) {
                garbageTypeEnums.add(GarbageTypeEnum.valueOfIndex(i));
            }
        }
        return garbageTypeEnums;
    }

    public synchronized List<GarbageTypeEnum> getNotEmptyGarbages() {
        List<GarbageTypeEnum> garbageTypeEnums = new ArrayList<>();
        for (int i = 0; i < this.garbagesAmount.length; i++) {
            if (this.garbagesAmount[i] > 0d) {
                garbageTypeEnums.add(GarbageTypeEnum.valueOfIndex(i));
            }
        }
        return garbageTypeEnums;
    }


    public boolean isFull() {
        return this.totalValue() > this.garbagesAmount.length;
    }

    public boolean isAny() {
        return this.totalValue() > 0d;
    }

    public boolean isEmpty() {
        return this.totalValue() == 0d;
    }

}
