package nts.uk.file.at.app.export.bento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 予約確認一覧起動情報
 * @author tuan.ha1
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateOrderInfoDataSource {
    private List<String> workplaceIds;
    private List<String> workplaceCodes;
    private DatePeriod period;
    private int totalExtractCondition;
    private int itemExtractCondition;
    private int frameNo;
    private String totalTitle;
    private String detailTitle;
    private int reservationClosingTimeFrame;
    private boolean isBreakPage;
}
