## Qedit information
Qedit is a file manager to edit/save/open files.  
It can also connect to other machines by SSH, telnet or serial.  
All the API has been made from scratch.

## Motivation
Make a reference tool to show Java skill. If people would contribute, please surround code part with Id.  
Try to find solutions to lack of notepad which I use to create notes in my professional life.  

## Installation
clone project into your workspace: > $ git clone https://github.com/qparrod/qedit  
Add usefull scripts into your environment. For example in bash, do > $ export PATH=/path/to/workspace/bin:$PATH
there is an existing executable jar file to launch program. To launch it, do > $ java -jar qedit.jar /path/to/workspace/store/  


### Developer Area

#### dev environment
* eclipse or manually
* SWT libraries  
To build and run project, use directly eclipse or use commands: 
> $ /path/to/workspace/bin/build  
> $ /path/to/workspace/bin/run
To create an executable jar file when you modified code, use > $ /path/to/workspace/bin/jar

#### Under Windows
Adding org.eclipse.swt to eclipse project: right clic on qedit project, then import. General/Existing project into workspace.  
Add the zip file in windows folder (swt-4.5-win32...), depending on your OS architecture.

## Contributors
* Quentin Parrod : author

## License
GPL
