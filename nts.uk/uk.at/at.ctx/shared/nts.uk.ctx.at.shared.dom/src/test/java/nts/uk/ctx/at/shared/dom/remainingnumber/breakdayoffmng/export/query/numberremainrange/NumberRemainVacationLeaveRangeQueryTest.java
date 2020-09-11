package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUseGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnitGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.DeadlCheckMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureName;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@RunWith(JMockit.class)
public class NumberRemainVacationLeaveRangeQueryTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private NumberRemainVacationLeaveRangeQuery.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	// 前回代休の集計結果 not isPresent
	@Test
	public void testOptBeforeResultNoPresent() {
		List<InterimDayOffMng> dayOffMng = new ArrayList<InterimDayOffMng>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimRemain> interimMng = new ArrayList<>();

		BreakDayOffRemainMngRefactParam inputParam = new BreakDayOffRemainMngRefactParam(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), false,
				GeneralDate.ymd(2019, 11, 30), false, interimMng, Optional.empty(), Optional.empty(), breakMng,
				dayOffMng, Optional.empty(), new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		new Expectations() {
			{
				require.getBySidYmd(anyString, anyString, (GeneralDate) any);
				result = new ArrayList<>();

				require.getBySidYmd(anyString, anyString, (GeneralDate) any, (DigestionAtr) any);
				result = new ArrayList<>();

				require.getRemainBySidPriod(anyString, (DatePeriod) any, (RemainType) any);
				result = Arrays.asList(
						new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, GeneralDate.ymd(2019, 11, 5),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921ee", SID, GeneralDate.ymd(2019, 11, 8),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("876caf30-5a4d-47b7-8147-d646f74be08a", SID, GeneralDate.ymd(2019, 11, 9),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));

				require.getDayOffBySidPeriod(anyString, (DatePeriod) any);
				result = Arrays.asList(
						new InterimDayOffMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)),
						new InterimDayOffMng("62d542c3-4b79-4bf3-bd39-7e7f06711c34", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)),
						new InterimDayOffMng("077a8929-3df0-4fd6-859e-29e615a921ee", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)),
						new InterimDayOffMng("876caf30-5a4d-47b7-8147-d646f74be08a", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));

				require.getBreakDayOffMng(anyString, anyBoolean, (DataManagementAtr) any);
				result = new ArrayList<>();

				require.findByEmployeeIdOrderByStartDate(anyString);
				result = Arrays.asList(
						new EmploymentHistShareImport(SID, "02",
								new DatePeriod(GeneralDate.ymd(2000, 1, 1), GeneralDate.ymd(2010, 03, 31))),
						new EmploymentHistShareImport(SID, "00",
								new DatePeriod(GeneralDate.ymd(2010, 4, 30), GeneralDate.ymd(9999, 12, 31))));
			}

		};
		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
				.getBreakDayOffMngInPeriod(require, inputParam);

		List<AccumulationAbsenceDetail> lstAccDetailExpected = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554e132")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 5))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "62d542c3-4b79-4bf3-bd39-7e7f06711c34")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 8))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "077a8929-3df0-4fd6-859e-29e615a921ee")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 9))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "876caf30-5a4d-47b7-8147-d646f74be08a")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build());

		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
				new VacationDetails(lstAccDetailExpected), new ReserveLeaveRemainingDayNumber(-4.0),
				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(4.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0d), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0), Arrays.asList(DayOffError.DAYERROR),
				Finally.of(GeneralDate.ymd(2020, 11, 01)), new ArrayList<>());

		assertData(resultActual, resultExpected);
		assertThat(resultActual.getVacationDetails().getLstAcctAbsenDetail())
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
						x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
						x -> x.getUnbalanceNumber().getTime())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),
						Tuple.tuple("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 5)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),
						Tuple.tuple("077a8929-3df0-4fd6-859e-29e615a921ee", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 8)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),
						Tuple.tuple("876caf30-5a4d-47b7-8147-d646f74be08a", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 9)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))));

	}

	// 前回代休の集計結果 isPresent, 前回集計期間の翌日 != 集計終了日
	@Test
	public void testOptBeforePresent() {
		List<InterimDayOffMng> dayOffMng = new ArrayList<InterimDayOffMng>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimRemain> interimMng = new ArrayList<>();

		Optional<SubstituteHolidayAggrResult> holidayAggrResult = Optional.of(new SubstituteHolidayAggrResult(
				new VacationDetails(Collections.emptyList()), new ReserveLeaveRemainingDayNumber(0d),
				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(0d), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0d), new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(0d),
				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(0d), new RemainingMinutes(0),
				Collections.emptyList(), Finally.of(GeneralDate.ymd(2019, 12, 01)), Collections.emptyList()));

		BreakDayOffRemainMngRefactParam inputParam = new BreakDayOffRemainMngRefactParam(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), false,
				GeneralDate.ymd(2019, 11, 30), false, interimMng, Optional.empty(), Optional.empty(), breakMng,
				dayOffMng, holidayAggrResult, new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		new Expectations() {
			{
				require.getBySidYmd(anyString, anyString, (GeneralDate) any);
				result = Arrays.asList(
						new CompensatoryDayOffManaData("adda6a46-2cbe-48c8-85f8-c04ca554e133", CID, SID, true,
								GeneralDate.ymd(2019, 11, 10), 1.0, 1, 1.0, 1),
						new CompensatoryDayOffManaData("adda6a46-2cbe-48c8-85f8-c04ca554e133", CID, SID, true,
								GeneralDate.ymd(2019, 11, 12), 1.0, 1, -1.0, -1));

				require.getBySidYmd(anyString, anyString, (GeneralDate) any, (DigestionAtr) any);
				result = Arrays.asList(
						new LeaveManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e133", CID, SID, true,
								GeneralDate.ymd(2019, 11, 11), GeneralDate.max(), 1.0, 0, 1.0, 0,
								DigestionAtr.UNUSED.value, 0, 0),
						new LeaveManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e133", CID, SID, true,
								GeneralDate.ymd(2019, 11, 13), GeneralDate.max(), 1.0, 0, -1.0, -1,
								DigestionAtr.UNUSED.value, 0, 0));

				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				// ClosureService
				require.getClosureDataByEmployee(SID, (GeneralDate) any);
				result = createClosure();

				// CompanyAdapter
				require.getFirstMonth(CID);
				result = new CompanyDto(11);

				require.getRemainBySidPriod(anyString, (DatePeriod) any, (RemainType) any);
				result = Arrays.asList(
						new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, GeneralDate.ymd(2019, 11, 5),
								CreateAtr.SCHEDULE, RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921ee", SID, GeneralDate.ymd(2019, 11, 8),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("876caf30-5a4d-47b7-8147-d646f74be08a", SID, GeneralDate.ymd(2019, 11, 9),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));

				require.getDayOffBySidPeriod(anyString, (DatePeriod) any);
				result = Arrays.asList(
						new InterimDayOffMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)),
						new InterimDayOffMng("62d542c3-4b79-4bf3-bd39-7e7f06711c34", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)),
						new InterimDayOffMng("077a8929-3df0-4fd6-859e-29e615a921ee", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)),
						new InterimDayOffMng("876caf30-5a4d-47b7-8147-d646f74be08a", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));

				require.getBreakDayOffMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", anyBoolean, (DataManagementAtr) any);
				result = Arrays.asList(
						new InterimBreakDayOffMng("", DataManagementAtr.INTERIM, "adda6a46-2cbe-48c8-85f8-c04ca554e132",
								DataManagementAtr.INTERIM, new UseTime(0), new UseDay(0d), SelectedAtr.AUTOMATIC));

				require.getBySidPeriod(SID, (DatePeriod) any);
				result = Arrays.asList(new InterimBreakMng("adda6a46-2cbe-48c8-85f8-c04ca554e132",
						new AttendanceTime(840), GeneralDate.max().addDays(-1), new OccurrenceTime(0),
						new OccurrenceDay(1.0), new AttendanceTime(240), new UnUsedTime(-240), new UnUsedDay(0.0)));

				require.getBreakDayOffMng(anyString, anyBoolean, (DataManagementAtr) any);
				result = Arrays.asList(
						new InterimBreakDayOffMng("bdda6a46-2cbe-48c8-85f8-c04ca554e132", DataManagementAtr.INTERIM, "",
								DataManagementAtr.INTERIM, new UseTime(0), new UseDay(0d), SelectedAtr.AUTOMATIC));

				require.findByEmployeeIdOrderByStartDate(anyString);
				result = Arrays.asList(
						new EmploymentHistShareImport(SID, "02",
								new DatePeriod(GeneralDate.ymd(2000, 1, 1), GeneralDate.ymd(2010, 03, 31))),
						new EmploymentHistShareImport(SID, "00",
								new DatePeriod(GeneralDate.ymd(2010, 4, 30), GeneralDate.ymd(9999, 12, 31))));
			}

		};
		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
				.getBreakDayOffMngInPeriod(require, inputParam);
		List<AccumulationAbsenceDetail> lstAccDetailExpected = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554e132")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 5))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "62d542c3-4b79-4bf3-bd39-7e7f06711c34")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 8))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "077a8929-3df0-4fd6-859e-29e615a921ee")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 9))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "876caf30-5a4d-47b7-8147-d646f74be08a")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build());

		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
				new VacationDetails(lstAccDetailExpected), new ReserveLeaveRemainingDayNumber(-4.0),
				new RemainingMinutes(-1), new ReserveLeaveRemainingDayNumber(4.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(1.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0d), new RemainingMinutes(-1),
				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
				Arrays.asList(DayOffError.DAYERROR, DayOffError.TIMEERROR, DayOffError.PREFETCH_ERROR), Finally.of(GeneralDate.ymd(2020, 11, 01)),
				new ArrayList<>());

		assertData(resultActual, resultExpected);
		assertThat(resultActual.getVacationDetails().getLstAcctAbsenDetail())
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
						x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
						x -> x.getUnbalanceNumber().getTime())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e133", SID, MngDataStatus.CONFIRMED, true,
								Optional.of(GeneralDate.ymd(2019, 11, 10)), 1.0, Optional.of(new AttendanceTime(1)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(1))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e133", SID, MngDataStatus.CONFIRMED, true,
								Optional.of(GeneralDate.ymd(2019, 11, 11)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.OCCURRENCE, 0.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 5)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("077a8929-3df0-4fd6-859e-29e615a921ee", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 8)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("876caf30-5a4d-47b7-8147-d646f74be08a", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 9)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))));
	}

	// 前回代休の集計結果 isPresent, 前回集計期間の翌日 = 集計終了日
	@Test
	public void testOptBeforePresentEqualDate() {

		List<InterimDayOffMng> dayOffMng = new ArrayList<InterimDayOffMng>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimRemain> interimMng = new ArrayList<>();

		Optional<SubstituteHolidayAggrResult> holidayAggrResult = Optional.of(new SubstituteHolidayAggrResult(
				new VacationDetails(Collections.emptyList()), new ReserveLeaveRemainingDayNumber(1d),
				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(1d), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0d), new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(1d),
				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(1d), new RemainingMinutes(0),
				Collections.emptyList(), Finally.of(GeneralDate.ymd(2019, 11, 01)), Collections.emptyList()));

		BreakDayOffRemainMngRefactParam inputParam = new BreakDayOffRemainMngRefactParam(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), false,
				GeneralDate.ymd(2019, 11, 30), false, interimMng, Optional.empty(), Optional.empty(), breakMng,
				dayOffMng, holidayAggrResult, new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		new Expectations() {
			{

				require.getRemainBySidPriod(anyString, (DatePeriod) any, (RemainType) any);
				result = Arrays.asList(
						new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
								CreateAtr.SCHEDULE, RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, GeneralDate.ymd(2019, 11, 5),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921ee", SID, GeneralDate.ymd(2019, 11, 8),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("876caf30-5a4d-47b7-8147-d646f74be08a", SID, GeneralDate.ymd(2019, 11, 9),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));

				require.getDayOffBySidPeriod(anyString, (DatePeriod) any);
				result = Arrays.asList(
						new InterimDayOffMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)),
						new InterimDayOffMng("62d542c3-4b79-4bf3-bd39-7e7f06711c34", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)),
						new InterimDayOffMng("077a8929-3df0-4fd6-859e-29e615a921ee", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)),
						new InterimDayOffMng("876caf30-5a4d-47b7-8147-d646f74be08a", new RequiredTime(0),
								new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));

				require.getBreakDayOffMng(anyString, anyBoolean, (DataManagementAtr) any);
				result = new ArrayList<>();

				require.getBreakDayOffMng(anyString, anyBoolean, (DataManagementAtr) any);
				result = new ArrayList<>();

				require.findByEmployeeIdOrderByStartDate(anyString);
				result = Arrays.asList(
						new EmploymentHistShareImport(SID, "02",
								new DatePeriod(GeneralDate.ymd(2000, 1, 1), GeneralDate.ymd(2010, 03, 31))),
						new EmploymentHistShareImport(SID, "00",
								new DatePeriod(GeneralDate.ymd(2010, 4, 30), GeneralDate.ymd(9999, 12, 31))));
			}

		};
		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
				.getBreakDayOffMngInPeriod(require, inputParam);
		List<AccumulationAbsenceDetail> lstAccDetailExpected = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554e132")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 5))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "62d542c3-4b79-4bf3-bd39-7e7f06711c34")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 8))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "077a8929-3df0-4fd6-859e-29e615a921ee")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 9))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "876caf30-5a4d-47b7-8147-d646f74be08a")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build());

		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
				new VacationDetails(lstAccDetailExpected), new ReserveLeaveRemainingDayNumber(-4.0),
				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(4.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(1.0d), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0), Arrays.asList(DayOffError.DAYERROR),
				Finally.of(GeneralDate.ymd(2020, 11, 01)), new ArrayList<>());

		assertData(resultActual, resultExpected);
		assertThat(resultActual.getVacationDetails().getLstAcctAbsenDetail())
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
						x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
						x -> x.getUnbalanceNumber().getTime())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),
						Tuple.tuple("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 5)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),
						Tuple.tuple("077a8929-3df0-4fd6-859e-29e615a921ee", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 8)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),
						Tuple.tuple("876caf30-5a4d-47b7-8147-d646f74be08a", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 9)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))));

	}

	// 月次か
	@Test
	public void testMonthMode() {

		List<InterimDayOffMng> dayOffMng = Arrays.asList(
				new InterimDayOffMng("62d542c3-4b79-4bf3-bd39-7e7f06711c34", new RequiredTime(0), new RequiredDay(1.0),
						new UnOffsetTime(0), new UnOffsetDay(1.0)),
				new InterimDayOffMng("077a8929-3df0-4fd6-859e-29e615a921ee", new RequiredTime(0), new RequiredDay(1.0),
						new UnOffsetTime(0), new UnOffsetDay(1.0)),
				new InterimDayOffMng("876caf30-5a4d-47b7-8147-d646f74be08a", new RequiredTime(0), new RequiredDay(1.0),
						new UnOffsetTime(0), new UnOffsetDay(1.0)),
				new InterimDayOffMng("077a8929-3df0-4fd6-859e-29e615a921ea", new RequiredTime(480),
						new RequiredDay(1.0), new UnOffsetTime(480), new UnOffsetDay(1.0)));

		List<InterimBreakMng> breakMng = Arrays.asList(
				new InterimBreakMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", new AttendanceTime(480),
						GeneralDate.max().addDays(-1), new OccurrenceTime(480), new OccurrenceDay(1.0),
						new AttendanceTime(240), new UnUsedTime(480), new UnUsedDay(1.0)),
				new InterimBreakMng("adda6a46-2cbe-48c8-85f8-c04ca554e333", new AttendanceTime(480),
						GeneralDate.ymd(2019, 6, 6), new OccurrenceTime(480), new OccurrenceDay(1.0),
						new AttendanceTime(240), new UnUsedTime(480), new UnUsedDay(1.0)));

		List<InterimRemain> interimMng = Arrays.asList(
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 5),
						CreateAtr.SCHEDULE, RemainType.BREAK, RemainAtr.SINGLE),
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e333", SID, GeneralDate.ymd(2019, 11, 6),
						CreateAtr.RECORD, RemainType.BREAK, RemainAtr.SINGLE),
				new InterimRemain("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, GeneralDate.ymd(2019, 11, 7),
						CreateAtr.RECORD, RemainType.BREAK, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921ee", SID, GeneralDate.ymd(2019, 11, 8),
						CreateAtr.RECORD, RemainType.BREAK, RemainAtr.SINGLE),
				new InterimRemain("876caf30-5a4d-47b7-8147-d646f74be08a", SID, GeneralDate.ymd(2019, 11, 9),
						CreateAtr.RECORD, RemainType.BREAK, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921ea", SID, GeneralDate.ymd(2019, 11, 10),
						CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddff")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(true, Optional.empty()),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554cccc")
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
				new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 15))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554ddde")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(0))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(480))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)),
				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 13))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554dddf")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(0))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(0))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)),
				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 14))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(0))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
												Optional.of(new AttendanceTime(0))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)));

		Optional<SubstituteHolidayAggrResult> holidayAggrResult = Optional.of(new SubstituteHolidayAggrResult(
				new VacationDetails(lstAccDetail), new ReserveLeaveRemainingDayNumber(1d), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(1d), new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(0d),
				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(1d), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(1d), new RemainingMinutes(0), Collections.emptyList(),
				Finally.of(GeneralDate.ymd(2019, 11, 01)), Collections.emptyList()));

		BreakDayOffRemainMngRefactParam inputParam = new BreakDayOffRemainMngRefactParam(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), true,
				GeneralDate.ymd(2019, 11, 30), false, interimMng, Optional.empty(), Optional.empty(), breakMng,
				dayOffMng, holidayAggrResult, new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

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

				require.getClosureDataByEmployee(SID, (GeneralDate) any);
				result = createClosure();

				require.getFirstMonth(CID);
				result = new CompanyDto(11);

				require.getBreakDayOffMng("077a8929-3df0-4fd6-859e-29e615a921ea", anyBoolean, (DataManagementAtr) any);
				result = Arrays.asList(
						new InterimBreakDayOffMng("", DataManagementAtr.INTERIM, "077a8929-3df0-4fd6-859e-29e615a921ea",
								DataManagementAtr.INTERIM, new UseTime(0), new UseDay(0d), SelectedAtr.AUTOMATIC));

				require.findComLeavEmpSet(CID, "02");
				result = createComLeav(ManageDistinct.YES, ManageDistinct.YES, "02");
			}

		};
		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
				.getBreakDayOffMngInPeriod(require, inputParam);
		List<AccumulationAbsenceDetail> lstAccDetailExpected = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554e132")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 5))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "62d542c3-4b79-4bf3-bd39-7e7f06711c34")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 8))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "077a8929-3df0-4fd6-859e-29e615a921ee")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 9))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "876caf30-5a4d-47b7-8147-d646f74be08a")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build());

		SubstituteHolidayAggrResult resultExpected = new SubstituteHolidayAggrResult(
				new VacationDetails(lstAccDetailExpected), new ReserveLeaveRemainingDayNumber(0.0),
				new RemainingMinutes(960), new ReserveLeaveRemainingDayNumber(2.0), new RemainingMinutes(480),
				new ReserveLeaveRemainingDayNumber(4.0), new RemainingMinutes(960),
				new ReserveLeaveRemainingDayNumber(1.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(1.0), new RemainingMinutes(480), Arrays.asList(),
				Finally.of(GeneralDate.ymd(2020, 11, 01)), new ArrayList<>());

		assertData(resultActual, resultExpected);
		assertThat(resultActual.getVacationDetails().getLstAcctAbsenDetail())
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
						x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
						x -> x.getUnbalanceNumber().getTime())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554cccc", SID, MngDataStatus.RECORD, true,
								Optional.empty(), 1.0, Optional.of(new AttendanceTime(0)), OccurrenceDigClass.DIGESTION,
								1.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554dddd", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 04, 11)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554dddf", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 4, 13)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554ddff", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 3)), 0.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554bbbb", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 5)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.OCCURRENCE, 0.0, Optional.of(new AttendanceTime(480))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e333", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 6)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(480))),

						Tuple.tuple("077a8929-3df0-4fd6-859e-29e615a921ea", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 10)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(480))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554eaaa", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 14)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.OCCURRENCE, 0.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554ddde", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 15)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(480))));

		assertThat(resultActual.getLstSeqVacation()).extracting(x -> x.getDateOfUse(), x -> x.getDayNumberUsed(),
				x -> x.getOutbreakDay(), x -> x.getTargetSelectionAtr())
				.containsExactly(Tuple.tuple(GeneralDate.ymd(2019, 11, 10), new ReserveLeaveRemainingDayNumber(1.0),
						GeneralDate.ymd(2019, 11, 5), TargetSelectionAtr.AUTOMATIC));

	}

	public static void assertData(SubstituteHolidayAggrResult resultActual,
			SubstituteHolidayAggrResult resultExpected) {

		assertThat(resultActual.getRemainDay().v()).isEqualTo(resultExpected.getRemainDay().v());
		assertThat(resultActual.getRemainTime().v()).isEqualTo(resultExpected.getRemainTime().v());
		assertThat(resultActual.getDayUse().v()).isEqualTo(resultExpected.getDayUse().v());
		assertThat(resultActual.getTimeUse().v()).isEqualTo(resultExpected.getTimeUse().v());
		assertThat(resultActual.getOccurrenceDay().v()).isEqualTo(resultExpected.getOccurrenceDay().v());
		assertThat(resultActual.getOccurrenceTime().v()).isEqualTo(resultExpected.getOccurrenceTime().v());
		assertThat(resultActual.getCarryoverDay().v()).isEqualTo(resultExpected.getCarryoverDay().v());
		assertThat(resultActual.getCarryoverTime().v()).isEqualTo(resultExpected.getCarryoverTime().v());
		assertThat(resultActual.getUnusedDay().v()).isEqualTo(resultExpected.getUnusedDay().v());
		assertThat(resultActual.getUnusedTime().v()).isEqualTo(resultExpected.getUnusedTime().v());
		assertThat(resultActual.getDayOffErrors()).isEqualTo(resultExpected.getDayOffErrors());
		assertThat(resultActual.getNextDay().get()).isEqualTo(resultExpected.getNextDay().get());
		// assertThat(resultActual.getLstSeqVacation()).isEqualTo(resultExpected.getLstSeqVacation());

	}

	public static Closure createClosure() {
		return new Closure(new ClosureGetMemento() {

			@Override
			public UseClassification getUseClassification() {
				return UseClassification.UseClass_Use;
			}

			@Override
			public CompanyId getCompanyId() {
				return new CompanyId(CID);
			}

			@Override
			public CurrentMonth getClosureMonth() {
				return new CurrentMonth(11);
			}

			@Override
			public ClosureId getClosureId() {
				return ClosureId.RegularEmployee;
			}

			@Override
			public List<ClosureHistory> getClosureHistories() {
				return Arrays.asList(new ClosureHistory(new ClosureHistoryGetMemento() {

					@Override
					public YearMonth getStartDate() {
						return YearMonth.of(1900, 1);
					}

					@Override
					public YearMonth getEndDate() {
						return YearMonth.of(9999, 12);
					}

					@Override
					public CompanyId getCompanyId() {
						return new CompanyId(CID);
					}

					@Override
					public ClosureName getClosureName() {
						return new ClosureName("AA");
					}

					@Override
					public ClosureId getClosureId() {
						return ClosureId.RegularEmployee;
					}

					@Override
					public ClosureDate getClosureDate() {
						return new ClosureDate(1, true);
					}
				}));
			}
		});
	}

	public static CompensatoryLeaveEmSetting createComLeav(ManageDistinct manageDistinct, ManageDistinct manageTime,
			String empCode) {
		return new CompensatoryLeaveEmSetting(new CompensatoryLeaveEmSettingGetMemento() {

			@Override
			public ManageDistinct getIsManaged() {
				return manageDistinct;
			}

			@Override
			public EmploymentCode getEmploymentCode() {
				return new EmploymentCode(empCode);
			}

			@Override
			public CompensatoryDigestiveTimeUnit getCompensatoryDigestiveTimeUnit() {
				return new CompensatoryDigestiveTimeUnit(new CompensatoryDigestiveTimeUnitGetMemento() {

					@Override
					public ManageDistinct getIsManageByTime() {
						return manageTime;
					}

					@Override
					public TimeDigestiveUnit getDigestiveUnit() {
						return TimeDigestiveUnit.OneMinute;
					}
				});
			}

			@Override
			public CompensatoryAcquisitionUse getCompensatoryAcquisitionUse() {
				return new CompensatoryAcquisitionUse(new CompensatoryAcquisitionUseGetMemento() {

					@Override
					public ApplyPermission getPreemptionPermit() {
						return ApplyPermission.ALLOW;
					}

					@Override
					public ExpirationTime getExpirationTime() {
						return ExpirationTime.THREE_MONTH;
					}

					@Override
					public DeadlCheckMonth getDeadlCheckMonth() {
						return DeadlCheckMonth.THREE_MONTH;
					}
				});
			}

			@Override
			public String getCompanyId() {
				return CID;
			}
		});
	}
}