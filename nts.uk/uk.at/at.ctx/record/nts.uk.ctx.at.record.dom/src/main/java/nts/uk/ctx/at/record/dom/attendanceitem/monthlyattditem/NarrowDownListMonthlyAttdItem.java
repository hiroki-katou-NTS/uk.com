package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.workrecord.goout.OutManage;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;

/**
 * DS : 利用できる月次の勤怠項目一覧を絞り込む
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤怠項目.月次の勤怠項目.利用できる月次の勤怠項目一覧を絞り込む
 * @author tutk
 *
 */
public class NarrowDownListMonthlyAttdItem {
	/**
	 * 	[1] 絞り込む
	 */
	public static List<Integer> get(Require require, String companyId, List<Integer> listAttdId) {
		List<Integer> listAttdIdNotAailable = new ArrayList<>();
		listAttdIdNotAailable.addAll(getOvertimeItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getHolidayItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getUnusableDivergenceItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getPaidItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getAnyItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getExtraItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getUnusableVariantLabor(require, companyId));
		listAttdIdNotAailable.addAll(getFlexWorkItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getTotalTimesItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getOutsideOTSettingItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getUnusableAnnualLeaveItems(require, companyId));
		listAttdIdNotAailable.addAll(getRetentionYearlySettingItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getUnavailableSubstituteHolidayItem(require, companyId));
		listAttdIdNotAailable.addAll(getUnavailableHolidayItem(require, companyId));
		listAttdIdNotAailable.addAll(getSpecialHolidayItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getAbsenceFrameItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getSpecialHolidayFrameItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getNursingItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getPublicHolidaySettingItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getMultipleWorkItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getTemporaryWorkItemNotAvailable(require, companyId));
		List<Integer> result = new ArrayList<>();
		result = listAttdId.stream().filter(c -> !listAttdIdNotAailable.contains(c)).collect(Collectors.toList());
		return result;
	}
	
	/**
	 * [prv-1] 利用できない残業項目を取得する
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getOvertimeItemNotAvailable(Require require, String companyId) {
		List<OvertimeWorkFrame> listOvertimeWorkFrame = require.getAllOvertimeWorkFrame(companyId);
		List<Integer> result = new ArrayList<>();
		listOvertimeWorkFrame.stream().forEach(domain -> {
			result.addAll(domain.getMonthlyAttendanceIdNotAvailable());
		});
		return result;
	}
	
	/**
	 * [prv-2] 利用できない休出項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getHolidayItemNotAvailable(Require require, String companyId) {
		List<WorkdayoffFrame> listWorkdayoffFrame = require.getAllWorkdayoffFrame(companyId);
		List<Integer> result = new ArrayList<>();
		listWorkdayoffFrame.stream().forEach(domain -> {
			result.addAll(domain.getMonthlyAttendanceIdNotAvailable());
		});
		return result;
	}
	
	/**
	 * [prv-3] 利用できない乖離項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getUnusableDivergenceItemNotAvailable(Require require, String companyId) {
		List<Integer> result = new ArrayList<>();
		List<DivergenceTimeRoot> listDivergenceTimeRoot = require.getAllDivTime(companyId);
		listDivergenceTimeRoot.stream().forEach(domain -> {
			result.addAll(domain.getMonthlyAttendanceIdNotAvailable());
		});
		return result;
	}
	
	/**
	 * [prv-4] 利用できない加給項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getPaidItemNotAvailable(Require require, String companyId) {
		List<BonusPayTimeItem> listBonusPayTimeItem = require.getListBonusPayTimeItem(companyId);
		List<Integer> result = new ArrayList<>();
		listBonusPayTimeItem.stream().forEach(domain -> {
			result.addAll(domain.getMonthlyAttendanceIdNotAvailable());
		});
		return result;
	}
	
	/**
	 * [prv-6] 利用できない任意項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getAnyItemNotAvailable(Require require, String companyId) {
		List<OptionalItem> listOptionalItem = require.findAllOptionalItem(companyId);
		List<Integer> result = new ArrayList<>();
		listOptionalItem.stream().forEach(domain -> {
			result.addAll(domain.getMonthlyAttendanceIdNotAvailable());
		});
		return result;
	}
	
	/**
	 * [prv-7] 利用できない割増項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getExtraItemNotAvailable(Require require, String companyId) {
		List<PremiumItem> listPremiumItem = require.findPremiumItemByCompanyID(companyId);
		List<Integer> result = new ArrayList<>();
		listPremiumItem.stream().forEach(domain -> {
			result.addAll(domain.getMonthlyAttendanceIdNotAvailable());
		});
		return result;
	}
	
	/**
	 * [prv-8] 利用できない変形労働を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getUnusableVariantLabor(Require require, String companyId) {
		Optional<AggDeformedLaborSetting> aggDeformedLaborSetting = require.findAggDeformedLaborSettingByCid(companyId);
		if (aggDeformedLaborSetting.isPresent()) {
			return aggDeformedLaborSetting.get().getMonthlyAttendanceIdNotAvailable();
		}
		return new ArrayList<>();
	}
	
	/**
	 * [prv-9] 利用できないフレックス勤務項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getFlexWorkItemNotAvailable(Require require, String companyId) {
		Optional<FlexWorkSet> flexWorkSet = require.findFlexWorkSet(companyId);
		if (flexWorkSet.isPresent()) {
			return flexWorkSet.get().getMonthlyAttendanceIdNotAvailable();
		}
		return new ArrayList<>();
	}
	/**
	 * 	[prv-10] 利用できない回数集計項目を取得する
	 */
	private static List<Integer> getTotalTimesItemNotAvailable(Require require, String companyId) {
		List<TotalTimes> listTotalTimes = require.getAllTotalTimes(companyId);
		List<Integer> result = new ArrayList<>();
		listTotalTimes.stream().forEach(domain -> {
			result.addAll(domain.getMonthlyAttendanceIdNotAvailable());
		});
		return result;
	}
	
