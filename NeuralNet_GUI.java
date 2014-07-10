
import java.io.File;
import javax.swing.JFileChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matthew Dickinson
 */
public class NeuralNet_GUI extends javax.swing.JFrame {
    
    private boolean withGA = false;
    
    private class MyCustomFilter extends javax.swing.filechooser.FileFilter {
        @Override
        public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
            return file.isDirectory() || file.getAbsolutePath().endsWith(".txt");
        }
        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Text documents (*.txt)";
        }
    }
    
    /**
     * Creates new form NeuralNet_GUI
     */
    public NeuralNet_GUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        delimiterBox = new javax.swing.JComboBox();
        delimLabel = new javax.swing.JLabel();
        selectDataButton = new javax.swing.JButton();
        nnTitle = new javax.swing.JLabel();
        executeButton = new javax.swing.JButton();
        fileNameTextArea = new javax.swing.JTextField();
        kFoldResultText = new javax.swing.JTextField();
        nnTypeLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        nnMenu = new javax.swing.JMenu();
        nnButton = new javax.swing.JMenuItem();
        nngaButton = new javax.swing.JMenuItem();
        compareButton = new javax.swing.JMenuItem();
        exitButton = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        fileFormatButton = new javax.swing.JMenuItem();

        fileChooser.setDialogTitle("This is my open Dialog");
        fileChooser.setFileFilter(new MyCustomFilter());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        delimiterBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Comma", "Tab" }));
        delimiterBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delimiterBoxActionPerformed(evt);
            }
        });

        delimLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        delimLabel.setText("Delimitter");

        selectDataButton.setText("Select Data Set");
        selectDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectDataButtonActionPerformed(evt);
            }
        });

        nnTitle.setText("Simple Neural Network");

        executeButton.setText("Execute");
        executeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeButtonActionPerformed(evt);
            }
        });

        fileNameTextArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileNameTextAreaActionPerformed(evt);
            }
        });

        kFoldResultText.setText("% Confidence (K-Fold Test)");

        nnTypeLabel.setText("Neural Network Type");

        fileMenu.setText("File");

        nnMenu.setText("Neural Network Type");

        nnButton.setText("Simple NN");
        nnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nnButtonActionPerformed(evt);
            }
        });
        nnMenu.add(nnButton);

        nngaButton.setText("NN w/ Genetic Algorithm");
        nngaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nngaButtonActionPerformed(evt);
            }
        });
        nnMenu.add(nngaButton);

        fileMenu.add(nnMenu);

        compareButton.setText("Comparison");
        fileMenu.add(compareButton);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        fileMenu.add(exitButton);

        jMenuBar1.add(fileMenu);

        helpMenu.setText("Help");

        fileFormatButton.setText("Data File Format");
        helpMenu.add(fileFormatButton);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(executeButton)
                    .addComponent(selectDataButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delimLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nnTypeLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nnTitle)
                    .addComponent(delimiterBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileNameTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kFoldResultText, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fileNameTextArea, kFoldResultText});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {delimLabel, executeButton, selectDataButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nnTitle)
                    .addComponent(nnTypeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delimiterBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delimLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectDataButton)
                    .addComponent(fileNameTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kFoldResultText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(executeButton))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {delimLabel, executeButton, nnTypeLabel, selectDataButton});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {delimiterBox, fileNameTextArea, kFoldResultText, nnTitle});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nngaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nngaButtonActionPerformed
        // TODO add your handling code here:
        nnTitle.setText("Neural Networks Employing Genetic Algorithms");
        withGA = true;
    }//GEN-LAST:event_nngaButtonActionPerformed

    private void delimiterBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delimiterBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_delimiterBoxActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void selectDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectDataButtonActionPerformed
        // TODO add your handling code here:
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
          // What to do with the file, e.g. display it in a TextArea
            fileNameTextArea.setText(file.getAbsolutePath());
    } else {
        System.out.println("File access cancelled by user.");
    }
    }//GEN-LAST:event_selectDataButtonActionPerformed

    private void nnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nnButtonActionPerformed
        // TODO add your handling code here:
        nnTitle.setText("Simple Neural Network");
        withGA = false;
    }//GEN-LAST:event_nnButtonActionPerformed

    private void executeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeButtonActionPerformed
        // TODO add your handling code here:
        // Call driver and pass it the data file location, the delimiter, and ...
        Driver execute = new Driver(fileNameTextArea.getText(), delimiterBox.getSelectedItem().toString(), withGA);
        kFoldResultText.setText(execute.getKFoldTest().getConfLevel() + " % Confidence (K-Fold Test)");
    }//GEN-LAST:event_executeButtonActionPerformed

    private void fileNameTextAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileNameTextAreaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileNameTextAreaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NeuralNet_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NeuralNet_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NeuralNet_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NeuralNet_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NeuralNet_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem compareButton;
    private javax.swing.JLabel delimLabel;
    private javax.swing.JComboBox delimiterBox;
    private javax.swing.JButton executeButton;
    private javax.swing.JMenuItem exitButton;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JMenuItem fileFormatButton;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JTextField fileNameTextArea;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JTextField kFoldResultText;
    private javax.swing.JMenuItem nnButton;
    private javax.swing.JMenu nnMenu;
    private javax.swing.JLabel nnTitle;
    private javax.swing.JLabel nnTypeLabel;
    private javax.swing.JMenuItem nngaButton;
    private javax.swing.JButton selectDataButton;
    // End of variables declaration//GEN-END:variables

}