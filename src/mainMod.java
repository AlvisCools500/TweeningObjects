import javax.swing.*;
import java.util.concurrent.Callable;

class task {

    public static void Wait(double Seconds) {
        try {
            Thread.sleep((int) (Seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Thread spawnReturnal(Runnable f) {
        Thread thread = new Thread(f);
        thread.start();

        return thread;
    }

    public static void spawn(Runnable f) {
        Thread thread = spawnReturnal(f);
    }
}

public class mainMod {
    public static VectorInt2D Resolution = new VectorInt2D(800, 800);

    public static long GetLongClock() {
        return System.nanoTime();
    }

    public static double GetDoubleClock() {
        long TheClock = GetLongClock();
        double ResClock = (TheClock / 1e9);

        return ResClock;
    }

    public static float GetFloatClock() {
        long TheClock = GetLongClock();
        float ResClock = (float) (TheClock / 1e9);



        return ResClock;
    }



}
