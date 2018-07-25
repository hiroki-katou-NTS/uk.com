package nts.uk.ctx.at.shared.ws.specialholidaynew;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation.AddGrantDateTblCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation.DeleteGrantDateTblCommand;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation.DeleteGrantDateTblCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation.GrantDateTblCommand;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation.UpdateGrantDateTblCommandHandler;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.ElapseYearDto;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.GrantDateTblDto;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.GrantDateTblFinder;

/**
 * 
 * @author tanlv
 *
 */
@Path("shared/grantdatetbl")
@Produces("application/json")
public class GrantDateTblWebService extends WebService {
	@Inject
	private GrantDateTblFinder finder;
	
	@Inject
	private AddGrantDateTblCommandHandler add;
	
	@Inject
	private UpdateGrantDateTblCommandHandler update;
	
	@Inject
	private DeleteGrantDateTblCommandHandler delete;
	
	/**
	 * Find all Grant Date data by Special Holiday Code
	 * @param specialHolidayCode
	 * @return
	 */
	@Path("findBySphdCd/{specialHolidayCode}")
	@POST
	public List<GrantDateTblDto> findBySphdCd(@PathParam("specialHolidayCode") int specialHolidayCode) {
		return finder.findBySphdCd(specialHolidayCode);
	}
	
	/**
	 * Find Grant Date by Grant Date Code
	 * @param grantDateCode
	 * @return
	 */
	@Path("findByGrantDateCd/{specialHolidayCode}/{grantDateCode}")
	@POST
	public List<ElapseYearDto> findByGrantDateCd(@PathParam("specialHolidayCode") int specialHolidayCode, @PathParam("grantDateCode") String grantDateCode) {
		return finder.findByGrantDateCd(specialHolidayCode, grantDateCode);
	}
	
	/**
	 * Add new Grant Date
	 * @param command
	 * @return
	 */
	@Path("add")
	@POST
	public JavaTypeResult<List<String>> add(GrantDateTblCommand command) {
		return new JavaTypeResult<List<String>>(add.handle(command));
	}
	
	/**
	 * Update Grant Date
	 * @param command
	 * @return
	 */
	@Path("update")
	@POST
	public JavaTypeResult<List<String>> update(GrantDateTblCommand command) {
		return new JavaTypeResult<List<String>>(update.handle(command));
	}
	
	/**
	 * Delete Grant Date
	 * @param command
	 */
	@Path("delete")
	@POST
	public void delete(DeleteGrantDateTblCommand command) {
		delete.handle(command);
	}
}
