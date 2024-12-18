import java.util.HashMap;
import java.util.Objects;

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
    public float GetValue(float alpha, EasingStyle style, EasingDirection direction) {
        switch (style) {
            case SINE:
                return GetSine(alpha, direction);
            case LINEAR:
                return alpha;
            default:
                return alpha;
        }
    }


    public float GetSine(float t, EasingDirection dir) {
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
    public static float easeInSine(float t) {
        return (float) -Math.cos(t * (Math.PI / 2)) + 1;
    }

    public static float easeOutSine(float t) {
        return (float) Math.sin(t * (Math.PI / 2));
    }

    public static float easeInOutSine(float t) {
        return (float) (0.5 * (1 - Math.cos(Math.PI * t)));
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
    float clock;
    EasingService.EasingStyle easeStyle;
    EasingService.EasingDirection easeDir;

    HashMap<String, Objects> propertyTable = new HashMap<>();

    public Tween() {}

    public void play() {
        MainCanvas.tweenList.add(this);
    }


}

public class TweenService {
    public TweenService() {}
}
