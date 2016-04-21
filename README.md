# Virtual-Memory

This purpose of this assignment is to simulate a virtual memory system and analyze different page replacement algorithms.
(this project must be done in pairs unless given permission otherwise)

Logistics
Since the goal is only to understand the page replacement algorithms, and be able to measure how efficient they are, you will not create actual processes. Instead you will just simulate a virtual memory manager and display its behaviour. There is no starter code, and all the necessary details are given in the following sections

System Details
Each process has a virtual address space of 64KB
Each page is of size 32-512B (must be a power of two)
The system has 2 KB of physical memory

Input
Your program will read the incoming processes from a text file. Each line in the text file will represent a process and will have the following information:
pid, address accessed, read/write
For example your text file might look like the following:
1, 16412, R
2, 247, R
1, 4569, W
3, 15856, R

Page Replacement Algorithms
Your code should be able to simulate the following page replacement algorithms:
Optimal page replacement algorithm (to get the best case scenario)
FIFO
LRU
Second chance algorithm
Enhanced second chance
Your own hybrid version

Design
Your design should be as follows:
Write your manager in java
Your main function should accept three strings. One which states which page replacement algorithm to use (e.g. fifo), one which states the size of a page (between the allowed range) and the last which states the input file name.
Read in the text file, and process each line as it comes in (note that for the optimal, you will need to read in the entire file before processing).

After processing each line print out the following information:
Page #
Process #
Frame # loaded
If there was a page fault
If a frame needed to be written
Virtual to physical address translation
Note that I do not care about efficiency for this program as long as the output is correct.
Analysis

For each algorithm you are required to provide the following information:
Number of page faults
Number of disk accesses (Note that for replacing dirty pages an additional disk access is needed. Also ignore actually accessing the page table since we are not focusing on that here.)

Submission
Besides the code to your manager please submit the following Report:
A short introduction to the problem you are trying to solve.
A description of the how each of the page replacement  methods work, including your hybrid one.
A description of your code design and your choice of data structures.
Correctness Results - In this section you should present the results for each replacement algorithm in the following way:
Use the setting that has the minimum amount of physical pages (so replacements will happen more often)
Show a small input file (around 15 memory accesses).

Manually draw a diagram (like in the HW solutions) which show the page replacements.
Manually calculate the number of page faults and disk accesses.
Show the output of your algorithm to ensure correctness.

Analysis Results - Using a larger input file (), show a table which compares the results of all different page replacement algorithms.  When generating larger files try to be thoughful of how you generate them (perhaps use the ideas of working sets).
Conclusion - Which algorithms worked the best and in what cases.
An appendix describing which part of the project each team member was in charge of.
References - If necessary.

Sample Output (512B page size):
Input file:
101, 512, R
102, 512, R
103, 512, R
104, 512, R
101, 513, W
102, 513, R
105, 513, R
101, 514, R
102, 514, R
103, 514, R
104, 514, R
105, 515, R
FIFO:
loaded page #1 of processes #101 to frame #0 with no replacement.
        Virtual Address: 512  ->  Physical Address: 0
loaded page #1 of processes #102 to frame #1 with no replacement.
        Virtual Address: 512  ->  Physical Address: 512
loaded page #1 of processes #103 to frame #2 with no replacement.
        Virtual Address: 512  ->  Physical Address: 1024
loaded page #1 of processes #104 to frame #3 with no replacement.
        Virtual Address: 512  ->  Physical Address: 1536
no page fault. accessed frame #0
        Virtual Address: 513  ->  Physical Address: 1
no page fault. accessed frame #1
        Virtual Address: 513  ->  Physical Address: 513
loaded page #1 of processes #105 to frame #0 with replacement
        Needed to write frame #0 to memory
        Virtual Address: 513  ->  Physical Address: 1
loaded page #1 of processes #101 to frame #1 with replacement
        Virtual Address: 514  ->  Physical Address: 514
loaded page #1 of processes #102 to frame #2 with replacement
        Virtual Address: 514  ->  Physical Address: 1026
loaded page #1 of processes #103 to frame #3 with replacement
        Virtual Address: 514  ->  Physical Address: 1538
loaded page #1 of processes #104 to frame #0 with replacement
        Virtual Address: 514  ->  Physical Address: 2
loaded page #1 of processes #105 to frame #1 with replacement
        Virtual Address: 515  ->  Physical Address: 515
Number of page faults: 10. Number of memory accesses: 11
LRU:
loaded page #1 of processes #101 to frame #0 with no replacement.
        Virtual Address: 512  ->  Physical Address: 0
loaded page #1 of processes #102 to frame #1 with no replacement.
        Virtual Address: 512  ->  Physical Address: 512
loaded page #1 of processes #103 to frame #2 with no replacement.
        Virtual Address: 512  ->  Physical Address: 1024
loaded page #1 of processes #104 to frame #3 with no replacement.
        Virtual Address: 512  ->  Physical Address: 1536
no page fault. accessed frame #0
        Virtual Address: 513  ->  Physical Address: 1
no page fault. accessed frame #1
        Virtual Address: 513  ->  Physical Address: 513
loaded page #1 of processes #105 to frame #2 with replacement
        Virtual Address: 513  ->  Physical Address: 1025
no page fault. accessed frame #0
        Virtual Address: 514  ->  Physical Address: 2
no page fault. accessed frame #1
        Virtual Address: 514  ->  Physical Address: 514
loaded page #1 of processes #103 to frame #3 with replacement
        Virtual Address: 514  ->  Physical Address: 1538
loaded page #1 of processes #104 to frame #2 with replacement
        Virtual Address: 514  ->  Physical Address: 1026
loaded page #1 of processes #105 to frame #0 with replacement
        Needed to write frame #0 to memory
        Virtual Address: 515  ->  Physical Address: 3
Number of page faults: 8. Number of memory accesses: 9
