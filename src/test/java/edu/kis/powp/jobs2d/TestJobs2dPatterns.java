package edu.kis.powp.jobs2d;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.AbstractLineDrawerAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.DrawPanelControllerAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.line.CustomLine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		SelectTestFigureOptionListener selectTestFigureOptionListener1 = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), 1);
		SelectTestFigureOptionListener selectTestFigureOptionListener2 = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), 2);
		SelectTestFigureOptionListener selectTestFigureOptionListener3 = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), 3);
		SelectTestFigureOptionListener selectTestFigureOptionListener4 = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), 4);
		SelectTestFigureOptionListener selectTestFigureOptionListener5 = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), 5);

		application.addTest("Figure Joe 1", selectTestFigureOptionListener1);
		application.addTest("Figure Joe 2", selectTestFigureOptionListener2);
		application.addTest("Figure Jane", selectTestFigureOptionListener3);
		application.addTest("Figure Star", selectTestFigureOptionListener4);
		application.addTest("Figure Hourglass", selectTestFigureOptionListener5);
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		Job2dDriver testDriver = new DrawPanelControllerAdapter();
		DriverFeature.addDriver("Draw Panel Controller", testDriver);

		Job2dDriver specialLineDriver = new LineDrawerAdapter(LineFactory.getSpecialLine());
		DriverFeature.addDriver("Special Line Drawer", specialLineDriver);

		Job2dDriver dottedLineDriver = new LineDrawerAdapter(LineFactory.getDottedLine());
		DriverFeature.addDriver("Dotted Line Drawer", dottedLineDriver);

		Job2dDriver customLineDriver = new LineDrawerAdapter(new CustomLine(Color.YELLOW, 6.9f, true));
		DriverFeature.addDriver("Custom Line Drawer", customLineDriver);

		//AbstractDriver janeDriver = new AbstractLineDrawerAdapter(new LineDrawerAdapter(new CustomLine(Color.RED, 4f, true)));
		//DriverFeature.addDriver("Abstract Line Drawer", janeDriver);

		DriverFeature.updateDriverInfo();
	}

	/**
	 * Auxiliary routines to enable using Buggy Simulator.
	 * 
	 * @param application Application context.
	 */
	private static void setupDefaultDrawerVisibilityManagement(Application application) {
		DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
		application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
				new SelectChangeVisibleOptionListener(defaultDrawerWindow), false);
		//defaultDrawerWindow.setVisible(true);
	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);
				setupDefaultDrawerVisibilityManagement(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupLogger(app);

				app.setVisibility(true);
			}
		});
	}

}
