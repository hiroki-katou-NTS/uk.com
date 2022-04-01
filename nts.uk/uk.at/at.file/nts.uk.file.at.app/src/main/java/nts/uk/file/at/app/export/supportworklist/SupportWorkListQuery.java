package nts.uk.file.at.app.export.supportworklist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class SupportWorkListQuery {
    /** 集計単位 */
    private int aggregationUnit;

    /** 応援勤務表コード */
    private String supportWorkCode;

    /** 期間 */
    private GeneralDate periodStart;
    private GeneralDate periodEnd;

    /** 職場リスト */
    private List<String> workplaceIds;

    /** <optional>基準日 */
    private String baseDate;

    /** 場所リスト */
    private List<String> workLocationCodes;

    /** ヘッダ情報<List> */
    private List<SupportWorkHeaderInfo> headerInfos;

    /**
     * true => save as Csv
     * false => save as Excel
     */
    private boolean exportCsv;

    public DatePeriod getPeriod() {
        return new DatePeriod(this.periodStart, this.periodEnd);
    }

    public Optional<GeneralDate> baseDateOpt() {
        return StringUtils.isEmpty(this.baseDate) ? Optional.empty() : Optional.of(GeneralDate.fromString(this.baseDate, "yyyy/MM/dd"));
    }
}
