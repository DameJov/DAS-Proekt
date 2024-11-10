import csv
from datetime import datetime
import Filter2


def sort_csv_by_key_and_date(csv_file_path):
    data = []

    with open(csv_file_path, mode='r', newline='', encoding='utf-8') as csv_file:
        reader = csv.reader(csv_file)
        header = next(reader)
        for row in reader:
            if not row or row[1] == 'Датум':
                continue

            issuer_code = row[0]
            try:
                date = datetime.strptime(row[1], "%d.%m.%Y")
            except ValueError as ve:
                print(f"Skipping row due to date parsing error: {row}. Error: {ve}")
                continue

            data.append([issuer_code, date] + row[2:])

    sorted_data = sorted(data, key=lambda x: (x[0], -x[1].timestamp()))

    with open(csv_file_path, mode='w', newline='', encoding='utf-8') as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow(header)

        for row in sorted_data:
            row[1] = row[1].strftime("%d.%m.%Y")
            writer.writerow(row)

def fill_missing_data(issuer_codes,file_path):
    Filter2.fill_database(issuer_codes,file_path)
    sort_csv_by_key_and_date(file_path)







