package com.local.tech.dtgapp;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class WebControls {
    String loginPath = "http://ddosthegame.com/index.php?page=login";
    String AttackPath = "http://ddosthegame.com/index.php?page=hub";
    String RandomAtkPath = "http://ddosthegame.com/index.php?page=hub&watsdis=1";
    String HashingPath = "http://ddosthegame.com/index.php?page=hashing&random=1";
    String MarketPath = "http://ddosthegame.com/index.php?page=market";
    String StorePath = "http://ddosthegame.com/index.php?page=store";
    String HighscorePath = "http://ddosthegame.com/index.php?page=highscore";
    String Groups = "http://ddosthegame.com/index.php?page=group";
    String GroupAtk = "http://ddosthegame.com/index.php?page=grouphub";
    String RandomGroupAtk = "http://ddosthegame.com/index.php?page=grouphub&random=1";
    String Missions = "http://ddosthegame.com/index.php?page=missions";
    String Resolve = "http://ddosthegame.com/index.php?page=resolve";
    String FAQ = "http://ddosthegame.com/index.php?page=faq";
    CookieManager cm = new CookieManager();
    CookieStore cookieStore;
    List<HttpCookie> cookieList;

    public Boolean login(String username, String password){
        try{
            System.out.println("Hit Login:"+username+":"+password);
            URL logUrl = new URL(loginPath);
            cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cm);
            HttpURLConnection logConn = (HttpURLConnection)logUrl.openConnection();
            logConn.setRequestMethod("POST");
            logConn.setConnectTimeout(25000);
            logConn.setReadTimeout(45000);

            logConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
            logConn.setRequestProperty("Accept-Language", "en-US,en;q=0.8,en-US;q=0.6,en;q=0.4");
            logConn.setRequestProperty("Connection", "keep-alive");
            logConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            logConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            logConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            logConn.setRequestProperty("Referer", "http://ddosthegame.com/index.php?page=login");
            System.out.println(logConn.getErrorStream());
            int response2 = logConn.getResponseCode();
            System.out.println("The response is: " + response2);
            String Parameters = "username="+username+"&password="+password+"&login_today=Sign+in+";

            logConn.setDoOutput(true);


            DataOutputStream wr = new DataOutputStream(logConn.getOutputStream());
            wr.writeBytes(Parameters);
            wr.flush();
            wr.close();

            int responseCode = logConn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(logConn.getInputStream())));
            String inputLine;
            while((inputLine = in.readLine()) != null){
                Pattern re = Pattern.compile("Success");
                Matcher m = re.matcher(inputLine);
                int mIdx = 0;
                while(m.find()){
                    for(int groupIdx = 0; groupIdx < m.groupCount()+1; groupIdx++){
                        Map<String, List<String>> headers = logConn.getHeaderFields();
                        cookieStore = cm.getCookieStore();
                        cookieList = cookieStore.getCookies();
                        return true;
                    }
                    mIdx++;
                }
            }
            in.close();
            logConn.disconnect();
            return false;

        }catch(MalformedURLException  MURLE){
            System.out.println("Login, MalFormed: "+MURLE.getMessage());
            return false;
        }catch(Exception exc){
            System.out.println("Login, Exception: "+exc.getMessage());
            return false;

        }
    }
    public void HubAttack(Boolean random, String IP, int port, int time){
        if(!random){
            String para = "derp=&host="+IP+"&port="+port+"&time="+time+"&send_fertilizier=";
            String reg = "Attack successfully sent";
            sendPostRequest(AttackPath, AttackPath, para, reg);
        }else{
            String para = "derp=&host="+getIp()+"&port="+port+"&time="+time+"&send_fertilizier=";
            String reg = "Attack successfully sent";
            sendPostRequest(RandomAtkPath, RandomAtkPath, para, reg);
        }
    }
    public void Hash(Boolean Redeem){
        if(!Redeem){
            sendPostRequest(HashingPath, HashingPath, "send_botpls=", "Hashing started");
        }else{
            sendPostRequest(HashingPath, HashingPath, "claim_all=Claim+All", "Withdraw(s) complete");
        }
    }


    public String getIp(){

        String getIp;
        String Reg = "name=\"host\" value=\"[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}\"";
        getIp = readPage(RandomAtkPath, RandomAtkPath, Reg);
        getIp = getIp.replace("name=", "");
        getIp = getIp.replace("value=","");
        getIp = getIp.replace("\"","");
        getIp = getIp.replace("host","");
        getIp = getIp.replaceAll("\\s+", "");

        return getIp;
    }


    public String readPage(String URL, String Ref, String RegEx){
        try {
            URL readPage = new URL(URL);

            cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cm);

            HttpURLConnection Conn = (HttpURLConnection)readPage.openConnection();
            Conn.setRequestMethod("POST");
            Conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
            Conn.setRequestProperty("Accept-Language", "en-US,en;q=0.8,en-US;q=0.6,en;q=0.4");
            Conn.setRequestProperty("Connection", "keep-alive");
            Conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            Conn.setRequestProperty("Accept-Encoding","gzip, deflate");
            Conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            Conn.setRequestProperty("Referer",Ref);
            int responseCode = Conn.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(Conn.getInputStream())));
            String inputLine;

            while((inputLine = in.readLine()) != null){
                Pattern re = Pattern.compile(RegEx);
                Matcher m = re.matcher(inputLine);
                int mIdx = 0;
                while(m.find()){
                    for(int groupIdx = 0; groupIdx < m.groupCount()+1; groupIdx++){
                        cookieStore = cm.getCookieStore();
                        cookieList = cookieStore.getCookies();
                        return m.group(groupIdx);
                    }
                    mIdx++;
                }
            }
            in.close();
            return null;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }catch(Exception exc){
            System.out.println("Exception 2: "+exc.getMessage());
            return null;
        }
    }
    public void sendPostRequest(String url, String Ref, String parameters,String RegEx){
        try{
            URL logUrl = new URL(url);
            cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cm);

            HttpURLConnection Conn = (HttpURLConnection)logUrl.openConnection();
            Conn.setRequestMethod("POST");
            Conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
            Conn.setRequestProperty("Accept-Language", "en-US,en;q=0.8,en-US;q=0.6,en;q=0.4");
            Conn.setRequestProperty("Connection", "keep-alive");
            Conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            Conn.setRequestProperty("Accept-Encoding","gzip, deflate");
            Conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            Conn.setRequestProperty("Referer",Ref);


            String Parameters = parameters;

            Conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(Conn.getOutputStream());
            wr.writeBytes(Parameters);
            wr.flush();
            wr.close();

            int responseCode = Conn.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(Conn.getInputStream())));
            String inputLine;

            while((inputLine = in.readLine()) != null){
                Pattern re = Pattern.compile(RegEx);
                Matcher m = re.matcher(inputLine);
                int mIdx = 0;
                while(m.find()){
                    for(int groupIdx = 0; groupIdx < m.groupCount()+1; groupIdx++){
                        Map<String, List<String>> headers = Conn.getHeaderFields();
                        cookieStore = cm.getCookieStore();
                        cookieList = cookieStore.getCookies();
                    }
                    mIdx++;
                }
            }
            in.close();

        }catch(MalformedURLException  MURLE){
            System.out.println("Post, MalFormed 2: "+MURLE.getMessage());
        }catch(Exception exc){
            System.out.println("Post, Exception: "+exc.getMessage());
        }
    }

    public String getLoginPath(){return this.loginPath;}
}
