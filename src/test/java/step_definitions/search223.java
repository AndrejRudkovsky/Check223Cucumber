package step_definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;

import java.util.ArrayList;
import java.util.List;

public class search223 {
    private WebDriver driver;
    public search223()
    {
        driver = ChromeIni.driver;
    }

    private ArrayList<String> resultList = new ArrayList<String>();
    private ArrayList<String> resultListCanceled = new ArrayList<String>();

    @Given("^Открываем 223.rts-tender.ru/supplier/auction/Trade/Search.aspx$")
    public void i_open_223search_website() throws Throwable {
        driver.get("https://223.rts-tender.ru/supplier/auction/Trade/Search.aspx");
        driver.manage().window().maximize();
    }
    @And("^Устанавливаем дату публикации$")
    public void i_navigate_to_publication_date() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.ignoring(WebDriverException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#BaseMainContent_MainContent_txtPublicationDate_txtDateFrom")));
        driver.findElement(By.cssSelector("#BaseMainContent_MainContent_txtPublicationDate_txtDateFrom")).sendKeys("15.04.2019");
        driver.findElement(By.cssSelector("#BaseMainContent_MainContent_txtPublicationDate_txtDateTo")).sendKeys("15.04.2019");
    }

    @And("^Чекаем Только 223$")
    public void i_click_only_223() throws Throwable {
        driver.findElement(By.cssSelector("label[for='BaseMainContent_MainContent_chkPurchaseType_0']")).click();
    }

    @And("^Чекаем Коммерческие$")
    public void i_click_commercial() throws Throwable {
        driver.findElement(By.cssSelector("label[for='BaseMainContent_MainContent_chkPurchaseType_1']")).click();
    }

    @And("^Устанавливаем начальную цену = 0$")
    public void i_enter_start_price() throws Throwable {
        driver.findElement(By.cssSelector("#BaseMainContent_MainContent_txtStartPrice_txtRangeFrom")).sendKeys("0");
    }

    @And("^Забираем результаты отбора у которых есть номер ЕИС")  /*-- TODO  ИСПРАВИТЬ ЭТО ГОВНО НА НОРМАЛЬНОЕ ОЖИДАНИЕ НЕСООТВЕТСТВИЯ СТРОК--*/
    public void get_seach_result() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver,10);
        List<WebElement> rows;
        List<WebElement> cells;
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#jqgh_BaseMainContent_MainContent_jqgTrade_ApplicationEndDate")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby='BaseMainContent_MainContent_jqgTrade_OrganizerName']")));
        int y = Integer.parseInt(driver.findElement(By.cssSelector("#sp_1_BaseMainContent_MainContent_jqgTrade_toppager")).getText());
        for (int i=0;i<y;i++)
        {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#jqgh_BaseMainContent_MainContent_jqgTrade_ApplicationEndDate")));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby='BaseMainContent_MainContent_jqgTrade_OrganizerName']")));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby='BaseMainContent_MainContent_jqgTrade_TradeName']")));
            rows = driver.findElements(By.cssSelector("table.ui-jqgrid-btable tr.ui-widget-content"));
            for (WebElement row: rows) {
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby='BaseMainContent_MainContent_jqgTrade_TradeName']")));
                cells = row.findElements(By.cssSelector("tr.ui-widget-content td:nth-child(6)"));
                for (WebElement cell: cells) {
                    if (!cell.getAttribute("title").equals("")){
                        resultList.add(cell.getText()+"  price  "+row.findElement(By.cssSelector("tr.ui-widget-content td:nth-child(11)")).getText());
                    }
                }
            }
            Thread.sleep(1000);
        }
    }


    @And("^Переходим на страницу отмененых закупок$")
    public void i_go_to_canceled_tenders() throws Throwable {
        driver.findElement(By.cssSelector("ul.ui-tabs-nav a[value='4']")).click();
    }

    /*--------------*/
    @And("^Забираем результаты отбора у которых есть номер ЕИС с статусом Отменен$")
    public void get_seach_result_on_page_returned() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver,10);
        List<WebElement> rows;
        List<WebElement> cells;
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#jqgh_BaseMainContent_MainContent_jqgTrade_ApplicationEndDate")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby='BaseMainContent_MainContent_jqgTrade_OrganizerName']")));
        /*-----*/
        int y = Integer.parseInt(driver.findElement(By.cssSelector("#sp_1_BaseMainContent_MainContent_jqgTrade_toppager")).getText());
        for (int i=0;i<y;i++)
        {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#jqgh_BaseMainContent_MainContent_jqgTrade_ApplicationEndDate")));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby='BaseMainContent_MainContent_jqgTrade_OrganizerName']")));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby='BaseMainContent_MainContent_jqgTrade_TradeName']")));
            rows = driver.findElements(By.cssSelector("table.ui-jqgrid-btable tr.ui-widget-content"));
            for (WebElement row: rows) {
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby='BaseMainContent_MainContent_jqgTrade_TradeName']")));
                cells = row.findElements(By.cssSelector("tr.ui-widget-content td:nth-child(6)"));
                for (WebElement cell: cells) {
                    if (!cell.getAttribute("title").equals("")){
                        resultListCanceled.add(cell.getText()+"  price  "+row.findElement(By.cssSelector("tr.ui-widget-content td:nth-child(11)")).getText());
                    }
                }
            }
            Thread.sleep(1500);
        }
    }

    @Then("^Нажимаем Поиск$")
    public void i_click_search_buttom() throws Throwable {
        driver.findElement(By.cssSelector("#BaseMainContent_MainContent_btnSearch")).click();
    }

    @Then("^Отображаем резльтат$")
        public void show_final_result() throws Throwable {
        System.out.println("resultList = " + resultList);
        System.out.println("resultListCanceled = " + resultListCanceled);
        resultList.removeAll(resultListCanceled);
        System.out.println("FinalList = " + resultList);
    }

    @And("^Записываем результат в файл$")
    public void write_final_result() throws Throwable {
        FileWriter finalList = new FileWriter("D:\\Autotest\\Check223Cucumber\\223_final.txt");
        for (String i: resultList) {
            finalList.write(i+"\n");
        }
        finalList.close();
    }
}
