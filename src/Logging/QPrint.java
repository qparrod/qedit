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

package Logging;

import java.lang.String;

import org.eclipse.swt.widgets.Text;

public final class QPrint
{
    private int     level;
    private String  moduleName;
    private static Text    text;
    private static boolean enableDbgTxt = false;
    
    public QPrint(String module)
    {
        setModuleName(module);
        setLevel(QLevel.VERBOSE); // default level
    }
    
    public QPrint (String module, int level)
    {
        setModuleName(module);
        setLevel(level);
    }
    
    private void qprint(String trace, int level, String arg)
    {
        if ( this.level >= level )
        {
            System.out.println(trace + moduleName + ": " + arg);
            
            if (enableDbgTxt)
            {
                if (text != null )
                {
                    text.append("\n");
                    text.append(trace + moduleName + ": " + arg);
                }
            }
        }
    }
    
    public void error(String arg)
    {
        qprint(QTrace.ERROR, QLevel.ERROR, arg);
    }
    
    public void warning(String arg)
    {
        qprint(QTrace.WARNING, QLevel.WARNING, arg);
    }
    
    public void verbose(String arg)
    {
        qprint(QTrace.VERBOSE, QLevel.VERBOSE, arg);
    }
    
    public void setLevel (int level)
    {
        this.level = level;
    }
    
    public void setModuleName(String name)
    {
        this.moduleName = name;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public String getModuleName()
    {
        return moduleName;
    }
    
    public void configureText(Text txt)
    {
        qprint(QTrace.VERBOSE, QLevel.VERBOSE, "enable DBG text");
        enableDbgTxt = true;
        text = txt;
    }
}

class QLevel
{
    public static final int NO_LOGGING = 0;
    public static final int ERROR      = 1;
    public static final int WARNING    = 2;
    public static final int VERBOSE    = 3;
}

class QTrace
{
    public static final String ERROR   = "[ERROR] "  ;
    public static final String WARNING = "[WARNING] ";
    public static final String VERBOSE = "[DBG] "    ;
}
