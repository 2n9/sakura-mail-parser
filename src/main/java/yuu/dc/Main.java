package yuu.dc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "./exe/chromedriver.exe");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //alive
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                WebDriver driver = new ChromeDriver();
                driver.get("https://secure.sakura.ad.jp/rs/cp/logging/processes");
                while (true) {
                    try {
                        Thread.sleep(1000*30);
                        List<WebElement> elems = driver.findElements(By.tagName("div"));
                        String cache = "";
                        for (WebElement el : elems){
                            if(el.getAttribute("class").equals("username")){
                                //System.out.println(el.getText().split("@")[0] + "@dummy");
                                cache = el.getText().split("@")[0] + "@dummy";
                            }
                            if(el.getAttribute("class").equals("lists-col capacity")){
                                boolean uses = !el.getText().split(" ")[0].equals("0B");
                                if(uses) {
                                    System.out.println(cache);
                                }
                            }
                        }
                        System.out.println("=========");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        List<mth> mths = new ArrayList<>();
        mths.add(new mth("dummy.txt"));
        System.out.println("Start " + mths.size() + "s threads");
        mths.forEach(Thread::start);
    }

}
