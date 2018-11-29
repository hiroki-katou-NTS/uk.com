/**
 * 4:27:34 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.ws.shift.businesscalendar.holiday;

//import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday.CreatePublicHolidayCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday.CreatePublicHolidayCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday.DeletePublicHolidayCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday.DeletePublicHolidayCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday.UpdatePublicHolidayCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday.UpdatePublicHolidayCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.holiday.PublicHolidayDto;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.holiday.PublicHolidayFinder;

/**
 * @author hungnm
 *
 */
@Path("at/schedule/holiday")
@Produces("application/json")
public class PublicHolidayWebService extends WebService {

	@Inject
	private PublicHolidayFinder publicHolidayFinder;

	@Inject
	private CreatePublicHolidayCommandHandler createPublicHoliday;

	@Inject
	private UpdatePublicHolidayCommandHandler updatePublicHoliday;

	@Inject
	private DeletePublicHolidayCommandHandler deletePublicHoliday;

	@POST
	@Path("getHolidayByListDate")
	public List<PublicHolidayDto> getHolidayByListDate(List<String> lstDate) {
		return this.publicHolidayFinder.getHolidaysByListDate(lstDate);
	}

	@POST
	@Path("getAllHoliday")
	public List<PublicHolidayDto> getAllHolidays() {
		return this.publicHolidayFinder.getAllHolidays();
	}
	
	@POST
	@Path("getHolidayByDate/{date}")
	public Optional<PublicHolidayDto> getHolidayByDate(@PathParam("date") String date){
		return this.publicHolidayFinder.getHolidayByDate(date);
	}

	@POST
	@Path("create")
	public void createPublicHoliday(CreatePublicHolidayCommand command) {
		this.createPublicHoliday.handle(command);
	}

	@POST
	@Path("update")
	public void updatePublicHoliday(UpdatePublicHolidayCommand command) {
		this.updatePublicHoliday.handle(command);
	}

	@POST
	@Path("delete")
	public void deletePublicHoliday(DeletePublicHolidayCommand command) {
		this.deletePublicHoliday.handle(command);
	}

}
