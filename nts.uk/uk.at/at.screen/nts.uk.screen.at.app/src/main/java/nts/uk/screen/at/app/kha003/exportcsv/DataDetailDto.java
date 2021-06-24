package nts.uk.screen.at.app.kha003.exportcsv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.DisplayInformation;

import java.util.List;

@AllArgsConstructor
@Getter
public class DataDetailDto {
    private List<DisplayInformation> lstDispInfo;
    private List<VerticalValueDailyDto> lstVerticalValue;
    private int totalPeriod;
}

@AllArgsConstructor
@Getter
class VerticalValueDailyDto {
    private String date;
    private int workingHours;
}
