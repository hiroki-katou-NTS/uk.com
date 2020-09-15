package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.PauseError;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.DaikyuFurikyuHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

@RunWith(JMockit.class)
public class NumberCompensatoryLeavePeriodQueryTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private NumberCompensatoryLeavePeriodQuery.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	/**
	 * テストしたい内容
	 *　　 前回代休の集計結果がない
	 * 準備するデータ
	 *　　 確定データ
	 * 　　振休管理データがある
	 * 　　振出管理データがある
	 * 　　→振休の集計結果 (残日数, 使用日数)
	 */
	@Test
	public void testOptBeforeResultPresent() {
		
		
		new Expectations() {
			{

				require.getByYmdUnOffset(CID, SID, (GeneralDate) any, 0);
				result = Arrays.asList(
						createSubOfHD("a1", 
								GeneralDate.ymd(2019, 11, 30),// 振休日
								1.0),// 未相殺日数
						createSubOfHD("a2", 
								GeneralDate.ymd(2019, 11, 29), // 振休日
								1.0),// 未相殺日数
						createSubOfHD("a3", 
								GeneralDate.ymd(2019, 11, 04), // 振休日
								1.0),// 未相殺日数
						createSubOfHD("a4", 
								GeneralDate.ymd(2019, 11, 05), // 振休日
								1.0),// 未相殺日数
						createSubOfHD("a2", 
								GeneralDate.ymd(2019, 11, 20),// 振休日
								-1.0));// 未相殺日数

				require.getByUnUseState(CID, SID, (GeneralDate) any, 0, DigestionAtr.UNUSED);
				result = Arrays.asList(createPayoutMngData("a5",
						GeneralDate.ymd(2019, 10, 28),// 振出日
						GeneralDate.max(),// 使用期限日
						1.0),// 未使用日数
						createPayoutMngData("a6", GeneralDate.ymd(2019, 10, 25), GeneralDate.max(), 1.0),
						createPayoutMngData("a7", GeneralDate.ymd(2019, 10, 27), GeneralDate.max(), 1.0),
						createPayoutMngData("a7", GeneralDate.ymd(2019, 12, 27), GeneralDate.max(), 1.0),
						createPayoutMngData("a7", GeneralDate.ymd(2019, 10, 25), GeneralDate.max(), -1.0),
						createPayoutMngData("a8", GeneralDate.ymd(2019, 10, 25), GeneralDate.ymd(2019, 10, 05), -1.0));

			}
		};

		CompenLeaveAggrResult compenLeaveAggrResult = compenLeaveAggrResult(new ArrayList<>(), // 振出振休明細
				12.0,// 繰越日数
				GeneralDate.ymd(2019, 10, 01));// 前回集計期間の翌日
		
		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				false, //モード 
				false, // 上書きフラグ
				Optional.of(compenLeaveAggrResult));//暫定管理データ
		
		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);

		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(
				new VacationDetails(new ArrayList<>()),// 振出振休明細
				new ReserveLeaveRemainingDayNumber(0.0),// 残日数
				new ReserveLeaveRemainingDayNumber(0.0),// 未消化日数
				new ReserveLeaveRemainingDayNumber(1.0),// 発生日数
				new ReserveLeaveRemainingDayNumber(4.0),// 使用日数
				new ReserveLeaveRemainingDayNumber(0.0), // 繰越日数
				Finally.of(GeneralDate.ymd(2020, 11, 1)),// 前回集計期間の翌日
                new ArrayList<>(),// 逐次休暇の紐付け情報
				Arrays.asList());// 振休エラー

		assertData(resultActual, resultExpected);

	}

	/**
	 * テストしたい内容
	 *　　 前回代休の集計結果がある
	 * 準備するデータ
	 *　　 確定データ
	 * 　　→振休の集計結果 (残日数, 使用日数)
	 */
	@Test
	public void testCaseOther() {

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.DIGESTION, // 消化
				Optional.of(GeneralDate.ymd(2019, 10, 3)), // 年月日
				"a1", // 残数管理データID
				1.0, 0, // 発生
				1.0, 0// 未相殺
		), DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.DIGESTION, // 消化
				Optional.of(GeneralDate.ymd(2019, 4, 11)), // 年月日
				"a2", // 残数管理データID
				1.0, 0, // 発生
				1.0, 0// 未相殺
		), DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.of(GeneralDate.ymd(2019, 10, 14)), // 年月日
				"a3", // 残数管理データID
				1.0, 0, // 発生
				0.0, 0// 未相殺
		));
		
		CompenLeaveAggrResult compenLeaveAggrResult = compenLeaveAggrResult(lstAccDetail, // 振出振休明細
				12.0,// 繰越日数
				GeneralDate.ymd(2019, 11, 01));// 前回集計期間の翌日

		new Expectations() {
			{

				require.findByEmployeeIdOrderByStartDate(anyString);
				result = Arrays.asList(
						new EmploymentHistShareImport(SID, "02",
								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
						new EmploymentHistShareImport(SID, "00",
								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));

				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

			}
		};

		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				true, //モード 
				true, // 上書きフラグ
				Optional.of(compenLeaveAggrResult));//前回振休の集計結果
		
		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);
	
		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(
				new VacationDetails(new ArrayList<>()),// 振出振休明細
				new ReserveLeaveRemainingDayNumber(-2.0), // 残日数
				new ReserveLeaveRemainingDayNumber(0.0),// 未消化日数
				new ReserveLeaveRemainingDayNumber(0.0), // 発生日数
				new ReserveLeaveRemainingDayNumber(0.0),// 使用日数
				new ReserveLeaveRemainingDayNumber(12.0),// 繰越日数
				Finally.of(GeneralDate.ymd(2020, 11, 1)),// 前回集計期間の翌日
				new ArrayList<>(),
				Arrays.asList(PauseError.PAUSEREMAINNUMBER));

		assertData(resultActual, resultExpected);

	}

	public static void assertData(CompenLeaveAggrResult resultActual, CompenLeaveAggrResult resultExpected) {

		//残日数
		assertThat(resultActual.getRemainDay().v()).isEqualTo(resultExpected.getRemainDay().v());
		// 未消化日数
		assertThat(resultActual.getDayUse().v()).isEqualTo(resultExpected.getDayUse().v());
		// 発生日数
		assertThat(resultActual.getOccurrenceDay().v()).isEqualTo(resultExpected.getOccurrenceDay().v());
		// 繰越日数
		assertThat(resultActual.getCarryoverDay().v()).isEqualTo(resultExpected.getCarryoverDay().v());
		// 使用日数
		assertThat(resultActual.getUnusedDay().v()).isEqualTo(resultExpected.getUnusedDay().v());
		// 前回集計期間の翌日
		assertThat(resultActual.getNextDay().get()).isEqualTo(resultExpected.getNextDay().get());
		// 振休エラー
		assertThat(resultActual.getPError()).isEqualTo(resultExpected.getPError());

	}
	
	private SubstitutionOfHDManagementData createSubOfHD(String id, GeneralDate date, Double remainDay) {

		return new SubstitutionOfHDManagementData(id, CID, SID,
				new CompensatoryDayoffDate(date == null, Optional.ofNullable(date)), new ManagementDataDaysAtr(1.0),
				new ManagementDataRemainUnit(remainDay));
	}
	
	private PayoutManagementData createPayoutMngData(String id, GeneralDate dateExec, GeneralDate deadLine,
			Double unUse) {
		return new PayoutManagementData(id, CID, SID, dateExec == null, dateExec, deadLine,
				HolidayAtr.PUBLIC_HOLIDAY.value, 1.0, unUse, 0);
	}
	
	private CompenLeaveAggrResult compenLeaveAggrResult(List<AccumulationAbsenceDetail> lstAccDetail, Double carryDay, GeneralDate nextDay) {
		return new CompenLeaveAggrResult(new VacationDetails(lstAccDetail), new ReserveLeaveRemainingDayNumber(0.0),
				new ReserveLeaveRemainingDayNumber(0.0), new ReserveLeaveRemainingDayNumber(0.0),
				new ReserveLeaveRemainingDayNumber(0.0), new ReserveLeaveRemainingDayNumber(carryDay),
				Finally.of(nextDay), Collections.emptyList(), Collections.emptyList());
	}
}
