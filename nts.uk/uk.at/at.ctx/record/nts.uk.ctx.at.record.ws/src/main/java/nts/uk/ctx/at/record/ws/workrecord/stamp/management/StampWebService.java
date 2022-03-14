package nts.uk.ctx.at.record.ws.workrecord.stamp.management;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultDto;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultFinder;

/**
 * 
 * @author tutk
 *
 */
@Path("at/record/workrecord/stamp/management/")
@Produces("application/json")
public class StampWebService extends WebService {
	
	@Inject
	private DisplayScreenStampingResultFinder displayScreenStampingResultFinder;
	
	@POST 
	@Path("getAllStampingResult")
	public List<DisplayScreenStampingResultDto> getDisplay(GetStampInfoInput input){
		
		int date = input.getRegionalTimeDifference() / 1440;
		
		DatePeriod datePerriod = new DatePeriod(GeneralDate.today().addDays(-3).addDays(date), GeneralDate.today().addDays(date));
		GeneralDateTime changeTime = GeneralDateTime.now().addMinutes(input.getRegionalTimeDifference());
		
		if (changeTime.day() != GeneralDateTime.now().day()) {
			if (GeneralDateTime.now().before(changeTime)) {
				datePerriod = new DatePeriod(GeneralDate.today().addDays(-3).addDays(date + 1), GeneralDate.today().addDays(date + 1));
			}
			if (GeneralDateTime.now().after(changeTime)) {
				datePerriod = new DatePeriod(GeneralDate.today().addDays(-3).addDays(date - 1), GeneralDate.today().addDays(date - 1));
			}
		}
		
		
		List<DisplayScreenStampingResultDto> data = displayScreenStampingResultFinder.getDisplay(datePerriod, input.getEmployeeId());
		return data;
	}
}
