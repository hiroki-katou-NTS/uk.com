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
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.PauseError;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
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
		CompenLeaveAggrResult compenLeaveAggrResult = new CompenLeaveAggrResult(
				new VacationDetails(Collections.emptyList()), new ReserveLeaveRemainingDayNumber(1.0),
				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(1.0),
				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(1.0),
				Finally.of(GeneralDate.ymd(2019, 12, 21)), Collections.emptyList(), Collections.emptyList());

		new Expectations() {
			{

				require.getByYmdUnOffset(CID, SID, (GeneralDate) any, 0);
				result = Arrays.asList(
						new SubstitutionOfHDManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e133", CID, SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 30))),
								new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(1.0)),
						new SubstitutionOfHDManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e134", CID, SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 29))),
								new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(1.0)),
						new SubstitutionOfHDManagementData("adda6a46-2cbe-48c8-85f8-c04ca554ea33", CID, SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 04))),
								new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(1.0)),
						new SubstitutionOfHDManagementData("adda6a46-2cbe-48c8-85f8-c04ca554a134", CID, SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 05))),
								new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(1.0)),
						new SubstitutionOfHDManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e134", CID, SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 20))),
								new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(-1.0)));

				require.getByUnUseState(CID, SID, (GeneralDate) any, 0, DigestionAtr.UNUSED);
				result = Arrays.asList(new PayoutManagementData("62d542c3-4b79-4bf3-bd39-7e7f06711ccc", CID, SID, false,
						GeneralDate.ymd(2019, 10, 28), GeneralDate.max(), HolidayAtr.PUBLIC_HOLIDAY.value, 1.0, 1.0, 0),
						new PayoutManagementData("62d542c3-4b79-4bf3-bd39-7e7f06711ccb", CID, SID, false,
								GeneralDate.ymd(2019, 10, 25), GeneralDate.max(), HolidayAtr.PUBLIC_HOLIDAY.value, 1.0,
								1.0, 0),
						new PayoutManagementData("62d542c3-4b79-4bf3-bd39-7e7f06711aaa", CID, SID, false,
								GeneralDate.ymd(2019, 10, 27), GeneralDate.max(), HolidayAtr.PUBLIC_HOLIDAY.value, 1.0,
								1.0, 0),
						new PayoutManagementData("62d542c3-4b79-4bf3-bd39-7e7f06711aaa", CID, SID, false,
								GeneralDate.ymd(2019, 12, 27), GeneralDate.max(), HolidayAtr.PUBLIC_HOLIDAY.value, 1.0,
								1.0, 0),
						new PayoutManagementData("62d542c3-4b79-4bf3-bd39-7e7f06711aaa", CID, SID, false,
								GeneralDate.ymd(2019, 10, 25), GeneralDate.max(), HolidayAtr.PUBLIC_HOLIDAY.value, 1.0,
								-1.0, 0),
						new PayoutManagementData("62d542c3-4b79-4bf3-bd39-7e7f06711aa6", CID, SID, false,
								GeneralDate.ymd(2019, 10, 25), GeneralDate.ymd(2019, 10, 05),
								HolidayAtr.PUBLIC_HOLIDAY.value, 1.0, -1.0, 0));

			}
		};

		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
				GeneralDate.ymd(2019, 11, 30), false, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
				Optional.of(compenLeaveAggrResult), Optional.empty(), Optional.empty(),
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);

		// @ConstructorProperties(value={"vacationDetails", "remainDay", "unusedDay",
		// "occurrenceDay", "dayUse", "carryoverDay", "nextDay", "lstSeqVacation",
		// "pError"})
		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(new VacationDetails(new ArrayList<>()),
				new ReserveLeaveRemainingDayNumber(0.0), new ReserveLeaveRemainingDayNumber(0.0),
				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(4.0),
				new ReserveLeaveRemainingDayNumber(0.0), Finally.of(GeneralDate.ymd(2020, 11, 1)), new ArrayList<>(),
				Arrays.asList());

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

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 10, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddff")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 11))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),

				new UnbalanceCompensation(new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 10, 14))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
						GeneralDate.ymd(2019, 12, 30), DigestionAtr.UNUSED, Optional.empty(), StatutoryAtr.PUBLIC));

		CompenLeaveAggrResult compenLeaveAggrResult = new CompenLeaveAggrResult(
				new VacationDetails(lstAccDetail), new ReserveLeaveRemainingDayNumber(1.0),
				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(1.0),
				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(1.0),
				Finally.of(GeneralDate.ymd(2019, 11, 01)), Collections.emptyList(), Collections.emptyList());

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

		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
				GeneralDate.ymd(2019, 11, 30), true, true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
				Optional.of(compenLeaveAggrResult), Optional.empty(), Optional.empty(),
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);

		// @ConstructorProperties(value={"vacationDetails", "remainDay", "unusedDay",
		// "occurrenceDay", "dayUse", "carryoverDay", "nextDay", "lstSeqVacation",
		// "pError"})
		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(new VacationDetails(new ArrayList<>()),
				new ReserveLeaveRemainingDayNumber(-2.0), new ReserveLeaveRemainingDayNumber(0.0),
				new ReserveLeaveRemainingDayNumber(0.0), new ReserveLeaveRemainingDayNumber(0.0),
				new ReserveLeaveRemainingDayNumber(1.0), Finally.of(GeneralDate.ymd(2020, 11, 1)), new ArrayList<>(),
				Arrays.asList(PauseError.PAUSEREMAINNUMBER));

		assertData(resultActual, resultExpected);

	}

	public static void assertData(CompenLeaveAggrResult resultActual, CompenLeaveAggrResult resultExpected) {

		assertThat(resultActual.getRemainDay().v()).isEqualTo(resultExpected.getRemainDay().v());
		assertThat(resultActual.getDayUse().v()).isEqualTo(resultExpected.getDayUse().v());
		assertThat(resultActual.getOccurrenceDay().v()).isEqualTo(resultExpected.getOccurrenceDay().v());
		assertThat(resultActual.getCarryoverDay().v()).isEqualTo(resultExpected.getCarryoverDay().v());
		assertThat(resultActual.getUnusedDay().v()).isEqualTo(resultExpected.getUnusedDay().v());
		assertThat(resultActual.getNextDay().get()).isEqualTo(resultExpected.getNextDay().get());
		assertThat(resultActual.getPError()).isEqualTo(resultExpected.getPError());

	}
}
