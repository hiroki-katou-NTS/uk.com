package nts.uk.ctx.at.request.ws.application.holidayshipment;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.RegisterWhenChangeDateHolidayShipmentCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.SaveHolidayShipmentCommandHandlerRef5;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.UpdateHolidayShipmentCommandHandlerRef5;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command.HolidayShipmentRefactor5Command;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command.RegisterWhenChangeDateCommand;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.ChangeValueItemsOnHolidayShipment;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.HolidayShipmentScreenAFinder;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.HolidayShipmentScreenBFinder;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.ChangeDateParam;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.ChangeWokTypeParam;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.ChangeWorkTypeResultDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInforWhenStarting;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.shr.com.context.AppContexts;

@Path("at/request/application/holidayshipment")
@Produces("application/json")
public class HolidayShipmentWebService extends WebService {

	@Inject
	private HolidayShipmentScreenAFinder screenAFinder;
	
	@Inject
	private ChangeValueItemsOnHolidayShipment changeValueItemsOnHolidayShipment;
	
	@Inject
	private SaveHolidayShipmentCommandHandlerRef5 saveCommandHandler;
	
	@Inject
	private HolidayShipmentScreenBFinder screenBFinder;
	
	@Inject
	private UpdateHolidayShipmentCommandHandlerRef5 updateHolidayShipment;
	
	@Inject
	private RegisterWhenChangeDateHolidayShipmentCommandHandler registerWhenChangeDateHolidayShipmentCommandHandler;
	

	@POST
	@Path("startPageARefactor")
	public DisplayInforWhenStarting startPageARefactor(StartPageARefactorParam param) {
		return this.screenAFinder.startPageARefactor(
			AppContexts.user().companyId(), 
			CollectionUtil.isEmpty(param.getSIDs()) ? Arrays.asList(AppContexts.user().employeeId()) : param.getSIDs(), 
			param.getAppDate(),
			param.getAppDispInfoStartup()
		);
	}
	
	@POST
	@Path("changeRecDate")
	public DisplayInforWhenStarting changeRecDate(ChangeDateParam param) {
		return changeValueItemsOnHolidayShipment.changeRecDate(param.getWorkingDate().get(), param.getHolidayDate(), param.displayInforWhenStarting);
	}
	
	@POST
	@Path("changeAbsDate")
	public DisplayInforWhenStarting changeAbsDate(ChangeDateParam param) {
		return changeValueItemsOnHolidayShipment.changeAbsDate(param.getWorkingDate(), param.getHolidayDate().get(), param.displayInforWhenStarting);
	}
	
	@POST
	@Path("save")
	public ProcessResult save(DisplayInforWhenStarting command) {
		return saveCommandHandler.register(command);
	}
	
	@POST
	@Path("changeWorkType")
	public ChangeWorkTypeResultDto changeWorkType(ChangeWokTypeParam param) {
		return changeValueItemsOnHolidayShipment.changeWorkType(param.workTypeBefore, param.workTypeAfter, Optional.ofNullable(param.workTimeCode == "" ? null: param.workTimeCode), param.leaveComDayOffMana, param.payoutSubofHDManagements);
	}
	
	@POST
	@Path("startPageBRefactor")
	public DisplayInforWhenStarting startPageBRefactor(StartScreenBParam param) {
		return this.screenBFinder.startPageBRefactor(AppContexts.user().companyId(), param.getAppID(), param.getAppDispInfoStartupDto());
	}
	
	@POST
	@Path("update")
	public ProcessResult update(HolidayShipmentRefactor5Command command) {
		return updateHolidayShipment.update(command);
	}
	
	@POST
	@Path("changeDateScreenC")
	public DisplayInforWhenStarting changeDateScreenC(RegisterWhenChangeDateCommand command) {
		return changeValueItemsOnHolidayShipment.changeDateCScreen(command.appDateNew, command.displayInforWhenStarting);
	}
	
	@POST
	@Path("saveChangeDateScreenC")
	public ProcessResult update(RegisterWhenChangeDateCommand command) {
		return registerWhenChangeDateHolidayShipmentCommandHandler.register(command.displayInforWhenStarting, command.appDateNew, command.appReason, command.appStandardReasonCD);
	}
	
}

@Value
class StartPageARefactorParam {
	//申請者リスト<Optional>
	public List<String> sIDs;
	//申請対象日リスト<Optional>
	public AppDispInfoStartupDto appDispInfoStartup;
	
	public List<GeneralDate> appDate;
}

@Value
class StartScreenBParam {
	private String appID;
	private AppDispInfoStartupDto appDispInfoStartupDto;
}

@Value
class StartScreenCParam {
	private String sID;
	private GeneralDate appDate;
	private int uiType;
}
