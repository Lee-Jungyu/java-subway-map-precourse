package subway.utils;

import subway.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class IOHandler {
    private Scanner scanner;

    public IOHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public String printMainMenu() {
        System.out.println("## 메인 화면");
        System.out.println("1. 역 관리");
        System.out.println("2. 노선 관리");
        System.out.println("3. 구간 관리");
        System.out.println("4. 지하철 노선도 출력");
        System.out.println("Q. 돌아가기");
        System.out.println();

        while(true) {
            System.out.println("원하는 기능을 선택하세요.");
            String menu = scanner.next();

            System.out.println();

            String[] strings = {"1", "2", "3", "4", "Q"};
            boolean check = Validator.checkValidValue(menu, Arrays.asList(strings.clone()));
            if(check) return menu;

            printError("선택할 수 없는 기능입니다.");
        }
    }

    public String printStationMenu() {
        System.out.println("## 역 관리 화면");
        System.out.println("1. 역 등록");
        System.out.println("2. 역 삭제");
        System.out.println("3. 역 조회");
        System.out.println("B. 돌아가기");
        System.out.println();

        while(true) {
            System.out.println("원하는 기능을 선택하세요.");
            String menu = scanner.next();

            System.out.println();

            String[] strings = {"1", "2", "3", "B"};
            boolean check = Validator.checkValidValue(menu, Arrays.asList(strings.clone()));
            if(check) return menu;


            printError("선택할 수 없는 기능입니다.");
        }
    }

    public String printLineMenu() {
        System.out.println("## 노선 관리 화면");
        System.out.println("1. 노선 등록");
        System.out.println("2. 노선 삭제");
        System.out.println("3. 노선 조회");
        System.out.println("B. 돌아가기");
        System.out.println();

        while(true) {
            System.out.println("원하는 기능을 선택하세요.");
            String menu = scanner.next();

            System.out.println();

            String[] strings = {"1", "2", "3", "B"};
            boolean check = Validator.checkValidValue(menu, Arrays.asList(strings.clone()));
            if(check) return menu;

            printError("선택할 수 없는 기능입니다.");
        }
    }

    public String printSectionMenu() {
        System.out.println("## 구간 관리 화면");
        System.out.println("1. 구간 등록");
        System.out.println("2. 구간 삭제");
        System.out.println("B. 돌아가기");
        System.out.println();

        while(true) {
            System.out.println("원하는 기능을 선택하세요.");
            String menu = scanner.next();

            System.out.println();

            String[] strings = {"1", "2", "B"};
            boolean check = Validator.checkValidValue(menu, Arrays.asList(strings.clone()));
            if(check) return menu;

            printError("선택할 수 없는 기능입니다.");
        }
    }

    public void printStationsInLines() {
        System.out.println("## 지하철 노선도");

        SectionRepository.sortSections();

        for(Line line : LineRepository.lines()) {
            String lineName = line.getName();

            printInfo(lineName);
            printInfo("---");

            List<Station> stations = SectionRepository.findStationsByLineName(lineName);

            for(Station station : stations)
                printInfo(station.getName());

            System.out.println();
        }
    }

    public void inputStation() {
        String stationName;

        try {
            System.out.println("## 등록할 역 이름을 입력하세요.");
            stationName = scanner.next();

            System.out.println();

            boolean check = Validator.checkUsingStationName(stationName);
            if(check) throw new IllegalArgumentException("이미 등록된 역 이름입니다.");

            check = Validator.checkNameLength(stationName, 2);
            if(!check) throw new IllegalArgumentException("역 이름은 2글자 이상입니다.");

            Station station = new Station(stationName);
            StationRepository.addStation(station);
            printInfo("지하철 역이 등록되었습니다.");

            System.out.println();
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void removeStation() {
        String stationName;

        try {
            System.out.println("## 삭제할 역 이름을 입력하세요.");
            stationName = scanner.next();

            System.out.println();

            boolean check = Validator.checkUsingStationName(stationName);
            if(!check) throw new IllegalArgumentException("등록되지 않은 역입니다.");

            check = Validator.checkStationInLine(stationName);
            if(check) throw new IllegalArgumentException("노선에 등록된 역은 삭제할 수 없습니다.");

            StationRepository.deleteStation(stationName);
            printInfo("지하철 역이 삭제되었습니다.");

            System.out.println();
       } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void printStations() {
        System.out.println("## 역 목록");

        for(Station station : StationRepository.stations()) {
            printInfo(station.getName());
        }
        System.out.println();
    }

    public void inputLine() {
        String lineName;
        String startStationName;
        String lastStationName;

        try {
            System.out.println("## 등록할 노선 이름을 입력하세요.");
            lineName = scanner.next();

            System.out.println();

            boolean check = Validator.checkUsingLineName(lineName);
            if (check) throw new IllegalArgumentException("이미 등록된 노선입니다.");

            check = Validator.checkNameLength(lineName, 2);
            if (!check) throw new IllegalArgumentException("노선 이름은 2글자 이상입니다.");

            System.out.println("## 등록할 노선의 상행 종점역 이름을 입력하세요.");
            startStationName = scanner.next();

            System.out.println();

            check = Validator.checkUsingStationName(startStationName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            System.out.println("## 등록할 노선의 하행 종점역 이름을 입력하세요.");
            lastStationName = scanner.next();

            System.out.println();

            check = Validator.checkUsingStationName(lastStationName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            Line line = new Line(lineName);
            LineRepository.addLine(line);

            Station station1 = StationRepository.getStationByName(startStationName);
            Station station2 = StationRepository.getStationByName(lastStationName);

            Section section1 = new Section(station1, line, 1);
            Section section2 = new Section(station2, line, 2);
            SectionRepository.addSection(section1);
            SectionRepository.addSection(section2);

            printInfo("지하철 노선이 등록되었습니다");

            System.out.println();
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void removeLine() {
        String lineName;

        try {
            System.out.println("## 삭제할 노선 이름을 입력하세요.");
            lineName = scanner.next();

            System.out.println();

            boolean check = Validator.checkUsingLineName(lineName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 노선입니다.");

            SectionRepository.deleteSectionByLineName(lineName);
            LineRepository.deleteLineByName(lineName);

            printInfo("지하철 노선이 삭제되었습니다.");

            System.out.println();

        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void printLines() {
        System.out.println("## 노선 목록");

        for(Line line : LineRepository.lines()) {
            printInfo(line.getName());
        }

        System.out.println();
    }

    public void inputSection() {
        String lineName;
        String stationName;
        String order;

        try {
            System.out.println("## 노선을 입력하세요.");
            lineName = scanner.next();

            System.out.println();

            boolean check = Validator.checkUsingLineName(lineName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 노선입니다.");

            System.out.println("## 역이름을 입력하세요.");
            stationName = scanner.next();

            System.out.println();

            check = Validator.checkUsingStationName(stationName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            System.out.println("## 순서를 입력하세요.");
            order = scanner.next();

            System.out.println();

            check = Validator.checkIntegerType(order);
            if (!check) throw new IllegalArgumentException("순서는 숫자만 입력 가능합니다.");

            int minNumber = 1;
            int maxNumber = LineRepository.getLineByName(lineName).getStationCount();
            check = Validator.checkIntegerRange(Integer.parseInt(order), minNumber, maxNumber);
            if (!check) throw new IllegalArgumentException(minNumber + "이상 " + maxNumber + "이하의 숫자만 가능합니다.");

            Station station = StationRepository.getStationByName(stationName);
            Line line = LineRepository.getLineByName(lineName);

            Section section = new Section(station, line, Integer.parseInt(order));
            SectionRepository.addSection(section);

            printInfo("구간이 등록되었습니다.");

            System.out.println();
        } catch(IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void removeSection() {
        String lineName;
        String stationName;

        try {
            System.out.println("## 삭제할 구간의 노선을 입력하세요.");
            lineName = scanner.next();

            System.out.println();

            boolean check = Validator.checkUsingLineName(lineName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 노선입니다.");

            System.out.print("## 삭제할 구간의 역을 입력하세요.");
            stationName = scanner.next();

            System.out.println();

            check = Validator.checkUsingStationName(stationName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            check = Validator.checkStationInLine(stationName, lineName);
            if (!check) throw new IllegalArgumentException("해당 노선에 " + stationName + "은 없습니다.");

            SectionRepository.deleteSection(stationName, lineName);

            printInfo("구간이 삭제되었습니다.");

            System.out.println();
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void printInfo(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public void printError(String msg) {
        System.err.println("[ERROR] " + msg);
        System.out.println();
    }
}
