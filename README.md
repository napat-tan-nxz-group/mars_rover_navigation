# Mars Rover Navigation

## Description

This system is created to control a Mars Rover in 2D navigation. The rover should be able to move forward, turn left and
right, and detect when encountering an obstacle or ran outside the area.

Programming Language used: JAVA

## Installation

### Prerequisite

- [Homebrew](https://brew.sh/)
- [SpringBoot 3.4.3](https://start.spring.io/)
- [Java Amazon Corretto 21](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/what-is-corretto-21.html)
- [Apache Maven 3.9.4](https://formulae.brew.sh/formula/maven)
- [Jetbrains IntelliJ IDEA](https://www.jetbrains.com/help/idea/installation-guide.html)
- [Google Java Format](https://plugins.jetbrains.com/plugin/8527-google-java-format)

### Dependency Installation

All dependencies are listed within `pom.xml` file. Run the command below to install them.

```shell
$ mvn clean install
```

## Instruction

### To Run Application

```shell
$ mvn exec:java -Dexec.mainClass="com.navigation.MarsRoverNavigationApplication" 
```

### URL

```shell
$ curl --location --request GET 'http://localhost:8080/rover/navigate' \
--header 'Content-Type: application/json' \
--data '{
    "grid_size": 5,
    "obstacles": [
        "(1,2)",
        "(3,3)"
    ],
    "commands": "RMMMLMMM"
}'
```

### Request Body

```json
{
  "grid_size": 5,
  "obstacles": [
    "(1,2)",
    "(3,3)"
  ],
  "commands": "RMMMLMMM"
}
```

`grid_size` refers to integer that represent grid's horizontal and vertical sizes.\
`obstacles` refers to a list of obstacle position (horizontal, vertical).\
`commands` refers to a chain of commands that are processed character by character.

### Commands

- `M`: Move command makes the rover mover forward by 1 (default rover speed).
- `L`: Turn rover to its left direction.
- `R`: Turn rover to its right direction.

These commands are chained together to be processed one by one command. For example, command `MMRMMLM` can be
interpreted as `Move` twice then `Turn Right` then `Move` twice then `Turn Left` and then `Move` once.

### How to read position

Position in this system is read in `(x,y)` format where `x` refers horizontal position and `y` refers to vertical
position.

## Testing

### Execute Unit Test

```shell
$ mvn clean test
```

### Expected Outputs

#### Successful Case - 200 Status

- Request Body

```json
{
  "grid_size": 5,
  "obstacles": [
    "(1,2)",
    "(3,3)"
  ],
  "commands": "LMLMLMLMM"
}
```

- Output

```json
{
  "final_position": "[0, 1]",
  "final_direction": "N",
  "final_status": "Success"
}
```

#### Out of Grid Case - 200 Status

- Request Body

```json
{
  "grid_size": 5,
  "obstacles": [
    "(1,2)",
    "(3,3)"
  ],
  "commands": "MMLMMMMM"
}
```

- Output

```json
{
  "final_position": "[-4, 2]",
  "final_direction": "W",
  "final_status": "Out of bounds"
}
```

#### Obstacle Encountered Case - 200 Status

- Request Body

```json
{
  "grid_size": 5,
  "obstacles": [
    "(1,2)",
    "(3,3)"
  ],
  "commands": "MMRMM"
}
```

- Output

```json
{
  "final_position": "[1, 2]",
  "final_direction": "E",
  "final_status": "Obstacle encountered"
}
```

#### Invalid Command Case - 400 Status

- Request Body

```json
{
  "grid_size": 5,
  "obstacles": [
    "(1,2)",
    "(3,3)"
  ],
  "commands": "MMMRMMRMMEMM"
}
```

- Output

```json
{
  "final_position": "[2, 1]",
  "final_direction": "S",
  "final_status": "Invalid command"
}
```

## Notes

### Future plan

- Add renderer to show the grid, the rover, and the obstacles.
- Make rover able to turn omnidirectional.