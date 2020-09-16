package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

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
    private AgreementOneMonthTime OvertimeOneMonthAgo;

    /** 2ヶ月前の時間外時間*/
    private AgreementOneMonthTime OvertimeTwoMonthAgo;

    /** 2ヶ月平均の時間外時間*/
    private AgreementOneMonthTime OvertimeTwoMonthAverage;

    /** 3ヶ月前の時間外時間*/
    private AgreementOneMonthTime OvertimeThreeMonthAgo;

    /** 3ヶ月平均の時間外時間*/
    private AgreementOneMonthTime OvertimeThreeMonthAverage;

    /** 4ヶ月前の時間外時間*/
    private AgreementOneMonthTime OvertimeFourMonthAgo;

    /** 4ヶ月平均の時間外時間*/
    private AgreementOneMonthTime OvertimeFourMonthAverage;

    /** 5ヶ月前の時間外時間*/
    private AgreementOneMonthTime OvertimeFiveMonthAgo;

    /** 5ヶ月平均の時間外時間*/
    private AgreementOneMonthTime OvertimeFiveMonthAverage;

    /** 6ヶ月平均の時間外時間*/
    private AgreementOneMonthTime OvertimeSixMonthAverage;

    /** 対象月度の時間外時間*/
    private AgreementOneMonthTime OvertimeHoursTargetMonth;

}
