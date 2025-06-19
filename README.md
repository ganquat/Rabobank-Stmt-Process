Rabobank statement processor
===================
Rabobank statement processor is a spring boot based web application. It uses thymeleaf template engine to display html files. The first page shows
option to upload MT940 file (either CSV or XML file ) from the local machine. On clicking the submit button the file is validated at the 
backend and status is displayed in the new page 


## Setup ##

This project built using Intellij IDEA ide and Maven dependency management. The project can be directly imported in IJ ide or can be run from the 
command line using maven command. The application uses inbuilt spring boot tomcat server can be accessed using the url http://localhost:8080


## Run using Intellij IDEA ##

Import the project in Intellij IDEA ide. Goto Source directory -> Right click SpringBootWebApplication class file and run it.


## Run using Maven commandline ##

Goto project directory in the command promt and run the following commands

__mvn clean package__

__java -jar target/Rabobank-Stmt-Process-1.0.jar__

## Future Enhancements

### 1. Support for More File Formats

Currently, the application supports CSV and XML statement files. To increase its versatility, support for other common financial data formats could be added. Examples include:

*   **OFX (Open Financial Exchange):** A widely used standard for exchanging financial data.
*   **QIF (Quicken Interchange Format):** An older but still common format used by Quicken and other financial software.
*   **JSON:** A modern, lightweight data interchange format that could be useful for API integrations or custom data feeds.

### 2. Enhanced Validation Rules

The application currently performs validation of individual transactions. This could be expanded with more sophisticated validation logic:

*   **Duplicate Transaction Detection:** Implement checks to identify duplicate transactions, potentially even across multiple uploaded files or a historical dataset.
*   **Balance Consistency Checks:** For statement formats that include opening and closing balances, verify that the opening balance plus all transactions equals the closing balance.
*   **Anomaly Detection:** Flag unusual transaction patterns, such as exceptionally large transactions or deviations from typical spending habits (this might require more advanced data analysis capabilities).

### 3. Data Export and Reporting

After processing and validating statements, users could benefit from options to export the data or generate simple reports:

*   **Export Validated Data:** Allow users to export the cleaned and validated transaction list to formats like a clean CSV file or a PDF summary. This would be useful for record-keeping or importing into other tools.
*   **Basic Analytical Reports:** Implement simple reporting features, such as:
    *   Spending by category (if category data is available in the statements or can be inferred).
    *   Monthly income vs. expenses summaries.
    *   Largest transactions by value.
