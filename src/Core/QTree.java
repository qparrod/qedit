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
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Qedit.  If not, see <http://www.gnu.org/licenses/>.
//
// author : Quentin Parrod - qparrod@gmail.com

package Core;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.io.File;

import Logging.QPrint;

import java.lang.System;

public class QTree
{
    private Tree   tree;
    private Text   text;
    private QPrint qprint = new QPrint("QTree");
    
    public QTree(Tree tree)
    {
        qprint.verbose("build QTree");
        this.tree = tree;
        this.update();
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
        qprint.verbose("createNode : " + root.getPath());
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
                else
                {
                    qprint.verbose("adding "+f.getName());
                }
            }
        }
    }
    
    private void deleteNode(File root, Tree tree)
    {
        TreeItem[] treeItemList = tree.getItems();
        for (TreeItem t : treeItemList)
        {
            t.clearAll(true);
            t.dispose();
        }
    }

    public void update()
    {
        qprint.verbose("update tree");
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
        // if treeItems already exist, delete and update
        if ( tree != null )
        {
            deleteNode(root, tree);
            tree.clearAll(true);
            
        }
        createNode(root, tree);
    }
    
    public void setOutput(Text text)
    {
        this.text = text;
        if (this.text == null)
        {
            qprint.verbose("test");
        }
    }
};