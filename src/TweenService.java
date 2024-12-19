import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import javax.swing.Timer;

class EasingService {

    public enum EasingStyle {
        LINEAR,
        SINE,
        EXPONENTIAL,
        CUBIC,
        ELASTIC,
        QUAD,
        BACK,
        BOUNCE,
    }

    public enum EasingDirection {
        IN,
        OUT,
        INOUT,
    }

    // Get Alpha Value
    public double GetValue(double alpha, EasingStyle style, EasingDirection direction) {
        double t = alpha;

        float s;
        float p;

        switch (style) {
            case BOUNCE:
                switch (direction) {
                    case INOUT:
                        return easeInOutBounce(t);
                    case OUT:
                        return easeOutBounce(t);
                    case IN:
                        return easeInBounce(t);
                }
            case ELASTIC:
                switch (direction) {
                    case INOUT:
                        s = 1.70158f;
                        p = 0.3f;
                        if (t == 0) return 0;
                        if (t == 1) return 1;
                        return (float) (-Math.pow(2, 10 * (t - 1)) * Math.sin((t - 1 - s / 4) * (2 * Math.PI) / p));
                    case OUT:
                        s = 1.70158f;
                        p = 0.3f;
                        if (t == 0) return 0;
                        if (t == 1) return 1;
                        return (float) (Math.pow(2, -10 * t) * Math.sin((t - s / 4) * (2 * Math.PI) / p) + 1);
                    case IN:
                        s = 1.70158f;
                        p = 0.45f;
                        if (t == 0) return 0;
                        if (t == 1) return 1;
                        if (t < 0.5f) return -0.5f * (float) (Math.pow(2, 20 * t - 10) * Math.sin((20 * t - 11.125f) * (2 * Math.PI) / p));
                        return (float) (Math.pow(2, -20 * t + 10) * Math.sin((20 * t - 11.125f) * (2 * Math.PI) / p) + 1) * 0.5f;
                }
            case BACK:
                switch (direction) {
                    case INOUT:
                        s = 1.70158f * 1.525f;
                        if (t < 0.5f) return 2 * t * t * ((s + 1) * t - s);
                        t -= 1;
                        return 2 * t * t * ((s + 1) * t + s) + 1;
                    case OUT:
                        s = 1.70158f;
                        t -= 1;
                        return t * t * ((s + 1) * t + s) + 1;
                    case IN:
                        s = 1.70158f;
                        return t * t * ((s + 1) * t - s);
                }
            case QUAD:
                switch (direction) {
                    case INOUT:
                        if (t < 0.5f) return 2 * t * t;
                        return -2 * t * (t - 2) - 1;
                    case OUT:
                        return -t * (t - 2);
                    case IN:
                        return t * t;
                }
            case CUBIC:
                switch (direction) {
                    case INOUT:
                        if (t < 0.5f) return 4 * t * t * t;
                        t -= 1;
                        return 4 * t * t * t + 1;
                    case OUT:
                        t--;
                        return -t * (t - 2);
                    case IN:
                        return t * t * t;
                }
            case EXPONENTIAL:
                switch (direction) {
                    case INOUT:
                        if (t == 0 || t == 1) return t;
                        if (t < 0.5f) return 0.5f * Math.pow(2, 20 * t - 10);
                        return 0.5f * (-Math.pow(2, -20 * t + 10) + 2);
                    case OUT:
                        return t == 1 ? 1 : (-Math.pow(2, -10 * t) + 1);
                    case IN:
                        return t == 0 ? 0 : Math.pow(2, 10 * (t - 1));}
            case SINE:
                switch (direction) {
                    case INOUT:
                        return (double) (0.5 * (1 - Math.cos(Math.PI * t)));
                    case OUT:
                        return (double) Math.sin(t * (Math.PI / 2));
                    case IN:
                        return (double) -Math.cos(t * (Math.PI / 2)) + 1;
                }
            case LINEAR:
                return alpha;
            default:
                return alpha;
        }
    }


    // Bounce Easing Functions
    public static double easeInBounce(double t) {
        return 1 - easeOutBounce(1 - t);
    }

    public static double easeOutBounce(double t) {
        if (t < (1 / 2.75f)) {
            return 7.5625f * t * t;
        } else if (t < (2 / 2.75f)) {
            t -= (1.5f / 2.75f);
            return 7.5625f * t * t + 0.75f;
        } else if (t < (2.5 / 2.75)) {
            t -= (2.25f / 2.75f);
            return 7.5625f * t * t + 0.9375f;
        } else {
            t -= (2.625f / 2.75f);
            return 7.5625f * t * t + 0.984375f;
        }
    }

    public static double easeInOutBounce(double t) {
        if (t < 0.5f) return easeInBounce(t * 2) * 0.5f;
        return easeOutBounce(t * 2 - 1) * 0.5f + 0.5f;
    }
}

