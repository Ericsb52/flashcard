package quizcard_builder;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizCardPlayer {//start class
	//
	private JTextArea display;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private int currentCardIndex;
	private JFrame frame;
	private JButton nextButton;
	private boolean isShowAnswer;
	

	public static void main(String[] args) {//start main
		QuizCardPlayer reader = new QuizCardPlayer();
		reader.go();
	}//close main

	public void go() {//start go
		//build GUI
		
		frame = new JFrame("Quiz Card Player");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif",Font.BOLD,24);
		
		display = new JTextArea(10,20);
		display.setFont(bigFont);
		
		display.setLineWrap(true);
		display.setEditable(false);
		
		JScrollPane qScroller = new JScrollPane(display);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		nextButton = new JButton("show Question");
		mainPanel.add(qScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("file");
		JMenuItem loadMenuItem = new JMenuItem("load card set");
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(640, 500);
		frame.setVisible(true);
	}//close go 
	
	public class NextCardListener implements ActionListener {//start NextCardListener class

	
		public void actionPerformed(ActionEvent ev) {//start action
			if (isShowAnswer) {//if
				display.setText(currentCard.getAnswer());
				nextButton.setText("next Card");
				isShowAnswer = false;
			}//end if
			else {//start else
				if (currentCardIndex < cardList.size()) {//start if
					showNextCard();
				}//end if
				else {//start else
					display.setText("that was the last card");
					nextButton.setEnabled(false);
				}//end else
			}//end else
		}//close action
		
	}//close NextCardListener class
	
	public class OpenMenuListener implements ActionListener{//start OpenMenuListener

		public void actionPerformed(ActionEvent ev) {//start action
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
						
		}//end action
		
	}//end OpenMenuListener
	
	private void loadFile(File file) {//start
		
		cardList = new ArrayList<QuizCard>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine())!= null) {//start while
				makeCard(line);
			}//end while
			
		} catch (Exception ex) {//start catch
			System.out.println("couldent read the file");
			ex.printStackTrace();
		}//end catch
		showNextCard();
	}//end loadFile
	
	private void makeCard(String lineToParse) {//start make card
		
		String[] results = lineToParse.split("/");
		QuizCard card = new QuizCard(results[0],results[1]);
		cardList.add(card);
		System.out.println("made a card");
	}//end make card
	
	private void showNextCard() {//start showNextCard
		
		currentCard = cardList.get(currentCardIndex);
		currentCardIndex++;
		display.setText(currentCard.getQuestion());
		nextButton.setText("show answer");
		isShowAnswer = true;
		
	}//end showNextCard
	
}//close class
