import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static ArrayList<WebLog> logList = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        getCsv();


        while(true){
            String command = bfr.readLine();
            if(command.compareTo("read webLog.csv") == 0)
                getCsv();
            else if(command.compareTo("sort -t") == 0) {
                sortByTime();
                printResult(0);
            }else if(command.compareTo("sort -ip") == 0) {
                sortByIp();
                printResult(1);
            }
        }
    }

    public static void printResult(int type){
        Iterator it = logList.iterator();
        final int TIME = 0;
        while(it.hasNext()) {
            WebLog log = (WebLog) it.next();
            if(type == TIME){
                System.out.println(log.time);
                System.out.println("IP: "+ log.ip);
            }else{
                System.out.println(log.ip);
                System.out.println("Time: "+ log.time);
            }
            System.out.println(log);
        }
    }

    public static void sortByIp(){
        logList.sort((WebLog w1, WebLog w2) -> {
            int compareIp = w1.ip.compareTo(w2.ip);
            if (compareIp == 0)
                return w1.time.compareTo(w2.time);
             else
                return compareIp;
        });
    }

    public static void sortByTime(){
        logList.sort((WebLog w1, WebLog w2) -> w1.time.compareTo(w2.time));
    }

    public static String fromMMMtoMM(String beforeDate) throws ParseException {
        SimpleDateFormat beforeFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.US);
        SimpleDateFormat changeFormat = new SimpleDateFormat("yyyy/MM/dd" ,Locale.US);
        return changeFormat.format(beforeFormat.parse(beforeDate));
    }

    public static void getCsv(){
        BufferedReader bfr;
        try{
            bfr = Files.newBufferedReader(Paths.get("/Users/leesanghoon/Desktop/학교생활/알고리즘/4주차/pa-04-dl57934/src/webLog.csv"));
            Charset.forName("UTF-8");
            String line;
            int i = 0;
            final int FIRST_INDEX = 0;
            while((line = bfr.readLine()) != null){
                if(i != FIRST_INDEX) {
                    String[] elements = line.split(",");
                    String ip = elements[0];
                    String originTime = elements[1];
                    String time = fromMMMtoMM(originTime.substring(1, 12)) + originTime.substring(12);
                    String url = elements[2];
                    String status = elements[3];
                    logList.add(new WebLog(ip, time, originTime, url, status));
                }else i+=1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class WebLog{
    String ip, originTime, url, status, time;
    public WebLog(String ip, String time, String originTime, String url, String status){
        this.ip = ip;
        this.time = time;
        this.originTime= originTime;
        this.url = url;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Url: "+url +"\nStatus: "+status;
    }
}