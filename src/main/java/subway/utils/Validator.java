package subway.utils;

import subway.domain.LineRepository;
import subway.domain.SectionRepository;
import subway.domain.StationRepository;

public class Validator {
    public static boolean checkUsingStationName(String stationName) {
        return StationRepository.findStation(stationName) >= 0;
    }

    public static boolean checkUsingLineName(String lineName) {
        return LineRepository.findLine(lineName) >= 0;
    }

    public static boolean checkNameLength(String stationName, int length) {
        return stationName.length() >= length;
    }

    public static boolean checkStationInLine(String stationName) {
        return SectionRepository.findByStationName(stationName);
    }
}
