package Core;

import Logging.QPrint;

public class QFileManager
{
    //private String mainPath;
    private QPrint qprint = new QPrint("QFileManager");
    
    public QFileManager(String root)
    {
        qprint.verbose("create QFileManager in " + root);
        //this.mainPath = root;
    }
    
    public void add()
    {
        qprint.verbose("Add pressed. Create File");
        // step 1: open a new windows to choose the name of file
        // step 2: save file
    }
    
    public void del()
    {
        qprint.verbose("Delete pressed");
    }
    
    public void open()
    {
        qprint.verbose("Open pressed");
    }
    
    public void save()
    {
        qprint.verbose("Save pressed");
    }
}
