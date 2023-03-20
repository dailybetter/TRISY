package com.c202.trisy.service;

import com.c202.trisy.dto.Area;
import com.c202.trisy.dto.MainCat;
import com.c202.trisy.dto.MiddleCat;
import com.c202.trisy.entity.Region;
import com.c202.trisy.entity.Theme;
import com.c202.trisy.repository.RegionRepository;
import com.c202.trisy.repository.ThemeRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {
    @Value("${trisy.serviceKey}")
    private String serviceKey;

    private final RegionRepository regionRepository;
    private final ThemeRepository themeRepository;
    public List<Area> getSi() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService/areaCode");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("20", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Area Response code: " + conn.getResponseCode());

        JsonObject obj = getConnectJsonObject(conn);

        JsonArray arr = obj.get("response").getAsJsonObject()
                .get("body").getAsJsonObject()
                .get("items").getAsJsonObject()
                .get("item").getAsJsonArray();
        List<Area> areaList = new ArrayList<>();
        for (JsonElement jsonElement : arr) {
            JsonObject temp = jsonElement.getAsJsonObject();
            Area area = Area.builder()
                    .siCode(temp.get("code") == null ? -1 :Integer.parseInt(temp.get("code").getAsString()))
                    .siName(temp.get("name") == null ? "wrong answer" :temp.get("name").getAsString())
                    .build();

            areaList.add(area);
        }

        for(Area area : areaList){
            System.out.println(area.getSiCode() + " " + area.getSiName());
        }

        return areaList;
    }



    public void saveRegion(List<Area> areaList) throws IOException {
        for(Area area : areaList){
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService/areaCode");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("35", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(area.getSiCode()), "UTF-8"));
            URL url = new URL(urlBuilder.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            System.out.println(area.getSiName() + " Region Response code: " + conn.getResponseCode());

            JsonObject obj = getConnectJsonObject(conn);

            JsonArray arr = obj.get("response").getAsJsonObject()
                    .get("body").getAsJsonObject()
                    .get("items").getAsJsonObject()
                    .get("item").getAsJsonArray();

            for (JsonElement jsonElement : arr) {
                JsonObject temp = jsonElement.getAsJsonObject();

                Region region = Region.builder()
                        .siCode(area.getSiCode())
                        .siName(area.getSiName())
                        .guGunCode(temp.get("code") == null ? -1 :Integer.parseInt(temp.get("code").getAsString()))
                        .guGunName(temp.get("name") == null ? "wrong answer" :temp.get("name").getAsString())
                        .build();

                regionRepository.save(region);
                System.out.println(region.toString());
            }

        }

    }
    public void saveTheme() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService/categoryCode");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("20", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("MainCat Response code: " + conn.getResponseCode());

        JsonObject obj = getConnectJsonObject(conn);

        JsonArray arr = obj.get("response").getAsJsonObject()
                .get("body").getAsJsonObject()
                .get("items").getAsJsonObject()
                .get("item").getAsJsonArray();
        List<MainCat> mainCatList = new ArrayList<>();
        for (JsonElement jsonElement : arr) {
            JsonObject temp = jsonElement.getAsJsonObject();
            MainCat mainCat = MainCat.builder()
                    .mainCode(temp.get("code") == null ? "wrong answer" :temp.get("code").getAsString())
                    .mainName(temp.get("name") == null ? "wrong answer" :temp.get("name").getAsString())
                    .build();

            mainCatList.add(mainCat);
        }
        saveThemeMiddle(mainCatList);

    }
    public void saveThemeMiddle(List<MainCat> mainCatList) throws IOException {
        for (MainCat mainCat : mainCatList) {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService/categoryCode");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode(mainCat.getMainCode(), "UTF-8"));
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            System.out.println("MiddleCat Response code: " + conn.getResponseCode());

            JsonObject obj = getConnectJsonObject(conn);

            JsonArray arr = obj.get("response").getAsJsonObject()
                    .get("body").getAsJsonObject()
                    .get("items").getAsJsonObject()
                    .get("item").getAsJsonArray();
            List<MiddleCat> middleCatList = new ArrayList<>();
            for (JsonElement jsonElement : arr) {
                JsonObject temp = jsonElement.getAsJsonObject();
                MiddleCat middleCat = MiddleCat.builder()
                        .mainCode(mainCat.getMainCode())
                        .mainName(mainCat.getMainName())
                        .middleCode(temp.get("code") == null ? "wrong answer" : temp.get("code").getAsString())
                        .middleName(temp.get("name") == null ? "wrong answer" : temp.get("name").getAsString())
                        .build();

                middleCatList.add(middleCat);
            }
            saveThemeSub(middleCatList);
        }
    }
    public void saveThemeSub(List<MiddleCat> middleCatList) throws IOException {
        for (MiddleCat middleCat : middleCatList) {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService/categoryCode");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode(middleCat.getMainCode(), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode(middleCat.getMiddleCode(), "UTF-8"));
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            System.out.println("subCat Response code: " + conn.getResponseCode());

            JsonObject obj = getConnectJsonObject(conn);

            JsonArray arr = obj.get("response").getAsJsonObject()
                    .get("body").getAsJsonObject()
                    .get("items").getAsJsonObject()
                    .get("item").getAsJsonArray();
            List<Theme> themeList = new ArrayList<>();
            for (JsonElement jsonElement : arr) {
                JsonObject temp = jsonElement.getAsJsonObject();
                Theme theme = Theme.builder()
                        .mainCategory(middleCat.getMainCode())
                        .mainName(middleCat.getMainName())
                        .middleCategory(middleCat.getMiddleCode())
                        .middleName(middleCat.getMiddleName())
                        .subCategory(temp.get("code") == null ? "wrong answer" : temp.get("code").getAsString())
                        .subName(temp.get("name") == null ? "wrong answer" : temp.get("name").getAsString())
                        .build();

                themeRepository.save(theme);
            }

        }
    }
    private JsonObject getConnectJsonObject(HttpURLConnection conn) throws IOException {
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(sb.toString()).getAsJsonObject();
        return obj;
    }
}
