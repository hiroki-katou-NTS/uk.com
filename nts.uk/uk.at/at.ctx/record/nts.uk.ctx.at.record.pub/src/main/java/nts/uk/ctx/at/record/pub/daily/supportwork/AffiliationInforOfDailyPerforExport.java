package nts.uk.ctx.at.record.pub.daily.supportwork;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;

@AllArgsConstructor
@Getter
public class AffiliationInforOfDailyPerforExport {
    //社員ID
    private String employeeId;
    //年月日
    private GeneralDate ymd;
    //所属情報
    private AffiliationInforOfDailyAttd affiliationInfor;
}
