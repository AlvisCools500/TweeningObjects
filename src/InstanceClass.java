import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

class Instance {
    HashMap<UUID, ListInst> listObj = new HashMap<>();
    ListInst Parent;
    ListInst myList = new ListInst(IEnum.IsA.Frame, this);

    public Instance() {
        this.setParent(MainCanvas.world.getListInst());
    }

    public HashMap<UUID, ListInst> GetInstances() {
        return listObj;
    }

    public ListInst getListInst() {
        return myList;
    }

    public void setParent(ListInst target) {

        if (this.Parent != null) {
            ListInst previousInst = (ListInst) this.Parent;
            Instance inst = (Instance) previousInst.Instance;

            inst.removeInstance(myList);

        }

        if (target != null) {
            ListInst targetInst = (ListInst) target;

            Instance inst = (Instance) targetInst.Instance;

            inst.listObj.put(myList.uuid,myList);
        }else {
            Parent = null;
        }
    }


    public void removeInstance(ListInst target) {
        Instance inst = (Instance) target.Instance;
        
    }
}

class World extends Instance {}

class Frame extends Instance {
    HashMap<IEnum.Properties, Object> properties = new HashMap<>();


    public Frame() {
        InstanceClass.AddDefaults(properties);
    }

    public void draw(Graphics2D g, AffineTransform originTransform) {
        Color col = (Color) this.properties.get(IEnum.Properties.Color);
        double rot = (double) this.properties.get(IEnum.Properties.Rotation);
        UDim2 Size = (UDim2) this.properties.get(IEnum.Properties.Size);
        UDim2 Pos = (UDim2) this.properties.get(IEnum.Properties.Position);
        VectorDouble2D anchor = (VectorDouble2D) this.properties.get(IEnum.Properties.AnchorPoint);
        double transparency = (double) this.properties.get(IEnum.Properties.Transparency);

        VectorInt2D resPos = Pos.GetAbsolute();
        VectorInt2D resSize;

        g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), (int) (255 * transparency)));

        if (this.properties.get(IEnum.Properties.UIRatio) != null) {
            double ratio = (double) this.properties.get(IEnum.Properties.UIRatio);
            resSize = Size.GetAbsoluteRatio(ratio);
        }else {
            resSize = Size.GetAbsolute();
        }

        g.setTransform(originTransform);

        VectorInt2D resAnchorPos = new VectorInt2D(
                (int) (resSize.x * anchor.x),
                (int) (resSize.y * anchor.y));

        resPos.substract(resAnchorPos);
        //resPos.substract(new VectorInt2D((resSize.x/2),(resSize.y/2)));
        g.translate(resPos.x + MainCanvas.CenterX,resPos.y + MainCanvas.CenterY);

        g.rotate(Math.toRadians(rot), resAnchorPos.x, resAnchorPos.y);

        g.fillRect(0, 0, resSize.x, resSize.y);

        if (this.properties.get(IEnum.Properties.ShowPoint) != null) {
            boolean ShowPoint = (boolean) this.properties.get(IEnum.Properties.ShowPoint);

            if (ShowPoint) {
                g.setTransform(originTransform);;
                g.translate(Pos.GetAbsolute().x + MainCanvas.CenterX, Pos.GetAbsolute().y + MainCanvas.CenterY);

                g.setColor(Color.BLACK);
                g.fillRect(-4, -4, 8, 8);
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

    public static void setRotation(HashMap<IEnum.Properties, Object> props, double value) {
        props.put(IEnum.Properties.Rotation, value);
    }

    public static void setSize(HashMap<IEnum.Properties, Object> props, UDim2 value) {
        props.put(IEnum.Properties.Size, value);
    }

    public static void setAnchorPoint(HashMap<IEnum.Properties, Object> props, VectorDouble2D value) {
        props.put(IEnum.Properties.AnchorPoint, value);
    }

    public static void setTransparency(HashMap<IEnum.Properties, Object> props, double value) {
        props.put(IEnum.Properties.Transparency, (double) value);
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
        props.put(IEnum.Properties.Transparency, (double) 1);
        props.put(IEnum.Properties.Rotation, (double) 0);
        props.put(IEnum.Properties.AnchorPoint, new VectorDouble2D(0.5,0.5));
        props.put(IEnum.Properties.UICorner, new UDim(0,0));
    }


}
