/*
 * Copyright 2016-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.joinfaces.example.view;


import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.PageFactory;

import org.springframework.boot.context.embedded.LocalServerPort;

/**
 * Abstract class to create utility methods to access WebClient and Page.
 * @author Marcelo Fernandes
 */
public class AbstractPageIT {

	@LocalServerPort
	private long port;

	private static WebDriver webDriver;

	private static int countFinish = 0;

	private static final int NUMBER_OF_SUBCLASSES = 5;

	@BeforeClass
	public static void init() {
		if (webDriver == null) {
			webDriver = getHtmlUnitDriver();
			//webDriver = getChromeDriver();
			//webDriver = getFirefoxDriver();
		}
	}

	private static WebDriver getHtmlUnitDriver() {
		return new HtmlUnitDriver(true);
	}

	/*
	private static WebDriver getChromeDriver() {
		ChromeDriverManager.getInstance().setup();
		return new ChromeDriver();
	}

	private static WebDriver getFirefoxDriver() {
		MarionetteDriverManager.getInstance().setup();
		return new FirefoxDriver();
	}
	*/

	@AfterClass
	public static void finish() {
		countFinish++;
		if (countFinish == NUMBER_OF_SUBCLASSES) {
			webDriver.close();
			webDriver.quit();
		}
	}

	protected <T extends AbstractPageComponent> T initElements(Class<T> classx) {
		T result = PageFactory.initElements(webDriver, classx);
		result.setPreffix(getPreffix());
		return result;
	}

	private String getPreffix() {
		return "http://localhost:" + port;
	}

}
