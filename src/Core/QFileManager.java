package Core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.*;
import java.io.*;

import org.eclipse.swt.widgets.Text;

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
        /*
        QPrint qprint = new QPrint("Main::open");
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
        }*/
    }
    
    public void save(Path file, Text text)
    {
        qprint.verbose("Save pressed");
        String s = text.getText();
        byte data[] = s.getBytes();

        try
        {
            Files.deleteIfExists(file);
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        OutputStream out;
        try
        {
            out = new BufferedOutputStream(
                    Files.newOutputStream(file, CREATE, WRITE));
            qprint.verbose("data: "+data.toString());
            out.write(data, 0, data.length);
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
