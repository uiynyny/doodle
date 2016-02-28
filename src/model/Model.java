package model;

import Paint.*;
import javax.swing.*;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Dexter on 2/18/2016.
 */
public class Model extends Object {
    public ArrayList<ArrayList<Elements>> elemList = new ArrayList<>();
    public ArrayList<ArrayList<Elements>> holder= new ArrayList<>();
    private ArrayList<IView> views = new ArrayList<>();
    private ArrayList<Elements> motion;
    private ArrayList<Elements> current;
    private int counter=0;
    public int maxCounter=0;
    private int size=1;
    private int strokeWidth=1;
    private BasicStroke style= new BasicStroke(strokeWidth,BasicStroke.JOIN_ROUND,BasicStroke.CAP_ROUND);
    private Color bgColor=Color.WHITE;
    private Color curcolor=Color.BLACK;
    private int defWidth=600;
    private int defHeight=400;
    private int newWidth=600;
    private int newHeight=400;
    public double hratio;
    public double vratio;
    public boolean menuflag;


    public void setRatio(){
        if(size==0) {
            hratio = (double) newWidth / (double) defWidth;
            vratio = (double) newHeight / (double) defHeight;
        }else{
            hratio=1;
            vratio=1;
        }
        updateAllViews();
    }
    public int getDefWidth(){
        return defWidth;
    }
    public int getDefHeight() {
        return defHeight;
    }

    public void setNewWidth(int w){
        newWidth=w;
    }
    public void setNewHeight(int h){
        newHeight=h;
    }
    public int getNewWidth() {
        return newWidth;
    }
    public int getNewHeight() {
        return newHeight;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        //System.err.println("size:"+size);
        this.size = size;
        setRatio();
        updateAllViews();
    }

    public Color getCurcolor() {
        return curcolor;
    }
    public void setCurcolor(Color curcolor) {
        this.curcolor = curcolor;
        updateAllViews();
    }
    public Color getBgColor(){return bgColor;}
    public int getCounter() {
        return counter;
    }
    public void setCounter(int counter) {
        this.counter=counter;
        holder.clear();
        for(int i=0;i<counter;i++){
            holder.add(elemList.get(i));
        }
        if(counter==0) {
            current = null;
        }else{
            current = holder.get(counter - 1);
        }
        updateAllViews();
    }
    public void incrementCounter(){
        counter++;
        maxCounter = counter;
        updateAllViews();
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setStyle(BasicStroke style) {
        this.style = style;
        updateAllViews();
    }
    public BasicStroke getStyle() {
        return style;
    }

    public Model(){}

    public void toolPressed(Point e){
        menuflag=false;
        motion = new ArrayList();
        if(size==0){
            e.x=(int)(e.x/hratio);
            e.y=(int)(e.y/vratio);
        }
        Elements ep = new Elements(e, curcolor, strokeWidth);
        holder.add(motion);
        motion.add(ep);
        updateAllViews();
    }
    public void toolReleased(Point e){
        if(!menuflag) {
            if(size==0){
                e.x=(int)(e.x/hratio);
                e.y=(int)(e.y/vratio);
            }
            motion.add(new Elements(e, curcolor, strokeWidth));

            //remove stroke on counter
            if (current == null) {
                elemList.clear();
            } else {
                for (int i = elemList.size() - 1; i > elemList.indexOf(current); i--) {
                    elemList.remove(i);
                }
            }
            elemList.add(motion);
            current = motion;
            incrementCounter();
            updateAllViews();
        }
    }
    public void toolDragged(Point e){
        if(!menuflag) {
            if(size==0){
                e.x=(int)(e.x/hratio);
                e.y=(int)(e.y/vratio);
            }
            Elements ep = new Elements(e, curcolor, strokeWidth);
            motion.add(ep);
            updateAllViews();
        }
    }
    public void reDraw(Graphics2D g2d){
        if(holder != null){
            for(int j=0;j<holder.size();j++){
                ArrayList<Elements> temp = holder.get(j);
                for(int i=1; i< temp.size(); i++){
                    g2d.setColor(temp.get(i).elemColor);
                    g2d.setStroke(new BasicStroke(temp.get(i).strokeWidth,BasicStroke.JOIN_ROUND,BasicStroke.CAP_ROUND));
                    g2d.drawLine((int)(temp.get(i - 1).p.x*hratio), (int)(temp.get(i - 1).p.y*vratio), (int)(temp.get(i).p.x*hratio), (int)(temp.get(i).p.y*vratio));
                }
            }
        }
    }

    Iterator lineIt;
    Iterator pointIt;
    Timer t;

    public void play(){

        holder.clear();
        lineIt = elemList.iterator();

        pointIt=((ArrayList<Elements>)lineIt.next()).iterator();
        motion = new ArrayList<>();
        ActionListener act = e -> {
            if(pointIt.hasNext()){
                motion.add((Elements) pointIt.next());
                holder.add(motion);
                this.updateAllViews();
            }else {
                t.stop();
                if(lineIt.hasNext()){
                    motion = new ArrayList<>();
                    pointIt=((ArrayList<Elements>)lineIt.next()).iterator();
                    t.start();
                }else{
                    this.updateAllViews();
                }
            }
;        };
        t= new Timer(1000/30,act);
        t.start();
    }


    public void addView(IView view){
        this.views.add(view);
        view.updateView();
    }

    public void loadUpdate(){
        maxCounter=elemList.size();
        setCounter(elemList.size());
    }


    private void updateAllViews(){
        for(IView view: this.views){
            view.updateView();
        }
    }
}
