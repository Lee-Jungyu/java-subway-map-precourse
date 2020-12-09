package subway.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import subway.domain.Station;
import subway.domain.StationRepository;

public class ValidatorTest {

    @Test
    public void 역이름_중복확인_true() {
        String stationName = "강남역";
        Station station = new Station(stationName);
        StationRepository.addStation(station);
        boolean check = Validator.checkUsingStationName(stationName);
        Assertions.assertEquals(check, true);
    }

    @Test
    public void 역이름_중복확인_false() {
        String stationName = "강남역";
        String stationName2 = "강북역";
        Station station = new Station(stationName);
        StationRepository.addStation(station);
        boolean check = Validator.checkUsingStationName(stationName2);
        Assertions.assertEquals(check, false);
    }

    @Test
    public void 역이름_글자수확인_true() {
        String stationName = "강남역";
        boolean check = Validator.checkStationNameLength(stationName);
        Assertions.assertEquals(check, true);
    }

    @Test
    public void 역이름_글자수확인_false() {
        String stationName = "역";
        boolean check = Validator.checkStationNameLength(stationName);
        Assertions.assertEquals(check, false);
    }
}
