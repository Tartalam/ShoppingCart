# Shopping Cart GUI

**Description**: (Add a brief description of your project here.)

---

## 🚀 Prerequisites
- Java JDK 11+
- Maven 3.6+
- Eclipse IDE (or any IDE with Maven support)
- JavaFX SDK (if not using the Maven dependency)

---

## 📥 Installation & Setup
1. Download the Project from GitHub
bash
### 1. Clone the Repository

git clone https://github.com/yourusername/yourrepository.git
cd yourrepository
3. Import into Eclipse IDE with Maven
Open Eclipse.

Go to File → Import → Maven → Existing Maven Projects.

Select the root directory of your cloned repository.

Click Finish to import the project.

3. Configure VM Arguments for JavaFX
Since the dependencies (including JavaFX) are managed in pom.xml, you only need to add the required modules via VM arguments.

For Windows:
Right-click your project → Run As → Run Configurations.

Under Java Application, create a new configuration.

Go to the Arguments tab.

In VM Arguments, add:

text
--add-modules javafx.controls,javafx.fxml
Click Apply and then Run.

For Linux:
The process is the same as Windows, but ensure JavaFX is properly installed (if not using Maven dependencies).

4. Run the Project
Right-click the project → Run As → Java Application (or use Maven commands).

Alternatively, via command line (with Maven):

bash
mvn clean javafx:run



##Troubleshooting
If JavaFX is not detected, ensure:

You have the correct JDK version.

The VM arguments are properly set.

Maven dependencies are resolved (check pom.xml).

License
(Specify your project's license here, e.g., MIT, GPL, etc.)
