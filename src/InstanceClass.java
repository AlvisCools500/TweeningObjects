import java.awt.*;
import java.util.HashMap;

class Frame {
    HashMap<IEnum.Properties, Object> properties = new HashMap<>();
    ListInst myList;

    public Frame() {
        InstanceClass.AddDefaults(properties);
        myList = new ListInst(IEnum.IsA.Frame, this);
        MainCanvas.objectList.add(myList);

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
