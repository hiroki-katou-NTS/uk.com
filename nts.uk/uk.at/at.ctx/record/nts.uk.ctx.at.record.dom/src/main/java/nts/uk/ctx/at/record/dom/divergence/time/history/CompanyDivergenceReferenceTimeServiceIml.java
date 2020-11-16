package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.divergence.time.JudgmentResult;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReason;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DiverdenceReasonCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeErrorCancelMethod;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Class CompanyDivergenceReferenceTimeServiceIml.
 */
public class CompanyDivergenceReferenceTimeServiceIml implements CompanyDivergenceReferenceTimeService {

	/** The div reference time usage unit repo. */
	@Inject
	DivergenceReferenceTimeUsageUnitRepository divReferenceTimeUsageUnitRepo;

	/** The company divergence reference time history repo. */
	@Inject
	CompanyDivergenceReferenceTimeHistoryRepository companyDivergenceReferenceTimeHistoryRepo;

	/** The company divergence reference time repo. */
	@Inject
	CompanyDivergenceReferenceTimeRepository companyDivergenceReferenceTimeRepo;

	/** The work type divergence ref time hist repo. */
	@Inject
	WorkTypeDivergenceReferenceTimeHistoryRepository workTypeDivergenceRefTimeHistRepo;

	/** The work type div ref time repo. */
	@Inject
	WorkTypeDivergenceReferenceTimeRepository workTypeDivRefTimeRepo;

	/** The type em service. */
	@Inject
	BusinessTypeEmpService typeEmService;

	/** The com div ref time hist repo. */
	@Inject
	CompanyDivergenceReferenceTimeHistoryRepository comDivRefTimeHistRepo;

	/** The com div ref time repo. */
	@Inject
	CompanyDivergenceReferenceTimeRepository comDivRefTimeRepo;

