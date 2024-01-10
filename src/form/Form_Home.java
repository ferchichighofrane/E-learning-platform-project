package form;

import dialog.Message;
import administrationlearning.dash;
import model.ModelCard;
import model.ModelStudent;
import model.database_Connection;
import model.matiereEntity;
import model.matiereService;
import model.userEntity;
import model.userService;
import icons.GoogleMaterialDesignIcons;
import icons.IconFontSwing;
import noticeboard.ModelNoticeBoard;
import swing.table.EventAction;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Form_Home extends javax.swing.JPanel {
    EventAction eventAction;
    int rangjava=0;
    int rangdata=0;
    int rangweb=0;
    int rangconcept=0;
    int partjava=0;
        int averageJava=0;
        int partconcept=0;
        int   averageconception=0;
        int partdata=0;
        int  averagedata=0;
        int partweb=0;
        int  averageweb=0;
    private static database_Connection instance;
    private userService userService;
    List<String> coursesnotAded;
    List<userEntity> list=new ArrayList<>();
    List<userEntity> listfromdatabase=new ArrayList<>();
    List<matiereEntity> notes=new ArrayList<>();
     matiereService matiereService;
     userEntity selectedStudent;
        public Form_Home() {
        initComponents();
        table1.fixTable(jScrollPane1);
        setOpaque(false);
       initCustomListeners();
        initData();
        noticeBroadcontent1.setVisible(false);
        imageProfilePanel.setPreferredSize(new Dimension(100,100));
         try {
             BufferedImage image = ImageIO.read(getClass().getResource("/icons/profile.jpg"));
            // Scale the image to fit in the panel
            Image scaledImage = image.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
          imageProfile.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
         firstcourse.setVisible(false);
        secondcourse.setVisible(false);
        thirdcourse.setVisible(false);
        forthcourse.setVisible(false);
        setVisible(true);
    }

    private void initData() {
        coursesnotAded =new ArrayList<>();
        userService=new userService();
        matiereService=new matiereService();
        try {
            list=userService.getAllUsers(); 
        } catch (SQLException ex) {
            Logger.getLogger(Form_Home.class.getName()).log(Level.SEVERE, null, ex);
        }
         list.forEach((o)->{
                try {
                    matiereService.getMatiereById(o.getId());
                } catch (SQLException ex) {
                    Logger.getLogger(Form_Home.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        try {
            notes=matiereService.getAllMatieres();
        } catch (SQLException ex) {
            Logger.getLogger(Form_Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        initCardData();
        //initNoticeBoard();
        initTableData();
    }
public void initCustomListeners(){
     search.setText("Search ...!");
        search.setForeground(new Color(186,197,172));
        search.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (search.getText().equals("Search ...!"))
                    search.setText("");
                search.setForeground(new Color(0,0,0));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (search.getText().equals("")) {
                    search.setText("Search ...!");
                    search.setForeground(new Color(186,197,172));
                }
                
            }
        } );
}
    private void initTableData()  {
        
        eventAction = new EventAction() {
            
            @Override
            public void delete(ModelStudent student) {
                int position=table1.getSelectedRow();
                if (position>=table1.getRowCount()) {
                    position=table1.getRowCount()-1;
                }
                table1.clearSelection();
                if (showMessage("Delete Student : " + list.get(position).getFirstName())) {
                    try {
                        System.out.println();
                        table1.deleteRow(position);
                        noticeBroadcontent.setVisible(false);
                        userEntity user=list.get(position);
                        list.remove(position);
                        notes.remove(position);
                        userService.deleteUser(user.getId());
                        matiereService.deleteMatiere(user.getId());
                    } catch (SQLException ex) {
                        Logger.getLogger(Form_Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
               
                } else {
                    System.out.println("User click Cancel");
                }
                System.out.println("table row count="+table1.getRowCount()+"list count="+list.size());
               
            }

            @Override
            public void update(ModelStudent student) {
                coursesnotAded =new ArrayList<>();
                System.out.println(student.getId()+student.getName()+"from list"+list.get(table1.getSelectedRow()));
                noticeBroadcontent.setVisible(false);
                noticeBroadcontent1.setVisible(true);
                selectedStudent=list.get(table1.getSelectedRow());
                
                name.setText(selectedStudent.getFirstName());
                lastname.setText(selectedStudent.getFamilyName());
                email.setText(selectedStudent.getEmail());
                
                
                matiereEntity matiere=notes.get(table1.getSelectedRow());
                noteconception.setValue(matiere.getConception());
                jLabel60.setText(noteconception.getValue()+"/100");
                
                notedata.setValue(matiere.getBase());
              
               jLabel70.setText(notedata.getValue()+"/100");
                notejava.setValue(matiere.getJava());
                jLabel50.setText(notejava.getValue()+"/100");
                noteweb.setValue(matiere.getProgrammationweb());
                jLabel65.setText(noteweb.getValue()+"/100");
                if (matiere.getJava()!=0) 
                  {  firstcourse.setVisible(true);
                  coursesnotAded.remove("java");
                }else{firstcourse.setVisible(false);
                         coursesnotAded.add("java");
                        } 
                if (matiere.getBase()!=0) {
                    secondcourse.setVisible(true);
                    coursesnotAded.remove("data base");
                }else{ secondcourse.setVisible(false);  
                coursesnotAded.add("data base");
                        } 
                if (matiere.getProgrammationweb()!=0) {
                    thirdcourse.setVisible(true);
                    coursesnotAded.remove("programmation web");
                }else {thirdcourse.setVisible(false);
                  coursesnotAded.add("programmation web");
                        
                }
                if (matiere.getConception()!=0) {
                    forthcourse.setVisible(true);
                    coursesnotAded.remove("conception");
                }else{ forthcourse.setVisible(false);
                  coursesnotAded.add("conception");
                        } 
              
            }
        };
        if (!list.isEmpty()) {
            
              for (int i = 0; i < list.size(); i++) {
                  listfromdatabase.add(list.get(i).colne());
                userEntity get = list.get(i).colne();
                get.setMatieres(notes.get(i));
                table1.addRow(get.changeToModelStudent().toRowTable(eventAction));
                
            }
                  System.out.println("size"+listfromdatabase.size());  
              
        }

    }

    private void initCardData() {
        
        
        
        card1.setData(new ModelCard(rangjava,"Java", 5100, 20, new ImageIcon(getClass().getResource("/images/java.png"))));
        card2.setData(new ModelCard(rangdata,"DataBase", 2000, 60, new ImageIcon(getClass().getResource("/images/database.png"))));
        card3.setData(new ModelCard(rangweb,"Programmation web", 3000, 80, new ImageIcon(getClass().getResource("/images/web.png"))));
        card4.setData(new ModelCard(rangconcept,"conception", 550, 95, new ImageIcon(getClass().getResource("/images/conception.png"))));
   updateCards();
    }

 /*   private void initNoticeBoard() {
        noticeBoard.addDate("04/10/2021");
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(94, 49, 238), "Hidemode", "Now", "Sets the hide mode for the component. If the hide mode has been specified in the This hide mode can be overridden by the component constraint."));
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(218, 49, 238), "Tag", "2h ago", "Tags the component with metadata name that can be used by the layout engine. The tag can be used to explain for the layout manager what the components is showing, such as an OK or Cancel button."));
        noticeBoard.addDate("03/10/2021");
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(32, 171, 43), "Further Reading", "12:30 PM", "There are more information to digest regarding MigLayout. The resources are all available at www.migcomponents.com"));
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(50, 93, 215), "Span", "10:30 AM", "Spans the current cell (merges) over a number of cells. Practically this means that this cell and the count number of cells will be treated as one cell and the component can use the space that all these cells have."));
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(27, 188, 204), "Skip ", "9:00 AM", "Skips a number of cells in the flow. This is used to jump over a number of cells before the next free cell is looked for. The skipping is done before this component is put in a cell and thus this cells is affected by it. \"count\" defaults to 1 if not specified."));
        noticeBoard.addNoticeBoard(new ModelNoticeBoard(new Color(238, 46, 57), "Push", "7:15 AM", "Makes the row and/or column that the component is residing in grow with \"weight\". This can be used instead of having a \"grow\" keyword in the column/row constraints."));
        noticeBoard.scrollToTop();
    }
*/
    private boolean showMessage(String message) {
        Message obj = new Message(dash.getFrames()[0], true);
        obj.showMessage(message);
        return obj.isOk();
    }
 private boolean showMessage(String message,String s) {
        Message obj = new Message(dash.getFrames()[0], true);
        
        
        obj.showMessage(message,coursesnotAded );
        String mat=obj.getMatiere();
                 if (mat=="java") 
                  {  firstcourse.setVisible(true);
                  
                  }
                  else if (mat=="data base") {
                    secondcourse.setVisible(true);
                  }
                  else if (mat=="data base") {
                    secondcourse.setVisible(true);
                  }
                  else if (mat=="programmation web") {
                    thirdcourse.setVisible(true);
                  }else if (mat=="conception")
                      forthcourse.setVisible(true);
                 coursesnotAded.remove(mat);
   System.out.println(mat); 
        return obj.isOk();
    }
    public void updateCards(){
        
        notes.forEach((o)->{
            if (o.getJava()!=0) {
                partjava++;
                averageJava=averageJava+o.getJava();
            }
            if (o.getBase()!=0) {
                partdata++;
                averagedata+=o.getBase();
            }
            if (o.getProgrammationweb()!=0) {
                partweb++;
                averageweb+=o.getProgrammationweb();
            }
            if (o.getConception()!=0) {
                partconcept++;
                averageconception+=o.getConception();
            }
            
        }); 
        List<Integer> rangs= new ArrayList<>();
        rangs.add(partjava);
        rangs.add(partdata);
        rangs.add(partweb);
        rangs.add(partconcept);
        rangs.sort(Integer::compare);
        
        if (partjava!=0) {
          card1.setData(new ModelCard(4-rangs.indexOf(partjava),"Java", partjava, averageJava/partjava, new ImageIcon(getClass().getResource("/images/java.png"))));
 
        } else  card1.setData(new ModelCard(0,"Java", 0, 0, new ImageIcon(getClass().getResource("/images/java.png"))));
 if (partdata!=0) {
          card2.setData(new ModelCard(4-rangs.indexOf(partdata),"DataBase", partdata, averagedata/partdata, new ImageIcon(getClass().getResource("/images/database.png"))));
 
        } else card2.setData(new ModelCard(0,"DataBase", 0, 0, new ImageIcon(getClass().getResource("/images/database.png"))));

 if (partweb!=0) {
          card3.setData(new ModelCard(4-rangs.indexOf(partweb),"Programmation web", partweb, averageweb/partweb, new ImageIcon(getClass().getResource("/images/web.png"))));
 
        } else card3.setData(new ModelCard(0,"Programmation web", 0, 0, new ImageIcon(getClass().getResource("/images/web.png"))));
 if (partconcept!=0) {
          card4.setData(new ModelCard(4-rangs.indexOf(partconcept),"conception", partconcept, averageconception/partconcept, new ImageIcon(getClass().getResource("/images/conception.png"))));
 
        } else card4.setData(new ModelCard(0,"conception", 0, 0, new ImageIcon(getClass().getResource("/images/conception.png"))));

          rangjava=0;
     rangdata=0;
     rangweb=0;
     rangconcept=0;
     partjava=0;
      averageJava=0;
      partconcept=0;
       averageconception=0;
      partdata=0;
        averagedata=0;
        partweb=0;
        averageweb=0;
      
      
   }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new component.Card();
        jLabel1 = new javax.swing.JLabel();
        card2 = new component.Card();
        card3 = new component.Card();
        card4 = new component.Card();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        noticeBroadcontent1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        lastname = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        imageProfilePanel = new javax.swing.JPanel();
        imageProfile = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        firstcourse = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        cmdEdit8 = new myswing.btn();
        notejava = new myswing.ProgressBarCustom();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        secondcourse = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        cmdEdit12 = new myswing.btn();
        notedata = new myswing.ProgressBarCustom();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        thirdcourse = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        cmdEdit11 = new myswing.btn();
        noteweb = new myswing.ProgressBarCustom();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        forthcourse = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        cmdEdit10 = new myswing.btn();
        noteconception = new myswing.ProgressBarCustom();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        noticeBroadcontent = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new swing.table.Table();
        jTextField1 = new javax.swing.JTextField();

        card1.setColorGradient(new java.awt.Color(211, 28, 215));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Dashboard / Home");

        card2.setBackground(new java.awt.Color(10, 30, 214));
        card2.setColorGradient(new java.awt.Color(72, 111, 252));

        card3.setBackground(new java.awt.Color(194, 85, 1));
        card3.setColorGradient(new java.awt.Color(255, 212, 99));

        card4.setBackground(new java.awt.Color(60, 195, 0));
        card4.setColorGradient(new java.awt.Color(208, 255, 90));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setAutoscrolls(true);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(76, 76, 76));
        jLabel2.setText("Notice Board");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 32, 130, 30));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(105, 105, 105));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menu.png"))); // NOI18N
        jLabel3.setText("return to profile");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel4.setOpaque(true);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 70, 449, 1));

        noticeBroadcontent1.setBackground(new java.awt.Color(255, 255, 255));
        noticeBroadcontent1.setAutoscrolls(true);
        noticeBroadcontent1.setLayout(new javax.swing.BoxLayout(noticeBroadcontent1, javax.swing.BoxLayout.Y_AXIS));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setMaximumSize(new java.awt.Dimension(400, 120));
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 110));

        jLabel13.setText("First Name:");

        jLabel14.setText("LastName :");

        jLabel15.setText("email :");

        name.setText("name");
        name.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        name.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameFocusLost(evt);
            }
        });
        name.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nameMouseClicked(evt);
            }
        });
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });

        lastname.setText("last name");
        lastname.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lastname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                lastnameFocusLost(evt);
            }
        });
        lastname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lastnameMouseClicked(evt);
            }
        });
        lastname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastnameActionPerformed(evt);
            }
        });

        email.setText("email");
        email.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                emailFocusLost(evt);
            }
        });
        email.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emailMouseClicked(evt);
            }
        });
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });

        imageProfilePanel.setMaximumSize(new java.awt.Dimension(50, 50));
        imageProfilePanel.setPreferredSize(new java.awt.Dimension(50, 50));
        imageProfilePanel.setLayout(new java.awt.BorderLayout());

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(255, 255, 204));
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("add course +");
        jTextField5.setAutoscrolls(false);
        jTextField5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField5MouseClicked(evt);
            }
        });
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageProfilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imageProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 84, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imageProfilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(lastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(imageProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(21, Short.MAX_VALUE))))
        );

        noticeBroadcontent1.add(jPanel4);

        firstcourse.setBackground(new java.awt.Color(255, 255, 255));
        firstcourse.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        firstcourse.setMaximumSize(new java.awt.Dimension(400, 150));
        firstcourse.setMinimumSize(new java.awt.Dimension(200, 95));
        firstcourse.setPreferredSize(new java.awt.Dimension(400, 80));

        jLabel48.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(51, 255, 51));
        jLabel48.setText("Course :Java");

        cmdEdit8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/java.png"))); // NOI18N
        cmdEdit8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEdit8ActionPerformed(evt);
            }
        });

        notejava.setBackground(new java.awt.Color(204, 204, 204));
        notejava.setValue(20);

        jLabel49.setText("x");

        jLabel50.setText("20/100");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel51.setText("+");
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setText("-");
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout firstcourseLayout = new javax.swing.GroupLayout(firstcourse);
        firstcourse.setLayout(firstcourseLayout);
        firstcourseLayout.setHorizontalGroup(
            firstcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstcourseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdEdit8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(firstcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(firstcourseLayout.createSequentialGroup()
                        .addComponent(notejava, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel50))
                    .addGroup(firstcourseLayout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 8, Short.MAX_VALUE))
        );
        firstcourseLayout.setVerticalGroup(
            firstcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstcourseLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(firstcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdEdit8, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(firstcourseLayout.createSequentialGroup()
                        .addGroup(firstcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(firstcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(notejava, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, firstcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel51)
                                .addComponent(jLabel50)))))
                .addGap(38, 38, 38))
        );

        noticeBroadcontent1.add(firstcourse);

        secondcourse.setBackground(new java.awt.Color(255, 255, 255));
        secondcourse.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        secondcourse.setMaximumSize(new java.awt.Dimension(400, 150));
        secondcourse.setMinimumSize(new java.awt.Dimension(200, 95));
        secondcourse.setPreferredSize(new java.awt.Dimension(400, 80));

        jLabel68.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(51, 255, 51));
        jLabel68.setText("Course : Data base");

        cmdEdit12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/database.png"))); // NOI18N
        cmdEdit12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEdit12ActionPerformed(evt);
            }
        });

        notedata.setBackground(new java.awt.Color(204, 204, 204));
        notedata.setValue(20);

        jLabel69.setText("x");

        jLabel70.setText("20/100");

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel71.setText("+");
        jLabel71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel71MouseClicked(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel72.setText("-");
        jLabel72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel72MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout secondcourseLayout = new javax.swing.GroupLayout(secondcourse);
        secondcourse.setLayout(secondcourseLayout);
        secondcourseLayout.setHorizontalGroup(
            secondcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(secondcourseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdEdit12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(secondcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(secondcourseLayout.createSequentialGroup()
                        .addComponent(notedata, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel70)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(secondcourseLayout.createSequentialGroup()
                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
        secondcourseLayout.setVerticalGroup(
            secondcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(secondcourseLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(secondcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdEdit12, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(secondcourseLayout.createSequentialGroup()
                        .addGroup(secondcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel69)
                            .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(secondcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel72, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(notedata, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secondcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel71)
                                .addComponent(jLabel70)))))
                .addGap(38, 38, 38))
        );

        noticeBroadcontent1.add(secondcourse);

        thirdcourse.setBackground(new java.awt.Color(255, 255, 255));
        thirdcourse.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        thirdcourse.setMaximumSize(new java.awt.Dimension(400, 150));
        thirdcourse.setMinimumSize(new java.awt.Dimension(200, 95));
        thirdcourse.setPreferredSize(new java.awt.Dimension(400, 80));

        jLabel63.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(51, 255, 51));
        jLabel63.setText("Course : web programming");

        cmdEdit11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/web.png"))); // NOI18N
        cmdEdit11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEdit11ActionPerformed(evt);
            }
        });

        noteweb.setBackground(new java.awt.Color(204, 204, 204));
        noteweb.setValue(20);

        jLabel64.setText("x");
        jLabel64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel64MouseClicked(evt);
            }
        });

        jLabel65.setText("20/100");

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel66.setText("+");
        jLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel66MouseClicked(evt);
            }
        });

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel67.setText("-");
        jLabel67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel67MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout thirdcourseLayout = new javax.swing.GroupLayout(thirdcourse);
        thirdcourse.setLayout(thirdcourseLayout);
        thirdcourseLayout.setHorizontalGroup(
            thirdcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thirdcourseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdEdit11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thirdcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thirdcourseLayout.createSequentialGroup()
                        .addComponent(noteweb, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel65)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(thirdcourseLayout.createSequentialGroup()
                        .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
        thirdcourseLayout.setVerticalGroup(
            thirdcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thirdcourseLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(thirdcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdEdit11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(thirdcourseLayout.createSequentialGroup()
                        .addGroup(thirdcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(thirdcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel67, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(noteweb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thirdcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel66)
                                .addComponent(jLabel65)))))
                .addGap(38, 38, 38))
        );

        noticeBroadcontent1.add(thirdcourse);

        forthcourse.setBackground(new java.awt.Color(255, 255, 255));
        forthcourse.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        forthcourse.setMaximumSize(new java.awt.Dimension(400, 150));
        forthcourse.setMinimumSize(new java.awt.Dimension(200, 95));
        forthcourse.setPreferredSize(new java.awt.Dimension(400, 80));

        jLabel58.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(51, 255, 51));
        jLabel58.setText("Course : conception");

        cmdEdit10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/conception.png"))); // NOI18N
        cmdEdit10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEdit10ActionPerformed(evt);
            }
        });

        noteconception.setBackground(new java.awt.Color(204, 204, 204));
        noteconception.setValue(20);

        jLabel59.setText("x");
        jLabel59.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel59MouseClicked(evt);
            }
        });

        jLabel60.setText("20/100");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setText("+");
        jLabel61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel61MouseClicked(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel62.setText("-");
        jLabel62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel62MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout forthcourseLayout = new javax.swing.GroupLayout(forthcourse);
        forthcourse.setLayout(forthcourseLayout);
        forthcourseLayout.setHorizontalGroup(
            forthcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(forthcourseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdEdit10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(forthcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(forthcourseLayout.createSequentialGroup()
                        .addComponent(noteconception, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel60)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(forthcourseLayout.createSequentialGroup()
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
        forthcourseLayout.setVerticalGroup(
            forthcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(forthcourseLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(forthcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdEdit10, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(forthcourseLayout.createSequentialGroup()
                        .addGroup(forthcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel59)
                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(forthcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(noteconception, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, forthcourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel61)
                                .addComponent(jLabel60)))))
                .addGap(38, 38, 38))
        );

        noticeBroadcontent1.add(forthcourse);

        jPanel1.add(noticeBroadcontent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 74, -1, 430));

        noticeBroadcontent.setBackground(new java.awt.Color(255, 255, 255));
        noticeBroadcontent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(noticeBroadcontent, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 270, -1, -1));

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(255, 51, 51));
        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(51, 51, 51));
        jTextField4.setText("  Cancel");
        jTextField4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 500, -1, 30));

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(0, 204, 51));
        jTextField6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(255, 255, 255));
        jTextField6.setText(" Confirm");
        jTextField6.setAutoscrolls(false);
        jTextField6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField6MouseClicked(evt);
            }
        });
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 500, 70, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("List Of Students");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/research.png"))); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        search.setToolTipText("Search ...");
        search.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel6)
                    .addContainerGap(504, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        table1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First Name", "Family Name", "courses", "Action"
            }
        ));
        jScrollPane1.setViewportView(table1);

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 204));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("+");
        jTextField1.setToolTipText("add student");
        jTextField1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jTextField1.setMinimumSize(new java.awt.Dimension(50, 50));
        jTextField1.setPreferredSize(new java.awt.Dimension(50, 50));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(179, 179, 179))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_searchActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void nameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameMouseClicked
        if (name.getText().equals("name")) {
          name.setText("");     
        }
             // TODO add your handling code here:
    }//GEN-LAST:event_nameMouseClicked

    private void lastnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lastnameMouseClicked
