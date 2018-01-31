/**
 * 9:43:56 AM Jan 31, 2018
 */
package nts.uk.ctx.at.request.ws.application.setting.workplace;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.workplace.ApprovalSettingWkpCommand;
import nts.uk.ctx.at.request.app.command.setting.workplace.ApprovalSettingWkpCommandHandler;
import nts.uk.ctx.at.request.app.find.setting.workplace.ApplicationApprovalSettingWkpDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.ApplicationApprovalSettingWkpFinder;

/**
 * @author hungnm
 *
 */
@Path("at/request/application/setting/workplace")
@Produces("application/json")
public class AppApprovalSettingWkpWebService extends WebService {

	@Inject
	private ApplicationApprovalSettingWkpFinder finder;

	@Inject
	private ApprovalSettingWkpCommandHandler commandHandler;

	@POST
	@Path("getall")
	public List<ApplicationApprovalSettingWkpDto> getAll() {
		return finder.getAll();
	}

	@POST
	@Path("update")
	public void update(ApprovalSettingWkpCommand command) {
		commandHandler.handle(command);
	}
	
}
