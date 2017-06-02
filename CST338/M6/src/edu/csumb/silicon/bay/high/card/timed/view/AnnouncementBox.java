package edu.csumb.silicon.bay.high.card.timed.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class AnnouncementBox
{
   static int HEIGHT = 200;
   static int WIDTH = 300;
   private JFrame window;
   private JLabel text;

   // constructor
   public AnnouncementBox(String input)
   {
      window = new JFrame();
      window.setSize(WIDTH, HEIGHT);
      window.setLocationRelativeTo(null);
      window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

      window.setLayout(new BorderLayout(10, 10));

      text = new JLabel(input);
      text.setHorizontalAlignment(SwingConstants.CENTER);
      text.setVerticalAlignment(SwingConstants.CENTER);
      window.add(text, BorderLayout.CENTER);
      JButton endButton = new JButton("OK");
      EndingListener buttonEar = new EndingListener(window);
      endButton.addActionListener(buttonEar);
      endButton.setHorizontalAlignment(SwingConstants.CENTER);
      endButton.setVerticalAlignment(SwingConstants.CENTER);
      window.add(endButton, BorderLayout.SOUTH);
      window.setVisible(true);

   }

   // on button click, closes window
   class EndingListener implements ActionListener
   {
      JFrame window;

      EndingListener(JFrame window)
      {
         this.window = window;
      }

      public void actionPerformed(ActionEvent e)
      {
         window.dispose();
      }
   }
}
