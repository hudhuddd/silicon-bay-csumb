# Lab 16
# Huda Mutwakil & Alejandro Guzman-Vega

import urllib

# open the website and assign it to a variable, then create a list
url = urllib.urlopen("https://calbusinesstech.com/services/")
urlFile = url.readlines()


# parse through the data to extract only the services
services = []
def services(list):
  print "---CBT Services!---"
  for line in list:
    if "<td class=" in line:
      start = line.find(">")
      end = line.find("</td>")
      print line[start:end]

# html function to create the html page with the services
def html():
    services(urlFile)

    file = open("/Users/hudhuddd/Desktop/CST205/Python Code/silicon-bay-csumb/Lab16/ham.html", "wt")

    file.write("""<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transition//EN" "http://www.w3.org/TR/html4/loose.dtd">
  
    <html>

    <head>
        <title>I made this page with Python!</title>
    </head>

    <body>

    <h1>CBT SERVICES!!</h1>

    # ***************need to insert the list here somehow***************

    </body>

    </html>""")

    file.close()
