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
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;

@RunWith(JMockit.class)
public class GetUnbalanceLeaveFixedTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private GetUnbalanceLeaveFixed.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * 　テストしたい内容
	 * 　　データを作成しない
	 * 
	 * 　準備するデータ
	 * 　　確定データがない
	 * 　　　
	 * */
	@Test
	public void testUnbalanceUnusedEmpty() {

		new Expectations() {
			{
				require.getBySidYmd(anyString, anyString, (GeneralDate) any);
				result = new ArrayList<>();

			}
		};

		List<AccumulationAbsenceDetail> actualResult = GetUnbalanceLeaveFixed.getUnbalanceUnused(require, CID, SID,
				GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30),//集計開始日, 集計終了日
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));
		
		assertThat(actualResult).isEqualTo(new ArrayList<>());
	}

	/*
	 * 　テストしたい内容
	 * 　　未相殺のデータだけが取得できるか。
	 * 
	 * 　準備するデータ
	 * 　　未相殺のデータ
	 * 　　　→紐づけがなくて残ってるやつ
	 * 　　　→紐づけしても残ってるやつ
	 * 　　相殺済みのデータ
	 * 　　　→最初から残ってない
	 * 　　　→紐づけしたら残ってない

	 * */
	@Test
	public void testUnbalanceUnused() {

		//代休管理データ
		List<CompensatoryDayOffManaData> lstComMock = new ArrayList<>();
		lstComMock.add(new CompensatoryDayOffManaData("d1", CID, SID, false,
				GeneralDate.ymd(2019, 11, 10), 1.0, 240, 1.0, 240));
		lstComMock.add(new CompensatoryDayOffManaData("d2", CID, SID, false,
				GeneralDate.ymd(2019, 11, 12), 1.0, 240, 1.0, 240));
		lstComMock.add(new CompensatoryDayOffManaData("d3", CID, SID, false,
				GeneralDate.ymd(2019, 11, 13), 1.0, 240, 0.0, 0));
		lstComMock.add(new CompensatoryDayOffManaData("d4", CID, SID, false,
				GeneralDate.ymd(2019, 11, 14), 1.0, 240, 1.0, 240));

		new Expectations() {
			{
				require.getBySidYmd(anyString, anyString, (GeneralDate) any);
				result = lstComMock;

//				//暫定休出代休紐付け管理
//				require.getDayOffByIdAndDataAtr(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM,
//						"d2");
//				result = Arrays.asList(
//						new InterimBreakDayOffMng("", DataManagementAtr.INTERIM, "d2",
//								DataManagementAtr.CONFIRM, new UseTime(240), new UseDay(1.0), SelectedAtr.AUTOMATIC));
//
//				//暫定休出代休紐付け管理
//				require.getDayOffByIdAndDataAtr(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM,
//						"d1");
//				result = Arrays.asList(
//						new InterimBreakDayOffMng("", DataManagementAtr.INTERIM, "d1",
//								DataManagementAtr.CONFIRM, new UseTime(120), new UseDay(0.5), SelectedAtr.AUTOMATIC));
				
				require.getBycomDayOffID(SID, GeneralDate.ymd(2019, 11, 12));
				result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 12), GeneralDate.ymd(2019, 11, 19), 1.0));
				
				require.getBycomDayOffID(SID, GeneralDate.ymd(2019, 11, 10));
				result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 10), GeneralDate.ymd(2019, 11, 19), 0.5));

			}
		};

		List<AccumulationAbsenceDetail> actualResult = GetUnbalanceLeaveFixed
				.getUnbalanceUnused(require, CID, SID, 
						GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30),//集計開始日, 集計終了日
						new FixedManagementDataMonth(//月別確定管理データ
								Arrays.asList(new CompensatoryDayOffManaData("d5",
										CID, SID, true, null, 1.0, 240, 1.0, 240)),//代休管理データ
								new ArrayList<>()));

		assertThat(actualResult)
				.extracting(x -> x.getManageId(), 
						x -> x.getDataAtr(),//状態
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),//年月日
						x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),//発生数
						x -> x.getOccurrentClass(), //発生消化区分
						x -> x.getUnbalanceNumber().getDay().v(), x -> x.getUnbalanceNumber().getTime())//未相殺数
				.containsExactly(
						Tuple.tuple("d1", MngDataStatus.CONFIRMED, false,
								Optional.of(GeneralDate.ymd(2019, 11, 10)), 1.0, Optional.of(new AttendanceTime(240)),
								OccurrenceDigClass.DIGESTION, 0.5, Optional.of(new AttendanceTime(240))),
						Tuple.tuple("d2", MngDataStatus.CONFIRMED, false,
								Optional.of(GeneralDate.ymd(2019, 11, 12)), 1.0, Optional.of(new AttendanceTime(240)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(240))),
						Tuple.tuple("d4", MngDataStatus.CONFIRMED, false,
								Optional.of(GeneralDate.ymd(2019, 11, 14)), 1.0, Optional.of(new AttendanceTime(240)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(240))),
						Tuple.tuple("d5", MngDataStatus.CONFIRMED, true,
								Optional.empty(), 1.0, Optional.of(new AttendanceTime(240)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(240))));
	}

}
