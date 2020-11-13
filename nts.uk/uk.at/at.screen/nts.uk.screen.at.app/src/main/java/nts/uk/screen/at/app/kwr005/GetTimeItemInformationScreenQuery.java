package nts.uk.screen.at.app.kwr005;


import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.screen.at.app.kwr003.AttendanceItemInfoDto;
import nts.uk.screen.at.app.kwr003.GetAttendanceItemInfo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetTimeItemInformationScreenQuery {
    @Inject
    private GetAttendanceItemInfo getAttendanceItemInfo;

    public AttendanceItemInfoDto geInfo(int formNumberDisplay) {
        int MONTHLY = 2;
        val classification = EnumAdaptor.valueOf(MONTHLY, DailyMonthlyClassification.class);
        return getAttendanceItemInfo.getAttendanceItemInfo(classification, formNumberDisplay,false,true);
    }

}
