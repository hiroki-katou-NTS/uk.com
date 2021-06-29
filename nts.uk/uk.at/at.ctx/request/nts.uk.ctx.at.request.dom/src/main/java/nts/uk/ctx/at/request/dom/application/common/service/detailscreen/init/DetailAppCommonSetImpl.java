package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeaveRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
//import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.NewAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.DetailScreenBefore;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailedScreenBeforeStartOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenAppData;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class DetailAppCommonSetImpl implements DetailAppCommonSetService {

	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private DetailScreenBefore detailScreenBefore;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private BeforePreBootMode beforePreBootMode;
	
	@Inject
	private InitMode initMode;
	
	@Inject
	private AppOverTimeRepository appOverTimeRepository;
	
	@Inject
	private ApplyForLeaveRepository applyForLeaveRepository;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;

	@Inject
	private RecruitmentAppRepository recRepo;
	
	@Override
	public ApplicationMetaOutput getDetailAppCommonSet(String companyID, String applicationID) {
		Optional<Application> opApplication = applicationRepository.findByID(companyID, applicationID);
		if(!opApplication.isPresent()){
			throw new BusinessException("Msg_198");
		}
		return new ApplicationMetaOutput(
				opApplication.get().getAppID(),
				opApplication.get().getAppType(), 
				opApplication.get().getAppDate().getApplicationDate());
	}

	@Override
	public List<ApplicationMetaOutput> getListDetailAppCommonSet(String companyID, List<String> listAppID) {
//		return applicationRepository.findByListID(companyID, listAppID)
//				.stream().map(x -> new ApplicationMetaOutput(
//						x.getAppID(),
//						x.getAppType(),
//						x.getAppDate()
//				)).collect(Collectors.toList());
		return null;
				
	}

	@Override
	public AppDispInfoStartupOutput getCommonSetBeforeDetail(String companyID, String appID) {
		// 詳細画面の申請データを取得する
		DetailScreenAppData detailScreenAppData = detailScreenBefore.getDetailScreenAppData(appID);
		// 取得した「申請．申請種類」をチェックする
		ApplicationType appType = detailScreenAppData.getApplication().getAppType();
		Optional<HolidayAppType> opHolidayAppType = Optional.empty();
		Optional<OvertimeAppAtr> opOvertimeAppAtr = Optional.empty();
		List<GeneralDate> dateLst = new ArrayList<>();
		if(appType==ApplicationType.OVER_TIME_APPLICATION) {
			// ドメインモデル「残業申請」を取得する
			opOvertimeAppAtr = appOverTimeRepository.find(companyID, appID).map(x -> x.getOverTimeClf());
		} else if(appType==ApplicationType.ABSENCE_APPLICATION) {
			// ドメインモデル「休暇申請」を取得する
			opHolidayAppType = applyForLeaveRepository.findApplyForLeave(companyID, appID).map(x -> x.getVacationInfo().getHolidayApplicationType());
		} else if(appType==ApplicationType.COMPLEMENT_LEAVE_APPLICATION) {
			// ドメインモデル「振休振出申請」を取得する
			Optional<AppHdsubRec> appHdsubRec = appHdsubRecRepository.findByAppId(appID);
			if(appHdsubRec.isPresent()) {
				if(appHdsubRec.get().getRecAppID().equals(appID)) {
					dateLst.add(detailScreenAppData.getApplication().getAppDate().getApplicationDate());
					dateLst.add(applicationRepository.findByID(appHdsubRec.get().getAbsenceLeaveAppID()).map(x -> x.getAppDate().getApplicationDate()).orElse(null));
				} else {
					dateLst.add(applicationRepository.findByID(appHdsubRec.get().getRecAppID()).map(x -> x.getAppDate().getApplicationDate()).orElse(null));
					dateLst.add(detailScreenAppData.getApplication().getAppDate().getApplicationDate());
				}
			} else {
				if(recRepo.findByID(appID).isPresent()) {
					dateLst.add(detailScreenAppData.getApplication().getAppDate().getApplicationDate());
					dateLst.add(null);
				} else {
					dateLst.add(null);
					dateLst.add(detailScreenAppData.getApplication().getAppDate().getApplicationDate());
				}
			}
		}
		// 起動時の申請表示情報を取得する
		List<String> applicantLst = Arrays.asList(detailScreenAppData.getApplication().getEmployeeID());
		GeneralDate startDate = detailScreenAppData.getApplication().getOpAppStartDate().map(x -> x.getApplicationDate())
				.orElse(detailScreenAppData.getApplication().getAppDate().getApplicationDate());
		GeneralDate endDate = detailScreenAppData.getApplication().getOpAppEndDate().map(x -> x.getApplicationDate())
				.orElse(detailScreenAppData.getApplication().getAppDate().getApplicationDate());
		if(appType!=ApplicationType.COMPLEMENT_LEAVE_APPLICATION) {
			for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)) {
				dateLst.add(loopDate);
			}
		}
		AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(
				companyID,
				appType, 
				applicantLst, 
				dateLst, 
				false,
				opHolidayAppType, 
				opOvertimeAppAtr);
		// 入力者の社員情報を取得する (Lấy employee inforrmation của người nhập)
		Optional<EmployeeInfoImport> opEmployeeInfoImport = commonAlgorithm.getEnterPersonInfor(
				detailScreenAppData.getApplication().getEmployeeID(), 
				detailScreenAppData.getApplication().getEnteredPersonID());
		// 詳細画面の利用者とステータスを取得する
		DetailedScreenBeforeStartOutput detailedScreenPreBootModeOutput = beforePreBootMode.judgmentDetailScreenMode(
				companyID, 
				AppContexts.user().employeeId(), 
				detailScreenAppData.getApplication(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate());
		// 詳細画面の画面モードを判断する
		OutputMode outputMode = initMode.getDetailScreenInitMode(
				detailedScreenPreBootModeOutput.getUser(), 
				detailedScreenPreBootModeOutput.getReflectPlanState().value);
		// 取得した「申請表示情報」を更新する
		AppDetailScreenInfo appDetailScreenInfo = new AppDetailScreenInfo(
				detailScreenAppData.getApplication(), 
				detailScreenAppData.getDetailScreenApprovalData().getApprovalLst(), 
				detailScreenAppData.getDetailScreenApprovalData().getAuthorComment(), 
				detailedScreenPreBootModeOutput.getUser(), 
				detailedScreenPreBootModeOutput.getReflectPlanState(), 
				outputMode);
		appDetailScreenInfo.setPastApp(detailedScreenPreBootModeOutput.isPastApp());
		appDetailScreenInfo.setAuthorizableFlags(Optional.of(detailedScreenPreBootModeOutput.isAuthorizableFlags()));
		appDetailScreenInfo.setApprovalATR(Optional.of(detailedScreenPreBootModeOutput.getApprovalATR()));
		appDetailScreenInfo.setAlternateExpiration(Optional.of(detailedScreenPreBootModeOutput.isAlternateExpiration()));
		appDispInfoStartupOutput.getAppDispInfoNoDateOutput().setOpEmployeeInfo(opEmployeeInfoImport);
		appDispInfoStartupOutput.setAppDetailScreenInfo(Optional.of(appDetailScreenInfo));
		// 更新した「申請表示情報」を返す
		return appDispInfoStartupOutput;
	}

}
