package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

@RunWith(JMockit.class)
public class OffsetChronologicalOrderTest {

	private static String SID = "000000000000-0117";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public void test() {
//
//		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
//				new AccuVacationBuilder(SID,
//						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddfk")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
//										Optional.of(new AttendanceTime(0))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
//										Optional.of(new AttendanceTime(0))))
//								.build(),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.build(),
//						new AttendanceTime(0), new AttendanceTime(0)),
//				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554cccc")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.build(),
//				new AccuVacationBuilder(SID,
//						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 4))),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.build(),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554ddde")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(480))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)),
//
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554dddf")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(240))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)),
//
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(0))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
//												Optional.of(new AttendanceTime(0))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)));
//		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
//		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
//				.process(SID, true, lstAccAbse, typeJudgment);
//		assertThat(resultActual.getRight())
//				.extracting(x -> x.getDateOfUse(), x -> x.getOutbreakDay(), x -> x.getDayNumberUsed().v())
//				.contains(Tuple.tuple(GeneralDate.ymd(2019, 04, 4), GeneralDate.ymd(2019, 04, 10), 1.0));
//		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
//	}

//	@Test
//	public void testSubstitutionHolidayOutputNull() {
//
//		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
//				new AccuVacationBuilder(SID,
//						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddff")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
//										Optional.of(new AttendanceTime(0))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
//										Optional.of(new AttendanceTime(0))))
//								.build(),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)),
//				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554cccc")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.build(),
//				new AccuVacationBuilder(SID,
//						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 4))),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.build(),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554ddde")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(0))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554dddf")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(240))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(240))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(0))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
//												Optional.of(new AttendanceTime(0))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)));
//		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
//		new Expectations() {
//			{
//
//			}
//
//		};
//		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
//				.process(SID, true, lstAccAbse, typeJudgment);
//		assertThat(resultActual.getRight()).isEqualTo(new ArrayList<>());
//		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
//	}
//
//	// ????????????????????????????????????
//	@Test
//	public void testOffsetJudgmenteError() {
//
//		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
//				new AccuVacationBuilder(SID,
//						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddff")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
//										Optional.of(new AttendanceTime(0))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
//										Optional.of(new AttendanceTime(0))))
//								.build(),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)),
//				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554cccc")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.build(),
//				new AccuVacationBuilder(SID,
//						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 4))),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.build(),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554ddde")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(0))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
//												Optional.of(new AttendanceTime(0))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554dddf")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(0))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(240))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)));
//		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
//		new Expectations() {
//			{
//
//			}
//
//		};
//		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
//				.process(SID, true, lstAccAbse, typeJudgment);
//
//		assertThat(resultActual.getRight()).isEqualTo(new ArrayList<>());
//		assertThat(resultActual.getLeft()).isEqualTo(Optional.of(DayOffError.PREFETCH_ERROR));
//	}

//	@Test
//	public void testManagerTimeCateNoFound() {
//
//		boolean lstTimeLap = false;
//
//		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8),
//				DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
//				new AccuVacationBuilder(SID,
//						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
//						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddf")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(0))))
//								.unbalanceNumber(
//										new NumberConsecuVacation(new ManagementDataRemainUnit(1.0), Optional.empty()))
//								.build(),
//				new AttendanceTime(480), new AttendanceTime(240)));
//		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
//		new Expectations() {
//			{
//
//			}
//
//		};
//		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
//				.process(SID, lstTimeLap, lstAccAbse, typeJudgment);
//		assertThat(resultActual.getRight()).isEqualTo(new ArrayList<>());
//		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
//	}

	// ????????????????????????????????????
	@Test
	public void testUpdateUnbNumMagTimeFalse() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddfk")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(120))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(120))))
										.build(),
						new AttendanceTime(0), new AttendanceTime(0)),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554cccc")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554ddde")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(120))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(60))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)),
				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554dddf")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(240))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
												Optional.of(new AttendanceTime(240))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)),
				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(0))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
												Optional.of(new AttendanceTime(0))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)));
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;

		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
				.process(SID, false, lstAccAbse, typeJudgment);
		assertThat(resultActual.getRight())
				.extracting(x -> x.getDateOfUse(), x -> x.getDayNumberUsed(), x -> x.getOutbreakDay(),
						x -> x.getTargetSelectionAtr())
				.contains(Tuple.tuple(GeneralDate.ymd(2019, 04, 04), new ReserveLeaveRemainingDayNumber(1.0),
						GeneralDate.ymd(2019, 11, 04), TargetSelectionAtr.AUTOMATIC));
	}

