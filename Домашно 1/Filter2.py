
import csv
import os
import time
from datetime import datetime, timedelta
from selenium import webdriver
from selenium.webdriver.firefox.service import Service as FirefoxService
from selenium.webdriver.firefox.options import Options as FirefoxOptions
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
from bs4 import BeautifulSoup


# Check the last available date for the issuer
def check_last_available_date(csv_file_path, issuer_code):
    last_date = None
    if not os.path.exists(csv_file_path):
        print(f"No existing data. Fetching 10 years of data for issuer: {issuer_code}")
        return None

    with open(csv_file_path, mode='r', newline='', encoding='utf-8') as csv_file:
        reader = csv.reader(csv_file)
        header = next(reader)
        date_index = header.index('Датум')
        issuer_index = header.index('Шифра')

        for row in reader:
            if row[issuer_index] == issuer_code:
                row_date = datetime.strptime(row[date_index], '%d.%m.%Y')
                if not last_date or row_date > last_date:
                    last_date = row_date

    if last_date:
        print(f"Last available date for issuer {issuer_code} is: {last_date.strftime('%d.%m.%Y')}")
    else:
        print(f"No data found for issuer {issuer_code}. Fetching 10 years of data.")

    return last_date


# Fill the database with data from the issuer codes
def fill_database(issuer_codes,csv_file_path):
    # Initialize the Firefox driver
    service = FirefoxService(executable_path="geckodriver.exe")
    options = FirefoxOptions()
    options.binary_location = "C:/Program Files/Mozilla Firefox/firefox.exe"
    driver = webdriver.Firefox(service=service, options=options)

    # Define the end date (today)
    end_date = datetime.now()

    start_time = time.time()
    for code in issuer_codes:
        url = f'https://www.mse.mk/mk/stats/symbolhistory/{code}'
        driver.get(url)
        print(f"Processing issuer: {code}")

        # Determine the start date
        last_date = check_last_available_date(csv_file_path, code)

        if last_date is None:
            start_date = end_date - timedelta(days=365 * 10)  # 10 years back
            file_exists = False
        else:
            start_date = last_date + timedelta(days=1)
            file_exists = True

        # Initialize the current end date as today
        current_end_date = end_date

        # Iterate through the date ranges in 365-day intervals
        while current_end_date > start_date:
            current_start_date = max(start_date, current_end_date - timedelta(days=365))

            # Format dates as "dd.mm.yyyy"
            from_date = current_start_date.strftime("%d.%m.%Y")
            to_date = current_end_date.strftime("%d.%m.%Y")

            # Set date range in the input fields
            from_date_input = driver.find_element(By.ID, "FromDate")
            to_date_input = driver.find_element(By.ID, "ToDate")

            from_date_input.clear()
            from_date_input.send_keys(from_date)
            to_date_input.clear()
            to_date_input.send_keys(to_date)

            # Click the "Прикажи" button to display data
            show_button = driver.find_element(By.XPATH, '//input[@value="Прикажи" and @onclick="return btnClick();"]')
            show_button.click()

            # Wait for the results table to load or skip if not found
            try:
                WebDriverWait(driver, 0.1).until(EC.visibility_of_element_located((By.ID, "resultsTable")))
            except TimeoutException:
                print(f"No data found for period: {from_date} to {to_date}. Skipping to next interval.")
                current_end_date -= timedelta(days=365)
                continue

            # Parse the page with BeautifulSoup
            soup = BeautifulSoup(driver.page_source, 'html.parser')
            table = soup.find('table', id='resultsTable')

            if not table:
                print(f"No table found for {code} from {from_date} to {to_date}.")
                current_end_date -= timedelta(days=365)
                continue

            # Write to CSV file
            with open(csv_file_path, mode='a', newline='', encoding='utf-8') as csv_file:
                writer = csv.writer(csv_file)

                # Write the header if the file doesn't exist
                if not file_exists:
                    header = ['Шифра', 'Датум', 'Цена на последна трансакција', 'Макс.', 'Мин.', 'Просечна цена',
                              '%пром.', 'Количина', 'Промет во БЕСТ во денари', 'Вкупен промет во денари']
                    writer.writerow(header)

                # Extract rows from the table
                rows = table.find_all('tr')
                for row in rows[1:]:
                    cols = row.find_all('td')
                    data = [code] + [col.text.strip() or '0,00' for col in cols]
                    writer.writerow(data)

            current_end_date -= timedelta(days=365)


    driver.quit()
    print("Data has been written to mse_stock_data.csv")
    # End the timer and calculate the elapsed time
    end_time = time.time()
    elapsed_time = end_time - start_time

    # Print the execution time
    print(f"Time taken to fill the database: {elapsed_time:.2f} seconds")


