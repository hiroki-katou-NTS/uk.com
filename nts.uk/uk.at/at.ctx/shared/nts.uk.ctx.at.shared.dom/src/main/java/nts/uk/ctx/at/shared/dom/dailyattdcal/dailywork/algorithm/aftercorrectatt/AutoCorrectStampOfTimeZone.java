package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.aftercorrectatt;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ReflectWorkInforDomainService;
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
		TimeLeavingOfDailyAttd timeLeaving = reflectWorkDomainService.createStamp(companyId,
				domainDaily.getWorkInformation(), Optional.of(workingCond),
				domainDaily.getAttendanceLeave().orElse(null), domainDaily.getEmployeeId(), domainDaily.getYmd(),
				Optional.empty());
		domainDaily.setAttendanceLeave(Optional.ofNullable(timeLeaving));

		// 直行直帰による、戻り時刻補正
		/// domain no map
		domainDaily
				.setOutingTime(ReturnDirectTimeCorrection.process(companyId, timeLeaving, domainDaily.getOutingTime()));

		return domainDaily;

	}

}
