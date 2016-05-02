package Core;

import Logging.QPrint;

class QFileType
{
    private String filePath;
    private String fileName;
    private String timeLastOpened;
    private String timeCreated;
    
    private QPrint qprint = new QPrint("QFileType");
    
    public QFileType()
    {
        qprint.verbose("created");
        filePath="";
        fileName="";
        timeLastOpened="";
        timeCreated="";
    }
    
    public String getFilePath()       { return filePath; }
    public String getFileName()       { return fileName; }
    public String getTimeLastOpened() { return timeLastOpened; }
    public String getTimeCreated()    { return timeCreated; }
    
    public void setFilePath(String filePath) {this.filePath = filePath;}
    public void setFileName(String fileName) {this.fileName = fileName;}
    public void setTimeLastOpened(String timeLastOpened) {this.timeLastOpened = timeLastOpened;}
    public void setTimeCreated(String timeCreated) {this.timeCreated = timeCreated;}
}

public class QFileManager
{
    // TODO : add a list to save all files open 
    
    private QPrint qprint = new QPrint("QFileManager");
    
    public QFileManager(String root)
    {
        qprint.verbose("create QFileManager in " + root);
    }
    
    public void add()
    {
        qprint.verbose("Add pressed. Create File");
        // step 1: open a new windows to choose the name of file
        // step 2: save file
        
        QFileType newFile = new QFileType();
        newFile.setFileName("toto");
        // list.add(newFile);
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
