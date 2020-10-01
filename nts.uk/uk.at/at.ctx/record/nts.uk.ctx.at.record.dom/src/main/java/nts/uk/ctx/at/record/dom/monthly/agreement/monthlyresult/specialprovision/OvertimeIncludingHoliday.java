package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;

/**
 * 申請時点の時間外時間（法定休出を含む）
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class OvertimeIncludingHoliday {

    /** 対象月度の時間外時間*/
    private final AgreementOneMonthTime overtimeHoursTargetMonth;

    /** 1ヶ月前の時間外時間*/
    private final AgreementOneMonthTime overtimeOneMonthAgo;

    /** 2ヶ月前の時間外時間*/
    private final AgreementOneMonthTime overtimeTwoMonthAgo;

    /** 2ヶ月平均の時間外時間*/
    private final AgreementOneMonthTime overtimeTwoMonthAverage;

    /** 3ヶ月前の時間外時間*/
    private final AgreementOneMonthTime overtimeThreeMonthAgo;

    /** 3ヶ月平均の時間外時間*/
    private final AgreementOneMonthTime overtimeThreeMonthAverage;

    /** 4ヶ月前の時間外時間*/
    private final AgreementOneMonthTime overtimeFourMonthAgo;

    /** 4ヶ月平均の時間外時間*/
    private final AgreementOneMonthTime overtimeFourMonthAverage;

    /** 5ヶ月前の時間外時間*/
    private final AgreementOneMonthTime overtimeFiveMonthAgo;

    /** 5ヶ月平均の時間外時間*/
    private final AgreementOneMonthTime overtimeFiveMonthAverage;

    /** 6ヶ月平均の時間外時間*/
    private final AgreementOneMonthTime overtimeSixMonthAverage;

    /**
     * [C-0] 申請時点の時間外時間（法定休出を含む）
     */
    public static OvertimeIncludingHoliday create(AgreementOneMonthTime overtimeHoursTargetMonth,AgreementOneMonthTime overtimeOneMonthAgo,
            AgreementOneMonthTime overtimeTwoMonthAgo,AgreementOneMonthTime overtimeTwoMonthAverage,AgreementOneMonthTime overtimeThreeMonthAgo,
            AgreementOneMonthTime overtimeThreeMonthAverage,AgreementOneMonthTime overtimeFourMonthAgo,AgreementOneMonthTime overtimeFourMonthAverage,
            AgreementOneMonthTime overtimeFiveMonthAgo,AgreementOneMonthTime overtimeFiveMonthAverage,AgreementOneMonthTime overtimeSixMonthAverage
    ) {
        return new OvertimeIncludingHoliday(overtimeHoursTargetMonth, overtimeOneMonthAgo,overtimeTwoMonthAgo,overtimeTwoMonthAverage,
                overtimeThreeMonthAgo, overtimeThreeMonthAverage,overtimeFourMonthAgo,overtimeFourMonthAverage,
                overtimeFiveMonthAgo, overtimeFiveMonthAverage,overtimeSixMonthAverage);
    }

}
