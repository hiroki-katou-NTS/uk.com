package nts.uk.ctx.at.record.dom.attendanceitem.dailyattditem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManage;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;

/**
 * DS : 利用できる日次の勤怠項目一覧を絞り込む
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤怠項目.日次の勤怠項目.利用できる日次の勤怠項目一覧を絞り込む
 * 
 * @author tutk
 *
 */
public class NarrowDownListDailyAttdItem {
	public static List<Integer> get(Require require, String companyId, List<Integer> listAttdId) {
		List<Integer> listAttdIdNotAailable = new ArrayList<>();
		listAttdIdNotAailable.addAll(getSupportWorkItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getOvertimeItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getHolidayItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getUnusableDivergenceItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getPaidItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getAnyItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getExtraItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getOutOfServiceItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getUnusableVariantLabor(require, companyId));
		listAttdIdNotAailable.addAll(getFlexWorkItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getUnusableAnnualLeaveItems(require, companyId));
		listAttdIdNotAailable.addAll(getUnavailableSubstituteHolidayItem(require, companyId));
		listAttdIdNotAailable.addAll(getUnavailableHolidayItem(require, companyId));
		listAttdIdNotAailable.addAll(getSpecialVacationItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getNursingItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getMultipleWorkItemNotAvailable(require, companyId));
		listAttdIdNotAailable.addAll(getTemporaryWorkItemNotAvailable(require, companyId));
		List<Integer> result = new ArrayList<>();
		result = listAttdId.stream().filter(c -> !listAttdIdNotAailable.contains(c)).collect(Collectors.toList());
		return result;
	}

