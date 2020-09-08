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

	/*
	 * 　テストしたい内容
	 * 　　期限がない。
	 * 
	 * 　準備するデータ
	 * 　　休暇使用期限が無期限

	 * */
	@Test
	public void testUnlimit() {

		GeneralDate actualResult = SettingExpirationDate.settingExp(
				ExpirationTime.UNLIMITED,
				Optional.of(new GetTightSettingResult(11, new ClosureDate(1, false),//// 期首月
						new DatePeriod(GeneralDate.ymd(2019, 11, 1),//日付
								GeneralDate.ymd(2019, 11, 30)))),//期間
				GeneralDate.ymd(2019, 11, 1));//年月日
		assertThat(actualResult).isEqualTo(GeneralDate.max());

	}

	/*
	 * 　テストしたい内容
	 * 　　期限が当月。
	 * 
	 * 　準備するデータ
	 * 　　日付チェックが必要　＞　締め日　→　翌月の締日
	 * 
	 * 　　日付チェックが必要　＜　締め日　→　当月締め日

	 * */
	@Test
	public void testThisMonth() {

		GeneralDate actualResult = SettingExpirationDate.settingExp(
				ExpirationTime.THIS_MONTH,
				Optional.of(new GetTightSettingResult(11, new ClosureDate(15, false),//// 期首月
						new DatePeriod(GeneralDate.ymd(2019, 11, 1),//日付
								GeneralDate.ymd(2019, 11, 30)))),//期間
				GeneralDate.ymd(2019, 11, 15));//年月日
		
		assertThat(actualResult).isEqualTo(GeneralDate.ymd(2019, 11, 15));

		GeneralDate actualResult2 = SettingExpirationDate.settingExp(ExpirationTime.THIS_MONTH,
				Optional.of(new GetTightSettingResult(11, new ClosureDate(15, false),
						new DatePeriod(GeneralDate.ymd(2019, 11, 1), GeneralDate.ymd(2019, 11, 30)))),
				GeneralDate.ymd(2019, 11, 16));
		assertThat(actualResult2).isEqualTo(GeneralDate.ymd(2019, 12, 15));

	}


	/*
	 * 　テストしたい内容
	 * 　　期限が年度末。
	 * 
	 * 　準備するデータ
	 * 　　月付チェックが必要　＞＝　期首月　→　来年
	 * 
	 * 　　月付チェックが必要　＜　期首月　→　今年度

	 * */
	@Test
	public void testThisYear() {

		GeneralDate actualResult = SettingExpirationDate.settingExp(
				ExpirationTime.END_OF_YEAR,
				Optional.of(new GetTightSettingResult(11, new ClosureDate(15, false),//// 期首月
						new DatePeriod(GeneralDate.ymd(2019, 11, 1),//日付
								GeneralDate.ymd(2019, 11, 30)))),//期間
				GeneralDate.ymd(2019, 11, 15));//年月日
		
		assertThat(actualResult).isEqualTo(GeneralDate.ymd(2020, 10, 30));

		GeneralDate actualResult2 = SettingExpirationDate.settingExp(ExpirationTime.END_OF_YEAR,
				Optional.of(new GetTightSettingResult(12, new ClosureDate(15, false),
						new DatePeriod(GeneralDate.ymd(2019, 11, 1), GeneralDate.ymd(2019, 11, 30)))),
				GeneralDate.ymd(2019, 11, 15));
		assertThat(actualResult2).isEqualTo(GeneralDate.ymd(2019, 11, 29));

	}

	/*
	 * 　テストしたい内容
	 * 　　期限が12か月以内。
	 * 
	 * 　準備するデータ
	 * 　　今月　→　休暇使用期限の月を加える
	 * 

	 * */
	@Test
	public void testOther() {

		GeneralDate actualResult = SettingExpirationDate.settingExp(
				ExpirationTime.SEVEN_MONTH,
				Optional.of(new GetTightSettingResult(11, new ClosureDate(15, false),//// 期首月
						new DatePeriod(GeneralDate.ymd(2019, 11, 1),//日付
								GeneralDate.ymd(2019, 11, 30)))),//期間
				GeneralDate.ymd(2019, 11, 15));//年月日
		
		assertThat(actualResult).isEqualTo(GeneralDate.ymd(2020, 6, 15));

	}
}
