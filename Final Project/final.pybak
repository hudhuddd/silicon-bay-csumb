# Final Project
# Huda Mutwakil, Andrea Weiland, Alejandro Guzman-Vega & Brandon Lewis

"""
********* PROGRAM START *********
Show welcome message.
User begins in main function.
Here is where we create the main menu.
User must make a selection.
A check for valid selection is made.
If selection is invalid, user is required to retry.
If select is valid, then the respective function is called.
"""
def mainMenu():
  showInformation("Welcome to OtterShop!")
  choice = requestString("Please make one of the following selections.[1-7]\n 1.Mirror\n 2.Add Text\n 3.Scale\n 4.Crop\n 5.Menu5\n 6.Menu6\n 7.Quit")
  if choice == "1":
    Mirror()
  if choice == "3":
    crop()
  if choice == "4":
    scale()
    
  elif choice == "7":
    return
    
  else:
    showInformation("Invalid Selection. Please Try Again")
    

def Mirror():
  choice = requestString("What kind of mirror would you like to perform?[1-4]\n 1.Vertical\n 2.Top To Bottom\n 3.Bottom To Top\n 4.Crazy Quad Mirror")
  if choice == "1":
    vertical()
    
  elif choice == "2":
    topToBottomMirror()
    
  elif choice == "3":
    bottomToTopMirror()
    
  elif choice == "4":
    crazyQuadMirror()
    
  else:
    showInformation("Invalid input. Please make an appropriate selection.")

# Begin Mirror Functions

def vertical():
  showInformation("Please Choose A Picture To Manipulate.")
  source = makePicture(pickAFile())
  width = getWidth(source)
  height = getHeight(source)
  startMirror = width / 2
  for y in range(0, height):
    for x in range(0, startMirror):
      leftPixel = getPixel(source, width - x - 1, y)
      rightPixel = getPixel(source, x, y)
      color = getColor(rightPixel)
      setColor(leftPixel, color) 
  show(source)
  choice = requestString("Would You Like To Save Your New Photo? (y/n)")
  if choice == "y":
    writePictureTo(source, "/Users/hudhuddd/Desktop/verticalMirror.jpg")
    showInformation("Your Photo Has Been Saved To Your Desktop as verticalmirror.jpg")
  elif choice == "n":
    return
  
def topToBottomMirror():
  showInformation("Please Choose A Picture To Manipulate.")
  source = makePicture(pickAFile())
  height = getHeight(source)
  width = getWidth(source)
  startMirror = height / 2
  for x in range(0, width):
    for y in range(0, startMirror):
      topPixel = getPixel(source, x, y)
      bottomPixel = getPixel(source, x, height - y - 2)
      color = getColor(topPixel)
      setColor(bottomPixel, color)
  show(source)
  choice = requestString("Would You Like To Save Your New Photo? (y/n)")
  if choice == "y":
    writePictureTo(source, "/Users/hudhuddd/Desktop/toptobottom.jpg")
    showInformation("Your Photo Has Been Saved To Your Desktop as toptobottom.jpg")
  elif choice == "n":
    return
      
def bottomToTopMirror():
  showInformation("Please Choose A Picture To Manipulate.")
  source = makePicture(pickAFile())
  height = getHeight(source)
  width = getWidth(source)
  startMirror = height / 2
  for x in range(0, width):
    for y in range(0, startMirror):
      topPixel = getPixel(source, x, height - y - 2)
      bottomPixel = getPixel(source, x, y)
      color = getColor(topPixel)
      setColor(bottomPixel, color)
  show(source)
  choice = requestString("Would You Like To Save Your New Photo? (y/n)")
  if choice == "y":
    writePictureTo(source, "/Users/hudhuddd/Desktop/bottomtotop.jpg")
    showInformation("Your Photo Has Been Saved To Your Desktop as bottomtotop.jpg")
  elif choice == "n":
    return
      
def crazyQuadMirror():
  showInformation("Please Choose A Picture To Manipulate.")
  source = makePicture(pickAFile())
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
  show(source)
  choice = requestString("Would You Like To Save Your New Photo? (y/n)")
  if choice == "y":
    writePictureTo(source, "/Users/hudhuddd/Desktop/crazyquad.jpg")
    showInformation("Your Photo Has Been Saved To Your Desktop as crazyquad.jpg")
  elif choice == "n":
    return
    
    
"""
************* ANDREAS CROP & SCALE FUNCTIONS *************
"""

#squares the photo. creates a new square image sized to the smallest
#dimension of the original and centers the image
def crop():
  showInformation("Please Choose A Picture To Manipulate.")
  pic = makePicture(pickAFile())
  smallestDim = getWidth(pic)
  if getHeight(pic) < smallestDim:
    smallestDim = getHeight(pic)
  croppedPic = makeEmptyPicture(smallestDim, smallestDim)
  for x in range(0, getWidth(croppedPic)):
    for y in range(0, getHeight(croppedPic)):
      color = getColor(getPixel(pic, (x + (getWidth(pic)-smallestDim)/2), y + (getHeight(pic)-smallestDim)/2))
      setColor(getPixel(croppedPic, x, y), color)
  show(pic)
  choice = requestString("Would You Like To Save Your New Photo? (y/n)")
  if choice == "y":
    writePictureTo(pic, "/Users/hudhuddd/Desktop/crazyquad.jpg")
    showInformation("Your Photo Has Been Saved To Your Desktop as crazyquad.jpg")
  elif choice == "n":
    return   

#scales the picture down to given pixel dimension. based on shrink function, 
#but instead of 
#reducing the image by half, it reduces it by the ratio of the original
#dimensions to the target size      
def scale():
  showInformation("Please Choose A Picture To Manipulate.")
  pic = makePicture(pickAFile())
  dimension = requestString("Enter the dimension you would like.")
  if getWidth(pic) < dimension:
    showInformation("Error! Your picture must be larger.")
  elif getWidth(pic) == dimension:
    return pic
  else:
    ratio = getWidth(pic)/float(dimension)
    newpic = makeEmptyPicture(dimension, dimension)
    for x in range(0, dimension-1):
      for y in range(0, dimension-1):
        targetx = round(x * ratio)
        targety = round(y * ratio)
        setColor(getPixel(newpic, x, y), getColor(getPixel(pic, int(targetx), int(targety))))
  show(pic)
  choice = requestString("Would You Like To Save Your New Photo? (y/n)")
  if choice == "y":
    writePictureTo(pic, "/Users/hudhuddd/Desktop/crazyquad.jpg")
    showInformation("Your Photo Has Been Saved To Your Desktop as crazyquad.jpg")
  elif choice == "n":
    return 

def main():
  mainMenu()
