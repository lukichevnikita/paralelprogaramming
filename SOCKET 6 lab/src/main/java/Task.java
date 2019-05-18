public class Task extends Thread {
    double[] array;
    double sum;
    private boolean isCalculated = false;

    Task(double[] array){
        this.array = array;
        sum = 0;
    }

    @Override
    public void run() {

        for (double el : array){
            sum += el;
        }

        isCalculated = true;

    }

    public double getSum(){
        return sum;
    }

    public boolean isCalculated(){
        return isCalculated;
    }
}
