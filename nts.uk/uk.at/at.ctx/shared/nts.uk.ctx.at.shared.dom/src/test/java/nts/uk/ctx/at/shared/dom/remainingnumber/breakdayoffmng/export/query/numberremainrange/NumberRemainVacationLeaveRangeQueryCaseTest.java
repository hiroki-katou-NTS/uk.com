package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

@RunWith(JMockit.class)
public class NumberRemainVacationLeaveRangeQueryCaseTest {

//	private static String CID = "000000000000-0117";
//
//	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";
//
//	@Injectable
//	private NumberRemainVacationLeaveRangeQuery.Require require;

	// 代休のみ4日
	// 2019,11, 4 2019, 11, 5 2019, 11, 14 2019, 11, 15

	@Test
	public void testCase1() {
//		List<InterimDayOffMng> dayOffMng = Arrays.asList(
//				DaikyuFurikyuHelper.createDayOff("d1", 0, 1.0),
//				DaikyuFurikyuHelper.createDayOff("d2", 0, 1.0),
//				DaikyuFurikyuHelper.createDayOff("d3", 0, 1.0),
//				DaikyuFurikyuHelper.createDayOff("d4", 0, 1.0));
//
//		List<InterimRemain> interimMng = Arrays.asList(
//				DaikyuFurikyuHelper.createRemain("d1", GeneralDate.ymd(2019, 11, 4),
//						CreateAtr.SCHEDULE, RemainType.BREAK),
//				DaikyuFurikyuHelper.createRemain("d2", GeneralDate.ymd(2019, 11, 5),
//						CreateAtr.RECORD, RemainType.BREAK),
//				DaikyuFurikyuHelper.createRemain("d3", GeneralDate.ymd(2019, 11, 14),
//						CreateAtr.RECORD, RemainType.BREAK),
//				DaikyuFurikyuHelper.createRemain("d4", GeneralDate.ymd(2019, 11, 15),
//						CreateAtr.RECORD, RemainType.BREAK));
//
//		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
//				true,//モード 
//				GeneralDate.ymd(2019, 11, 30), //画面表示日
//				true, //上書きフラグ
//				interimMng, new ArrayList<>(), dayOffMng,//暫定残数管理データ
//				Optional.empty(),//前回代休の集計結果
//				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ
//		
//		new Expectations() {
//			{
//
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(
//						new EmploymentHistShareImport(SID, "02",
//								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
//						new EmploymentHistShareImport(SID, "00",
//								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
//			}
//
//		};
//
//		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
//				.getBreakDayOffMngInPeriod(require, inputParam);
//
//		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
//				new VacationDetails(new ArrayList<>()), new ReserveLeaveRemainingDayNumber(-4.0),
//				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(4.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0), Arrays.asList(DayOffError.DAYERROR),
//				Finally.of(GeneralDate.ymd(2020, 11, 01)), new ArrayList<>());
//		NumberRemainVacationLeaveRangeQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation()).isEqualTo(new ArrayList<>());

	}

	// 暫定休出のみ4日取得する
	// 休出日 11/2/2019 11/3/2019 11/9/2019 11/10/2019
//	@Test
//	public void testCase2() {
//		List<InterimBreakMng> breakMng = Arrays.asList(
//				new InterimBreakMng("d1", new AttendanceTime(480),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//						new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0)),
//
//				new InterimBreakMng("d2", new AttendanceTime(480),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//						new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0)),
//
//				new InterimBreakMng("d3", new AttendanceTime(480),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//						new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0)),
//
//				new InterimBreakMng("d4", new AttendanceTime(480),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//						new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0)));
//
//		List<InterimRemain> interimMng = Arrays.asList(
//				DaikyuFurikyuHelper.createRemain("d1", GeneralDate.ymd(2019, 11, 2),
//						CreateAtr.SCHEDULE, RemainType.BREAK),
//				DaikyuFurikyuHelper.createRemain("d2", GeneralDate.ymd(2019, 11, 3),
//						CreateAtr.RECORD, RemainType.BREAK ),
//				DaikyuFurikyuHelper.createRemain("d3", GeneralDate.ymd(2019, 11, 9),
//						CreateAtr.RECORD, RemainType.BREAK),
//				DaikyuFurikyuHelper.createRemain("d4", GeneralDate.ymd(2019, 11, 10),
//						CreateAtr.RECORD, RemainType.BREAK));
//
//		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
//				true,//モード 
//				GeneralDate.ymd(2019, 11, 30), //画面表示日
//				true, //上書きフラグ
//				interimMng, breakMng, new ArrayList<>(),//暫定残数管理データ
//				Optional.empty(),//前回代休の集計結果
//				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ
//
//		new Expectations() {
//			{
//
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(
//						new EmploymentHistShareImport(SID, "02",
//								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
//						new EmploymentHistShareImport(SID, "00",
//								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
////				require.getClosureDataByEmployee(SID, (GeneralDate) any);
////				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();
//
////				require.getFirstMonth(CID);
////				result = new CompanyDto(11);
//
//			}
//
//		};
//
//		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
//				.getBreakDayOffMngInPeriod(require, inputParam);
//
//
//		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
//				new VacationDetails(new ArrayList<>()), new ReserveLeaveRemainingDayNumber(4.0),
//				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(4.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0), Arrays.asList(),
//				Finally.of(GeneralDate.ymd(2020, 11, 01)), new ArrayList<>());
//		NumberRemainVacationLeaveRangeQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation()).isEqualTo(new ArrayList<>());
//
//	}
//
//	// 3 暫定代休、休出が同じ数
//	// 休出日 11/2/2019 11/3/2019 11/9/2019 11/10/2019
//	// 代休 2019,11, 4 2019, 11, 5 2019, 11, 14 2019, 11, 15
//	@Test
//	public void testCase3() {
//
//		List<InterimDayOffMng> dayOffMng = Arrays.asList(
//				DaikyuFurikyuHelper.createDayOff("d5", 0, 1.0),
//				DaikyuFurikyuHelper.createDayOff("d6", 0, 1.0),
//				DaikyuFurikyuHelper.createDayOff("d7", 0, 1.0),
//				DaikyuFurikyuHelper.createDayOff("d8", 0, 1.0));
//
//		List<InterimBreakMng> breakMng = Arrays.asList(
//				new InterimBreakMng("d1", new AttendanceTime(480),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//						new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0)),
//
//				new InterimBreakMng("d2", new AttendanceTime(480),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//						new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0)),
//
//				new InterimBreakMng("d3", new AttendanceTime(480),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//						new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0)),
//
//				new InterimBreakMng("d4", new AttendanceTime(480),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//						new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0)));
//
//		List<InterimRemain> interimMng = Arrays.asList(
//				DaikyuFurikyuHelper.createRemain("d1", GeneralDate.ymd(2019, 11, 2),
//						CreateAtr.SCHEDULE, RemainType.BREAK),
//				DaikyuFurikyuHelper.createRemain("d2", GeneralDate.ymd(2019, 11, 3),
//						CreateAtr.RECORD, RemainType.BREAK),
//				DaikyuFurikyuHelper.createRemain("d3", GeneralDate.ymd(2019, 11, 9),
//						CreateAtr.RECORD, RemainType.BREAK),
//				DaikyuFurikyuHelper.createRemain("d4", GeneralDate.ymd(2019, 11, 10),
//						CreateAtr.RECORD, RemainType.BREAK),
//
//				DaikyuFurikyuHelper.createRemain("d5", GeneralDate.ymd(2019, 11, 4),
//						CreateAtr.SCHEDULE, RemainType.SUBHOLIDAY),
//				DaikyuFurikyuHelper.createRemain("d6", GeneralDate.ymd(2019, 11, 5),
//						CreateAtr.RECORD, RemainType.SUBHOLIDAY),
//				DaikyuFurikyuHelper.createRemain("d7", GeneralDate.ymd(2019, 11, 14),
//						CreateAtr.RECORD, RemainType.SUBHOLIDAY),
//				DaikyuFurikyuHelper.createRemain("d8", GeneralDate.ymd(2019, 11, 15),
//						CreateAtr.RECORD, RemainType.SUBHOLIDAY));
//
//		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
//				true,//モード 
//				GeneralDate.ymd(2019, 11, 30), //画面表示日
//				true, //上書きフラグ
//				interimMng, breakMng, dayOffMng,//暫定残数管理データ
//				Optional.empty(),//前回代休の集計結果
//				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ
//
//		new Expectations() {
//			{
//
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(
//						new EmploymentHistShareImport(SID, "02",
//								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
//						new EmploymentHistShareImport(SID, "00",
//								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
////				require.getClosureDataByEmployee(SID, (GeneralDate) any);
////				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();
//
////				require.getFirstMonth(CID);
////				result = new CompanyDto(11);
//
//			}
//
//		};
//
//		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
//				.getBreakDayOffMngInPeriod(require, inputParam);
//
//
//		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
//				new VacationDetails(new ArrayList<>()), new ReserveLeaveRemainingDayNumber(0.0),
//				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(4.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(4.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0), Arrays.asList(),
//				Finally.of(GeneralDate.ymd(2020, 11, 01)), new ArrayList<>());
//		NumberRemainVacationLeaveRangeQueryTest.assertData(resultActual, resultExpected);
//
//		assertThat(resultActual.getLstSeqVacation())
//				.extracting(x -> x.getOutbreakDay(), x -> x.getDateOfUse(), x -> x.getDayNumberUsed(),
//						x -> x.getTargetSelectionAtr())
//				.containsExactly(
//						Tuple.tuple(GeneralDate.ymd(2019, 11, 2), GeneralDate.ymd(2019, 11, 4),
//								new ReserveLeaveRemainingDayNumber(1.0), TargetSelectionAtr.AUTOMATIC),
//						Tuple.tuple(GeneralDate.ymd(2019, 11, 3), GeneralDate.ymd(2019, 11, 5),
//								new ReserveLeaveRemainingDayNumber(1.0), TargetSelectionAtr.AUTOMATIC),
//						Tuple.tuple(GeneralDate.ymd(2019, 11, 9), GeneralDate.ymd(2019, 11, 14),
//								new ReserveLeaveRemainingDayNumber(1.0), TargetSelectionAtr.AUTOMATIC),
//						Tuple.tuple(GeneralDate.ymd(2019, 11, 10), GeneralDate.ymd(2019, 11, 15),
//								new ReserveLeaveRemainingDayNumber(1.0), TargetSelectionAtr.AUTOMATIC));
//
//	}
//
//	// 4 暫定代休、休出を取得 但し代休が半日
//	// 休出日 11/2/2019 11/3/2019 (日数 = 1)
//	// 代休 2019,11, 4 2019, 11, 5 (日数 = 0.5)
//	@Test
//	public void testCase4() {
//
//		List<InterimDayOffMng> dayOffMng = Arrays.asList(
//				DaikyuFurikyuHelper.createDayOff("d5", 0, 0.5),
//				DaikyuFurikyuHelper.createDayOff("d6", 0, 0.5));
//		
//		List<InterimBreakMng> breakMng = Arrays.asList(
//				new InterimBreakMng("d1", new AttendanceTime(480),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//						new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0)),
//
//				new InterimBreakMng("d2", new AttendanceTime(480),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//						new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0)));
//
//		List<InterimRemain> interimMng = Arrays.asList(
//				DaikyuFurikyuHelper.createRemain("d1", GeneralDate.ymd(2019, 11, 2),
//						CreateAtr.SCHEDULE, RemainType.BREAK),
//				DaikyuFurikyuHelper.createRemain("d2", GeneralDate.ymd(2019, 11, 3),
//						CreateAtr.RECORD, RemainType.BREAK),
//
//				DaikyuFurikyuHelper.createRemain("d5", GeneralDate.ymd(2019, 11, 4),
//						CreateAtr.SCHEDULE, RemainType.SUBHOLIDAY),
//				DaikyuFurikyuHelper.createRemain("d6", GeneralDate.ymd(2019, 11, 5),
//						CreateAtr.RECORD, RemainType.SUBHOLIDAY));
//
//		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
//				true,//モード 
//				GeneralDate.ymd(2019, 11, 30), //画面表示日
//				true, //上書きフラグ
//				interimMng, breakMng, dayOffMng,//暫定残数管理データ
//				Optional.empty(),//前回代休の集計結果
//				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ
//
//		new Expectations() {
//			{
//
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(
//						new EmploymentHistShareImport(SID, "02",
//								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
//						new EmploymentHistShareImport(SID, "00",
//								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
////				require.getClosureDataByEmployee(SID, (GeneralDate) any);
////				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();
//
////				require.getFirstMonth(CID);
////				result = new CompanyDto(11);
//
//			}
//
//		};
//
//		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
//				.getBreakDayOffMngInPeriod(require, inputParam);
//
//		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
//				new VacationDetails(new ArrayList<>()), new ReserveLeaveRemainingDayNumber(1.0),
//				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(1.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(2.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0), Arrays.asList(),
//				Finally.of(GeneralDate.ymd(2020, 11, 01)), new ArrayList<>());
//		NumberRemainVacationLeaveRangeQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation())
//				.extracting(x -> x.getOutbreakDay(), x -> x.getDateOfUse(), x -> x.getDayNumberUsed(),
//						x -> x.getTargetSelectionAtr())
//				.containsExactly(
//						Tuple.tuple(GeneralDate.ymd(2019, 11, 2), GeneralDate.ymd(2019, 11, 4),
//								new ReserveLeaveRemainingDayNumber(0.5), TargetSelectionAtr.AUTOMATIC),
//						Tuple.tuple(GeneralDate.ymd(2019, 11, 2), GeneralDate.ymd(2019, 11, 5),
//								new ReserveLeaveRemainingDayNumber(0.5), TargetSelectionAtr.AUTOMATIC));
//
//	}
//
//	// 5 暫定代休、休出を取得 但し休出が半日
//	// 休出日 11/2/2019 11/3/2019 (日数 = 0.5)
//	// 代休 2019,11, 4 2019, 11, 5 (日数 = 1)
//	@Test
//	public void testCase5() {
//
//		List<InterimDayOffMng> dayOffMng = Arrays.asList(
//				DaikyuFurikyuHelper.createDayOff("d5", 0,1.0),
//				DaikyuFurikyuHelper.createDayOff("d6",0, 1.0));
//
//		List<InterimBreakMng> breakMng = Arrays.asList(
//				new InterimBreakMng("d1", new AttendanceTime(0),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(0.5),
//						new AttendanceTime(0), new UnUsedTime(0), new UnUsedDay(0.5)),
//
//				new InterimBreakMng("d2", new AttendanceTime(0),
//						GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(0.5),
//						new AttendanceTime(0), new UnUsedTime(0), new UnUsedDay(0.5)));
//
//		List<InterimRemain> interimMng = Arrays.asList(
//				DaikyuFurikyuHelper.createRemain("d1", GeneralDate.ymd(2019, 11, 2),
//						CreateAtr.SCHEDULE, RemainType.BREAK),
//				DaikyuFurikyuHelper.createRemain("d2", GeneralDate.ymd(2019, 11, 3),
//						CreateAtr.RECORD, RemainType.BREAK),
//
//				DaikyuFurikyuHelper.createRemain("d5", GeneralDate.ymd(2019, 11, 4),
//						CreateAtr.SCHEDULE, RemainType.SUBHOLIDAY),
//				DaikyuFurikyuHelper.createRemain("d6", GeneralDate.ymd(2019, 11, 5),
//						CreateAtr.RECORD, RemainType.SUBHOLIDAY));
//
//		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
//				true,//モード 
//				GeneralDate.ymd(2019, 11, 30), //画面表示日
//				true, //上書きフラグ
//				interimMng, breakMng, dayOffMng,//暫定残数管理データ
//				Optional.empty(),//前回代休の集計結果
//				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ
//
//		new Expectations() {
//			{
//
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(
//						new EmploymentHistShareImport(SID, "02",
//								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
//						new EmploymentHistShareImport(SID, "00",
//								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
////				require.getClosureDataByEmployee(SID, (GeneralDate) any);
////				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();
//
////				require.getFirstMonth(CID);
////				result = new CompanyDto(11);
//
//			}
//
//		};
//
//		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
//				.getBreakDayOffMngInPeriod(require, inputParam);
//
//
//		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
//				new VacationDetails(new ArrayList<>()), new ReserveLeaveRemainingDayNumber(-1.0),
//				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(2.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(1.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0), Arrays.asList(DayOffError.DAYERROR),
//				Finally.of(GeneralDate.ymd(2020, 11, 01)), new ArrayList<>());
//		NumberRemainVacationLeaveRangeQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation())
//				.extracting(x -> x.getOutbreakDay(), x -> x.getDateOfUse(), x -> x.getDayNumberUsed(),
//						x -> x.getTargetSelectionAtr())
//				.containsExactly(
//						Tuple.tuple(GeneralDate.ymd(2019, 11, 2), GeneralDate.ymd(2019, 11, 4),
//								new ReserveLeaveRemainingDayNumber(1.0), TargetSelectionAtr.AUTOMATIC),
//						Tuple.tuple(GeneralDate.ymd(2019, 11, 3), GeneralDate.ymd(2019, 11, 4),
//								new ReserveLeaveRemainingDayNumber(0.5), TargetSelectionAtr.AUTOMATIC));
//
//	}
//
//	// 6 暫定半日代休取得、確定休出1日残っているが2019/11/14に期限切れになる
//	// 代休取得日 2019/11/14 2019/11/15 (日数 = 0.5)
//	// 確定休出 2019/8/14, 期限日2019/11/14(日数 = 1)
//	@Test
//	public void testCase6() {
//
//		List<InterimDayOffMng> dayOffMng = Arrays.asList(
//				DaikyuFurikyuHelper.createDayOff("d5", 0, 0.5),
//				DaikyuFurikyuHelper.createDayOff("d6", 0, 0.5));
//
//
//		List<InterimRemain> interimMng = Arrays.asList(
//
//				DaikyuFurikyuHelper.createRemain("d5", GeneralDate.ymd(2019, 11, 14),
//						CreateAtr.SCHEDULE, RemainType.SUBHOLIDAY),
//				DaikyuFurikyuHelper.createRemain("d6", GeneralDate.ymd(2019, 11, 15),
//						CreateAtr.RECORD, RemainType.SUBHOLIDAY));
//
//		List<LeaveManagementData> leavFix = Arrays.asList(new LeaveManagementData(
//				"d1", CID, SID, true, GeneralDate.ymd(2019, 8, 14),
//				GeneralDate.ymd(2019, 11, 14), 1.0, 0, 1.0, 0, DigestionAtr.UNUSED.value, 0, 0));
//
//		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
//				true,//モード 
//				GeneralDate.ymd(2019, 11, 30), //画面表示日
//				true, //上書きフラグ
//				interimMng, new ArrayList<>(), dayOffMng,//暫定残数管理データ
//				Optional.empty(),//前回代休の集計結果
//				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ
//
//		new Expectations() {
//			{
//
//				require.getBySidYmd(CID, SID, (GeneralDate) any, DigestionAtr.UNUSED);
//				result = leavFix;
//
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(new EmploymentHistShareImport(SID, "00",
//						new DatePeriod(GeneralDate.ymd(2010, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
//				require.findComLeavEmpSet(CID, "00");
//				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.YES, ManageDistinct.NO,
//						"00");
//
//			}
//
//		};
//
//		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
//				.getBreakDayOffMngInPeriod(require, inputParam);
//
//		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
//				new VacationDetails(new ArrayList<>()), new ReserveLeaveRemainingDayNumber(-0.5),
//				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(1.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(1.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.5), new RemainingMinutes(0), Arrays.asList(DayOffError.DAYERROR),
//				Finally.of(GeneralDate.ymd(2020, 11, 01)), new ArrayList<>());
//		NumberRemainVacationLeaveRangeQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation())
//				.extracting(x -> x.getOutbreakDay(), x -> x.getDateOfUse(), x -> x.getDayNumberUsed(),
//						x -> x.getTargetSelectionAtr())
//				.containsExactly(Tuple.tuple(GeneralDate.ymd(2019, 8, 14), GeneralDate.ymd(2019, 11, 14),
//						new ReserveLeaveRemainingDayNumber(0.5), TargetSelectionAtr.AUTOMATIC));
//
//	}
//
//	// 7 暫定1日休出し、暫定1日代休取得 確定休出0.5日（0.5日紐づけ済み）が残っている
//	// 代休取得日 2019/11/15 (日数 1)
//	// 休出日 2019/11/10 (日数 1)
//	// 確定休出 2019/10/14 (日数 0.5) 期限日 2020/1/14
//	@Test
//	public void testCase7() {
//
//		List<InterimDayOffMng> dayOffMng = Arrays.asList(DaikyuFurikyuHelper.createDayOff("d5",0, 1.0));
//
//		List<InterimBreakMng> breakMng = Arrays.asList(new InterimBreakMng("d1",
//				new AttendanceTime(480), GeneralDate.max().addDays(-1), new OccurrenceTime(0), new OccurrenceDay(1.0),
//				new AttendanceTime(240), new UnUsedTime(0), new UnUsedDay(1.0))
//
//		);
//
//		List<InterimRemain> interimMng = Arrays.asList(DaikyuFurikyuHelper.createRemain("d1",
//				GeneralDate.ymd(2019, 11, 10), CreateAtr.SCHEDULE, RemainType.BREAK),
//
//				DaikyuFurikyuHelper.createRemain("d5", GeneralDate.ymd(2019, 11, 4),
//						CreateAtr.SCHEDULE, RemainType.SUBHOLIDAY));
//
//		List<LeaveManagementData> leavFix = Arrays.asList(new LeaveManagementData(
//				"d9", CID, SID, false, GeneralDate.ymd(2019, 10, 14),
//				GeneralDate.ymd(2020, 1, 14), 0.5, 0, 0.5, 0, DigestionAtr.UNUSED.value, 0, 0));
//
//		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
//				new DatePeriod(GeneralDate.ymd(2019, 4, 01), GeneralDate.ymd(2020, 3, 31)), //集計開始日, 集計終了日
//				true,//モード 
//				GeneralDate.ymd(2019, 11, 30), //画面表示日
//				true, //上書きフラグ
//				interimMng, breakMng, dayOffMng,//暫定残数管理データ
//				Optional.empty(),//前回代休の集計結果
//				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ
//
//		new Expectations() {
//			{
//
//				require.getBySidYmd(CID, SID, (GeneralDate) any, DigestionAtr.UNUSED);
//				result = leavFix;
//
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(
//						new EmploymentHistShareImport(SID, "02",
//								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
//						new EmploymentHistShareImport(SID, "00",
//								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
////				require.getClosureDataByEmployee(SID, (GeneralDate) any);
////				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();
//
////				require.getFirstMonth(CID);
////				result = new CompanyDto(11);
//
//			}
//
//		};
//
//		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
//				.getBreakDayOffMngInPeriod(require, inputParam);
//
//		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
//				new VacationDetails(new ArrayList<>()), new ReserveLeaveRemainingDayNumber(0.5),
//				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(1.0), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(1.5), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(0.5), new RemainingMinutes(0),
//				new ReserveLeaveRemainingDayNumber(.0), new RemainingMinutes(0), Arrays.asList(DayOffError.PREFETCH_ERROR),
//				Finally.of(GeneralDate.ymd(2020, 04, 01)), new ArrayList<>());
//		NumberRemainVacationLeaveRangeQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation())
//				.extracting(x -> x.getOutbreakDay(), x -> x.getDateOfUse(), x -> x.getDayNumberUsed(),
//						x -> x.getTargetSelectionAtr())
//				.containsExactly(Tuple.tuple(GeneralDate.ymd(2019, 10, 14), GeneralDate.ymd(2019, 11, 4),
//						new ReserveLeaveRemainingDayNumber(1.0), TargetSelectionAtr.AUTOMATIC));
//
//	}
}