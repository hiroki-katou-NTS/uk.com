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
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
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
	 *     　INPUTとOUTPUTが同じであること
	 * 
	 * 　準備するデータ
	 * 　　「逐次発生の休暇明細」（消化）．未相殺時間数 がない
	 * 　　　時間管理
	 * 　　　
	 * */
	@Test
	public void test() {

		List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
				new TimeLapseVacationSetting(
						new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),// 期間
						true, //管理区分
						30, //使用期限
						true, // 先取り許可
						Optional.of(true), //時間管理区分
						Optional.of(1)));// 時間消化単位

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				DaikyuFurikyuHelper.createDetailDefaultUnba(true, //代休
						OccurrenceDigClass.DIGESTION,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 3)), //年月日
						"a1", 
						1.0, 0),//未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, //代休
						OccurrenceDigClass.OCCURRENCE,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 4)),//年月日
						"a2", 
						1.0, 0)//未相殺数

		);
		
		
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		lstAccAbse.sort(new AccumulationAbsenceDetailComparator());
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
				.process(require, SID, lstTimeLap, lstAccAbse, typeJudgment);
		
		assertThat(resultActual.getRight()).isEqualTo(new ArrayList<>());
		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
		
		assertThat(lstAccAbse)
		.extracting(x -> x.getManageId(),
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),// 年月日
				x -> x.getOccurrentClass(), //発生消化区分
				x -> x.getUnbalanceNumber().getDay().v(), x -> x.getUnbalanceNumber().getTime())//未相殺数
				.containsExactly(
						Tuple.tuple("a1", false, Optional.of(GeneralDate.ymd(2019, 11, 3)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),
						Tuple.tuple("a2", false, Optional.of(GeneralDate.ymd(2019, 11, 4)),
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
				new TimeLapseVacationSetting(
						new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),//期間
						true, //管理区分
						30, // 使用期限
						true, // 先取り許可
						Optional.of(false), // 時間管理区分
						Optional.of(1)));// 時間消化単位

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				DaikyuFurikyuHelper.createDetailDefaultUnba(true,  //代休
						OccurrenceDigClass.DIGESTION,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 3)), //年月日
						"a1", 
						0.0, 10),//未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, //代休
						OccurrenceDigClass.OCCURRENCE,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 4)),//年月日
						"a2",
						1.0, 0));//未相殺数
				
		TypeOffsetJudgment typeJudgment = TypeOffsetJudgment.REAMAIN;
		lstAccAbse.sort(new AccumulationAbsenceDetailComparator());
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> resultActual = OffsetChronologicalOrder
				.process(require, SID, lstTimeLap, lstAccAbse, typeJudgment);
		assertThat(resultActual.getRight()).isEqualTo(new ArrayList<>());
		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
		assertThat(lstAccAbse)
		.extracting(x -> x.getManageId(), x -> x.getDataAtr(),
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
				x -> x.getOccurrentClass(),
				x -> x.getUnbalanceNumber().getDay().v(),
				x -> x.getUnbalanceNumber().getTime())
		.containsExactly(
				Tuple.tuple("a1", MngDataStatus.RECORD, false,
						Optional.of(GeneralDate.ymd(2019, 11, 3)), 
						OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(10))),
				Tuple.tuple("a2", MngDataStatus.RECORD, false,
						Optional.of(GeneralDate.ymd(2019, 11, 4)),
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
				new TimeLapseVacationSetting(
						new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),//期間
						true, //管理区分
						30, // 使用期限
						false, // 先取り許可
						Optional.of(true), // 時間管理区分
						Optional.of(1)));// 時間消化単位

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 3)), // 年月日
				"a3", 0.0, 0), // 未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
						"a2", 1.0, 120), // 未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
						OccurrenceDigClass.DIGESTION, // 発生消化区分
						Optional.empty(), // 年月日
						"a4", 1.0, 120), // 未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
						OccurrenceDigClass.DIGESTION, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 4)), // 年月日
						"a5", 1.0, 120), // 未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 8)), // 年月日
						"a6", 0.0, 0), // 未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 10)), // 年月日
						"a9", 1.0, 480)// 未相殺数

		);
		
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
	 * 　　相殺判定後、次の「逐次発生の休暇明細」(発生)チェック
	 * */
	@Test
	public void testUpdateContinue() {

		List<TimeLapseVacationSetting> lstTimeLap = Arrays.asList(
				new TimeLapseVacationSetting(
						new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),//期間
						true, //管理区分
						30, // 使用期限
						true, // 先取り許可
						Optional.of(false), // 時間管理区分
						Optional.of(1)));// 時間消化単位
		
		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a3", 1.0, 120), // 未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 3)), // 年月日
						"a2", 0.5, 120), // 未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 10, 3)), // 年月日
						"a10", 0.5, 120), // 未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
						OccurrenceDigClass.DIGESTION, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 4)), // 年月日
						"a5", 1.0, 120)// 未相殺数
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
								GeneralDate.ymd(2019, 10, 3), TargetSelectionAtr.AUTOMATIC),
						Tuple.tuple(GeneralDate.ymd(2019, 04, 04), new ReserveLeaveRemainingDayNumber(0.5),
								GeneralDate.ymd(2019, 11, 3), TargetSelectionAtr.AUTOMATIC));
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
				new TimeLapseVacationSetting(
						new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)),//期間
						true, //管理区分
						30, // 使用期限
						true, // 先取り許可
						Optional.of(false), // 時間管理区分
						Optional.of(1)));// 時間消化単位

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a1", 1.0, 120), // 未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
						OccurrenceDigClass.OCCURRENCE, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 3)), // 年月日
						"a2", 0.5, 120), // 未相殺数

				DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
						OccurrenceDigClass.DIGESTION, // 発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 4)), // 年月日
						"a5", 1.0, 120)// 未相殺数
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
		//TODO: thieu check du lieu khi sd het
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
				new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)), // 期間
				true, // 管理区分
				30, // 使用期限
				true, // 先取り許可
				Optional.of(true), // 時間管理区分
				Optional.of(1));// 時間消化単位

		AccumulationAbsenceDetail dig = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a2", 1.0, 0);// 未相殺数

		AccumulationAbsenceDetail occ = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 1, 15)), // 年月日
				"a2", GeneralDate.ymd(2019, 10, 8), //期限日
				1.0, 480);// 未相殺数
				
		Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>> returnValue = (Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>>) privateMethod
				.invoke(OffsetChronologicalOrder.class, setting, dig, occ, TypeOffsetJudgment.REAMAIN);

		assertThat(returnValue.getLeft()).isEqualTo(OffsetJudgment.SUCCESS);
		//check het han

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
				new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)), // 期間
				true, // 管理区分
				30, // 使用期限
				true, // 先取り許可
				Optional.of(true), // 時間管理区分
				Optional.of(1));// 時間消化単位


		AccumulationAbsenceDetail dig = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 1.0, 0);// 未相殺数

		AccumulationAbsenceDetail occ = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 15)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				0.0, 0);// 未相殺数
		
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
				new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)), // 期間
				true, // 管理区分
				30, // 使用期限
				false, // 先取り許可
				Optional.of(true), // 時間管理区分
				Optional.of(1));// 時間消化単位


		AccumulationAbsenceDetail dig = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 1.0, 0);// 未相殺数

		AccumulationAbsenceDetail occ = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 15)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				0.0, 120);// 未相殺数
		
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
				new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2020, 1, 1)), // 期間
				true, // 管理区分
				30, // 使用期限
				false, // 先取り許可
				Optional.of(true), // 時間管理区分
				Optional.of(1));// 時間消化単位

		AccumulationAbsenceDetail dig = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 1.0, 0);// 未相殺数

		AccumulationAbsenceDetail occ = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 10, 15)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 30), //期限日
				0.0, 120);// 未相殺数
		
		Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>> returnValue = (Pair<OffsetJudgment, Optional<SeqVacationAssociationInfo>>) privateMethod
				.invoke(OffsetChronologicalOrder.class, setting, dig, occ, TypeOffsetJudgment.REAMAIN);

		assertThat(returnValue.getLeft()).isEqualTo(OffsetJudgment.SUCCESS);
		// check update

	}

}
