package nts.uk.screen.at.app.kwr005;


import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.screen.at.app.kwr003.AttendanceItemInfoDto;
import nts.uk.screen.at.app.kwr003.GetAttendanceItemInfo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Collections;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetTimeItemInformationScreenQuery {
    @Inject
    private GetAttendanceItemInfo getAttendanceItemInfo;

    public AttendanceItemInfoDto geInfo(int formNumberDisplay) {
        val listMonthly = getAttendanceItemInfo.getAttendanceItemInfo(DailyMonthlyClassification.MONTHLY, formNumberDisplay);
        return new AttendanceItemInfoDto(Collections.emptyList(),listMonthly);
    }

}
