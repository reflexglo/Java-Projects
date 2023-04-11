public class BiomeTile {
    int x;
    int y;
    int biome;
    int width;
    int height;
    boolean trace;
    //Generic biome tile class
    public BiomeTile(int x, int y, int width, int height, int biome)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.biome = biome;
    }
    //Get and set methods for fields
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    public int getBiome()
    {
        return biome;
    }
    public void setTrace(boolean trace)
    {
        this.trace = trace;
    }
    public boolean getTrace()
    {
        return trace;
    }
}
