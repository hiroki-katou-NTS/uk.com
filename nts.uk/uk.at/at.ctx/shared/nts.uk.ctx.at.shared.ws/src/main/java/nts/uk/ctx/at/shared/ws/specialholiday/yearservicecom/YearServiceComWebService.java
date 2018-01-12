package nts.uk.ctx.at.shared.ws.specialholiday.yearservicecom;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom.UpdateYearServiceComCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom.UpdateYearServiceComCommandHandler;
import nts.uk.ctx.at.shared.app.find.specialholiday.yearservicecom.YearServiceComDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.yearservicecom.YearServiceComFinder;

@Path("at/shared/yearservicecom")
@Produces("application/json")
public class YearServiceComWebService extends WebService{
	@Inject
	private YearServiceComFinder finder;
	@Inject
	private UpdateYearServiceComCommandHandler update;
	/**
	 * get all data
	 * @return
	 */
	@POST
	@Path("findAll/{specialHolidayCode}") 
	public YearServiceComDto finder(@PathParam("specialHolidayCode") String specialHolidayCode){
		return this.finder.finder(specialHolidayCode);
	}
//	@POST
//	@Path("add")
//	public void insert(InsertYearServiceComCommand command){
//		this.add.handle(command);
//	}
	@POST
	@Path("update")
	public JavaTypeResult<List<String>> insert(UpdateYearServiceComCommand command){
		return new JavaTypeResult<List<String>>(this.update.handle(command));
	}
}
