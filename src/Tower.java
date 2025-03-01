/* Name: Nguyễn Huỳnh Minh Thông - ITITIU21321
 Purpose: This program purpose is to create a runable program and to test basic knowledge in DSA and OOP
*/
import java.awt.*;
import java.awt.geom.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.util.*;
// Main class for the Tower of Hanoi game
public class Tower extends JPanel implements MouseListener,MouseMotionListener{
	ImagePanel imagePanel= new ImagePanel();
	int moveCount=0;
	int numberOfpeg;
	Stack<Rectangle2D.Double> stack[]= new Stack[3];// Array of stacks to store the colors of the disks
	int disk[]={2,3,4,5,6,7,8,9,10,11,12,13,14};

	Stack <Color>colorOfDisk[]=new Stack[3];
	Rectangle2D.Double top=null;
	Color topColor= null;
	double ax,ay,height,width;
	boolean dragable=false;
	boolean begin=false;
	
	JLabel bestMove,move,noOfPeg;
	JLabel bestMovetxt,moveTxt,noOfPegTxt;

	// Buttons for game controls
	JButton level=new JButton(imagePanel.imagelevel);
	JButton back= new JButton(imagePanel.imageBack);
	JButton restart= new JButton(imagePanel.imageRestart);
	JButton rules = new JButton(imagePanel.imageHelp);
	JButton save = new JButton(imagePanel.imageOption);
	JButton exit = new JButton(imagePanel.imageexit);
	JLabel levno=new JLabel();
	JPanel Separator1= new JPanel();
	JPanel Separator2= new JPanel();
	JPanel Separator3= new JPanel();
	JPanel Separator4= new JPanel();
	
	JLabel poleA  = new JLabel("Pole A");
	JLabel poleB = new JLabel("Pole B");
	JLabel poleC = new JLabel("Pole C");

	// Constructor for the Tower class
	public Tower(int l){
		this.setLayout(null);
		bestMove = new JLabel("Best Move");
		move = new JLabel("Player Move");
		noOfPeg= new JLabel("Number of Disks");
		
		noOfPegTxt = new JLabel("0");
		bestMovetxt= new JLabel("0");
		moveTxt= new JLabel("0");
		
		Font labelFont = new Font("Arial",Font.PLAIN, 20);
		noOfPeg.setFont(labelFont);
		move.setFont(labelFont);
		bestMove.setFont(labelFont);
		
		Font resultFont = new Font("Arial",Font.BOLD,30);
		noOfPegTxt.setFont(resultFont);
		moveTxt.setFont(resultFont);
		bestMovetxt.setFont(resultFont);

		// Panel to display the number of disks
		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBounds(30, 120,250, 120);
		noOfPeg.setBounds(50,10,150,40);
		noOfPegTxt.setBounds(110,60,150,40);
		panel1.add(noOfPeg);
		panel1.add(noOfPegTxt);
		this.add(panel1);

		// Panel to display the best move
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBounds(325,120,250, 120);
		bestMove.setBounds(70,10,150,40);
		bestMovetxt.setBounds(110,60,150,40);
		panel2.add(bestMovetxt);
		panel2.add(bestMove);
		this.add( panel2);

		// Panel to display the player's move
		JPanel panel3 = new JPanel();
		panel3.setLayout(null);
		panel3.setBounds(620,120,250, 120);
		move.setBounds(70,10,150,40);
		moveTxt.setBounds(110,60,150,40);
		panel3.add(move);
		panel3.add(moveTxt);
		this.add( panel3);

		level.setBounds(30,40,150, 45);
		level.setBorder(null);
		this.add(level);
		back.setBounds(539,40,65, 45);
		back.setBorder(null);
		this.add(back);
		restart.setBounds(606,40,65, 45);
		restart.setBorder(null);
		this.add(restart);
		rules.setBounds(673,40,65, 45);
		rules.setBorder(null);
		this.add(rules);
		save.setBounds(740,40,65, 45);
		save.setBorder(null);
		this.add(save);
		exit.setBounds(807,40,65, 45);
		exit.setBorder(null);
		this.add(exit);

		// Set font for level number label
		Font labelNo = new Font("Arial",Font.PLAIN, 25);
		levno.setForeground(Color.white);
		level.setLayout(null);
		levno.setBounds(110,12, 30, 20);
		levno.setFont(labelFont);
		level.add(levno);

		// Set bounds and add separators
		Separator1.setBounds(0,573,20, 17);
		this.add(Separator1);
		Separator1.setBackground(Color.GRAY);
		Separator1.setBorder(null);
		Separator2.setBounds(279,573,39, 17);
		this.add(Separator2);
		Separator2.setBackground(Color.GRAY);
		Separator2.setBorder(null);
		Separator3.setBounds(577,573,39, 17);
		this.add(Separator3);
		Separator3.setBackground(Color.GRAY);
		Separator3.setBorder(null);
		Separator4.setBounds(874,573,20, 17);
		this.add(Separator4);
		Separator4.setBackground(Color.GRAY);
		Separator4.setBorder(null);

		// Set font and bounds for pole labels
		Font pole = new Font("Arial",Font.BOLD,30);
		poleA.setForeground(Color.white);
		poleA.setBounds(105,600, 100, 30);
		poleA.setFont(pole);
		this.add(poleA);
		poleB.setForeground(Color.white);
		poleB.setBounds(405,600, 100, 30);
		poleB.setFont(pole);
		this.add(poleB);
		poleC.setForeground(Color.white);
		poleC.setBounds(705,600, 100, 30);
		poleC.setFont(pole);
		this.add(poleC);
		
		begin = true;// Set game start flag to true
		System.out.println("inside Tower: " + l);
		init(l);// Initialize the game with the given level
		this.addMouseListener(this);// Add mouse listener to the panel
		this.addMouseMotionListener(this);// Add mouse motion listener to the panel
		addTowerActionListener(); // Add action listeners to the buttons
	}

