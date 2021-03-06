package authoringApp;

import com.alee.laf.WebLookAndFeel;

public class App {
	private static MainViewController mainViewController;
	private static MainViewModel appModel;
	
	private static void createAndShowGUI() {
		mainViewController = new MainViewController();
		appModel = new MainViewModel();
		mainViewController.setModel(appModel);
		final MainFrame frame = new MainFrame(mainViewController);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
            			WebLookAndFeel.install();
            			WebLookAndFeel.initializeManagers();
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                createAndShowGUI();

            }
        });
	}
}
