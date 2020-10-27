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
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

@RunWith(JMockit.class)
public class GetUnbalancedLeaveTemporaryTest {

	@Injectable
	private GetUnbalancedLeaveTemporary.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * テストしたい内容 暫定データから消化データを作成する
	 * 
	 * 準備するデータ 暫定代休管理データがある 
	 * →紐づけがなくて残ってるやつ 
	 * →紐づけしても残ってるやつ 
	 * 暫定休出代休紐付け管理がある 
	 * →最初から残ってない
	 * →紐づけしたら残ってない モード : 月次か
	 * 
	 */
	@Test
	public void testModeMonth() {
		List<InterimDayOffMng> dayOffMng = Arrays.asList(
				DaikyuFurikyuHelper.createDayOff("d1", //暫定代休管理データID
						0, 1.0),//必要数
                  DaikyuFurikyuHelper.createDayOff("d3", //暫定代休管理データID 
						480, 1.0)); //必要数

		List<InterimBreakMng> breakMng = Arrays.asList(DaikyuFurikyuHelper.createBreak("k1", // 暫定休出管理データID
				GeneralDate.ymd(2020, 6, 6), // 使用期限日
				480, 1.0), // 未使用数,
				DaikyuFurikyuHelper.createBreak("k2", // 暫定休出管理データID
						GeneralDate.ymd(2019, 6, 6), // 使用期限日
						480, 1.0));// 未使用数

		List<InterimRemain> interimMng = Arrays.asList(
				DaikyuFurikyuHelper.createRemain("k1", GeneralDate.ymd(2019, 11, 5), CreateAtr.SCHEDULE,
						RemainType.BREAK),
				DaikyuFurikyuHelper.createRemain("k2", GeneralDate.ymd(2019, 11, 6), CreateAtr.RECORD,
						RemainType.BREAK),

				DaikyuFurikyuHelper.createRemain("d1", GeneralDate.ymd(2019, 11, 9), CreateAtr.RECORD,
						RemainType.SUBHOLIDAY),
				DaikyuFurikyuHelper.createRemain("d3", GeneralDate.ymd(2019, 11, 10), CreateAtr.RECORD,
						RemainType.SUBHOLIDAY));

		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
				true,//モード : True: 月次か
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				false, //上書きフラグ
				interimMng, breakMng, dayOffMng, //暫定残数管理データ
				Optional.empty(),//前回代休の集計結果
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ
		
		new Expectations() {
			{

//				require.getBreakDayOffMng("d3", anyBoolean, (DataManagementAtr) any);
//				//暫定休出代休紐付け管理
//				result = Arrays.asList(
//						new InterimBreakDayOffMng("", DataManagementAtr.INTERIM, "d3",
//								DataManagementAtr.INTERIM, new UseTime(480), new UseDay(1d), SelectedAtr.AUTOMATIC));
				require.getBycomDayOffID(anyString, GeneralDate.ymd(2019, 11, 10));
				result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 10), GeneralDate.ymd(2019, 11, 19), 1.0));

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnbalancedLeaveTemporary.process(require, inputParam);

