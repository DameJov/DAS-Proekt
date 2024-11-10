import requests
from bs4 import BeautifulSoup
import re

# Step 1: Send a request to the URL
def scrape_issuer_codes():
    url = "https://www.mse.mk/mk/stats/symbolhistory/ALKB"
    response = requests.get(url)

    # Check if the request was successful
    if response.status_code == 200:
        # Step 2: Parse the HTML content
        soup = BeautifulSoup(response.text, 'html.parser')

        # Step 3: Find the <select> tag with id="Code"
        select_tag = soup.find('select', {'id': 'Code', 'name': 'Code'})

        if select_tag:
            # Step 4: Extract all <option> values and filter out those with numbers
            issuer_codes = []
            for option in select_tag.find_all('option'):
                code = option.get('value')
                # Filter out values with numbers
                if code and not re.search(r'\d', code):
                    issuer_codes.append(code)

    return issuer_codes