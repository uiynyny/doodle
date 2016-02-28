package view;

import model.IView;
import model.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dexter on 2/18/2016.
 */
public class SlideView extends JPanel implements IView {
    private Model model;
    private JSlider timeSlider;
    private JButton play = new JButton("Play");
    private JButton start = new JButton("Start");
    private JButton end = new JButton("End");

    public SlideView(Model model){
        super();
        this.model = model;
        this.layoutView();
        this.registerControllers();
        this.model.addView(this);
    }

    private void layoutView(){
        SpringLayout layout=new SpringLayout();
        setLayout(layout);
        timeSlider = new JSlider(0,0);
        timeSlider.setMajorTickSpacing(5);
        timeSlider.setMinorTickSpacing(1);
        timeSlider.setPaintTicks(true);
        timeSlider.setPaintLabels(true);
        Dimension d=play.getPreferredSize();
        play.setPreferredSize(new Dimension(d.width,d.height));
        start.setPreferredSize(new Dimension(d.width+10,d.height));
        end.setPreferredSize(new Dimension(d.width,d.height));
        layout.putConstraint(SpringLayout.EAST,this,5,SpringLayout.EAST,end);
        layout.putConstraint(SpringLayout.WEST,end,5,SpringLayout.EAST,start);
        layout.putConstraint(SpringLayout.WEST,start,5,SpringLayout.EAST,timeSlider);
        layout.putConstraint(SpringLayout.WEST,timeSlider,5,SpringLayout.EAST,play);
        layout.putConstraint(SpringLayout.WEST,play,5,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.SOUTH,this,5,SpringLayout.SOUTH,end);
        layout.putConstraint(SpringLayout.SOUTH,this,5,SpringLayout.SOUTH,start);
        layout.putConstraint(SpringLayout.SOUTH,this,5,SpringLayout.SOUTH,play);
        layout.putConstraint(SpringLayout.SOUTH,this,5,SpringLayout.SOUTH,timeSlider);
        layout.putConstraint(SpringLayout.NORTH,play,5,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.NORTH,timeSlider,5,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.NORTH,start,5,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.NORTH,end,5,SpringLayout.NORTH,this);
        this.add(this.play);
        this.add(this.timeSlider);
        this.add(this.start);
        this.add(this.end);
    }

    private void registerControllers(){
        this.timeSlider.addChangeListener(e-> {
            if(((JSlider)e.getSource()).getValueIsAdjusting()) {
                model.setCounter(timeSlider.getValue());
            }
        });

        this.play.addActionListener(e-> {
            model.setPlayflag(true);
            model.play();
        });
        this.start.addActionListener(e-> {
            model.setCounter(0);
        });
        this.end.addActionListener(e->{
            model.setCounter(model.maxCounter);
        });
    }

    @Override
    public void updateView() {
        timeSlider.setMaximum(model.maxCounter);
        timeSlider.setValue(model.getCounter());
        play.setEnabled(model.maxCounter>0);
        start.setEnabled(model.getCounter()>0);
        end.setEnabled(model.getCounter()<model.maxCounter);
    }
}
