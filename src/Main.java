import model.Model;
import view.*;
import view.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception e){System.err.println("look feel not set");}

        JFrame frame = new JFrame("Doodle");

        Model model = new Model();

        JMenuBar mb = new MenuBar(model);
        SlideView slideView= new SlideView(model);
        ToolView toolView = new ToolView(model);
        //GraphicView graphicView = new GraphicView(model);
        ScrollView scrollView = new ScrollView(model);
        //JScrollPane scrollPane=new JScrollPane(graphicView);
        JScrollPane scrollPane=new JScrollPane(scrollView);

        frame.setJMenuBar(mb);
        frame.setLayout(new BorderLayout());
        frame.add(slideView,BorderLayout.SOUTH);
        frame.add(toolView,BorderLayout.WEST);
        frame.add(scrollPane,BorderLayout.CENTER);

        scrollPane.getHorizontalScrollBar().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                frame.repaint();
            }
        });
        scrollPane.getVerticalScrollBar().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                frame.repaint();
            }
        });
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                //System.err.println("Main::scrollpane:"+scrollPane.getWidth()+" "+scrollPane.getHeight());
                model.setNewWidth(scrollPane.getWidth());
                model.setNewHeight(scrollPane.getHeight());
                model.setRatio();
            }
        });

        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
