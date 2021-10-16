package yuu.dc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mth extends Thread implements Runnable {

    List<String> mails;

    public mth(String std) throws IOException {
        mails = new ArrayList<>();
        File f = new File(std);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        while (line != null) {
            mails.add(line);
            line = br.readLine();
        }
        br.close();
    }

    @Override
    public void run() {
        try {
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.dcuniverseinfinite.com/register");
            Thread.sleep(10000);  // Let the user actually see something!
            //System.out.println(driver.getPageSource());
            // data-v-93fae3a6
            //WebElement searchBox = driver.findElement(By.);
            List<WebElement> elems = driver.findElements(By.tagName("input"));

            for (int i = 0; i < mails.size(); i++) {
                String str = mails.get(i);

                Thread.sleep(5000);

                elems = driver.findElements(By.tagName("input"));
                for (WebElement el : elems) {
                    if (el.getAttribute("id").equals("email")) {
                        el.click();
                        el.sendKeys(str);
                        System.out.println("[#" + this.getId() + "] complete ( " + i + " / " + mails.size() + " ) " + str);
                    }

                    if (el.getAttribute("id").equals("password")) {
                        el.click();
                        el.sendKeys("@dummy password");
                    }
                }

                Thread.sleep(1000);
                boolean first = true;

                elems = driver.findElements(By.tagName("span"));
                for (WebElement el : elems) {
                    if (el.getAttribute("class").equals("simple_check") && first) {
                        first = false;
                        continue;
                    }
                    if (el.getAttribute("class").equals("simple_check")) {
                        el.click();
                    }
                }

                Thread.sleep(1000);

                elems = driver.findElements(By.tagName("button"));

                for (WebElement el : elems) {
                    if (el.getAttribute("class").equals("button button--fullwidth button--dark")) {
                        el.click();
                        break;
                    }
                }

                Thread.sleep(10000);

                List<String> divs = new ArrayList<>();
                driver.findElements(By.tagName("div")).forEach(tt -> divs.add(tt.getText()));
                for (String str2 : divs) {
                    if (str2.equals("Email already exists.")) {
                        System.out.println("[#" + this.getId() + "] " + str + " > " + str2);
                        break;
                    } else if (str2.equalsIgnoreCase("unknown error.")) {
                        for (int j = 0; j < 5; j++) {
                            List<WebElement> elems2 = driver.findElements(By.tagName("button"));
                            for (WebElement el : elems2) {
                                if (el.getAttribute("class").equals("button button--fullwidth button--dark")) {
                                    el.click();
                                    break;
                                }
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                Thread.sleep(10000);

                driver.get("https://www.dcuniverseinfinite.com/logout");

                Thread.sleep(5000);

                driver.get("https://www.dcuniverseinfinite.com/register");

                Thread.sleep(5000);
            }
        } catch (
                Exception ex) {

        }
    }
}
