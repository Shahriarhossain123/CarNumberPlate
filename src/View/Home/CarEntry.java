/*
 * Copyright 2013 JavaANPR contributors
 * Copyright 2006 Ondrej Martinsky
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package View.Home;

import Controller.gui.tools.FileListModelEntry;
import Controller.gui.tools.ImageFileFilter;
import Model.imageanalysis.CarSnapshot;
import Model.imageanalysis.Photo;
import carnumberplate.Main;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.UIManager;

public class CarEntry extends JFrame {
    
    Connection con;
    PreparedStatement pst;
    ResultSet rst;
    
    private static final long serialVersionUID = 0L;

    private CarSnapshot car;
    private BufferedImage panelCarContent;
    private final JFileChooser fileChooser;
    private DefaultListModel<FileListModelEntry> fileListModel;
    private int selectedIndex = -1;

    private JList<FileListModelEntry> fileList;
    private JPanel panelCar;
    private JLabel recognitionLabel;

    /**
     * Creates new form MainFrame.
     */
    private void carEntrydb(){
        try {
                String sql ="INSERT INTO car_number.car(number_plate, plate_image, entry_time, created_date, modified_date) VALUES (?,?,?,?,?)";
                pst=con.prepareStatement(sql);
               // pst.setString(1,txt_book_id.getText());
                //pst.setString(2,txt_book_name.getText());
               // pst.setString(3,txt_author.getText());
                //pst.setString(4,txt_edition.getText());
                
                
                //if isbn field are empty
                //String isbnget=txt_isbn.getText();
                //int isbn=isbnget.length();
                
                //if(isbn!='/'){
                //pst.setString(5,txt_isbn.getText());
                //}
               // else {
                 //   pst.setString(5,isbnget);
               // }
                
                
               // pst.setString(6,(String)cmb_publisher_id.getSelectedItem());
              //  pst.setString(7,txt_publisher_name.getText());
             //   pst.setString(8,(String)cmb_depertment.getSelectedItem());
             //   pst.setString(9,txt_total_pices.getText());
             //   pst.setString(10,txt_total_pices.getText());
            //    pst.setString(11,txt_location.getText());
                pst.execute();
            
                JOptionPane.showMessageDialog(null, "Car Add Done");
            
            } catch (HeadlessException | SQLException e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Plese Fill the form Correctly!");
            }
    }
    
    public CarEntry() {
        initComponents();

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setDialogTitle("Load snapshots");
        // fileChooser.setFileFilter(new ImageFileFilter()); // TODO why not???

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = getWidth();
        int height = getHeight();
        setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    private void initComponents() {
        Font arial11 = new Font("Arial", 0, 11);
        recognitionLabel = new JLabel();
        panelCar = new JPanel() {

            private static final long serialVersionUID = 0L;

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(panelCarContent, 0, 0, null);
            }
        };
        JScrollPane fileListScrollPane = new JScrollPane();
        fileList = new JList<>();
        JButton recognizeButton = new JButton();
        JLabel bottomLine = new JLabel();
        JMenuBar menuBar = new JMenuBar();
        JMenu imageMenu = new JMenu();
        JMenuItem openItem = new JMenuItem();
        JMenuItem exitItem = new JMenuItem();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("JavaANPR");
        setResizable(false);
        recognitionLabel.setBackground(new Color(0, 0, 0));
        recognitionLabel.setFont(new Font("Arial", 0, 24));
        recognitionLabel.setForeground(new Color(255, 204, 51));
        recognitionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recognitionLabel.setText(null);
        recognitionLabel.setBorder(BorderFactory.createEtchedBorder());
        recognitionLabel.setOpaque(true);
        panelCar.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout panelCarLayout = new GroupLayout(panelCar);
        panelCar.setLayout(panelCarLayout);
        panelCarLayout.setHorizontalGroup(
                panelCarLayout.createParallelGroup(GroupLayout.LEADING).add(0, 585, Short.MAX_VALUE));
        panelCarLayout
                .setVerticalGroup(panelCarLayout.createParallelGroup(GroupLayout.LEADING).add(0, 477, Short.MAX_VALUE));
        fileListScrollPane.setBorder(BorderFactory.createEtchedBorder());
        fileListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fileList.setBackground(UIManager.getDefaults().getColor("Panel.background"));
        fileList.setFont(arial11);
        fileList.addListSelectionListener(this::fileListValueChanged);
        fileListScrollPane.setViewportView(fileList);
        recognizeButton.setFont(arial11);
        recognizeButton.setText("Recognize plate");
        recognizeButton.addActionListener(this::recognizeButtonActionPerformed);
        bottomLine.setFont(arial11);
        menuBar.setFont(arial11);
        imageMenu.setText("Image");
        imageMenu.setFont(arial11);
        openItem.setFont(arial11);
        openItem.setText("Load snapshots");
        openItem.addActionListener(this::openItemActionPerformed);
        imageMenu.add(openItem);
        exitItem.setFont(arial11);
        exitItem.setText("Exit");
        exitItem.addActionListener(this::exitItemActionPerformed);
        imageMenu.add(exitItem);
        menuBar.add(imageMenu);
   
        setJMenuBar(menuBar);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout); // TODO refactor
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.LEADING)
                .add(layout.createSequentialGroup().addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.TRAILING)
                                .add(GroupLayout.LEADING, bottomLine, GroupLayout.DEFAULT_SIZE, 589,
                                        Short.MAX_VALUE)
                                .add(GroupLayout.LEADING, panelCar, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.TRAILING)
                                .add(fileListScrollPane, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                .add(GroupLayout.LEADING, recognitionLabel, GroupLayout.DEFAULT_SIZE, 190,
                                        Short.MAX_VALUE)
                                .add(recognizeButton, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING)
                .add(layout.createSequentialGroup().addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.LEADING).add(layout.createSequentialGroup()
                                .add(fileListScrollPane, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.RELATED).add(recognizeButton)
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(recognitionLabel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
                                .add(panelCar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)).addPreferredGap(LayoutStyle.RELATED).add(bottomLine)));
        pack();
    }

    private void helpItemActionPerformed(ActionEvent evt) {
        /*try {
            new FrameHelp(FrameHelp.FrameHelpContent.SHOW_HELP);
        } catch (IOException e) {
            e.printStackTrace(); // TODO exception
        }*/
    }

    private void aboutItemActionPerformed(ActionEvent evt) {
        /*try {
            new FrameHelp(FrameHelp.FrameHelpContent.SHOW_ABOUT);
        } catch (IOException e) {
            e.printStackTrace(); // TODO exception
        }*/
    }

    private void recognizeButtonActionPerformed(ActionEvent evt) {
        new RecognizeThread(this).start();
    }

    private void fileListValueChanged(ListSelectionEvent evt) {
        int selectedNow = fileList.getSelectedIndex();
        if ((selectedNow != -1)) {
            recognitionLabel.setText(fileListModel.get(selectedNow).getRecognizedPlate());
            selectedIndex = selectedNow;
            String path = fileListModel.getElementAt(selectedNow).getFullPath();
            new LoadImageThread(this, path).start();
        }
    }

    private void exitItemActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void openItemActionPerformed(ActionEvent evt) {
        int returnValue;
        returnValue = fileChooser.showOpenDialog((Component) evt.getSource());
        if (returnValue != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File[] selectedFiles = fileChooser.getSelectedFiles();
        fileListModel = new DefaultListModel<>();
        for (File selectedFile : selectedFiles) {
            if (selectedFile.isFile()) {
                fileListModel
                        .addElement(new FileListModelEntry(selectedFile.getName(), selectedFile.getAbsolutePath()));
            } else if (selectedFile.isDirectory()) {
                String[] fileNames = selectedFile.list();
                if (fileNames != null) {
                    for (String fileName : fileNames) {
                        if (ImageFileFilter.accept(fileName)) {
                            fileListModel.addElement(new FileListModelEntry(fileName,
                                    selectedFile + File.separator + fileName));
                        }
                    }
                }
            }
        }
        fileList.setModel(fileListModel);
    }

    public class RecognizeThread extends Thread {
        private CarEntry parentFrame = null;

        public RecognizeThread(CarEntry parentFrame) {
            this.parentFrame = parentFrame;
        }

        private void setFailedAndPrintStackTrace(Exception exception) {
            parentFrame.recognitionLabel.setText("failed");
            exception.printStackTrace();
        }

        @Override
        public void run() {
            String recognizedText;
            parentFrame.recognitionLabel.setText("processing...");
            int index = parentFrame.selectedIndex;
            try {
                recognizedText = Main.systemLogic.recognize(parentFrame.car, false);
            } catch (IllegalArgumentException | IOException exception) {
                setFailedAndPrintStackTrace(exception);
                return;
            }
            parentFrame.recognitionLabel.setText(recognizedText);
            parentFrame.fileListModel.get(index).setRecognizedPlate(recognizedText);
        }
    }

    public class LoadImageThread extends Thread {
        private CarEntry parentFrame = null;
        private String url = null;

        public LoadImageThread(CarEntry parentFrame, String url) {
            this.parentFrame = parentFrame;
            this.url = url;
        }

        @Override
        public void run() {
            try {
                parentFrame.car = new CarSnapshot(url);
                parentFrame.panelCarContent = parentFrame.car.duplicate().getImage();
                parentFrame.panelCarContent =
                        Photo.linearResizeBi(parentFrame.panelCarContent, parentFrame.panelCar.getWidth(),
                                parentFrame.panelCar.getHeight());
                parentFrame.panelCar.paint(parentFrame.panelCar.getGraphics());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
                UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CarEntry().setVisible(true);
            }
        });
    }
}
