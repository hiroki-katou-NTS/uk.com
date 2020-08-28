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
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.TotalRemainUndigestNumber.RemainUndigestResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
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

	@Test
	public void testSubstitutionHolidayOutputNull() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddff")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(
										new ManagementDataRemainUnit(0.0), Optional.of(new AttendanceTime(0))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554cccc")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddf")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(240))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build());

		new Expectations() {
			{

				require.findEmploymentHistory(anyString, SID, (GeneralDate) any);
				result = Optional.empty();
			}

		};
		RemainUndigestResult resultActual = TotalRemainUndigestNumber.process(require, CID, SID,
				GeneralDate.ymd(2020, 11, 30), lstAccAbse, false);
		RemainUndigestResult resultExpect = new RemainUndigestResult(-1.5, -240, 0d, 0);
		assertRemainUndigestResult(resultActual, resultExpect);
	}

	@Test
	public void testSubstitutionHolidayOutNoNull() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddff")
								.numberOccurren(
										new NumberConsecuVacation(new ManagementDataRemainUnit(0.0), Optional.empty()))
								.unbalanceNumber(
										new NumberConsecuVacation(new ManagementDataRemainUnit(0.0), Optional.empty()))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554cccc")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(
										new NumberConsecuVacation(new ManagementDataRemainUnit(1.0), Optional.empty()))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddf")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(240))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build());

		new Expectations() {
			{

				require.findEmploymentHistory(anyString, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.findComLeavEmpSet(anyString, anyString);
				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.YES, ManageDistinct.YES, anyString);
			}

		};
		RemainUndigestResult resultActual = TotalRemainUndigestNumber.process(require, CID, SID,
				GeneralDate.ymd(2020, 11, 30), lstAccAbse, false);
		RemainUndigestResult resultExpect = new RemainUndigestResult(-2.0, -240, 0d, 240);
		assertRemainUndigestResult(resultActual, resultExpect);
	}

	// 未使用
	@Test
	public void testNumberHalfDay() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddff")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(
										new ManagementDataRemainUnit(0.0), Optional.of(new AttendanceTime(0))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554cccc")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddf")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(240))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
										Optional.of(new AttendanceTime(0))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build());

		new Expectations() {
			{

				require.findEmploymentHistory(anyString, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.findComLeavEmpSet(anyString, anyString);
				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.NO, ManageDistinct.NO, anyString);
			}

		};
		RemainUndigestResult resultActual = TotalRemainUndigestNumber.process(require, CID, SID,
				GeneralDate.ymd(2020, 11, 30), lstAccAbse, false);
		RemainUndigestResult resultExpect = new RemainUndigestResult(-2.0, -240, 2.5, 1200);
		assertRemainUndigestResult(resultActual, resultExpect);
	}

	// 未使用
	@Test
	public void testDeadlineAfterDate() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddff")
								.numberOccurren(
										new NumberConsecuVacation(new ManagementDataRemainUnit(0.0), Optional.empty()))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554cccc")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddf")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(240))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(
										new NumberConsecuVacation(new ManagementDataRemainUnit(0.5), Optional.empty()))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2020, 12, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build());

		new Expectations() {
			{

				require.findEmploymentHistory(anyString, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.findComLeavEmpSet(anyString, anyString);
				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.NO, ManageDistinct.NO, anyString);
			}

		};
		RemainUndigestResult resultActual = TotalRemainUndigestNumber.process(require, CID, SID,
				GeneralDate.ymd(2020, 11, 30), lstAccAbse, false);
		RemainUndigestResult resultExpect = new RemainUndigestResult(-1.5, -240, 2.0, 960);
		assertRemainUndigestResult(resultActual, resultExpect);
	}

	private void assertRemainUndigestResult(RemainUndigestResult resultActual, RemainUndigestResult resultExpect) {
		assertThat(resultActual.getRemainingDay()).isEqualTo(resultExpect.getRemainingDay());
		assertThat(resultActual.getRemainingTime()).isEqualTo(resultExpect.getRemainingTime());
		assertThat(resultActual.getUndigestDay()).isEqualTo(resultExpect.getUndigestDay());
		assertThat(resultActual.getUndigestTime()).isEqualTo(resultExpect.getUndigestTime());
	}

}
