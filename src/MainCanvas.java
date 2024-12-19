
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

class ListInst {
    IEnum.IsA TypeInst;
    Object Instance;
    UUID uuid = UUID.randomUUID();

    public ListInst(IEnum.IsA typeInst, Object inst) {
        this.TypeInst = typeInst;
        this.Instance = inst;
    }
}

public class MainCanvas extends JPanel implements Runnable {

    public static World world = new World();
    public static JFrame mainJFrame;

    public static VectorInt2D Resolution = new VectorInt2D(mainMod.Resolution.x, mainMod.Resolution.y);

    public static double CenterX = -8;
    public static double CenterY = -20;

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

        HashMap<UUID, ListInst> objectList = world.GetInstances();


        // Prepare ZIndexes
        for (var a : objectList.entrySet()) {
            ListInst v = a.getValue();

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

                    inst.draw(g, originalTransform);


                }

                g.setTransform(originalTransform);
            }
        }


        //g.setColor(Color.black);

        //int Width = MainCanvas.mainJFrame.getWidth();
        //int Height = MainCanvas.mainJFrame.getHeight();

        //g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
        //g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
    }

    public MainCanvas() {
        System.out.println("Start");
        OpenThread();

        task.spawn(() -> OnStart());
    }

    public void OnStart() {


        task.spawn(() -> AnimFraming1());


        System.out.println("YAY ITS ENDING!");


    }

    public void AnimFraming1() {
        Frame myFrame2 = new Frame();
        HashMap<IEnum.Properties, Object> myProp = myFrame2.properties;
        // myFrame2.properties.put(IEnum.Properties.UIRatio, (double) 1);
        myFrame2.properties.put(IEnum.Properties.ShowPoint, false);

        // Position
        instServ.setPosition(myProp, new UDim2(0.5,0,0.5,0));
        instServ.setAnchorPoint(myProp, new VectorDouble2D(0.5,0.5));

        instServ.setSize(myProp, new UDim2(0.5,0,0.5,0));
        instServ.setColor(myProp, new Color(32, 202, 225));
        instServ.setZIndex(myProp, -10);


        ArrayList<Tween> tweenList = new ArrayList<>();

        HashMap<IEnum.Properties, Object> targetProp = new HashMap<>();

        instServ.setSize(targetProp, new UDim2(0.1, 0, 0.1 ,0));

        tweenList.add(new Tween(0.25, EasingService.EasingStyle.SINE, EasingService.EasingDirection.OUT, myFrame2.myList, (HashMap<IEnum.Properties, Object>) targetProp.clone()));

        targetProp.clear();

        instServ.setSize(targetProp, new UDim2(0.3, 0, 0.3 ,0));

        tweenList.add(new Tween(0.25, EasingService.EasingStyle.EXPONENTIAL, EasingService.EasingDirection.IN, myFrame2.myList, (HashMap<IEnum.Properties, Object>) targetProp.clone()));

        targetProp.clear();

        instServ.setSize(targetProp, new UDim2(0.5, 0, 0.5 ,0));
        //instServ.setTransparency(targetProp, 0);


        tweenList.add(new Tween(0.5, EasingService.EasingStyle.ELASTIC, EasingService.EasingDirection.OUT, myFrame2.myList, (HashMap<IEnum.Properties, Object>) targetProp.clone()));

        task.Wait(1);

        tweenList.get(0).play();
        tweenList.get(0).waitEnd();

        tweenList.get(1).play();
        tweenList.get(1).waitEnd();

        tweenList.get(2).play();
        tweenList.get(2).waitEnd();
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