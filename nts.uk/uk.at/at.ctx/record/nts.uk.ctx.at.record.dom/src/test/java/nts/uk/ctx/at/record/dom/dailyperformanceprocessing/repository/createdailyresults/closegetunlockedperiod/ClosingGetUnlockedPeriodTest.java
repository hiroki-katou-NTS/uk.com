package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.closegetunlockedperiod;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.AchievementAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.GetPeriodCanProcesse;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.IgnoreFlagDuringLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;

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
		IgnoreFlagDuringLock ignoreFlagDuringLock = IgnoreFlagDuringLock.CAN_CAL_LOCK;
		AchievementAtr achievementAtr = AchievementAtr.DAILY;// dummy
		ClosureEmployment closureEmployment = new ClosureEmployment("companyId", employmentCode, 1);
		
		new Expectations() {
			{
				require.findByEmploymentCD(anyString);
				result = Optional.of(closureEmployment);

			}
		};
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
		IgnoreFlagDuringLock ignoreFlagDuringLock = IgnoreFlagDuringLock.CANNOT_CAL_LOCK;
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
		IgnoreFlagDuringLock ignoreFlagDuringLock = IgnoreFlagDuringLock.CANNOT_CAL_LOCK;
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
	 * require.findClosureById(anyInt); empty
	 */
	@Test
	public void test_get_4() {
		DatePeriod period = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 2, 15)); // dummy
		String employmentCode = "employmentCode";// dummy
		IgnoreFlagDuringLock ignoreFlagDuringLock = IgnoreFlagDuringLock.CANNOT_CAL_LOCK;
		AchievementAtr achievementAtr = AchievementAtr.DAILY;// dummy
		ClosureEmployment closureEmployment = new ClosureEmployment("companyId", employmentCode, 1);
		
		ActualLock actualLock = new ActualLock("companyId", ClosureId.RegularEmployee, LockStatus.LOCK, LockStatus.LOCK);

		new Expectations() {
			{
				require.findByEmploymentCD(anyString);
				result = Optional.of(closureEmployment);

				require.findById(anyInt);
				result = Optional.of(actualLock);
				
				require.findClosureById(anyInt);
			}
		};
		List<DatePeriod> result = ClosingGetUnlockedPeriod.get(require, period, employmentCode, ignoreFlagDuringLock,
				achievementAtr);
	
		assertThat(result).isEmpty();

	}


	/**
	 * ignoreFlagDuringLock != IgnoreFlagDuringLock.CAL_DURING_LOCK
	 * Requireで締めIDを取得する not empty 
	 * require.findClosureById(anyInt); not empty
	 * Requireで「当月の実績ロック」を取得する not empty
	 */
	@Test
	public void test_get_5() {
		DatePeriod period = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 2, 15)); // dummy
		String employmentCode = "employmentCode";// dummy
		IgnoreFlagDuringLock ignoreFlagDuringLock = IgnoreFlagDuringLock.CANNOT_CAL_LOCK;
		AchievementAtr achievementAtr = AchievementAtr.DAILY;// dummy
		ClosureEmployment closureEmployment = new ClosureEmployment("companyId", employmentCode, 1);
		
		ActualLock actualLock = new ActualLock("companyId", ClosureId.RegularEmployee, LockStatus.LOCK, LockStatus.LOCK);
		DatePeriod periodClosure = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 31));

		Closure closure = new Closure(ClosureId.ClosureFive); 
		CurrentMonth closureMonth = new CurrentMonth(202110);
		closure.setCurrentMonth(closureMonth);
		new Expectations() {
			{
				require.findByEmploymentCD(anyString);
				result = Optional.of(closureEmployment);

				require.findById(anyInt);
				result = Optional.of(actualLock);
				
				require.findClosureById(anyInt);
				result = Optional.of(closure);
				
				require.getClosurePeriod(anyInt, (YearMonth)any);
				result = periodClosure;
				
				require.findClosureById(anyInt);
				result = Optional.of(new Closure(new ClosureGetMemento() {
					@Override
					public UseClassification getUseClassification() {
						return UseClassification.UseClass_Use;
					}
					@Override
					public CompanyId getCompanyId() {
						return new CompanyId("1");
					}
					@Override
					public CurrentMonth getClosureMonth() {
						return new CurrentMonth(1);
					}
					@Override
					public ClosureId getClosureId() {
						return ClosureId.RegularEmployee;
					}
					@Override
					public List<ClosureHistory> getClosureHistories() {
						return new ArrayList<>();
					}
				}));
			}
		};
		List<DatePeriod> result = ClosingGetUnlockedPeriod.get(require, period, employmentCode, ignoreFlagDuringLock,
				achievementAtr);
	
        assertThat(result.get(0).start()).isEqualTo(GeneralDate.ymd(2021, 01, 31));
        assertThat(result.get(0).end()).isEqualTo(GeneralDate.ymd(2021, 02, 15));

	}

}
