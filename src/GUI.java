import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.jena.rdf.model.Model;

public class GUI extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private JTextField filename = new JTextField();
	private JTextField dir = new JTextField();
	private JTextField map = new JTextField();
	private String directory = null;
	private String mapdir = null;
	private String filepath = null;
	private String mappath = null;

  private JButton open = new JButton("Input");
  private JButton convert = new JButton("Convert");
  private JButton mapfile = new JButton("Mapping");

  public GUI() {
    JPanel p = new JPanel();
    open.addActionListener(new OpenL());
    p.add(open);
    mapfile.addActionListener(new MapL());
    p.add(mapfile);
    convert.addActionListener(new ConvertL());
    p.add(convert);
    Container cp = getContentPane();
    cp.add(p, BorderLayout.SOUTH);
    dir.setEditable(false);
    filename.setEditable(false);
    map.setEditable(false);
    p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    JLabel jlabel = new JLabel("<html><div style='text-align: center;'>" + "File" + "</div></html>");
    p.add(jlabel);
    jlabel.setFont(new Font("Verdana",1,10));
    p.add(filename);
    JLabel jlabel2 = new JLabel("<html><div style='text-align: center;'>" + "Mapping File" + "</div></html>");
    p.add(jlabel2);
    jlabel2.setFont(new Font("Verdana",1,10));
    p.add(map);
    cp.add(p, BorderLayout.NORTH);
    
  }

  class OpenL implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
    	JFileChooser c = new JFileChooser("D:\\");
    	c.setFileFilter(filter);
    	int rVal = c.showOpenDialog(GUI.this);
    	if (rVal == JFileChooser.APPROVE_OPTION) {
    		filename.setText(c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName());
    		filepath = c.getSelectedFile().getName();
    		directory = c.getCurrentDirectory().toString();
    	}
    	if (rVal == JFileChooser.CANCEL_OPTION) {
    		filename.setText("Cancelled");
    	}
    }
  }

  class MapL implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	    	FileNameExtensionFilter filter = new FileNameExtensionFilter("TTL Files (*.ttl)", "ttl");
	    	JFileChooser c = new JFileChooser("D:\\");
	    	c.setFileFilter(filter);
	      int rVal = c.showOpenDialog(GUI.this);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	        map.setText(c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName());
	        mappath = c.getSelectedFile().getName();
	        mapdir = c.getCurrentDirectory().toString();
	      }
	      if (rVal == JFileChooser.CANCEL_OPTION) {
	        filename.setText("Cancelled");
	        map.setText("");
	      }
	    }
	  }
  
  class ConvertL implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	if(directory == null || mapdir == null) {
    		JOptionPane.showMessageDialog(null, "Please choose all the required files!");
    		
    	}
    	
    	else {
    		FileWriter output = null;
    		FileWriter RDFoutput = null;
    		try {
    			output = new FileWriter(directory + "\\output.json");
    			RDFoutput = new FileWriter(directory + "\\rdfoutput.xml");
    		} catch (IOException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
    			JOptionPane.showMessageDialog(null, e2.getMessage());
    		}
		
    		String csv = directory + "\\" + filepath;
    		String mapping = mapdir + "\\" + mappath;
    		String namespace = "http://sis.hust.edu.vn/foaf/";
    		String encoding = "UTF-8";
    		char separator = ',';

    		Configurations cfg = new Configurations(separator, csv, mapping, namespace, encoding);
    		Model model = null;
    		try {
    			model = Converter.getInstance().convert(cfg);
    		} catch (Exception e1) {
    			System.out.println(e1.getMessage());
    			JOptionPane.showMessageDialog(null, e1.getMessage());
    			System.exit(2);
    		}
    		model.write(output, "JSON-LD");
    		model.write(RDFoutput, "RDF/XML");
    		
    		JOptionPane.showMessageDialog(null, "Converted successfully!" + "\n" + "Output file is stored at " + directory + "\\output.json"
    		+ "\n" + "From BKC Lab with Love <3");
    		try {
    			output.close();
    			RDFoutput.close();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    			JOptionPane.showMessageDialog(null, e1.getMessage());
    		}
    	}
    }
  }


  public static void run(JFrame frame, int width, int height) {
	  Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	  frame.setLocation(dim.width/3, dim.height/3);
	  frame.setTitle("CSV to RDF Converter");
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setSize(width, height);
	  frame.setVisible(true);
  }
} ///:~



           