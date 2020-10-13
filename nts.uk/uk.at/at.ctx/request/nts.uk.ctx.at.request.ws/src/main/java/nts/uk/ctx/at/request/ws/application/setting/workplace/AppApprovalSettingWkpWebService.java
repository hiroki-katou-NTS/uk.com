/**
 * 9:43:56 AM Jan 31, 2018
 */
package nts.uk.ctx.at.request.ws.application.setting.workplace;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;

/**
 * @author hungnm
 *
 */
@Path("at/request/application/setting/workplace")
@Produces("application/json")
public class AppApprovalSettingWkpWebService extends WebService {

//	@Inject
//	private ApplicationApprovalSettingWkpFinder finder;

//	@Inject
//	private ApprovalSettingWkpCommandHandler commandHandler;
//
//	@Inject
//	private DeleteApprovalSettingWkpCommandHandler deleteCmdHandler;

//	@Inject
//	private RequestOfEachCompanyCommandHandler saveComConfig;
//
//	@Inject
//	private RequestOfEachCompanyFinder comAprvConfigFinder;

//	@POST
//	@Path("getall")
//	public List<ApplicationApprovalSettingWkpDto> getAll(List<String> lstWkpId) {
//		return finder.getAll(lstWkpId);
//	}

//	@POST
//	@Path("update")
//	public void update(List<ApprovalSettingWkpCommand> command) {
//		commandHandler.handle(command);
//	}
//
//	@POST
//	@Path("remove")
//	public void remove(ApprovalSettingWkpCommand command) {
//		deleteCmdHandler.handle(command);
//	}

//	@POST
//	@Path("get-com")
//	public RequestOfEachCompanyDto getCom() {
//		return comAprvConfigFinder.findRequest();
//	}
//
//	@POST
//	@Path("save-com-config")
//	public void saveConfig(RequestOfEachCompanyCommand command) {
//		saveComConfig.handle(command);
//	}
}
