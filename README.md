# Unravel Backend Challenge

## Features

**Session Manager**  
Thread-safe, scalable session management with login, logout, and retrieval.

**Memory Manager**  
Simulates memory usage per session using `WeakReference`, with explicit allocation and release.

**Producer-Consumer with Priority**  
Uses `PriorityBlockingQueue` to process tasks with priority-based consumption.

**Deadlock Simulation**  
Illustrates deadlock prevention using consistent locking order.

**Database Connection Pooling with HikariCP Monitoring**  
Monitors connection pool actively using **in-memory H2 DB** for frictionless testing.

---

## Tech Stack

- Java 17
- Spring Boot
- Maven
- HikariCP
- H2 Database (for zero-setup testing)

---

## Project Structure

src/main/java/com/unravel/demo
│
├── controller // REST endpoints for testing
├── db // DatabaseManager with HikariCP
├── memory // MemoryManager for allocation/release
├── concurrency // PriorityLogProcessor, Task, Producer, Consumer
├── deadlock // DeadlockSimulator
└── DemoApplication // Main Spring Boot entry point

## Setup Instructions
--Clone the repository:
#```bash
git clone <your-repo-url>
cd <repo-folder>
-- Build
mvn clean install
-- Run
mvn spring-boot:run
-- App runs on
http://localhost:8080

## API Endpoints for Testing
**Session Manager**
Login :
curl -X POST "http://localhost:8080/session/login?userId=user1"

Get Session :
curl "http://localhost:8080/session/user1"

Logout :
curl -X POST "http://localhost:8080/session/logout?userId=user1"


**Memory Manager**
Allocate 10MB :
curl -X POST "http://localhost:8080/session/memory/allocate?sessionId=s1"

Release Memory :
curl -X POST "http://localhost:8080/session/memory/release?sessionId=s1"

**Producer-Consumer w/ Priority**
Add Task :
curl -X POST "http://localhost:8080/session/producer/add?name=task1&priority=1"

Consumer Task :
curl "http://localhost:8080/session/consumer/consume"

**Deadlock Testing**
Trigger deadlock prevention :
curl "http://localhost:8080/session/deadlock/test"

Check console logs:
Method1: acquired lock1 and lock2
Method2: acquired lock1 and lock2

*HickariCP Monitoring*
Test connection acquisition:
curl "http://localhost:8080/session/dbtest"

Check console logs:
[HikariCP Monitor] Active: X, Idle: Y, Total: Z


## Includes automated JUnit tests for all implemented modules.
