package nts.uk.screen.at.app.kwr003;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class GetAttendanceItemInfoScreenQuery {
    @Inject
    private GetAttendanceItemInfo getAttendanceItemInfo;

    public AttendanceItemInfoDto getInfo( AttendanceItemInfoPrams prams) {
        return getAttendanceItemInfo.getAttendanceItemInfo(prams.getFormNumberDisplay(),true,false);
    }
}