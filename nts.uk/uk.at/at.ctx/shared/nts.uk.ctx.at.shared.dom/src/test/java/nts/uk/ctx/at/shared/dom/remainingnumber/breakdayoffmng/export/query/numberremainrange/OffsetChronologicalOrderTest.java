package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
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
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.vacation.algorithm.TimeLapseVacationSetting;

@RunWith(JMockit.class)
public class OffsetChronologicalOrderTest {

	@Injectable
	private OffsetChronologicalOrder.Require require;

	private static String SID = "000000000000-0117";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {

		List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
				new TimeLapseVacationSetting(new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),
						true, 30, true, Optional.of(true), Optional.of(1)));

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddfk")
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
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build());
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		List<SeqVacationAssociationInfo> resultActual = OffsetChronologicalOrder.process(require, SID, lstTimeLap,
				lstAccAbse, typeJudgment);
		assertThat(resultActual).isEqualTo(new ArrayList<>());
	}

	@Test
	public void testSubstitutionHolidayOutputNull() {

		List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
				new TimeLapseVacationSetting(new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),
						true, 30, true, Optional.of(true), Optional.of(1)));

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
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(
										new NumberConsecuVacation(new ManagementDataRemainUnit(1.0), Optional.empty()))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
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
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		new Expectations() {
			{

			}

		};
		List<SeqVacationAssociationInfo> resultActual = OffsetChronologicalOrder.process(require, SID, lstTimeLap,
				lstAccAbse, typeJudgment);
		assertThat(resultActual).isEqualTo(new ArrayList<>());
	}

	// 相殺判定の返すパラメータ
	@Test
	public void testOffsetJudgmenteError() {

		List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
				new TimeLapseVacationSetting(new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),
						true, 30, false, Optional.of(true), Optional.of(1)));

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
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
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
								.build());
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		new Expectations() {
			{

			}

		};
		List<SeqVacationAssociationInfo> resultActual = OffsetChronologicalOrder.process(require, SID, lstTimeLap,
				lstAccAbse, typeJudgment);
		assertThat(resultActual).isEqualTo(new ArrayList<>());
	}

	@Test
	public void testManagerTimeCateNoFound() {

		List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
				new TimeLapseVacationSetting(new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),
						true, 30, false, Optional.empty(), Optional.of(1)));

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(new AccuVacationBuilder(SID,
				new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
				OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddf")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(0))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0), Optional.empty()))
						.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
								Optional.of(GeneralDate.ymd(2019, 12, 30)), new AttendanceTime(480),
								new AttendanceTime(240)))
						.build());
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		new Expectations() {
			{

			}

		};
		List<SeqVacationAssociationInfo> resultActual = OffsetChronologicalOrder.process(require, SID, lstTimeLap,
				lstAccAbse, typeJudgment);
		assertThat(resultActual).isEqualTo(new ArrayList<>());
	}

	// 相殺判定の返すパラメータ
	@Test
	public void testUpdateUnbNumMagTimeFalse() {

		List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
				new TimeLapseVacationSetting(new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),
						true, 30, true, Optional.of(false), Optional.of(1)));

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddfk")
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
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceVacation(null).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(60))))
								.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
										DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
										new AttendanceTime(480), new AttendanceTime(240)))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddf")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
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
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
	
		List<SeqVacationAssociationInfo> resultActual = OffsetChronologicalOrder.process(require, SID, lstTimeLap,
				lstAccAbse, typeJudgment);
		assertThat(resultActual)
				.extracting(x -> x.getDateOfUse(), x -> x.getDayNumberUsed(), x -> x.getOutbreakDay(),
						x -> x.getTargetSelectionAtr())
				.contains(
						Tuple.tuple(GeneralDate.ymd(2019, 04, 04), new ReserveLeaveRemainingDayNumber(1.0),
								GeneralDate.ymd(2019, 04, 10), TargetSelectionAtr.AUTOMATIC),
						Tuple.tuple(GeneralDate.ymd(2019, 04, 04), new ReserveLeaveRemainingDayNumber(1.0),
								GeneralDate.ymd(2019, 04, 10), TargetSelectionAtr.AUTOMATIC));
	}
	
	// 相殺判定の返すパラメータ
		@Test
		public void testUpdateUnbNumMagTimeTrue() {

			List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
					new TimeLapseVacationSetting(new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),
							true, 30, true, Optional.of(true), Optional.of(1)));

			List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
					new AccuVacationBuilder(SID,
							new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
							OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddfk")
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
									.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
											Optional.of(new AttendanceTime(120))))
									.unbalanceVacation(null).build(),
					new AccuVacationBuilder(SID,
							new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
							OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
									.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
											Optional.of(new AttendanceTime(0))))
									.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
											Optional.of(new AttendanceTime(60))))
									.unbalanceVacation(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
											DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
											new AttendanceTime(480), new AttendanceTime(240)))
									.build(),
					new AccuVacationBuilder(SID,
							new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
							OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddf")
									.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
											Optional.of(new AttendanceTime(0))))
									.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
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
			TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		
			List<SeqVacationAssociationInfo> resultActual = OffsetChronologicalOrder.process(require, SID, lstTimeLap,
					lstAccAbse, typeJudgment);
			assertThat(resultActual).isEqualTo(new ArrayList<>());
				
		}

}
