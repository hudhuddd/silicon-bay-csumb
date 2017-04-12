# Final Project
# Huda Mutwakil, Andrea Weiland, Alejandro Guzman-Vega & Brandon Lewis

welcomeMsg = "Welcome to OtterShop! \nHere are your 6 options for photo manipulation: \n1. Mirror \n2. Add Text"

def Mirror():
  # User is prompted with what kind of mirror they would like to do
  # Top to bottom, bottom to top, crazy quad, or vertical
  # Use if statements to determine what the input was

# Begin Mirror Functions

def verticalMirror():
  width = getWidth(source)
  height = getHeight(source)
  startMirror = width / 2
  for y in range(0, height):
    for x in range(0, startMirror):
      leftPixel = getPixel(source, width - x - 1, y)
      rightPixel = getPixel(source, x, y)
      color = getColor(rightPixel)
      setColor(leftPixel, color)
  
def topToBottomMirror():
  height = getHeight(source)
  width = getWidth(source)
  startMirror = height / 2
  for x in range(0, width):
    for y in range(0, startMirror):
      topPixel = getPixel(source, x, y)
      bottomPixel = getPixel(source, x, height - y - 2)
      color = getColor(topPixel)
      setColor(bottomPixel, color)
      
def bottomToTopMirror():
  height = getHeight(source)
  width = getWidth(source)
  startMirror = height / 2
  for x in range(0, width):
    for y in range(0, startMirror):
      topPixel = getPixel(source, x, height - y - 2)
      bottomPixel = getPixel(source, x, y)
      color = getColor(topPixel)
      setColor(bottomPixel, color)
      
def crazyQuadMirror():
  height = getHeight(source)
  width = getWidth(source)
  startMirror = width / 2
  for y in range(0, height):
    for x in range(0, startMirror):
      leftPixel = getPixel(source, width - x - 1, y)
      rightPixel = getPixel(source, x, y)
      color = getColor(rightPixel)
      setColor(leftPixel, color)
  for x in range(0, width):
    for y in range(0, startMirror):
      topPixel = getPixel(source, x, height - y - 2)
      bottomPixel = getPixel(source, x, y)
      color = getColor(topPixel)
      setColor(bottomPixel, color)
      
def main():
  showInformation(welcomeMsg)