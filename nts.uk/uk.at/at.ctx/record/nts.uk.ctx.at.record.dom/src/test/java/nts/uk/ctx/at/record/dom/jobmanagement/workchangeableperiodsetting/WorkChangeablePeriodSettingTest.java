package nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class WorkChangeablePeriodSettingTest {

	@Injectable
	private WorkChangeablePeriodSetting.Require require;

	private String employeeId = "employeeId";

	private GeneralDate date = GeneralDate.today();

	@Test
	public void testCaseDefault() {

		new Expectations() {
			{
				require.get();

				require.getPeriod(employeeId, date);
				result = new DatePeriod(date, date);
			}
		};

		WorkChangeablePeriodSetting periodSetting = new WorkChangeablePeriodSetting(
				EnumAdaptor.valueOf(0, MonthsAgo.class));

		GeneralDate result = periodSetting.getWorkCorrectionStartDate(require, employeeId);

		assertThat(result).isEqualTo(GeneralDate.today());
	}

	@Test
	public void testCaseOneMonthAgo() {

		WorkChangeablePeriodSetting setting = new WorkChangeablePeriodSetting(EnumAdaptor.valueOf(1, MonthsAgo.class));

		new Expectations() {
			{
				require.get();
				result = setting;

				require.getPeriod(employeeId, date);
				result = new DatePeriod(date, date);
			}
		};

		WorkChangeablePeriodSetting periodSetting = new WorkChangeablePeriodSetting(
				EnumAdaptor.valueOf(0, MonthsAgo.class));

		GeneralDate result = periodSetting.getWorkCorrectionStartDate(require, employeeId);

		assertThat(result.month()).isEqualTo(GeneralDate.today().addMonths(-1).month());
	}

	@Test
	public void testCaseTowMonthAgo() {

		WorkChangeablePeriodSetting setting = new WorkChangeablePeriodSetting(EnumAdaptor.valueOf(2, MonthsAgo.class));

		new Expectations() {
			{
				require.get();
				result = setting;

				require.getPeriod(employeeId, date);
				result = new DatePeriod(date, date);
			}
		};

		WorkChangeablePeriodSetting periodSetting = new WorkChangeablePeriodSetting(
				EnumAdaptor.valueOf(0, MonthsAgo.class));

		GeneralDate result = periodSetting.getWorkCorrectionStartDate(require, employeeId);

		assertThat(result.month()).isEqualTo(GeneralDate.today().addMonths(-2).month());
	}

	@Test
	public void testCaseThreeMonthAgo() {

		WorkChangeablePeriodSetting setting = new WorkChangeablePeriodSetting(EnumAdaptor.valueOf(3, MonthsAgo.class));

		new Expectations() {
			{
				require.get();
				result = setting;

				require.getPeriod(employeeId, date);
				result = new DatePeriod(date, date);
			}
		};

		WorkChangeablePeriodSetting periodSetting = new WorkChangeablePeriodSetting(
				EnumAdaptor.valueOf(0, MonthsAgo.class));

		GeneralDate result = periodSetting.getWorkCorrectionStartDate(require, employeeId);

		assertThat(result.month()).isEqualTo(GeneralDate.today().addMonths(-3).month());
	}

	@Test
	public void testCaseFourMonthAgo() {

		WorkChangeablePeriodSetting setting = new WorkChangeablePeriodSetting(EnumAdaptor.valueOf(4, MonthsAgo.class));

		new Expectations() {
			{
				require.get();
				result = setting;

				require.getPeriod(employeeId, date);
				result = new DatePeriod(date, date);
			}
		};

		WorkChangeablePeriodSetting periodSetting = new WorkChangeablePeriodSetting(
				EnumAdaptor.valueOf(0, MonthsAgo.class));

		GeneralDate result = periodSetting.getWorkCorrectionStartDate(require, employeeId);

		assertThat(result.month()).isEqualTo(GeneralDate.today().addMonths(-4).month());
	}

	@Test
	public void testCaseFiveMonthAgo() {

		WorkChangeablePeriodSetting setting = new WorkChangeablePeriodSetting(EnumAdaptor.valueOf(5, MonthsAgo.class));

		new Expectations() {
			{
				require.get();
				result = setting;

				require.getPeriod(employeeId, date);
				result = new DatePeriod(date, date);
			}
		};

		WorkChangeablePeriodSetting periodSetting = new WorkChangeablePeriodSetting(
				EnumAdaptor.valueOf(0, MonthsAgo.class));

		GeneralDate result = periodSetting.getWorkCorrectionStartDate(require, employeeId);

		assertThat(result.month()).isEqualTo(GeneralDate.today().addMonths(-5).month());
	}

}
