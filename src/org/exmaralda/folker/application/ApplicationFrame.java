/*
 * ApplicationFrame.java
 *
 * Created on 9. Mai 2008, 14:08
 */

package org.exmaralda.folker.application;

//import com.apple.eawt.ApplicationEvent;
import java.awt.desktop.OpenFilesEvent;
import javax.swing.ImageIcon;
import java.io.*;
import java.util.prefs.BackingStoreException;
import javax.swing.JOptionPane;
import org.exmaralda.folker.gui.StartupSplashScreen;
import org.exmaralda.folker.utilities.FOLKERInternationalizer;
import org.exmaralda.partitureditor.partiture.StringUtilities;

/**
 *
 * @author  Thomas
 */
public class ApplicationFrame extends javax.swing.JFrame implements org.exmaralda.common.ExmaraldaApplication {
    
    
    ApplicationControl applicationControl;
    MainPanel mainPanel;

    String[] HELP_MENU_TITLES = {FOLKERInternationalizer.getString("help_menu.web"), FOLKERInternationalizer.getString("help_menu.about"), ""};

    /** Creates new form ApplicationFrame */
    public ApplicationFrame(String[] args) {
        new StartupSplashScreen(this);

        org.exmaralda.common.Logger.initialiseLogger(this);
        
        initComponents();

        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/org/exmaralda/folker/gui/folkerlogo.png")).getImage());
        
        applicationControl = new ApplicationControl(this);
        mainPanel = new MainPanel(applicationControl);
        
        applicationControl.assignActions();
        mainPanel.textViewsTabbedPane.addChangeListener(applicationControl);
        mainPanel.textViewsTabbedPane.setEnabledAt(1,false);
        mainPanel.textViewsTabbedPane.setEnabledAt(2,false);

        applicationControl.setAddEventInvoker(mainPanel.addEventInPartiturButton);

        
        getContentPane().add(mainPanel);
        
        pack();

        applicationControl.assignKeyboardShortcuts();
        
        applicationControl.retrieveSettings();
        
        applicationControl.displayRateSpinner();

        // if this is a MAC OS: init the MAC OS X specific actions
        String os = System.getProperty("os.name").substring(0,3);
        if (os.equalsIgnoreCase("mac")) {
            // added 03-03-2010
            //setupMacOSXApplicationListener();
            
            // new 02-03-2020
            java.awt.Desktop.getDesktop().setOpenFileHandler(new java.awt.desktop.OpenFilesHandler(){
                    @Override
                    public void openFiles(OpenFilesEvent e){
                        try{
                            boolean proceed = true;
                            if (applicationControl.DOCUMENT_CHANGED){
                                proceed = applicationControl.checkSave();
                            }
                            if (!proceed) return;
                            String fileNameToOpen = e.getFiles().get(0).getAbsolutePath();
                            // dirty fix for #216
                            File fileToOpen = new File(StringUtilities.fixFilePath(fileNameToOpen));                            
                            if (!(fileNameToOpen.toLowerCase().endsWith(".wav"))){
                                applicationControl.openTranscriptionFile(fileToOpen);
                            } else {
                                applicationControl.newTranscriptionFile(fileToOpen);
                            }
                        } catch (Exception ex){
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
                        }
                    }
            });
            
        }


        if (args.length>0){
            String fileNameToOpen = args[0];
            // dirty fix for #216
            File fileToOpen = new File(StringUtilities.fixFilePath(fileNameToOpen));                                                        
            //File fileToOpen = new File(fileNameToOpen);
            if (!(fileNameToOpen.toLowerCase().endsWith(".wav"))){
                applicationControl.openTranscriptionFile(fileToOpen);
            } else {
                applicationControl.newTranscriptionFile(fileToOpen);
            }
        }

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        applicationToolBar = new javax.swing.JToolBar();
        dummyPanelForCocoaQT = new javax.swing.JPanel();
        applicationMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        editMenu = new javax.swing.JMenu();
        viewMenu = new javax.swing.JMenu();
        showVirtualKeyboardCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        showQuickTranscriptionCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        showMatchListCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        showVideoPanelCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        transcriptionMenu = new javax.swing.JMenu();
        helpMenu = new org.exmaralda.common.application.HelpMenu("Hilfe", this, HELP_MENU_TITLES, "http://agd.ids-mannheim.de/folker.shtml");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(getApplicationName() + " " + getVersion());

        applicationToolBar.setMaximumSize(new java.awt.Dimension(800, 24));
        applicationToolBar.setMinimumSize(new java.awt.Dimension(200, 24));
        applicationToolBar.setPreferredSize(new java.awt.Dimension(800, 24));

        dummyPanelForCocoaQT.setMaximumSize(new java.awt.Dimension(1, 1));
        dummyPanelForCocoaQT.setMinimumSize(new java.awt.Dimension(1, 1));
        dummyPanelForCocoaQT.setPreferredSize(new java.awt.Dimension(1, 1));
        dummyPanelForCocoaQT.setLayout(new javax.swing.BoxLayout(dummyPanelForCocoaQT, javax.swing.BoxLayout.LINE_AXIS));
        applicationToolBar.add(dummyPanelForCocoaQT);

        getContentPane().add(applicationToolBar, java.awt.BorderLayout.NORTH);

        fileMenu.setText(FOLKERInternationalizer.getString("file_menu"));
        applicationMenuBar.add(fileMenu);

        editMenu.setText(FOLKERInternationalizer.getString("edit_menu"));
        applicationMenuBar.add(editMenu);

        viewMenu.setText(FOLKERInternationalizer.getString("view_menu"));

        showVirtualKeyboardCheckBoxMenuItem.setSelected(true);
        showVirtualKeyboardCheckBoxMenuItem.setText(FOLKERInternationalizer.getString("view_menu.keyboard"));
        showVirtualKeyboardCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showVirtualKeyboardCheckBoxMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(showVirtualKeyboardCheckBoxMenuItem);

        showQuickTranscriptionCheckBoxMenuItem.setText(FOLKERInternationalizer.getString("view_menu.helper"));
        showQuickTranscriptionCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showQuickTranscriptionCheckBoxMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(showQuickTranscriptionCheckBoxMenuItem);

        showMatchListCheckBoxMenuItem.setText(FOLKERInternationalizer.getString("view_menu.matchlist"));
        showMatchListCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMatchListCheckBoxMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(showMatchListCheckBoxMenuItem);

        showVideoPanelCheckBoxMenuItem.setText(FOLKERInternationalizer.getString("view_menu.videopanel"));
        showVideoPanelCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showVideoPanelCheckBoxMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(showVideoPanelCheckBoxMenuItem);

        applicationMenuBar.add(viewMenu);

        transcriptionMenu.setText(FOLKERInternationalizer.getString("transcription_menu"));
        applicationMenuBar.add(transcriptionMenu);

        helpMenu.setText(FOLKERInternationalizer.getString("help_menu"));
        applicationMenuBar.add(helpMenu);

        setJMenuBar(applicationMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showQuickTranscriptionCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showQuickTranscriptionCheckBoxMenuItemActionPerformed
        applicationControl.toggleQuickTranscriptionDialog(showQuickTranscriptionCheckBoxMenuItem.isSelected());
    }//GEN-LAST:event_showQuickTranscriptionCheckBoxMenuItemActionPerformed

    private void showVirtualKeyboardCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showVirtualKeyboardCheckBoxMenuItemActionPerformed
        applicationControl.toggleVirtualKeyboard(showVirtualKeyboardCheckBoxMenuItem.isSelected());
}//GEN-LAST:event_showVirtualKeyboardCheckBoxMenuItemActionPerformed

