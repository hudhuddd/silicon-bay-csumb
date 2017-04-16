# Huda Mutwakil & Alejandro Guzman-Vega
# Lab 15
# Begin Problem 1
import random
import calendar
import pprint
from datetime import *

instruction = """This program simulates the dice game 'Craps'.\n
A player rolls two six-sided dice and adds the numbers rolled together.\n
If the player rolls a 7 or 11 on the first roll, they automatically win.
If the player rolls a 2, 3 or 12 on the first roll, they automatically lose and the play is over.
The player continues to roll two dice again until one of two things happens: either they roll the 'point' again, in which case they win, or they roll a 7, in which case they lose."""  
  
def craps():
  firstroll = 0
  newroll = 0
  
  response = requestString("Are you ready to play craps (y/n)?")
  if response == "y":
    showInformation(instruction)
    
  while response == "y":
    firstroll = random.randint(1, 7) + random.randint(1, 7)
    
    if firstroll == 7 or firstroll == 11:
      showInformation("Congrats! You Rolled %d! You Win" % firstroll)
      return
      
    if firstroll == 2 or firstroll == 3 or firstroll == 12:
      showInformation("Sorry, you rolled %d... You lose" % firstroll)
      return
      
    else:
      showInformation("You rolled a %d. You must roll that number again to win" % firstroll)
      points = firstroll
      
      newroll = random.randint(1, 7) + random.randint(1, 7)
      showInformation("You rolled a %d." % newroll)
      
      while newroll != points and newroll != 7:
        showInformation("Roll again to roll your point.")
        newroll = random.randint(1, 7) + random.randint(1, 7)
        
        showInformation("You rolled a %d." % newroll)
        
      if newroll == points:
        showInformation("Congrats! You Rolled %d! You Win" % newroll)
        return
        
      else:
        showInformation("Sorry, You Rolled %d... You Lose" % newroll)
        return
        
# Begin Problem 2

# Print birthday month
def birthdayMonth():
  year = int(raw_input("Enter the year you were born: "))
  month = int(raw_input("Enter the month in number form that you were born: "))
  calendar.prmonth(year,month)
 
def daysTilBirthday():
  today = date.today()
  day = int(raw_input("Enter the day you were born: "))
  month = int(raw_input("Enter the month in number form that you were born: "))
  year = int(raw_input("Enter the next year you will have a birthday: "))
  future = date(year,month,day)
  diff = future - today
  print diff.days
  
def printDayOfWeek():
  date = (calendar.weekday(1776, 7, 4))
  if date == 0:
    print "The Delcaration of Independence was ratified on a : Monday"
  if date == 1:
    print "The Delcaration of Independence was ratified on a : Tuesday"
  if date == 2:
    print "The Delcaration of Independence was ratified on a : Wednesday"
  if date == 3:
    print "The Delcaration of Independence was ratified on a : Thursday"
  if date == 4:
    print "The Delcaration of Independence was ratified on a : Friday"
  if date == 5:
    print "The Delcaration of Independence was ratified on a : Saturday"
  if date == 6:
    print "The Delcaration of Independence was ratified on a : Sunday"
  
