package nts.uk.ctx.at.shared.ws.specialholiday.yearserviceper;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper.AddYearServicePerCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper.AddYearServicePerCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper.DeleteYearServicePerCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper.DeleteYearServicePerCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper.UpdateYearServicePerCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper.UpdateYearServicePerCommandHandler;
import nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper.YearServicePerDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper.YearServicePerFinder;
import nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper.YearServicePerSetDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper.YearServicePerSetFinder;

@Path("at/shared/yearserviceper")
@Produces("application/json")
public class YearServicePerWebService extends WebService{
	@Inject
	private YearServicePerFinder finderPer;
	@Inject
	private YearServicePerSetFinder finderPerSet;
	@Inject
	private UpdateYearServicePerCommandHandler updatePer;
	@Inject
	private AddYearServicePerCommandHandler addPer;
	@Inject
	private DeleteYearServicePerCommandHandler delete;
	/**
	 * get all data Per
	 * @return
	 */
	@POST
	@Path("findAllPer/{specialHolidayCode}")
	public List<YearServicePerDto> finderPer(@PathParam("specialHolidayCode") String specialHolidayCode){
		return this.finderPer.finder(specialHolidayCode);
	}
	/**
	 * get all data per set 
	 */
	@POST
	@Path("findAllPerSet/{a}/{b}")
	public List<YearServicePerSetDto> finderPerSet(@PathParam("a") String a, @PathParam("b") String b){
		return this.finderPerSet.finder(a, b);
	}
	@POST
	@Path("update")
	public JavaTypeResult<List<String>> update(UpdateYearServicePerCommand command){
		return new JavaTypeResult<List<String>>(this.updatePer.handle(command));
	}
	@POST
	@Path("add")
	public JavaTypeResult<List<String>> add(AddYearServicePerCommand command){
		return new JavaTypeResult<List<String>>(this.addPer.handle(command));
	}
	@POST
	@Path("delete")
	public void delete(DeleteYearServicePerCommand command){
		this.delete.handle(command);
	}
}
