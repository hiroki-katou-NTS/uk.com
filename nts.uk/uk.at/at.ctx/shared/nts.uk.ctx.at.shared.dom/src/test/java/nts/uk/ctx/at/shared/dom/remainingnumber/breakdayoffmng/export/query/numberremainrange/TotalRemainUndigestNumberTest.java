package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

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
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.TotalRemainUndigestNumber.RemainUndigestResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

@RunWith(JMockit.class)
public class TotalRemainUndigestNumberTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private TotalRemainUndigestNumber.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * 　テストしたい内容
	 * 　　残数と未消化数を集計する、
	 * 
	 * 　準備するデータ
	 * 　　　代休の設定がない
	 * 　　　→　残数は増加できません（EA not catch）
	 * */
	@Test
	public void testSubstitutionHolidayOutputNull() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				DaikyuFurikyuHelper.createDetail(true, //代休
						OccurrenceDigClass.OCCURRENCE,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 3)), //年月日
						GeneralDate.ymd(2019, 11, 4),//期限日
						"a1", 
						1.0,480,//発生数
						1.0, 0),//未相殺数

				DaikyuFurikyuHelper.createDetail(true, //代休
						OccurrenceDigClass.DIGESTION,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 3)), //年月日
						null,//期限日
						"a2", 
						1.0,480,//発生数
						1.0, 120)//未相殺数
		);
		
		new Expectations() {
			{

				require.findEmploymentHistory(anyString, SID, (GeneralDate) any);
				result = Optional.empty();
			}

		};
		RemainUndigestResult resultActual = TotalRemainUndigestNumber.process(require, CID, SID,
				GeneralDate.ymd(2020, 11, 30), lstAccAbse, false);
		RemainUndigestResult resultExpect = new RemainUndigestResult(-1.0, -120, 0d, 0);
		assertRemainUndigestResult(resultActual, resultExpect);
	}

	/*
	 * 　テストしたい内容
	 * 　　残数と未消化数を集計する、ーーTinh so ngay nghi con lai, so ngay het han
	 * 　　時間代休管理と期限切れの場合、相殺済みできません、未消化時間 に追加
	 *         ーーKhong the bu trong khi qua han、thêm vào thoi gian qua han
	 * 
	 * 　準備するデータ
	 * 　　　時間代休管理です
	 * 　　　
	 * 　　　休出日が期限切れです
	 * 　　　　
	 * */
	@Test
	public void testSubstitutionHolidayOutNoNull() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				DaikyuFurikyuHelper.createDetail(true, //代休
						OccurrenceDigClass.OCCURRENCE,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 4)), //年月日
						GeneralDate.ymd(2019, 12, 8),//期限日
						"a3", 
						1.0,480,//発生数
						0.0, 120),//未相殺数

				DaikyuFurikyuHelper.createDetail(true, //代休
						OccurrenceDigClass.DIGESTION,//発生消化区分
						Optional.empty(), //年月日
						null,//期限日
						"a4", 
						1.0,480,//発生数
						0.0, 120),//未相殺数
				DaikyuFurikyuHelper.createDetail(true, //代休
						OccurrenceDigClass.DIGESTION,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 4)), //年月日
						null,//期限日
						"a5", 
						1.0,480,//発生数
						0.0, 0),//未相殺数

				DaikyuFurikyuHelper.createDetail(true, //代休
						OccurrenceDigClass.OCCURRENCE,//発生消化区分
						Optional.empty(), //年月日
						GeneralDate.ymd(2019, 12, 8),//期限日
						"a6", 
						1.0,480,//発生数
						0.0, 0)//未相殺数
		);

		new Expectations() {
			{

				require.findEmploymentHistory(anyString, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.findComLeavEmpSet(anyString, anyString);
				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.YES, ManageDistinct.YES,
						anyString);
			}

		};
		RemainUndigestResult resultActual = TotalRemainUndigestNumber.process(require, CID, SID,
				GeneralDate.ymd(2020, 11, 30), lstAccAbse, false);
		RemainUndigestResult resultExpect = new RemainUndigestResult(0d, -120, 0d, 120);
		assertRemainUndigestResult(resultActual, resultExpect);
	}

	/*
	 * 　テストしたい内容
	 * 　　残数と未消化数を集計する、ーーTinh so ngay nghi con lai, so ngay het han
	 * 　　時間代休管理がないと期限切れの場合、相殺済みできません、未消化時間と未消化日数 に追加
	 *         ーーKhong the bu trong khi qua han、thêm vào thoi gian qua han、so ngay qua han
	 * 
	 * 　準備するデータ
	 * 　　　時間代休管理がない
	 * 　　　
	 * 　　　休出日が期限切れです
	 * 　　　　
	 * */
	@Test
	public void testNumberHalfDay() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(DaikyuFurikyuHelper.createDetail(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				GeneralDate.ymd(2019, 12, 8), // 期限日
				"a3", 1.0, 480, // 発生数
				0.5, 120), // 未相殺数
				DaikyuFurikyuHelper.createDetail(true, // 代休
						OccurrenceDigClass.DIGESTION, // 発生消化区分
						Optional.empty(), // 年月日
						null, // 期限日
						"a4", 1.0, 480, // 発生数
						1.0, 120), // 未相殺数
				DaikyuFurikyuHelper.createDetail(true, // 代休
						OccurrenceDigClass.DIGESTION, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 4)), // 年月日
						null, // 期限日
						"a5", 1.0, 480, // 発生数
						1.0, 120), // 未相殺数

				DaikyuFurikyuHelper.createDetail(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.empty(), // 年月日
						GeneralDate.ymd(2019, 12, 8), // 期限日
						"a6", 1.0, 480, // 発生数
						1.0, 480), // 未相殺数
				DaikyuFurikyuHelper.createDetail(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 10)), // 年月日
						GeneralDate.ymd(2019, 6, 8), // 期限日
						"a7", 1.0, 480, // 発生数
						1.0, 120), // 未相殺数
				DaikyuFurikyuHelper.createDetail(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 6, 8)), // 年月日
						GeneralDate.ymd(2019, 4, 11), // 期限日
						"a8", 1.0, 480, // 発生数
						0.5, 0)// 未相殺数
		);
		
		new Expectations() {
			{

				require.findEmploymentHistory(anyString, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.findComLeavEmpSet(anyString, anyString);
				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.NO, ManageDistinct.NO,
						anyString);
			}

		};
		RemainUndigestResult resultActual = TotalRemainUndigestNumber.process(require, CID, SID,
				GeneralDate.ymd(2020, 11, 30), lstAccAbse, false);
		RemainUndigestResult resultExpect = new RemainUndigestResult(-2.0, -240, 3.0, 1440);
		assertRemainUndigestResult(resultActual, resultExpect);
	}

	/*
	 * 　テストしたい内容
	 * 　　残数と未消化数を集計する、ーーTinh so ngay nghi con lai, so ngay het han
	 * 　　時間代休管理がないと期限切れてないの場合、相殺済みできる、残時間数と残日数 に追加
	 *         ーーco the bu trong khi khong qua han, bu ca thoi gian va ngay
	 * 
	 * 　準備するデータ
	 * 　　　時間代休管理がない
	 * 　　　
	 * 　　　期限切れてない
	 * 　　　　
	 * */
	@Test
	public void testDeadlineAfterDate() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(DaikyuFurikyuHelper.createDetail(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				GeneralDate.ymd(2020, 12, 8), // 期限日
				"a3", 1.0, 480, // 発生数
				0.5, 240), // 未相殺数
				DaikyuFurikyuHelper.createDetail(true, // 代休
						OccurrenceDigClass.DIGESTION, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 4)), // 年月日
						null, // 期限日
						"a5", 1.0, 480, // 発生数
						1.0, 480), // 未相殺数
				DaikyuFurikyuHelper.createDetail(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 10)), // 年月日
						GeneralDate.ymd(2020, 12, 8), // 期限日
						"a7", 1.0, 480, // 発生数
						1.0, 480)// 未相殺数
		);
		
		new Expectations() {
			{

				require.findEmploymentHistory(anyString, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.findComLeavEmpSet(anyString, anyString);
				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.NO, ManageDistinct.NO,
						anyString);
			}

		};
		RemainUndigestResult resultActual = TotalRemainUndigestNumber.process(require, CID, SID,
				GeneralDate.ymd(2020, 11, 30), lstAccAbse, false);
		RemainUndigestResult resultExpect = new RemainUndigestResult(0.5, 240, 0.0, 0);
		assertRemainUndigestResult(resultActual, resultExpect);
	}

	/*
	 * 　テストしたい内容
	 * 　　残数と未消化数を集計する、ーーTinh so ngay nghi con lai, so ngay het han
	 * 　　時間代休管理
	 * 　　　　　　　　　期限切れてない　→残時間数と残日数 に追加
	 * 　　　　　　　　　期限切れ　→　未消化時間 に追加
	 * 
	 * 　準備するデータ
	 * 　　　時間代休管理がある
	 * 　　　
	 * 　　　期限切れてない、
	 * 　　　期限切れて
	 * 　　　　
	 * */
	@Test
	public void testManagerTime() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(DaikyuFurikyuHelper.createDetail(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				GeneralDate.ymd(2019, 12, 8), // 期限日
				"a3", 1.0, 480, // 発生数
				1.0, 480), // 未相殺数
				DaikyuFurikyuHelper.createDetail(true, // 代休
						OccurrenceDigClass.DIGESTION, // 発生消化区分
						Optional.empty(), // 年月日
						null, // 期限日
						"a5", 1.0, 480, // 発生数
						1.0, 480), // 未相殺数
				DaikyuFurikyuHelper.createDetail(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
						GeneralDate.ymd(2020, 12, 8), // 期限日
						"a7", 1.0, 480, // 発生数
						0.5, 240)// 未相殺数
		);
		
		new Expectations() {
			{

				require.findEmploymentHistory(anyString, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.findComLeavEmpSet(anyString, anyString);
				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.NO, ManageDistinct.YES,
						anyString);
			}

		};
		RemainUndigestResult resultActual = TotalRemainUndigestNumber.process(require, CID, SID,
				GeneralDate.ymd(2020, 11, 30), lstAccAbse, false);
		RemainUndigestResult resultExpect = new RemainUndigestResult(-0.5, -240, 1.0, 480);
		assertRemainUndigestResult(resultActual, resultExpect);
	}

	private void assertRemainUndigestResult(RemainUndigestResult resultActual, RemainUndigestResult resultExpect) {
		assertThat(resultActual.getRemainingDay()).isEqualTo(resultExpect.getRemainingDay());
		assertThat(resultActual.getRemainingTime()).isEqualTo(resultExpect.getRemainingTime());
		assertThat(resultActual.getUndigestDay()).isEqualTo(resultExpect.getUndigestDay());
		assertThat(resultActual.getUndigestTime()).isEqualTo(resultExpect.getUndigestTime());
	}

}