//	// ????????????????????????????????????
//	@Test
//	public void testUpdateUnbNumMagTimeTrue() {
//
//		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
//				new AccuVacationBuilder(SID,
//						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddfk")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
//										Optional.of(new AttendanceTime(0))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
//										Optional.of(new AttendanceTime(0))))
//								.build(),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)),
//				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554cccc")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.build(),
//				new AccuVacationBuilder(SID,
//						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 4))),
//						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
//								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//										Optional.of(new AttendanceTime(120))))
//								.build(),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554ddde")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(120))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(60))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554dddf")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(240))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
//												Optional.of(new AttendanceTime(240))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)),
//				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
//						Optional.of(GeneralDate.ymd(2019, 12, 30)),
//						new AccuVacationBuilder(SID,
//								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
//								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
//								"adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
//										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
//												Optional.of(new AttendanceTime(0))))
//										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
//												Optional.of(new AttendanceTime(0))))
//										.build(),
//						new AttendanceTime(480), new AttendanceTime(240)));
//		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
//
//		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
//				.process(SID, true, lstAccAbse, typeJudgment);
//		assertThat(resultActual.getRight()).isEqualTo(new ArrayList<>());
//
//	}

	// test offsetJudgment
	// ????????????
	@Test
	@SuppressWarnings("unchecked")
	public void testCase1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method privateMethod = OffsetChronologicalOrder.class.getDeclaredMethod("offsetJudgment",
				boolean.class, AccumulationAbsenceDetail.class, AccumulationAbsenceDetail.class,
				TypeOffsetJudgment.class);
		privateMethod.setAccessible(true);

		AccumulationAbsenceDetail dig = new AccuVacationBuilder(SID,
				new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 11))),
				OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(0))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(0))))
						.build();

		AccumulationAbsenceDetail occ = new UnbalanceVacation(GeneralDate.ymd(2019, 10, 8), DigestionAtr.UNUSED,
				Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 15))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.build(),
				new AttendanceTime(480), new AttendanceTime(240));

		Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>> returnValue = (Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>>) privateMethod
				.invoke(OffsetChronologicalOrder.class, true, dig, occ, TypeOffsetJudgment.REAMAIN);

		assertThat(returnValue.getLeft()).isEqualTo(OffsetJudgment.SUCCESS);

	}

	// test offsetJudgment
	// ????????????
	@Test
	@SuppressWarnings("unchecked")
	public void testCase2() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method privateMethod = OffsetChronologicalOrder.class.getDeclaredMethod("offsetJudgment",
				boolean.class, AccumulationAbsenceDetail.class, AccumulationAbsenceDetail.class,
				TypeOffsetJudgment.class);
		privateMethod.setAccessible(true);

		AccumulationAbsenceDetail dig = new AccuVacationBuilder(SID,
				new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 11))),
				OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(0))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(0))))
						.build();

		AccumulationAbsenceDetail occ = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
				Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 15))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AttendanceTime(480), new AttendanceTime(240));

		Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>> returnValue = (Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>>) privateMethod
				.invoke(OffsetChronologicalOrder.class, true, dig, occ, TypeOffsetJudgment.REAMAIN);

		assertThat(returnValue.getLeft()).isEqualTo(OffsetJudgment.SUCCESS);

	}

	// ????????????
	@Test
	@SuppressWarnings("unchecked")
	public void testCase4() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method privateMethod = OffsetChronologicalOrder.class.getDeclaredMethod("offsetJudgment",
				boolean.class, AccumulationAbsenceDetail.class, AccumulationAbsenceDetail.class,
				TypeOffsetJudgment.class);
		privateMethod.setAccessible(true);

		AccumulationAbsenceDetail dig = new AccuVacationBuilder(SID,
				new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 11))),
				OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(0))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(0))))
						.build();

		AccumulationAbsenceDetail occ = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
				Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 10, 15))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new AttendanceTime(480), new AttendanceTime(240));

		Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>> returnValue = (Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>>) privateMethod
				.invoke(OffsetChronologicalOrder.class, true, dig, occ, TypeOffsetJudgment.REAMAIN);

		assertThat(returnValue.getLeft()).isEqualTo(OffsetJudgment.SUCCESS);

	}

}
