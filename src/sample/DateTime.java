package sample;

import javafx.scene.input.DataFormat;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class DateTime {
    public static void time(){
        System.out.println(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

}
