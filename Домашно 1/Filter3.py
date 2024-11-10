import csv
from datetime import datetime
import Filter2


def sort_csv_by_key_and_date(csv_file_path):
    # Read the data from the CSV file
    data = []

    with open(csv_file_path, mode='r', newline='', encoding='utf-8') as csv_file:
        reader = csv.reader(csv_file)
        header = next(reader)  # Extract the header
        for row in reader:
            if not row or row[1] == 'Датум':  # Additional check to skip any empty rows or header rows
                continue

            issuer_code = row[0]
            try:
                # Parse date in 'dd.mm.yyyy' format
                date = datetime.strptime(row[1], "%d.%m.%Y")
            except ValueError as ve:
                print(f"Skipping row due to date parsing error: {row}. Error: {ve}")
                continue

            data.append([issuer_code, date] + row[2:])

    # Sort data first by issuer code (key) ascending, then by date in descending order
    sorted_data = sorted(data, key=lambda x: (x[0], -x[1].timestamp()))

    # Write the sorted data back to the CSV file
    with open(csv_file_path, mode='w', newline='', encoding='utf-8') as csv_file:
        writer = csv.writer(csv_file)
        # Write the header back to the file
        writer.writerow(header)

        # Write sorted rows, converting date back to string format
        for row in sorted_data:
            row[1] = row[1].strftime("%d.%m.%Y")  # Convert datetime back to string format
            writer.writerow(row)

def fill_missing_data(issuer_codes,file_path):
    Filter2.fill_database(issuer_codes,file_path)
    sort_csv_by_key_and_date(file_path)







