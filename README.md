Score Four Minimax AI – Extracted Code Samples

This repository holds some code that I implemented myself for a team project. We built a 3D Score Four game, which is like Connect Four, all in Java. The complete project stays private for now. So this repo pulls out just my AI engine, along with the game state evaluator and a testing setup. I cleaned them up specifically for my portfolio.

The files here show off a few key things in game development. They cover the design of the game AI itself. You see minimax algorithms with alpha beta pruning to make decisions faster. There are heuristic functions that evaluate positions on the board. The code models turn based game states pretty carefully. It uses solid object oriented principles in Java. Plus, there are utilities for testing everything from the command line.

Included Files
AIPlayer.java

This handles the computer controlled player in the game. It features minimax search along with alpha beta pruning for efficiency. You can adjust difficulty by changing the search depth. Moves get generated and evaluated based on the GameState class. Behavior can be deterministic or a bit random, depending on your settings. In a way, this class acts as the brain behind the AI decisions.

GameState.java

This captures a snapshot of the Score Four board and the active player at any moment. Core tasks include tracking whose turn it is right now. It clones boards and simulates moves without messing up the original state. Legal moves get generated from the current setup. The class also has a heuristic evaluation function built in. That function rewards lines with two or three pieces plus empty spots for potential wins. It penalizes threats from the opponent side. Terminal states, like wins, get detected right away. The AI relies on this class a lot when looking ahead at possible board positions.

Test.java

This sets up a text based harness for testing the Score Four components interactively. You can add or remove beads using simple typed commands. The board state displays clearly whenever you need it. It runs calculations for AI moves on demand. From the full project, you could even launch a GUI mode through this. Overall, it helps with debugging and checking how the system behaves. This shows the AI integrating smoothly with other parts like the board and interface.

Techniques and Concepts Demonstrated

The techniques and concepts shown here go deeper into AI for games. The minimax search algorithm forms the foundation for smart play. Alpha beta pruning optimizes performance by cutting unnecessary branches. Heuristic scoring evaluates game lines based on strategic value. Cloning and simulating states keeps things safe during planning. Java object oriented principles structure the code cleanly. Simple command line input gets parsed for user interactions. Multiple modules come together, from board logic to AI and even GUI elements.

About the Original Project

As for the original project, these files came from a bigger group effort in a university course. The full version had a 3D game board implemented fully. A GUI used Swing for the visual side of things. Game logic covered move validation and rendering details. It supported multiplayer sessions along with AI opponents. Those extra components sit in a private repository still. This current repo focuses only on the work I authored myself. I extracted it for demonstration during interviews or reviews.

Contact

If you want a walkthrough on how the AI actually works, or to check out more parts of my implementation, just reach out.

GitHub: https://github.com/Adesoye04

LinkedIn: https://www.linkedin.com/in/adesoye-oyeyiola/

Email: adesoyeoyeyiola44@gmail.com
