package nts.uk.ctx.at.request.ws.application.gobackdirectly;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.CheckInsertGoBackCommandHandler;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.CheckUpdateGoBackCommandHandler;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.InsertApplicationGoBackDirectlyCommand;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.InsertGoBackDirectlyCommandHandler;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.UpdateApplicationGoBackDirectlyCommand;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.UpdateGoBackDirectlyCommandHandler;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectDetailDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectSettingDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectlyDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectlyFinder;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.InforGoBackCommonDirectDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.InforGoBackDirectOutput;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.ParamChangeDate;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.ParamDirectBack;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.ParamGetAppGoBack;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectlyRegisterService;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.shr.com.context.AppContexts;

@Path("at/request/application/gobackdirectly")
@Produces("application/json")
public class GoBackDirectlyService extends WebService {
	@Inject
	private GoBackDirectlyFinder goBackDirectlyFinder;
	
	@Inject 
	private InsertGoBackDirectlyCommandHandler insertGoBackHandler;
	
	@Inject 
	private CheckInsertGoBackCommandHandler checkInsertGoBackHandler;
	
	@Inject
	private CheckUpdateGoBackCommandHandler checkUpdateGoBackHandler;

	@Inject 
	private UpdateGoBackDirectlyCommandHandler updateGoBackHandler;
	
	@Inject
	private GoBackDirectlyRegisterService goBackDirectlyRegisterService;

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
	public GoBackDirectSettingDto getGoBackCommonSetting(ParamGetAppGoBack param) {
		return this.goBackDirectlyFinder.getGoBackDirectCommonSetting(param.getEmployeeIDs(), param.getAppDate());
	}
	@POST
	@Path("getGoBackCommonSettingNew")
	public InforGoBackCommonDirectDto getGoBackCommonSettingNew(ParamDirectBack param) {
//		String companyID = AppContexts.user().companyId();
//		List<String> sIds = new ArrayList<String>();
//		sIds.add(AppContexts.user().employeeId());
//		List<String> appDates = new ArrayList<String>();
//		GeneralDate today = GeneralDate.today();
//		appDates.add(today.toString("yyyy/MM/dd"));
//		Boolean newMode = true;
		//起動時の申請表示情報を取得する
		
		return this.goBackDirectlyFinder.getOutputApplication(param.getApplicantEmployeeID(), param.getApplicantList());
	}
	@POST
	@Path("getAppDataByDate")
	public InforGoBackCommonDirectDto changeDataByDate(ParamChangeDate param) {
//		AppDispInfoStartupDto appDispInfoStartupDto = param.getInforGoBackCommonDirectDto().getAppDispInfoStartupDto();
////		申請日を変更する
//		AppDispInfoWithDateOutput appDispInfoWithDateOutput = goBackDirectlyFinder.getDataByDate(param.getCompanyId(), 
//				param.getAppDates(), param.getEmployeeIds(), ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, appDispInfoStartupDto.appDispInfoNoDateOutput.toDomain(), appDispInfoStartupDto.appDispInfoWithDateOutput.toDomain());
//		appDispInfoStartupDto.appDispInfoWithDateOutput = AppDispInfoWithDateDto.fromDomain(appDispInfoWithDateOutput);
//		param.getInforGoBackCommonDirectDto().setAppDispInfoStartupDto(appDispInfoStartupDto);
////		直行直帰申請起動時初期データを取得する
//		List<GeneralDate> lstDate = new ArrayList<>();
//		if (!CollectionUtil.isEmpty(param.getAppDates())) {
//			lstDate.addAll(param.getAppDates().stream().map(item -> GeneralDate.fromString(item, "yyyy/MM/dd"))
//					.collect(Collectors.toList()));
//		}
//		String sId = CollectionUtil.isEmpty(param.getEmployeeIds()) ? "" : param.getEmployeeIds().get(0);
//		GeneralDate appDate = CollectionUtil.isEmpty(lstDate) ? null : lstDate.get(0);
//		GeneralDate baseDate = appDispInfoStartupDto.toDomain().getAppDispInfoWithDateOutput().getBaseDate();
//		AppEmploymentSetting aes = appDispInfoStartupDto.toDomain().getAppDispInfoWithDateOutput().getEmploymentSet();
//		List<WorkTimeSetting> lstWts = appDispInfoStartupDto.toDomain().getAppDispInfoWithDateOutput().getWorkTimeLst();
//		InforGoBackDirectOutput inforGoBackDirectOutput = goBackDirectlyFinder.getInforGoBackDirect(param.getCompanyId(),
//				sId,
//				appDate,
//				baseDate,
//				aes,
//				lstWts);
//		param.getInforGoBackCommonDirectDto().setWorkType(inforGoBackDirectOutput.getWorkType());
//		param.getInforGoBackCommonDirectDto().setWorkTime(inforGoBackDirectOutput.getWorkTime());
//		param.getInforGoBackCommonDirectDto().setLstWorkType(inforGoBackDirectOutput.getLstWorkType().stream().map(item -> WorkTypeDto.fromDomain(item)).collect(Collectors.toList()));
//		return param.getInforGoBackCommonDirectDto();
		return null;
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
	public ProcessResult insertGoBackData (InsertApplicationGoBackDirectlyCommand command) {
		return insertGoBackHandler.handle(command);
	}
	
	/**
	 * check before insert OR update
	 * @param command
	 */
	@POST
	@Path("checkBeforeChangeGoBackDirectly")
	public void checkBeforeInsertGoBackData (InsertApplicationGoBackDirectlyCommand command) {
		this.checkInsertGoBackHandler.handle(command);
	}
	
	@POST
	@Path("checkBeforeUpdateGoBackData")
	public void checkBeforeUpdateGoBackData (InsertApplicationGoBackDirectlyCommand command) {
		this.checkUpdateGoBackHandler.handle(command);
	}
	
	
	/**
	 * update command
	 * @param command
	 */
	@POST
	@Path("updateGoBackDirectly")
	public ProcessResult updateGoBackData (UpdateApplicationGoBackDirectlyCommand command) {
		return this.updateGoBackHandler.handle(command);
	}
	
	@POST
	@Path("confirmInconsistency")
	public List<String> confirmInconsistency(InsertApplicationGoBackDirectlyCommand command) {
		String companyID = AppContexts.user().companyId();
		return goBackDirectlyRegisterService.inconsistencyCheck(
				companyID, 
				command.getAppCommand().getApplicantSID(), 
				command.getAppCommand().getApplicationDate());
	}
		
}

