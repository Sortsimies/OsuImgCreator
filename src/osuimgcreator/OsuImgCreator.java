package osuimgcreator;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

public class OsuImgCreator {

	private JFrame frame;
	private String path;
	private JLabel label;
	private Image orig;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
				try {
					OsuImgCreator window = new OsuImgCreator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public OsuImgCreator() {
		initialize();
		path = "";
		orig = null;
		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 300, 90);
		frame.setLocationRelativeTo(null);
		frame.setTitle("OsuImgCreator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnChooseAnImage = new JButton("Choose an Image");
		btnChooseAnImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
//				fd.setDirectory("C:\\");
//				fd.setFile("*.xml");
				fd.setVisible(true);
				String filename = fd.getFile();
				
				if (filename == null){
					emptyImage();
				}
				else{
					File[] select = fd.getFiles();
					if(select.length>1){
						JOptionPane.showMessageDialog(frame,
							    "Only one image plox",
							    "Error",
							    JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						orig = ImageIO.read(select[0]);
						label.setText(path);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frame,
							    "Failed to read image",
							    "Error",
							    JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		
		label = new JLabel(" ");
		
		JButton btnSplit = new JButton("SPLIT");
		btnSplit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(orig==null){
					JOptionPane.showMessageDialog(frame,
						    "No image loaded",
						    "Error",
						    JOptionPane.PLAIN_MESSAGE);
					return;
				}
				long time = System.currentTimeMillis();
				File directory = new File(""+time);
				try{
					directory.mkdir();
				} catch (Exception e) {
					e.printStackTrace();
				}
				int x = 0, y = 0, w = 93, h = 86;
				BufferedImage bi1 = new BufferedImage(93,86,BufferedImage.TYPE_INT_ARGB);
				bi1.getGraphics().drawImage(orig, 0, 0, w, h, x, y, x+w, y+h, null);
				try {ImageIO.write(bi1, "png", new File(time+"\\"+"selection-mode.png"));} catch (IOException e) {e.printStackTrace();}
				
				x = 93;
				w = 77;
				BufferedImage bi2 = new BufferedImage(77,86,BufferedImage.TYPE_INT_ARGB);
				bi2.getGraphics().drawImage(orig, 0, 0, w, h, x, y, x + w, y + h, null);
				try {ImageIO.write(bi2, "png", new File(time+"\\"+"selection-mods.png"));} catch (IOException e) {e.printStackTrace();}
				
				x = 170;
				bi2.getGraphics().drawImage(orig, 0, 0, w, h, x, y, x + w, y + h, null);
				try {ImageIO.write(bi2, "png", new File(time+"\\"+"selection-random.png"));} catch (IOException e) {e.printStackTrace();}
				
				x = 247;
				bi2.getGraphics().drawImage(orig, 0, 0, w, h, x, y, x + w, y + h, null);
				try {ImageIO.write(bi2, "png", new File(time+"\\"+"selection-options.png"));} catch (IOException e) {e.printStackTrace();}
				
				String path = OsuImgCreator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				try {
					String decodedPath = URLDecoder.decode(path, "UTF-8");
					Desktop.getDesktop().open(new File(decodedPath+"\\..\\"+time));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame,
						    ""+e.getMessage(),
						    "Error",
						    JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		JLabel lblNewLabel = new JLabel("324*86");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnChooseAnImage)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
							.addComponent(btnSplit)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnChooseAnImage)
						.addComponent(btnSplit)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private void emptyImage(){
		path = "";
		orig = null;
		label.setText(" ");
	}
}
