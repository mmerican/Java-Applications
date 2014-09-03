/* This is a game called MasterMind. The player must guess the chosen word
 * by typing it in from a list. If they do not pick the right word, the game
 * will tell the player how many letters are in the correct position in the
 * word, but it will not say which of the letters it is. The player has 4
 * chances to get the right answer.
 */

package MasterMind;

import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MasterMind extends JFrame implements ActionListener {
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblDifficulty = new JLabel("Difficulty");
	private JPanel panel;
	private JRadioButton diff1, diff2, diff3, diff4, diff5;
	private JButton btnStart;
	private static int wLength;
	private static int numWords;
	private ArrayList<String> dictionary;
	private ArrayList<String> wordBank = new ArrayList<String>();
	private JPanel panel_1;
	private JButton btnSubmit;
	private JTextArea textArea;
	private JLabel outcomeLabel;
	private int numTries;
	private String answer = "";
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				MasterMind frame = new MasterMind();
				frame.setSize(450,800);
				frame.setVisible(true);
			}
		});
	}
	
	public MasterMind() {
		dictionary = this.getWords();
		
		setTitle("MasterMind by mmerican");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{434, 0};
		gridBagLayout.rowHeights = new int[]{72, 72, 72, 72, 151, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		lblDifficulty.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblDifficulty = new GridBagConstraints();
		gbc_lblDifficulty.fill = GridBagConstraints.BOTH;
		gbc_lblDifficulty.insets = new Insets(0, 0, 5, 0);
		gbc_lblDifficulty.gridx = 0;
		gbc_lblDifficulty.gridy = 0;
		getContentPane().add(lblDifficulty, gbc_lblDifficulty);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		diff1 = new JRadioButton("1");
		buttonGroup.add(diff1);
		diff1.setSelected(true);
		panel.add(diff1);
		
		diff2 = new JRadioButton("2");
		buttonGroup.add(diff2);
		panel.add(diff2);
		
		diff3 = new JRadioButton("3");
		buttonGroup.add(diff3);
		panel.add(diff3);
		
		diff4 = new JRadioButton("4");
		buttonGroup.add(diff4);
		panel.add(diff4);
		
		diff5 = new JRadioButton("5");
		buttonGroup.add(diff5);
		panel.add(diff5);
		
		btnStart = new JButton("START");
		panel.add(btnStart);
		btnStart.addActionListener(this);
		
		JLabel lblEnterWord = new JLabel("Guess ( left)");
		lblEnterWord.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblEnterWord = new GridBagConstraints();
		gbc_lblEnterWord.fill = GridBagConstraints.BOTH;
		gbc_lblEnterWord.insets = new Insets(0, 0, 5, 0);
		gbc_lblEnterWord.gridx = 0;
		gbc_lblEnterWord.gridy = 2;
		getContentPane().add(lblEnterWord, gbc_lblEnterWord);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 3;
		getContentPane().add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		btnSubmit = new JButton("SUBMIT");
		panel_1.add(btnSubmit);
		btnSubmit.setVisible(false);
		btnSubmit.addActionListener(this);
		
		textArea = new JTextArea(15,20);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.VERTICAL;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 4;
		getContentPane().add(textArea, gbc_textArea);
		
		outcomeLabel = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridx = 0;
		gbc_label.gridy = 5;
		getContentPane().add(outcomeLabel, gbc_label);
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource().equals(btnStart)) {
			btnStart.setVisible(false);
			outcomeLabel.setText("4 Tries Left");
			numTries = 4;
			this.setDifficulty();
			wordBank.clear();
			for (int i = 0; i < numWords; i++) {
				wordBank.add(getRandWord());
				textArea.append(wordBank.get(i) + "\n");
			}
			btnSubmit.setVisible(true);
			answer = getAnswer(wordBank);
		}
		if (evt.getSource().equals(btnSubmit)) {
			numTries -= 1;
			String text = textField.getText();
			if (numTries > 0) {
				if (text.toUpperCase().equals(answer)) {
					outcomeLabel.setText("You Win");
					btnSubmit.setVisible(false);
					btnStart.setVisible(true);
					textArea.setText("");
				} else {
					int correct;
					correct = getNumCorrect(text, answer);
					outcomeLabel.setText(correct + " Correct / " + numTries + " Tries Left");
				}
			} else {
				btnSubmit.setVisible(false);
				btnStart.setVisible(true);
				if (text.toUpperCase().equals(answer)) {
					outcomeLabel.setText("You Win");
					textArea.setText("");
				} else {
					outcomeLabel.setText("0 Tries Left, You Lose");
					textArea.setText("");
				}
			}
		}
	}
	
	private void setDifficulty() {
		if (diff1.isSelected()) {
			wLength = 4 + (int)Math.random();
			numWords = 5;
		} else if (diff2.isSelected()) {
			wLength = 6 + (int)(Math.random() * 2);
			numWords = 7;
		} else if (diff3.isSelected()) {
			wLength = 9 + (int)Math.random();
			numWords = 10;
		} else if (diff4.isSelected()) {
			wLength = 11 + (int)Math.random();
			numWords = 13;
		} else if (diff5.isSelected()) {
			wLength = 13 + (int)(Math.random() * 2);
			numWords = 15;
		} else { 
			wLength = 4 + (int)Math.random();
			numWords = 5;
		}
		System.out.println("wLength:" + wLength + " numWords:" + numWords);
	}
	
	private ArrayList<String> getWords() {
		File file = new File("d:/workspace/MasterMind/src/MasterMind/enable1.txt");
		ArrayList<String> list1 = new ArrayList<String>();
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				list1.add(line);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return list1;
	}

	private String getRandWord() {
		Random random = new Random();
		String newWord = "";
		do {
			int i = random.nextInt(dictionary.size());
			newWord = dictionary.get(i);
		} while (newWord.length() != wLength);
		return newWord.toUpperCase();
	}

	private String getAnswer(ArrayList<String> wordList) {
		String[] string = wordList.toArray(new String[wordList.size()]);
		Random random = new Random();
		return string[random.nextInt(string.length)];
	}
	
	private int getNumCorrect(String inputText, String theAnswer) {
		int numCorrect = 0;
		System.out.println("I Made it");
			for (int i = 0; i < inputText.length(); i++) {
				if (inputText.toUpperCase().charAt(i) == theAnswer.charAt(i)) {
					numCorrect++;
					System.out.println("Made It");
				}
			}
		return numCorrect;
	}
	
}