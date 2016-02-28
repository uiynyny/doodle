package view;

import model.IView;
import model.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by temp user on 27/02/16.
 */
public class ScrollView extends JPanel implements IView {
    private Model model;
    private FlowLayout flow;
    private SpringLayout spring;
    public ScrollView(Model model){
        super();
        this.model=model;
        this.setBackground(Color.darkGray);
        GraphicView graphicView = new GraphicView(model);
        flow=new FlowLayout(FlowLayout.LEFT,0,0);
        spring=new SpringLayout();
        this.setLayout(flow);
        //controller();
        this.add(graphicView);
        this.model.addView(this);
    }

//    private void controller(){
//        this.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                super.componentResized(e);
//                System.out.println("ScrollView::scroll: "+getWidth()+" "+getHeight());
//            }
//        });
//    }

    @Override
    public void updateView() {
        if(model.getSize()==0){
            this.setLayout(spring);
        }else{
            this.setLayout(flow);
        }
        this.validate();
    }
}
