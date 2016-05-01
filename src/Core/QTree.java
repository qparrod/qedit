package Core;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.io.File;

import Logging.Debug;

import java.lang.System;

public class QTree
{
    private Text text;
    
    public QTree(Tree tree)
    {
        Debug.verbose("build QTree");
        this.open(tree);
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
        System.out.println("createNode : " + root.getPath());
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
        String ROOT = "";
        if ( System.getProperty("os.name").startsWith("Linux") )
        {
            ROOT = "/home/qparrod/workspace/qedit/store";
        }
        else if ( System.getProperty("os.name").startsWith("Windows") )
        {
            ROOT = "G:/repo_qedit/store";
        }
        File root = new File(ROOT);
        createNode(root, tree);
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