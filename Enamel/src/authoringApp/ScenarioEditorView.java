package authoringApp;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicArrowButton;

import authoringApp.interactionModels.CellClearInteraction;
import authoringApp.interactionModels.DisplayBrailleInteraction;
import authoringApp.interactionModels.Interaction;
import authoringApp.interactionModels.KeywordInteraction;
import authoringApp.interactionModels.PauseInteraction;
import authoringApp.interactionModels.ReadInteraction;
import authoringApp.interactionModels.ResetButtonInteraction;
import authoringApp.interactionModels.SkipButtonInteraction;
import authoringApp.interactionModels.SkipInteraction;
import authoringApp.interactionModels.UserInputInteraction;
import authoringApp.interactionViews.CellClearInteractionView;
import authoringApp.interactionViews.DisplayBrailleInteractionView;
import authoringApp.interactionViews.KeywordInteractionView;
import authoringApp.interactionViews.PauseInteractionView;
import authoringApp.interactionViews.ReadInteractionView;
import authoringApp.interactionViews.ResetButtonInteractionView;
import authoringApp.interactionViews.SkipButtonInteractionView;
import authoringApp.interactionViews.SkipInteractionView;
import authoringApp.interactionViews.UserInputInteractionView;

public class ScenarioEditorView {
	// Frame
	private static JFrame frame;

	// Menus
	private static JMenuBar menuBar;
	private static JMenu fileMenu;
	private static JMenuItem newFile, openFile, saveFile, saveAsFile, exit;

	// Editor containers
	private static JSplitPane designerPane;
	private static JScrollPane interactionListPane;
	private static JPanel interactionEditorPanel;
	private static JPanel controlsPanel;

	// Buttons
	private static JButton addIntButton;
	private static JButton removeIntButton;
	private static JButton moveUpIntButton;
	private static JButton moveDownIntButton;
	private static JButton saveScenarioButton;
	private static JButton runScenarioButton;

	// Controls
	private static JComboBox newIntOptions;

	// Layout managers
	private static GridBagConstraints c;

	private static JList<String> list;

	public static Scenario test = new Scenario(new File("./FactoryScenarios/Scenario_1.txt"));

