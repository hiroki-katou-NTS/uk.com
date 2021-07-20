package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.closegetunlockedperiod;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.AchievementAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.GetPeriodCanProcesse;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.IgnoreFlagDuringLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock.Require;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@RunWith(JMockit.class)
public class ClosingGetUnlockedPeriodTest {

	@Injectable
	private GetPeriodCanProcesse.Require require;

	/**
	 * ignoreFlagDuringLock == IgnoreFlagDuringLock.CAL_DURING_LOCK
	 */
	@Test
	public void test_get_1() {
		DatePeriod period = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 2, 1)); // dummy
		String employmentCode = "employmentCode";// dummy
		IgnoreFlagDuringLock ignoreFlagDuringLock = IgnoreFlagDuringLock.CAL_DURING_LOCK;
		AchievementAtr achievementAtr = AchievementAtr.DAILY;// dummy
		List<DatePeriod> result = ClosingGetUnlockedPeriod.get(require, period, employmentCode, ignoreFlagDuringLock,
				achievementAtr);

		assertThat(result.get(0).start()).isEqualTo(period.start());
		assertThat(result.get(0).end()).isEqualTo(period.end());

	}

	/**
	 * ignoreFlagDuringLock != IgnoreFlagDuringLock.CAL_DURING_LOCK
	 * Requireで締めIDを取得する isEmpty
	 * 
	 */
	@Test
	public void test_get_2() {
		DatePeriod period = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 2, 1)); // dummy
		String employmentCode = "employmentCode";// dummy
		IgnoreFlagDuringLock ignoreFlagDuringLock = IgnoreFlagDuringLock.CAN_BE_AGGREGATED;
		AchievementAtr achievementAtr = AchievementAtr.DAILY;// dummy

		new Expectations() {
			{
				require.findByEmploymentCD(anyString);
			}
		};
		List<DatePeriod> result = ClosingGetUnlockedPeriod.get(require, period, employmentCode, ignoreFlagDuringLock,
				achievementAtr);

		assertThat(result).isEmpty();

	}

	/**
	 * ignoreFlagDuringLock != IgnoreFlagDuringLock.CAL_DURING_LOCK
	 * Requireで締めIDを取得する not empty 
	 * Requireで「当月の実績ロック」を取得する is empty
	 */
	@Test
	public void test_get_3() {
		DatePeriod period = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 2, 1)); // dummy
		String employmentCode = "employmentCode";// dummy
		IgnoreFlagDuringLock ignoreFlagDuringLock = IgnoreFlagDuringLock.CAN_BE_AGGREGATED;
		AchievementAtr achievementAtr = AchievementAtr.DAILY;// dummy
		ClosureEmployment closureEmployment = new ClosureEmployment("companyId", employmentCode, 1);
		new Expectations() {
			{
				require.findByEmploymentCD(anyString);
				result = Optional.of(closureEmployment);

				require.findById(anyInt);
			}
		};
		List<DatePeriod> result = ClosingGetUnlockedPeriod.get(require, period, employmentCode, ignoreFlagDuringLock,
				achievementAtr);

		assertThat(result.get(0).start()).isEqualTo(period.start());
		assertThat(result.get(0).end()).isEqualTo(period.end());

	}

	/**
	 * ignoreFlagDuringLock != IgnoreFlagDuringLock.CAL_DURING_LOCK
	 * Requireで締めIDを取得する not empty 
	 * Requireで「当月の実績ロック」を取得する not empty
	 */
	@Test
	public void test_get_4() {
		DatePeriod period = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 2, 1)); // dummy
		String employmentCode = "employmentCode";// dummy
		IgnoreFlagDuringLock ignoreFlagDuringLock = IgnoreFlagDuringLock.CAN_BE_AGGREGATED;
		AchievementAtr achievementAtr = AchievementAtr.DAILY;// dummy
		ClosureEmployment closureEmployment = new ClosureEmployment("companyId", employmentCode, 1);
		
		List<DatePeriod> listP = new ArrayList<>();
		listP.add(new DatePeriod(GeneralDate.ymd(2021, 1, 15), GeneralDate.ymd(2021, 2, 1)));
		
		ActualLock actualLock = new ActualLock("companyId", ClosureId.RegularEmployee, LockStatus.LOCK, LockStatus.LOCK);

		new MockUp<ActualLock>() {
			@Mock
			public List<DatePeriod> askForUnlockedPeriod(Require require, DatePeriod period,
					AchievementAtr achievementAtr) {
				return listP;
			}
		};

		new Expectations() {
			{
				require.findByEmploymentCD(anyString);
				result = Optional.of(closureEmployment);

				require.findById(anyInt);
				result = Optional.of(actualLock);
			}
		};
		List<DatePeriod> result = ClosingGetUnlockedPeriod.get(require, period, employmentCode, ignoreFlagDuringLock,
				achievementAtr);

		assertThat(result.get(0).start()).isEqualTo(listP.get(0).start());
		assertThat(result.get(0).end()).isEqualTo(listP.get(0).end());

	}

}
