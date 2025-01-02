# Exploring multiple processes and IPC

- The project consist of 4 different main componenets.
    - CPU
    - Memory
    - Timer
    - Interrupt process
 
  There is also an instruction set with the following key:
    1 = Load value
    2 = Load addr
    3 = LoadInd addr   
    4 = LoadIdxX addr
    5 = LoadIdxY addr
    6 = LoadSpX
    7 = Store addr
    8 = Get 
    9 = Put port
   10 = AddX
   11 = AddY
   12 = SubX
   13 = SubY
   14 = CopyToX
   15 = CopyFromX
   16 = CopyToY
   17 = CopyFromY
   18 = CopyToSp
   19 = CopyFromSp   
   20 = Jump addr
   21 = JumpIfEqual addr
   22 = JumpIfNotEqual addr
   23 = Call addr
   24 = Ret 
   25 = IncX 
   26 = DecX 
   27 = Push
   28 = Pop
   29 = Int 
   30 = IRet
   50 = End

# examples of input files
This program gets 3 random integers and sums them, then prints the result. 
Note that each line only has one number.

       8   // Get 
      14  // CopyToX
       8   // Get
      16  // CopyToY
       8   // Get
      10   // AddX
      11   // AddY
       9   // Put 1
       1
      50  // End     

This program prints HI followed by a newline to the screen.  To demonstrate a procedure call, the newline is printed by calling a procedure.

       1   // Load 72=H
      72
       9   // Put 2
       2
       1   // Load 73=I
      73
       9   // Put 2
       2
      23  // Call 11
      11
      50  // End 
       1   // Load 10=newline
      10 
       9   // Put 2
       2
      24  // Return