		assertThat(actualResult).extracting(x -> x.getManageId(),
				x -> x.getDataAtr(),//状態
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),//年月日
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),// 発生数
				x -> x.getOccurrentClass(), //発生消化区分
				x -> x.getUnbalanceNumber().getDay().v(), x -> x.getUnbalanceNumber().getTime(),//未相殺数
     			x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE ? ((UnbalanceVacation) x).getDeadline()//期限日
						: Optional.empty())
				.containsExactly(
						Tuple.tuple("d1", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 9)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0)), Optional.empty()),
						Tuple.tuple("d3", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 10)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(480)),
								Optional.empty()));

	}

	/*
	 * テストしたい内容 
	 * 暫定データから消化データを作成する
	 * 休出データがある　→　作成しない
	 * 準備するデータ 
	 * 　暫定代休管理データがある 
	 * →紐づけがなくて残ってるやつ 
	 * →紐づけしても残ってるやつ 
	 * 　暫定休出代休紐付け管理がある 
	 * →最初から残ってない
	 * →紐づけしたら残ってない 
	 * モード : 月次か
	 * 
	 */
	@Test
	public void testModeMonthNokyu() {
		List<InterimDayOffMng> dayOffMng = Arrays.asList(
				DaikyuFurikyuHelper.createDayOff("d1", //暫定代休管理データID
						0, 1.0),//必要数
                  DaikyuFurikyuHelper.createDayOff("d3", //暫定代休管理データID 
						480, 1.0)); //必要数

		List<InterimRemain> interimMng = Arrays.asList(
				DaikyuFurikyuHelper.createRemain("k1", GeneralDate.ymd(2019, 11, 5), CreateAtr.SCHEDULE,
						RemainType.BREAK),
				DaikyuFurikyuHelper.createRemain("k2", GeneralDate.ymd(2019, 11, 6), CreateAtr.RECORD,
						RemainType.BREAK),

				DaikyuFurikyuHelper.createRemain("d1", GeneralDate.ymd(2019, 11, 9), CreateAtr.RECORD,
						RemainType.SUBHOLIDAY),
				DaikyuFurikyuHelper.createRemain("d3", GeneralDate.ymd(2019, 11, 10), CreateAtr.RECORD,
						RemainType.SUBHOLIDAY));

		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
				true,//モード : True: 月次か
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				false, //上書きフラグ
				interimMng, new ArrayList<>(), dayOffMng, //暫定残数管理データ
				Optional.empty(),//前回代休の集計結果
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ
		
		new Expectations() {
			{

//				require.getBreakDayOffMng("d3", anyBoolean, (DataManagementAtr) any);
//				//暫定休出代休紐付け管理
//				result = Arrays.asList(
//						new InterimBreakDayOffMng("", DataManagementAtr.INTERIM, "d3",
//								DataManagementAtr.INTERIM, new UseTime(480), new UseDay(1d), SelectedAtr.AUTOMATIC));
				require.getBycomDayOffID(anyString, GeneralDate.ymd(2019, 11, 10));
				result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 10), GeneralDate.ymd(2019, 11, 19), 1.0));

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnbalancedLeaveTemporary.process(require, inputParam);

		assertThat(actualResult).extracting(x -> x.getManageId(),
				x -> x.getDataAtr(),//状態
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),//年月日
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),// 発生数
				x -> x.getOccurrentClass(), //発生消化区分
				x -> x.getUnbalanceNumber().getDay().v(), x -> x.getUnbalanceNumber().getTime(),//未相殺数
     			x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE ? ((UnbalanceVacation) x).getDeadline()//期限日
						: Optional.empty())
				.containsExactly(
						Tuple.tuple("d1", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 9)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0)), Optional.empty()),
						Tuple.tuple("d3", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 10)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(480)),
								Optional.empty()));

	}

	
	/*
	 * テストしたい内容 暫定データから消化データを作成する
	 * 
	 * 準備するデータ 暫定代休管理データがある 
	 * →紐づけがなくて残ってるやつ 
	 * →紐づけしても残ってるやつ 
	 * 暫定休出代休紐付け管理がある 
	 * →最初から残ってない
	 * →紐づけしたら残ってない 
	 * モード : その他か
	 * 
	 */
	@Test
	public void testModeOther() {
		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
				false,//モード 
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				false, //上書きフラグ
				new ArrayList<>(), new ArrayList<>(),  new ArrayList<>(),//暫定残数管理データ
				Optional.empty(),//前回代休の集計結果
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ

		new Expectations() {
			{

				//暫定残数管理データ
				require.getRemainBySidPriod(anyString, (DatePeriod) any, (RemainType) any);
				result = Arrays.asList(
						DaikyuFurikyuHelper.createRemain("k1", GeneralDate.ymd(2019, 11, 4),
								CreateAtr.SCHEDULE, RemainType.SUBHOLIDAY),
						DaikyuFurikyuHelper.createRemain("k3", GeneralDate.ymd(2019, 11, 5),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY),
						DaikyuFurikyuHelper.createRemain("k4", GeneralDate.ymd(2019, 11, 8),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY),
						DaikyuFurikyuHelper.createRemain("d1", GeneralDate.ymd(2019, 11, 9),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY),
						DaikyuFurikyuHelper.createRemain("d2", GeneralDate.ymd(2019, 11, 10),
								CreateAtr.RECORD, RemainType.SUBHOLIDAY));

				//暫定代休管理データ
				require.getDayOffBySidPeriod(anyString, (DatePeriod) any);
				result = Arrays.asList(DaikyuFurikyuHelper.createDayOff("k1", 480, 1.0),
						DaikyuFurikyuHelper.createDayOff("k3", 480, 1.0),
						DaikyuFurikyuHelper.createDayOff("k4", 480, 1.0),
						DaikyuFurikyuHelper.createDayOff("d1", 480, 1.0),
						DaikyuFurikyuHelper.createDayOff("d2", 0, 0.0));

				//暫定休出代休紐付け管理
//				require.getBreakDayOffMng("k3", false, DataManagementAtr.INTERIM);
//				result = Arrays.asList(
//						new InterimBreakDayOffMng("", DataManagementAtr.INTERIM, "k3",
//								DataManagementAtr.INTERIM, new UseTime(0), new UseDay(0d), SelectedAtr.AUTOMATIC));
				require.getBycomDayOffID(anyString, GeneralDate.ymd(2019, 11, 10));
				result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 5), GeneralDate.ymd(2019, 11, 19), 0.0));

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnbalancedLeaveTemporary.process(require, inputParam);

		assertThat(actualResult).extracting(x -> x.getManageId(), 
				x -> x.getDataAtr(),//状態
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),//年月日
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),// 発生数
				x -> x.getOccurrentClass(), //発生消化区分
				x -> x.getUnbalanceNumber().getDay().v(), x -> x.getUnbalanceNumber().getTime(),//未相殺数
     			x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE ? ((UnbalanceVacation) x).getDeadline()//期限日
						: Optional.empty())
				.containsExactly(Tuple.tuple("k1", MngDataStatus.SCHEDULE, false,
						Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, Optional.of(new AttendanceTime(480)),
						OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(480)), Optional.empty()),

						Tuple.tuple("k3", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 05)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(480)),
								Optional.empty()),

						Tuple.tuple("k4", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 8)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(480)),
								Optional.empty()),

						Tuple.tuple("d1", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 9)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(480)),
								Optional.empty()),

						Tuple.tuple("d2", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 10)), 0.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(0)),
								Optional.empty()

						));
	}

}
