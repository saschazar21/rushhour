# Rush Hour - An A* Implementation

## Team

- Fabian Pirklbauer
- Sascha Zarhuber

## Abstract

As an exercise for the course [Artificial Intelligence]() at the [University of Applied Sciences Upper Austria Campus Hagenberg]() we implemented the [A* algorithm]() to solve the rush hour puzzle.

To reduce the number of nodes visited as well as the branching factor of the search, heuristics are used to make an educated guess on whether it is a good idea to visit a given node or not.

## The Game

Rush Hour is a sliding puzzle game. The red car has to reach the exit, while every car on the board may be moved forward or backward as far as it can in each turn.

## The A* Algorithm

The A* algorithm uses an open and closed list to ensure, that nodes are not visited twice. Also the open list is sorted, that nodes with a lower heuristic value are visited first. In addition, when adding a node to the open list with the same depth, only the one with the better heuristic value will be kept.

## Heuristics

Heuristics should make a good guess on how many moves would be required when visiting a given node. It is important to never overestimate the moves required, as the heuristic would not be admissible any more. As a result the solution found by A* may not be optimal.

Every move required counts a value of one.

### Zero Heuristic

The zero heuristic returns the same value for every node, which doesn't improve the algorithm at all.

### Blocking Heuristic

The blocking heuristic counts the number of cars, blocking the car (referred to as "goal car") that has to reach the exit. This value reflects the minimum required moves for a given node's state.

For getting the desired value, we go through each car on the board, ignore any cars that are behind the goal car and those that are aligned in the same way. If there were an equally aligned car blocking the goal car, the puzzle would not be solvable.

Every remaining car intersecting with the goal car counts one. This value plus one for the goal car (to move the goal car to the exit, one move is required) is the result for a state.

### Advanced Heuristic

#### Idea

The blocking heuristic already improves the algorithm by simply counting cars blocking the goal car. It doesn't take cars into account, that are blocking the blocking cars, or cars that are blocking the blocking blocking cars, or...

That's exactly what our heuristic does. It tries to count every blocking car recursively.

#### Challenges

The idea did sound simple at a first glance. It soon turned out, that there were several situations that simply cannot occur or are not relevant for cars blocking the goal car:

- How much space would a car need to move, to clear the way for another car?
- How much space is actually available?
- How many cars are blocking (there could me more than one, in two directions)?
- Cars cannot be considered twice.
- Cars could be aligned in the same way.
- If there are multiple cars aligned in the same way, the space required to move has to be passed on recursively and reduced by the space available between equally aligned cars.
- There are walls.

#### Solution

> Dummy code, explaining the entry point of the heuristic:

```go
getValue(State state) {
  return 0 if state.isGoal()

  visited.clear()
  visited.add(goalCar)

  value = 1;

  for (getInitialBlockingCars() as car) {
    value += getBlockingValue(car, getRequiredSpace(goalCar, car))
  }

  return value;
}
```

> Dummy code, explaining the recursive identification of blocking cars

```go
getBlockingValue(car, requiredSpace) {
  visited.add(car)

  value = 1

  for (allCars as next) {
    continue if next == car
    continue if visited.contains(next)
    continue unless car.intersectsWith(next)

    forwardCosts = 0, backwardCosts = 0

    if (canMoveForward(car, next, requiredSpace.forward) {
      forwardCosts = getBlockingValue(car, getRequiredSpace(car, next))
    } else if (isWallBlockingAhead(car, requiredSpace.forward)) {
      forwardCosts = INFINITY
    }

    if (canMoveBackward(car, next, requiredSpace.backward) {
      backwardCosts = getBlockingValue(car, getRequiredSpace(car, next))
    } else if (isWallBlockingBehind(car, requiredSpace.backward)) {
      backwardCosts = INFINITY
    }

    value += min(forwardCosts, backwardCosts);
  }

  return value;
}
```

### Drawbacks

- The time required to calculate the heuristic value for a state is higher than that of the the blocking heuristic.

- Cars cannot count twice. If a car was already considered as blocking, it must not count as blocking again, as the heuristic may overestimate in that case. If the car was already counted, it is considered as "moved", so it may, or may not be blocking another car.

#### Results

We could accomplish to implement an admissible and optimal heuristic, that visits fewer nodes than the blocking heuristic in most cases, but never visits more nodes.

> The results can also be found in a separate file `RESULTS.txt` as plain text.

