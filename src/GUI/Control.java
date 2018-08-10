package GUI;

import javax.swing.JButton;

public class Control {

	int r;
	int c;
	public JButton button = new JButton();

	public Control(int i, int j, JButton button) {
		this.r = i;
		this.c = j;
		this.button = button;
	}
	int getRow(){
		return r ; 
	}
	int getCol(){
		return c ; 
	}
}
