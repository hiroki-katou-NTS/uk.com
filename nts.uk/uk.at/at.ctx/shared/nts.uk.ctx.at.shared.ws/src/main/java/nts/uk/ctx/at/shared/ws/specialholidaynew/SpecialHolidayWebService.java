package nts.uk.ctx.at.shared.ws.specialholidaynew;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.DeleteSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.SpecialHolidayCommand;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.SpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.SpecialHolidayDto;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.SpecialHolidayFinder;

/**
 * 
 * @author tanlv
 *
 */
@Path("shared/specialholidaynew")
@Produces("application/json")
public class SpecialHolidayWebService extends WebService{
	
	@Inject
	private SpecialHolidayFinder sphdFinder;
	
	@Inject
	private SpecialHolidayCommandHandler sphdHandler;
	
	@Inject
	private DeleteSpecialHolidayCommandHandler deleteSphdHandler;
	
	@Path("findByCid")
	@POST
	public List<SpecialHolidayDto> findByCid() {
		return sphdFinder.findByCompanyId();
	}
	
	@Path("getSpecialHoliday/{specialHolidayCode}")
	@POST
	public SpecialHolidayDto getSpecialHoliday(@PathParam("specialHolidayCode") int specialHolidayCode) {
		return sphdFinder.getSpecialHoliday(specialHolidayCode);
	}
	
	@Path("update")
	@POST
	public void add(SpecialHolidayCommand command) {
		sphdHandler.handle(command);
	}

	@Path("delete")
	@POST
	public void delete(SpecialHolidayCommand command) {
		deleteSphdHandler.handle(command);
	}
}
