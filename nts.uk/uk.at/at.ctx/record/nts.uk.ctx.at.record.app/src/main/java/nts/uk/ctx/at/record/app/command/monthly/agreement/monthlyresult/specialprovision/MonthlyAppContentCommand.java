package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.MonthlyAppContent;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;

import java.util.Optional;

@Value
public class MonthlyAppContentCommand {
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
    Integer alarmTime;
    /**
     * 36協定特別条項の適用申請.申請時間.申請時間.1ヶ月時間.年月度
     */
    int yearMonth;
    /**
     * 36協定特別条項の適用申請.36協定申請理由
     */
    String reason;

    public MonthlyAppContent toMonthlyAppContent() {
        return new MonthlyAppContent(employeeId, new YearMonth(yearMonth), new AgreementOneMonthTime(errorTime),
                alarmTime == null ? Optional.empty() : Optional.of(new AgreementOneMonthTime(alarmTime)), new ReasonsForAgreement(reason));
    }
}
