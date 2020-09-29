package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;

/**
 * 申請時点の時間外時間（法定休出を含む）
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OvertimeIncludingHoliday {

    /** 1ヶ月前の時間外時間*/
    private AgreementOneMonthTime overtimeOneMonthAgo;

    /** 2ヶ月前の時間外時間*/
    private AgreementOneMonthTime overtimeTwoMonthAgo;

    /** 2ヶ月平均の時間外時間*/
    private AgreementOneMonthTime overtimeTwoMonthAverage;

    /** 3ヶ月前の時間外時間*/
    private AgreementOneMonthTime overtimeThreeMonthAgo;

    /** 3ヶ月平均の時間外時間*/
    private AgreementOneMonthTime overtimeThreeMonthAverage;

    /** 4ヶ月前の時間外時間*/
    private AgreementOneMonthTime overtimeFourMonthAgo;

    /** 4ヶ月平均の時間外時間*/
    private AgreementOneMonthTime overtimeFourMonthAverage;

    /** 5ヶ月前の時間外時間*/
    private AgreementOneMonthTime overtimeFiveMonthAgo;

    /** 5ヶ月平均の時間外時間*/
    private AgreementOneMonthTime overtimeFiveMonthAverage;

    /** 6ヶ月平均の時間外時間*/
    private AgreementOneMonthTime overtimeSixMonthAverage;

    /** 対象月度の時間外時間*/
    private AgreementOneMonthTime overtimeHoursTargetMonth;

}
