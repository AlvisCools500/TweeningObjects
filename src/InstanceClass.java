import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

class Frame {
    HashMap<IEnum.Properties, Object> properties = new HashMap<>();
    ListInst myList;

    public Frame() {
        InstanceClass.AddDefaults(properties);
        myList = new ListInst(IEnum.IsA.Frame, this);
        MainCanvas.objectList.add(myList);

    }

    public void draw(Graphics2D g, AffineTransform originTransform) {
        Color col = (Color) this.properties.get(IEnum.Properties.Color);
        int rot = (int) this.properties.get(IEnum.Properties.Rotation);
        UDim2 Size = (UDim2) this.properties.get(IEnum.Properties.Size);
        UDim2 Pos = (UDim2) this.properties.get(IEnum.Properties.Position);
        VectorDouble2D anchor = (VectorDouble2D) this.properties.get(IEnum.Properties.AnchorPoint);

        VectorInt2D resPos = Pos.GetAbsolute();
        VectorInt2D resSize;

        g.setColor(col);

        if (this.properties.get(IEnum.Properties.UIRatio) != null) {
            double ratio = (double) this.properties.get(IEnum.Properties.UIRatio);
            resSize = Size.GetAbsoluteRatio(ratio);
        }else {
            resSize = Size.GetAbsolute();
        }

        resPos.substract(new VectorInt2D((int) (resSize.x * anchor.x), (int) (resSize.y * anchor.y)));

        g.translate(resPos.x, resPos.y);

        g.rotate(Math.toRadians(rot));

        g.fillRect(0, 0, resSize.x, resSize.y);

        if (this.properties.get(IEnum.Properties.ShowPoint) != null) {
            boolean ShowPoint = (boolean) this.properties.get(IEnum.Properties.ShowPoint);

            if (ShowPoint) {
                g.setTransform(originTransform);
                g.setColor(Color.BLACK);
                g.fillRect(Pos.GetAbsolute().x - (16/2), Pos.GetAbsolute().y - (16/2), 16, 16);
            }
        }

    }
}

class IEnum {
    public enum Properties {
        Position,
        Size,
        ZIndex,
        Color,
        Transparency,
        Rotation,
        AnchorPoint,
        UICorner,
        UIRatio,
        ShowPoint,
        Text,
        TextFont,
        TextColor,
        TextSize,
        TextScaled,
    }
    public enum IsA {
        Frame,
        TextLabel,
    }
}

class instServ {
    public static void setPosition(HashMap<IEnum.Properties, Object> props, UDim2 value) {
        props.put(IEnum.Properties.Position, value);
    }

    public static void setRotation(HashMap<IEnum.Properties, Object> props, Integer value) {
        props.put(IEnum.Properties.Rotation, value);
    }

    public static void setSize(HashMap<IEnum.Properties, Object> props, UDim2 value) {
        props.put(IEnum.Properties.Size, value);
    }

    public static void setAnchorPoint(HashMap<IEnum.Properties, Object> props, VectorDouble2D value) {
        props.put(IEnum.Properties.AnchorPoint, value);
    }

    public static void setTransparency(HashMap<IEnum.Properties, Object> props, double value) {
        props.put(IEnum.Properties.Transparency, value);
    }

    public static void setColor(HashMap<IEnum.Properties, Object> props, Color value) {
        props.put(IEnum.Properties.Color, value);
    }

    public static void setZIndex(HashMap<IEnum.Properties, Object> props, int value) {
        props.put(IEnum.Properties.ZIndex, value);
    }
}

public class InstanceClass {
    public static void AddDefaults(HashMap<IEnum.Properties, Object> props) {
        props.put(IEnum.Properties.Position, new UDim2(0.5,0,0.5,0));
        props.put(IEnum.Properties.Size, new UDim2(0, 100, 0, 100));
        props.put(IEnum.Properties.ZIndex, (int) 0);
        props.put(IEnum.Properties.Color, new Color(255,255,255));
        props.put(IEnum.Properties.Transparency, 0);
        props.put(IEnum.Properties.Rotation, 0);
        props.put(IEnum.Properties.AnchorPoint, new VectorDouble2D(0.5,0.5));
        props.put(IEnum.Properties.UICorner, new UDim(0,0));
    }


}
