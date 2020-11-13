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
        int DAILY = 1;
        DailyMonthlyClassification classification = EnumAdaptor.valueOf(DAILY,DailyMonthlyClassification.class);
        return getAttendanceItemInfo.getAttendanceItemInfo(classification,prams.getFormNumberDisplay(),true,false);
    }
}