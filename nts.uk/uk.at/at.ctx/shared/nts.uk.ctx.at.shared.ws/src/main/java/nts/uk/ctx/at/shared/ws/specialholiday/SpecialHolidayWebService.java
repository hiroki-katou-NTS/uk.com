package nts.uk.ctx.at.shared.ws.specialholiday;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholiday.AddSpecialHolidayCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.AddSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.GrantDateComCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.GrantDateComCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.RemoveSpecialHolidayCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.RemoveSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.UpdateSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.find.specialholiday.GrantDateComDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.GrantDateSetDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.SpecialHolidayDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.SpecialHolidayFinder;


@Path("shared/specialholiday")
@Produces("application/json")
public class SpecialHolidayWebService extends WebService{
	
	@Inject
	private SpecialHolidayFinder specialHolidayFinder;
	
	@Inject
	private AddSpecialHolidayCommandHandler addSpecialHolidayCommandHandler;
	
	@Inject
	private UpdateSpecialHolidayCommandHandler updateSpecialHolidayCommandHandler;
	
	@Inject
	private RemoveSpecialHolidayCommandHandler removeSpecialHolidayCommandHandler;
	
	@Inject
	private GrantDateComCommandHandler grantDateComCommandHandler;
	
	@Path("findByCid")
	@POST
	public List<SpecialHolidayDto> findByCid() {
		return specialHolidayFinder.findAllSpecialHoliday();
	}
	
	@Path("findRagular")
	@POST
	public List<SpecialHolidayDto> findRagular() {
		return specialHolidayFinder.findAllSpecialHoliday();
	}
	
	@Path("add")
	@POST
	public JavaTypeResult<List<String>> add(AddSpecialHolidayCommand command) {
		return new JavaTypeResult<List<String>>(this.addSpecialHolidayCommandHandler.handle(command));
	}

	@Path("update")
	@POST
	public void update(AddSpecialHolidayCommand command) {
		this.updateSpecialHolidayCommandHandler.handle(command);
	}

	@Path("delete")
	@POST
	public void delete(RemoveSpecialHolidayCommand command) {
		this.removeSpecialHolidayCommandHandler.handle(command);
	}
	
	@Path("getComByCode/{specialHolidayCode}")
	@POST
	public GrantDateComDto getComByCode(@PathParam("specialHolidayCode") String specialHolidayCode) {
		return this.specialHolidayFinder.getComByCode(specialHolidayCode);
	}
	
	@Path("getAllSetByCode/{specialHolidayCode}")
	@POST
	public List<GrantDateSetDto> getAllSetByCode(@PathParam("specialHolidayCode") String specialHolidayCode) {
		return this.specialHolidayFinder.getAllSetByCode(specialHolidayCode);
	}
	
	@Path("addGrantDateCom")
	@POST
	public void add(GrantDateComCommand command) {
		this.grantDateComCommandHandler.handle(command);
	}
}