	/**
	 * 	[prv-11] 利用できない時間外超過項目を取得する
	 *
	 */
	private static List<Integer> getOutsideOTSettingItemNotAvailable(Require require, String companyId) {
		Optional<OutsideOTSetting> outsideOTSetting = require.reportById(companyId);
		if (outsideOTSetting.isPresent()) {
			return outsideOTSetting.get().getMonthlyAttendanceIdNotAvailable();
		}
		return new ArrayList<>();
	}
	
	/**
	 * [prv-12] 利用できない年休項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getUnusableAnnualLeaveItems(Require require, String companyId) {
		AnnualPaidLeaveSetting annualPaidLeaveSetting = require.findByCompanyId(companyId);
		if (annualPaidLeaveSetting != null) {
			return annualPaidLeaveSetting.getMonthlyAttendanceItemsNotAvailable();
		}
		return new ArrayList<>();
	}
	
	/**
	 * [prv-13] 利用できない積立年休項目を取得する
	 *
	 */
	private static List<Integer> getRetentionYearlySettingItemNotAvailable(Require require, String companyId) {
		Optional<RetentionYearlySetting> outsideOTSetting = require.findRetentionYearlySettingByCompanyId(companyId);
		if (outsideOTSetting.isPresent()) {
			return outsideOTSetting.get().getMonthlyAttendanceItems(require);
		}
		return new ArrayList<>();
	}
	
