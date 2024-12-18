import java.util.HashMap;
import javax.swing.Timer;

class EasingService {

    public enum EasingStyle {
        LINEAR,
        SINE,
        EXPONENTIAL,
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
        switch (style) {
            case SINE:
                return GetSine(alpha, direction);
            case LINEAR:
                return alpha;
            default:
                return alpha;
        }
    }


    public double GetSine(double t, EasingDirection dir) {
        switch (dir) {
            default -> {
                return easeInOutSine(t);
            }
            case IN -> {
                return easeInSine(t);
            }
            case OUT -> {
                return easeOutSine(t);
            }
            case INOUT -> {
                return easeInOutSine(t);
            }
        }
    }

    // Sine Easing Functions
    public static double easeInSine(double t) {
        return (double) -Math.cos(t * (Math.PI / 2)) + 1;
    }

    public static double easeOutSine(double t) {
        return (double) Math.sin(t * (Math.PI / 2));
    }

    public static double easeInOutSine(double t) {
        return (double) (0.5 * (1 - Math.cos(Math.PI * t)));
    }

    // Quad Easing Functions
    public static float easeInQuad(float t) {
        return t * t;
    }

    public static float easeOutQuad(float t) {
        return -t * (t - 2);
    }

    public static float easeInOutQuad(float t) {
        if (t < 0.5f) return 2 * t * t;
        return -2 * t * (t - 2) - 1;
    }

    // Exponential Easing Functions
    public static float easeInExpo(float t) {
        return t == 0 ? 0 : (float) Math.pow(2, 10 * (t - 1));
    }

    public static float easeOutExpo(float t) {
        return t == 1 ? 1 : (float) (-Math.pow(2, -10 * t) + 1);
    }

    public static float easeInOutExpo(float t) {
        if (t == 0 || t == 1) return t;
        if (t < 0.5f) return 0.5f * (float) Math.pow(2, 20 * t - 10);
        return 0.5f * ((float) -Math.pow(2, -20 * t + 10) + 2);
    }

    // Cubic Easing Functions
    public static float easeInCubic(float t) {
        return t * t * t;
    }

    public static float easeOutCubic(float t) {
        t--;
        return t * t * t + 1;
    }

    public static float easeInOutCubic(float t) {
        if (t < 0.5f) return 4 * t * t * t;
        t -= 1;
        return 4 * t * t * t + 1;
    }

    // Back Easing Functions
    public static float easeInBack(float t) {
        float s = 1.70158f;
        return t * t * ((s + 1) * t - s);
    }

    public static float easeOutBack(float t) {
        float s = 1.70158f;
        t -= 1;
        return t * t * ((s + 1) * t + s) + 1;
    }

    public static float easeInOutBack(float t) {
        float s = 1.70158f * 1.525f;
        if (t < 0.5f) return 2 * t * t * ((s + 1) * t - s);
        t -= 1;
        return 2 * t * t * ((s + 1) * t + s) + 1;
    }

    // Bounce Easing Functions
    public static float easeInBounce(float t) {
        return 1 - easeOutBounce(1 - t);
    }

    public static float easeOutBounce(float t) {
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

    public static float easeInOutBounce(float t) {
        if (t < 0.5f) return easeInBounce(t * 2) * 0.5f;
        return easeOutBounce(t * 2 - 1) * 0.5f + 0.5f;
    }

    // Elastic Easing Functions
    public static float easeInElastic(float t) {
        float s = 1.70158f;
        float p = 0.3f;
        if (t == 0) return 0;
        if (t == 1) return 1;
        return (float) (-Math.pow(2, 10 * (t - 1)) * Math.sin((t - 1 - s / 4) * (2 * Math.PI) / p));
    }

    public static float easeOutElastic(float t) {
        float s = 1.70158f;
        float p = 0.3f;
        if (t == 0) return 0;
        if (t == 1) return 1;
        return (float) (Math.pow(2, -10 * t) * Math.sin((t - s / 4) * (2 * Math.PI) / p) + 1);
    }

    public static float easeInOutElastic(float t) {
        float s = 1.70158f;
        float p = 0.45f;
        if (t == 0) return 0;
        if (t == 1) return 1;
        if (t < 0.5f) return -0.5f * (float) (Math.pow(2, 20 * t - 10) * Math.sin((20 * t - 11.125f) * (2 * Math.PI) / p));
        return (float) (Math.pow(2, -20 * t + 10) * Math.sin((20 * t - 11.125f) * (2 * Math.PI) / p) + 1) * 0.5f;
    }
}

class Tween {
    float duration;
    private EasingService.EasingStyle easeStyle;
    private EasingService.EasingDirection easeDir;
    private ListInst targetinst;
    private HashMap<IEnum.Properties, Object> targetTable;

    public Tween(float Time, EasingService.EasingStyle EaseStyle, EasingService.EasingDirection EaseDirection, ListInst targetinst, HashMap<IEnum.Properties, Object> PropertyTarget) {
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

        long startClock = MainModule.GetLongClock();

        Timer timer = new javax.swing.Timer(0, null);

        EasingService easeServ = new EasingService();

        timer.addActionListener(e -> {
            long TargetClock = MainModule.GetLongClock();
            double ResClock = (double) (TargetClock - startClock) / 1e9;
            double Alpha = Math.clamp(ResClock/this.duration, 0, 1);
            double easedAlpha = easeServ.GetValue(Alpha, easeStyle, easeDir);

            for (var v : propEnd.entrySet()) {
                IEnum.Properties index = v.getKey();

                // UDim2 Support
                if (index == IEnum.Properties.Position || index == IEnum.Properties.Size) {

                    UDim2 targUDim2 = (UDim2) v.getValue();
                    UDim2 startUDim2 = (UDim2) propStart.get(index);

                    propertyTable.put(index, new UDim2(
                            basicAdds_Double(startUDim2.x.scale, targUDim2.x.scale, easedAlpha),
                            basicAdds_Int(startUDim2.x.offset, targUDim2.x.offset, easedAlpha),
                            basicAdds_Double(startUDim2.y.scale, targUDim2.y.scale, easedAlpha),
                            basicAdds_Int(startUDim2.y.offset, targUDim2.y.offset, easedAlpha)
                    ));

                    UDim2 temp = (UDim2) propertyTable.get(index);
                }
            }

            if (Alpha >= 1) {
                timer.stop();
            }
        });

        timer.start();
    }


}

public class TweenService {
    public TweenService() {}
}
