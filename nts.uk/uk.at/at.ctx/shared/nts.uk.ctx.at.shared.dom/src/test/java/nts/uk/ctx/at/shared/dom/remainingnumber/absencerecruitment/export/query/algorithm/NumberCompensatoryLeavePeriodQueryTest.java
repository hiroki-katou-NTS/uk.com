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
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeQueryTest;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;
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

	@Test
	public void testOptBeforeResultPresent() {

		List<InterimAbsMng> useAbsMng = Arrays.asList(
				new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", new RequiredDay(1.0), new UnOffsetDay(1.0)));

		List<InterimRecMng> useRecMng = Arrays.asList(
				new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e333", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("62d542c3-4b79-4bf3-bd39-7e7f06711c34", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("077a8929-3df0-4fd6-859e-29e615a921ee", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)));

		List<InterimRemain> interimMng = Arrays.asList(
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
						CreateAtr.SCHEDULE, RemainType.PAUSE, RemainAtr.SINGLE),

				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e333", SID, GeneralDate.ymd(2019, 11, 5),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, GeneralDate.ymd(2019, 11, 14),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921ee", SID, GeneralDate.ymd(2019, 11, 15),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE));

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
				GeneralDate.ymd(2019, 11, 30), false, false, useAbsMng, interimMng, useRecMng,
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

	@Test
	public void testOptBeforeResultNoPresent() {

		List<InterimAbsMng> useAbsMng = Arrays.asList(
				new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", new RequiredDay(1.0), new UnOffsetDay(1.0)));

		List<InterimRecMng> useRecMng = Arrays.asList(
				new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e333", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("62d542c3-4b79-4bf3-bd39-7e7f06711c34", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("077a8929-3df0-4fd6-859e-29e615a921ee", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)));

		List<InterimRemain> interimMng = Arrays.asList(
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
						CreateAtr.SCHEDULE, RemainType.PAUSE, RemainAtr.SINGLE),

				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e333", SID, GeneralDate.ymd(2019, 11, 5),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, GeneralDate.ymd(2019, 11, 14),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921ee", SID, GeneralDate.ymd(2019, 11, 15),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE));

		new Expectations() {
			{

				require.getByYmdUnOffset(CID, SID, (GeneralDate) any, anyDouble);
				result = Arrays.asList(
						new SubstitutionOfHDManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e133", CID, SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 30))),
								new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(1.0)),
						new SubstitutionOfHDManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e134", CID, SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 29))),
								new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(1.0)),
						new SubstitutionOfHDManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e135", CID, SID,
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
								-1.0, 0));

			}
		};

		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
				GeneralDate.ymd(2019, 11, 30), false, false, useAbsMng, interimMng, useRecMng, Optional.empty(),
				Optional.empty(), Optional.empty(), new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);
		// @ConstructorProperties(value={"vacationDetails", "remainDay", "unusedDay",
		// "occurrenceDay", "dayUse", "carryoverDay", "nextDay", "lstSeqVacation",
		// "pError"})
		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(new VacationDetails(new ArrayList<>()),
				new ReserveLeaveRemainingDayNumber(2.0), new ReserveLeaveRemainingDayNumber(0.0),
				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(2.0),
				new ReserveLeaveRemainingDayNumber(2.0), Finally.of(GeneralDate.ymd(2020, 11, 1)), new ArrayList<>(),
				new ArrayList<>());

		assertData(resultActual, resultExpected);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCaseOther() {

		List<InterimAbsMng> useAbsMng = Arrays.asList(
				new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", new RequiredDay(1.0), new UnOffsetDay(1.0)),
				new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e133", new RequiredDay(0.5), new UnOffsetDay(0.5)),
				new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e136", new RequiredDay(1.0), new UnOffsetDay(1.0)));

		List<InterimRecMng> useRecMng = Arrays.asList(
				new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e333", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("62d542c3-4b79-4bf3-bd39-7e7f06711c34", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("077a8929-3df0-4fd6-859e-29e615a921ee", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("077a8929-3df0-4fd6-859e-29e615a921e7", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("077a8929-3df0-4fd6-859e-29e615a921e8", GeneralDate.max(), new OccurrenceDay(0.5),
						StatutoryAtr.PUBLIC, new UnUsedDay(0.5)),
				new InterimRecMng("077a8929-3df0-4fd6-859e-29e615a921e6", GeneralDate.ymd(2010, 10, 4),
						new OccurrenceDay(1.0), StatutoryAtr.PUBLIC, new UnUsedDay(1.0)));

		List<InterimRemain> interimMng = Arrays.asList(
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
						CreateAtr.SCHEDULE, RemainType.PAUSE, RemainAtr.SINGLE),
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e133", SID, GeneralDate.ymd(2019, 11, 6),
						CreateAtr.RECORD, RemainType.PAUSE, RemainAtr.SINGLE),
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, GeneralDate.ymd(2019, 11, 7),
						CreateAtr.RECORD, RemainType.PAUSE, RemainAtr.SINGLE),

				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e333", SID, GeneralDate.ymd(2019, 11, 5),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, GeneralDate.ymd(2019, 11, 14),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921ee", SID, GeneralDate.ymd(2019, 11, 15),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921e7", SID, GeneralDate.ymd(2019, 11, 11),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921e8", SID, GeneralDate.ymd(2019, 11, 11),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921e6", SID, GeneralDate.ymd(2019, 11, 16),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE));

		CompenLeaveAggrResult compenLeaveAggrResult = new CompenLeaveAggrResult(
				new VacationDetails(Collections.emptyList()), new ReserveLeaveRemainingDayNumber(1.0),
				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(1.0),
				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(1.0),
				Finally.of(GeneralDate.ymd(2019, 11, 01)), Collections.emptyList(), Collections.emptyList());

		new Expectations() {
			{

				require.getRecOrAbsMngs((List<String>) (any), anyBoolean, DataManagementAtr.INTERIM);
				result = Arrays.asList(
						new InterimRecAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", DataManagementAtr.INTERIM, "",
								DataManagementAtr.INTERIM, new UseDay(1.0), SelectedAtr.MANUAL),

						new InterimRecAbsMng("", DataManagementAtr.INTERIM, "adda6a46-2cbe-48c8-85f8-c04ca554e333",
								DataManagementAtr.INTERIM, new UseDay(1.0), SelectedAtr.MANUAL),
						new InterimRecAbsMng("", DataManagementAtr.INTERIM, "62d542c3-4b79-4bf3-bd39-7e7f06711c34",
								DataManagementAtr.INTERIM, new UseDay(1.0), SelectedAtr.MANUAL),
						new InterimRecAbsMng("", DataManagementAtr.INTERIM, "077a8929-3df0-4fd6-859e-29e615a921ee",
								DataManagementAtr.INTERIM, new UseDay(1.0), SelectedAtr.MANUAL));

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
				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();

				require.getFirstMonth(CID);
				result = new CompanyDto(11);

				require.findEmpById(anyString, anyString);
				result = Optional.of(new EmpSubstVacation(CID, "00", new SubstVacationSetting(ManageDistinct.YES,
						ExpirationTime.THIS_MONTH, ApplyPermission.ALLOW)));

			}
		};

		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
				GeneralDate.ymd(2019, 11, 30), true, true, useAbsMng, interimMng, useRecMng,
				Optional.of(compenLeaveAggrResult), Optional.empty(), Optional.empty(),
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);

		// @ConstructorProperties(value={"vacationDetails", "remainDay", "unusedDay",
		// "occurrenceDay", "dayUse", "carryoverDay", "nextDay", "lstSeqVacation",
		// "pError"})
		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(new VacationDetails(new ArrayList<>()),
				new ReserveLeaveRemainingDayNumber(0.0), new ReserveLeaveRemainingDayNumber(0.0),
				new ReserveLeaveRemainingDayNumber(4.5), new ReserveLeaveRemainingDayNumber(2.5),
				new ReserveLeaveRemainingDayNumber(1.0), Finally.of(GeneralDate.ymd(2020, 11, 1)), new ArrayList<>(),
				Arrays.asList());

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
