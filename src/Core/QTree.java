package Core;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.io.File;

import Logging.Debug;

public class QTree
{
    private Text text;
    
    public QTree()
    {
        Debug.verbose("build tree");
    }
    
    private void createNode(File root, Tree tree)
    {
        File[] subs = root.listFiles();
        if (subs != null)
        {
            for (File f : subs)
            {
                TreeItem file = new TreeItem(tree, 0);
                file.setText(f.getName());
                if (f.isDirectory()) // folder
                {
                    createNode(f, file);
                    file.setExpanded(true);
                }
            }
        }
    }
    
    private void createNode(File root, TreeItem treeItem)
    {
        System.out.println("createNode");
        treeItem.setExpanded(true);
        File[] subs = root.listFiles();
        if (subs != null)
        {
            for (File f : subs)
            {
                TreeItem file = new TreeItem(treeItem, 0);
                file.setText(f.getName());
                if (f.isDirectory()) // folder
                {
                    createNode(f, file);
                    file.setExpanded(true);
                }
            }
        }
    }

    public void open(Tree tree)
    {
        Debug.verbose("open tree");
        String ROOT = "C:/users/qparrod/workspace/qedit2/store";
        File root = new File(ROOT);
        createNode(root, tree);
    }
    
    public void setComposite(Composite composite)
    {
        //this.composite = composite;
    }
    
    public void setOutput(Text text)
    {
        this.text = text;
        if (this.text == null)
        {
                System.out.println("test");
        }
    }
};