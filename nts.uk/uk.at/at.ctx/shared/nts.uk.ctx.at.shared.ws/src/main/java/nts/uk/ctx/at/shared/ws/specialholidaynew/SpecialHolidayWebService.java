package nts.uk.ctx.at.shared.ws.specialholidaynew;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.CreateSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.DeleteSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.EditSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.SpecialHolidayCommand;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.SpecialHolidayDeleteCommand;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.SpecialHolidayDtoNew;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.SpecialHolidayFinderNew;

/**
 * 
 * @author tanlv
 *
 */
@Path("shared/specialholidaynew")
@Produces("application/json")
public class SpecialHolidayWebService extends WebService{
	
	@Inject
	private SpecialHolidayFinderNew sphdFinder;
	
	@Inject
	private CreateSpecialHolidayCommandHandler add;
	
	@Inject
	private EditSpecialHolidayCommandHandler update;
	
	@Inject
	private DeleteSpecialHolidayCommandHandler deleteSphdHandler;
	
	@Path("findByCid")
	@POST
	public List<SpecialHolidayDtoNew> findByCid() {
		return sphdFinder.findByCompanyId();
	}
	
	@Path("getSpecialHoliday/{specialHolidayCode}")
	@POST
	public SpecialHolidayDtoNew getSpecialHoliday(@PathParam("specialHolidayCode") int specialHolidayCode) {
		return sphdFinder.getSpecialHoliday(specialHolidayCode);
	}
	
	@Path("add")
	@POST
	public void add(SpecialHolidayCommand command) {
		add.handle(command);
	}
	
	@Path("update")
	@POST
	public void update(SpecialHolidayCommand command) {
		update.handle(command);
	}

	@Path("delete")
	@POST
	public void delete(SpecialHolidayDeleteCommand command) {
		deleteSphdHandler.handle(command);
	}
}
