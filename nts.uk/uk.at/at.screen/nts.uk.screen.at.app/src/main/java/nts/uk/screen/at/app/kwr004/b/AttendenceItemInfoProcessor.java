package nts.uk.screen.at.app.kwr004.b;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.GetAttendanceIdByFormNumberQuery;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.screen.at.app.reservation.BentoReservationScreenRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AttendenceItemInfoProcessor {

    @Inject
    private GetAttendanceIdByFormNumberQuery query;

    @Inject
    private BentoReservationScreenRepository bentoReservationScreenRepository;

    public void getAttendenceItemInfo(RequestParams requestParams) {
        List<Integer> attendenceId = query.getAttendanceId(EnumAdaptor.valueOf(requestParams.getDailyMonthlyClass(), DailyMonthlyClassification.class),requestParams.getScreenNumber());


    }

}
