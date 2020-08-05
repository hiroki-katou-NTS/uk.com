package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.GetTightSetting.GetTightSettingResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.SettingExpirationDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

public class SettingExpirationDateTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testUnlimit() {

		GeneralDate actualResult = SettingExpirationDate.settingExp(ExpirationTime.UNLIMITED,
				Optional.of(new GetTightSettingResult(11, new ClosureDate(1, false),
						new DatePeriod(GeneralDate.ymd(2019, 11, 1), GeneralDate.ymd(2019, 11, 30)))),
				GeneralDate.ymd(2019, 11, 1));
		assertThat(actualResult).isEqualTo(GeneralDate.max());

	}

	@Test
	public void testThisMonth() {

		GeneralDate actualResult = SettingExpirationDate.settingExp(ExpirationTime.THIS_MONTH,
				Optional.of(new GetTightSettingResult(11, new ClosureDate(15, false),
						new DatePeriod(GeneralDate.ymd(2019, 11, 1), GeneralDate.ymd(2019, 11, 30)))),
				GeneralDate.ymd(2019, 11, 15));
		assertThat(actualResult).isEqualTo(GeneralDate.ymd(2019, 11, 15));

		GeneralDate actualResult2 = SettingExpirationDate.settingExp(ExpirationTime.THIS_MONTH,
				Optional.of(new GetTightSettingResult(11, new ClosureDate(15, false),
						new DatePeriod(GeneralDate.ymd(2019, 11, 1), GeneralDate.ymd(2019, 11, 30)))),
				GeneralDate.ymd(2019, 11, 16));
		assertThat(actualResult2).isEqualTo(GeneralDate.ymd(2019, 12, 15));

	}

	@Test
	public void testThisYear() {

		GeneralDate actualResult = SettingExpirationDate.settingExp(ExpirationTime.END_OF_YEAR,
				Optional.of(new GetTightSettingResult(11, new ClosureDate(15, false),
						new DatePeriod(GeneralDate.ymd(2019, 11, 1), GeneralDate.ymd(2019, 11, 30)))),
				GeneralDate.ymd(2019, 11, 15));
		assertThat(actualResult).isEqualTo(GeneralDate.ymd(2020, 10, 30));

		GeneralDate actualResult2 = SettingExpirationDate.settingExp(ExpirationTime.END_OF_YEAR,
				Optional.of(new GetTightSettingResult(12, new ClosureDate(15, false),
						new DatePeriod(GeneralDate.ymd(2019, 11, 1), GeneralDate.ymd(2019, 11, 30)))),
				GeneralDate.ymd(2019, 11, 15));
		assertThat(actualResult2).isEqualTo(GeneralDate.ymd(2019, 11, 29));

	}

	@Test
	public void testOther() {

		GeneralDate actualResult = SettingExpirationDate.settingExp(ExpirationTime.SEVEN_MONTH,
				Optional.of(new GetTightSettingResult(11, new ClosureDate(15, false),
						new DatePeriod(GeneralDate.ymd(2019, 11, 1), GeneralDate.ymd(2019, 11, 30)))),
				GeneralDate.ymd(2019, 11, 15));
		assertThat(actualResult).isEqualTo(GeneralDate.ymd(2020, 6, 15));

	}
}
