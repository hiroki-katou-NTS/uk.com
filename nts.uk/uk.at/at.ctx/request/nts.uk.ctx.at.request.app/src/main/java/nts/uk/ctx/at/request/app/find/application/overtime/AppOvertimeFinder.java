package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeInputDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.PreAppOvertimeDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartApprovalRootService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartCheckErrorService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceID;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayPrePost;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeInstructInfomation;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.DefaultFlg;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.breaktime.breaktimeframe.BreaktimeFrame;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe.OvertimeFrame;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe.OvertimeFrameRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppOvertimeFinder {
	final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	private StartApprovalRootService startApprovalRootService;
	
	@Inject
	private  StartCheckErrorService  startCheckErrorService;
	
	@Inject
	private OvertimeService overtimeService;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;
	
	@Inject
	private WorkTimeRepository workTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private OvertimeFrameRepository overtimeFrameRepository;
	
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	/**
	 * @param url
	 * @param appDate
	 * @param uiType
	 * @return
	 */
	public OverTimeDto getOvertimeByUIType(String url,String appDate,int uiType){
		
		OverTimeDto result = new OverTimeDto();
		ApplicationDto applicationDto = new ApplicationDto();
		List<OvertimeInputDto> overTimeInputs = new ArrayList<>();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		int rootAtr = 1;
		PreAppOvertimeDto preAppOvertimeDto = new PreAppOvertimeDto();
		
		
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				rootAtr, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), GeneralDate.fromString(appDate, DATE_FORMAT));
		result.setManualSendMailAtr(appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value  ==1 ?true : false);
		//アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
		startApprovalRootService.getApprovalRootPattern(companyID, employeeID, 1, ApplicationType.OVER_TIME_APPLICATION.value, null);
		//アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する 
		startCheckErrorService.checkError(ApplicationType.OVER_TIME_APPLICATION.value);
		// 02_残業区分チェック : check loai lam them
		int overtimeAtr = overtimeService.checkOvertime(url);
		result.setOvertimeAtr(overtimeAtr);
		// 01_初期データ取得
		getData(result,uiType,appDate,companyID,employeeID,appCommonSettingOutput,applicationDto,overtimeAtr,overTimeInputs,preAppOvertimeDto);
		
		result.setApplication(applicationDto);
		String employeeName = "";
		if(Strings.isNotBlank(applicationDto.getApplicantSID())){
			employeeName = employeeAdapter.getEmployeeName(applicationDto.getApplicantSID());
			result.setEmployeeID(applicationDto.getApplicantSID());
		} else {
			employeeName = employeeAdapter.getEmployeeName(employeeID);
			result.setEmployeeID(employeeID);
		}
		result.setEmployeeName(employeeName);
		
		return result;
	}
	
	/**
	 * @param appDate
	 * @param prePostAtr
	 * @return
	 */
	public OverTimeDto findByChangeAppDate(String appDate,int prePostAtr ){
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		OverTimeDto result = new OverTimeDto();
		ApplicationDto applicationDto = new ApplicationDto();
		PreAppOvertimeDto preAppOvertimeDto = new PreAppOvertimeDto();
		// 申請日を変更する : chưa xử lí
		
		// 01-01_残業通知情報を取得
		int rootAtr = 1;
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				rootAtr, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), GeneralDate.fromString(appDate, DATE_FORMAT));
		OvertimeInstructInfomation overtimeInstructInfomation = iOvertimePreProcess.getOvertimeInstruct(appCommonSettingOutput, appDate, employeeID);
		result.setDisplayOvertimeInstructInforFlg(overtimeInstructInfomation.isDisplayOvertimeInstructInforFlg());
		result.setOvertimeInstructInformation(overtimeInstructInfomation.getOvertimeInstructInfomation());
		applicationDto.setPrePostAtr(prePostAtr);
		result.setApplication(applicationDto);
		// 01-09_事前申請を取得
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		if(prePostAtr  == PrePostAtr.POSTERIOR.value ){
			
			AppOverTime appOvertime = iOvertimePreProcess.getPreApplication(employeeID,overtimeRestAppCommonSet, appDate,prePostAtr);
			if(appOvertime != null){
				convertOverTimeDto(companyID,preAppOvertimeDto,result,appOvertime);
			}else{
				result.setPreAppPanelFlg(false);
			}
			
		}
		// 01-18_実績の内容を表示し直す : chưa xử lí
		
		// ドメインモデル「申請表示設定」．事前事後区分表示をチェックする
		if(appCommonSettingOutput.applicationSetting.getDisplayPrePostFlg().value == AppDisplayAtr.NOTDISPLAY.value){
			// 3.事前事後の判断処理(事前事後非表示する場合)
			PrePostAtr prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), GeneralDate.fromString(appDate, DATE_FORMAT));
		}
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする ( Domain model "application setting". Check base date of approval route )
		List<RequestAppDetailSetting> requestAppDetailSettings = appCommonSettingOutput.requestOfEachCommon.getRequestAppDetailSettings();
		if(requestAppDetailSettings != null){
			List<RequestAppDetailSetting>  requestAppDetailSetting = requestAppDetailSettings.stream().filter( c -> c.appType == ApplicationType.OVER_TIME_APPLICATION).collect(Collectors.toList());
			if(appCommonSettingOutput.applicationSetting.getBaseDateFlg().value == BaseDateFlg.APP_DATE.value){
				if(requestAppDetailSetting != null){
					// 時刻計算利用チェック
					if (requestAppDetailSetting.get(0).getTimeCalUseAtr().value == UseAtr.USE.value) {
						result.setDisplayCaculationTime(true);
						//ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
						Optional<PersonalLaborCondition> personalLablorCodition = personalLaborConditionRepository.findById(employeeID,GeneralDate.fromString(appDate, DATE_FORMAT));
						// 07_勤務種類取得: lay loai di lam 
						WorkTypeOvertime workTypeOvertime = overtimeService.getWorkType(companyID, employeeID,personalLablorCodition,requestAppDetailSetting.get(0));
						result.setWorkType(workTypeOvertime);
						
						// 08_就業時間帯取得(lay loai gio lam viec) 
						SiftType siftType = overtimeService.getSiftType(companyID, employeeID, personalLablorCodition, requestAppDetailSetting.get(0));
						result.setSiftType(siftType);
					}else{
						result.setDisplayCaculationTime(false);
					}
				}
			}
		if(requestAppDetailSetting != null){
			if (requestAppDetailSetting.get(0).getTimeCalUseAtr().value == UseAtr.USE.value) {
				result.setDisplayCaculationTime(true);
				// 01-14_勤務時間取得(lay thoi gian): chua xong  Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」): to do
				iOvertimePreProcess.getWorkingHours(companyID, employeeID,appDate,requestAppDetailSetting.get(0));
			}else{
				result.setDisplayCaculationTime(false);
			}
		}
		}
		String employeeName = "";
		if(Strings.isNotBlank(applicationDto.getApplicantSID())){
			employeeName = employeeAdapter.getEmployeeName(applicationDto.getApplicantSID());
		} else {
			employeeName = employeeAdapter.getEmployeeName(employeeID);
		}
		result.setEmployeeName(employeeName);
		
		return result;
		
	}
	
	/**
	 * @param result
	 * @param uiType
	 * @param appDate
	 * @param companyID
	 * @param employeeID
	 * @param appCommonSettingOutput
	 * @param applicationDto
	 * @param overtimeAtr
	 * @param overTimeInputs
	 */
	private void getData(OverTimeDto result,int uiType,String appDate,String companyID,String employeeID,
			AppCommonSettingOutput appCommonSettingOutput,ApplicationDto applicationDto,int overtimeAtr,
			List<OvertimeInputDto> overTimeInputs,PreAppOvertimeDto preAppOvertimeDto){
		//申請日付を取得 : lay thong tin lam them
				applicationDto.setApplicationDate(appDate);
		// 01-01_残業通知情報を取得
				OvertimeInstructInfomation overtimeInstructInfomation = iOvertimePreProcess.getOvertimeInstruct(appCommonSettingOutput, appDate, employeeID);
				result.setDisplayOvertimeInstructInforFlg(overtimeInstructInfomation.isDisplayOvertimeInstructInforFlg());
				result.setOvertimeInstructInformation(overtimeInstructInfomation.getOvertimeInstructInfomation());
		//01-02_時間外労働を取得: lay lao dong ngoai thoi gian
		/*
		 * chưa phải làm
		 */
		// 01-13_事前事後区分を取得
				DisplayPrePost displayPrePost =	iOvertimePreProcess.getDisplayPrePost(companyID, uiType,appDate);
				result.setDisplayPrePostFlg(displayPrePost.getDisplayPrePostFlg());
				applicationDto.setPrePostAtr(displayPrePost.getPrePostAtr());
				if(displayPrePost.getPrePostAtr() == InitValueAtr.POST.value){
					result.setReferencePanelFlg(true);
				}
				result.setApplication(applicationDto);
				
//		String workplaceID = employeeAdapter.getWorkplaceId(companyID, employeeID, GeneralDate.today());
		List<RequestAppDetailSetting> requestAppDetailSettings = appCommonSettingOutput.requestOfEachCommon.getRequestAppDetailSettings();
		if(requestAppDetailSettings != null){
			List<RequestAppDetailSetting>  requestAppDetailSetting = requestAppDetailSettings.stream().filter( c -> c.appType == ApplicationType.OVER_TIME_APPLICATION).collect(Collectors.toList());
			if(requestAppDetailSetting != null){
				// 時刻計算利用チェック
				if (requestAppDetailSetting.get(0).getTimeCalUseAtr().value == UseAtr.USE.value) {
					result.setDisplayCaculationTime(true);
					//ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
					Optional<PersonalLaborCondition> personalLablorCodition = personalLaborConditionRepository.findById(employeeID,GeneralDate.fromString(appDate, DATE_FORMAT));
					// 07_勤務種類取得: lay loai di lam 
					WorkTypeOvertime workTypeOvertime = overtimeService.getWorkType(companyID, employeeID,personalLablorCodition,requestAppDetailSetting.get(0));
					result.setWorkType(workTypeOvertime);
					
					// 08_就業時間帯取得(lay loai gio lam viec) 
					SiftType siftType = overtimeService.getSiftType(companyID, employeeID, personalLablorCodition, requestAppDetailSetting.get(0));
					result.setSiftType(siftType);
					
					// 01-14_勤務時間取得(lay thoi gian): chua xong  Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」): to do
					iOvertimePreProcess.getWorkingHours(companyID, employeeID,appDate,requestAppDetailSetting.get(0));
					
					// 01-17_休憩時間取得(lay thoi gian nghi ngoi)
					boolean displayRestTime = iOvertimePreProcess.getRestTime(requestAppDetailSetting.get(0));
					result.setDisplayRestTime(displayRestTime);
					
				}else{
					result.setDisplayCaculationTime(false);
				}
			}
		}
		
		// 01-03_残業枠を取得: chua xong
		result.setAppOvertimeNightFlg(appCommonSettingOutput.applicationSetting.getAppOvertimeNightFlg().value);
		List<OvertimeFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(overtimeAtr,companyID);
		
		for(OvertimeFrame overtimeFrame :overtimeFrames){
			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
			overtimeInputDto.setAttendanceID(AttendanceID.NORMALOVERTIME.value);
			overtimeInputDto.setFrameNo(overtimeFrame.getOtFrameNo());
			overtimeInputDto.setFrameName(overtimeFrame.getOvertimeFrameName().toString());
			overTimeInputs.add(overtimeInputDto);
		}
		
		// lay breakTime
		List<BreaktimeFrame> breaktimeFrames = iOvertimePreProcess.getBreaktimeFrame(companyID);
		for(BreaktimeFrame breaktimeFrame :breaktimeFrames){
			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
			overtimeInputDto.setAttendanceID(AttendanceID.BREAKTIME.value);
			overtimeInputDto.setFrameNo(breaktimeFrame.getBreakTimeFrameNo());
			overtimeInputDto.setFrameName(breaktimeFrame.getBreakTimeFrameName().toString());
			overTimeInputs.add(overtimeInputDto);
		}
		
		result.setOverTimeInputs(overTimeInputs);
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		// xu li hien thi du lieu xin truoc
		if(overtimeRestAppCommonSet.isPresent()){
			if(overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.NOTUSE.value){
				result.setPreAppPanelFlg(false);
			}
		}
		// 01-04_加給時間を取得: chua xong
		if(overtimeRestAppCommonSet.isPresent()){
			if(overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr().value == UseAtr.USE.value){
				result.setDisplayBonusTime(true);
				List<BonusPayTimeItem> bonusPayTimeItems= this.iOvertimePreProcess.getBonusTime(employeeID,overtimeRestAppCommonSet,appDate,companyID, result.getSiftType());
				for(BonusPayTimeItem bonusPayTimeItem : bonusPayTimeItems){
					OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
					overtimeInputDto.setAttendanceID(AttendanceID.BONUSPAYTIME.value);
					overtimeInputDto.setFrameNo(bonusPayTimeItem.getId());
					overtimeInputDto.setFrameName(bonusPayTimeItem.getTimeItemName().toString());
					overtimeInputDto.setTimeItemTypeAtr(bonusPayTimeItem.getTimeItemTypeAtr().value);
					overTimeInputs.add(overtimeInputDto);
				}
			}else{
				result.setDisplayBonusTime(false);
			}
		}
		
		// 01-05_申請定型理由を取得, 01-06_申請理由を取得
				Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID,  ApplicationType.OVER_TIME_APPLICATION.value);
				if(appTypeDiscreteSetting.isPresent()){
					// 01-05_申請定型理由を取得
					if(appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
						result.setTypicalReasonDisplayFlg(true);
						List<ApplicationReason> applicationReasons = iOvertimePreProcess.getApplicationReasonType(companyID,ApplicationType.OVER_TIME_APPLICATION.value,appTypeDiscreteSetting);
						List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
						for (ApplicationReason applicationReason : applicationReasons) {
							ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
									applicationReason.getReasonTemp(), applicationReason.getDefaultFlg().value);
							applicationReasonDtos.add(applicationReasonDto);
						}
						result.setApplicationReasonDtos(applicationReasonDtos);
					}else{
						result.setTypicalReasonDisplayFlg(false);
					}
					//01-06_申請理由を取得
					result.setDisplayAppReasonContentFlg(iOvertimePreProcess.displayAppReasonContentFlg(appTypeDiscreteSetting));
				}
				if(overtimeRestAppCommonSet.isPresent()){
					//01-08_乖離定型理由を取得
					if(overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr().value == UseAtr.USE.value){
						result.setDisplayDivergenceReasonForm(true);
						List<DivergenceReason> divergenceReasons = iOvertimePreProcess.getDivergenceReasonForm(companyID,ApplicationType.OVER_TIME_APPLICATION.value,overtimeRestAppCommonSet);
						convertToDivergenceReasonDto(divergenceReasons,result);
					}else{
						result.setDisplayDivergenceReasonForm(false);
					}
					//01-07_乖離理由を取得
					result.setDisplayDivergenceReasonInput(iOvertimePreProcess.displayDivergenceReasonInput(overtimeRestAppCommonSet));
					
				}
				//01-09_事前申請を取得
				if(result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value ){
					AppOverTime appOvertime = iOvertimePreProcess.getPreApplication(employeeID,overtimeRestAppCommonSet, appDate,result.getApplication().getPrePostAtr());
					if(appOvertime != null){
						convertOverTimeDto(companyID,preAppOvertimeDto,result,appOvertime);
					}else{
						result.setPreAppPanelFlg(false);
					}
					
				}
		
	}

	/**
	 * @param divergenceReasons
	 * @param result
	 */
	private void convertToDivergenceReasonDto(List<DivergenceReason> divergenceReasons, OverTimeDto result){
				
				List<DivergenceReasonDto> divergenceReasonDtos = new ArrayList<>();
				for(DivergenceReason divergenceReason : divergenceReasons){
					DivergenceReasonDto divergenceReasonDto = new DivergenceReasonDto();
					divergenceReasonDto.setDivergenceReasonID(divergenceReason.getReasonTypeItem().getReasonID());
					divergenceReasonDto.setReasonTemp(divergenceReason.getReasonTypeItem().getReasonTemp().toString());
					if(divergenceReason.getReasonTypeItem().getDefaultFlg().value == DefaultFlg.DEFAULT.value){
						divergenceReasonDto.setDivergenceReasonIdDefault(divergenceReason.getReasonTypeItem().getReasonID());
					}
					divergenceReasonDtos.add(divergenceReasonDto);
				}
				result.setDivergenceReasonDtos(divergenceReasonDtos);
	}
	
	/**
	 * @param companyID
	 * @param applicationDto
	 * @param result
	 * @param appOvertime
	 */
	private void convertOverTimeDto(String companyID,PreAppOvertimeDto preAppOvertimeDto, OverTimeDto result,AppOverTime appOvertime){
		if(appOvertime.getApplication() != null){
			if(appOvertime.getApplication().getApplicationDate() != null){
				preAppOvertimeDto.setAppDatePre(appOvertime.getApplication().getApplicationDate().toString(DATE_FORMAT));
			}
		}
		
		if (appOvertime.getWorkTypeCode() != null) {
			WorkTypeOvertime workTypeOvertime = new WorkTypeOvertime();
			workTypeOvertime.setWorkTypeCode(appOvertime.getWorkTypeCode().toString());
			Optional<WorkType> workType = workTypeRepository.findByPK(companyID,
					appOvertime.getWorkTypeCode().toString());
			if (workType.isPresent()) {
				workTypeOvertime.setWorkTypeName(workType.get().getName().toString());
			}
			preAppOvertimeDto.setWorkTypePre(workTypeOvertime);
		}
		if (appOvertime.getSiftCode() != null) {
			SiftType siftType = new SiftType();

			siftType.setSiftCode(appOvertime.getSiftCode().toString());
			Optional<WorkTime> workTime = workTimeRepository.findByCode(companyID,
					appOvertime.getSiftCode().toString());
			if (workTime.isPresent()) {
				siftType.setSiftName(workTime.get().getWorkTimeDisplayName().toString());
			}
			preAppOvertimeDto.setSiftTypePre(siftType);
		}
		preAppOvertimeDto.setWorkClockFrom1Pre(appOvertime.getWorkClockFrom1());
		preAppOvertimeDto.setWorkClockTo1Pre(appOvertime.getWorkClockTo1());
		preAppOvertimeDto.setWorkClockFrom2Pre(appOvertime.getWorkClockFrom2());
		preAppOvertimeDto.setWorkClockTo2Pre(appOvertime.getWorkClockTo2());
		
		List<OvertimeInputDto> overtimeInputDtos = new ArrayList<>();
		List<OverTimeInput> overtimeInputs = appOvertime.getOverTimeInput();
		if (overtimeInputs != null && !overtimeInputs.isEmpty()) {
			List<Integer> frameNo = new ArrayList<>();
			for (OverTimeInput overTimeInput : overtimeInputs) {
				OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
				overtimeInputDto.setAttendanceID(overTimeInput.getAttendanceID().value);
				overtimeInputDto.setFrameNo(overTimeInput.getFrameNo());
				overtimeInputDto.setStartTime(overTimeInput.getStartTime().v());
				overtimeInputDto.setEndTime(overTimeInput.getEndTime().v());
				overtimeInputDto.setApplicationTime(overTimeInput.getApplicationTime().v());
				overtimeInputDtos.add(overtimeInputDto);
				frameNo.add(overTimeInput.getFrameNo());
			}
			List<OvertimeFrame> overtimeFrames = this.overtimeFrameRepository.getOvertimeFrameByFrameNo(frameNo);
			for (OvertimeInputDto overtimeInputDto : overtimeInputDtos) {
				for (OvertimeFrame overtimeFrame : overtimeFrames) {
					if (overtimeInputDto.getFrameNo() == overtimeFrame.getOtFrameNo()) {
						overtimeInputDto.setFrameName(overtimeFrame.getOvertimeFrameName().toString());
						continue;
					}
				}
			}
			result.setOverTimeInputs(overtimeInputDtos);
			result.setOverTimeShiftNight(appOvertime.getOverTimeShiftNight());
			result.setFlexExessTime(appOvertime.getFlexExessTime());
		}

	}
}
