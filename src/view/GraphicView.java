package view;

import model.IView;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Dexter on 2/22/2016.
 */
public class GraphicView extends JPanel implements IView {

    private Model model;
    public GraphicView(Model model){
        super();
        this.model=model;
        this.setLayout();
        this.registerController();
        this.model.addView(this);
    }

    private void setLayout(){
        this.setBackground(model.getBgColor());
        this.setPreferredSize(new Dimension(model.getDefWidth(),model.getDefHeight()));
    }

    private void registerController(){
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {
                model.toolPressed(e.getPoint());
            }
            public void mouseReleased(MouseEvent e) {
                model.toolReleased(e.getPoint());
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                model.toolDragged(e.getPoint());
            }
            public void mouseMoved(MouseEvent e) {}
        });
    }


    @Override
    public void paintComponent(Graphics g) {
        if(model.getSize()==0){
            this.setPreferredSize(new Dimension(model.getNewWidth(),model.getNewHeight()));
        }else{
            this.setPreferredSize(new Dimension(model.getDefWidth(),model.getDefHeight()));
        }
        this.validate();
        super.paintComponent(g);
        Graphics2D g2d= (Graphics2D) g;
        g2d.setColor(model.getBgColor());
        g2d.fillRect(0,0,getWidth(),getHeight());
        model.reDraw(g2d);
    }

    @Override
    public void updateView() {
        if(model.getSize()==0){
            this.setPreferredSize(new Dimension(model.getNewWidth(),model.getNewHeight()));
        }else{
            this.setPreferredSize(new Dimension(model.getDefWidth(),model.getDefHeight()));
        }
        this.validate();
        repaint();
    }
}
