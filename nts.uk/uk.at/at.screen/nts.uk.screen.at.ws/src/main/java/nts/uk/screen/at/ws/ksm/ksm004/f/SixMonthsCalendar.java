package nts.uk.screen.at.ws.ksm.ksm004.f;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.shift.sixmonthscalendar.SixMonthsCalendarScreenProcessor;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarScreenDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("screen/at/ksm004/ksm004/f")
@Produces("application/json")
public class SixMonthsCalendar {

    @Inject
    private SixMonthsCalendarScreenProcessor processor;

    @POST
    @Path("sixmonthscalendar/{companyid}/{startDate}/{endDate}")
    public List<SixMonthsCalendarScreenDto> getSixMonthsCompany(@PathParam("companyid") String empId, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        GeneralDate startDateFormat = GeneralDate.fromString(startDate, "yyyy-MM-dd");
        GeneralDate endDateFormat = GeneralDate.fromString(endDate, "yyyy-MM-dd");
        return this.processor.getSixMonthsCalendar(empId, startDateFormat, endDateFormat);
    }
}
