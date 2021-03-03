package source;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class TileButton extends javax.swing.JButton {
	private static final long serialVersionUID = 1L;

	int id;

	public TileButton(int id) {
		super(new ImageIcon(new ImageIcon("src/RainbowIcon.jpg").getImage().getScaledInstance(Main.winSize / Main.size, Main.winSize / Main.size, Image.SCALE_DEFAULT)));
		this.id = id;
		this.setSize(Main.winSize / Main.size, Main.winSize / Main.size);
		this.setLocation((id % Main.size) * (Main.winSize / Main.size), (id / Main.size) * (Main.winSize / Main.size));
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPressed();
			}
		});
		//this.setVisible(true);
		
	}

	private void buttonPressed() {
		System.out.println("Button " + id + " pressed.");
		try {
			Main.buttonPressed(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	
	protected void setImageState(int state) {
		//0 = Blank, 1 = X, 2 = O
		if(state == 0)
			this.setIcon(new ImageIcon(new ImageIcon("src/RainbowIcon.jpg").getImage().getScaledInstance(Main.winSize / Main.size, Main.winSize / Main.size, Image.SCALE_DEFAULT)));
		else if(state == 1)
			this.setIcon(new ImageIcon(new ImageIcon("src/RainbowIconX.jpg").getImage().getScaledInstance(Main.winSize / Main.size, Main.winSize / Main.size, Image.SCALE_DEFAULT)));
		else if(state == 2)
			this.setIcon(new ImageIcon(new ImageIcon("src/RainbowIconO.jpg").getImage().getScaledInstance(Main.winSize / Main.size, Main.winSize / Main.size, Image.SCALE_DEFAULT)));
		else
			System.out.println("Requested state " + state + " for button " + id + "out of bounds.");
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	}
	
}

