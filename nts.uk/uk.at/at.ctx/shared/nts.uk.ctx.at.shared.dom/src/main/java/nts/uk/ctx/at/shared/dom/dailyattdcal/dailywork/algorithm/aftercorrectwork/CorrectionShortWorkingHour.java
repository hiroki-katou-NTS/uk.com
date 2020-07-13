package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.aftercorrectwork;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.repository.ConfirmReflectWorkingTimeOuput;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.repository.ReflectShortWorkingTimeDomainService;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;

/**
 * @author ThanhNX
 * 
 *         短時間勤務の補正
 */
@Stateless
public class CorrectionShortWorkingHour {

	@Inject
	private SWorkTimeHistItemRepository sWTHistItemRepo;

	@Inject
	private ReflectShortWorkingTimeDomainService reflectShortWorkTimeDomService;

	// 短時間勤務の補正
	public IntegrationOfDaily correct(String companyId, IntegrationOfDaily domainDaily) {

		String sid = domainDaily.getEmployeeId();
		GeneralDate date = domainDaily.getYmd();
		// 短時間勤務時間帯を反映するか確認する
		ConfirmReflectWorkingTimeOuput rShotWT = reflectShortWorkTimeDomService.confirmReflectWorkingTime(
				UUID.randomUUID().toString(), companyId, date, sid, domainDaily.getWorkInformation(),
				domainDaily.getAttendanceLeave().orElse(null));

		if (rShotWT.isReflect()) {
			// 社員の短時間勤務履歴を期間で取得する
			List<ShortWorkTimeHistoryItem> shortTimeHistItem = sWTHistItemRepo.findWithSidDatePeriod(companyId,
					Arrays.asList(sid), new DatePeriod(date, date));

			if (shortTimeHistItem.isEmpty() || !domainDaily.getShortTime().isPresent())
				return domainDaily;
			// 短時間勤務を変更
			domainDaily.getShortTime().get().change(shortTimeHistItem.get(0), domainDaily.getEditState());

		} else {
			// 短時間勤務をクリア
			domainDaily.getShortTime().get().clear(domainDaily.getEditState());
		}

		return domainDaily;

	}

}
