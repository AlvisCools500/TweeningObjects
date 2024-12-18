class VectorInt2D {
    int x;
    int y;

    // Creating Vector Int
    public VectorInt2D(int X, int Y) {
        this.x = X;
        this.y = Y;
    }

    // Manual

    // This + (X,Y)
    public void plus(int X, int Y) {
        this.x += X;
        this.y += Y;
    }

    // This - (X,Y)
    public void substract(int X, int Y) {
        this.x -= X;
        this.y -= Y;
    }

    // This / (X,Y)
    public void divide(int X, int Y) {
        this.x /= X;
        this.y /= Y;
    }

    // This * (X,Y)
    public void multiply(int X, int Y) {
        this.x *= X;
        this.y *= Y;
    }

    // With Other Vectors

    // This + Other
    public void plus(VectorInt2D other) {
        plus(other.x, other.y);
    }

    // This - (X,Y)
    public void substract(VectorInt2D other) {
        substract(other.x, other.y);
    }

    // This / (X,Y)
    public void divide(VectorInt2D other) {
        divide(other.x, other.y);
    }

    // This * (X,Y)
    public void multiply(VectorInt2D other) {
        multiply(other.x, other.y);
    }

    @Override
    public String toString() {
        return "x" + this.x + " y" + this.y;
    }
}

class VectorDouble2D {
    double x;
    double y;

    // Creating Vector Int
    public VectorDouble2D(double X, double Y) {
        this.x = X;
        this.y = Y;
    }

    // Manual

    // This + (X,Y)
    public void plus(double X, double Y) {
        this.x += X;
        this.y += Y;
    }

    // This - (X,Y)
    public void substract(double X, double Y) {
        this.x -= X;
        this.y -= Y;
    }

    // This / (X,Y)
    public void divide(double X, double Y) {
        this.x /= X;
        this.y /= Y;
    }

    // This * (X,Y)
    public void multiply(double X, double Y) {
        this.x *= X;
        this.y *= Y;
    }

    // With Other Vectors

    // This + Other
    public void plus(VectorDouble2D other) {
        plus(other.x, other.y);
    }

    // This - (X,Y)
    public void substract(VectorDouble2D other) {
        substract(other.x, other.y);
    }

    // This / (X,Y)
    public void divide(VectorDouble2D other) {
        divide(other.x, other.y);
    }

    // This * (X,Y)
    public void multiply(VectorDouble2D other) {
        multiply(other.x, other.y);
    }

    @Override
    public String toString() {
        return "x" + this.x + " y" + this.y;
    }
}

class UDim {
    double scale;
    int offset;

    public UDim(double Scale, int Offset) {
        scale = Scale;
        offset = Offset;
    }

    @Override
    public String toString() {
        return "sc" + this.scale + " of" + this.offset;
    }
}

class UDim2 {
    UDim x;
    UDim y;

    public UDim2(double ScaleX, int OffsetX, double ScaleY, int OffsetY) {
        x = new UDim(ScaleX, OffsetX);
        y = new UDim(ScaleY, OffsetY);
    }

    public UDim2(UDim UX, UDim UY) {
        x = UX;
        y = UY;
    }

    public VectorInt2D GetAbsolute() {
        int Width = MainCanvas.mainJFrame.getWidth();
        int Height = MainCanvas.mainJFrame.getHeight();


        VectorDouble2D ResVect = new VectorDouble2D(Width, Height);

        ResVect.x = (ResVect.x * this.x.scale);
        ResVect.y = (ResVect.y * this.y.scale);

        return new VectorInt2D((int) Math.round(ResVect.x), (int) Math.round(ResVect.y));
    }

    public VectorInt2D GetAbsoluteRatio(double ratio) {
        int Width = GetAbsolute().x;
        int Height = GetAbsolute().y;

        int newWidth = (int) (Height * ratio);
        int newHeight = Height;

        if (newWidth > Width) {
            newWidth = Width;
            newHeight = (int) (Width / ratio);
        }

        return new VectorInt2D(newWidth, newHeight);
    }

    @Override
    public String toString() {
        String str = "xs" + x.scale + " xo" + x.offset + " ys" + y.scale + " yo" + y.offset;

        return str;
    }
}

public class VectorClass {

    public VectorClass() {}

}
