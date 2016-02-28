package view;

import model.IView;
import model.Model;
import Paint.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Dexter on 2/18/2016.
 */
public class MenuBar extends JMenuBar implements IView{

    private Model model;
    private JMenu file = new JMenu("File");
    private JMenuItem save = new JMenuItem("Save");
    private JMenuItem load = new JMenuItem("Load");
    private JMenuItem quit = new JMenuItem("Quit");
    private JMenu view = new JMenu("View");
    private JMenuItem full = new JMenuItem("Full");
    private JMenuItem fit = new JMenuItem("Fit");

    public MenuBar(Model model){
        super();
        this.model=model;
        file.add(save);
        file.add(load);
        file.addSeparator();
        file.add(quit);
        view.add(full);
        view.add(fit);
        this.add(file);
        this.add(view);
        this.registerController();
        this.model.addView(this);
    }
    private void registerController(){

        //handle menu
        this.file.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {

            }

            @Override
            public void menuDeselected(MenuEvent e) {
                model.menuflag=true;
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                model.menuflag=true;
            }
        });
        this.view.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {

            }

            @Override
            public void menuDeselected(MenuEvent e) {
                model.menuflag=true;
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                model.menuflag=true;
            }
        });
        this.full.addActionListener(e->{
            model.setSize(1);
        });
        this.fit.addActionListener(e->{
            model.setSize(0);
        });
        this.save.addActionListener(e-> {
            try{
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Text file","txt"));
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Binary file","bin"));
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                fc.setAcceptAllFileFilterUsed(true);
                int ret = fc.showSaveDialog(this);
                if(ret== JFileChooser.APPROVE_OPTION){
                    model.setDirty(false);
                    String extention =fc.getFileFilter().getDescription();
                    File file = fc.getSelectedFile();
                    FileOutputStream out;
                    ObjectOutputStream oos;
                    String ext="";
                    Boolean hasext=false;
                    if(!extention.equals("Text file")&&!extention.equals("Binary file")){
                        String name=file.getName();
                        try {
                            ext = name.substring(name.lastIndexOf("."));
                            hasext=true;
                        }catch(StringIndexOutOfBoundsException ex){
                            ext=".bin";
                        }
                        //name.substring(0,name.lastIndexOf("."));
                        if(ext.equals(".bin"))
                            extention="Binary file";
                        if(ext.equals(".txt"))
                            extention="Text file";
                    }

                    if(extention.equals("Binary file")) {
                        ext = ".bin";
                        if(hasext){
                            out=new FileOutputStream(file);
                        }else{
                            out= new FileOutputStream(file+ext);
                        }
                        oos=new ObjectOutputStream(out);
                        oos.writeObject(model.elemList);
                        oos.close();
                        out.close();
                    }
                    if(extention.equals("Text file")) {
                        ext = ".txt";
                        FileWriter sf;
                        if(hasext) {
                            sf = new FileWriter(file);
                        }else {
                            sf = new FileWriter(file + ext);
                        }
                        for(Object line:model.elemList){
                            ArrayList<Elements> l=(ArrayList<Elements>)line;
                            sf.write("new"+"\n");
                            sf.write(l.get(0).strokeWidth+"\n");
                            sf.write(l.get(0).elemColor.getRGB()+"\n");
                            for(Object p: l){
                                Elements point = (Elements)p;
                                sf.write(point.p.x+","+point.p.y + "\n");
                            }
                        }
                        sf.close();
                    }
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        });
        this.quit.addActionListener(e->{
            System.exit(0);
            setFocusable(false);
        });
        this.load.addActionListener(e->{
            try {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Text file", "txt"));
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Binary file", "bin"));
                fc.setAcceptAllFileFilterUsed(true);
                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                int ret = fc.showOpenDialog(fc);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    String ext =fc.getFileFilter().getDescription();
                    File file = fc.getSelectedFile();
                    String name = file.getName();
                    ext=name.substring(name.lastIndexOf("."));
                    if(ext.equals(".bin"))
                        ext="Binary file";
                    if(ext.equals(".txt"))
                        ext="Text file";

                    if(ext.equals("Binary file")) {
                        FileInputStream in = new FileInputStream(file);
                        ObjectInputStream ois = new ObjectInputStream(in);
                        model.elemList=(ArrayList<ArrayList<Elements>>)ois.readObject();
                        in.close();
                        ois.close();
                    }
                    if(ext.equals("Text file")){
                        BufferedReader lf = new BufferedReader(new FileReader(file));
                        String line="";
                        ArrayList<Elements> eleLine=new ArrayList<Elements>();
                        Elements point=new Elements();
                        int width=1;
                        Color color=Color.WHITE;
                        model.elemList.clear();
                        while((line=lf.readLine())!=null){
                            if(line.equals("new")){
                                if(eleLine.size()!=0) {
                                    model.elemList.add(eleLine);
                                }
                                eleLine = new ArrayList<Elements>();
                                width= Integer.parseInt(lf.readLine());
                                color= new Color(Integer.parseInt(lf.readLine()));
                            }else{
                                String[] pt=line.split(",");
                                int x= Integer.parseInt(pt[0]);
                                int y= Integer.parseInt(pt[1]);
                                point = new Elements(new Point(x,y),color,width);
                                eleLine.add(point);
                            }
                        }
                        model.elemList.add(eleLine);
                        lf.close();
                    }
                }
                model.loadUpdate();
            }catch (IOException ex){
                ex.printStackTrace();
            }catch (ClassNotFoundException ex){
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void updateView() {
        if(model.getDirty()==true){
            save.setEnabled(true);
        }else{
            save.setEnabled(false);
        }
    }
}
