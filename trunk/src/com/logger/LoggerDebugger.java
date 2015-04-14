package com.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerDebugger {

	private Logger logger;
	private File file;
	private FileHandler file_handler;

	public LoggerDebugger() {
		try {
			logger = Logger.getLogger("8-Puzzle - Log!");
			file = new File(System.getProperty("user.dir")
					+ "\\Log\\FileLog.txt");
			if(file.exists()) {
				file.delete();
				file = new File(System.getProperty("user.dir")
				+ "\\Log\\FileLog.txt");
			}
			file_handler = new FileHandler();
			logger.addHandler(file_handler);
			SimpleFormatter formatter = new SimpleFormatter();
			file_handler.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			saveLog(e.getMessage(), "warning");
		}
	}

	public void saveLog(String log, String type) {
		/** REVIEW **/
		switch (type) {
		case "info":
			logger.info(log);
			break;
		case "warning":
			logger.severe(log);
			break;
		default:
			break;
		}
	}

}
