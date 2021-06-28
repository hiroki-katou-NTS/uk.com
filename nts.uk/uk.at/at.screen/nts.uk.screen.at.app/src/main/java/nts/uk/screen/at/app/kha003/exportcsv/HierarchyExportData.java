package nts.uk.screen.at.app.kha003.exportcsv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.DisplayInformation;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.SummaryItemDetail;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.VerticalValueDaily;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class HierarchyExportData {
    HierarchyDetailInfo level1;
    HierarchyDetailInfo level2;
    HierarchyDetailInfo level3;
    HierarchyDetailInfo level4;
}

@AllArgsConstructor
@Getter
class VerticalValueDailyDto {
    private String date;
    private int workingHours;
}

@AllArgsConstructor
@Getter
class HierarchyDetailInfo {
    private List<DisplayInformation> lstDispInfo;
    private List<VerticalValueDailyDto> lstVerticalValue;
    private int verticalTotal;
    private int horizontalTotal;
}