	// 乖離時間をチェックする
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeService#CheckDivergenceTime(java.lang.
	 * String, java.lang.String, nts.arc.time.GeneralDate, int,
	 * nts.uk.ctx.at.shared.dom.common.time.AttendanceTime)
	 */

	@Override
	public JudgmentResultDetermineRefTime CheckDivergenceTime(String userId, GeneralDate processDate,
			int divergenceTimeNo, JudgmentResult checkCategory, AttendanceTime DivergenceTimeOccurred,
			DiverdenceReasonCode divReasonCode, DivergenceReason divReason,
			DivergenceTimeErrorCancelMethod divTimeErrotCancelMethod) {

		JudgmentResultDetermineRefTime judgmentResultDetermineRefTime = new JudgmentResultDetermineRefTime();
		DetermineReferenceTime determineRefTime = new DetermineReferenceTime();
		JudgmentResult result = JudgmentResult.ERROR;
		final DivergenceReferenceTime zeroTime = new DivergenceReferenceTime(0);
		String companyId = AppContexts.user().companyId();

		// get DivergenceReferenceTimeUsageUnit
		Optional<DivergenceReferenceTimeUsageUnit> optionalDivReferenceTimeUsageUnit = divReferenceTimeUsageUnitRepo
				.findByCompanyId(companyId);
		if (optionalDivReferenceTimeUsageUnit.isPresent()
				&& optionalDivReferenceTimeUsageUnit.get().isWorkTypeUseSet()) {

			determineRefTime.setReferenceTime(ReferenceTime.WORKTYPE);

			// get BusinessTypeOfEmployee
			BusinessTypeOfEmployee typeofEmployee = typeEmService.getData(userId, processDate).get(0);

			// get workTypeDivRefTimeHist
			WorkTypeDivergenceReferenceTimeHistory workTypeDivRefTimeHist = workTypeDivergenceRefTimeHistRepo
					.findByKey(typeofEmployee.getHistoryId());

			// get DateHistoryItem
			DateHistoryItem dateHistItem = workTypeDivRefTimeHist.getHistoryItems().stream()
					.filter(item -> item.start().before(processDate) && item.end().after(processDate)).findFirst()
					.get();

			// check DateHistoryItem is null
			if (dateHistItem != null) {
				// get optionalWorkTypeDivRefTime
				Optional<WorkTypeDivergenceReferenceTime> optionalWorkTypeDivRefTime = workTypeDivRefTimeRepo.findByKey(
						typeofEmployee.getHistoryId(), workTypeDivRefTimeHist.getWorkTypeCode(), divergenceTimeNo);

				if (optionalWorkTypeDivRefTime.isPresent()) {
					WorkTypeDivergenceReferenceTime workTypeDivRefTime = optionalWorkTypeDivRefTime.get();

					// check getNotUseAtr
					if (workTypeDivRefTime.getNotUseAtr() == NotUseAtr.USE) {

						DivergenceReferenceTime referenceTime = new DivergenceReferenceTime(0);

						if (checkCategory == JudgmentResult.ALARM) {
							referenceTime = workTypeDivRefTime.getDivergenceReferenceTimeValue().get().getAlarmTime()
									.get();
						} else {
							referenceTime = workTypeDivRefTime.getDivergenceReferenceTimeValue().get().getErrorTime()
									.get();
						}
						// check reference Time
						if (referenceTime.greaterThan(DivergenceTimeOccurred.v()) || referenceTime.equals(zeroTime)) {
							// set judgment result
							result = JudgmentResult.NORMAL;
						} else { // set determineRefTime.Threshold

							if ((divTimeErrotCancelMethod.isReasonSelected() && divReasonCode != null)
									|| (divTimeErrotCancelMethod.isReasonInputed() && divReason != null)) {
								// set judgment result
								result = JudgmentResult.NORMAL;
							} else {
								// case ReasonSelected=false and ReasonInputed=false
								result = checkCategory;
								determineRefTime.setThreshold(referenceTime);
							}
						}

					} else {
						// set judgment result
						result = JudgmentResult.NORMAL;
					}
				}

			} else {
				// get companyDivergenceReferenceTimeHistory
				CompanyDivergenceReferenceTimeHistory companyDivergenceReferenceTimeHistory = comDivRefTimeHistRepo
						.findByHistId(typeofEmployee.getHistoryId());
				// get dateHistItemcom
				DateHistoryItem dateHistItemcom = companyDivergenceReferenceTimeHistory.getHistoryItems().stream()
						.filter(item -> item.start().before(processDate) && item.end().after(processDate)).findFirst()
						.get();
				// check dateHistItemcom
				if (dateHistItemcom != null) {
					// get CompanyDivergenceReferenceTime
					Optional<CompanyDivergenceReferenceTime> optionalComDivRefTime = comDivRefTimeRepo
							.findByKey(typeofEmployee.getHistoryId(), divergenceTimeNo);
					// determine divergence time
					determineDivergenceTime(result, optionalComDivRefTime, checkCategory, DivergenceTimeOccurred, divReasonCode, divReason, divTimeErrotCancelMethod, determineRefTime);
				}
			}
			// set judgmentResultDetermineRefTime
			judgmentResultDetermineRefTime.setJudgmentResult(result);
			judgmentResultDetermineRefTime.setDetermineReafTime(determineRefTime);

		} else { // Incase false or domain is not exist
			// get company's history items
			CompanyDivergenceReferenceTimeHistory companyDivergenceReferenceTimeHistory = companyDivergenceReferenceTimeHistoryRepo
					.findByDate(companyId, processDate);
			// get dateHistItemcom
			DateHistoryItem dateHistItemcom = companyDivergenceReferenceTimeHistory.getHistoryItems().stream()
					.filter(item -> item.start().before(processDate) && item.end().after(processDate)).findFirst()
					.get();
			// check dateHistItemcom
			if (dateHistItemcom != null) {
				// get CompanyDivergenceReferenceTime
				Optional<CompanyDivergenceReferenceTime> optionalComDivRefTime = comDivRefTimeRepo
						.findByKey(dateHistItemcom.identifier(), divergenceTimeNo);
				// determine divergence time
				determineDivergenceTime(result, optionalComDivRefTime, checkCategory, DivergenceTimeOccurred, divReasonCode, divReason, divTimeErrotCancelMethod, determineRefTime);

			}
			// set reference time type
			determineRefTime.setReferenceTime(ReferenceTime.COMPANY);
			// set judgmentResultDetermineRefTime
			judgmentResultDetermineRefTime.setDetermineReafTime(determineRefTime);
			judgmentResultDetermineRefTime.setJudgmentResult(result);
		}
		return judgmentResultDetermineRefTime;
	}

	private void determineDivergenceTime(JudgmentResult result, Optional<CompanyDivergenceReferenceTime> optionalComDivRefTime, JudgmentResult checkCategory, AttendanceTime DivergenceTimeOccurred,
			DiverdenceReasonCode divReasonCode, DivergenceReason divReason,DivergenceTimeErrorCancelMethod divTimeErrotCancelMethod, DetermineReferenceTime determineRefTime) {
		
		final DivergenceReferenceTime zeroTime = new DivergenceReferenceTime(0);
		// check NotUseAtr
		if (optionalComDivRefTime.isPresent() && optionalComDivRefTime.get().getNotUseAtr() == NotUseAtr.USE) {
			
			CompanyDivergenceReferenceTime companyDivergenceReferenceTime = optionalComDivRefTime.get();
			DivergenceReferenceTime referenceTime = new DivergenceReferenceTime(0);
			
			// check category
			if (checkCategory == JudgmentResult.ALARM) {
				referenceTime = companyDivergenceReferenceTime.getDivergenceReferenceTimeValue().get()
						.getAlarmTime().get();
			} else {
				referenceTime = companyDivergenceReferenceTime.getDivergenceReferenceTimeValue().get()
						.getErrorTime().get();
			}
			// check reference Time
			if (referenceTime.greaterThan(DivergenceTimeOccurred.v()) || referenceTime.equals(zeroTime)) {
				// set judgment result
				result = JudgmentResult.NORMAL;
			} else { // set determineRefTime.Threshold
				if ((divTimeErrotCancelMethod.isReasonSelected() && divReasonCode != null)
						|| (divTimeErrotCancelMethod.isReasonInputed() && divReason != null)) {
					// set judgment result
					result = JudgmentResult.NORMAL;
				} else {
					// case ReasonSelected=false and ReasonInputed=false
					result = checkCategory;
					determineRefTime.setThreshold(referenceTime);
				}
			}
		} else {
			// set Judgment result
			result = JudgmentResult.NORMAL;
		}
	}
}