	// Method to add action listeners to the buttons
	public void addTowerActionListener(){
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameMain.buttonmusic();
				System.exit(0);
			}
		});
		back.addActionListener(new backPage1());
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameHold.sevisiblex();
				init(1);// Re-initialize the game with level 1
				moveCount=0;// Reset move count
				String str = Integer.toString(moveCount);
				moveTxt.setText(str);// Update move count display
				GameMain.music();
			}
		});
		
		restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameMain.buttonmusic();
				init(Integer.parseInt(levno.getText()));// Re-initialize the game with the current level
				moveCount=0;// Reset move count
				String str = Integer.toString(moveCount);
				moveTxt.setText(str);// Update move count display
			}
		});
		
		rules.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameMain.buttonmusic();
				System.out.println("Rules");
				HowToPlay newFrame = new HowToPlay();
			    newFrame.setTitle("How To Play");
			    newFrame.setSize(800,600);
			    newFrame.setLocationRelativeTo(null);
			    newFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			    newFrame.setVisible(true);	
			    newFrame.setResizable(false);
			}
		});

		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameMain.buttonmusic();
				System.out.println("saved");
				saveMethod();	
			}
		});
	}
	
	
	public static void music(){
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/move.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
		    clip.start();
	
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	public class ImagePanel extends JPanel{
		private ImageIcon imageIcon = new ImageIcon("images/untitle.jpg");
		private ImageIcon imagelevel = new ImageIcon("images/level.jpg");
		private ImageIcon imageBack = new ImageIcon("images/backgame.jpg");
		private ImageIcon imageRestart = new ImageIcon("images/restart.jpg");
		private ImageIcon imageHelp = new ImageIcon("images/helpgame.jpg");
		private ImageIcon imageexit = new ImageIcon("images/exitgame.jpg");
		private ImageIcon imageOption = new ImageIcon("images/optiongame.jpg");
		private Image image = imageIcon.getImage();
		protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	    }
	 }

	public void init(int level){
		stack[0]=new Stack<Rectangle2D.Double>();// Initialize stack for pole A
		stack[1]=new Stack<Rectangle2D.Double>();// Initialize stack for pole B
		stack[2]=new Stack<Rectangle2D.Double>();// Initialize stack for pole C
		moveCount=0;
		int noOfPeg=disk[level-1];// Get the number of disks for the given level
		levno.setText(Integer.toString(level));
		Color pegColor[]={Color.black,Color.red,Color.yellow,Color.cyan,Color.blue,Color.green,Color.orange,Color.pink,Color.MAGENTA,Color.lightGray};
		
		colorOfDisk[0]=new Stack<Color>();// Initialize color stack for pole A
		colorOfDisk[1]=new Stack<Color>();// Initialize color stack for pole B
		colorOfDisk[2]=new Stack<Color>();// Initialize color stack for pole C

		for(int i=0;i<noOfPeg;i++){
			Rectangle2D.Double rect=new Rectangle2D.Double();
			System.out.println("getWidth: " + getWidth());
			double x = getWidth()/6;	
			if(x==0)
				x=150;
			System.out.println("x: " + x);
			double wr= noOfPeg*25-20*i;
			rect.setFrame (x-wr/2,553-i*20,wr,20);
			stack[0].push(rect);// Push the disk onto pole A
			colorOfDisk[0].push(pegColor[i]);
		}
	
		top=null;
		topColor=null;
		ax=0.0;
		ay=0.0;
		height=0.0;
		width = 0.0;
		dragable= false;
		repaint();
		
		String str = Integer.toString(noOfPeg);
		noOfPegTxt.setText(str);
		
		int best= (int) (Math.pow(2,noOfPeg)-1);
		str = Integer.toString(best);
		bestMovetxt.setText(str);
		System.out.println("inside init: " + level);
		System.out.println("No of disk: " + noOfPeg);
	}
	
	public void saveMethod(){
		try{
			DataOutputStream output= new DataOutputStream(new FileOutputStream("images/input.txt"));
			output.writeInt(Integer.parseInt(levno.getText()));
			output.close();
			DataInputStream input =new DataInputStream(new FileInputStream("images/input.txt"));
			while (true) {
				System.out.println(input.readInt());
			}
			
		}
		catch(IOException ex){
			System.out.println("Error in Reading Data");
		}
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g1=(Graphics2D)g;
		g1.setColor(Color.GRAY);
		g1.fillRect(0,0, getWidth(),getHeight());

		int baseX = getWidth()/6;
		int baseY1 = getHeight()-10*40;
		int baseY2 = getHeight()-90;
		
		g1.setColor(Color.WHITE);
		g1.setStroke(new BasicStroke(15));
		
		g1.drawLine(baseX, baseY1, baseX, baseY2);
		g1.drawLine(3*baseX, baseY1, 3*baseX, baseY2);
		g1.drawLine(5*baseX, baseY1, 5*baseX, baseY2);
		
		g1.drawLine(0,baseY2,getWidth(),baseY2);
		g1.setStroke(new BasicStroke(1));
		g1.setColor(topColor);
		
		if(dragable==true && top!=null)
		      g1.fill(top);
		
		drawTower(g1,0);
		drawTower(g1,1);
		drawTower(g1,2);
	}
	
	private void drawTower(Graphics2D g,int n){
		if(stack[n].empty()==false){
			for(int i=0;i<stack[n].size();i++){
				g.setColor(colorOfDisk[n].get(i));
				g.fill(stack[n].get(i));
			}
		}
	}
	
	private int currentTower(Point p){
		Rectangle2D.Double rA= new Rectangle2D.Double();
		Rectangle2D.Double rB= new Rectangle2D.Double();
		Rectangle2D.Double rC= new Rectangle2D.Double();
		
		rA.setFrame(0,0,getWidth()/3,getHeight());
	    rB.setFrame(getWidth()/3,0,getWidth()/3,getHeight());
	    rC.setFrame(2*getWidth()/3,0,getWidth()/3,getHeight());
	    
	    if(rA.contains(p))
	    	return 0;
	    else if(rB.contains(p))
	    	return 1;
	    else if(rC.contains(p))
	    	return 2;
	    else
	    	return -1;	
	}
	
	public void mousePressed(MouseEvent e){
		Point p= e.getPoint();
		//System.out.println(p.getY());
		int n= currentTower(p);
		if(!stack[n].empty()){
			top=stack[n].peek();
			if(top.contains(p)){
				top=stack[n].pop();// Remove the top disk from the stack
				topColor=colorOfDisk[n].pop();// Get the color of the top disk
				ax=top.getX();// Store the initial X position of the disk
				ay=top.getY();// Store the initial Y position of the disk
				width=p.getX()-ax;
				dragable=true;// Set the draggable flag to true
			}
			else{
				top=null;
				topColor=Color.BLACK;
				dragable=false;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent ev) {
		// TODO Auto-generated method stub
		int cx=ev.getX();
	    int cy=ev.getY();
	    if(top!=null && dragable==true){
	    	top.setFrame(cx-width,cy-height,top.getWidth(),top.getHeight());
	        repaint();
	     }
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void mouseReleased(MouseEvent ev){
		if(top!=null && dragable==true){// Check if there's a disk being dragged
			int tower=currentTower(ev.getPoint());
			double x,y;
			if(!stack[tower].empty()){// If the tower is not empty
				if(stack[tower].peek().getWidth()>top.getWidth()){// If the top disk on the tower is larger than the disk being dragged
					y=stack[tower].peek().getY()-20;// Calculate the new Y position for the disk
					moveCount++;
					System.out.println(moveCount);
					String str = Integer.toString(moveCount);
					moveTxt.setText(str);// Update the move count text field
					music();
				}
				else{
					JOptionPane.showMessageDialog(this,"Wrong Move","Tower Of Hanoi",JOptionPane.ERROR_MESSAGE);
					tower=currentTower(new Point((int)ax,(int)ay)); // Reset the tower to the previous position
					if(!stack[tower].empty())
						y=stack[tower].peek().getY()-20; 
					else
						y=getHeight()-118;// If the previous tower is empty, set the Y position to the bottom
               }
			}
			else{// If the tower is empty
				y=getHeight()-118;
				moveCount++;
				System.out.println(moveCount);
				String str = Integer.toString(moveCount); // Convert move count to string
				moveTxt.setText(str); // Update the move count text field
				music();
			}
			x=(int)(getWidth()/6+(getWidth()/3)*tower-top.getWidth()/2);// Calculate the new X position for the disk
			top.setFrame(x,y,top.getWidth(),top.getHeight()); // Set the new position of the disk
			stack[tower].push(top);// Push the disk onto the stack
			colorOfDisk[tower].push(topColor);// Push the disk color onto the color stack
			
			top=null;
			topColor=Color.black;
			dragable = false;
			repaint();
		}
		
		if(stack[0].empty() && stack[1].empty()){// Check if the first two towers are empty
			int best = Integer.parseInt(bestMovetxt.getText());// Get the best move count
			int playerMove = Integer.parseInt(moveTxt.getText());// Get the player's move count
			System.out.println("Best: " + best);
			System.out.println("Player Move: " + playerMove);
			if(best==playerMove){// If the player matched the best move count
				int l = Integer.parseInt(levno.getText()); // Get the current level number
				GameMain.buttonsuccess();// Call the success button method
				String msg = "You Successfully Complete this level\nNext Level: " + (l+1);
				JOptionPane.showMessageDialog(this,msg,"Tower Of Hanoi",JOptionPane.INFORMATION_MESSAGE);
				
				System.out.println("Level: " + l);
				l++;
				levno.setText(Integer.toString(l));// Update the level number text field
				init(l);
				moveTxt.setText("0"); // Reset the move count text field
			}
			else{// If the player did not match the best move count
				int extra=playerMove-best;// Calculate the extra moves
				GameMain.buttonfail(); // Call the fail button method
				String msg="Failed !! You Took "+ extra + " Extra Moves";
				JOptionPane.showMessageDialog(this,msg,"Tower Of Hanoi",JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(this,"Try Again!!!","Tower Of Hanoi",JOptionPane.INFORMATION_MESSAGE);
				init(Integer.parseInt(levno.getText()));
				moveCount=0;
				String str = Integer.toString(moveCount);// Convert move count to string
				moveTxt.setText(str);// Update the move count text field
			}
		}
		
		if(Integer.parseInt(levno.getText())>=10){// Check if the current level is 10 or higher
			JOptionPane.showMessageDialog(this,"Congratulation !! You Complete all the level!!! ","Tower of Hanoi",JOptionPane.INFORMATION_MESSAGE); // Show the completion message
			//GameHold.sevisiblex();
			init(1);// Initialize the first level
			moveCount=0;
			String str = Integer.toString(moveCount); // Convert move count to string
			moveTxt.setText(str);// Update the move count text field
			
		}
    }
}