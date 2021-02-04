package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command.AbsenceLeaveAppCmd;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command.RecruitmentAppCmd;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInforWhenStarting;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInformationApplication;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.LinkingManagementInforDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.RemainingHolidayInforDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * @author ThanhPV
 */
/**
 * @author thanhpv
 *
 */
@Stateless
public class HolidayShipmentScreenBFinder {

	@Inject
	private ComSubstVacationRepository comSubrepo;

	@Inject
	private RemainNumberTempRequireService remainNumberTempRequireService;
	
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepo;
	
	@Inject
	private RecruitmentAppRepository recRepo;
	
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	
	@Inject
	private HolidayShipmentScreenAFinder aFinder;
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;
	
	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;
	
	// 1.振休振出申請（詳細）起動処理(đơn xin nghỉ bù làm bù(detail) Xử lý khởi động)
	public DisplayInforWhenStarting startPageBRefactor(String companyId, String appId, AppDispInfoStartupDto appDispInfoStartup) {
		DisplayInforWhenStarting result = new DisplayInforWhenStarting();
		result.appDispInfoStartup = appDispInfoStartup;
		
		//ドメインモデル「振休振出申請」を取得する(Lấy domain[đơn xin nghi bu lam bu])
		Optional<RecruitmentApp> rec = recRepo.findByAppId(appId);
		Optional<AbsenceLeaveApp> abs = absRepo.findByAppId(appId);
		Optional<AppHdsubRec> appHdsubRec = appHdsubRecRepository.findByAppId(appId);
		if(appHdsubRec.isPresent() && rec.isPresent()) {
			abs = absRepo.findByAppId(appHdsubRec.get().getAbsenceLeaveAppID());
		}else if(appHdsubRec.isPresent()){
			rec = recRepo.findByAppId(appHdsubRec.get().getRecAppID());
		}
		
		result.setRec(rec.map(c-> RecruitmentAppCmd.fromDomain(c)).orElse(null));
		result.setAbs(abs.map(c-> AbsenceLeaveAppCmd.fromDomain(c)).orElse(null));
		
		String employeeID = null;
		if(result.existRec()) {
			employeeID = result.rec.getApplication().getEmployeeID();
		}else {
			employeeID = result.abs.getApplication().getEmployeeID();
		}
		
		// 振休振出申請設定の取得(get setting đơn xin nghỉ bù làm bù)
		aFinder.getWithDrawalReqSet(companyId, result);
		if(result.existAbs()) {
			DisplayInformationApplication applicationForHoliday = new DisplayInformationApplication();
			//振休用勤務種類の取得(Lấy worktype nghỉ bù)
			List<WorkType> workTypeListAbs = aFinder.getWorkTypeForHoliday(companyId, appDispInfoStartup.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(), result.abs.workInformation.getWorkType());
			//振休申請起動時の表示情報．勤務種類リスト=取得した振休用勤務種類(List) /(DisplayInfo khi khởi động đơn xin nghỉ bù. WorktypeList= worktype nghỉ bù(List) đã lấy)
			applicationForHoliday.setWorkTypeList(workTypeListAbs.stream().map(c->WorkTypeDto.fromDomain(c)).collect(Collectors.toList()));
			result.applicationForHoliday = applicationForHoliday;
		}
		if(result.existRec()) {
			DisplayInformationApplication applicationForWorkingDay = new DisplayInformationApplication();
			//振出用勤務種類の取得(Lấy worktype làm bù)
			List<WorkType> workTypeListRec = aFinder.getWorkTypeForWorkingDay(companyId, appDispInfoStartup.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(), result.rec.workInformation.getWorkType());
			//振出申請起動時の表示情報．勤務種類リスト=取得した振出用勤務種類(List)/ (DisplayInfo khi khởi động đơn xin làm bù. WorktypeList = worktype làm bù(list đã lấy))
			applicationForWorkingDay.setWorkTypeList(workTypeListRec.stream().map(c->WorkTypeDto.fromDomain(c)).collect(Collectors.toList()));
			result.applicationForWorkingDay = applicationForWorkingDay;
		}
		//[No.506]振休残数を取得する ([No.506]Lấy số ngày nghỉ bù còn lại)
		AbsRecRemainMngOfInPeriod absRecMngRemain = AbsenceReruitmentMngInPeriodQuery.getAbsRecMngRemain(remainNumberTempRequireService.createRequire(), new CacheCarrier(), employeeID, GeneralDate.today());
		
		RemainingHolidayInforDto remainingHolidayInfor = new RemainingHolidayInforDto(absRecMngRemain);
		
		//一番近い期限日を取得する - get ngày kì hạn gần nhất
		Optional<GeneralDate> closestDueDate = aFinder.getClosestDeadline(absRecMngRemain.getLstAbsRecMng());
		if(closestDueDate.isPresent()) {
			remainingHolidayInfor.setClosestDueDate(closestDueDate.get().toString());
		}
		
		result.setRemainingHolidayInfor(remainingHolidayInfor);
		
		//振休の紐付け管理区分を取得する get phân loại quản lý liên kết của nghỉ bù
		Optional<ComSubstVacation> comSubstVacation = comSubrepo.findById(companyId);
		if(comSubstVacation.isPresent()) {
			result.setHolidayManage(comSubstVacation.get().getLinkingManagementATR().value);
		}else {
			result.setHolidayManage(ManageDistinct.NO.value);
		}
		
		//代休の紐付け管理区分を取得する get phân loại quản lý liên kết của nghỉ thay thế
		CompensatoryLeaveComSetting compensatoryLeaveComSetting = compensLeaveComSetRepo.find(companyId);
		if(compensatoryLeaveComSetting == null) {
			result.setSubstituteManagement(ManageDistinct.NO.value);
		}else {
			result.setSubstituteManagement(compensatoryLeaveComSetting.getIsManaged().value);
		}
		
		LinkingManagementInforDto linkingManagementInfor = this.getLinkingManagementInfor(companyId, employeeID, rec, abs, result.substituteManagement == 1, result.holidayManage == 1);
		if(result.existRec()) {
			result.rec.leaveComDayOffMana = linkingManagementInfor.recLeaveComDayOffMana;
		}
		if(result.existAbs()){
			result.abs.leaveComDayOffMana = linkingManagementInfor.absLeaveComDayOffMana;
			result.abs.payoutSubofHDManagements = linkingManagementInfor.absPayoutSubofHDManagements;
		}
		
		return result;
	}
	
	
	/**
	 * @name 紐付管理情報を取得する
	 * @param companyId 会社ID
	 * @param appId 社員ID
	 * @param rec 振出申請
	 * @param abs 振休申請
	 * @param substituteManagement 代休紐付け管理区分
	 * @param holidayManage 振休紐付け管理区分
	 * @return
	 */
	public LinkingManagementInforDto getLinkingManagementInfor(String companyId, String employeeID, Optional<RecruitmentApp> rec, Optional<AbsenceLeaveApp> abs, boolean substituteManagement, boolean holidayManage) {
		LinkingManagementInforDto result = new LinkingManagementInforDto();
		//INPUT．振出申請をチェックする
		if(rec.isPresent()) {
			//<<Public>> 指定した勤務種類をすべて取得する
			Optional<WorkType> workType = workTypeRepo.findByDeprecated(companyId, rec.get().getWorkInformation().getWorkTypeCode().v());
			if(workType.isPresent()) {
				result.recLeaveComDayOffMana = this.getLinkingManagement(employeeID, rec.get().getAppDate().getApplicationDate(), workType.get(), substituteManagement).stream().map(c-> LeaveComDayOffManaDto.fromDomain(c)).collect(Collectors.toList());
			}
		}
		
		if(abs.isPresent()) {
			//<<Public>> 指定した勤務種類をすべて取得する
			Optional<WorkType> workType = workTypeRepo.findByDeprecated(companyId, abs.get().getWorkInformation().getWorkTypeCode().v());
			if(workType.isPresent()) {
				result.absLeaveComDayOffMana = this.getLinkingManagement(employeeID, abs.get().getAppDate().getApplicationDate(), workType.get(), substituteManagement).stream().map(c-> LeaveComDayOffManaDto.fromDomain(c)).collect(Collectors.toList());
				result.absPayoutSubofHDManagements = this.getLinkingManagementAbs(employeeID, abs.get().getAppDate().getApplicationDate(), workType.get(), holidayManage).stream().map(c-> PayoutSubofHDManagementDto.fromDomain(c)).collect(Collectors.toList());
			}
		}
		
		return result;
	}
	
