package nts.uk.screen.at.app.kwr003;

import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;

@Stateless
public class GetAttendanceItemInfoScreenQuery {
    @Inject
    private GetAttendanceItemInfo getAttendanceItemInfo;

    public AttendanceItemInfoDto getInfo(AttendanceItemInfoPrams prams) {
        val listDaily = getAttendanceItemInfo.getAttendanceItemInfo(DailyMonthlyClassification.DAILY, prams.getFormNumberDisplay());
        return new AttendanceItemInfoDto(listDaily, Collections.emptyList());
    }
}