	/**
	 * [prv-14] 利用できない代休項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getUnavailableSubstituteHolidayItem(Require require, String companyId) {
		CompensatoryLeaveComSetting compensatoryLeaveComSetting = require.findCompensatoryLeaveComSetting(companyId);
		if (compensatoryLeaveComSetting != null) {
			return compensatoryLeaveComSetting.getMonthlyAttendanceItems();
		}
		return new ArrayList<>();
	}
	
	/**
	 * [prv-15] 利用できない振休項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getUnavailableHolidayItem(Require require, String companyId) {
		Optional<ComSubstVacation> comSubstVacation = require.findComSubstVacationById(companyId);
		if (comSubstVacation.isPresent()) {
			return comSubstVacation.get().getMonthlyAttendanceItems();
		}
		return new ArrayList<>();
	}
	
	/**
	 * [prv-16] 利用できない特別休暇項目を取得する
	 *
	 */
	private static List<Integer> getSpecialHolidayItemNotAvailable(Require require, String companyId) {
		List<SpecialHoliday> listSpecialHoliday = require.findSpecialHolidayByCompanyId(companyId);
		List<Integer> result = new ArrayList<>();
		listSpecialHoliday.stream().forEach(domain -> {
			result.addAll(domain.getMonthlyAttendanceItems(require));
		});
		
		List<SpecialHolidayCode> listCodeNo = new ArrayList<>();
		for(int i = 1;i<=20;i++) {
			listCodeNo.add(new SpecialHolidayCode(i));
		}

		List<Integer> listNoSpecialHoliday = listSpecialHoliday.stream().map(c -> c.getSpecialHolidayCode().v())
				.collect(Collectors.toList());

		//$設定なしリスト = $コード一覧：except $特別休暇コードリスト	
		listCodeNo = listCodeNo.stream()
				.filter(x -> !listNoSpecialHoliday.stream().filter(lt -> lt == x.v()).findFirst().isPresent())
				.collect(Collectors.toList());
		
		result.addAll(SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(listCodeNo));
		return result;
	}
	
	/**
	 * 	[prv-17] 利用できない欠勤項目を取得する
	 *
	 */
	private static List<Integer> getAbsenceFrameItemNotAvailable(Require require, String companyId) {
		List<AbsenceFrame> listAbsenceFrame = require.findAllAbsenceFrame(companyId);
		List<Integer> result = new ArrayList<>();
		listAbsenceFrame.stream().forEach(domain -> {
			result.addAll(domain.getMonthAttdItems());
		});
		return result;
	}
	
	/**
	 * [prv-18] 利用できない特別休暇枠項目を取得する
	 *
	 */
	private static List<Integer> getSpecialHolidayFrameItemNotAvailable(Require require, String companyId) {
		List<SpecialHolidayFrame> listSpecialHolidayFrame = require.findAllSpecialHolidayFrame(companyId);
		List<Integer> result = new ArrayList<>();
		listSpecialHolidayFrame.stream().forEach(domain -> {
			result.addAll(domain.getMonthlyAttendanceItems());
		});
		return result;
	}
	
	/**
	 * [prv-19] 利用できない介護看護項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getNursingItemNotAvailable(Require require, String companyId) {
		List<NursingLeaveSetting> listNursingLeaveSetting = require.findNursingLeaveSetting(companyId);
		List<Integer> result = new ArrayList<>();
		listNursingLeaveSetting.stream().forEach(domain -> {
			result.addAll(domain.getMonthlyAttendanceItems());
		});
		return result;
	}
	
	/**
	 * 	[prv-20] 利用できない公休項目を取得する
	 *
	 */
	private static List<Integer> getPublicHolidaySettingItemNotAvailable(Require require, String companyId) {
		Optional<PublicHolidaySetting> publicHolidaySetting = require.getPublicHolidaySetting(companyId);
		if (publicHolidaySetting.isPresent()) {
			return publicHolidaySetting.get().getMonthlyAttendanceItems();
		}
		return new ArrayList<>();
	}
	
	/**
	 * [prv-21] 利用できない複数回勤務項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getMultipleWorkItemNotAvailable(Require require, String companyId) {
		Optional<WorkManagementMultiple> workManagementMultiple = require.findWorkManagementMultiple(companyId);
		if (workManagementMultiple.isPresent()) {
			return workManagementMultiple.get().getMonthlyAttendanceItems(require);
		}
		return new ArrayList<>();
	}
	
	/**
	 * [prv-22] 利用できない臨時勤務項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getTemporaryWorkItemNotAvailable(Require require, String companyId) {
		Optional<TemporaryWorkUseManage> temporaryWorkUseManage = require.findTemporaryWorkUseManage(companyId);
		if (temporaryWorkUseManage.isPresent()) {
			return temporaryWorkUseManage.get().getMonthlyAttendanceItems(require);
		}
		return new ArrayList<>();
	}

	public static interface Require extends WorkManagementMultiple.Require, TemporaryWorkUseManage.Require,
			RetentionYearlySetting.Require, SpecialHoliday.Require {
		/**
		 * [R-1] 残業枠を取得する OvertimeWorkFrameRepository
		 */
		List<OvertimeWorkFrame> getAllOvertimeWorkFrame(String companyId);
		
