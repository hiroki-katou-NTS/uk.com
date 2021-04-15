package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DetailOfArbitrarySchedule {
    List<AttendanceItemDisplayContents> contentsList;
    List<AttendanceDetailDisplayContents> detailDisplayContents;

    List<WorkplaceTotalDisplayContent> totalDisplayContents;

    List<DisplayContent> totalAll;

    List<CumulativeWorkplaceDisplayContent> cumulativeWorkplaceDisplayContents;
}