if (lastname.getText().equals("last name")) 
        lastname.setText("");         // TODO add your handling code here:
    }//GEN-LAST:event_lastnameMouseClicked

    private void emailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emailMouseClicked
if (email.getText().equals("email")) 

        email.setText("");         // TODO add your handling code here:

    }//GEN-LAST:event_emailMouseClicked

    private void nameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameFocusLost
        if (name.getText().trim().equals("")) {
            name.setText("hisName");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFocusLost

    private void lastnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastnameActionPerformed

    private void lastnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lastnameFocusLost
if (lastname.getText().trim().equals("")) {
            lastname.setText("hisName");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_lastnameFocusLost

    private void emailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailFocusLost
if (email.getText().trim().equals("")) {
            email.setText("hisName");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_emailFocusLost

    private void cmdEdit8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEdit8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdEdit8ActionPerformed

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        // TODO add your handling code here:
        notejava.setValue(notejava.getValue()+1);
        jLabel50.setText(notejava.getValue()+"/100");
        
    }//GEN-LAST:event_jLabel51MouseClicked

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
        // TODO add your handling code here:
        notejava.setValue(notejava.getValue()-1);
        jLabel50.setText(notejava.getValue()+"/100");
    }//GEN-LAST:event_jLabel52MouseClicked

    private void cmdEdit10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEdit10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdEdit10ActionPerformed

    private void jLabel61MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel61MouseClicked
        noteconception.setValue(noteconception.getValue()+1);
       jLabel60.setText(noteconception.getValue()+"/100"); // TODO add your handling code here:
    }//GEN-LAST:event_jLabel61MouseClicked

    private void jLabel62MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel62MouseClicked
            noteconception.setValue(noteconception.getValue()-1);
    jLabel60.setText(noteconception.getValue()+"/100");
