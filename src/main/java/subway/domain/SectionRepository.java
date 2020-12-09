package subway.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SectionRepository {
    private static List<Section> sections = new ArrayList<>();

    public static List<Section> sections() {
        return sections;
    }

    public static void addSection(Section section) {
        for(Section s : sections) {
            if(s.getLineName().equals(section.getLineName()) && s.getOrder() >= section.getOrder()) {
                section.increaseOrder();
            }
        }
        sections.add(section);
    }

    public static void deleteSection(String stationName, String lineName) {
        int order = -1;
        for(int i = 0; i < sections.size(); i++) {
            Section section = sections.get(i);
            if(section.getStationName().equals(stationName) && section.getLineName().equals(lineName)) {
                order = section.getOrder();
                sections.remove(i);
            }
        }

        for(int i = 0; i < sections.size(); i++) {
            Section section = sections.get(i);
            if(section.getLineName().equals(lineName) && section.getOrder() > order) {
                section.decreaseOrder();
            }
        }
    }

    public static boolean findByStationName(String stationName) {
        for(Section section : sections) {
            if(section.getStationName().equals(stationName)) return true;
        }
        return false;
    }

    public static void sortSections() {
        Collections.sort(sections);
    }
}