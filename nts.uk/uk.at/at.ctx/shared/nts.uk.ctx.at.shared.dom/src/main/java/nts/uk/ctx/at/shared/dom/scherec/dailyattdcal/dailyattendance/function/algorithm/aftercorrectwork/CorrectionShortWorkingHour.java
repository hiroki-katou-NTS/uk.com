package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork.CheckReflectShortTimeProcess.CheckReflectShortTimerResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
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
	private CheckReflectShortTimeProcess checkReflectShortTimeProcess;

	// 短時間勤務の補正
	public IntegrationOfDaily correct(String companyId, IntegrationOfDaily domainDaily) {

		String sid = domainDaily.getEmployeeId();
		GeneralDate date = domainDaily.getYmd();
		// 短時間勤務時間帯を反映するか確認する
		CheckReflectShortTimerResult rShotWT = checkReflectShortTimeProcess.check(companyId, date, sid,
				domainDaily.getWorkInformation(), domainDaily.getAttendanceLeave().orElse(null));

		if (rShotWT == CheckReflectShortTimerResult.REFLECT) {
			// 社員の短時間勤務履歴を期間で取得する
			List<ShortWorkTimeHistoryItem> shortTimeHistItem = sWTHistItemRepo.findWithSidDatePeriod(companyId,
					Arrays.asList(sid), new DatePeriod(date, date));

			if (shortTimeHistItem.isEmpty() && domainDaily.getShortTime().isPresent()) {
				domainDaily.getShortTime().get().clear(domainDaily.getEditState());
				return domainDaily;
			}
			// 短時間勤務を変更
			// if (domainDaily.getShortTime().isPresent())
			domainDaily.setShortTime(Optional.ofNullable(new ShortTimeOfDailyAttd(
					ShortTimeOfDailyAttd.change(shortTimeHistItem.get(0), domainDaily.getEditState()))));

		} else {
			// 短時間勤務をクリア
			if (domainDaily.getShortTime().isPresent())
				domainDaily.getShortTime().get().clear(domainDaily.getEditState());
		}

		return domainDaily;

	}

}
