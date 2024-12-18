import java.rmi.server.UID;

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
}

class UDim {
    float scale;
    int offset;

    public UDim(float Scale, int Offset) {
        scale = Scale;
        offset = Offset;
    }

}

class UDim2 {
    UDim x;
    UDim y;

    public UDim2(float ScaleX, int OffsetX, float ScaleY, int OffsetY) {
        x = new UDim(ScaleX, OffsetX);
        y = new UDim(ScaleY, OffsetX);
    }

    public UDim2(UDim UX, UDim UY) {
        x = UX;
        y = UY;
    }
}

public class VectorClass {

    public VectorClass() {}

}
