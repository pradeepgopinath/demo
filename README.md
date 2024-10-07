# demo
### Assumptions made
Given port&protocol combination exists in the lookup table.
If a port&protocol combination does not exist then the flowlog data will be tagged as "untagged"

### Compile/Run program
Compile the Java code by typing “javac [filename].java” in the command prompt/terminal.
Input files ( flow log file and  lookup file ) need to be present in the same location as the program file.
No additional dependencies need to be installed to compile/execute the program.

### Output files
Count of matches for each tag is stored in an output file - tag_counts.csv .
Count of matches for each port/protocol combination is stored in an output file - port_protocol_counts.csv 
