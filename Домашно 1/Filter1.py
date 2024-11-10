import requests
from bs4 import BeautifulSoup
import re

def scrape_issuer_codes():
    url = "https://www.mse.mk/mk/stats/symbolhistory/ALKB"
    response = requests.get(url)

    if response.status_code == 200:
        soup = BeautifulSoup(response.text, 'html.parser')

        select_tag = soup.find('select', {'id': 'Code', 'name': 'Code'})

        if select_tag:
            issuer_codes = []
            for option in select_tag.find_all('option'):
                code = option.get('value')
                if code and not re.search(r'\d', code):
                    issuer_codes.append(code)

    return issuer_codes
