package Core;

public class QFileManager
{
    private String mainPath;
    public QFileManager(String root)
    {
        System.out.println("create QFileManager in " + root);
        this.mainPath = root;
    }
    
    public void add()
    {
        System.out.println("Add pressed. Create File");
        // step 1: open a new windows to choose the name of file
        // step 2: save file
    }
    
    public void del()
    {
        System.out.println("Delete pressed");
    }
    
    public void open()
    {
        System.out.println("Open pressed");
    }
    
    public void save()
    {
        System.out.println("Save pressed");
    }
}
