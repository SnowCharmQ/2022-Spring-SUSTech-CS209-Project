package com.cs209.project.mapper;

import com.cs209.project.entity.SpringBootIssueVersion;
import com.cs209.project.service.ISpringBootService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;

@org.springframework.boot.test.context.SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootTest {

    private final String[] arr = {"2013-10", "2013-11", "2013-12", "2014-01", "2014-02", "2014-03", "2014-04", "2014-05", "2014-06", "2014-07", "2014-08", "2014-09", "2014-10", "2014-11", "2014-12", "2015-01", "2015-02", "2015-03", "2015-04", "2015-05", "2015-06", "2015-07", "2015-08", "2015-09", "2015-10", "2015-11", "2015-12", "2016-01", "2016-02", "2016-03", "2016-04", "2016-05", "2016-06", "2016-07", "2016-08", "2016-09", "2016-10", "2016-11", "2016-12", "2017-01", "2017-02", "2017-03", "2017-04", "2017-05", "2017-06", "2017-07", "2017-08", "2017-09", "2017-10", "2017-11", "2017-12", "2018-01", "2018-02", "2018-03", "2018-04", "2018-05", "2018-06", "2018-07", "2018-08", "2018-09", "2018-10", "2018-11", "2018-12", "2019-01", "2019-02", "2019-03", "2019-04", "2019-05", "2019-06", "2019-07", "2019-08", "2019-09", "2019-10", "2019-11", "2019-12", "2020-01", "2020-02", "2020-03", "2020-04", "2020-05", "2020-06", "2020-07", "2020-08", "2020-09", "2020-10", "2020-11", "2020-12", "2021-01", "2021-02", "2021-03", "2021-04", "2021-05", "2021-06", "2021-07", "2021-08", "2021-09", "2021-10", "2021-11", "2021-12", "2022-01", "2022-02", "2022-03", "2022-04", "2022-05"};

    @Test
    public void test() {
        System.out.println(arr.length);
    }

    @Autowired
    private ISpringBootService springBootService;

    @Test
    public void closedIssueTest() throws IOException {
        File f = new File("src/main/resources/static/js/SpringBootClosedIssue.js");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write("open_issue_history = [[\"issue_count\", \"version\", \"date\"],");
        List<SpringBootIssueVersion> list = springBootService.selectClosedIssueVersion();
        HashMap<String, Integer> map = new HashMap<>();
        TreeMap<String, List<SpringBootIssueVersion>> listHashMap = new TreeMap<>();
        for (SpringBootIssueVersion sbiv : list) {
            int cnt = sbiv.getCount();
            if (!map.containsKey(sbiv.getVersion())) map.put(sbiv.getVersion(), sbiv.getCount());
            else {
                cnt = map.get(sbiv.getVersion());
                cnt = cnt + sbiv.getCount();
                map.put(sbiv.getVersion(), cnt);
            }
            List<SpringBootIssueVersion> ans;
            if (!listHashMap.containsKey(sbiv.getVersion())) ans = new ArrayList<>();
            else ans = listHashMap.get(sbiv.getVersion());
            ans.add(new SpringBootIssueVersion(sbiv.getVersion(), sbiv.getYear(), sbiv.getMonth(), cnt));
            listHashMap.put(sbiv.getVersion(), ans);
        }
        List<Map.Entry<String, List<SpringBootIssueVersion>>> entries = listHashMap.entrySet().stream().toList();
        System.out.println(entries.size());
        for (Map.Entry<String, List<SpringBootIssueVersion>> e : entries) {
            String v = e.getKey();
            List<SpringBootIssueVersion> sbivs = e.getValue();
            int j = 0, k = -1, m = 0;
            while (j < arr.length) {
                String actual = arr[j];
                SpringBootIssueVersion sbiv;
                if (m < sbivs.size()) sbiv = sbivs.get(m);
                else {
                    bw.write(String.format("[%d,\"%s\",\"%s\"],", sbivs.get(k).getCount(), v, actual));
                    j++;
                    continue;
                }
                if (sbiv.getTime().equals(actual)) {
                    k = m;
                    m++;
                    bw.write(String.format("[%d,\"%s\",\"%s\"],", sbivs.get(k).getCount(), v, actual));
                } else {
                    if (k == -1) bw.write(String.format("[%d,\"%s\",\"%s\"],", 0, v, actual));
                    else bw.write(String.format("[%d,\"%s\",\"%s\"],", sbivs.get(k).getCount(), v, actual));
                }
                j++;
            }
        }
        bw.write("]");
        bw.close();
    }

    @Test
    public void openIssueTest() throws IOException {
        File f = new File("src/main/resources/static/js/SpringBootOpenIssue.js");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write("open_issue_history = [[\"issue_count\", \"version\", \"date\"],");
        List<SpringBootIssueVersion> list = springBootService.selectOpenIssueVersion();
        HashMap<String, Integer> map = new HashMap<>();
        TreeMap<String, List<SpringBootIssueVersion>> listHashMap = new TreeMap<>();
        for (SpringBootIssueVersion sbiv : list) {
            int cnt = sbiv.getCount();
            if (!map.containsKey(sbiv.getVersion())) map.put(sbiv.getVersion(), sbiv.getCount());
            else {
                cnt = map.get(sbiv.getVersion());
                cnt = cnt + sbiv.getCount();
                map.put(sbiv.getVersion(), cnt);
            }
            List<SpringBootIssueVersion> ans;
            if (!listHashMap.containsKey(sbiv.getVersion())) ans = new ArrayList<>();
            else ans = listHashMap.get(sbiv.getVersion());
            ans.add(new SpringBootIssueVersion(sbiv.getVersion(), sbiv.getYear(), sbiv.getMonth(), cnt));
            listHashMap.put(sbiv.getVersion(), ans);
        }
        List<Map.Entry<String, List<SpringBootIssueVersion>>> entries = listHashMap.entrySet().stream().toList();
        System.out.println(entries.size());
        for (Map.Entry<String, List<SpringBootIssueVersion>> e : entries) {
            String v = e.getKey();
            List<SpringBootIssueVersion> sbivs = e.getValue();
            int j = 0, k = -1, m = 0;
            while (j < arr.length) {
                String actual = arr[j];
                SpringBootIssueVersion sbiv;
                if (m < sbivs.size()) sbiv = sbivs.get(m);
                else {
                    bw.write(String.format("[%d,\"%s\",\"%s\"],", sbivs.get(k).getCount(), v, actual));
                    j++;
                    continue;
                }
                if (sbiv.getTime().equals(actual)) {
                    k = m;
                    m++;
                    bw.write(String.format("[%d,\"%s\",\"%s\"],", sbivs.get(k).getCount(), v, actual));
                } else {
                    if (k == -1) bw.write(String.format("[%d,\"%s\",\"%s\"],", 0, v, actual));
                    else bw.write(String.format("[%d,\"%s\",\"%s\"],", sbivs.get(k).getCount(), v, actual));
                }
                j++;
            }
        }
        bw.write("]");
        bw.close();
    }

    @Test
    public void generateArr() {
        for (int i = 2013; i <= 2022; i++) {
            for (int j = 1; j <= 12; j++) {
                if (j < 10) System.out.print("\"" + i + "-0" + j + "\",");
                else System.out.print("\"" + i + "-" + j + "\",");
            }
        }
    }

    @Test
    public void versionTest() {
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/com/cs209/project/file/SpringBootIteration.txt")))) {
            String line;
            while ((line = inline.readLine()) != null) {
                String version = inline.readLine().trim();
                String time = line.substring(0, 10).trim();
                String str = String.format("""
                        <div class="row">
                        <span class="col">%s</span>
                        <span class="col">%s</span>
                        </div>""", version, time);
                System.out.println(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void versionIterTest(){
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/com/cs209/project/file/SpringBootIteration.txt")))) {
            String line;
            while ((line = inline.readLine()) != null) {
                String version = inline.readLine().trim();
                String time = line.substring(0, 10).trim();
                System.out.println(version + "," + time);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
