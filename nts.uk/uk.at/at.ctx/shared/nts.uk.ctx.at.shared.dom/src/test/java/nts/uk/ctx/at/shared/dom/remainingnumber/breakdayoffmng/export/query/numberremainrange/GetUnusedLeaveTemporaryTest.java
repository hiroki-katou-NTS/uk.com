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
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
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
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

@RunWith(JMockit.class)
public class GetUnusedLeaveTemporaryTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private GetUnusedLeaveTemporary.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * テストしたい内容 
	 * 暫定データから発生データを作成する
	 * 
	 * 準備するデータ 
	 * 暫定休出管理データがある 
	 * →紐づけがなくて残ってるやつ 
	 * →紐づけしても残ってるやつ 
	 * 暫定休出代休紐付け管理がある 
	 * →最初から残ってない
	 * →紐づけしたら残ってない 
	 * モード : 月次か
	 * 
	 */
	@Test
	public void testModeMonth() {

		List<InterimDayOffMng> dayOffMng = Arrays.asList(DaikyuFurikyuHelper.createDayOff("d1", 
				0, 1.0),//未相殺数
				DaikyuFurikyuHelper.createDayOff("d2", //ID
						480, 1.0));//未相殺数

		// BREAK filter
		List<InterimBreakMng> breakMng = Arrays.asList(
				DaikyuFurikyuHelper.createBreak("k1", GeneralDate.ymd(2020, 6, 6), //使用期限日
						480, 1.0),//未使用数
				DaikyuFurikyuHelper.createBreak("k2", GeneralDate.ymd(2020, 6, 6), 0, 0.0),
				DaikyuFurikyuHelper.createBreak("k3", GeneralDate.ymd(2019, 6, 6), 480, 1.0));

		List<InterimRemain> interimMng = Arrays.asList(
				DaikyuFurikyuHelper.createRemain("k1", GeneralDate.ymd(2019, 11, 5), CreateAtr.SCHEDULE,
						RemainType.BREAK),
				DaikyuFurikyuHelper.createRemain("k2", GeneralDate.ymd(2019, 11, 8), CreateAtr.SCHEDULE,
						RemainType.BREAK),
				DaikyuFurikyuHelper.createRemain("k3", GeneralDate.ymd(2019, 11, 6), CreateAtr.RECORD,
						RemainType.BREAK),

				DaikyuFurikyuHelper.createRemain("d1", GeneralDate.ymd(2019, 11, 9), CreateAtr.RECORD,
						RemainType.SUBHOLIDAY),
				DaikyuFurikyuHelper.createRemain("d2", GeneralDate.ymd(2019, 11, 10), CreateAtr.RECORD,
						RemainType.SUBHOLIDAY));

		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
				true,//モード 
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				false, //上書きフラグ
				interimMng, breakMng, dayOffMng,//暫定残数管理データ
				Optional.empty(),//前回代休の集計結果
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ

		new Expectations() {
			{
				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.findComLeavEmpSet(CID, anyString);
				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.YES, ManageDistinct.YES,
						"02");
				
				//require.getBreakDayOffMng("k1", anyBoolean, (DataManagementAtr) any);
				require.getByLeaveID(SID, GeneralDate.ymd(2019, 11, 5));
				result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 5), GeneralDate.ymd(2019, 11, 15), 0.5));
//				result = Arrays.asList(
//						new InterimBreakDayOffMng("", DataManagementAtr.INTERIM, "k1",
//								DataManagementAtr.INTERIM, new UseTime(120), new UseDay(0.5), SelectedAtr.AUTOMATIC));

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnusedLeaveTemporary.process(require, inputParam);

		assertThat(actualResult).extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
				x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
				x -> x.getUnbalanceNumber().getTime(),
				x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE ? ((UnbalanceVacation) x).getDeadline()
						: Optional.empty())
				.containsExactly(
						Tuple.tuple("k1", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 5)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.OCCURRENCE, 0.5, Optional.of(new AttendanceTime(480)),
								GeneralDate.ymd(2020, 06, 06)),
						Tuple.tuple("k2", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 8)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.OCCURRENCE, 0.0, Optional.of(new AttendanceTime(0)),
								GeneralDate.ymd(2020, 06, 06)),
						Tuple.tuple("k3", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 6)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(480)),
								GeneralDate.ymd(2019, 06, 06)));
	}

	/*
	 * テストしたい内容 
	 * 暫定データから発生データを作成する
	 * 
	 * 準備するデータ 
	 * 暫定休出管理データがある 
	 * →紐づけがなくて残ってるやつ 
	 * →紐づけしても残ってるやつ 
	 * 暫定休出代休紐付け管理がある 
	 * →最初から残ってない
	 * →紐づけしたら残ってない 
	 * モード : 月次か
	 * 
	 */
	@Test
	public void testModeMonthNoDaikyu() {

		// BREAK filter
				List<InterimBreakMng> breakMng = Arrays.asList(
						DaikyuFurikyuHelper.createBreak("k1", GeneralDate.ymd(2020, 6, 6), //使用期限日
								480, 1.0),//未使用数
						DaikyuFurikyuHelper.createBreak("k2", GeneralDate.ymd(2020, 6, 6), 0, 0.0),
						DaikyuFurikyuHelper.createBreak("k3", GeneralDate.ymd(2019, 6, 6), 480, 1.0));

				List<InterimRemain> interimMng = Arrays.asList(
						DaikyuFurikyuHelper.createRemain("k1", GeneralDate.ymd(2019, 11, 5), CreateAtr.SCHEDULE,
								RemainType.BREAK),
						DaikyuFurikyuHelper.createRemain("k2", GeneralDate.ymd(2019, 11, 8), CreateAtr.SCHEDULE,
								RemainType.BREAK),
						DaikyuFurikyuHelper.createRemain("k3", GeneralDate.ymd(2019, 11, 6), CreateAtr.RECORD,
								RemainType.BREAK)
					);

				BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
						new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), //集計開始日, 集計終了日
						true,//モード 
						GeneralDate.ymd(2019, 11, 30), //画面表示日
						false, //上書きフラグ
						interimMng, breakMng, new ArrayList<>(),//暫定残数管理データ
						Optional.empty(),//前回代休の集計結果
						new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ

				new Expectations() {
					{
						require.findEmploymentHistory(CID, SID, (GeneralDate) any);
						result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
								new DatePeriod(GeneralDate.min(), GeneralDate.max())));

						require.findComLeavEmpSet(CID, anyString);
						result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.YES, ManageDistinct.YES,
								"02");
						
						require.getByLeaveID(SID, GeneralDate.ymd(2019, 11, 5));
						result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 5), GeneralDate.ymd(2019, 11, 7), 0.5));
