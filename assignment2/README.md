# Bombastic – Assignment 2: the game

A two-player, Bomberman-inspired game in JavaFX, built without FXML or Scene Builder. Collision detection runs concurrently on a thread pool.

---

## How to run

1. Open the `assignment2` folder itself in VS Code (File → Open Folder). The `.vscode` settings only apply when this folder is the one opened.
2. JavaFX is configured in `.vscode/settings.json` and `.vscode/launch.json`. If your JavaFX SDK is somewhere else, change the path `/Users/hansmar/javafx/javafx-sdk-25.0.5/lib` in both files.
3. Run **Run and Debug → Launch Bombastic** (F5). The main class is `Main`.

## How to play

| | Red player | Blue player |
|---|---|---|
| Move | W A S D | I J K L |
| Place bomb | Space | Enter |

Each player starts with 3 lives. Bombs explode after 2 seconds in a cross shape. Explosions destroy crates and set off other bombs; walls stop them. A player hit by an explosion loses one life. Destroyed crates can reveal power-ups:

| Power-up | Effect |
|---|---|
| Capacity | One more bomb at a time |
| Range | Explosions reach one tile further |
| Speed | Faster movement |
| Life | One extra life |

The last player standing wins. If both players lose their last life at the same time, it's a draw. **New round** starts a fresh game without restarting the app.

---

## Project structure

```
src/
├── Main.java                      Starts JavaFX; shuts the thread pool down on exit
├── css/default.css                All styling (style classes and theme colours)
├── images/                        Backgrounds and game assets
└── bombastic/
    ├── Model, View, Controller    Top-level MVC: shared state and screen switching
    ├── menu/                      Main menu, settings and chat (Assignment 1)
    └── game/
        ├── GameModel.java         The game state: all entities and the game rules
        ├── GameView.java          Canvas, lives display and result overlay
        ├── GameController.java   Game loop, keyboard input and thread pool
        ├── GameSettings.java     Constants: tile size, arena size, number of threads
        ├── CollisionDetection.java  AABB overlap test
        ├── arena/Arena.java       Builds the arena from a char[][] map
        └── entities/              The entity hierarchy (below)
```

### The entity hierarchy

```
Entity (abstract)                    position, size, image; update(), render(), interact(), isOccupying()
├── Floor                            can be walked on
├── Wall                             blocks players and explosions; indestructible
├── Crate                            blocks players; destroyed by explosions
├── Bomb                             explodes after a fuse; blocks players once they have walked off it
├── Explosion                        lasts 500 ms; damages players, destroys crates, sets off bombs
├── Player                           moves with delta time; places bombs; has lives
└── PowerUp (abstract)               picked up by players; apply(Player) is abstract
    ├── CapacityPowerUp
    ├── RangePowerUp
    ├── SpeedPowerUp
    └── LifePowerUp
```

---

## Concurrency

### Design

Collision detection runs on a fixed thread pool (`ExecutorService`) using `invokeAll` with `Callable` tasks. The pair checks are split **interleaved** between the tasks (task 0 takes entities 0, n, 2n…; task 1 takes 1, n+1…), so each thread gets an even share of the triangular pair loop. Each task returns its own list of collisions, which are combined with `Future.get()`.

This is thread-safe **without locks**:
- **No shared writes:** each task writes only to its own list.
- **Nothing changes during detection:** the JavaFX thread waits in `invokeAll` until all tasks are done, so no entity moves, appears or disappears while the tasks read.
- **Visibility:** submitting tasks to an executor and reading results with `Future.get()` guarantee that the worker threads see the current positions, and that the JavaFX thread sees their results.

Interactions are processed afterwards, sequentially, on the JavaFX application thread, which is also the only thread that touches the scene graph.

The thread pool is shut down in `Application.stop()`. Without that, the pool's threads would keep the program running after the window closes.

### Measurements

Collision detection time per frame on my Mac, median over about 800 frames (the first 20% of each run skipped as warm-up), with about 320 entities and about 52,000 pair checks per frame:

| Version | Median | 90% of frames under | Slowest frame |
|---|---|---|---|
| Sequential | 530 µs | 759 µs | 1.1 ms |
| Concurrent, 1 thread | 570 µs | 969 µs | 2.4 ms |
| Concurrent, 2 threads | **344 µs** | 538 µs | 2.7 ms |
| Concurrent, 4 threads | 424 µs | 741 µs | 9.6 ms |
| Concurrent, 8 threads | 378 µs | 750 µs | 9.2 ms |

### Reflection

- **The executor has a cost.** With one thread, the concurrent version does exactly the same work as the sequential one, but is about 40 µs (8%) slower. That's the price of creating a task, handing it to a pool thread and collecting the result.
- **Two threads were the best choice,** about 35% faster than sequential. Splitting the work over two cores more than pays for the overhead.
- **More threads didn't help.** With 4 and 8 threads, each task has less work while the overhead stays, and the threads compete for CPU time with the JavaFX thread and the rest of the system. Frame times also became less stable, with occasional frames around 9 ms instead of 1–3 ms.
- **All versions fit easily in a frame:** even the slowest median is about 3% of the 16.7 ms available at 60 FPS. For this game size, concurrency is correct and safe, but only modestly beneficial. It would pay off more with many more entities or more expensive checks.

The number of threads is set in `GameSettings.COLLISION_THREADS`. The sequential version is still in the code (`GameModel.findCollisions()`) for comparison, with commented-out timing lines in `GameController`.

---