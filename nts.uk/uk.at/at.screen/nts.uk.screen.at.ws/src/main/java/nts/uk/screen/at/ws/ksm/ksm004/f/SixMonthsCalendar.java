package nts.uk.screen.at.ws.ksm.ksm004.f;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.shift.sixmonthscalendar.SixMonthsCalendarScreenProcessor;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarClassScreenDto;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarCompanyScreenDto;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarWorkPlaceScreenDto;

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
       @Path("sixmonthscalendarcompany/{startDate}/{endDate}")
    public List<SixMonthsCalendarCompanyScreenDto> getSixMonthsCompany(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        GeneralDate startDateFormat = GeneralDate.fromString(startDate, "yyyy-MM-dd");
        GeneralDate endDateFormat = GeneralDate.fromString(endDate, "yyyy-MM-dd");
        return this.processor.getSixMonthsCalendarCompany(new DatePeriod(startDateFormat, endDateFormat));
    }

    @POST
    @Path("sixmonthscalendarworkplace/{workPlaceId}/{startDate}/{endDate}")
    public List<SixMonthsCalendarWorkPlaceScreenDto> getSixMonthsWorkPlace(@PathParam("workPlaceId") String wordPlaceId, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        GeneralDate startDateFormat = GeneralDate.fromString(startDate, "yyyy-MM-dd");
        GeneralDate endDateFormat = GeneralDate.fromString(endDate, "yyyy-MM-dd");
        return this.processor.getSixMonthsCalendarWorkPlace(wordPlaceId, new DatePeriod(startDateFormat, endDateFormat));
    }

    @POST
    @Path("sixmonthscalendarclass/{classId}/{startDate}/{endDate}")
    public List<SixMonthsCalendarClassScreenDto> getSixMonthsClass(@PathParam("classId") String classId, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        GeneralDate startDateFormat = GeneralDate.fromString(startDate, "yyyy-MM-dd");
        GeneralDate endDateFormat = GeneralDate.fromString(endDate, "yyyy-MM-dd");
        return this.processor.getSixMonthsCalendarClass(classId, new DatePeriod(startDateFormat, endDateFormat));
    }
}