| | Zero | | | Blocking | | | Advanced | | |
|-----------|---------|------|--------|---------|------|--------|---------|------|-------
|**name** | **nodes** | **dpth** | **br.fac** | **nodes** | **dpth** | **br.fac** | **nodes** | **dpth** | **br.fac**
|Jam-1      |   11587 |    8 |  3.066 |    8678 |    8 |  2.950 |    4026 |    8 |  2.661
|Jam-2      |   24178 |    8 |  3.380 |    6201 |    8 |  2.820 |    4634 |    8 |  2.712
|Jam-3      |    7814 |   14 |  1.789 |    5007 |   14 |  1.728 |    3931 |   14 |  1.695
|Jam-4      |    3491 |    9 |  2.326 |    1303 |    9 |  2.061 |     378 |    9 |  1.762
|Jam-5      |   24040 |    9 |  2.928 |    8353 |    9 |  2.583 |    1999 |    9 |  2.173
|Jam-6      |   16046 |    9 |  2.792 |    7339 |    9 |  2.543 |    3993 |    9 |  2.364
|Jam-7      |   56222 |   13 |  2.215 |   24529 |   13 |  2.068 |   24529 |   13 |  2.068
|Jam-8      |    6470 |   12 |  1.957 |    5864 |   12 |  1.940 |    3588 |   12 |  1.854
|Jam-9      |    5913 |   12 |  1.941 |    3704 |   12 |  1.860 |    3704 |   12 |  1.860
|Jam-10     |   16197 |   17 |  1.677 |   12787 |   17 |  1.651 |   11865 |   17 |  1.643
|Jam-11     |    6848 |   25 |  1.349 |    5918 |   25 |  1.340 |    4847 |   25 |  1.328
|Jam-12     |   11686 |   17 |  1.642 |    7247 |   17 |  1.591 |    5325 |   17 |  1.560
|Jam-13     |   72952 |   16 |  1.923 |   33949 |   16 |  1.827 |   23274 |   16 |  1.781
|Jam-14     |  116381 |   17 |  1.901 |   44014 |   17 |  1.787 |   44014 |   17 |  1.787
|Jam-15     |    3197 |   23 |  1.338 |    3171 |   23 |  1.337 |    3148 |   23 |  1.337
|Jam-16     |   23072 |   21 |  1.534 |   17662 |   21 |  1.513 |   16119 |   21 |  1.506
|Jam-17     |   19580 |   24 |  1.436 |   18287 |   24 |  1.432 |   16920 |   24 |  1.427
|Jam-18     |   13881 |   25 |  1.392 |   12024 |   25 |  1.383 |    7246 |   25 |  1.352
|Jam-19     |    3585 |   22 |  1.366 |    3533 |   22 |  1.365 |    3533 |   22 |  1.365
|Jam-20     |   13881 |   10 |  2.464 |    4925 |   10 |  2.203 |    3635 |   10 |  2.131
|Jam-21     |    1690 |   21 |  1.334 |    1590 |   21 |  1.329 |    1266 |   21 |  1.313
|Jam-22     |   32371 |   26 |  1.423 |   22704 |   26 |  1.402 |   14757 |   26 |  1.376
|Jam-23     |   19901 |   29 |  1.342 |   11978 |   29 |  1.316 |   10794 |   29 |  1.311
|Jam-24     |   46313 |   25 |  1.468 |   43306 |   25 |  1.464 |   43306 |   25 |  1.464
|Jam-25     |   82852 |   27 |  1.457 |   64644 |   27 |  1.443 |   60849 |   27 |  1.439
|Jam-26     |   40855 |   28 |  1.397 |   34202 |   28 |  1.387 |   33998 |   28 |  1.387
|Jam-27     |   21342 |   28 |  1.362 |   17927 |   28 |  1.352 |   16426 |   28 |  1.348
|Jam-28     |   15591 |   30 |  1.316 |   11162 |   30 |  1.299 |    8022 |   30 |  1.283
|Jam-29     |   38355 |   31 |  1.345 |   38111 |   31 |  1.345 |   38082 |   31 |  1.345
|Jam-30     |    8610 |   32 |  1.264 |    7930 |   32 |  1.260 |    7316 |   32 |  1.257
|Jam-31     |   32852 |   37 |  1.270 |   31236 |   37 |  1.268 |   29594 |   37 |  1.266
|Jam-32     |    3292 |   37 |  1.184 |    2853 |   37 |  1.178 |    2788 |   37 |  1.177
|Jam-33     |   37590 |   40 |  1.250 |   24507 |   40 |  1.235 |   19364 |   40 |  1.227
|Jam-34     |   37544 |   43 |  1.229 |   36076 |   43 |  1.227 |   34943 |   43 |  1.226
|Jam-35     |   34045 |   43 |  1.225 |   33326 |   43 |  1.225 |   32983 |   43 |  1.224
|Jam-36     |   22760 |   44 |  1.207 |   18627 |   44 |  1.201 |   16928 |   44 |  1.198
|Jam-37     |   15270 |   47 |  1.179 |   15231 |   47 |  1.179 |   13888 |   47 |  1.177
|Jam-38     |   28560 |   48 |  1.192 |   24130 |   48 |  1.187 |   23497 |   48 |  1.187
|Jam-39     |   24877 |   50 |  1.179 |   24361 |   50 |  1.179 |   24277 |   50 |  1.178
|Jam-40     |   24467 |   51 |  1.174 |   22288 |   51 |  1.172 |   18337 |   51 |  1.167