	/**
	 * [prv-1] 利用できない応援作業項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getSupportWorkItemNotAvailable(Require require, String companyId) {
		Optional<SupportOperationSetting> supportOperationSetting = require.getSupportOperationSetting(companyId);
		if (supportOperationSetting.isPresent()) {
			return supportOperationSetting.get().getDaiLyAttendanceIdNotAvailable(require);
		}
		Optional<TaskOperationSetting> taskOperationSetting = require.getTasksOperationSetting(companyId);
		if (taskOperationSetting.isPresent()) {
			return taskOperationSetting.get().getDaiLyAttendanceIdNotAvailable(require);
		}
		return new ArrayList<>();
	}

	/**
	 * [prv-2] 利用できない残業項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getOvertimeItemNotAvailable(Require require, String companyId) {
		List<OvertimeWorkFrame> listOvertimeWorkFrame = require.getAllOvertimeWorkFrame(companyId);
		List<Integer> result = new ArrayList<>();
		listOvertimeWorkFrame.stream().forEach(domain -> {
			result.addAll(domain.getDailyAttendanceIdNotAvailable());
		});
		return result;
	}

	/**
	 * [prv-3] 利用できない休出項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getHolidayItemNotAvailable(Require require, String companyId) {
		List<WorkdayoffFrame> listWorkdayoffFrame = require.getAllWorkdayoffFrame(companyId);
		List<Integer> result = new ArrayList<>();
		listWorkdayoffFrame.stream().forEach(domain -> {
			result.addAll(domain.getDailyAttendanceIdNotAvailable());
		});
		return result;
	}

	/**
	 * [prv-4] 利用できない乖離項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getUnusableDivergenceItemNotAvailable(Require require, String companyId) {
		List<Integer> result = new ArrayList<>();
		List<DivergenceTimeRoot> listDivergenceTimeRoot = require.getAllDivTime(companyId);
		listDivergenceTimeRoot.stream().forEach(domain -> {
			result.addAll(domain.getDailyAttendanceIdNotAvailable());
		});

		List<DivergenceReasonInputMethod> listDivergenceReasonInputMethod = require
				.getDivergenceReasonInputMethod(companyId);

		for (DivergenceReasonInputMethod divMethod : listDivergenceReasonInputMethod) {
			for (DivergenceTimeRoot divergenceTimeRoot : listDivergenceTimeRoot) {
				if (divMethod.getDivergenceTimeNo() == divergenceTimeRoot.getDivergenceTimeNo()) {
					result.addAll(divMethod.getDaiLyAttendanceIdNotAvailable(
							UseClassification.valueOf(divergenceTimeRoot.getDivTimeUseSet().value)));
					break;
				}
			}
		}

		return result;
	}

	/**
	 * [prv-5] 利用できない加給項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getPaidItemNotAvailable(Require require, String companyId) {
		List<BonusPayTimeItem> listBonusPayTimeItem = require.getListBonusPayTimeItem(companyId);
		List<Integer> result = new ArrayList<>();
		listBonusPayTimeItem.stream().forEach(domain -> {
			result.addAll(domain.getDaiLyAttendanceIdNotAvailable());
		});
		return result;
	}

	/**
	 * [prv-7] 利用できない任意項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getAnyItemNotAvailable(Require require, String companyId) {
		List<OptionalItem> listOptionalItem = require.findAllOptionalItem(companyId);
		List<Integer> result = new ArrayList<>();
		listOptionalItem.stream().forEach(domain -> {
			result.addAll(domain.getDailyAttendanceIdNotAvailable());
		});
		return result;
	}

	/**
	 * [prv-8] 利用できない割増項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getExtraItemNotAvailable(Require require, String companyId) {
		List<PremiumItem> listPremiumItem = require.findPremiumItemByCompanyID(companyId);
		List<Integer> result = new ArrayList<>();
		listPremiumItem.stream().forEach(domain -> {
			result.addAll(domain.getDailyAttendanceIdNotAvailable());
		});
		return result;
	}

	/**
	 * [prv-9] 利用できない外出項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getOutOfServiceItemNotAvailable(Require require, String companyId) {
		Optional<OutManage> outManage = require.findOutManageByID(companyId);
		if (outManage.isPresent()) {
			return outManage.get().getDaiLyAttendanceIDNotAvailable();
		}
		return new ArrayList<>();
	}

	/**
	 * [prv-10] 利用できない変形労働を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getUnusableVariantLabor(Require require, String companyId) {
		Optional<AggDeformedLaborSetting> aggDeformedLaborSetting = require.findAggDeformedLaborSettingByCid(companyId);
		if (aggDeformedLaborSetting.isPresent()) {
			return aggDeformedLaborSetting.get().getDaiLyAttendanceIdNotAvailable();
		}
		return new ArrayList<>();
	}

	/**
	 * [prv-11] 利用できないフレックス勤務項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getFlexWorkItemNotAvailable(Require require, String companyId) {
		Optional<FlexWorkSet> flexWorkSet = require.findFlexWorkSet(companyId);
		if (flexWorkSet.isPresent()) {
			return flexWorkSet.get().getDaiLyAttendanceIdNotAvailable();
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
			return annualPaidLeaveSetting.getDailyAttendanceItemsNotAvailable();
		}
		return new ArrayList<>();
	}

	/**
	 * [prv-13] 利用できない代休項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getUnavailableSubstituteHolidayItem(Require require, String companyId) {
		CompensatoryLeaveComSetting compensatoryLeaveComSetting = require.findCompensatoryLeaveComSetting(companyId);
		if (compensatoryLeaveComSetting != null) {
			return compensatoryLeaveComSetting.getDailyAttendanceItems();
		}
		return new ArrayList<>();
	}

	/**
	 * [prv-14] 利用できない振休項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getUnavailableHolidayItem(Require require, String companyId) {
		Optional<ComSubstVacation> comSubstVacation = require.findComSubstVacationById(companyId);
		if (comSubstVacation.isPresent()) {
			return comSubstVacation.get().getDailyAttendanceItems();
		}
		return new ArrayList<>();
	}

	/**
	 * [prv-15] 利用できない時間特別休暇項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getSpecialVacationItemNotAvailable(Require require, String companyId) {
		Optional<TimeSpecialLeaveManagementSetting> timeSpecialLeaveManagementSetting = require
				.findTimeSpecialLeaveManagementSetting(companyId);
		if (timeSpecialLeaveManagementSetting.isPresent()) {
			return timeSpecialLeaveManagementSetting.get().getDailyAttdItemsNotAvailable();
		}
		return new ArrayList<>();
	}

	/**
	 * [prv-16] 利用できない介護看護項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getNursingItemNotAvailable(Require require, String companyId) {
		List<NursingLeaveSetting> listNursingLeaveSetting = require.findNursingLeaveSetting(companyId);
		List<Integer> result = new ArrayList<>();
		listNursingLeaveSetting.stream().forEach(domain -> {
			result.addAll(domain.getDailyAttendanceItems());
		});
		return result;
	}

	/**
	 * [prv-17] 利用できない複数回勤務項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getMultipleWorkItemNotAvailable(Require require, String companyId) {
		Optional<WorkManagementMultiple> workManagementMultiple = require.findWorkManagementMultiple(companyId);
		if (workManagementMultiple.isPresent()) {
			return workManagementMultiple.get().getDailyAttendanceItems(require);
		}
		return new ArrayList<>();
	}

	/**
	 * [prv-18] 利用できない臨時勤務項目を取得する
	 * 
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static List<Integer> getTemporaryWorkItemNotAvailable(Require require, String companyId) {
		Optional<TemporaryWorkUseManage> temporaryWorkUseManage = require.findTemporaryWorkUseManage(companyId);
		if (temporaryWorkUseManage.isPresent()) {
			return temporaryWorkUseManage.get().getDailyAttendanceItems(require);
		}
		return new ArrayList<>();
	}

	public static interface Require extends SupportOperationSetting.Require, TaskOperationSetting.Require,
			WorkManagementMultiple.Require, TemporaryWorkUseManage.Require {

		/**
		 * require.乖離理由の入力方法を取得する(会社ID)
		 * DivergenceReasonInputMethodRepository
		 * 
		 * @param companyId
		 * @return
		 */
		List<DivergenceReasonInputMethod> getDivergenceReasonInputMethod(String companyId);

