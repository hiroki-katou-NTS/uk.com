package nts.uk.ctx.at.function.dom.adapter.supportworkdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;

@AllArgsConstructor
@Getter
public class AffiliationInforOfDailyPerforImport {
    //社員ID
    private String employeeId;
    //年月日
    private GeneralDate ymd;
    //所属情報
    private AffiliationInforOfDailyAttd affiliationInfor;
}
