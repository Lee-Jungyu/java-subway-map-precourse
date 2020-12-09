package subway.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import subway.domain.*;

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

    @Test
    public void 노선에_역이_있는지_확인_true() {
        String stationName = "강남역";
        Station station = new Station(stationName);
        Line line = new Line("2호선");
        Section section = new Section(station, line, 1);
        SectionRepository.addSection(section);

        boolean check = Validator.checkStationInLine(stationName);
        Assertions.assertEquals(check, true);
    }

    @Test
    public void 노선에_역이_있는지_확인_false() {
        String stationName = "강남역";
        String stationName2 = "강북역";
        Station station = new Station(stationName);
        Line line = new Line("2호선");
        Section section = new Section(station, line, 1);
        SectionRepository.addSection(section);

        boolean check = Validator.checkStationInLine(stationName2);
        Assertions.assertEquals(check, false);
    }
}