// TODO add your handling code here:
    }//GEN-LAST:event_jLabel62MouseClicked

    private void cmdEdit11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEdit11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdEdit11ActionPerformed

    private void jLabel66MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MouseClicked
        noteweb.setValue(noteweb.getValue()+1);
        jLabel65.setText(noteweb.getValue()+"/100");
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel66MouseClicked

    private void jLabel67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseClicked
        // TODO add your handling code here:
                noteweb.setValue(noteweb.getValue()-1);
                jLabel65.setText(noteweb.getValue()+"/100");

    }//GEN-LAST:event_jLabel67MouseClicked

    private void cmdEdit12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEdit12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdEdit12ActionPerformed

    private void jLabel71MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel71MouseClicked
        // TODO add your handling code here:
        notedata.setValue(notedata.getValue()+1);
         jLabel70.setText(notedata.getValue()+"/100");
    }//GEN-LAST:event_jLabel71MouseClicked

    private void jLabel72MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel72MouseClicked
        // TODO add your handling code here:
                notedata.setValue(notedata.getValue()-1);
                 jLabel70.setText(notedata.getValue()+"/100");

    }//GEN-LAST:event_jLabel72MouseClicked

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jLabel59MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel59MouseClicked
forthcourse.setVisible(false);    
// TODO add your handling code here:
    }//GEN-LAST:event_jLabel59MouseClicked

    private void jLabel64MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel64MouseClicked
