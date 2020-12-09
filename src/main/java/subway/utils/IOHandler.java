package subway.utils;

import subway.domain.Station;
import subway.domain.StationRepository;

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

            check = Validator.checkStationNameLength(stationName);
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

    public void printInfo(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public void printError(String msg) {
        System.err.println("[ERROR] " + msg);
    }
}
