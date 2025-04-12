# 🛒 Shopping Expense Tracker (Backend)

This is the backend service for the **Shopping Expense Tracker** — a simple Spring Boot application designed to help users create shopping lists, track purchased items, and calculate expenses.

**Purpose**: Creating an up-to date Spring boot template (2025) with Latest version of the (Java 24). With standard utiltiy libraries, pre-defined components to be used in other project. 

---

## 📦 Tech Stack

- **Java 21+**
- **Spring Boot 3.4.4**
- **Gradle (Groovy DSL)**
- **PostgreSQL**
- **Docker** (for containerized deployment)
- **JWT** (for authentication, planned)

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 21+ installed
- Gradle installed (optional, wrapper included)
- PostgreSQL running (locally or via Docker)
- Git

### 🔧 Clone the Repo

```bash
git clone https://github.com/AnawaratneMANA/shopping-tracker-backend.git
cd shopping-tracker-backend
```

### ⚙️ Configuration

Update your `application.yml` or `application.properties` with your PostgreSQL credentials.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/shopping_db
    username: youruser
    password: yourpassword
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```
> ⚠️ Will be introduced "Flyway" to version the SQL fiels with ddl-auto "disabled"
---

## 🐘 Run PostgreSQL with Docker (Optional)

```yaml
# docker-compose.yml

version: '3'
services:
  postgres:
    image: postgres:15
    container_name: shopping-db
    environment:
      POSTGRES_DB: shopping_db
      POSTGRES_USER: youruser
      POSTGRES_PASSWORD: yourpassword
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

```bash
docker compose up -d
```

---

## ▶️ Run the Application

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/shopping-*.jar
```

---

## 📂 Project Structure

```
src
├── main
│   ├── java
│   │   └── com.nir.shopping
│   │       ├── controller
│   │       ├── service
│   │       ├── model
│   │       ├── repository
│   │       └── config
│   └── resources
│       └── application.yml
└── test
```

---

## 🛠️ Development Workflow

### 🪴 Branching Strategy

We follow a simplified Git Flow:

- `master`: production-ready code only (protected)
- `dev`: active development branch
- `qa`: qa verifications, functional testing
- `staging`: pre-production-ready code only
- `feature/*`: feature-specific work (e.g., `feature/authentication`)
- `bugfix/*`: patches and hotfixes
- `release/*`: prep for release (optional, use when needed)

#### Example:

```bash
git checkout -b feature/shopping-list
```

---

### 📝 Commit Message Convention

We follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/):

```bash
<type>(optional-scope): short description
```

#### Common Types

| Type       | Description                             |
|------------|-----------------------------------------|
| `feat`     | A new feature                           |
| `fix`      | A bug fix                               |
| `docs`     | Documentation changes                   |
| `style`    | Formatting, missing semicolons, etc.    |
| `refactor` | Code refactoring (no logic change)      |
| `test`     | Adding or fixing tests                  |
| `chore`    | Maintenance tasks like dependency bumps |
| `ci`       | CI/CD configuration                     |

#### Examples

```bash
feat(auth): implement JWT-based login
fix(item): prevent duplicate items from being added
docs: update README with setup instructions
```

---

## 📈 Future Enhancements

- Monthly Weekly Budget tracking
- Reports with filtering
- Barcode scanning (TBA)
- Cloud deployment with CI/CD

---

## 🤝 Contributing

Pull requests are welcome! Follow the branching and commit message conventions. Fork the repository, create a feature branch, and submit a PR to the `dev` branch.

---

## 📜 License

MIT License — see the [LICENSE](LICENSE) file.
