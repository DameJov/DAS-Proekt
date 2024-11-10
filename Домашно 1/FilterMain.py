import Filter1
import Filter2
import Filter3

issuer_codes = Filter1.scrape_issuer_codes()
file_path = 'mse_stock_data.csv'
Filter2.fill_database(issuer_codes,file_path)
Filter3.fill_missing_data(issuer_codes,file_path)