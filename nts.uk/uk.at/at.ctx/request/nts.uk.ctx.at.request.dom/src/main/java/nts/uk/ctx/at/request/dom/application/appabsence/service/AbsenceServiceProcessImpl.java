package nts.uk.ctx.at.request.dom.application.appabsence.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeaveRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.service.four.AppAbsenceFourProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.SpecAbsenceDispInfo;
import nts.uk.ctx.at.request.dom.application.appabsence.service.three.AppAbsenceThreeProcess;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaGrantRemainingImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.AppliedDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.UseAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.vacation.history.service.PlanVacationRuleError;
import nts.uk.ctx.at.request.dom.vacation.history.service.PlanVacationRuleExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.MaxNumberDayType;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.CheckWkTypeSpecHdEventOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.DateSpecHdRelationOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.MaxDaySpecHdOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.SpecialHolidayEventAlgorithm;
import nts.uk.ctx.at.shared.dom.vacation.setting.SettingDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AnnualHoliday;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.LeaveSetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AbsenceServiceProcessImpl implements AbsenceServiceProcess{
	@Inject
	private AppAbsenceRepository appAbsenceRepository;
	@Inject
	private ApplicationApprovalService_New appRepository;
	@Inject
	private PlanVacationRuleExport planVacationRuleExport;
	@Inject
	private AbsenceTenProcess absenceTenProcess;
	@Inject
	private AppForSpecLeaveRepository repoSpecLeave;
	@Inject
	private AcquisitionRuleRepository repoAcquisitionRule;
	@Inject
	private GetClosureStartForEmployee getClosureStartForEmp;
	@Inject
	private AbsenceReruitmentMngInPeriodQuery absRertMngInPeriod;
	@Inject
	private BreakDayOffMngInPeriodQuery breakDayOffMngInPeriod;
	@Inject
	private AnnLeaveRemainNumberAdapter annLeaRemNumberAdapter;
	@Inject
	private ReserveLeaveManagerApdater rsvLeaMngApdater;
	
	@Inject
	private HdAppSetRepository hdAppSetRepository;
	
	@Inject
	private DisplayReasonRepository displayRep;
	
	@Inject
	private SpecialHolidayEventAlgorithm specialHolidayEventAlgorithm;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private BasicScheduleService basicScheduleService;
	
	@Inject
	private PredetemineTimeSettingRepository predTimeRepository;
	
	@Inject
	private AppAbsenceFourProcess appAbsenceFourProcess;
	
	@Inject
	private AppAbsenceThreeProcess appAbsenceThreeProcess;
	
	@Override
	public SpecialLeaveInfor getSpecialLeaveInfor(String workTypeCode) {
		SpecialLeaveInfor specialLeaveInfor = new SpecialLeaveInfor();
//		boolean relationFlg = false;
//		boolean mournerDisplayFlg = false;
//		boolean displayRelationReasonFlg = false;
//		int maxDayRelate = 0;
		//指定した勤務種類に特別休暇に当てはまるかチェックする
		
		return specialLeaveInfor;
	}

	@Override
	public void createAbsence(AppAbsence domain, Application_New newApp) {
		// insert Application
		this.appRepository.insert(newApp);
		// insert Absence
		this.appAbsenceRepository.insertAbsence(domain);
		if(domain.getHolidayAppType().equals(HolidayAppType.SPECIAL_HOLIDAY) && domain.getAppForSpecLeave() != null){
			repoSpecLeave.addSpecHd(domain.getAppForSpecLeave());
		}
		
	}

	/**
	 * 13.計画年休上限チェック
	 */
	@Override
	public void checkLimitAbsencePlan(String cID, String sID, String workTypeCD, GeneralDate sDate, GeneralDate eDate,
			HolidayAppType hdAppType, List<GeneralDate> lstDateIsHoliday) {
		//INPUT．休暇種類をチェックする(check INPUT. phân loại holidays)
		if(hdAppType.equals(HolidayAppType.ANNUAL_PAID_LEAVE)){//INPUT．休暇種類が年休
			//計画年休の上限チェック(check giới hạn trên của plan annual holidays)
			List<PlanVacationRuleError> check = planVacationRuleExport.checkMaximumOfPlan(cID, sID, workTypeCD, new DatePeriod(sDate, eDate), lstDateIsHoliday);
			if(!check.isEmpty()){
				if(check.contains(PlanVacationRuleError.OUTSIDEPERIOD)) {
					//Msg_1345を表示
					throw new BusinessException("Msg_1453");	
				} else {
					throw new BusinessException("Msg_1345");
				}
				
			}
		}
	}
	/**
	 * @author hoatt
	 * 14.休暇種類表示チェック
	 * @param companyID
	 * @param sID
	 * @param baseDate
	 * @return
	 */
	@Override
	public CheckDispHolidayType checkDisplayAppHdType(String companyID, String sID, GeneralDate baseDate) {
		//A4_3 - 年休設定
		boolean isYearManage = false;
		//A4_4 - 代休管理設定
		boolean isSubHdManage = false;
		//A4_5 - 振休管理設定
		boolean isSubVacaManage = false;
		//A4_8 - 積立年休設定
		boolean isRetentionManage = false;
		//10-1.年休の設定を取得する
		AnnualHolidaySetOutput annualHd = absenceTenProcess.getSettingForAnnualHoliday(companyID);
		isYearManage = annualHd.isYearHolidayManagerFlg();
		//10-4.積立年休の設定を取得する
		isRetentionManage = absenceTenProcess.getSetForYearlyReserved(companyID, sID, baseDate);
		//10-2.代休の設定を取得する
		SubstitutionHolidayOutput subHd = absenceTenProcess.getSettingForSubstituteHoliday(companyID, sID, baseDate);
		isSubHdManage = subHd.isSubstitutionFlg();
		//10-3.振休の設定を取得する
		LeaveSetOutput leaveSet = absenceTenProcess.getSetForLeave(companyID, sID, baseDate);
		isSubVacaManage = leaveSet.isSubManageFlag();
		return new CheckDispHolidayType(isYearManage, isSubHdManage, isSubVacaManage, isRetentionManage);
	}
	/**
	 * @author hoatt
	 * 代休振休優先消化チェック
	 * @param pridigCheck - 休暇申請設定．年休より優先消化チェック区分 - HdAppSet
	 * @param isSubVacaManage - 振休管理設定．管理区分
	 * @param subVacaTypeUseFlg - 休暇申請対象勤務種類．休暇種類を利用しない（振休） - AppEmploymentSetting
	 * @param isSubHdManage - 代休管理設定．管理区分
	 * @param subHdTypeUseFlg - 休暇申請対象勤務種類．休暇種類を利用しない（代休） - AppEmploymentSetting
	 * @param numberSubHd - 代休残数
	 * @param numberSubVaca - 振休残数
	 * @return エラーメッセージ - 確認メッセージ
	 */
	@Override
	public void checkDigestPriorityHd(AppliedDate pridigCheck, boolean isSubVacaManage, boolean subVacaTypeUseFlg,
			boolean isSubHdManage, boolean subHdTypeUseFlg, int numberSubHd, int numberSubVaca) {
		//新規モード(new mode)
		//アルゴリズム「振休代休優先チェック」を実行する(Thực hiện thuật toán 「Check độ ưu tiên substituteHoliday và rest 」)
		this.checkPriorityHoliday(pridigCheck, isSubVacaManage, subVacaTypeUseFlg, isSubHdManage,
				subHdTypeUseFlg, numberSubHd, numberSubVaca);
	}
	/**
	 * @author hoatt
	 * 振休代休優先チェック
	 * @param pridigCheck - 休暇申請設定．年休より優先消化チェック区分 - HdAppSet
	 * @param isSubVacaManage - 振休管理設定．管理区分
	 * @param subVacaTypeUseFlg - 休暇申請対象勤務種類．休暇種類を利用しない（振休） - AppEmploymentSetting
	 * @param isSubHdManage - 代休管理設定．管理区分
	 * @param subHdTypeUseFlg - 休暇申請対象勤務種類．休暇種類を利用しない（代休） - AppEmploymentSetting
	 * @param numberSubHd - 代休残数
	 * @param numberSubVaca - 振休残数
	 * @return エラーメッセージ - 確認メッセージ
	 */
	@Override
	public void checkPriorityHoliday(AppliedDate pridigCheck, boolean isSubVacaManage, boolean subVacaTypeUseFlg,
			boolean isSubHdManage, boolean subHdTypeUseFlg, int numberSubHd, int numberSubVaca) {
		//休暇申請設定．年休より優先消化チェック区分をチェックする(Check pridigCheck)
		if(pridigCheck.equals(AppliedDate.DONT_CHECK)){//チェックしないの場合
			return;
		}
		//それ以外
		//ログインしている会社をもとにドメインモデル「休暇の取得ルール」を取得する (lấy dữ liệu domain 「AcquisitionRule」 dựa vào công ty đang đăng nhập)
		Optional<AcquisitionRule> acqRule = repoAcquisitionRule.findById(AppContexts.user().companyId());
		if(!acqRule.isPresent()){
			return;
		}
		AcquisitionRule rule = acqRule.get();
		AnnualHoliday annualHoliday = rule.getAnnualHoliday();
		//振休使用フラグをチェックする (Check restFlag)
		//休暇の取得ルール．年休より優先する休暇．振休を優先＝ false OR 休暇申請対象勤務種類．休暇種類を利用しない（振休）＝ true OR 振休管理設定．管理区分＝管理しない
//		2018/09/07 muto upd EA#2660
//		条件の追加： 「休暇の取得ルール．取得する順番をチェックする＝設定しない」
		if(annualHoliday.isPrioritySubstitute() && !subVacaTypeUseFlg && isSubVacaManage 
				&& rule.getCategory().equals(SettingDistinct.YES)){
			//振休残数をチェックする (Check restRemaining)
			if(numberSubVaca > 0){//振休残数>0(restRemaining >0)
				if(pridigCheck.equals(AppliedDate.CHECK_IMPOSSIBLE)){//年休より優先消化チェック区分＝チェックする（登録不可）(pridigCheck == CHECK_IMPOSSIBLE)
					//エラーメッセージ（Msg_1392）を返す (Return errorMessage)
					throw new BusinessException("Msg_1392");
				}
				//年休より優先消化チェック区分＝チェックする（登録可）(pridigCheck == CHECK_AVAILABLE)
				//確認メッセージ（Msg_1393）を返す (Return confirmMessage)
				throw new BusinessException("Msg_1393");
			}
		}
		//休暇の取得ルール．年休より優先する休暇．代休を優先＝ false OR 休暇申請対象勤務種類．休暇種類を利用しない（代休）＝ true OR 代休管理設定．管理区分＝管理しない
		//代休使用フラグをチェックする (Check substituteHolidayFlag)
//		2018/09/07 muto upd EA#2660
//		条件の追加： 「休暇の取得ルール．取得する順番をチェックする＝設定しない」
		if(!annualHoliday.isPriorityPause() || subHdTypeUseFlg || !isSubHdManage || rule.getCategory().equals(SettingDistinct.NO)){
			return;
		}
		if(numberSubHd <= 0){//代休残数<=0
			return;
		}
		//代休残数>0(substituteHolidayRemaining > 0)
		if(pridigCheck.equals(AppliedDate.CHECK_IMPOSSIBLE)){//年休より優先消化チェック区分＝チェックする（登録不可）(pridigCheck == CHECK_IMPOSSIBLE)
			//エラーメッセージ（Msg_1394）を返す
			throw new BusinessException("Msg_1394");
		}
		//年休より優先消化チェック区分＝チェックする（登録可）(pridigCheck == CHECK_AVAILABLE)
		//確認メッセージ（Msg_1395）を返す
		throw new BusinessException("Msg_1395");
	}
	/**
	 * @author hoatt
	 * 残数取得する
	 * @param companyID - 会社ID
	 * @param employeeID - 社員ID　＝申請者社員ID
	 * @param baseDate - 基準日
	 * @return 年休残数-代休残数-振休残数-ストック休暇残数
	 */
	@Override
	public NumberOfRemainOutput getNumberOfRemaining(String companyID, String employeeID, GeneralDate baseDate,
			boolean yearManage, boolean subHdManage, boolean subVacaManage, boolean retentionManage) {
		//アルゴリズム「社員に対応する締め開始日を取得する」を実行する
		Optional<GeneralDate> closure = getClosureStartForEmp.algorithm(employeeID);
		if(!closure.isPresent()){
			return new NumberOfRemainOutput(null, null, null, null);
		}
		//年休残数
		Double yearRemain = null;
		//代休残数
		Double subHdRemain = null;
		//振休残数
		Double subVacaRemain = null;
		//ストック休暇残数
		Double stockRemain = null;
		GeneralDate closureDate = closure.get();
		
		//1
		if(subVacaManage){//output．振休管理区分が管理する
			//アルゴリズム「期間内の振出振休残数を取得する」を実行する - RQ204
			//・会社ID＝ログイン会社ID
//			・社員ID＝申請者社員ID
//			・集計開始日＝締め開始日
//			・集計終了日＝締め開始日.AddYears(1).AddDays(-1)
//			・モード＝その他モード
//			・基準日＝申請開始日
//			・上書きフラグ＝false
			AbsRecMngInPeriodParamInput paramInput = new AbsRecMngInPeriodParamInput(companyID, employeeID, new DatePeriod(closureDate, closureDate.addYears(1).addDays(-1)), 
					baseDate, false, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty());
			AbsRecRemainMngOfInPeriod subVaca = absRertMngInPeriod.getAbsRecMngInPeriod(paramInput);
			//振休残数 ← 残日数　（アルゴリズム「期間内の振出振休残数を取得する」のoutput）
			subVacaRemain = subVaca.getRemainDays();//残日数
		}
		
		//2
		if(subHdManage){//output．代休管理区分が管理する
			//アルゴリズム「期間内の休出代休残数を取得する」を実行する - RQ203
			//・会社ID＝ログイン会社ID
//			・社員ID＝申請者社員ID
//			・集計開始日＝締め開始日
//			・集計終了日＝締め開始日.AddYears(1).AddDays(-1)
//			・モード＝その他モード
//			・基準日＝申請開始日
//			・上書きフラグ＝false
			BreakDayOffRemainMngParam inputParam = new BreakDayOffRemainMngParam(companyID, employeeID, new DatePeriod(closureDate, closureDate.addYears(1).addDays(-1)), 
					false, baseDate, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty());
			BreakDayOffRemainMngOfInPeriod subHd = breakDayOffMngInPeriod.getBreakDayOffMngInPeriod(inputParam);
			//代休残数 ← 残日数　（アルゴリズム「期間内の代休残数を取得する」のoutput）
			subHdRemain = subHd.getRemainDays();
		}
		
		//3
		if(retentionManage){//output．積休管理区分が管理する
			//基準日時点の積立年休残数を取得する - RQ201
			Optional<RsvLeaManagerImport> stock = rsvLeaMngApdater.getRsvLeaveManager(employeeID, baseDate);
			if(stock.isPresent()){
				//積休残数 ←  積立年休情報.残数.積立年休（マイナスあり）.残数.合計残日数 
				//reserveLeaveInfo.remainingNumber.reserveLeaveWithMinus.remainingNumber.totalRemainingDays
				if(stock.get().getGrantRemainingList().size() > 0){
					stockRemain = new Double(0L);
					for (RsvLeaGrantRemainingImport rsv : stock.get().getGrantRemainingList()) {
						stockRemain = stockRemain + rsv.getRemainingNumber();
					}
				}
			}
		}
		
		//4
		if(yearManage){//output．年休管理区分が管理する
			//基準日時点の年休残数を取得する - RQ198
			ReNumAnnLeaReferenceDateImport year = annLeaRemNumberAdapter.getReferDateAnnualLeaveRemainNumber(employeeID, baseDate);
			//年休残数 ← 年休残数.年休残数（付与前）日数 annualLeaveRemainNumberExport.annualLeaveGrantPreDay
			yearRemain = year.getAnnualLeaveRemainNumberExport() == null ? null : 
				year.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantDay();
		}
        return NumberOfRemainOutput.init(yearRemain, subHdRemain, subVacaRemain, stockRemain, yearManage, subHdManage, subVacaManage, retentionManage);
	}

	@Override
	public HolidayRequestSetOutput getHolidayRequestSet(String companyID) {
		// ドメインモデル「休暇申請設定」を取得する(lấy dữ liệu domain 「休暇申請設定」)
		HdAppSet hdAppSet = hdAppSetRepository.getAll().get();
		// ドメインモデル「申請理由表示」を取得する(lấy dữ liệu domain 「申請理由表示」)
		List<DisplayReason> displayReasonLst = displayRep.findDisplayReason(companyID);
		// 取得した情報を返す
		return new HolidayRequestSetOutput(hdAppSet, displayReasonLst);
	}

	@Override
	public RemainVacationInfo getRemainVacationInfo(String companyID, String employeeID, GeneralDate date) {
		// 各休暇の管理区分を取得する
		CheckDispHolidayType checkDispHolidayType = this.checkDisplayAppHdType(companyID, employeeID, date);
		// 各休暇の残数を取得する
		NumberOfRemainOutput numberOfRemainOutput = this.getNumberOfRemaining(
				companyID, 
				employeeID, 
				date, 
				checkDispHolidayType.isYearManage(), 
				checkDispHolidayType.isSubHdManage(), 
				checkDispHolidayType.isSubVacaManage(), 
				checkDispHolidayType.isRetentionManage());
		// 取得した情報もとに「休暇残数情報」にセットして返す
		return new RemainVacationInfo(
				checkDispHolidayType.isYearManage(), 
				checkDispHolidayType.isSubHdManage(), 
				checkDispHolidayType.isSubVacaManage(), 
				checkDispHolidayType.isRetentionManage(), 
				numberOfRemainOutput.getYearRemain(), 
				numberOfRemainOutput.getSubHdRemain(), 
				numberOfRemainOutput.getSubVacaRemain(), 
				numberOfRemainOutput.getStockRemain());
	}

	@Override
	public AppAbsenceStartInfoOutput getSpecAbsenceUpperLimit(String companyID,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, Optional<String> workTypeCD) {
		// 「休暇申請起動時の表示情報」の「特別休暇表示情報」をクリアする
		appAbsenceStartInfoOutput.setSpecAbsenceDispInfo(Optional.empty());
		if(!appAbsenceStartInfoOutput.getSelectedWorkTypeCD().isPresent() ||
				Strings.isBlank(appAbsenceStartInfoOutput.getSelectedWorkTypeCD().get())) {
			// 「休暇申請起動時の表示情報」を返す
			return appAbsenceStartInfoOutput;
		}
		// 指定する勤務種類が事象に応じた特別休暇かチェックする
		CheckWkTypeSpecHdEventOutput checkWkTypeSpecHdEventOutput = specialHolidayEventAlgorithm.checkWkTypeSpecHdForEvent(companyID, workTypeCD.get());
		if(checkWkTypeSpecHdEventOutput.getSpecHdEvent().get().getMaxNumberDay() == MaxNumberDayType.REFER_RELATIONSHIP) {
			// 指定する特休枠の続柄に対する上限日数を取得する
			List<DateSpecHdRelationOutput> dateSpecHdRelationOutputLst = 
					specialHolidayEventAlgorithm.getMaxDaySpecHdByRelaFrameNo(companyID, checkWkTypeSpecHdEventOutput.getFrameNo().get());
			// 指定する特休枠の上限日数を取得する
			MaxDaySpecHdOutput maxDaySpecHdOutput = specialHolidayEventAlgorithm.getMaxDaySpecHd(
					companyID, 
					checkWkTypeSpecHdEventOutput.getFrameNo().get(), 
					checkWkTypeSpecHdEventOutput.getSpecHdEvent().get(), 
					dateSpecHdRelationOutputLst.stream().findFirst().map(x -> x.getRelationCD()));
			// INPUT．「休暇申請起動時の表示情報」に項目をセットして返す
			SpecAbsenceDispInfo specAbsenceDispInfo = new SpecAbsenceDispInfo(
					checkWkTypeSpecHdEventOutput.isSpecHdForEventFlag(), 
					checkWkTypeSpecHdEventOutput.getSpecHdEvent(), 
					checkWkTypeSpecHdEventOutput.getFrameNo(), 
					Optional.of(maxDaySpecHdOutput.getMaxDay()), 
					Optional.of(maxDaySpecHdOutput.getDayOfRela()),
					Optional.of(dateSpecHdRelationOutputLst));
			appAbsenceStartInfoOutput.setSpecAbsenceDispInfo(Optional.of(specAbsenceDispInfo));
		} else {
			// 指定する特休枠の上限日数を取得する
			MaxDaySpecHdOutput maxDaySpecHdOutput = specialHolidayEventAlgorithm.getMaxDaySpecHd(
					companyID, 
					checkWkTypeSpecHdEventOutput.getFrameNo().get(), 
					checkWkTypeSpecHdEventOutput.getSpecHdEvent().get(),
					Optional.empty());
			// INPUT．「休暇申請起動時の表示情報」に項目をセットして返す
			SpecAbsenceDispInfo specAbsenceDispInfo = new SpecAbsenceDispInfo(
					checkWkTypeSpecHdEventOutput.isSpecHdForEventFlag(), 
					checkWkTypeSpecHdEventOutput.getSpecHdEvent(), 
					checkWkTypeSpecHdEventOutput.getFrameNo(), 
					Optional.of(maxDaySpecHdOutput.getMaxDay()), 
					Optional.of(maxDaySpecHdOutput.getDayOfRela()),
					Optional.empty());
			appAbsenceStartInfoOutput.setSpecAbsenceDispInfo(Optional.of(specAbsenceDispInfo));
		}
		return appAbsenceStartInfoOutput;
	}

	@Override
	public AppAbsenceStartInfoOutput workTimesChangeProcess(String companyID,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, String workTypeCD, Optional<String> workTimeCD, Integer holidayType) {
		// INPUT．「休暇申請起動時の表示情報．勤務時間帯一覧」をクリアする
		appAbsenceStartInfoOutput.setWorkTimeLst(new ArrayList<>());
		// INPUT．「休暇申請起動時の表示情報．選択中の就業時間帯」を更新する
		appAbsenceStartInfoOutput.setSelectedWorkTimeCD(workTimeCD);
		// INPUT．「就業時間帯コード」を確認する
		if(!workTimeCD.isPresent() || 
				Strings.isBlank(workTimeCD.get())) {
			// 「休暇申請起動時の表示情報」を返す
			return appAbsenceStartInfoOutput;
		}
		// INPUT．「休暇種類」をチェックする(Check HolidayType được chọn)
		if (holidayType != null && holidayType == HolidayAppType.DIGESTION_TIME.value) {
			// アルゴリズム「必要な時間を算出する」を実行する(Thực hiện [Tính toán thời gian cần thiết])
			// 9.必要な時間を算出する
			// pending / chưa đối ứng
		}
		// 勤務時間初期値の取得
		PrescribedTimezoneSetting prescribedTimezoneSet = this.initWorktimeCode(companyID, workTypeCD, workTimeCD.get());
		// 返ってきた「時間帯(使用区分付き)」を「休暇申請起動時の表示情報」にセットする
		if(prescribedTimezoneSet != null) {
			appAbsenceStartInfoOutput.setWorkTimeLst(prescribedTimezoneSet.getLstTimezone());
		}
		// 「休暇申請起動時の表示情報」を返す
		return appAbsenceStartInfoOutput;
	}
	
	/**
	 * 勤務時間初期値の取得
	 * 
	 * @param companyID
	 * @param workTypeCode
	 * @param workTimeCode
	 * @return
	 */
	public PrescribedTimezoneSetting initWorktimeCode(String companyID, String workTypeCode, String workTimeCode) {
		Optional<WorkType> WkTypeOpt = workTypeRepository.findByPK(companyID, workTypeCode);
		if (WkTypeOpt.isPresent()) {
			// アルゴリズム「1日半日出勤・1日休日系の判定」を実行する
			WorkStyle workStyle = basicScheduleService.checkWorkDay(WkTypeOpt.get().getWorkTypeCode().toString());
			if (workStyle == null) {
				return null;
			}
			if (!workStyle.equals(WorkStyle.ONE_DAY_REST)) {
				// アルゴリズム「所定時間帯を取得する」を実行する
				// 所定時間帯を取得する
				if (workTimeCode != null && !workTimeCode.equals("")) {
					if (predTimeRepository.findByWorkTimeCode(companyID, workTimeCode).isPresent()) {
						PrescribedTimezoneSetting prescribedTzs = predTimeRepository
								.findByWorkTimeCode(companyID, workTimeCode).get().getPrescribedTimezoneSetting();
						return prescribedTzs;
					}
				}
			}
		}
		return null;
	}

	@Override
	public AppAbsenceStartInfoOutput workTypeChangeProcess(String companyID,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, Integer holidayType, Optional<String> workTypeCD) {
		// INPUT．「休暇申請起動時の表示情報．選択中の勤務種類」にセットする
		appAbsenceStartInfoOutput.setSelectedWorkTypeCD(workTypeCD);
		// 就業時間帯の表示制御フラグを確認する
		boolean controlDispWorkingHours = appAbsenceFourProcess.getDisplayControlWorkingHours(
				workTypeCD.get(), 
				Optional.of(appAbsenceStartInfoOutput.getHdAppSet()), 
				companyID);
		// 返ってきた「就業時間帯表示フラグ」を「休暇申請起動時の表示情報」にセットする
		appAbsenceStartInfoOutput.setWorkTimeDisp(controlDispWorkingHours);
		// INPUT．「休暇種類」をチェックする
		if(holidayType == HolidayAppType.SPECIAL_HOLIDAY.value) {
			// 特別休暇の上限情報取得する
			appAbsenceStartInfoOutput = this.getSpecAbsenceUpperLimit(companyID, appAbsenceStartInfoOutput, workTypeCD);
		}
		// 取得した「就業時間帯表示フラグ」を確認する
		if(controlDispWorkingHours) {
			// 就業時間帯変更時処理
			appAbsenceStartInfoOutput = this.workTimesChangeProcess(
					companyID, 
					appAbsenceStartInfoOutput, 
					workTypeCD.get(), 
					appAbsenceStartInfoOutput.getSelectedWorkTimeCD(), 
					holidayType);
		}
		// 返ってきた「休暇申請起動時の表示情報」を返す
		return appAbsenceStartInfoOutput;
	}

	@Override
	public AppAbsenceStartInfoOutput holidayTypeChangeProcess(String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, 
			boolean displayHalfDayValue, Integer alldayHalfDay, Integer holidayType) {
		// INPUT．「休暇申請起動時の表示情報．勤務種類一覧」をクリアする
		appAbsenceStartInfoOutput.setWorkTypeLst(new ArrayList<>());
		// 勤務種類を取得する
		List<WorkType> workTypes = appAbsenceThreeProcess.getWorkTypeDetails(
				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getEmploymentSet(), 
				companyID, 
				holidayType, 
				alldayHalfDay,
				displayHalfDayValue);
		// 返ってきた「勤務種類<List>」を「休暇申請起動時の表示情報」にセットする
		appAbsenceStartInfoOutput.setWorkTypeLst(workTypes);
		// 「休暇申請起動時の表示情報．選択中の勤務種類」を更新する
		List<String> workTypeCDLst = workTypes.stream().map(x -> x.getWorkTypeCode().v()).collect(Collectors.toList());
		Optional<String> selectedWorkTypeCD = appAbsenceStartInfoOutput.getSelectedWorkTypeCD();
		if(!selectedWorkTypeCD.isPresent() || !workTypeCDLst.contains(selectedWorkTypeCD.get())) {
			if(appAbsenceStartInfoOutput.getHdAppSet().getDisplayUnselect() == UseAtr.USE) {
				appAbsenceStartInfoOutput.setSelectedWorkTypeCD(Optional.empty());
			} else {
				appAbsenceStartInfoOutput.setSelectedWorkTypeCD(workTypes.stream().findFirst().map(x -> x.getWorkTypeCode().v()));
			}
		}
		// 勤務種類変更時処理
		appAbsenceStartInfoOutput = this.workTypeChangeProcess(
				companyID, 
				appAbsenceStartInfoOutput, 
				holidayType, 
				appAbsenceStartInfoOutput.getSelectedWorkTypeCD());
		// 返ってきた「休暇申請起動時の表示情報」を返す
		return appAbsenceStartInfoOutput;
	}

	@Override
	public AppAbsenceStartInfoOutput allHalfDayChangeProcess(String companyID,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, boolean displayHalfDayValue, Integer alldayHalfDay,
			Optional<Integer> holidayType) {
		// INPUT．「休暇種類」を確認する
		if(holidayType.isPresent()) {
			// 休暇種類変更時処理
			return this.holidayTypeChangeProcess(companyID, appAbsenceStartInfoOutput, displayHalfDayValue, alldayHalfDay, holidayType.get());
		}
		// 返ってきた「休暇申請起動時の表示情報」を返す
		return appAbsenceStartInfoOutput;
	}
	
}
