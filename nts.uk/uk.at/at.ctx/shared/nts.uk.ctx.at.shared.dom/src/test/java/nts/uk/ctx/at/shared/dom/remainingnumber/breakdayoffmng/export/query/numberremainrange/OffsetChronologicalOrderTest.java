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
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
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

	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新しない
	 * 
	 * 　準備するデータ
	 * 　　「逐次発生の休暇明細」（消化）．未相殺時間数 がない
	 * 　　　時間管理
	 * 　　　
	 * */
	@Test
	public void test() {

		List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
				new TimeLapseVacationSetting(new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),
						true, 30, true, Optional.of(true), Optional.of(1)));

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddfk")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(1))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
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
												Optional.of(new AttendanceTime(0))))
										.build(),
						new AttendanceTime(0), new AttendanceTime(0)));
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		lstAccAbse.sort(new AccumulationAbsenceDetailComparator());
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
				.process(require, SID, lstTimeLap, lstAccAbse, typeJudgment);
		assertThat(resultActual.getRight()).isEqualTo(new ArrayList<>());
		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
		assertThat(lstAccAbse)
		.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
				x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
				x -> x.getUnbalanceNumber().getTime())
		.containsExactly(
				Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554ddfk", SID, MngDataStatus.RECORD, false,
						Optional.of(GeneralDate.ymd(2019, 11, 3)), 1.0, Optional.of(new AttendanceTime(1)),
						OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),
				Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554bbbb", SID, MngDataStatus.RECORD, false,
						Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, Optional.of(new AttendanceTime(120)),
						OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(0))));
	}

	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新しない
	 * 
	 * 　準備するデータ
	 * 　　「逐次発生の休暇明細」（消化）．未相殺時間数 がない
	 * 　　　時間管理がない
	 * 　　　
	 * */
	@Test
	public void testNoMag() {

		List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
				new TimeLapseVacationSetting(new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),
						true, 30, true, Optional.of(false), Optional.of(1)));

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddfk")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(1))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(10))))
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
												Optional.of(new AttendanceTime(0))))
										.build(),
						new AttendanceTime(0), new AttendanceTime(0)));
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		lstAccAbse.sort(new AccumulationAbsenceDetailComparator());
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
				.process(require, SID, lstTimeLap, lstAccAbse, typeJudgment);
		assertThat(resultActual.getRight()).isEqualTo(new ArrayList<>());
		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
		assertThat(lstAccAbse)
		.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
				x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
				x -> x.getUnbalanceNumber().getTime())
		.containsExactly(
				Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554ddfk", SID, MngDataStatus.RECORD, false,
						Optional.of(GeneralDate.ymd(2019, 11, 3)), 1.0, Optional.of(new AttendanceTime(1)),
						OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(10))),
				Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554bbbb", SID, MngDataStatus.RECORD, false,
						Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, Optional.of(new AttendanceTime(120)),
						OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(0))));
	}

	
	// 相殺判定の返すパラメータ
	/*
	 * 　テストしたい内容
	 *     　エラーメッセージに追加がある
	 * 
	 * 　準備するデータ
	 * 　　逐次発生の休暇明細（消化）.年月日 < 逐次発生の休暇明細（発生）.年月日
	 * 　　先取りをできない
	 * 　　　
	 * */
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
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
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
						new AttendanceTime(480), new AttendanceTime(240)),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(true, Optional.empty()),
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
				new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false,  Optional.of(GeneralDate.ymd(2019, 04, 8))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554ddde")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(0))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
												Optional.of(new AttendanceTime(0))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)),
				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554dddf")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(0))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(240))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)));
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		new Expectations() {
			{

			}

		};
		lstAccAbse.sort(new AccumulationAbsenceDetailComparator());
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
				.process(require, SID, lstTimeLap, lstAccAbse, typeJudgment);

		assertThat(resultActual.getRight()).isEqualTo(new ArrayList<>());
		assertThat(resultActual.getLeft()).isEqualTo(Optional.of(DayOffError.PREFETCH_ERROR));
	}

	// 相殺判定の返すパラメータ
	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新 
	 * 
	 * 　準備するデータ
	 *
	 * 　　相殺処理を行ったら、すべての逐次発生の休暇明細（発生）が0になるデータ
	 * */
	@Test
	public void testUpdateUnbNumMagTimeFalse() {

		List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
				new TimeLapseVacationSetting(new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),
						true, 30, true, Optional.of(false), Optional.of(1)));

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddfk")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(120))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(120))))
										.build(),
						new AttendanceTime(0), new AttendanceTime(0)),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.build()
				);
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		lstAccAbse.sort(new AccumulationAbsenceDetailComparator());
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
				.process(require, SID, lstTimeLap, lstAccAbse, typeJudgment);
		assertThat(resultActual.getRight())
				.extracting(x -> x.getDateOfUse(), x -> x.getDayNumberUsed(), x -> x.getOutbreakDay(),
						x -> x.getTargetSelectionAtr())
				.contains(
						Tuple.tuple(GeneralDate.ymd(2019, 04, 04), new ReserveLeaveRemainingDayNumber(1.0),
								GeneralDate.ymd(2019, 11, 3), TargetSelectionAtr.AUTOMATIC));
	}

	// test offsetJudgment
	// 相殺判定
	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新しない
	 * 
	 * 　準備するデータ
	 * 　　期限切れーー ngay nghi bu truoc ngay het han cua ngay lam bu
	 * */
	@Test
	@SuppressWarnings("unchecked")
	public void testCase1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method privateMethod = OffsetChronologicalOrder.class.getDeclaredMethod("offsetJudgment",
				TimeLapseVacationSetting.class, AccumulationAbsenceDetail.class, AccumulationAbsenceDetail.class,
				TypeOffsetJudgment.class);
		privateMethod.setAccessible(true);

		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)), true, 30, true,
				Optional.of(true), Optional.of(1));

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
				.invoke(OffsetChronologicalOrder.class, setting, dig, occ, TypeOffsetJudgment.REAMAIN);

		assertThat(returnValue.getLeft()).isEqualTo(OffsetJudgment.SUCCESS);

	}

	// test offsetJudgment
	// 相殺判定
		/*
		 * 　テストしたい内容
		 *     　未相殺数を更新しない
		 * 
		 * 　準備するデータ
		 * 　　逐次発生の休暇明細（発生）の未相殺数がない
		 * 　　　→　未相殺数を更新しない
		 * */
	@Test
	@SuppressWarnings("unchecked")
	public void testCase2() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method privateMethod = OffsetChronologicalOrder.class.getDeclaredMethod("offsetJudgment",
				TimeLapseVacationSetting.class, AccumulationAbsenceDetail.class, AccumulationAbsenceDetail.class,
				TypeOffsetJudgment.class);
		privateMethod.setAccessible(true);

		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)), true, 30, true,
				Optional.of(true), Optional.of(1));

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
				.invoke(OffsetChronologicalOrder.class, setting, dig, occ, TypeOffsetJudgment.REAMAIN);

		assertThat(returnValue.getLeft()).isEqualTo(OffsetJudgment.SUCCESS);

	}

	//// 先取りをできるか
	// 相殺判定
	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新しない
	 *     　エラーメッセージに追加がある
	 * 
	 * 　準備するデータ
	 * 　　先取りをできない
	 * 　　　逐次発生の休暇明細（消化）.年月日 < 逐次発生の休暇明細（発生）.年月日
	 * 　　　→　未相殺数を更新しない
	 * */
	@Test
	@SuppressWarnings("unchecked")
	public void testCase3() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method privateMethod = OffsetChronologicalOrder.class.getDeclaredMethod("offsetJudgment",
				TimeLapseVacationSetting.class, AccumulationAbsenceDetail.class, AccumulationAbsenceDetail.class,
				TypeOffsetJudgment.class);
		privateMethod.setAccessible(true);

		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)), true, 30, false,
				Optional.of(true), Optional.of(1));

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
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 12, 15))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddde")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new AttendanceTime(480), new AttendanceTime(240));

		Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>> returnValue = (Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>>) privateMethod
				.invoke(OffsetChronologicalOrder.class, setting, dig, occ, TypeOffsetJudgment.REAMAIN);

		assertThat(returnValue.getLeft()).isEqualTo(OffsetJudgment.ERROR);

	}

	// 相殺判定
	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新
	 *     　→　データをオフセットできます
	 * 
	 * 　準備するデータ
	 * */
	@Test
	@SuppressWarnings("unchecked")
	public void testCase4() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method privateMethod = OffsetChronologicalOrder.class.getDeclaredMethod("offsetJudgment",
				TimeLapseVacationSetting.class, AccumulationAbsenceDetail.class, AccumulationAbsenceDetail.class,
				TypeOffsetJudgment.class);
		privateMethod.setAccessible(true);

		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)), true, 30, false,
				Optional.of(true), Optional.of(1));

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
				.invoke(OffsetChronologicalOrder.class, setting, dig, occ, TypeOffsetJudgment.REAMAIN);

		assertThat(returnValue.getLeft()).isEqualTo(OffsetJudgment.SUCCESS);

	}

}
