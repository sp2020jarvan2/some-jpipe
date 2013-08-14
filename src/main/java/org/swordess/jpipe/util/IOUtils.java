package org.swordess.jpipe.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {

	private IOUtils() {
	}
	
	public static List<String> readLines(String file) {
		if (StringUtils.isNotEmpty(file)) {
			try {
				return readLines(new FileReader(file));
			} catch (FileNotFoundException e) {
				// ignore this
			}
		}
		return new ArrayList<String>();
	}
	
	public static List<String> readLines(Reader reader) {
		return readLines(reader, true);
	}
	
	public static List<String> readLines(Reader reader, boolean autoClose) {
		List<String> lines = new ArrayList<String>();
		if (null != reader) {
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;
			try {
				while ((line = bufferedReader.readLine()) != null){
					lines.add(line);
				}
			} catch (IOException e) {
				// ignore this
			} finally {
				if (autoClose) {
					try {
						reader.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return lines;
	}
	
	public static void writeLine(String file, String line) {
		if (StringUtils.isNotEmpty(file) && StringUtils.isNotEmpty(line)) {
			try {
				writeLine(new FileWriter(file), line);
			} catch (IOException e) {
				// ignore this
			}
		}
	}
	
	public static void writeLine(Writer writer, String line) {
		writeLine(writer, line, true);
	}
	
	public static void writeLine(Writer writer, String line, boolean autoClose) {
		if (null != writer) {
			if (StringUtils.isNotEmpty(line)) {
				PrintWriter printWriter = new PrintWriter(writer);
				printWriter.println(line);
			}
			
			if (autoClose) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static void writeLines(String file, List<String> lines) {
		if (StringUtils.isEmpty(file) || CollectionUtils.isEmpty(lines)) {
			try {
				writeLines(new FileWriter(file), lines);
			} catch (IOException e) {
				// ignore this
			}
		}
	}
	
	public static void writeLines(Writer writer, List<String> lines) {
		writeLines(writer, lines, true);
	}
	
	public static void writeLines(Writer writer, List<String> lines, boolean autoClose) {
		if (null != writer) {
			if (CollectionUtils.isNotEmpty(lines)) {
				PrintWriter printWriter = new PrintWriter(writer);
				for (String line : lines) {
					printWriter.println(line);
				}
			}
			
			if (autoClose) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
}
