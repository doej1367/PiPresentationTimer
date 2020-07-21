package me.timer;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptionsWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_m;
	private JTextField textField_s;
	private JTextField textField_h;

	public OptionsWindow(PiePresentationTimer piePresentationTimer) {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 164);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 555, 30);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 36, 0, 86, 6, 86, 16, 86, 16, 0, 0 };
		gbl_panel.rowHeights = new int[] { 20, 20, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblTimer_1 = new JLabel("Timer:");
		GridBagConstraints gbc_lblTimer_1 = new GridBagConstraints();
		gbc_lblTimer_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimer_1.anchor = GridBagConstraints.EAST;
		gbc_lblTimer_1.gridx = 1;
		gbc_lblTimer_1.gridy = 1;
		panel.add(lblTimer_1, gbc_lblTimer_1);

		textField_h = new JTextField();
		textField_h.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_h.setText("0");
		GridBagConstraints gbc_textField_h = new GridBagConstraints();
		gbc_textField_h.fill = GridBagConstraints.BOTH;
		gbc_textField_h.insets = new Insets(0, 0, 5, 5);
		gbc_textField_h.gridx = 2;
		gbc_textField_h.gridy = 1;
		panel.add(textField_h, gbc_textField_h);
		textField_h.setColumns(10);

		JLabel lblH = new JLabel("h");
		GridBagConstraints gbc_lblH = new GridBagConstraints();
		gbc_lblH.fill = GridBagConstraints.BOTH;
		gbc_lblH.insets = new Insets(0, 0, 5, 5);
		gbc_lblH.gridx = 3;
		gbc_lblH.gridy = 1;
		panel.add(lblH, gbc_lblH);

		textField_m = new JTextField();
		textField_m.setText("0");
		textField_m.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_textField_m = new GridBagConstraints();
		gbc_textField_m.fill = GridBagConstraints.BOTH;
		gbc_textField_m.insets = new Insets(0, 0, 5, 5);
		gbc_textField_m.gridx = 4;
		gbc_textField_m.gridy = 1;
		panel.add(textField_m, gbc_textField_m);
		textField_m.setColumns(10);

		JLabel lblMin = new JLabel("min");
		GridBagConstraints gbc_lblMin = new GridBagConstraints();
		gbc_lblMin.anchor = GridBagConstraints.WEST;
		gbc_lblMin.insets = new Insets(0, 0, 5, 5);
		gbc_lblMin.gridx = 5;
		gbc_lblMin.gridy = 1;
		panel.add(lblMin, gbc_lblMin);

		textField_s = new JTextField();
		textField_s.setText("60");
		textField_s.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_textField_s = new GridBagConstraints();
		gbc_textField_s.fill = GridBagConstraints.BOTH;
		gbc_textField_s.insets = new Insets(0, 0, 5, 5);
		gbc_textField_s.gridx = 6;
		gbc_textField_s.gridy = 1;
		panel.add(textField_s, gbc_textField_s);
		textField_s.setColumns(10);

		JLabel lblSec = new JLabel("sec");
		GridBagConstraints gbc_lblSec = new GridBagConstraints();
		gbc_lblSec.insets = new Insets(0, 0, 5, 5);
		gbc_lblSec.anchor = GridBagConstraints.WEST;
		gbc_lblSec.gridx = 7;
		gbc_lblSec.gridy = 1;
		panel.add(lblSec, gbc_lblSec);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				piePresentationTimer.setTimerDuration(getTimerDuration());
			}
		});
		contentPane.add(btnApply, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_1, BorderLayout.NORTH);

		JButton btnCloseApplication = new JButton("Close Application");
		btnCloseApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCloseApplication.setHorizontalAlignment(SwingConstants.LEADING);
		panel_1.add(btnCloseApplication);
	}

	public long getTimerDuration() {
		try {
			return Long.parseLong(textField_h.getText()) * 3600 + Long.parseLong(textField_m.getText()) * 60
					+ Long.parseLong(textField_s.getText());
		} catch (NumberFormatException ex) {
			return 60;
		}
	}

}
