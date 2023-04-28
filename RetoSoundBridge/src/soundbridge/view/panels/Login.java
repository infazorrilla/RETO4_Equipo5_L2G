package soundbridge.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import soundbridge.controller.Controller;
import soundbridge.database.pojos.Client;
import soundbridge.database.pojos.Employee;
import soundbridge.view.components.TextPrompt;
import soundbridge.view.factory.PanelFactory;

public class Login extends JPanel {

	private static final long serialVersionUID = -3238378133872295962L;
	private Controller controller;
	private Client client;
	private Employee employee;

	public Login(JFrame frame) {
		controller = new Controller();

		setBounds(0, 0, 1000, 672);
		setLayout(null);
		setBackground(Color.black);

		JTextField textFieldUserLogIn = new JTextField();
		textFieldUserLogIn.setBounds(700, 200, 200, 50);
		add(textFieldUserLogIn);
		textFieldUserLogIn.setColumns(10);
		textFieldUserLogIn.setBackground(new Color(0, 0, 0, 0));
		textFieldUserLogIn.setForeground(Color.white);
		textFieldUserLogIn.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		textFieldUserLogIn.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldUserLogIn.setBorder(new LineBorder(Color.WHITE, 2));
		textFieldUserLogIn.setCaretColor(Color.WHITE);
		textFieldUserLogIn.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldUserLogIn.setBorder(new LineBorder(new Color(244, 135, 244), 2));
			}

			@Override
			public void focusLost(FocusEvent e) {
				textFieldUserLogIn.setBorder(new LineBorder(Color.WHITE, 2));
			}
		});

		JPasswordField passwordFieldLogIn = new JPasswordField();
		passwordFieldLogIn.setBounds(700, 280, 200, 50);
		passwordFieldLogIn.setHorizontalAlignment(SwingConstants.CENTER);
		passwordFieldLogIn.setBackground(new Color(0, 0, 0, 0));
		passwordFieldLogIn.setForeground(Color.white);
		passwordFieldLogIn.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		passwordFieldLogIn.setBorder(new LineBorder(Color.WHITE, 2));
		passwordFieldLogIn.setCaretColor(Color.WHITE);
		add(passwordFieldLogIn);
		passwordFieldLogIn.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordFieldLogIn.setBorder(new LineBorder(new Color(244, 135, 244), 2));
			}

			@Override
			public void focusLost(FocusEvent e) {
				passwordFieldLogIn.setBorder(new LineBorder(Color.WHITE, 2));
			}
		});
		passwordFieldLogIn.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					logIn(frame, textFieldUserLogIn, passwordFieldLogIn);
				}
			}
		});

		TextPrompt placeholderUser = new TextPrompt("Usuario", textFieldUserLogIn);
		placeholderUser.changeAlpha(0.8f);
		placeholderUser.changeStyle(Font.ITALIC);
		placeholderUser.setHorizontalAlignment(SwingConstants.CENTER);

		TextPrompt placeholderPasswd = new TextPrompt("Contraseña", passwordFieldLogIn);
		placeholderPasswd.changeAlpha(0.8f);
		placeholderPasswd.changeStyle(Font.ITALIC);
		placeholderPasswd.setHorizontalAlignment(SwingConstants.CENTER);

		JButton btnAcceptLogIn = new JButton("Iniciar sesión");
		btnAcceptLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIn(frame, textFieldUserLogIn, passwordFieldLogIn);
			}
		});
		btnAcceptLogIn.setBounds(700, 360, 200, 50);
		add(btnAcceptLogIn);
		btnAcceptLogIn.setForeground(Color.white);
		btnAcceptLogIn.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		btnAcceptLogIn.setBackground(new Color(244, 135, 244, 20));
		btnAcceptLogIn.setBorder(new LineBorder(new Color(244, 135, 244), 2));
		btnAcceptLogIn.setOpaque(true);

		JButton btnRegister = new JButton("REGISTRARME");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnRegister.setBounds(700, 450, 200, 50);
		add(btnRegister);
		btnRegister.setForeground(Color.black);
		btnRegister.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		btnRegister.setBackground(new Color(244, 135, 244));
		btnRegister.setBorder(new LineBorder(new Color(244, 135, 244), 2));
		btnRegister.setOpaque(true);

		JPanel panelBackground = new JPanel();
		panelBackground.setBounds(0, 0, 1000, 672);
		add(panelBackground);
		panelBackground.setLayout(new BorderLayout(0, 0));

		JLabel lblBackground = new JLabel("");
		panelBackground.add(lblBackground, BorderLayout.CENTER);

		addImage(panelBackground, lblBackground, "img/panel/login_bg.jpeg");
	}

	private void addImage(JPanel panel, JLabel label, String path) {
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
		icon.setImage(resizedImg);
		label.setIcon(icon);
	}

	private void logIn(JFrame frame, JTextField textFieldUserLogIn, JPasswordField passwordFieldLogIn) {
		String username = textFieldUserLogIn.getText();
		
		String typeOfUser = controller.checkTypeOfUser(textFieldUserLogIn, passwordFieldLogIn);
		controller.checkLogin(textFieldUserLogIn, passwordFieldLogIn);
		
		if (typeOfUser != null) {
			if (typeOfUser.equals("client")) {
				if (client == null) {
					client = new Client();
				}
				
				if (client != null) {
					setClient(controller.returnLoggedClient(username));
					frame.getContentPane().removeAll();
					frame.getContentPane().add(PanelFactory.getJPanel(PanelFactory.LIBRARY, frame, client));
					frame.revalidate();
					frame.repaint();
				}
			} else if (typeOfUser.equals("employee")) {
				setEmployee(controller.returnLoggedEmployee(textFieldUserLogIn.getText()));
				if (employee != null) {

				}
			}
		}
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