	/**
	 * @name 振出の紐付管理情報を取得する
	 * @param employeeID 社員ID
	 * @param applicationDate 対象日
	 * @param workType 勤務種類
	 * @param substituteManagement 代休紐付け管理区分
	 * @return
	 */
	public List<LeaveComDayOffManagement> getLinkingManagement(String employeeID, GeneralDate applicationDate, WorkType workType, boolean substituteManagement) {
		//INPUT．代休紐付け管理区分をチェックする
		if(substituteManagement && (
				(workType.getDailyWork().getWorkTypeUnit().isOneDay() && workType.getDailyWork().getOneDay() == WorkTypeClassification.SubstituteHoliday) || 
				(workType.getDailyWork().getWorkTypeUnit().isMonringAndAfternoon() && (workType.getDailyWork().getMorning() == WorkTypeClassification.SubstituteHoliday || workType.getDailyWork().getAfternoon() == WorkTypeClassification.SubstituteHoliday))
				)
		){
			return leaveComDayOffManaRepository.getByListDate(employeeID, Arrays.asList(applicationDate))
					.stream().filter(c-> { 
						return c.getAssocialInfo().getTargetSelectionAtr() == TargetSelectionAtr.REQUEST 
								&& c.getAssocialInfo().getDayNumberUsed().v().compareTo(workType.getDailyWork().getWorkTypeUnit().isOneDay() ? 1 : 0.5) == 0;
						}
					).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
	
	
	/**
	 * @name 振休の紐付管理情報を取得する
	 * @param employeeID 社員ID
	 * @param applicationDate 対象日
	 * @param workType 勤務種類
	 * @param substituteManagement 代休紐付け管理区分
	 * @param holidayManage 振休紐付け管理区分
	 * @return
	 */
	public List<PayoutSubofHDManagement> getLinkingManagementAbs(String employeeID, GeneralDate applicationDate, WorkType workType, boolean holidayManage) {
		
		//INPUT．「勤務種類」が代休の勤務種類かチェックする
		//INPUT．代休紐付け管理区分をチェックする
		//ドメインモデル「休出代休紐付け管理」を取得する
		// get form this.getLinkingManagementRec()
		
		//INPUT．「勤務種類」が振休の勤務種類かチェックする
		if(holidayManage && (
				(workType.getDailyWork().getWorkTypeUnit().isOneDay() && workType.getDailyWork().getOneDay() == WorkTypeClassification.Pause) || 
				(workType.getDailyWork().getWorkTypeUnit().isMonringAndAfternoon() && (workType.getDailyWork().getMorning() == WorkTypeClassification.Pause || workType.getDailyWork().getAfternoon() == WorkTypeClassification.Pause))
				)
		){
			return payoutSubofHDManaRepository.getByListDate(employeeID, Arrays.asList(applicationDate))
					.stream().filter(c-> { 
						return c.getAssocialInfo().getTargetSelectionAtr() == TargetSelectionAtr.REQUEST 
								&& c.getAssocialInfo().getDayNumberUsed().v().compareTo(workType.getDailyWork().getWorkTypeUnit().isOneDay() ? 1 : 0.5) == 0;
						}
					).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
}
