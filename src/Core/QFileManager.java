package Core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.*;
import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
    // TODO : add a list to save all files open?
    
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
        new QFileManagerShell();
        // list.add(newFile);
    }
    
    public void del()
    {
        qprint.verbose("Delete pressed");
    }
    
    public void open(Path file, Text text)
    {
        qprint.verbose("open file "+file.toString());
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(file, charset))
        {
            byte[] encoded = Files.readAllBytes(file);
            String toPrint = new String(encoded, charset);
            qprint.verbose("toPrint="+toPrint);
            if ( null == text ) { return; }
            text.setText(toPrint);
        }
        catch (IOException x)
        {
            qprint.error("Exception: " + x.getMessage());
        }
    }
    
    public void open()
    {
        qprint.verbose("Open pressed");
    }
    
    public void save(Path file, Text text)
    {
        qprint.verbose("Save pressed");
        String s = text.getText();
        byte data[] = s.getBytes();

        OutputStream out;
        try
        {
            out = new BufferedOutputStream(
                    Files.newOutputStream(file, CREATE, WRITE));
            qprint.verbose("message to write: "+s);
            out.write(data, 0, data.length);
            out.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

class QFileManagerShell extends Shell
{
    private QFileManagerShell shell;
    private QPrint qprint = new QPrint("QFileManagerShell");
    
    public QFileManagerShell()
    {
        shell = this;
        shell.setText("test");
        shell.setLayout(new GridLayout(2, false));
        
        Composite composite = new Composite(this.shell, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        GridData gridData= new GridData();
        composite.setLayoutData(gridData);
        
        qprint.verbose("begin");
        shell.open();
        
        // TODO: fill window with parameters
        
    }
    
    protected void checkSubclass()
    {
        // Disable the check that prevents subclassing of SWT components
    }
}
