
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

class ListInst {
    IEnum.IsA TypeInst;
    Object Instance;

    public ListInst(IEnum.IsA typeInst, Object inst) {
        this.TypeInst = typeInst;
        this.Instance = inst;
    }
}

public class MainCanvas extends JPanel implements Runnable {

    public static ArrayList<ListInst> objectList = new ArrayList<>();
    public static JFrame mainJFrame;

    public static VectorInt2D Resolution = new VectorInt2D(MainModule.Resolution.x, MainModule.Resolution.y);

    Thread mainThread;


    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);

       // System.out.println("Painted");

        ArrayList<Integer> ZIndexList = new ArrayList<>();
        HashMap<Integer, ArrayList<ListInst>> sortedZ = new HashMap<>();

        Graphics2D g = (Graphics2D) gr;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform originalTransform = g.getTransform();


        // Prepare ZIndexes
        for (int i = objectList.size() - 1; i>=0; i--) {
            ListInst v = objectList.get(i);

            int ZIndex = 0;

            var TempVal = (Frame) v.Instance;

            if (TempVal.properties.get(IEnum.Properties.ZIndex) != null) {
                ZIndex = (int) TempVal.properties.get(IEnum.Properties.ZIndex);

                if (sortedZ.get(ZIndex) == null) {
                    sortedZ.put(ZIndex, new ArrayList<>());
                    ZIndexList.add(ZIndex);
                }

                sortedZ.get(ZIndex).add(v);
            }
        }

        ZIndexList.sort(Integer::compareTo);

        for (int ZIndex : ZIndexList) {
            for (ListInst v : sortedZ.get(ZIndex)) {
                IEnum.IsA typeInst = v.TypeInst;

                if (typeInst == IEnum.IsA.Frame) {
                    Frame inst = (Frame) v.Instance;
                    HashMap<IEnum.Properties, Object> properties = inst.properties;

                    Color col = (Color) properties.get(IEnum.Properties.Color);
                    int rot = (int) properties.get(IEnum.Properties.Rotation);
                    UDim2 Size = (UDim2) properties.get(IEnum.Properties.Size);
                    UDim2 Pos = (UDim2) properties.get(IEnum.Properties.Position);
                    VectorDouble2D anchor = (VectorDouble2D) properties.get(IEnum.Properties.AnchorPoint);

                    VectorInt2D resPos = Pos.GetAbsolute();
                    VectorInt2D resSize;

                    g.setColor(col);


                    if (properties.get(IEnum.Properties.UIRatio) != null) {
                        double ratio = (double) properties.get(IEnum.Properties.UIRatio);
                        resSize = Size.GetAbsoluteRatio(ratio);
                    }else {
                        resSize = Size.GetAbsolute();
                    }



                    resPos.substract(new VectorInt2D((int) (resSize.x * anchor.x), (int) (resSize.y * anchor.y)));

                    g.translate(resPos.x, resPos.y);

                    g.rotate(Math.toRadians(rot));

                    g.fillRect(0, 0, resSize.x, resSize.y);

                    if (properties.get(IEnum.Properties.ShowPoint) != null) {
                        boolean ShowPoint = (boolean) properties.get(IEnum.Properties.ShowPoint);

                        if (ShowPoint) {
                            g.setTransform(originalTransform);
                            g.setColor(Color.BLACK);
                            g.fillRect(Pos.GetAbsolute().x - (16/2), Pos.GetAbsolute().y - (16/2), 16, 16);
                        }
                    }




                }

                g.setTransform(originalTransform);
            }
        }
    }

    public MainCanvas() {


        Timer timer = new Timer(0, null);
        timer.addActionListener(e -> OnStart(timer));
        timer.start();


        System.out.println("Start");
        OpenThread();



    }

    public void OnStart(Timer t) {
        Frame myFrame1 = new Frame();
        myFrame1.properties.put(IEnum.Properties.Size, new UDim2(0.25,0,0.25,0));
        myFrame1.properties.put(IEnum.Properties.Position, new UDim2(0.75,0,0.5,0));
        myFrame1.properties.put(IEnum.Properties.Color, new Color(109, 32, 225));

        Frame myFrame2 = new Frame();
        myFrame2.properties.put(IEnum.Properties.Size, new UDim2(0.25,0,25,0));
        myFrame2.properties.put(IEnum.Properties.Position, new UDim2(0.25,0,0.5,0));
        myFrame2.properties.put(IEnum.Properties.Color, new Color(32, 202, 225));
        myFrame2.properties.put(IEnum.Properties.UIRatio, (double) 1);
        myFrame2.properties.put(IEnum.Properties.ShowPoint, true);

        System.out.println("added MyFrame");

        HashMap<IEnum.Properties, Object> targetProp = new HashMap<>();
        targetProp.put(IEnum.Properties.Position, new UDim2(0.25,0,1,0));

        Tween tweens = new Tween(2, EasingService.EasingStyle.LINEAR, EasingService.EasingDirection.INOUT, myFrame2.properties, targetProp);

        tweens.play();

        t.stop();
    }

    @Override
    public void run() {
        while (mainThread != null) {
            //System.out.println("Run");
            // Draw
            repaint();
        }
    }

    public void OpenThread() {
        mainThread = new Thread(this);
        mainThread.start();
    }

    public static void main(String[] args) {
        // Run Frame
        mainJFrame = new JFrame("Minesweeper Java 2D");
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJFrame.setSize(Resolution.x, Resolution.y);
        mainJFrame.setLocationRelativeTo(null);
        mainJFrame.add(new MainCanvas());
        mainJFrame.setVisible(true);
    }


}