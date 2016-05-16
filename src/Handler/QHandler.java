package Handler;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import Logging.QPrint;

public class QHandler
{
    // TODO: handle windows listener or adapters
    QPrint qprint = new QPrint("QHandler");
    private Tree tree;
    
    public QHandler(Tree tree)
    {
        qprint.verbose("create");
        this.tree = tree;
    }
    
    
    public MouseAdapter listener()
    {
        return new MouseAdapter() {
            public void mouseDown(MouseEvent e)
            {
                int buttonType = e.button;
                switch(buttonType)
                {
                    case 1: // left click
                    {
                        TreeItem dest = tree.getItem(new Point(e.x, e.y));
                        if (null == dest) { return; }
                        qprint.verbose(dest.getText());
                        //Path path = Paths.get(findAbsolutePath(dest, dest.getText()));
                        //qprint.verbose("left button clicked, selected path="+ path.toString());
                        //fileSelected = path;
                        break;
                    }
                    case 3: // right click
                    {
                        qprint.verbose("right button clicked");
                        break;
                    }
                }
            }
            public void mouseDoubleClick(MouseEvent event)
            {
                qprint.verbose("double click");
            }
            /*
            public String findAbsolutePath(TreeItem tree, String path)
            {
                if ( null == tree.getParentItem() ) { return (ROOT + path); }
                String parent = tree.getParentItem().getText();
                return findAbsolutePath(tree.getParentItem(), parent + "/"+ path);
            }*/
        };
    }
}