	private static void createAndShowGUI() {
		c = new GridBagConstraints();

		InitMenu();
		InitInteractionPanel();
		InitControlsPanel();

		// Create frame
		frame = new JFrame("Braille Author");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		frame.setLayout(new GridBagLayout());

		c.ipadx = 10;
		c.ipady = 10;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		frame.getContentPane().add(designerPane, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().add(controlsPanel, c);

		// Display the window
		frame.setSize(600, 600);
		// frame.pack();
		frame.setVisible(true);
	}

	private static void InitMenu() {
		// Create menu
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenu.getAccessibleContext().setAccessibleDescription(
				"File menu which contains options to create new scenario file or open an existing one.");

		// File menu items
		newFile = new JMenuItem("New");
		openFile = new JMenuItem("Open");
		saveFile = new JMenuItem("Save");
		saveAsFile = new JMenuItem("Save As");
		exit = new JMenuItem("Exit");

		fileMenu.add(newFile);
		fileMenu.add(openFile);
		fileMenu.addSeparator();
		fileMenu.add(saveFile);
		fileMenu.add(saveAsFile);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		menuBar.add(fileMenu);
	}

	private static void InitInteractionList() {
		list = new JList(test.getInteractionList());
		list.setDragEnabled(true);
		list.setDropMode(DropMode.INSERT);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int itemIndex = list.getSelectedIndex();
				Interaction currentInteraction = test.getInteractionList().get(itemIndex);
				String interactionId = Integer.toString(currentInteraction.getId());
				CardLayout cards = (CardLayout) interactionEditorPanel.getLayout();
				cards.show(interactionEditorPanel, interactionId);
			}
		});
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(0);
		list.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		interactionListPane = new JScrollPane(list);
		Dimension minimumSize = new Dimension(100, 50);
		interactionListPane.setMinimumSize(minimumSize);
	}

	private static void InitInteractionEditor() {
		// Right pane (controls for editing)
		interactionEditorPanel = new JPanel(new CardLayout());
		// Provide minimum sizes for the two components in the split pane.
		Dimension minimumSize = new Dimension(100, 50);
		interactionEditorPanel.setMinimumSize(minimumSize);
		CreateInteractionCards(test.getInteractionList());
	}

	private static void InitInteractionPanel() {
		InitInteractionList();
		InitInteractionEditor();

		// Split pane
		designerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, interactionListPane, interactionEditorPanel);
		designerPane.setOneTouchExpandable(true);
		designerPane.setDividerLocation(150);

		// Provide a preferred size for the split pane.
		designerPane.setPreferredSize(new Dimension(400, 200));
	}

	private static void InitControlsPanel() {
		controlsPanel = new JPanel();
		controlsPanel.setLayout(new GridBagLayout());
		c.ipadx = 10;
		c.ipady = 10;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = GridBagConstraints.RELATIVE;
		c.gridy = GridBagConstraints.RELATIVE;

		newIntOptions = new JComboBox();
		newIntOptions.setToolTipText("Select type of interaction to add");
		for (Interaction.InteractionType iType : Interaction.InteractionType.values()) {
			newIntOptions.addItem(iType.getDescription());
		}

		addIntButton = new JButton("+");
		removeIntButton = new JButton("-");
		moveUpIntButton = new BasicArrowButton(BasicArrowButton.NORTH);
		moveDownIntButton = new BasicArrowButton(BasicArrowButton.SOUTH);

		addIntButton.setToolTipText("Add new interaction");
		removeIntButton.setToolTipText("Remove interaction");
		moveUpIntButton.setToolTipText("Move interaction up");
		moveDownIntButton.setToolTipText("Move interaction down");

		controlsPanel.add(newIntOptions, c);
		controlsPanel.add(addIntButton, c);
		controlsPanel.add(removeIntButton, c);
		controlsPanel.add(moveUpIntButton, c);
		controlsPanel.add(moveDownIntButton, c);

		c.anchor = GridBagConstraints.EAST;
		saveScenarioButton = new JButton("Save");
		runScenarioButton = new JButton("Run");

		saveScenarioButton.setToolTipText("Save scenario");
		runScenarioButton.setToolTipText("Run simulation");

		controlsPanel.add(saveScenarioButton, c);
		controlsPanel.add(runScenarioButton, c);
	}

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                createAndShowGUI();
            }
        });
    }

	private static void CreateInteractionCards(CustomListModel<Interaction> list) {
		for (Interaction i : list) {
			System.out.println(i.getType());
			if (i.getType() == "READ") {
				interactionEditorPanel.add(new ReadInteractionView((ReadInteraction) i).getInteractionView(),
						Integer.toString(i.getId()));
			} else if (i.getType() == "CLEAR BRAILLE") {
				interactionEditorPanel.add(new CellClearInteractionView((CellClearInteraction) i).getInteractionView(),
						Integer.toString(i.getId()));
			} else if (i.getType() == "DISPLAY BRAILLE") {
				interactionEditorPanel.add(new DisplayBrailleInteractionView((DisplayBrailleInteraction) i).getInteractionView(),
						Integer.toString(i.getId()));
			} else if (i.getType() == "KEYWORD") {
				interactionEditorPanel.add(new KeywordInteractionView((KeywordInteraction) i).getInteractionView(),
						Integer.toString(i.getId()));
			} else if (i.getType() == "RESET BUTTONS") {
				interactionEditorPanel.add(new ResetButtonInteractionView((ResetButtonInteraction) i).getInteractionView(),
						Integer.toString(i.getId()));
			} else if (i.getType() == "PAUSE") {
				interactionEditorPanel.add(new PauseInteractionView((PauseInteraction) i).getInteractionView(),
						Integer.toString(i.getId()));
			} else if (i.getType() == "USER INPUT") {
				interactionEditorPanel.add(new UserInputInteractionView((UserInputInteraction) i).getInteractionView(),
						Integer.toString(i.getId()));
			} else if (i.getType() == "SKIP BUTTON") {
				interactionEditorPanel.add(new SkipButtonInteractionView((SkipButtonInteraction) i).getInteractionView(),
						Integer.toString(i.getId()));
			} else if (i.getType() == "SKIP") {
				interactionEditorPanel.add(new SkipInteractionView((SkipInteraction) i).getInteractionView(),
						Integer.toString(i.getId()));
			} else {
				System.out.println("Not there yet...");
			}
		}
	}

}
