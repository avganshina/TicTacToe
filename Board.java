import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")

public class Board extends JFrame
{ 
	
    private JPanel canvas;
    private JMenuBar menuBar;
	public Symbol[][] board; // either o or x or null
	public Symbol player; // either o or x or null
	public GameStatus status; // on / nonwin / xwin / owin
	
	public enum GameStatus { // to know when to end the game
		on, nonwin, xwin, owin
	}
	
	public enum Symbol {
		None, x, o
	}

    
	private void drawGrid(Graphics2D g) {
		g.setColor(Color.BLACK);
		Font font = new Font("Verdana", Font.BOLD, 40);
		g.setFont(font);
		
		for (int i = 0; i < 4; i++) {	         
        	g.drawLine(i * 100, 0, i * 100, 300);
         }
         
         for (int j = 0; j < 4; j++) {	         
	        	g.drawLine(0, j * 100, 300, j * 100);
	         }
         
         for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	               int x = j * 100 + 5;
	               int y = i * 100 + 5;
	               if (board[i][j] == Symbol.x) { // 
	                  
	            	  g.setColor(Color.RED);
	                  g.fillRect(x, y, 90, 90);
	                  g.setColor(Color.BLACK);
	                  g.setFont(new Font("Microsoft YaHei", Font.PLAIN, 120));
	                  g.drawString("X", x+5, y+90);
	                 

	               } else if (board[i][j] == Symbol.o) {
	            	   g.setColor(Color.BLUE);
		               g.fillRect(x, y, 90, 90);
		               g.setColor(Color.BLACK);
		               g.setFont(new Font("Microsoft YaHei", Font.PLAIN, 120));
		               g.drawString("O", x-5, y+90);
	               }
	            }
	         }
         
          
         
    }
	
	private void createMenuBar() {
    	menuBar = new JMenuBar();
        JMenu menu = new JMenu("Actions");
        menuBar.add(menu);
        
        addToMenu(menu, "New Game", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newgame();
                repaint();
            }
        });
        
        this.setJMenuBar(menuBar);
    }
    
    private void addToMenu(JMenu menu, String title, ActionListener listener) {
    	JMenuItem menuItem = new JMenuItem(title);
    	menu.add(menuItem);
    	menuItem.addActionListener(listener);
    }
    
    public Board() {

        TicTacToe.configureBoard();
        
        setTitle("TicTacToe!");

        JFrame frame = this;
        
        canvas = new JPanel() {

            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
            
            public boolean isFocusable() {
                return true;
            }

			@Override
        	public void paint(Graphics graphics) {
        		Graphics2D g = (Graphics2D)graphics;
        		drawGrid(g);
        		setPreferredSize(new Dimension(300,300));
        		frame.pack();
        	}
        };
        
        
        this.addMouseListener(new MouseAdapter() {
			@Override
	         public void mouseClicked(MouseEvent e) {
				System.out.printf("Mouse cliked at (%d, %d)\n", e.getX(), e.getY());
				int row = (e.getY()-50)/100;
				int col = e.getX()/100;
				
				if (status == GameStatus.on) {
					if (board[row][col] == Symbol.None){
						board[row][col] = player;
						repaint();
						check(player, row, col); 
						if (player == Symbol.x)
		                	  player = Symbol.o;
		                else
		                	  player = Symbol.x;
					}
				} else {
					newgame();
				}
				repaint();
			}
		});
        
        this.getContentPane().add(canvas, BorderLayout.CENTER);
        this.setResizable(false);
        this.pack();
        this.setLocation(100,100);
        this.setFocusable(true);
        createMenuBar();
        
        board = new Symbol[3][3];
        
        repaint();
    }
    
    public void newgame() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j< 3; j++) {
				board[i][j] = Symbol.None;
			}
		}
		status = GameStatus.on; // this means that the game is in process
		player = Symbol.x;
	}
	
	public void check(Symbol ox, int r, int c) {
		if (gameover(ox, r, c) == 1) {
			if (ox == Symbol.x) {
				status = GameStatus.xwin;       
			}
			else
				status = GameStatus.owin;
		} else if(gameover(ox, r, c) == 2) {
			status = GameStatus.nonwin;
		}
		
		if (status == GameStatus.nonwin) {
      	  JOptionPane.showMessageDialog(this, "Nobody Won!","", JOptionPane.INFORMATION_MESSAGE);
      	  System.exit(0);
        } else if (status == GameStatus.xwin) {
      	  JOptionPane.showMessageDialog(this, "Red X Won!","", JOptionPane.INFORMATION_MESSAGE);
      	  System.exit(0);
        } else if (status== GameStatus.owin) {
      	  JOptionPane.showMessageDialog(this, "Blue O Won!","", JOptionPane.INFORMATION_MESSAGE);
      	  System.exit(0);
        }
	}
	
	public int gameover(Symbol ox, int r, int c) {
	      
		if ((board[0][0] == ox && board[1][1] == ox && board[2][2] == ox) ||
			(board[0][2] == ox && board[1][1] == ox && board[2][0] == ox) ||
	    	(board[r][0] == ox && board[r][1] == ox && board[r][2] == ox) ||
	    	(board[0][c] == ox && board[1][c] == ox && board[2][c] == ox)) 
	    	return 1;
		int count = 0;
		for (int i = 0; i < 3; i++) {
	         for (int j = 0; j < 3; j++) {
	            if (board[i][j] == Symbol.None) {
	               count++;
	            }
	         }
	      }
		if (count>0)
			return 0;
		else
			return 2;
	   } 
}
