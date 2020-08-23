package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsDaysRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

@RunWith(JMockit.class)
public class PrepareInfoBeginOfMonthTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private PrepareInfoBeginOfMonth.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * テストしたい内容
	 *　　確定データから「逐次発生の休暇明細」を作成
	 * 　　繰越数を計算する
	 * 準備するデータ
	 * 　　振休管理データがある
	 * 
　	 *　　振出管理データがある
	 * 
	 * 
	 */
	@Test
	public void test() {

		List<AccumulationAbsenceDetail> lstAccDetail = new ArrayList<>();

		new Expectations() {
			{

				//振休管理データ
				require.getByYmdUnOffset(CID, SID, (GeneralDate) any, anyDouble);
				result = Arrays.asList(
						createSubMagData("a1", 
								GeneralDate.ymd(2019, 11, 30), //振休日
								1.0),//未相殺日数
						createSubMagData("a2",
								GeneralDate.ymd(2019, 11, 29),//振休日
								1.0),//未相殺日数
						createSubMagData("a3",
								GeneralDate.ymd(2019, 11, 20),//振休日
								1.0));//未相殺日

				require.getByUnUseState(CID, SID, (GeneralDate) any, 0, DigestionAtr.UNUSED);
				result = Arrays.asList(createPayout("a4", 
						GeneralDate.ymd(2019, 10, 28), //振出日
						1.0),// 未使用日数	
						createPayout("a5", 
								GeneralDate.ymd(2019, 10, 25), //振出日
								1.0),// 未使用日数	
						createPayout("a6",
								GeneralDate.ymd(2019, 10, 27),//振出日
								1.0),// 未使用日数	
						createPayout("a7", 
								GeneralDate.ymd(2019, 12, 27), //振出日
								1.0),// 未使用日数	
						createPayout("a8", 
								GeneralDate.ymd(2019, 10, 25), //振出日
								1.0));// 未使用日数	

			}
		};

		AbsDaysRemain resultActual = PrepareInfoBeginOfMonth.prepare(require, CID, SID, GeneralDate.ymd(2019, 11, 01),
				GeneralDate.ymd(2019, 11, 30), false, lstAccDetail,
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		//残日数
		assertThat(resultActual.getRemainDays()).isEqualTo(2.0);

		//未消化日数
		assertThat(resultActual.getUnDigestedDays()).isEqualTo(0.0);

	}
	
	private SubstitutionOfHDManagementData createSubMagData(String id, GeneralDate date, Double remainDay) {
		return new SubstitutionOfHDManagementData(id, CID, SID,
				new CompensatoryDayoffDate(date == null, Optional.ofNullable(date)),
				new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(1.0));
	}

	private  PayoutManagementData createPayout(String id, GeneralDate date, Double unUseDay) {
		return new PayoutManagementData(id, CID, SID, date == null,
				date, GeneralDate.max(), HolidayAtr.PUBLIC_HOLIDAY.value, 1.0,
				unUseDay, 0);
	}
}
