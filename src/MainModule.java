public class MainModule {
    public static VectorInt2D Resolution = new VectorInt2D(900, 800);

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
