package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

/*import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;*/
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.SubstVacationFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto.SubstVacationSettingDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecGenerationDigestionHis;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentManaQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AsbRemainTotalInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.NumberCompensatoryLeavePeriodProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffManagementQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffOutputHisData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcessCommon;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.LeaveSetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmploymentSystemFinder {	
	@Inject
	private BreakDayOffManagementQuery breakDayOffManagementQuery;
	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;
	@Inject
	private AbsenceReruitmentManaQuery absenceReruitmentManaQuery;
//	@Inject
//	private CompensLeaveEmSetRepository compensLeaveEmSetRepository;
//	@Inject
//	private EmpSubstVacationRepository empSubrepo;
//	@Inject
//	private ComSubstVacationRepository comSubrepo;
	
	@Inject
	private AbsenceTenProcessCommon absenceTenProcessCommon;

	@Inject
	private SubstVacationFinder substVacationFinder;
	
	@Inject
	private NumberCompensatoryLeavePeriodProcess numberCompensatoryLeavePeriodProcess;

	@Inject
	private NumberRemainVacationLeaveRangeProcess numberRemainVacationLeaveRangeProcess;
	
	@Inject
	private RemainNumberTempRequireService requireService;
	
	/** 
	 * KDL005
	 * アルゴリズム「代休確認ダイア起動」を実行する
	 * 
	 * @param employeeIds
	 * @param baseDate
	 * @return
	 */
	public List<EmployeeBasicInfoDto> getEmployeeData(List<String> employeeIds, String baseDate) {
		String companyId = AppContexts.user().companyId();
		
		// 社員情報リストを取得する
		List<EmployeeBasicInfoDto> data = employeeRequestAdapter.getPerEmpBasicInfo(companyId, employeeIds)
				.stream().map(c -> EmployeeBasicInfoDto.fromDomain(c)).collect(Collectors.toList());
		
		if(data.isEmpty()) {
			return Collections.emptyList();
		}
		
		return data;
	}
	
	/**
	 * KDL005
	 * アルゴリズム「代休確認ダイア詳細取得」を実行する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public AcquisitionNumberRestDayDto getDetailsConfirm(String employeeId, String baseDate) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		String companyId = AppContexts.user().companyId();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		
		AcquisitionNumberRestDayDto detailsdDto = new AcquisitionNumberRestDayDto();
		
		// 基準日（指定がない場合はシステム日付）
		if (baseDate.isEmpty()) {
			baseDate = dtf.format(localDate);
		} else {
			baseDate = GeneralDate.fromString(baseDate, "yyyyMMdd").toString();
		}
		GeneralDate inputDate = GeneralDate.fromString(baseDate, "yyyy/MM/dd");
		// #110215 10-2.代休の設定を取得する
		SubstitutionHolidayOutput subHd = this.absenceTenProcessCommon.getSettingForSubstituteHoliday(companyId,
				employeeId, inputDate);

		if (!subHd.isSubstitutionFlg() && !subHd.isTimeOfPeriodFlg()) {
			// #110215 取得した管理区分を渡す
			detailsdDto.setIsManagementSection(false);
			detailsdDto.setListPegManagement(new ArrayList<>());
			detailsdDto.setListRemainNumberDetail(new ArrayList<>());
			return detailsdDto;
		}
		
		// 	アルゴリズム「休出代休発生消化履歴の取得」を実行する
		Optional<BreakDayOffOutputHisData> data = this.breakDayOffManagementQuery.getBreakDayOffData(companyId, employeeId, inputDate);
		
		// #110215 アルゴリズム「社員に対応する締め期間を取得する」を実行する
		DatePeriod closingPeriod = ClosureService.findClosurePeriod(require, cacheCarrier, employeeId, inputDate);

		// #110215 期間内の休出代休残数を取得する
		BreakDayOffRemainMngRefactParam inputParam = new BreakDayOffRemainMngRefactParam(
				companyId, 
				employeeId,
				getDatePeroid(closingPeriod.start()), 
				false, 
				GeneralDate.today(), 
				false, 
				Collections.emptyList(),
				Optional.empty(), 
				Optional.empty(), 
				Collections.emptyList(), 
				Collections.emptyList(), 
				Optional.empty(),
				new FixedManagementDataMonth(Collections.emptyList(), Collections.emptyList()));
		SubstituteHolidayAggrResult substituteHolidayAggrResult = this.numberRemainVacationLeaveRangeProcess
				.getBreakDayOffMngInPeriod(inputParam);
		
		// #110215 残数詳細を作成
		List<RemainNumberDetailDto> listRemainNumberDetail = substituteHolidayAggrResult.getVacationDetails()
				.getLstAcctAbsenDetail().stream().map(item -> {
					RemainNumberDetailDto itemDto = new RemainNumberDetailDto();
					itemDto.setExpiredInCurrentMonth(false);
					if (item.getOccurrentClass().equals(OccurrenceDigClass.OCCURRENCE)) {
						UnbalanceVacation itemOccurrent = (UnbalanceVacation) item;
						// 	・逐次発生の休暇明細．発生消化区分　＝＝　発生　－＞発生日　＝　逐次発生の休暇明細．年月日
						itemDto.setOccurrenceDate(itemOccurrent.getDateOccur().getDayoffDate().orElse(null));
						// field ・発生数　＝　取得した逐次発生の休暇明細．発生数．日数
						itemDto.setOccurrenceNumber(itemOccurrent.getNumberOccurren().getDay().v());
						// field ・発生時間　＝　取得した逐次発生の休暇明細．発生数．時間
						Optional<AttendanceTime> oOccurrenceHour = itemOccurrent.getNumberOccurren().getTime();
						itemDto.setOccurrenceHour(oOccurrenceHour.map(o -> o.v()).orElse(null));						
						// field ・期限日　＝　取得した逐次発生の休暇明細．休暇発生明細．期限日
						itemDto.setExpirationDate(itemOccurrent.getDeadline());
						// condition 取得した期間．開始日＜＝期限日＜＝取得した期間．終了日　－＞・当月で期限切れ　＝　True　ELSE　－＞・当月で期限切れ　＝　False
						itemDto.setExpiredInCurrentMonth(closingPeriod.contains(itemOccurrent.getDeadline()));
					} else if (item.getOccurrentClass().equals(OccurrenceDigClass.DIGESTION)) {
						// field ・逐次発生の休暇明細．発生消化区分　＝＝　消化　－＞消化日　＝　逐次発生の休暇明細．年月日
						itemDto.setDigestionDate(item.getDateOccur().getDayoffDate().orElse(null));
						// field ・消化数　＝　取得した逐次発生の休暇明細．発生数．日数
						itemDto.setDigestionNumber(item.getNumberOccurren().getDay().v());
						// field ・消化時間　＝　取得した逐次発生の休暇明細．発生数．時間
						Optional<AttendanceTime> oDigestionHour = item.getNumberOccurren().getTime();
						itemDto.setDigestionHour(oDigestionHour.map(o -> o.v()).orElse(null));
					}
					// field 管理データ状態区分　＝　取得した逐次発生の休暇明細．状態
					itemDto.setManagementDataStatus(item.getDataAtr().value);
					return itemDto;
				}).collect(Collectors.toList());
		detailsdDto.setListRemainNumberDetail(listRemainNumberDetail);

		// #110215 紐付け情報を生成する ()
		List<PegManagementDto> listPegManagement = substituteHolidayAggrResult.getLstSeqVacation().stream()
				.map(item -> {
					PegManagementDto itemDto = new PegManagementDto();
					itemDto.setUsageDate(item.getDateOfUse());
					itemDto.setUsageDay(item.getDayNumberUsed().v());
					itemDto.setUsageHour(0);
					itemDto.setOccurrenceDate(item.getOutbreakDay());
					return itemDto;
				}).collect(Collectors.toList());
		detailsdDto.setListPegManagement(listPegManagement);

		// #110215 取得内容を画面に反映させる
		if (data.isPresent()) {
			// A8_1_2 繰越日数
			detailsdDto.setCarryForwardDay(substituteHolidayAggrResult.getCarryoverDay().v());
			// A8_2_2 実績発生日数
			detailsdDto.setOccurrenceDay(substituteHolidayAggrResult.getOccurrenceDay().v());
			// A8_2_3 予定発生日数
			detailsdDto.setScheduleOccurrencedDay(data.get().getTotalInfor().getScheOccurrenceDays());
			// A8_3_2 使用日数
			detailsdDto.setUsageDay(substituteHolidayAggrResult.getDayUse().v());
			// A8_3_3 予定使用日数
			detailsdDto.setScheduledUsageDay(data.get().getTotalInfor().getScheUseDays());
			// A8_4_2 残数
			detailsdDto.setRemainingDay(substituteHolidayAggrResult.getRemainDay().v());
			// A8_4_3 予定残数
			detailsdDto.setScheduledRemainingDay(
					detailsdDto.getScheduleOccurrencedDay() - detailsdDto.getScheduledUsageDay());
			// 	残数詳細
			detailsdDto.setListRemainNumberDetail(listRemainNumberDetail);
			// 	使用期限
			detailsdDto.setExpiredDay(subHd.getExpirationOfsubstiHoliday());
			// 	使用区分
			if (subHd.isSubstitutionFlg() || subHd.isTimeOfPeriodFlg()) {
				detailsdDto.setIsManagementSection(true);
			} else {
				detailsdDto.setIsManagementSection(false);
			}
			// 	繰越時間
			detailsdDto.setCarryForwardHour(substituteHolidayAggrResult.getCarryoverTime().v());
			// 	発生時間
			detailsdDto.setOccurrenceHour(substituteHolidayAggrResult.getOccurrenceTime().v());
			// 	予定発生時間
			detailsdDto.setScheduleOccurrencedHour(data.get().getTotalInfor().getScheHours());
			// 	使用時間
			detailsdDto.setUsageHour(substituteHolidayAggrResult.getTimeUse().v());
			// 	予定使用時間
			detailsdDto.setScheduledUsageHour(data.get().getTotalInfor().getScheUseHours());
			// 	残数時間
			detailsdDto.setRemainingHour(substituteHolidayAggrResult.getRemainTime().v());
			//	 予定残数時間
			detailsdDto.setScheduledRemainingHour(
					detailsdDto.getScheduleOccurrencedHour() - detailsdDto.getScheduledUsageHour());
		}
		
		return detailsdDto;
	}
	
	private DatePeriod getDatePeroid(GeneralDate startDate) {
		return new DatePeriod(startDate, startDate.addYears(1).addDays(-1));
	}

//	private DeadlineDetails getDeadlineDetails(String companyId, Optional<EmploymentHistoryImported> empImpOpt) {
//		DeadlineDetails  result  = null;
//		
//		if (empImpOpt.isPresent()) {
//			CompensatoryLeaveEmSetting emSet = this.compensLeaveEmSetRepository.find(companyId,
//					empImpOpt.get().getEmploymentCode());
//
//			if (emSet == null) {
//				CompensatoryLeaveComSetting comSet = this.compensLeaveComSetRepository.find(companyId);
//				if(comSet == null){
//					throw new BusinessException("代休管理設定 && 雇用の代休管理設定 = null");
//				}
//				result = new DeadlineDetails(comSet.getIsManaged().value, comSet.getCompensatoryAcquisitionUse().getExpirationTime().value );
//			}else{
//				result = new DeadlineDetails(emSet.getIsManaged().value, emSet.getCompensatoryAcquisitionUse().getExpirationTime().value);
//			}
//		}
//		
//		return result;
//		
//	}

	/**
	 * KDL009
	 * アルゴリズム「振休確認ダイアログ開始」を実行する
	 * 
	 * @param employeeIds
	 * @param baseDate
	 * @return
	 */
	public List<EmployeeBasicInfoDto> getEmployee(List<String> employeeIds, String baseDate) {
		String companyId = AppContexts.user().companyId();
		
		// 対象社員の件数をチェックする
		if(employeeIds.size() == 0) {
			// エラーメッセージ(#Msg_918#)を表示する
			throw new BusinessException("Msg_918");
		}
		
		// 社員情報リストを取得する
		List<EmployeeBasicInfoDto> data = employeeRequestAdapter.getPerEmpBasicInfo(companyId, employeeIds)
				.stream().map(c -> EmployeeBasicInfoDto.fromDomain(c)).collect(Collectors.toList());
		
		if(data.isEmpty()) {
			return Collections.emptyList();
		}
		
		return data;
	}

	/**
	 * KDL009
	 * アルゴリズム「振休残数情報の取得」を実行する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public AcquisitionNumberRestDayDto getAcquisitionNumberRestDays(String employeeId, String baseDate) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		String companyId = AppContexts.user().companyId();
		GeneralDate inputDate = GeneralDate.fromString(baseDate, "yyyyMMdd");
		AcquisitionNumberRestDayDto result = new AcquisitionNumberRestDayDto();
		
		// Step 10-3.振休の設定を取得する
		LeaveSetOutput leaveSet = this.absenceTenProcessCommon.getSetForLeave(companyId, employeeId, inputDate);
		result.setIsManagementSection(leaveSet.isSubManageFlag());
		
		// If 取得した振休管理区分　＝＝　False
		if (!leaveSet.isSubManageFlag()) {
			// 「確認残数情報」を返す
			result.setListRemainNumberDetail(Collections.emptyList());
			result.setListPegManagement(Collections.emptyList());
			return result;
		}

		// Step 社員に対応する締め期間を取得する		
		DatePeriod closingPeriod = ClosureService.findClosurePeriod(require, cacheCarrier, employeeId, inputDate);
		
		// Step アルゴリズム「期間内の振出振休残数を取得する」を実行する
		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(
				companyId,
				employeeId,
				getDatePeroid(closingPeriod.start()),
				GeneralDate.today(),
				false,
				false, 
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				new FixedManagementDataMonth(Collections.emptyList(), Collections.emptyList()));
		CompenLeaveAggrResult compenLeaveAggrResult = this.numberCompensatoryLeavePeriodProcess.process(inputParam);

		// Step 紐付け情報を生成する ()
		List<PegManagementDto> listPegManagement = compenLeaveAggrResult.getLstSeqVacation().stream()
				.map(item -> {
					PegManagementDto itemDto = new PegManagementDto();
					itemDto.setUsageDate(item.getDateOfUse());
					itemDto.setUsageDay(item.getDayNumberUsed().v());
					itemDto.setUsageHour(0);
					itemDto.setOccurrenceDate(item.getOutbreakDay());
					return itemDto;
				})
				.collect(Collectors.toList());
		result.setListPegManagement(listPegManagement);
		
		// Step 「残数詳細」を作成
		List<RemainNumberDetailDto> listRemainNumberDetail = compenLeaveAggrResult.getVacationDetails().getLstAcctAbsenDetail().stream()
				.map(item -> {
					RemainNumberDetailDto itemDto = new RemainNumberDetailDto();
					itemDto.setExpiredInCurrentMonth(false);
					if (item.getOccurrentClass().equals(OccurrenceDigClass.OCCURRENCE)) {
						UnbalanceCompensation itemOccurrent = (UnbalanceCompensation) item;
						// 	・逐次発生の休暇明細．発生消化区分　＝＝　発生　－＞発生日　＝　逐次発生の休暇明細．年月日
						itemDto.setOccurrenceDate(itemOccurrent.getDateOccur().getDayoffDate().orElse(null));
						// field ・発生数　＝　取得した逐次発生の休暇明細．発生数．日数
						itemDto.setOccurrenceNumber(itemOccurrent.getNumberOccurren().getDay().v());
						// field ・発生時間　＝　取得した逐次発生の休暇明細．発生数．時間
						Optional<AttendanceTime> oOccurrenceHour = itemOccurrent.getNumberOccurren().getTime();
						itemDto.setOccurrenceHour(oOccurrenceHour.map(o -> o.v()).orElse(null));						
						// field ・期限日　＝　取得した逐次発生の休暇明細．休暇発生明細．期限日
						itemDto.setExpirationDate(itemOccurrent.getDeadline());
						// condition 取得した期間．開始日＜＝期限日＜＝取得した期間．終了日　－＞・当月で期限切れ　＝　True　ELSE　－＞・当月で期限切れ　＝　False
						itemDto.setExpiredInCurrentMonth(closingPeriod.contains(itemOccurrent.getDeadline()));
					} else if (item.getOccurrentClass().equals(OccurrenceDigClass.DIGESTION)) {
						// field ・逐次発生の休暇明細．発生消化区分　＝＝　消化　－＞消化日　＝　逐次発生の休暇明細．年月日
						itemDto.setDigestionDate(item.getDateOccur().getDayoffDate().orElse(null));
						// field ・消化数　＝　取得した逐次発生の休暇明細．発生数．日数
						itemDto.setDigestionNumber(item.getNumberOccurren().getDay().v());
						// field ・消化時間　＝　取得した逐次発生の休暇明細．発生数．時間
						Optional<AttendanceTime> oDigestionHour = item.getNumberOccurren().getTime();
						itemDto.setDigestionHour(oDigestionHour.map(o -> o.v()).orElse(null));
					}
					// field 管理データ状態区分　＝　取得した逐次発生の休暇明細．状態
					itemDto.setManagementDataStatus(item.getDataAtr().value);
					return itemDto;
				})
				.collect(Collectors.toList());
		result.setListRemainNumberDetail(listRemainNumberDetail);
		
		// Step 振出振休発生消化履歴の取得
		Optional<AbsRecGenerationDigestionHis> data = this.absenceReruitmentManaQuery.generationDigestionHis(companyId, employeeId, inputDate);
		if (data.isPresent()) {
			AsbRemainTotalInfor absRemainInfor = data.get().getAbsRemainInfor();
			// A8_1_2 繰越日数
			result.setCarryForwardDay(compenLeaveAggrResult.getCarryoverDay().v());
			// A8_2_2 発生日数
			result.setOccurrenceDay(compenLeaveAggrResult.getOccurrenceDay().v());
			// A8_2_3 予定発生日数
			result.setScheduleOccurrencedDay(absRemainInfor.getScheOccurrenceDays());
			// A8_3_2 使用日数
			result.setUsageDay(compenLeaveAggrResult.getDayUse().v());
			// A8_3_3 予定使用日数
			result.setScheduledUsageDay(absRemainInfor.getScheUseDays());
			// A8_4_2 残数
			result.setRemainingDay(compenLeaveAggrResult.getRemainDay().v());
			// A8_4_3 予定残数
			result.setScheduledRemainingDay(absRemainInfor.getScheOccurrenceDays() - absRemainInfor.getScheUseDays());
		}
		
		//fix bug #115862
		SubstVacationSettingDto subHd = this.substVacationFinder.findComSetting(companyId);
		result.setExpiredDay(subHd.getExpirationDate());
		
		return result;
	}

	@Data
	@AllArgsConstructor
	class DeadlineDetails {
		// 管理区分
		private Integer isManaged;
		// 使用期限
		private Integer expirationTime;
	}
}
