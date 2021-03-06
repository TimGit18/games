package de.domain.games.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTextArea;

import de.domain.games.model.EndgameModel;

public class EndgameActionListener implements ActionListener {
	
	private static JTextArea activeTextArea = null; 	
	private static String activeAccessPath = null;
	private static final String EMPTY_STRING = "";
	private static List<String> endgameList = null;
	
	public EndgameActionListener(JTextArea textArea, String accessPath) {
		activeTextArea = textArea;
		activeAccessPath = accessPath;
	}
	
  public void actionPerformed(ActionEvent event) {
  	activeTextArea.setText(EMPTY_STRING);
  	activeTextArea.append("Aktueller Zugriffspfad: " + activeAccessPath + "\n");
   	endgameList = EndgameModel.getEndgameList(activeAccessPath);
   	EndgameModel.processEndgames(activeAccessPath);
   	for (String year : endgameList) {
  		activeTextArea.append(year + "\n");
  	}
  }
}