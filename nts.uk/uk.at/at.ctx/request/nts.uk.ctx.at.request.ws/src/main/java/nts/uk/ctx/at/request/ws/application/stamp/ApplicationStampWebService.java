package nts.uk.ctx.at.request.ws.application.stamp;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.stamp.RegisterAppStampCommandHandler;
import nts.uk.ctx.at.request.app.command.application.stamp.RegisterAppStampCommandHandler_Old;
import nts.uk.ctx.at.request.app.command.application.stamp.RegisterOrUpdateAppStampParam;
import nts.uk.ctx.at.request.app.command.application.stamp.UpdateAppStampCommandHandler;
import nts.uk.ctx.at.request.app.command.application.stamp.UpdateAppStampCommandHandler_Old;
import nts.uk.ctx.at.request.app.command.application.stamp.command.AppStampCmd;
import nts.uk.ctx.at.request.app.find.application.stamp.AppStampFinder;
import nts.uk.ctx.at.request.app.find.application.stamp.BeforeRegisterOrUpdateParam;
import nts.uk.ctx.at.request.app.find.application.stamp.ChangeDateParam;
import nts.uk.ctx.at.request.app.find.application.stamp.ChangeDateParamMobile;
import nts.uk.ctx.at.request.app.find.application.stamp.DetailAppStampParam;
import nts.uk.ctx.at.request.app.find.application.stamp.StartAppStampParam;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampDto_Old;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampNewPreDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampOutputDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.StampCombinationDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Path("at/request/application/stamp")
@Produces("application/json")
public class ApplicationStampWebService extends WebService {
	
	@Inject
	private AppStampFinder appStampFinder;
	
	@Inject 
	private RegisterAppStampCommandHandler_Old registerApplicationStampCommandHandler;
	
	@Inject
	private UpdateAppStampCommandHandler_Old updateApplicationStampCommandHandler;
	
	@Inject 
	private RegisterAppStampCommandHandler registerApplicationStampCommandHandlerNew;
	
	@Inject
	private UpdateAppStampCommandHandler updateApplicationStampCommandHandlerNew;
	
	@POST
	@Path("findByID")
	public AppStampDto_Old findByID(String appID){
		return this.appStampFinder.getAppStampByID(appID);
	}
	
	@POST
	@Path("newAppStampInitiative")
	public AppStampNewPreDto newAppStampInitiative(StampNewParam newParam){
		return this.appStampFinder.newAppStampPreProcess(newParam.getEmployeeID(), newParam.getDate());
	}
	
	@POST
	@Path("insert")
	public ProcessResult insert(AppStampCmd command){
		return this.registerApplicationStampCommandHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public ProcessResult update(AppStampCmd command){
		return this.updateApplicationStampCommandHandler.handle(command);
	}
	
	@POST
	@Path("enum/stampCombination")
	public List<StampCombinationDto> getStampCombinationAtr(){
		return this.appStampFinder.getStampCombinationAtr();
	}
	
	@POST
	@Path("getAttendanceItem")
	public List<AttendanceResultImport> getAttendanceItem(StampAttendanceParam param){
		return this.appStampFinder.getAttendanceItem(
				param.getEmployeeIDLst(), 
				param.getDate(), 
				param.getStampRequestMode());
	}
	
//	Refctor4
	
	@POST
	@Path("startStampApp")
	public AppStampOutputDto getInitData(StartAppStampParam startParam) {
		return appStampFinder.getDataCommon(startParam);
	}
	
	@POST
	@Path("checkBeforeRegister")
	public List<ConfirmMsgOutput> checkBeforeRegister(BeforeRegisterOrUpdateParam beforeRegisterParam) {
		return appStampFinder.checkBeforeRegister(beforeRegisterParam);
	}
	
	@POST
	@Path("checkBeforeUpdate")
	public List<ConfirmMsgOutput> checkBeforeUpdate(BeforeRegisterOrUpdateParam beforeRegisterParam) {
		return appStampFinder.checkBeforeUpdate(beforeRegisterParam);
	}
	
	@POST
	@Path("register")
	public ProcessResult register(RegisterOrUpdateAppStampParam command) {
		return registerApplicationStampCommandHandlerNew.handle(command);
	}
	
	@POST
	@Path("updateNew")
	public ProcessResult update(RegisterOrUpdateAppStampParam command) {
		return updateApplicationStampCommandHandlerNew.handle(command);
	}
	
	@POST
	@Path("changeDate")
	public ProcessResult changeDate(RegisterOrUpdateAppStampParam command) {
		return null;
	}
	
	@POST
	@Path("detailAppStamp")
	public AppStampOutputDto getDetailAppStamp(DetailAppStampParam detailAppStampParam) {
		return appStampFinder.getDataDetailCommon(detailAppStampParam);
	}
	
	@POST
	@Path("changeAppDateMobile")
	public AppStampOutputDto changeAppDateMobile(ChangeDateParamMobile param) {
	    return appStampFinder.changeDateAppStamp(param);
	}
	
	
	
	
	
}
