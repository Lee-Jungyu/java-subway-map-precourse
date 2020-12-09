package subway.utils;

import subway.domain.SectionRepository;
import subway.domain.StationRepository;

public class Validator {
    public static boolean checkUsingStationName(String stationName) {
        return StationRepository.findStation(stationName) >= 0;
    }

    public static boolean checkStationNameLength(String stationName) {
        return stationName.length() > 1;
    }

    public static boolean checkStationInLine(String stationName) {
        return SectionRepository.findByStationName(stationName);
    }
}
