package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AnnualAppContent;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;

@Value
public class AnnualAppContentCommand {
    /**
     * 対象者ID
     */
    String employeeId;
    /**
     * 36協定特別条項の適用申請.申請時間.1ヶ月時間
     */
    int errorTime;
    /**
     * 36協定特別条項の適用申請.申請時間.1ヶ月時間
     */
    int alarmTime;
    /**
     * 36協定特別条項の適用申請.申請時間.申請時間.年間時間.年度
     */
    int year;
    /**
     * 36協定特別条項の適用申請.36協定申請理由
     */
    String reason;

    public AnnualAppContent toAnnualAppContent() {
        return new AnnualAppContent(employeeId, new Year(year), new AgreementOneYearTime(errorTime),
                new AgreementOneYearTime(alarmTime), new ReasonsForAgreement(reason));
    }
}
