package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpService;
import nts.uk.ctx.at.record.dom.divergence.time.JudgmentResult;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.history.DateHistoryItem;

public class CompanyDivergenceReferenceTimeServiceIml implements CompanyDivergenceReferenceTimeService {

	@Inject
	DivergenceReferenceTimeUsageUnitRepository divReferenceTimeUsageUnitRepo;

	@Inject
	WorkTypeDivergenceReferenceTimeHistoryRepository workTypeDivergenceRefTimeHistRepo;

	@Inject
	WorkTypeDivergenceReferenceTimeRepository workTypeDivRefTimeRepo;

	@Inject
	BusinessTypeEmpService typeEmService;

	@Inject
	CompanyDivergenceReferenceTimeHistoryRepository comDivRefTimeHistRepo;

	@Inject
	CompanyDivergenceReferenceTimeRepository comDivRefTimeRepo;

	@Override
	public JudgmentResultDetermineRefTime CheckDivergenceTime(String userId, String companyId, GeneralDate processDate,
			int divergenceTimeNo, AttendanceTime DivergenceTimeOccurred) {

		JudgmentResultDetermineRefTime judgmentResultDetermineRefTime = new JudgmentResultDetermineRefTime();

		Optional<DivergenceReferenceTimeUsageUnit> optionalDivReferenceTimeUsageUnit = divReferenceTimeUsageUnitRepo
				.findByCompanyId(companyId);
		if (optionalDivReferenceTimeUsageUnit.isPresent()
				&& optionalDivReferenceTimeUsageUnit.get().isWorkTypeUseSet()) {

			DetermineReferenceTime determineRefTime = new DetermineReferenceTime();
			JudgmentResult result = JudgmentResult.ERROR;
			BusinessTypeOfEmployee typeofEmployee = typeEmService.getData(userId, processDate).get(0);

			WorkTypeDivergenceReferenceTimeHistory workTypeDivRefTimeHist = workTypeDivergenceRefTimeHistRepo
					.findByKey(typeofEmployee.getHistoryId());

			DateHistoryItem dateHistItem = workTypeDivRefTimeHist.getHistoryItems().stream()
					.filter(item -> item.start().before(processDate) && item.end().after(processDate)).findFirst()
					.get();

			if (dateHistItem != null) {
				Optional<WorkTypeDivergenceReferenceTime> optionalWorkTypeDivRefTime = workTypeDivRefTimeRepo.findByKey(
						typeofEmployee.getHistoryId(), workTypeDivRefTimeHist.getWorkTypeCode(), divergenceTimeNo);

				if (optionalWorkTypeDivRefTime.isPresent()) {
					WorkTypeDivergenceReferenceTime workTypeDivRefTime = optionalWorkTypeDivRefTime.get();

					if (workTypeDivRefTime.getNotUseAtr() == NotUseAtr.USE) {

						if (workTypeDivRefTime.getDivergenceReferenceTimeValue().get().getAlarmTime().get()
								.greaterThan(DivergenceTimeOccurred)
								|| workTypeDivRefTime.getDivergenceReferenceTimeValue().get().getAlarmTime()
										.get() == new DivergenceReferenceTime(0)) {
							result = JudgmentResult.NORMAL;
						} else {
							result = JudgmentResult.ERROR;
							determineRefTime.setThreshold(
									workTypeDivRefTime.getDivergenceReferenceTimeValue().get().getAlarmTime().get());
						}

						if (workTypeDivRefTime.getDivergenceReferenceTimeValue().get().getErrorTime().get()
								.greaterThan(DivergenceTimeOccurred)
								|| workTypeDivRefTime.getDivergenceReferenceTimeValue().get().getErrorTime()
										.get() == new DivergenceReferenceTime(0)) {
							result = JudgmentResult.NORMAL;
						} else {
							result = JudgmentResult.ERROR;
							determineRefTime.setThreshold(
									workTypeDivRefTime.getDivergenceReferenceTimeValue().get().getErrorTime().get());
						}

					} else {
						result = JudgmentResult.NORMAL;
					}
				}

			} else {
				CompanyDivergenceReferenceTimeHistory companyDivergenceReferenceTimeHistory = comDivRefTimeHistRepo
						.findByHistId(typeofEmployee.getHistoryId());

				DateHistoryItem dateHistItemcom = companyDivergenceReferenceTimeHistory.getHistoryItems().stream()
						.filter(item -> item.start().before(processDate) && item.end().after(processDate)).findFirst()
						.get();
				if (dateHistItemcom != null) {
					Optional<CompanyDivergenceReferenceTime> optionalComDivRefTime = comDivRefTimeRepo
							.findByKey(typeofEmployee.getHistoryId(), divergenceTimeNo);

					if (optionalComDivRefTime.isPresent()) {
						CompanyDivergenceReferenceTime companyDivergenceReferenceTime = optionalComDivRefTime.get();

						if (companyDivergenceReferenceTime.getNotUseAtr() == NotUseAtr.USE) {

							if (companyDivergenceReferenceTime.getDivergenceReferenceTimeValue().get().getAlarmTime()
									.get().greaterThan(DivergenceTimeOccurred)
									|| companyDivergenceReferenceTime.getDivergenceReferenceTimeValue().get()
											.getAlarmTime().get() == new DivergenceReferenceTime(0)) {
								result = JudgmentResult.NORMAL;
							} else {
								result = JudgmentResult.ERROR;
								determineRefTime.setThreshold(companyDivergenceReferenceTime
										.getDivergenceReferenceTimeValue().get().getAlarmTime().get());
							}

							if (companyDivergenceReferenceTime.getDivergenceReferenceTimeValue().get().getErrorTime()
									.get().greaterThan(DivergenceTimeOccurred)
									|| companyDivergenceReferenceTime.getDivergenceReferenceTimeValue().get()
											.getErrorTime().get() == new DivergenceReferenceTime(0)) {
								result = JudgmentResult.NORMAL;
							} else {
								result = JudgmentResult.ERROR;
								determineRefTime.setThreshold(companyDivergenceReferenceTime
										.getDivergenceReferenceTimeValue().get().getErrorTime().get());
							}

						} else {
							result = JudgmentResult.NORMAL;
						}
					}
				}

			}
			
			judgmentResultDetermineRefTime.setJudgmentResult(result);
			judgmentResultDetermineRefTime.setDetermineReafTime(determineRefTime);

		}
		if (!optionalDivReferenceTimeUsageUnit.isPresent()
				|| !optionalDivReferenceTimeUsageUnit.get().isWorkTypeUseSet()) {
			// Incase false or domain is not exist
		}
		return new JudgmentResultDetermineRefTime();
	}

}
