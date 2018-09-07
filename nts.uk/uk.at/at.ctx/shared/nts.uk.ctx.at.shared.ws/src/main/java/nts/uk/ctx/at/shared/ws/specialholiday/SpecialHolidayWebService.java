package nts.uk.ctx.at.shared.ws.specialholiday;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholiday.CreateSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.DeleteSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.EditSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.SpecialHolidayCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.SpecialHolidayDeleteCommand;
import nts.uk.ctx.at.shared.app.find.specialholiday.SpecialHolidayDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.SpecialHolidayFinder;
import nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe.SpecialHolidayFrameDto;

/**
 * 
 * @author tanlv
 *
 */
@Path("shared/specialholiday")
@Produces("application/json")
public class SpecialHolidayWebService extends WebService {

	@Inject
	private SpecialHolidayFinder sphdFinder;

	@Inject
	private CreateSpecialHolidayCommandHandler add;

	@Inject
	private EditSpecialHolidayCommandHandler update;

	@Inject
	private DeleteSpecialHolidayCommandHandler deleteSphdHandler;

	@Path("findByCid")
	@POST
	public List<SpecialHolidayDto> findByCid() {
		return sphdFinder.findByCompanyId();
	}

	@Path("findForScreenJ/{selectedCode}")
	@POST
	public List<SpecialHolidayFrameDto> findForScreenJ(@PathParam("selectedCode") int selectedCode) {
		return sphdFinder.findForScreenJ(selectedCode);
	}
	
	@Path("findAllItemFrame")
	@POST
	public List<SpecialHolidayFrameDto> findAllItemFrame() {
		return sphdFinder.findAllItemFrame();
	}

	@Path("getSpecialHoliday/{specialHolidayCode}")
	@POST
	public SpecialHolidayDto getSpecialHoliday(@PathParam("specialHolidayCode") int specialHolidayCode) {
		return sphdFinder.getSpecialHoliday(specialHolidayCode);
	}

	@Path("add")
	@POST
	public JavaTypeResult<List<String>> add(SpecialHolidayCommand command) {
		return new JavaTypeResult<List<String>>(add.handle(command));
	}

	@Path("update")
	@POST
	public JavaTypeResult<List<String>> update(SpecialHolidayCommand command) {
		return new JavaTypeResult<List<String>>(update.handle(command));
	}

	@Path("delete")
	@POST
	public void delete(SpecialHolidayDeleteCommand command) {
		deleteSphdHandler.handle(command);
	}
}