//						require.getBreakDayOffMng("k1", anyBoolean, (DataManagementAtr) any);
//						result = Arrays.asList(
//								new InterimBreakDayOffMng("", DataManagementAtr.INTERIM, "k1",
//										DataManagementAtr.INTERIM, new UseTime(120), new UseDay(0.5), SelectedAtr.AUTOMATIC));

					}

				};

				List<AccumulationAbsenceDetail> actualResult = GetUnusedLeaveTemporary.process(require, inputParam);

				assertThat(actualResult).extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
						x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
						x -> x.getUnbalanceNumber().getTime(),
						x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE ? ((UnbalanceVacation) x).getDeadline()
								: Optional.empty())
						.containsExactly(
								Tuple.tuple("k1", SID, MngDataStatus.SCHEDULE, false,
										Optional.of(GeneralDate.ymd(2019, 11, 5)), 1.0, Optional.of(new AttendanceTime(480)),
										OccurrenceDigClass.OCCURRENCE, 0.5, Optional.of(new AttendanceTime(480)),
										GeneralDate.ymd(2020, 06, 06)),
								Tuple.tuple("k2", SID, MngDataStatus.SCHEDULE, false,
										Optional.of(GeneralDate.ymd(2019, 11, 8)), 1.0, Optional.of(new AttendanceTime(480)),
										OccurrenceDigClass.OCCURRENCE, 0.0, Optional.of(new AttendanceTime(0)),
										GeneralDate.ymd(2020, 06, 06)),
								Tuple.tuple("k3", SID, MngDataStatus.RECORD, false,
										Optional.of(GeneralDate.ymd(2019, 11, 6)), 1.0, Optional.of(new AttendanceTime(480)),
										OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(480)),
										GeneralDate.ymd(2019, 06, 06)));
	}
	
	/*
	 * テストしたい内容 
	 * 暫定データから発生データを作成する
	 * 
	 * 準備するデータ 
	 * 暫定休出管理データがある 
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
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),//暫定残数管理データ
				Optional.empty(),//前回代休の集計結果
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ
		
		new Expectations() {
			{

				require.getByLeaveID(SID, GeneralDate.ymd(2019, 11, 5));
				result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 5), GeneralDate.ymd(2019, 11, 7), 0.5));

				// 暫定残数管理データ
				require.getRemainBySidPriod(SID, (DatePeriod) any, RemainType.BREAK);
				result = Arrays.asList(
						DaikyuFurikyuHelper.createRemain("k1", GeneralDate.ymd(2019, 11, 5),
								CreateAtr.SCHEDULE, RemainType.BREAK),
						DaikyuFurikyuHelper.createRemain("k2",  GeneralDate.ymd(2019, 11, 8),
								CreateAtr.SCHEDULE, RemainType.BREAK),
						DaikyuFurikyuHelper.createRemain("k3",  GeneralDate.ymd(2019, 11, 6),
								CreateAtr.RECORD, RemainType.BREAK));

				require.getBySidPeriod(SID, (DatePeriod) any);
				result = Arrays.asList(
						DaikyuFurikyuHelper.createBreak("k1", GeneralDate.ymd(2020, 6, 6), // 使用期限日
								480, 1.0), // 未使用数,

						DaikyuFurikyuHelper.createBreak("k2", GeneralDate.ymd(2020, 6, 6), 0, 0.0),
						DaikyuFurikyuHelper.createBreak("k3", GeneralDate.ymd(2019, 6, 6), 480, 1.0));

				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.findComLeavEmpSet(CID, anyString);
				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.YES, ManageDistinct.YES,
						"02");

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnusedLeaveTemporary.process(require, inputParam);

		assertThat(actualResult).extracting(x -> x.getManageId(), x -> x.getDataAtr(),
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
				x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
				x -> x.getUnbalanceNumber().getTime(),
				x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE ? ((UnbalanceVacation) x).getDeadline()
						: Optional.empty())
				.containsExactly(
						Tuple.tuple("k1", MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 5)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.OCCURRENCE, 0.5, Optional.of(new AttendanceTime(480)),
								GeneralDate.ymd(2020, 06, 06)),
						Tuple.tuple("k2", MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 8)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.OCCURRENCE, 0.0, Optional.of(new AttendanceTime(0)),
								GeneralDate.ymd(2020, 06, 06)),
						Tuple.tuple("k3", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 6)), 1.0, Optional.of(new AttendanceTime(480)),
								OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(480)),
								GeneralDate.ymd(2019, 06, 06)));
	}

}
