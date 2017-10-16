package nts.uk.ctx.at.request.ws.application.gobackdirectly;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.ws.WebService;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.InsertApplicationGoBackDirectlyCommand;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.InsertGoBackDirectlyCommand;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.InsertGoBackDirectlyCommandHandler;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.UpdateGoBackDirectlyCommand;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.UpdateGoBackDirectlyCommandHandler;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectDetailDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectSettingDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectlyDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectlyFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("at/request/application/gobackdirectly")
@Produces("application/json")
public class GoBackDirectlyService extends WebService {
	@Inject
	private GoBackDirectlyFinder goBackDirectlyFinder;
	
	@Inject 
	private InsertGoBackDirectlyCommandHandler insertGoBackHandler;

	@Inject 
	private UpdateGoBackDirectlyCommandHandler updateGoBackHandler;

	/**
	 * 
	 * @return
	 */
	@POST
	@Path("getGoBackDirectlyByAppID")
	public GoBackDirectlyDto getGoBackDirectlyByAppID(String appID) {
		return this.goBackDirectlyFinder.getGoBackDirectlyByAppID(appID);
	}
	
	/**
	 * 
	 * @return
	 */
	@POST
	@Path("getGoBackCommonSetting")
	public GoBackDirectSettingDto getGoBackCommonSetting() {
		String SID = AppContexts.user().employeeId();
		return this.goBackDirectlyFinder.getGoBackDirectCommonSetting(SID);
	}

	/**
	 * 
	 * @return
	 */
	@POST
	@Path("getGoBackDirectDetail/{appID}")
	public GoBackDirectDetailDto getGoBackDetailData(@PathParam("appID") String appID) {
		return this.goBackDirectlyFinder.getGoBackDirectDetailByAppId(appID);
	}
	/**
	 * insert
	 * @param command
	 */
	@POST
	@Path("insertGoBackDirectly")
	public void insertGoBackData (InsertApplicationGoBackDirectlyCommand command) {
		this.insertGoBackHandler.handle(command);
	}
	/**
	 * update command
	 * @param command
	 */
	@POST
	@Path("updateGoBackDirectly")
	public void updateGoBackData (UpdateGoBackDirectlyCommand command) {
		this.updateGoBackHandler.handle(command);
	}
		
}
