package nts.uk.ctx.at.record.ws.kdw008;

import nts.uk.ctx.at.record.app.find.kdw008.AcquisitionOfMonthlyAttendanceItemFinder;
import nts.uk.ctx.at.shared.app.find.scherec.attitem.AttItemParam;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;


@Path("at/record/kdw/008")
@Produces("application/json")
public class Kdw008WebService {
    @Inject
    private AcquisitionOfMonthlyAttendanceItemFinder attendanceItemFinder;

    @POST
    @Path("modifyAnyPeriodAttItems")
    public List<AttItemName> getModifyAnyPeriodAttItems() {
        return this.attendanceItemFinder.getMonthlyAttendaceItem();
    }
}