thirdcourse.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel64MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField5MouseClicked
        
        if (showMessage("add course","add")) {
            System.out.println("updated");
            
            
        }else System.out.println("notupdated");
         // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5MouseClicked

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField6MouseClicked

        selectedStudent.setFirstName(name.getText());
        selectedStudent.setFamilyName(lastname.getText());
        selectedStudent.setEmail(email.getText());
        try {
            userService.updateUser(selectedStudent);
        } catch (SQLException ex) {
            Logger.getLogger(Form_Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        matiereEntity matieres=new matiereEntity(selectedStudent.getId(),notejava.getValue(),notedata.getValue(),noteweb.getValue(),noteconception.getValue());
        selectedStudent.setMatieres(matieres);
        int position=list.indexOf(selectedStudent);
        notes.set(position, matieres);
        try {
            matiereService.updateMatiere(matieres);
        } catch (SQLException ex) {
            Logger.getLogger(Form_Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(matieres.getJava());   
        updateCards();
        table1.updateRow(selectedStudent.changeToModelStudent().toRowTable(eventAction),position);
       
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6MouseClicked

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.Card card1;
    private component.Card card2;
    private component.Card card3;
    private component.Card card4;
    private myswing.btn cmdEdit10;
    private myswing.btn cmdEdit11;
    private myswing.btn cmdEdit12;
    private myswing.btn cmdEdit8;
    private javax.swing.JTextField email;
    private javax.swing.JPanel firstcourse;
    private javax.swing.JPanel forthcourse;
    private javax.swing.JLabel imageProfile;
    private javax.swing.JPanel imageProfilePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField lastname;
    private javax.swing.JTextField name;
    private myswing.ProgressBarCustom noteconception;
    private myswing.ProgressBarCustom notedata;
    private myswing.ProgressBarCustom notejava;
    private myswing.ProgressBarCustom noteweb;
    private javax.swing.JPanel noticeBroadcontent;
    private javax.swing.JPanel noticeBroadcontent1;
    private javax.swing.JTextField search;
    private javax.swing.JPanel secondcourse;
    private swing.table.Table table1;
    private javax.swing.JPanel thirdcourse;
    // End of variables declaration//GEN-END:variables
}
