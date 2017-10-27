package nts.uk.ctx.at.shared.ws.specialholiday;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholiday.AddGrantDatePerCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.AddSpecialHolidayCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.AddSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.GrantDateComCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.GrantDateComCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.GrantDatePerCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.RemoveGrantDatePerCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.RemoveGrantDatePerCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.RemoveSpecialHolidayCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.RemoveSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.UpdateGrantDatePerCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.UpdateSpecialHolidayCommandHandler;
import nts.uk.ctx.at.shared.app.find.specialholiday.GrantDateComDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.GrantDatePerDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.GrantDatePerSetDto;
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
	
	@Inject
	private AddGrantDatePerCommandHandler addGrantDatePerCommandHandler;
	
	@Inject
	private UpdateGrantDatePerCommandHandler updateGrantDatePerCommandHandler;
	
	@Inject
	private RemoveGrantDatePerCommandHandler removeGrantDatePerCommandHandler;
	
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
	public JavaTypeResult<List<String>> update(AddSpecialHolidayCommand command) {
		return new JavaTypeResult<List<String>>(this.updateSpecialHolidayCommandHandler.handle(command));
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
	public JavaTypeResult<List<String>> add(GrantDateComCommand command) {
		return new JavaTypeResult<List<String>>(this.grantDateComCommandHandler.handle(command));
	}
	
	@Path("getPerByCode/{specialHolidayCode}/{personalGrantDateCode}")
	@POST
	public GrantDatePerDto getPerByCode(@PathParam("specialHolidayCode") String specialHolidayCode, @PathParam("personalGrantDateCode") String personalGrantDateCode) {
		return this.specialHolidayFinder.getPerByCode(specialHolidayCode, personalGrantDateCode);
	}
	
	@Path("getPerSetByCode/{specialHolidayCode}/{personalGrantDateCode}")
	@POST
	public List<GrantDatePerSetDto> getPerSetByCode(@PathParam("specialHolidayCode") String specialHolidayCode, @PathParam("personalGrantDateCode") String personalGrantDateCode) {
		return this.specialHolidayFinder.getPerSetByCode(specialHolidayCode, personalGrantDateCode);
	}
	
	@Path("addPer")
	@POST
	public JavaTypeResult<List<String>> addPer(GrantDatePerCommand command) {
		return new JavaTypeResult<List<String>>(this.addGrantDatePerCommandHandler.handle(command));
	}
	
	@Path("updatePer")
	@POST
	public JavaTypeResult<List<String>> updatePer(GrantDatePerCommand command) {
		return new JavaTypeResult<List<String>>(this.updateGrantDatePerCommandHandler.handle(command));
	}
	
	@Path("getAllPerByCode/{specialHolidayCode}")
	@POST
	public List<GrantDatePerDto> getAllPerByCode(@PathParam("specialHolidayCode") String specialHolidayCode) {
		return this.specialHolidayFinder.getAllPerByCode(specialHolidayCode);
	}
	
	@POST
	@Path("removePer")
	public void removePer(RemoveGrantDatePerCommand command) {
		removeGrantDatePerCommandHandler.handle(command);
	}
}
