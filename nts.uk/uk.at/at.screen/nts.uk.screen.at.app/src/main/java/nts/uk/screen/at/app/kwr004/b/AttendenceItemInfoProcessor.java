package nts.uk.screen.at.app.kwr004.b;

import nts.uk.screen.at.app.kwr003.AttendanceItemInfoDto;
import nts.uk.screen.at.app.kwr003.GetAttendanceItemInfo;
import nts.uk.screen.at.app.reservation.BentoReservationScreenRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * B：年間勤務台帳の出力項目設定
 */
@Stateless
public class AttendenceItemInfoProcessor {

    @Inject
    private BentoReservationScreenRepository bentoReservationScreenRepository;

    @Inject
    private GetAttendanceItemInfo getAttendanceItemInfo;

    public AttendanceItemInfoDto getInfo(RequestParams requestParams) {
        return getAttendanceItemInfo.getAttendanceItemInfo(requestParams.getFormNumberDisplay(),true,false);
    }
}
