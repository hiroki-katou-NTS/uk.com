package nts.uk.ctx.at.request.ws.application.applicationlist;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.applicationlist.AppTypeBfCommand;
import nts.uk.ctx.at.request.app.command.application.applicationlist.ApprovalListAppCommand;
import nts.uk.ctx.at.request.app.command.application.applicationlist.ApprovalListAppCommandHandler;
import nts.uk.ctx.at.request.app.command.application.applicationlist.ReflectAfterApproveAsyncCmd;
import nts.uk.ctx.at.request.app.command.application.applicationlist.ReflectAfterApproveAsyncCmdHandler;
import nts.uk.ctx.at.request.app.command.application.applicationlist.UpdateAppTypeBfCommandHandler;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppListParamFilter;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppTypeBfDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppTypeBfFinder;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationListDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationListFinder;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationDisplayAtr;

/**
 * 
 * @author hoatt
 *
 */
@Path("at/request/application/applist")
@Produces("application/json")
public class ApplicationListWebservice extends WebService{

	@Inject
	private ApplicationListFinder appListFinder;
	
	@Inject
	private AppTypeBfFinder bfreqFinder;
	
	@Inject
	private UpdateAppTypeBfCommandHandler update;
	
	@Inject
	private ApprovalListAppCommandHandler approvalListApp;
	
	@Inject
	private ReflectAfterApproveAsyncCmdHandler reflect;
	
	@POST
	/**
	 * get all list application
	 * @param param
	 * @return
	 */
	@Path("getapplist")
	public ApplicationListDto getApplicationList(AppListParamFilter param) {
		return this.appListFinder.getAppList(param);
	}
	
	/**
	 * get before After Restriction
	 * @return
	 * @author yennth
	 */
	@POST
	@Path("getappDisp")
	public AppTypeBfDto getBeforAfer() {
		return this.bfreqFinder.findByCom();
	}
	/**
	 * update after, before and apptype set
	 * @return
	 * @author yennth
	 */
	@POST
	@Path("update")
	public void update(AppTypeBfCommand cm) {
		this.update.handle(cm);
	}
	
	/**
	 * Enum 申請表示区分.
	 *
	 * @return the list
	 */
	@POST
	@Path("get/appdisplayatr")
	public List<EnumConstant> getListAppDisplayAtr() {
		return EnumAdaptor.convertToValueNameList(ApplicationDisplayAtr.class);
	}
	/**
	 * approval list application
	 * @param command
	 */
	@POST
	@Path("approval")
	public List<String> approvalListApp(List<ApprovalListAppCommand> command){
		return approvalListApp.handle(command);
	}
	
	@POST
	@Path("reflect-list")
	public void reflectAfterApprove(List<String> command){
		reflect.handle(command);
	}
}
