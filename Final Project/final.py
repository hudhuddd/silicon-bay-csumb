import os
import math

# Final Project
# Huda Mutwakil, Andrea Weiland, Alejandro Guzman Vega & Brandon Lewis

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
  while (true):
    choice = requestString(
      "Please make one of the following selections.[1-7]\n 1.Mirror\n 2.Add Text\n 3.Scale\n 4.Crop\n 5.Filters\n 6.Transform between media types\n 7.Quit")
    if choice == "1":
      Mirror()
    elif choice == "2":
      pass
    elif choice == "3":
      crop()
    elif choice == "4":
      scale()
    elif choice == "5":
      filters()
    elif choice == "6":
      transformMedia()
    elif choice == "7":
      return
    else:
      showInformation("Invalid Selection. Please Try Again")

def transformMedia():
  choice = requestString("How would you like to transform your media[1-2]\n 1.Picture to Audio\n 2.Audio to Picture")
  if choice == "1":
    transformPictureToAudio()
  elif choice == "2":
    transformAudioToPicture()

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

def filters():
  choice = requestString(
    "What kind of filter would you like to apply?[1-1]\n 1.PixelFocus\n 2.Black and White\n 3.Sepia\n 4.Artify")
  if choice == "1":
    pixelfocus()
  elif choice == "2":
    betterBnW()
  elif choice == "3":
    sepia()
  elif choice == "4":
    artify()
  else:
    showInformation("Invalid input. Please make an appropriate selection.")

def savephoto(source, tag):
  choice = requestString("Would You Like To Save Your New Photo? (y/n)")
  if choice == "y":
    originalImagePath = source.getFileName()
    newImagePath = os.path.splitext(originalImagePath)[0] + "-" + tag + ".png"
    writePictureTo(source, newImagePath)
    showInformation("Your Photo Has Been Saved To %s" % newImagePath)

def saveSoundFromPicture(pictureSource, sound):
  choice = requestString("Would You Like To Save Your New Sound? (y/n)")
  if choice == "y":
    originalImagePath = pictureSource.getFileName()
    newSoundPath = os.path.splitext(originalImagePath)[0] + ".wav"
    writeSoundTo(sound, newSoundPath)
    showInformation("Your Sound Has Been Saved To %s" % newSoundPath)

def savePictureFromSound(soundSource, picture):
  choice = requestString("Would You Like To Save Your New Picture? (y/n)")
  if choice == "y":
    originalSoundPath = soundSource.getFileName()
    newImagePath = os.path.splitext(originalSoundPath)[0] + ".png"
    writePictureTo(picture, newImagePath)
    showInformation("Your Sound Has Been Saved To %s" % newImagePath)

# Filters
def pixelfocus():
  showInformation("Please Choose A Picture To Manipulate.")
  source = makePicture(pickAFile())
  for y in range(getHeight(source)):
    mirrorColor = white
    pixelsToCopy = 25.0
    rePaint = 0;
    for x in range(getWidth(source)):
      if rePaint > 0:
        setColor(getPixel(source, x, y), mirrorColor)
        rePaint = rePaint - 1
      else:
        mirrorColor = getColor(getPixel(source, x, y))
        rePaint = int(pixelsToCopy * ((cos(((x * 1.0) / getWidth(source)) * 2 * pi) + 1) / 2))
  show(source)
  savephoto(source, "pixelfocus")

def betterBnW():
  showInformation("Please Choose A Picture To Manipulate.")
  source = makePicture(pickAFile())
  pixels = getPixels(source)
  for p in pixels:
    lum = ((getRed(p) * .299) + (getBlue(p) * .114) + (getGreen(p) * .587))
    setRed(p, lum)
    setBlue(p, lum)
    setGreen(p, lum)
  show(source)
  savephoto(source, "blackandwhite")

