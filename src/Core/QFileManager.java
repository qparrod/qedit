package Core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.*;
import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
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
        Shell shell = new Shell();
        FileDialog fd = new FileDialog(shell, SWT.OPEN);
        fd.setText("Open");
        String s = "";
        if ( System.getProperty("os.name").startsWith("Linux") )
        {
            s = "/home/qparrod/workspace/qedit/store/";
        }
        else if ( System.getProperty("os.name").startsWith("Windows") )
        {
            s = "G:/repo_qedit/store/";
        }
        fd.setFilterPath(s);
        String[] filterExt = {"*.txt","*.log", "*"};
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        qprint.verbose(selected);
        
        // add file to editor
        
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
        shell.setText("Add");
        shell.setLayout(new GridLayout(3, false));
        
        Composite composite = new Composite(this.shell, SWT.NONE);
        composite.setLayout(new GridLayout(3, true));
        GridData gridData= new GridData();
        composite.setLayoutData(gridData);
        
        //label + text + bouton browse
        Label lblName = new Label(composite, SWT.NONE);
        lblName.setText("File name");
        gridData= new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = false;
        lblName.setLayoutData(gridData);
        
        final Text textName = new Text(composite, SWT.BORDER);
        gridData= new GridData();
        if ( System.getProperty("os.name").startsWith("Linux") )
        {
            textName.setText("/home/qparrod/workspace/qedit/store/");
        }
        else if ( System.getProperty("os.name").startsWith("Windows") )
        {
            textName.setText("G:/repo_qedit/store/");
        }
        
        gridData.horizontalAlignment = GridData.FILL;
        textName.setLayoutData(gridData);
        
        Button btnBrowse = new Button(composite, SWT.NONE);
        btnBrowse.setText("Browse...");
        btnBrowse.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e)
            {
                qprint.verbose("Browse");
                DirectoryDialog dlg = new DirectoryDialog(shell);
                dlg.setFilterPath(textName.getText());
                dlg.setText("Directory Dialog");
                dlg.setMessage("Select a directory");
                String dir = dlg.open();
                if (dir != null)
                {
                    textName.setText(dir);
                }
            }
        });
        
        Button btnOk = new Button(composite, SWT.NONE);
        btnOk.setText("OK");
        btnOk.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e)
            {
                qprint.verbose("OK. file=" + textName.getText());
                Path path = Paths.get(textName.getText());
                qprint.verbose(path.toString());
                if (path.isAbsolute())
                {
                    qprint.verbose("path is absolute");
                }
                qprint.verbose(path.getFileName().toString());
                qprint.verbose("" + path.getNameCount());
                for (int fileIdx=0;fileIdx<path.getNameCount();fileIdx++)
                {
                    //if ()
                }
            }
        });
        
        qprint.verbose("begin");
        shell.open();
        
    }
    
    protected void checkSubclass()
    {
        // Disable the check that prevents subclassing of SWT components
    }
}
