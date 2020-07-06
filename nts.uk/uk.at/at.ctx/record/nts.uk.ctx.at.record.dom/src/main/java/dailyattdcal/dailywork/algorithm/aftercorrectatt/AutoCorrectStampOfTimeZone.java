package dailyattdcal.dailywork.algorithm.aftercorrectatt;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectWorkInforDomainService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * @author ThanhNX
 *
 *         自動打刻セットの時間帯補正
 */
@Stateless
public class AutoCorrectStampOfTimeZone {

	@Inject
	private ReflectWorkInforDomainService reflectWorkDomainService;

	// 自動打刻セットの時間帯補正
	public IntegrationOfDaily autoCorrect(String companyId, IntegrationOfDaily domainDaily,
			WorkingConditionItem workingCond) {

		// 自動打刻セットの補正
		TimeLeavingOfDailyPerformance timeLeaving = reflectWorkDomainService.createStamp(companyId,
				domainDaily.getWorkInformation(), Optional.of(workingCond),
				domainDaily.getAttendanceLeave().orElse(null), domainDaily.getWorkInformation().getEmployeeId(),
				domainDaily.getWorkInformation().getYmd(), Optional.empty());
		domainDaily.setAttendanceLeave(Optional.ofNullable(timeLeaving));

		// 直行直帰による、戻り時刻補正
		/// domain no map
		domainDaily.setOutingTime(ReturnDirectTimeCorrection.process(timeLeaving, domainDaily.getOutingTime()));

		return domainDaily;

	}

}