		/**
		 * [R-2] 休出枠を取得する WorkdayoffFrameRepository
		 */
		List<WorkdayoffFrame> getAllWorkdayoffFrame(String companyId);
		
		/**
		 * [R-3] 乖離時間を取得する DivergenceTimeRepository
		 */
		List<DivergenceTimeRoot> getAllDivTime(String companyId);
		
		/**
		 * [R-4] 加給時間項目を取得する [R-5] 特定加給時間項目を取得する BPTimeItemRepository
		 */
		List<BonusPayTimeItem> getListBonusPayTimeItem(String companyId);
		
		/**
		 * [R-7] 任意項目を取得する OptionalItemRepository
		 */
		List<OptionalItem> findAllOptionalItem(String companyId);
		
		/**
		 * [R-8] 割増時間項目を取得する PremiumItemRepository
		 */
		List<PremiumItem> findPremiumItemByCompanyID(String companyID);
		
		/**
		 * [R-9] 外出管理を取得する OutManageRepository
		 */
		Optional<OutManage> findOutManageByID(String companyID);
		
		/**
		 * [R-10] 変形労働の集計設定を取得する AggDeformedLaborSettingRepository
		 */
		Optional<AggDeformedLaborSetting> findAggDeformedLaborSettingByCid(String companyId);
		
		/**
		 * [R-11] フレックス勤務の設定を取得する FlexWorkMntSetRepository
		 */
		Optional<FlexWorkSet> findFlexWorkSet(String companyId);

		/**
		 * [R-12] 年休設定を取得する AnnualPaidLeaveSettingRepository
		 */
		AnnualPaidLeaveSetting findByCompanyId(String companyId);
		
		/**
		 * [R-13] 積立年休設定を取得する  RetentionYearlySettingRepository.findByCompanyId
		 */
		Optional<RetentionYearlySetting> findRetentionYearlySettingByCompanyId(String companyId);
		
		/**
		 * [R-14] 代休管理設定を取得する CompensLeaveComSetRepository
		 */
		CompensatoryLeaveComSetting findCompensatoryLeaveComSetting(String companyId);
		
		/**
		 * [R-15] 振休管理設定を取得する ComSubstVacationRepository
		 */
		Optional<ComSubstVacation> findComSubstVacationById(String companyId);
		
		/**
		 * [R-16] 特別休暇を取得する  SpecialHolidayRepository.findByCompanyId
		 */
		List<SpecialHoliday> findSpecialHolidayByCompanyId(String companyId);
		
		/**
		 * [R-17] 欠勤枠を取得する  AbsenceFrameRepository.findAll
		 */
		List<AbsenceFrame> findAllAbsenceFrame(String companyId);
		
		/**
		 * [R-18] 特別休暇枠を取得する  SpecialHolidayFrameRepository.findAll
		 */
		List<SpecialHolidayFrame> findAllSpecialHolidayFrame(String companyId);
		
		/**
		 * [R-19] 介護看護休暇設定を取得する NursingLeaveSettingRepository
		 */
		List<NursingLeaveSetting> findNursingLeaveSetting(String companyId);
		
		/**
		 * [R-20] 公休設定を取得する PublicHolidaySettingRepository.get
		 */
		Optional<PublicHolidaySetting> getPublicHolidaySetting(String companyId);
		
		/**
		 * [R-21] 回数集計を取得する TotalTimesRepository
		 */
		List<TotalTimes> getAllTotalTimes(String companyId);
		
		/**
		 * [R-22] 時間外超過設定を取得する OutsideOTSettingRepository
		 */
		Optional<OutsideOTSetting> reportById(String companyId);
		
		/**
		 * [R-23] 複数回勤務管理を取得する WorkManagementMultipleRepository
		 */
		Optional<WorkManagementMultiple> findWorkManagementMultiple(String companyID);

		/**
		 * [R-24] 臨時勤務利用管理を取得する TemporaryWorkUseManageRepository
		 */
		Optional<TemporaryWorkUseManage> findTemporaryWorkUseManage(String companyId);
		
		
	}
}
