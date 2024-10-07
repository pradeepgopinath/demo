# demo
### Assumptions made
Given port&protocol combination exists in the lookup table.
If a port&protocol combination does not exist then the flowlog data will be tagged as "untagged"
Tested the program with input file ( containing flow log data ) of few MB's as per problem statement. 

### Compile/Run program
Run the Java code by typing "java FlowLogProgram.java" in the command prompt/terminal. Don't have to compile beforehand. Note: Use Java 11 SDK or above.
Input files ( flow log file and  lookup file ) need to be present in the same location as the program file.
No additional dependencies need to be installed to compile/execute the program.

### Output files
Count of matches for each tag is stored in an output file - tag_counts.csv .
Count of matches for each port/protocol combination is stored in an output file - port_protocol_counts.csv 
