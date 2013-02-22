package Filler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class gui extends JFrame {
	public gui() {
		initComponents();
	}

	private void button1MouseClicked(MouseEvent e) 
	{
		String s = comboBox1.getSelectedItem().toString();
		if (s.contains("Vial"))
		{
			LegacyFiller.EID = 229;
			LegacyFiller.FID = 227;
			LegacyFiller.CWID = 2;
		}
		if (s.contains("Jug"))
		{
			LegacyFiller.EID = 1935;
			LegacyFiller.FID = 1937;
			LegacyFiller.CWID = 6;
		}
		if (s.contains("Bucket"))
		{
			LegacyFiller.EID = 1925;
			LegacyFiller.FID = 1929;
			LegacyFiller.CWID = 10;
		}
		if (s.contains("Bowl"))
		{
			LegacyFiller.EID = 1921;
			LegacyFiller.FID = 1923;
			LegacyFiller.CWID = 14;
		}
		/*
		if (s.contains("Waterskin"))
		{
			LegacyFiller.EID = 229;
			LegacyFiller.FID = 227;
			LegacyFiller.CWID = 18;
		}
		*/
		LegacyFiller.GUIDone = true;
		this.setVisible(false);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Brandon Heaton
		label1 = new JLabel();
		comboBox1 = new JComboBox<>();
		label2 = new JLabel();
		button1 = new JButton();

		//======== this ========
		setTitle("LegacyFiller");
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Item:");
		label1.setFont(new Font("Verdana", Font.PLAIN, 11));
		contentPane.add(label1);
		label1.setBounds(new Rectangle(new Point(5, 10), label1.getPreferredSize()));

		//---- comboBox1 ----
		comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
			"Vial",
			"Jug",
			"Bucket",
			"Bowl",
			"Waterskin"
		}));
		contentPane.add(comboBox1);
		comboBox1.setBounds(45, 5, 75, comboBox1.getPreferredSize().height);

		//---- label2 ----
		label2.setText("AnarchistRage");
		label2.setFont(new Font("Verdana", Font.BOLD, 11));
		contentPane.add(label2);
		label2.setBounds(new Rectangle(new Point(0, 30), label2.getPreferredSize()));

		//---- button1 ----
		button1.setText("Start");
		button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				button1MouseClicked(e);
			}
		});
		contentPane.add(button1);
		button1.setBounds(145, 5, 70, button1.getPreferredSize().height);

		{ // compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Brandon Heaton
	private JLabel label1;
	private JComboBox<String> comboBox1;
	private JLabel label2;
	private JButton button1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
