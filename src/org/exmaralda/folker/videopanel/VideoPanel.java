/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.exmaralda.folker.videopanel;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.exmaralda.folker.timeview.TimeSelectionEvent;
import org.exmaralda.folker.timeview.TimeSelectionListener;
import org.exmaralda.partitureditor.jexmaraldaswing.fileFilters.ParameterFileFilter;
import org.exmaralda.partitureditor.sound.JDSPlayer;
import org.exmaralda.partitureditor.sound.Playable;
import org.exmaralda.partitureditor.sound.PlayableEvent;
import org.exmaralda.partitureditor.sound.PlayableListener;

/**
 *
 * @author Schmidt
 */
public class VideoPanel extends javax.swing.JDialog implements PlayableListener, TimeSelectionListener {

    Playable videoPlayer;
    
    double startTime = 0.0;
    double endTime = 10.0;
    private String preferredPath = "C:\\Users\\Schmidt\\Dropbox\\IDS\\VIDEO";
    
    /**
     * Creates new form VideoPanel
     * @param parent
     * @param modal
     */
    public VideoPanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        videoPlayer = new JDSPlayer();
        //videoPlayer = new MMFPlayer();
        videoPlayer.addPlayableListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        videoDisplayPanel = new javax.swing.JPanel();
        timePanel = new javax.swing.JPanel();
        startPositionLabel = new javax.swing.JLabel();
        currentPositionLabel = new javax.swing.JLabel();
        endPositionLabel = new javax.swing.JLabel();
        bottomPanel = new javax.swing.JPanel();
        controlsPanel = new javax.swing.JPanel();
        playSelectionButton = new javax.swing.JButton();
        filePanel = new javax.swing.JPanel();
        openFileButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("FOLKER Video Panel");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        videoDisplayPanel.setBackground(new java.awt.Color(153, 153, 153));
        videoDisplayPanel.setPreferredSize(new java.awt.Dimension(480, 320));
        getContentPane().add(videoDisplayPanel, java.awt.BorderLayout.CENTER);

        startPositionLabel.setForeground(new java.awt.Color(0, 255, 0));
        startPositionLabel.setText("-");
        timePanel.add(startPositionLabel);

        currentPositionLabel.setText("-");
        timePanel.add(currentPositionLabel);

        endPositionLabel.setForeground(new java.awt.Color(255, 0, 0));
        endPositionLabel.setText("-");
        endPositionLabel.setToolTipText("");
        timePanel.add(endPositionLabel);

        getContentPane().add(timePanel, java.awt.BorderLayout.PAGE_START);

        bottomPanel.setLayout(new java.awt.BorderLayout());

        playSelectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/exmaralda/folker/tangoicons/tango-icon-theme-0.8.1/16x16/actions/media-playback-start.png"))); // NOI18N
        playSelectionButton.setText("[*]");
        playSelectionButton.setToolTipText("Auswahl abspielen");
        playSelectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playSelectionButtonActionPerformed(evt);
            }
        });
        controlsPanel.add(playSelectionButton);

        bottomPanel.add(controlsPanel, java.awt.BorderLayout.CENTER);

        openFileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/exmaralda/folker/tangoicons/tango-icon-theme-0.8.1/16x16/devices/video-display.png"))); // NOI18N
        openFileButton.setToolTipText("Videodatei �ffnen...");
        openFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileButtonActionPerformed(evt);
            }
        });
        filePanel.add(openFileButton);

        bottomPanel.add(filePanel, java.awt.BorderLayout.EAST);

        getContentPane().add(bottomPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileButtonActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new ParameterFileFilter("mpg", "MPEG-1 Videodateien (*.mpg)"));
        //jfc.setFileFilter(new ParameterFileFilter("mp4", "MPEG-1 Videodateien (*.mp4)"));
        jfc.setCurrentDirectory(new File(preferredPath));
        int ret = jfc.showOpenDialog(this);
        if (ret==JFileChooser.APPROVE_OPTION){
            try {
                File f = jfc.getSelectedFile();
                openVideoFile(f);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(bottomPanel, ex);
            }
        }
    }//GEN-LAST:event_openFileButtonActionPerformed

    private void playSelectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playSelectionButtonActionPerformed
        videoPlayer.startPlayback();
    }//GEN-LAST:event_playSelectionButtonActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        int dialogWidth = this.getWidth();
        int dialogHeight = this.getHeight();

        if (videoPlayer instanceof JDSPlayer){
            JDSPlayer jdsPlayer = (JDSPlayer)videoPlayer; 
            //int currentVideoWidth = jdsPlayer.getVisibleComponent().getWidth();
            //int currentVideoHeight = jdsPlayer.getVisibleComponent().getHeight();
            int sourceWidth = jdsPlayer.getSourceWidth();
            int sourceHeight = jdsPlayer.getSourceHeight();

            float widthRatio = (float)dialogWidth / (float)sourceWidth;
            float heightRatio = (float)dialogHeight / (float)sourceHeight;

            Component c = jdsPlayer.getVisibleComponent();

            if (widthRatio<heightRatio){
                    c.setPreferredSize(new java.awt.Dimension(
                            dialogWidth,
                            (int) Math.round((double)(dialogWidth/(double)sourceWidth) * sourceHeight)
                    ));

            } else {
                    c.setPreferredSize(new java.awt.Dimension(
                            (int) Math.round((double)(dialogHeight/(double)sourceHeight) * sourceWidth),
                            dialogHeight
                    ));            
            }
            videoDisplayPanel.setPreferredSize(c.getPreferredSize());
        }
        //pack();
    }//GEN-LAST:event_formComponentResized

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
            java.util.logging.Logger.getLogger(VideoPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VideoPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VideoPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VideoPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VideoPanel dialog = new VideoPanel(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JLabel currentPositionLabel;
    private javax.swing.JLabel endPositionLabel;
    private javax.swing.JPanel filePanel;
    private javax.swing.JButton openFileButton;
    private javax.swing.JButton playSelectionButton;
    private javax.swing.JLabel startPositionLabel;
    private javax.swing.JPanel timePanel;
    private javax.swing.JPanel videoDisplayPanel;
    // End of variables declaration//GEN-END:variables

    private void openVideoFile(File f) throws IOException {
        JDSPlayer jdsPlayer = (JDSPlayer)videoPlayer;
        jdsPlayer.setSoundFile(f.getAbsolutePath());
        
        if (jdsPlayer.getVisibleComponent()!=null){
            //videoDisplayPanel.removeAll();
            //System.out.println("Dummy panel removed");
            Component c = jdsPlayer.getVisibleComponent();
            //c.setPreferredSize(new java.awt.Dimension(
            //        edsp.wrappedPlayer.getSourceWidth(),
            //        edsp.wrappedPlayer.getSourceHeight()));
            // change 02-06-2015: attempt to set size for video
            int sourceWidth = jdsPlayer.getSourceWidth();
            int sourceHeight = jdsPlayer.getSourceHeight();
            //float aspectRatio = edsp.wrappedPlayer.getAspectRatio();
            if (sourceWidth<=480){
                c.setPreferredSize(new java.awt.Dimension(sourceWidth, sourceHeight));                    
            } else {
                c.setPreferredSize(new java.awt.Dimension(
                        480,
                        (int) Math.round((double)(480.0/(double)sourceWidth) * sourceHeight)
                        ));
            }
            videoDisplayPanel.add(c);
            videoDisplayPanel.setPreferredSize(c.getPreferredSize());
            //pack();
        }
    }

    @Override
    public void processPlayableEvent(PlayableEvent e) {
        double currentPosition = e.getPosition();
        currentPositionLabel.setText("   " + org.exmaralda.folker.utilities.TimeStringFormatter.formatMiliseconds(currentPosition*1000.0,2) + "   ");
    }

    @Override
    public void processTimeSelectionEvent(TimeSelectionEvent event) {
        
        if (event.getType()!=TimeSelectionEvent.ZOOM_CHANGED){
            double selectionStart = event.getStartTime();
            double selectionEnd = event.getEndTime();
            currentPositionLabel.setText("   " + org.exmaralda.folker.utilities.TimeStringFormatter.formatMiliseconds(selectionStart,2) + "   ");

            if (selectionStart!=selectionEnd){
                startPositionLabel.setText(org.exmaralda.folker.utilities.TimeStringFormatter.formatMiliseconds(selectionStart,2));
                endPositionLabel.setText(org.exmaralda.folker.utilities.TimeStringFormatter.formatMiliseconds(selectionEnd,2));
                
                videoPlayer.setStartTime(selectionStart / 1000.0);
                videoPlayer.setEndTime(selectionEnd / 1000.0);
                
                ((JDSPlayer)videoPlayer).updateVideo(selectionStart / 1000.0);


            } else {
                startPositionLabel.setText("-");
                endPositionLabel.setText("-");
                videoPlayer.setStartTime(selectionStart / 1000.0);
                videoPlayer.setEndTime(videoPlayer.getTotalLength());
            }
            
        }
        
    }

    public void setPreferredPath(String currentMediaPath) {
        preferredPath = currentMediaPath;
    }
}