def sepia():
  showInformation("Please Choose A Picture To Manipulate.")
  source = makePicture(pickAFile())
  for p in pixels:
    lum = ((getRed(p) * .299) + (getBlue(p) * .114) + (getGreen(p) * .587))
    setRed(p, lum)
    setBlue(p, lum)
    setGreen(p, lum)
  for pix in getPixels(source):
    redValue = getRed(pix)
    if redValue < 63:
      setRed(pix, getRed(pix) * 1.1)
      setBlue(pix, getBlue(pix) * .9)
    elif 62 < redValue < 192:
      setRed(pix, getRed(pix) * 1.15)
      setBlue(pix, getBlue(pix) * .85)
    else:
      if getRed(pix) * 1.08 > 255:
        setRed(pix, 255)
      else:
        setRed(pix, getRed(pix) * 1.08)
      setBlue(pix, getBlue(pix) * .93)
  show(source)
  savephoto(source, "sepia")

def artify():
  showInformation("Please Choose A Picture To Manipulate.")
  source = makePicture(pickAFile())
  for pix in getPixels(source):
    redValue = getRed(pix)
    if redValue < 64:
      setRed(pix, 31)
    elif 63 < redValue < 128:
      setRed(pix, 95)
    elif 127 < redValue < 192:
      setRed(pix, 159)
    else:
      setRed(pix, 223)

    blueValue = getBlue(pix)
    if blueValue < 64:
      setBlue(pix, 31)
    elif 63 < blueValue < 128:
      setBlue(pix, 95)
    elif 127 < blueValue < 192:
      setBlue(pix, 159)
    else:
      setBlue(pix, 223)

    greenValue = getGreen(pix)
    if greenValue < 64:
      setGreen(pix, 31)
    elif 63 < greenValue < 128:
      setGreen(pix, 95)
    elif 127 < greenValue < 192:
      setGreen(pix, 159)
    else:
      setGreen(pix, 223)
  show(source)
  savephoto(source, "artify")

# Transform
def median(lst):
    lst = sorted(lst)
    if len(lst) < 1:
            return None
    if len(lst) %2 == 1:
            return lst[((len(lst)+1)/2)-1]
    else:
            return float(sum(lst[(len(lst)/2)-1:(len(lst)/2)+1]))/2.0

def transformPictureToAudio():
  showInformation("Please Choose A Picture To Transform Into A Sound.")
  source = makePicture(pickAFile())
  pixels = getPixels(source)
  pixelValues = []
  for p in pixels:
    pixelValues.append((getRed(p) + getBlue(p) + getGreen(p)) * 10)
  sound = makeEmptySound(len(pixelValues))
  shift = median(pixelValues)
  i = 0
  for pv in pixelValues:
    setSampleValueAt(sound, i, pv - shift)
    i += 1
  explore(sound)
  saveSoundFromPicture(source, sound)


def transformAudioToPicture():
  showInformation("Please Choose A Sound To Transform Into A Picture.")
  source = makeSound(pickAFile())
  samples = getSamples(source)
  minSample = min(samples, key=lambda p: p.getValue())
  maxSample = max(samples, key=lambda p: p.getValue())
  handw = int(math.sqrt(len(samples)))
  pic = makeEmptyPicture(handw, handw)
  i = 0
  for x in range(handw):
    for y in range(handw):
      norm = (getSampleValueAt(source, i) - minSample.getValue()) / float(maxSample.getValue() - minSample.getValue())
      i += 1
      setRed(getPixelAt(pic, x, y), int(255 * norm))
      setBlue(getPixelAt(pic, x, y), int(255 * norm))
      setGreen(getPixelAt(pic, x, y), int(255 * norm))
  explore(pic)
  savePictureFromSound(source, pic)

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
  savephoto(source, "vertical")

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
  savephoto(source, "toptobottom")

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
  savephoto(source, "bottomtotop")

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
  savephoto(source, "quad")
  
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
  savephoto(pic, "crop")

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
  savephoto(pic, "scale")

def main():
  mainMenu()
