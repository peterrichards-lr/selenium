package com.liferay.sales.selenium.insurance;
import com.liferay.sales.selenium.api.ClickpathBase;
import com.liferay.sales.selenium.chrome.ChromeDriverInitializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class LiferayInsurance {

	public static void main(String[] args) {
		try {
			doIt(args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}	
	
	public static void doIt(String[] args) {
// Before starting the script, make adjustments in this top block
// to reflect the behavior that you need.
// Inspect the clickpaths.
// If you run the A/B-Test, note that you'll have to prepare the 
// content according to the documentation
// https://docs.google.com/document/d/1h2E7UUt_i3yqwge25Pd8YXOLHt3Jujz4VBAdgHSeKQw/edit#heading=h.w4lf8kpcller
		
//			System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
			
		String[][] cityUsers = readUserCSV("/home/olaf/Dokumente/HomeDirUntil2023-02-T19/cityUsers.csv");
		String baseUrl = "https://webserver-lctidcanalyst2023-prd.lfr.cloud/";
		String[] arguments = new String[] { "--remote-allow-origins=*" };
//		String[] arguments = new String[] { "--headless", "--remote-allow-origins=*" };
		int repeats = 1;
		
		ClickpathBase[] paths = new ClickpathBase[] {
				 new LiferayInsuranceClickpath1(new ChromeDriverInitializer(arguments), baseUrl)
				,new LiferayInsuranceClickpath1(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathNewsABTest(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathNewsABTest(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathNewsABTest(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathNewsABTest(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathNewsABTest(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathNewsABTest(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathNewsABTest(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathNewsABTest(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathNewsABTest(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathNewsABTest(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath1(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath2(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath3(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath4(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath3(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath4(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathABTest(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathABTest(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathABTest(new ChromeDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath1(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath2(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath3(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath4(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath1(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath2(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath3(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpath4(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathABTest(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathABTest(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathABTest(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathABTest(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathABTest(new FirefoxDriverInitializer(arguments), baseUrl)
//				,new LiferayCityClickpathABTest(new FirefoxDriverInitializer(arguments), baseUrl)
		};

//		paths = new ClickpathBase[] {
//				new LiferayCityClickpath4(new ChromeDriverInitializer(), baseUrl)
//		};
		

// Typically, nothing more to "configure" below this line. 
// Anything that you need to customize your scripts is above.
		
		System.out.println("Running " + paths.length + " clickpaths for " + repeats + " times");
		
		long start = System.currentTimeMillis();
		LinkedList<String> log = new LinkedList<String>();

		for(int i=0; i<repeats; i++) {
			long thisStart = System.currentTimeMillis();
			ClickpathBase path = null;
			int pos = (int)(Math.random()*cityUsers.length);
			String[] user = cityUsers[pos];
			pos = (int)(Math.random()*paths.length);
			path = paths[pos];
			System.out.println("Number of failures so far:" + log.size());
			System.out.println("#" + i + "/" + repeats + ": Running user " + user[0] + " with path " 
					+ pos + " (" + path.getClass().getSimpleName() + ", using "
					+ path.getDriver().getClass().getSimpleName() 
					+ ")" );
			path.setDefaultSleep(4000);
			try {
				path.run(user[0], user[1]);
			} catch(Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String tstamp = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH).format(LocalDateTime.now());
				log.add(
					tstamp + "\n" +
					""+i+", ["+ user[0] + ", path" + pos + "]\n" +
					e.getClass().getName() + " " +
					e.getMessage() + "\n" + 
					sw.toString()
				);
				try {
					path.writePageToDisk("ERROR", ""+i);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
			if(path != null) {
				path.quit();
				path = null;
			}
			long now = System.currentTimeMillis();
			long runtime = now-start;
			long expectedTimeSpan = (long)((runtime / (i+1)) * repeats);
			long thisTimeSpan = now - thisStart;
			Date expectedEnd = new Date(start+expectedTimeSpan);
			System.out.println("Runtime (current/average) in sec: " + ((long)(thisTimeSpan/1000)) + "/" + ((long)(runtime/((i+1)*1000))));
			System.out.println("Expected remaining run time:      " + expectedEnd);
			System.out.println("Current time:                     " + new Date());
		
			System.out.println();
		}
		
		System.out.println("==================================================");
		System.out.println("End of run");
		System.out.println("Failed attempts: " + log.size());
		for (String string : log) {
			System.out.println(string);
			System.out.println("---------------------------------------------");
		}
	}
	
	/**
	 * Read a stupidly simple CSV format: No title, content is just 
	 * rows with "name,password" (comma-separated, no escaping, no quotes)
	 * Luxury trimming done to individual entries without extra charge.
	 * 
	 * @param filename
	 * @return
	 */
	public static String[][] readUserCSV(String filename) {
		ArrayList<String[]> content = new ArrayList<String[]>();
		try (Scanner scanner = new Scanner(new File(filename))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				try (Scanner rowScanner = new Scanner(line)) {
					ArrayList<String> row = new ArrayList<String>(2);
					rowScanner.useDelimiter(",");
					while (rowScanner.hasNext()) {
						row.add(rowScanner.next().trim());
					}
					content.add(row.toArray(new String[row.size()]));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return (String[][]) content.toArray(new String[content.size()][]);
	}	
	
}