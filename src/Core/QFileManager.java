// Copyright (C) 2016
//
// This file is part of Qedit.
//
// Qedit is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Qedit is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Qedit.  If not, see <http://www.gnu.org/licenses/>.
//
// author : Quentin Parrod - qparrod@gmail.com

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
import org.eclipse.swt.widgets.Display;
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
    
    public QFileType(String filePath, String fileName)
    {
        this.filePath = filePath;
        this.fileName = fileName;
    }
    
    public QFileType()
    {
        qprint.verbose("created");
        this.filePath       = "";
        this.fileName       = "";
        this.timeLastOpened = "";
        this.timeCreated    = "";
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
    private QTree  qtree;
    private String workspace;
    private QPrint qprint = new QPrint("QFileManager");
    
    public QFileManager(String root)
    {
        workspace = root;
        qprint.verbose("create QFileManager in " + root);
    }
    
    public void setTree(QTree tree) { this.qtree = tree; }
    
    public void add()
    {
        qprint.verbose("Add pressed. Create File");
        
        Display display = Display.getDefault();
        
        QFileManagerShell fm = new QFileManagerShell();
        while (!fm.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        QFileType file = fm.getFile();
        if ( null == file )
        {
            qprint.error("file is null");
            return;
        }
        
        // create a new file and update tree
        File f = new File(file.getFilePath());
        try
        {
            qprint.verbose("create file "+f.getName());
            f.createNewFile();
            qtree.update();
        } 
        catch (IOException e)
        {
            qprint.error("Imposible to create file");
        }
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
        if (null == workspace)
        {
            qprint.error("workspace not defined");
            return;
        }
        fd.setFilterPath(workspace);
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
    private QFileType file;
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
        
        Label lblDir = new Label(composite, SWT.NONE);
        lblDir.setText("Directory");
        gridData= new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = false;
        lblDir.setLayoutData(gridData);
        
        final Text textDir = new Text(composite, SWT.BORDER);
        gridData= new GridData();
        if ( System.getProperty("os.name").startsWith("Linux") )
        {
            textDir.setText("/home/qparrod/workspace/qedit/store/");
        }
        else if ( System.getProperty("os.name").startsWith("Windows") )
        {
            textDir.setText("G:/repo_qedit/store/");
        }
        
        gridData.horizontalAlignment = GridData.FILL;
        textDir.setLayoutData(gridData);
        
        Button btnBrowse = new Button(composite, SWT.NONE);
        btnBrowse.setText("Browse...");
        btnBrowse.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e)
            {
                qprint.verbose("Browse");
                DirectoryDialog dd = new DirectoryDialog(shell, SWT.OPEN);
                dd.setText("Browse...");
                String s = "";
                if ( System.getProperty("os.name").startsWith("Linux") )
                {
                    s = "/home/qparrod/workspace/qedit/store/";
                }
                else if ( System.getProperty("os.name").startsWith("Windows") )
                {
                    s = "G:/repo_qedit/store/";
                }
                dd.setFilterPath(s);
                String selected = dd.open();
                if (selected != null)
                {
                    textDir.setText(selected+"/");
                }
                qprint.verbose(selected);
            }
        });
        
        Label lblName = new Label(composite, SWT.NONE);
        lblName.setText("File name");
        gridData= new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = false;
        lblName.setLayoutData(gridData);
        final Text textName = new Text(composite, SWT.BORDER);
        gridData= new GridData();
        
        gridData.horizontalAlignment = GridData.FILL;
        textName.setLayoutData(gridData);
        
        Button btnOk = new Button(composite, SWT.NONE);
        btnOk.setText("OK");
        btnOk.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e)
            {
                qprint.verbose("OK. dir=" + textDir.getText()+", file=" + textName.getText());
                Path path = Paths.get(textDir.getText()+textName.getText());
                qprint.verbose(path.toString());
                
                // TODO : check if path is a correct one
                
                file = new QFileType();
                qprint.verbose("path="+path.toString());
                qprint.verbose("name="+path.getFileName().toString());
                file.setFileName(path.getFileName().toString());
                file.setFilePath(path.toString());
                file.setTimeCreated("0h00");
                file.setTimeLastOpened("0h00");
                shell.dispose();
            }
        });
        
        qprint.verbose("begin");
        shell.open();
        
    }
    
    public QFileType getFile()
    {
        return file;
    }
    
    
    protected void checkSubclass()
    {
        // Disable the check that prevents subclassing of SWT components
    }
}
