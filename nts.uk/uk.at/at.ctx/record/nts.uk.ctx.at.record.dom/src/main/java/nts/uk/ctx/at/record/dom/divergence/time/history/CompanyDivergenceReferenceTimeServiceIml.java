package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpService;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.record.dom.divergence.time.JudgmentResult;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergencetime.DiverdenceReasonCode;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
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
							// set judgment result
							result = JudgmentResult.ALARM;
						} else {
							referenceTime = workTypeDivRefTime.getDivergenceReferenceTimeValue().get().getErrorTime()
									.get();
							// set judgment result
							result = JudgmentResult.ERROR;
						}

						// check reference Time
						if (referenceTime.greaterThan(DivergenceTimeOccurred)
								|| referenceTime == new DivergenceReferenceTime(0)) {
							// set judgment result
							result = JudgmentResult.NORMAL;
						} else { // set determineRefTime.Threshold

							if ((divTimeErrotCancelMethod.isReasonSelected() && divReasonCode != null)
									|| (divTimeErrotCancelMethod.isReasonInputed() && divReasonCode != null)) {
								// set judgment result
								result = JudgmentResult.NORMAL;
							} else {
								//case ReasonSelected=false and ReasonInputed=false
								if (!divTimeErrotCancelMethod.isReasonSelected()
										&& !divTimeErrotCancelMethod.isReasonInputed())
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

					if (optionalComDivRefTime.isPresent()) {
						CompanyDivergenceReferenceTime companyDivergenceReferenceTime = optionalComDivRefTime.get();
						// check NotUseAtr
						if (companyDivergenceReferenceTime.getNotUseAtr() == NotUseAtr.USE) {

							DivergenceReferenceTime referenceTime = new DivergenceReferenceTime(0);

							if (checkCategory == JudgmentResult.ALARM) {
								referenceTime = companyDivergenceReferenceTime.getDivergenceReferenceTimeValue().get()
										.getAlarmTime().get();
								// set judgment result
								result = JudgmentResult.ALARM;
							} else {
								referenceTime = companyDivergenceReferenceTime.getDivergenceReferenceTimeValue().get()
										.getErrorTime().get();
								// set judgment result
								result = JudgmentResult.ERROR;
							}

							// check reference Time
							if (referenceTime.greaterThan(DivergenceTimeOccurred)
									|| referenceTime == new DivergenceReferenceTime(0)) {
								// set judgment result
								result = JudgmentResult.NORMAL;
							} else { // set determineRefTime.Threshold

								if (divTimeErrotCancelMethod.isReasonSelected()
										|| divTimeErrotCancelMethod.isReasonInputed()) {
									// set judgment result
									result = JudgmentResult.NORMAL;
								} else {
									determineRefTime.setThreshold(referenceTime);
								}

							}

						} else {
							// set Judgment result
							result = JudgmentResult.NORMAL;
						}
					}
				}

			}
			// set judgmentResultDetermineRefTime
			judgmentResultDetermineRefTime.setJudgmentResult(result);
			judgmentResultDetermineRefTime.setDetermineReafTime(determineRefTime);

		} else {
			// Incase false or domain is not exist
			// get company's history items
			CompanyDivergenceReferenceTimeHistory companyDivergenceReferenceTimeHistory = companyDivergenceReferenceTimeHistoryRepo
					.findByDate(companyId, processDate);
			// get history item
			Optional<DateHistoryItem> dateHisItem = companyDivergenceReferenceTimeHistory.getHistoryItems().stream()
					.filter(item -> item.start().after(processDate) && item.end().before(processDate)).findFirst();
			if (dateHisItem.isPresent()) {
				// get company's deviation reference time based on NO
				Optional<CompanyDivergenceReferenceTime> companyDivRefTime = companyDivergenceReferenceTimeRepo
						.findByKey(dateHisItem.get().identifier(), divergenceTimeNo);
				if (companyDivRefTime.isPresent() && companyDivRefTime.get().getNotUseAtr().equals(NotUseAtr.USE)) {
					// determine divergence time
					// check error time
					if (DivergenceTimeOccurred.lessThan(
							companyDivRefTime.get().getDivergenceReferenceTimeValue().get().getErrorTime().get())) {
						// check alarm time
						if (DivergenceTimeOccurred.greaterThanOrEqualTo(
								companyDivRefTime.get().getDivergenceReferenceTimeValue().get().getAlarmTime().get())) {
							Optional<DivergenceReferenceTime> alarmTime = companyDivRefTime.get()
									.getDivergenceReferenceTimeValue().get().getAlarmTime();
							determineRefTime.setThreshold(alarmTime.get());
							result = JudgmentResult.ALARM;
						} else {
							result = JudgmentResult.NORMAL;
						}
					} else {
						Optional<DivergenceReferenceTime> errorTime = companyDivRefTime.get()
								.getDivergenceReferenceTimeValue().get().getErrorTime();
						determineRefTime.setThreshold(errorTime.get());
						result = JudgmentResult.ERROR;
					}
				} else {
					result = JudgmentResult.NORMAL;
				}
			}
			// set reference time type
			determineRefTime.setReferenceTime(ReferenceTime.COMPANY);
			// set judgmentResultDetermineRefTime
			judgmentResultDetermineRefTime.setDetermineReafTime(determineRefTime);
			judgmentResultDetermineRefTime.setJudgmentResult(result);
		}
		return judgmentResultDetermineRefTime;
	}

}
