package nts.uk.ctx.at.request.ws.application.workchange;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.workchange.ApplicationDateCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.CheckChangeAppDateCommandHandler;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeCommonSetDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeCommonSetFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("at/request/application/workchange")
@Produces("application/json")
public class WorkchangeService extends WebService {
	
	@Inject
	AppWorkChangeCommonSetFinder commonFinder;
	
	@Inject
	CheckChangeAppDateCommandHandler checkHander;
	
	@POST
	@Path("getWorkchangeByAppID")
	public void getWorkchangeByAppID(String appID) {
		
	}
	@POST
	@Path("getWorkChangeCommonSetting")
	public AppWorkChangeCommonSetDto getWorkChangeCommonSetting()
	{
		String sId = AppContexts.user().employeeId();
		return commonFinder.getWorkChangeCommonSetting(sId);
	}
	
	/**
	 * 共通アルゴリズム「申請日を変更する」を実行する
	 * @param command : 申請日付分　（開始日～終了日）
	 */
	@POST
	@Path("checkChangeApplicationDate")
	public void checkChangeApplicationDate(ApplicationDateCommand command){
		checkHander.handle(command);
	}
	
}
