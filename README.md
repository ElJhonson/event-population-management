# 🔬 Sensor Placement Optimizer

![Java](https://img.shields.io/badge/Java-23-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven)
![Algorithm](https://img.shields.io/badge/Algorithm-Genetic-blueviolet)
![Topic](https://img.shields.io/badge/Topic-Petri%20Nets-informational)
![License](https://img.shields.io/badge/License-MIT-yellow)

A genetic algorithm that optimizes sensor placement for industrial process monitoring. Generates and evolves sensor populations using **Petri Net matrices** to minimize total sensor cost while preserving full event detectability across all monitored transitions.

---

## 🧠 Problem Statement

In industrial process monitoring, instrumenting every place and transition of a system is costly and redundant. The goal is to find the **minimum-cost subset of sensor placements** such that every observable event in the process can still be detected — a classic combinatorial optimization problem.

This project models the system as a **Petri Net** (places × transitions incidence matrix), represents candidate sensor configurations as binary vectors, and uses a **genetic algorithm** to evolve toward the optimal cost solution.

---

## ⚙️ How It Works

### 1. Petri Net Representation
The monitored system is encoded as an incidence matrix **C** of size `places × transitions`:
- Each row represents a **place** in the Petri Net
- Each column represents a **transition**
- Values `{-1, 0, 1}` encode consumption, neutrality, or production of tokens

The built-in example matrix is **34 places × 23 transitions**, loaded from `matriz.csv`.

### 2. Cost Model
Each place and transition has an associated placement cost:
- **Places:** costs between `30f – 50f` per sensor
- **Transitions:** fixed cost of `300f` per sensor

The fitness function minimizes total cost while ensuring all events remain detectable.

### 3. Genetic Algorithm

The optimizer runs **10 independent runs**, each evolving **100 individuals over 1000 generations**:

| Operator | Class | Description |
|---|---|---|
| **Initialization** | `GenesGenerator` | Generates random binary sensor configurations |
| **Fitness** | `FitnessCalculator` + `EventDetectabilityChecker` | Evaluates cost and full event observability |
| **Selection** | `RouletteSelection` | Roulette wheel (fitness-proportionate) selection |
| **Crossover** | `SensorCrossover` | Combines parent place/transition vectors |
| **Mutation** | `SensorMutation` | Randomly flips sensor bits to explore search space |
| **Evolution** | `EvolutionManager` | Orchestrates the full generational loop |

### 4. Output
After all runs, the algorithm prints the **global best** sensor configuration:
- Fitness score (total cost)
- Number of instrumented places and transitions
- Transition placement vector (binary)
- Total sensors used

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 23 |
| Build | Maven |
| Architecture | OOP — layered packages (evolution, fitness, model, util) |
| Paradigm | Genetic Algorithm + Petri Net theory |
| Input | CSV matrix (`matriz.csv`) with fallback to hardcoded example |

---

## 📁 Project Structure

```
sensor-placement-optimizer/
├── src/main/java/com/algorithm/
│   ├── evolution/
│   │   ├── EvolutionManager        # Orchestrates generational evolution loop
│   │   ├── GenesGenerator          # Random population initialization
│   │   ├── RouletteSelection       # Fitness-proportionate parent selection
│   │   ├── SensorCrossover         # Crossover operator for sensor configs
│   │   └── SensorMutation          # Mutation operator (bit flip)
│   ├── fitness/
│   │   ├── EventDetectabilityChecker  # Validates full event observability
│   │   ├── FitnessCalculator          # Computes total placement cost
│   │   └── SensorManagerCost          # Holds place/transition cost arrays
│   ├── model/
│   │   ├── PlaceTransitionGenerator   # Generates place/transition configs
│   │   └── SensorConfig               # Represents a single sensor placement candidate
│   ├── util/
│   │   ├── CsvParser               # Loads incidence matrix from CSV file
│   │   ├── OneZeroToPositions      # Converts binary vector to active positions
│   │   └── PopulationUtils         # Population-level helper methods
│   └── Main.java                   # Entry point — runs 10 evolution cycles
├── matriz.csv                      # Petri Net incidence matrix (34 places × 23 transitions)
├── pom.xml
└── .gitignore
```

---

## 🏃 Running Locally

### Prerequisites

- Java 23+
- Maven 3.8+

### Steps

```bash
# Clone the repository
git clone https://github.com/LuisAlvarezMtz/sensor-placement-optimizer.git
cd sensor-placement-optimizer

# Build the project
mvn clean compile

# Run
mvn exec:java -Dexec.mainClass="com.algorithm.Main"
```

### CSV Matrix Input

By default, `Main.java` loads the matrix from a hardcoded local path. To use your own matrix, update the path in `Main.java`:

```java
String csvPath = "path/to/your/matriz.csv";
```

If the file is not found, the algorithm falls back to the built-in 5×4 example matrix.

---

## 📊 Built-in Matrix

The included `matriz.csv` encodes a real industrial process as a **34 × 23 Petri Net incidence matrix** (34 places, 23 transitions). Values are `{-1, 0, 1}`:

```
Place 1:  -1  0  0  0  0  0  0  0  0  0  0  0  0  1  0  0  0  0  0  0  0  0  0
Place 2:   1 -1 -1  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0
Place 3:   0  1  0 -1  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0
...        (34 rows × 23 columns)
```

---

## 📈 Sample Output

```
╔══════════════════════════════════════════════════════════════╗
║        Sensor Placement Optimizer — Genetic Algorithm        ║
║     Petri Net-based optimization for event detectability     ║
╚══════════════════════════════════════════════════════════════╝
Matrix size: 34 places x 23 transitions

┌─────────────────────────────────────────────────────────────┐
│                          Run 1 of 10                        │
└─────────────────────────────────────────────────────────────┘
  Generation   0 → Fitness: 2850.0
  Generation  10 → Fitness: 2400.0
  ...
  Generation 990 → Fitness: 1200.0

  ── Best result this run ──────────────────────────────────
  Fitness:             1200.0
  Sensor places:       [2, 5, 9]
  Sensor transitions:  [1, 4, 7, 12]
  Transition vector:   [0, 1, 0, 0, 1, 0, 0, 1, ...]

╔══════════════════════════════════════════════════════════════╗
║                     Global Best Result                       ║
╚══════════════════════════════════════════════════════════════╝
  Fitness:             1150.0
  Sensor places:       [2, 9]
  Sensor transitions:  [1, 4, 7, 12]
  Total sensors used:  6
```

---

## 📚 Concepts

- **Petri Nets** — mathematical modeling language for distributed systems and industrial processes
- **Incidence Matrix** — encodes place-transition relationships as `{-1, 0, 1}` values
- **Genetic Algorithms** — metaheuristic inspired by natural selection for combinatorial optimization
- **Event Detectability** — a system is fully observable if every transition firing is detected by at least one sensor
- **Roulette Selection** — selection probability proportional to fitness, favoring lower-cost configurations

---

## 👤 Author

**Luis Angel Alvarez Martinez**
📧 [luisangel.alvarezmtz@gmail.com](mailto:luisangel.alvarez@mtzgmail.com)

---

## 📄 License

This project is licensed under the MIT License.
