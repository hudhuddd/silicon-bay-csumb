# Lab 16
# Huda Mutwakil & Alejandro Guzman-Vega

import urllib

# open the website and assign it to a variable, then create a list
url = urllib.urlopen("http://calbusinesstech.com/services/")
urlFile = url.readlines()


# parse through the data to extract only the services
cbtservices = []
def services(list):
  for line in list:
    if "<td class=" in line:
      start = line.find(">") + 1
      end = line.find("</td>")
      cbtservices.append(line[start:end])      
 

# html function to create the html page with the services
def html():
    services(urlFile)
    servicesString =  "<br>".join(cbtservices)
    file = open("/Users/alejandro/ham.html", "w")

    file.write("""<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transition//EN" "http://www.w3.org/TR/html4/loose.dtd">
  
    <html>

    <head>
        <title>I made this page with Python!</title>
    </head>

    <body>

    <h1>CBT SERVICES!!</h1>
    
    %s

    </body>

    </ht>""" % servicesString)

    file.close()