class Tween {
    double duration;
    private EasingService.EasingStyle easeStyle;
    private EasingService.EasingDirection easeDir;
    private ListInst targetinst;
    private HashMap<IEnum.Properties, Object> targetTable;
    boolean isPlaying = false;
    CountDownLatch latchEvent = new CountDownLatch(1);

    public Tween(double Time, EasingService.EasingStyle EaseStyle, EasingService.EasingDirection EaseDirection, ListInst targetinst, HashMap<IEnum.Properties, Object> PropertyTarget) {
        this.easeStyle = EaseStyle;
        this.easeDir = EaseDirection;
        this.targetinst = targetinst;
        this.duration = Time;
        this.targetTable = PropertyTarget;
    }

    private double basicAdds_Double(double startVal, double endVal, double alpha) {
        return startVal + (endVal - startVal) * alpha;
    }

    private int basicAdds_Int(int startVal, int endVal, double alpha) {
        return (int) basicAdds_Double(startVal, endVal, alpha);
    }

    public void play() {
        if (!this.isPlaying) {
            this.isPlaying = true;

            HashMap<IEnum.Properties, Object> propStart = new HashMap<>();
            HashMap<IEnum.Properties, Object> propEnd = targetTable;

            HashMap<IEnum.Properties, Object> propertyTable;

            if (targetinst.TypeInst == IEnum.IsA.Frame) {
                Frame frame = (Frame) targetinst.Instance;

                propertyTable = frame.properties;
            }else {
                Frame frame = (Frame) targetinst.Instance;

                propertyTable = frame.properties;
            }

            for (var v : propEnd.entrySet()) {
                IEnum.Properties index = v.getKey();

                if (propertyTable.get(index) != null) {
                    propStart.put(index, propertyTable.get(index));
                }
            }

            long startClock = mainMod.GetLongClock();

            Timer timer = new javax.swing.Timer(0, null);

            EasingService easeServ = new EasingService();

            // Add Action
            timer.addActionListener(e -> {
                long TargetClock = mainMod.GetLongClock();
                double ResClock = (double) (TargetClock - startClock) / 1e9;
                double Alpha = Math.clamp(ResClock/this.duration, 0, 1);
                double easedAlpha = easeServ.GetValue(Alpha, easeStyle, easeDir);

                for (var v : propEnd.entrySet()) {
                    IEnum.Properties index = v.getKey();

                    String valType = v.getValue().getClass().getSimpleName();

                    // UDim2 Support
                    if (valType.equals("UDim2")) {

                        UDim2 targUDim2 = (UDim2) v.getValue();
                        UDim2 startUDim2 = (UDim2) propStart.get(index);

                        propertyTable.put(index, new UDim2(
                                basicAdds_Double(startUDim2.x.scale, targUDim2.x.scale, easedAlpha),
                                basicAdds_Int(startUDim2.x.offset, targUDim2.x.offset, easedAlpha),
                                basicAdds_Double(startUDim2.y.scale, targUDim2.y.scale, easedAlpha),
                                basicAdds_Int(startUDim2.y.offset, targUDim2.y.offset, easedAlpha)
                        ));
                    }else if (valType.equals("UDim")) {
                        UDim targUDim = (UDim) v.getValue();
                        UDim startUDim = (UDim) propStart.get(index);

                        propertyTable.put(index, new UDim(
                                basicAdds_Double(startUDim.scale, targUDim.scale, easedAlpha),
                                basicAdds_Int(startUDim.offset, targUDim.offset, easedAlpha)
                        ));
                    }else if (valType.equals("Double")) {
                        double targDoub = (double) v.getValue();
                        double startDoub = (double) propStart.get(index);

                        propertyTable.put(index, basicAdds_Double(startDoub, targDoub, easedAlpha));
                    }else if (valType.equals("Integer")) {
                        int targInt = (int) v.getValue();
                        int startInt = (int) propStart.get(index);

                        propertyTable.put(index, basicAdds_Int(startInt, targInt, easedAlpha));
                    }else if (valType.equals("VectorDouble2D")) {
                        VectorDouble2D targVect = (VectorDouble2D) v.getValue();
                        VectorDouble2D startVect = (VectorDouble2D) propStart.get(index);

                        propertyTable.put(index, new VectorDouble2D(
                                basicAdds_Double(startVect.x, targVect.x, easedAlpha),
                                basicAdds_Double(startVect.y, targVect.y, easedAlpha)
                        ));
                    }else {
                        System.out.println(valType + " is not available");
                    }
                }

                if (Alpha >= 1) {
                    latchEvent.countDown();
                    timer.stop();
                }
            });

            timer.start();
        }

    }

    public void waitEnd() {
        if (this.isPlaying) {
            try {
                latchEvent.await();
            }catch (InterruptedException a) {
                a.printStackTrace();
            }

        }
    }

}

public class TweenService {
    public TweenService() {}
}
