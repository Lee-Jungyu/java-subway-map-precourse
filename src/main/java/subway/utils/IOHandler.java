package subway.utils;

import subway.domain.*;

import java.util.Scanner;

public class IOHandler {
    private Scanner scanner;

    public IOHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void inputStation() {
        String stationName;

        try {
            System.out.println("## 등록할 역 이름을 입력하세요.");
            stationName = scanner.next();

            boolean check = Validator.checkUsingStationName(stationName);
            if(check) throw new IllegalArgumentException("이미 등록된 역 이름입니다.");

            check = Validator.checkNameLength(stationName, 2);
            if(!check) throw new IllegalArgumentException("역 이름은 2글자 이상입니다.");

            Station station = new Station(stationName);
            StationRepository.addStation(station);
            printInfo("지하철 역이 등록되었습니다.");
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void removeStation() {
        String stationName;

        try {
            System.out.println("## 삭제할 역 이름을 입력하세요.");
            stationName = scanner.next();

            boolean check = Validator.checkUsingStationName(stationName);
            if(!check) throw new IllegalArgumentException("등록되지 않은 역입니다.");

            check = Validator.checkStationInLine(stationName);
            if(!check) throw new IllegalArgumentException("노선에 등록된 역은 삭제할 수 없습니다.");

            StationRepository.deleteStation(stationName);
            printInfo("지하철 역이 삭제되었습니다.");
       } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void printStations() {
        System.out.println("## 역 목록");

        for(Station station : StationRepository.stations()) {
            printInfo(station.getName());
        }
    }

    public void inputLine() {
        String lineName;
        String startStationName;
        String lastStationName;

        try {
            System.out.println("## 등록할 노선 이름을 입력하세요.");
            lineName = scanner.next();

            boolean check = Validator.checkUsingLineName(lineName);
            if (check) throw new IllegalArgumentException("이미 등록된 노선입니다.");

            check = Validator.checkNameLength(lineName, 2);
            if (!check) throw new IllegalArgumentException("노선 이름은 2글자 이상입니다.");

            System.out.println("## 등록할 노선의 상행 종점역 이름을 입력하세요.");
            startStationName = scanner.next();

            check = Validator.checkUsingStationName(startStationName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            System.out.println("## 등록할 노선의 하행 종점역 이름을 입력하세요.");
            lastStationName = scanner.next();

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
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void removeLine() {
        String lineName;

        try {
            System.out.println("## 삭제할 노선 이름을 입력하세요.");
            lineName = scanner.next();

            boolean check = Validator.checkUsingLineName(lineName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 노선입니다.");

            SectionRepository.deleteSectionByLineName(lineName);
            LineRepository.deleteLineByName(lineName);

            printInfo("지하철 노선이 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void printLines() {
        System.out.println("## 노선 목록");

        for(Line line : LineRepository.lines()) {
            printInfo(line.getName());
        }
    }

    public void inputSection() {
        String lineName;
        String stationName;
        String order;

        try {
            System.out.println("## 노선을 입력하세요.");
            lineName = scanner.next();

            boolean check = Validator.checkUsingLineName(lineName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 노선입니다.");

            System.out.println("## 역이름을 입력하세요.");
            stationName = scanner.next();

            check = Validator.checkUsingStationName(stationName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            System.out.println("## 순서를 입력하세요.");
            order = scanner.next();

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

            boolean check = Validator.checkUsingLineName(lineName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 노선입니다.");

            System.out.print("## 삭제할 구간의 역을 입력하세요.");
            stationName = scanner.next();

            check = Validator.checkUsingStationName(stationName);
            if (!check) throw new IllegalArgumentException("존재하지 않는 역입니다.");

            check = Validator.checkStationInLine(stationName, lineName);
            if (!check) throw new IllegalArgumentException("해당 노선에 " + stationName + "은 없습니다.");

            SectionRepository.deleteSection(stationName, lineName);

            printInfo("구간이 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
        }
    }

    public void printInfo(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public void printError(String msg) {
        System.err.println("[ERROR] " + msg);
    }
}