		/**
		 * [R-1] 作業運用設定を取得する TaskOperationSettingRepository
		 */
		Optional<TaskOperationSetting> getTasksOperationSetting(String companyId);

		/**
		 * [R-2] 応援の運用設定を取得する SupportOperationSettingRepository
		 */
		Optional<SupportOperationSetting> getSupportOperationSetting(String companyId);

		/**
		 * [R-3] 残業枠を取得する OvertimeWorkFrameRepository
		 */
		List<OvertimeWorkFrame> getAllOvertimeWorkFrame(String companyId);

		/**
		 * [R-4] 休出枠を取得する WorkdayoffFrameRepository
		 */
		List<WorkdayoffFrame> getAllWorkdayoffFrame(String companyId);

		/**
		 * [R-5] 乖離時間を取得する DivergenceTimeRepository
		 */
		List<DivergenceTimeRoot> getAllDivTime(String companyId);

		/**
		 * [R-6] 加給時間項目を取得する [R-7] 特定加給時間項目を取得する BPTimeItemRepository
		 */
		List<BonusPayTimeItem> getListBonusPayTimeItem(String companyId);

		/**
		 * [R-8] 任意項目を取得する OptionalItemRepository
		 */
		List<OptionalItem> findAllOptionalItem(String companyId);

		/**
		 * [R-9] 割増時間項目を取得する PremiumItemRepository
		 */
		List<PremiumItem> findPremiumItemByCompanyID(String companyID);

		/**
		 * [R-10] 外出管理を取得する OutManageRepository
		 */
		Optional<OutManage> findOutManageByID(String companyID);

		/**
		 * [R-11] 変形労働の集計設定を取得する AggDeformedLaborSettingRepository
		 */
		Optional<AggDeformedLaborSetting> findAggDeformedLaborSettingByCid(String companyId);

		/**
		 * [R-12] フレックス勤務の設定を取得する FlexWorkMntSetRepository
		 */
		Optional<FlexWorkSet> findFlexWorkSet(String companyId);

		/**
		 * [R-13] 年休設定を取得する AnnualPaidLeaveSettingRepository
		 */
		AnnualPaidLeaveSetting findByCompanyId(String companyId);

		/**
		 * [R-14] 代休管理設定を取得する CompensLeaveComSetRepository
		 */
		CompensatoryLeaveComSetting findCompensatoryLeaveComSetting(String companyId);

		/**
		 * [R-15] 振休管理設定を取得する ComSubstVacationRepository
		 */
		Optional<ComSubstVacation> findComSubstVacationById(String companyId);

		/**
		 * [R-16] 時間特別休暇の管理設定を取得する TimeSpecialLeaveMngSetRepository
		 */
		Optional<TimeSpecialLeaveManagementSetting> findTimeSpecialLeaveManagementSetting(String companyId);

		/**
		 * [R-17] 介護看護休暇設定を取得する NursingLeaveSettingRepository
		 */
		List<NursingLeaveSetting> findNursingLeaveSetting(String companyId);

		/**
		 * [R-18] 複数回勤務管理を取得する WorkManagementMultipleRepository
		 */
		Optional<WorkManagementMultiple> findWorkManagementMultiple(String companyID);

		/**
		 * [R-19] 臨時勤務利用管理を取得する TemporaryWorkUseManageRepository
		 */
		Optional<TemporaryWorkUseManage> findTemporaryWorkUseManage(String companyId);

	}

}
