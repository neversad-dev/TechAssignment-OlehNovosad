# PaymentApp

Android Payment App implementing Clean Architecture and MVVM, featuring:

- **PinPad Screen** — enter transaction amounts (Jetpack Compose)
- **Transaction Simulation** — fetch and process JSON transaction data
- **Receipt Screen** — display final transaction details with tax, tips, and discount calculations

This project demonstrates UI development, state management, and business logic separation in a scalable, modular design.

---

## Project Setup

1. **Clone the repository:**
   ```sh
   git clone https://github.com/neversad-dev/TechAssignment-OlehNovosad.git
   cd TechAssignment-OlehNovosad
   ```

2. **Open in Android Studio**  
   Open the project root in latest version of Android Studio.

3. **Build the project:**
   - From the IDE: Use the "Build Project" action.
   - Or from the command line:
     ```sh
     ./gradlew build
     ```

---

## Linter Setup

This project uses [**Kotlinter**](https://github.com/jeremymailen/kotlinter-gradle) (with [ktlint](https://github.com/pinterest/ktlint)) for Kotlin code style and lint checks. As well as [a set of rules for Compose](https://github.com/mrmans0n/compose-rules?ref=nlopez.io)

- **Run linter manually:**
  ```sh
  ./gradlew lintKotlin
  ```
- **Auto-format code:**
  ```sh
  ./gradlew formatKotlin
  ```
- The linter will fail the build on any lint errors (see `build.gradle.kts` for configuration).

---

## Optional: Pre-commit Hook

To help ensure code is formatted before every commit, you can install the provided pre-commit hook:

1. **Install the hook:**
   - Create a file named `pre-commit` in the `.git/hooks` directory.
   - Copy the following contents into the file:
     ```sh
     if ! ${'$'}GRADLEW formatKotlin ; then
         echo 1>&2 "\nformatKotlin had non-zero exit status, aborting commit"
         exit 1
     fi
     ```
   - Make the hook executable:
     ```sh
     chmod +x .git/hooks/pre-commit
     ```

2. **What it does:**  
   This hook runs `./gradlew formatKotlin` before each commit. If formatting fails, the commit is aborted.

---

### License and Usage Notice

This repository is provided **for technical assignment purposes only**.  
It is not licensed for public reuse or distribution.
