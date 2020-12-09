package subway.utils;

import subway.domain.StationRepository;

public class Validator {
    public static boolean checkUsingStationName(String stationName) {
        return StationRepository.findStation(stationName);
    }

    public static boolean checkStationNameLength(String stationName) {
        if(stationName.length() > 1) return true;
        return false;
    }
}
