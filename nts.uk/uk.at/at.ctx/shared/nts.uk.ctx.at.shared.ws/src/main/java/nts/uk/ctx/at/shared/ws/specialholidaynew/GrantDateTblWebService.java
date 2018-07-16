package nts.uk.ctx.at.shared.ws.specialholidaynew;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation.AddGrantDateTblCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation.DeleteGrantDateTblCommand;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation.DeleteGrantDateTblCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation.GrantDateTblCommand;
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
	private DeleteGrantDateTblCommandHandler delete;
	
	@Path("findBySphdCd/{specialHolidayCode}")
	@POST
	public List<GrantDateTblDto> findBySphdCd(@PathParam("specialHolidayCode") int specialHolidayCode) {
		return finder.findBySphdCd(specialHolidayCode);
	}
	
	@Path("findByGrantDateCd/{grantDateCode}")
	@POST
	public List<ElapseYearDto> findByGrantDateCd(@PathParam("grantDateCode") String grantDateCode) {
		return finder.findByGrantDateCd(grantDateCode);
	}
	
	@Path("add")
	@POST
	public void add(GrantDateTblCommand command) {
		add.handle(command);
	}
	
	@Path("delete")
	@POST
	public void delete(DeleteGrantDateTblCommand command) {
		delete.handle(command);
	}
}