    private void showMatchListCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMatchListCheckBoxMenuItemActionPerformed
        applicationControl.toggleMatchList(showMatchListCheckBoxMenuItem.isSelected());
    }//GEN-LAST:event_showMatchListCheckBoxMenuItemActionPerformed

    private void showVideoPanelCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showVideoPanelCheckBoxMenuItemActionPerformed
        applicationControl.toggleVideoPanel(showVideoPanelCheckBoxMenuItem.isSelected());
    }//GEN-LAST:event_showVideoPanelCheckBoxMenuItemActionPerformed
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try{
            System.out.println("Setting system L&F : " + javax.swing.UIManager.getSystemLookAndFeelClassName());
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
                e.printStackTrace();        
        }
        
        final String[] theArgs = args;
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ApplicationFrame af = new ApplicationFrame(theArgs);                    
                af.setVisible(true);
            }
        });
    }

    /** returns the icon associated with this application
     * @return  */
    @Override
    public java.awt.Image getIconImage(){
        return new javax.swing.ImageIcon(getClass().getResource("/org/exmaralda/folker/gui/folkerlogo.png")).getImage();
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar applicationMenuBar;
    public javax.swing.JToolBar applicationToolBar;
    javax.swing.JPanel dummyPanelForCocoaQT;
    public javax.swing.JMenu editMenu;
    public javax.swing.JMenu fileMenu;
    public javax.swing.JMenu helpMenu;
    javax.swing.JCheckBoxMenuItem showMatchListCheckBoxMenuItem;
    javax.swing.JCheckBoxMenuItem showQuickTranscriptionCheckBoxMenuItem;
    javax.swing.JCheckBoxMenuItem showVideoPanelCheckBoxMenuItem;
    javax.swing.JCheckBoxMenuItem showVirtualKeyboardCheckBoxMenuItem;
    public javax.swing.JMenu transcriptionMenu;
    javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables

    @Override
    public String getVersion() {
       return org.exmaralda.common.EXMARaLDAConstants.FOLKER_VERSION;
    }

    @Override
    public String getApplicationName() {
        return "Folker";
    }

    @Override
    public String getPreferencesNode() {
        return "org.exmaralda.folker";
    }

    @Override
    public ImageIcon getWelcomeScreen() {
        return new javax.swing.ImageIcon(getClass().getResource("/org/exmaralda/folker/gui/SplashScreen.png"));
    }
    
    @Override
    public void resetSettings(){
        try {
            java.util.prefs.Preferences.userRoot().node(getPreferencesNode()).clear();                
            JOptionPane.showMessageDialog(rootPane, "Preferences reset.\nRestart the editor.");
        } catch (BackingStoreException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "Problem resetting preferences:\n" + ex.getLocalizedMessage());
        }        
    }
    
    /** added 01-03-2010 */
    /*private void setupMacOSXApplicationListener() {
        final com.apple.eawt.Application application = com.apple.eawt.Application.getApplication();
        application.setEnabledAboutMenu(true); // damit ein "Ueber " Menu erscheint
        application.addPreferencesMenuItem(); // "Einstellen..." Dialog
        application.setEnabledPreferencesMenu(true); // diesen Dialog auch
        application.addApplicationListener(new com.apple.eawt.ApplicationListener() {

            @Override
            public void handleAbout(com.apple.eawt.ApplicationEvent ae) {
                ((HelpMenu)helpMenu).aboutAction.actionPerformed(null);
                ae.setHandled(true); // habe fertig...
            }

            // app wird ueber den finder geoeffnet. wie auch sonst.
            // (lies: total unnuetz!)
            @Override
            public void handleOpenApplication(ApplicationEvent ae) {
            }

            @Override
            public void handlePreferences(ApplicationEvent ae) {
                applicationControl.editPreferencesAction.actionPerformed(null);
                ae.setHandled(true);
            }

            @Override
            public void handlePrintFile(ApplicationEvent ae) {
                System.out.println("Drucken?!");
                JOptionPane.showMessageDialog(rootPane, "Drucken wird nicht unterstuetzt.\b Bitte nutzen Sie die Funktionen unter\bDatei > Ausgabe...");
                ae.setHandled(true);
            }

            @Override
            public void handleQuit(ApplicationEvent ae) {
                boolean reallyQuit = applicationControl.exitApplication();
                ae.setHandled(reallyQuit); // da wird wohl nichts mehr draus!
            }

            // anwendung laeuft bereits und jemand startet es nochmal
            @Override
            public void handleReOpenApplication(ApplicationEvent ae) {
                System.out.println("Laeuft schon");
                // will ich mehrere instanzen? nein
                //JOptionPane.showMessageDialog(new JFrame(), "You already have an instance\nof the EXMARaLDA Partitur-Editor running.");
                ae.setHandled(true);
            }

            // anwendung laeuft schon, dokument wird ueber den finder geoeffnet
            @Override
            public void handleOpenFile(ApplicationEvent ae) {
                try{
                    boolean proceed = true;
                    if (applicationControl.DOCUMENT_CHANGED){
                        proceed = applicationControl.checkSave();
                    }
                    if (!proceed) return;
                    String fileNameToOpen = ae.getFilename();
                    File fileToOpen = new File(fileNameToOpen);
                    if (!(fileNameToOpen.toLowerCase().endsWith(".wav"))){
                        applicationControl.openTranscriptionFile(fileToOpen);
                    } else {
                        applicationControl.newTranscriptionFile(fileToOpen);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(rootPane, e.getLocalizedMessage());
                }
            }
           });
	}*/
}
