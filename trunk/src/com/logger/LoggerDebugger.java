package com.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerDebugger {

	private Logger logger;
	private File file;
	private FileHandler file_handler;

	/** Constructor - Creates Logger Configurations **/
	public LoggerDebugger() {
		try {
			logger = Logger.getLogger("8-Puzzle");
			logger.setUseParentHandlers(false);
			file = new File(System.getProperty("user.dir")
					+ "\\Log\\FileLog.txt");
			if (file.exists()) {
				file.delete();
				file = new File(System.getProperty("user.dir")
						+ "\\Log\\FileLog.txt");
			}
			file.createNewFile();
			file_handler = new FileHandler(file.getAbsolutePath());
			logger.addHandler(file_handler);
			MyFormatter formatter = new MyFormatter();
			file_handler.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			saveLog(e.getMessage(), "warning");
		}
	}

	/** Uploads log to File **/
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

	/** My Formatter Class Auxiliary **/
	class MyFormatter extends Formatter {

		@Override
		/** Unimplemented Method - Format String **/
		public String format(LogRecord record) {
			StringBuilder builder = new StringBuilder(1000);
			builder.append("[").append(record.getLevel()).append("] - ");
			builder.append(formatMessage(record));
			builder.append("\n");
			return builder.toString();
		}

	}
}
