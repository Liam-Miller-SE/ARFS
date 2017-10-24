Team ARFS : Joshua Eng, Melissa Gould, Tyler Davis, Liam Miller

Welcome to our First release of our AFRS System!
Here are all of the known defects in the system(Same as "Status of the Implementation" section of the Design Document)

Additionally, ignore the textFiles Folder with the blank files
Please see the "compile" and "run" scripts to begin using our system

Known in the System:

For our AFRS implementation, all code compiles. For the different input requests, airport and help work
as expected to their output responses. 
For the input requests, it is assumed that the user knows the exact syntax (no spaces, needed parameters, separated by commas).
For example an invalid command : info,abc; returns an unknown command error. There are some help functions to help the user 
with the correct inputs.
The system will crash if an unknown airport code( that is all caps and length of 3) is inputted into the system.
Does not return an empty string if result of query equals none. Will crash if request has an empty field (,,) in the input.
info does not return anything for changing the number of connections other than the default (2).
Does not appropriately sort based on sort order field. 
For the reserve request, the defect is that it does not create a reservation or check correctly if the id parameter is valid.
Will always print line "Retrieving some data" if the correct number of parameters are sent.
Will print an error: "error, unknown Something" if id parameter is wrong.
Since the system cannot make a reservation yet, the delete and retrieve functions are also not covered. 
They will not break the system if the user tries to call these commands, but will give some unexpected outputs. 
Delete will print of the number of arguments passed in.