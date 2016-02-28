package view;

import model.IView;
import model.Model;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Dexter on 2/19/2016.
 */
public class ToolView extends JToolBar implements IView{
    private Model model;
    private Box vertBox = Box.createVerticalBox();
    private JPanel colorBox = new JPanel();
    private JButton colorBlack = new JButton();
    private JButton colorRed = new JButton();
    private JButton colorGreen = new JButton();
    private JButton colorYellow = new JButton();
    private JButton colorMagenta = new JButton();
    private JButton colorBlue = new JButton();
    private JPanel ccPanel = new JPanel();
    private JColorChooser colorChooser = new JColorChooser(Color.BLACK);
    private Color newColor;


    public ToolView(Model model){
        super();
        this.model=model;
        this.layoutView();
        this.registerControllers();
        this.model.addView(this);
    }

    private void layoutView(){
        this.setLayout(new BorderLayout());
        this.setBackground(Color.darkGray);
        setColorBox();
        setToolBox();
        this.add(toolBox,BorderLayout.NORTH);

        vertBox.add(colorBox);
        vertBox.add(new JPanel());
        vertBox.add(ccPanel);
        ccPanel.setBackground(model.getCurcolor());
        ccPanel.setPreferredSize(new Dimension(80,40));
        ccPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        ccPanel.setBorder(BorderFactory.createMatteBorder(5,2,5,2,Color.white));
        this.add(vertBox,BorderLayout.SOUTH);
    }

    private JSlider sizeSlider = new JSlider(0,5);
    private SizeDisplay sizeDisplay = new SizeDisplay();
    private Box toolBox = Box.createVerticalBox();

    private void setToolBox(){
        toolBox.add(sizeDisplay);
        sizeSlider.setValue(0);
        sizeSlider.setMajorTickSpacing(1);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPreferredSize(new Dimension(60,20));
        sizeSlider.setBorder(BorderFactory.createLineBorder(Color.black));
        sizeSlider.setFocusable(false);
        toolBox.add(sizeSlider);

    }

    @Override
    public void updateView() {
        sizeDisplay.repaint();
        ccPanel.setBackground(model.getCurcolor());
    }

    private class SizeDisplay extends JPanel{
        public SizeDisplay(){
            this.setPreferredSize(new Dimension(this.getPreferredSize().width,60));
        }
        public void paintComponent(Graphics g){
            Graphics2D g2d =(Graphics2D)g;
            g2d.setColor(Color.white);
            g2d.fillRect(0,0,this.getWidth(),this.getHeight());
            g2d.setStroke(model.getStyle());
            g2d.setColor(model.getCurcolor());
            g2d.drawLine(0,0,this.getWidth(),this.getHeight());
        }

    }

    private void setColorBox(){
        colorBox.setLayout(new GridLayout(0,2));
        colorBox.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        colorBox.add(colorBlack);
        colorBlack.setBackground(Color.black);
        colorBlack.setContentAreaFilled(false);
        colorBlack.setOpaque(true);
        colorBlack.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.gray));
        colorBox.add(colorMagenta);
        colorMagenta.setBackground(Color.MAGENTA);
        colorMagenta.setContentAreaFilled(false);
        colorMagenta.setOpaque(true);
        colorMagenta.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.gray));
        colorBox.add(colorRed);
        colorRed.setBackground(Color.RED);
        colorRed.setContentAreaFilled(false);
        colorRed.setOpaque(true);
        colorRed.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.gray));
        colorBox.add(colorGreen);
        colorGreen.setBackground(Color.green);
        colorGreen.setContentAreaFilled(false);
        colorGreen.setOpaque(true);
        colorGreen.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.gray));
        colorBox.add(colorYellow);
        colorYellow.setBackground(Color.yellow);
        colorYellow.setContentAreaFilled(false);
        colorYellow.setOpaque(true);
        colorYellow.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.gray));
        colorBox.add(colorBlue);
        colorBlue.setBackground(Color.BLUE);
        colorBlue.setContentAreaFilled(false);
        colorBlue.setOpaque(true);
        colorBlue.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.gray));
        for(Component btn: colorBox.getComponents()){
            btn.setPreferredSize(new Dimension(30,35));
        }
    }

    private void registerControllers(){
        this.ccPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e) {
                newColor = JColorChooser.showDialog(colorChooser,"Choose Color", Color.BLACK);
                if (newColor != null){
                    ccPanel.setBackground(newColor);
                    model.setCurcolor(newColor);
                }
            }
        });
        this.colorBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurcolor(Color.BLUE);
            }
        });
        this.colorBlack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurcolor(Color.BLACK);
            }
        });
        this.colorRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurcolor(Color.red);
            }
        });
        this.colorGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurcolor(Color.green);
            }
        });
        this.colorMagenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurcolor(Color.MAGENTA);
            }
        });
        this.colorYellow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurcolor(Color.yellow);
            }
        });
        this.sizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source=(JSlider)e.getSource();
                model.setStrokeWidth(source.getValue()*2);
                model.setStyle(new BasicStroke(model.getStrokeWidth(),BasicStroke.JOIN_ROUND,BasicStroke.CAP_ROUND));
            }
        });
    }

}
