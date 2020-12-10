package subway;

import subway.domain.*;
import subway.utils.IOHandler;

import java.util.Scanner;

public class SubwayApplication {

    private IOHandler ioHandler;

    public SubwayApplication(Scanner scanner) {
        this.ioHandler = new IOHandler(scanner);
    }

    public void startApplication() {
        setInitialize();
        String selectedMenu = "0";
        while(!selectedMenu.equals("Q")) {
            selectedMenu = ioHandler.printMainMenu();

            if(selectedMenu.equals("1"))
                doStationManaging();
            if(selectedMenu.equals("2"))
                doLineManaging();
            if(selectedMenu.equals("3"))
                doSectionManaging();
            if(selectedMenu.equals("4"))
                ioHandler.printStationsInLines();

        }
    }

    public void doStationManaging() {
        String selectedMenu = ioHandler.printStationMenu();

        if(selectedMenu.equals("1"))
            ioHandler.inputStation();
        if(selectedMenu.equals("2"))
            ioHandler.removeStation();
        if(selectedMenu.equals("3"))
            ioHandler.printStations();

    }

    public void doLineManaging() {
        String selectedMenu = ioHandler.printLineMenu();

        if(selectedMenu.equals("1"))
            ioHandler.inputLine();
        if(selectedMenu.equals("2"))
            ioHandler.removeLine();
        if(selectedMenu.equals("3"))
            ioHandler.printLines();

    }

    public void doSectionManaging() {
        String selectedMenu = ioHandler.printSectionMenu();

        if(selectedMenu.equals("1"))
            ioHandler.inputSection();
        if(selectedMenu.equals("2"))
            ioHandler.removeSection();

    }

    public void setInitialize() {
        Station station1 = new Station("교대역");
        StationRepository.addStation(station1);
        Station station2 = new Station("강남역");
        StationRepository.addStation(station2);
        Station station3 = new Station("역삼역");
        StationRepository.addStation(station3);
        Station station4 = new Station("남부터미널역");
        StationRepository.addStation(station4);
        Station station5 = new Station("양재역");
        StationRepository.addStation(station5);
        Station station6 = new Station("양재시민의숲역");
        StationRepository.addStation(station6);
        Station station7 = new Station("매봉역");
        StationRepository.addStation(station7);

        Line line1 = new Line("2호선");
        LineRepository.addLine(line1);
        Line line2 = new Line("3호선");
        LineRepository.addLine(line2);
        Line line3 = new Line("신분당선");
        LineRepository.addLine(line3);

        Section section1 = new Section(station1, line1, 1);
        SectionRepository.addSection(section1);
        Section section2 = new Section(station2, line1, 2);
        SectionRepository.addSection(section2);
        Section section3 = new Section(station3, line1, 3);
        SectionRepository.addSection(section3);
        Section section4 = new Section(station1, line2, 1);
        SectionRepository.addSection(section4);
        Section section5 = new Section(station4, line2, 2);
        SectionRepository.addSection(section5);
        Section section6 = new Section(station5, line2, 3);
        SectionRepository.addSection(section6);
        Section section7 = new Section(station7, line2, 4);
        SectionRepository.addSection(section7);
        Section section8 = new Section(station2, line3, 1);
        SectionRepository.addSection(section8);
        Section section9 = new Section(station5, line3, 2);
        SectionRepository.addSection(section9);
        Section section10 = new Section(station6, line3, 3);
        SectionRepository.addSection(section10);
    }
}