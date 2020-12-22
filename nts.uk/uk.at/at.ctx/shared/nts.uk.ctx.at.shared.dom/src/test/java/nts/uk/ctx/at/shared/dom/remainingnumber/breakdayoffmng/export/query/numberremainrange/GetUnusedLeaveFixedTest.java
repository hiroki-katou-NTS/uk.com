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
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;

@RunWith(JMockit.class)
public class GetUnusedLeaveFixedTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private GetUnusedLeaveFixed.Require require;

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

		List<AccumulationAbsenceDetail> actualResult = GetUnusedLeaveFixed.getUnbalanceUnused(require, CID, SID,
				GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30),
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		assertThat(actualResult).isEqualTo(new ArrayList<>());
	}

	/*
	 * 　テストしたい内容
	 * 　　未使用数のデータだけが取得できるか。
	 * 
	 * 　準備するデータ
	 * 　　未使用数のデータ
	 * 　　　→紐づけがなくて残ってるやつ
	 * 　　　→紐づけしても残ってるやつ
	 * 　　相殺済みのデータ
	 * 　　　→最初から残ってない
	 * 　　　→紐づけしたら残ってない

	 * */
	@Test
	public void testUnbalanceUnused() {

		//休出管理データ
		List<LeaveManagementData> lstLeavMock = new ArrayList<>();

		lstLeavMock.add(new LeaveManagementData("k1", CID, SID, false,
				GeneralDate.ymd(2019, 11, 11), GeneralDate.max(), 1.0, 240, 1.0, 240, DigestionAtr.UNUSED.value, 0, 0));
		lstLeavMock.add(new LeaveManagementData("k2", CID, SID, false,
				GeneralDate.ymd(2019, 11, 13), GeneralDate.max(), 1.0, 240, 1.0, 240, DigestionAtr.UNUSED.value, 0, 0));
		lstLeavMock.add(new LeaveManagementData("k3", CID, SID, false,
				GeneralDate.ymd(2019, 11, 14), GeneralDate.max(), 1.0, 240, 0.0, 0, DigestionAtr.UNUSED.value, 0, 0));
		lstLeavMock.add(new LeaveManagementData("k4", CID, SID, false,
				GeneralDate.ymd(2019, 11, 15), GeneralDate.max(), 1.0, 240, 1.0, 240, DigestionAtr.UNUSED.value, 0, 0));

		new Expectations() {
			{

				require.getBySidYmd(CID, SID, (GeneralDate) any, (DigestionAtr) any);
				result = lstLeavMock;

				//暫定休出代休紐付け管理
//				require.getBreakByIdAndDataAtr(DataManagementAtr.CONFIRM, DataManagementAtr.INTERIM,
//						"k1");
//				result = Arrays.asList(
//						new InterimBreakDayOffMng("", DataManagementAtr.CONFIRM, "k1",
//								DataManagementAtr.INTERIM, new UseTime(240), new UseDay(1.0), SelectedAtr.AUTOMATIC));
//
//				require.getBreakByIdAndDataAtr(DataManagementAtr.CONFIRM, DataManagementAtr.INTERIM,
//						"k2");
//				result = Arrays.asList(
//						new InterimBreakDayOffMng("", DataManagementAtr.CONFIRM, "k2",
//								DataManagementAtr.INTERIM, new UseTime(120), new UseDay(0.5), SelectedAtr.AUTOMATIC));
				
				require.getByLeaveID(SID, GeneralDate.ymd(2019, 11, 11));
				result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 11), GeneralDate.ymd(2019, 11, 18),  1.0));
				
				require.getByLeaveID(SID, GeneralDate.ymd(2019, 11, 13));
				result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 13), GeneralDate.ymd(2019, 11, 19), 0.5));

			}
		};

		List<AccumulationAbsenceDetail> actualResult = GetUnusedLeaveFixed.getUnbalanceUnused(require, CID, SID,
				GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30),
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		assertThat(actualResult)
				.extracting(x -> x.getManageId(), 
						x -> x.getDataAtr(),//状態
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),//年月日
						x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),//発生数
						x -> x.getOccurrentClass(), //発生消化区分
						x -> x.getUnbalanceNumber().getDay().v(), x -> x.getUnbalanceNumber().getTime())//未相殺数
				.containsExactly(
						Tuple.tuple("k1",  MngDataStatus.CONFIRMED,
								false, Optional.of(GeneralDate.ymd(2019, 11, 11)), 1.0, Optional.of(new AttendanceTime(240)),
								OccurrenceDigClass.OCCURRENCE, 0.0, Optional.of(new AttendanceTime(240))),
						
						Tuple.tuple("k2",  MngDataStatus.CONFIRMED,
						false, Optional.of(GeneralDate.ymd(2019, 11, 13)), 1.0, Optional.of(new AttendanceTime(240)),
						OccurrenceDigClass.OCCURRENCE, 0.5, Optional.of(new AttendanceTime(240))),
						
						Tuple.tuple("k4",  MngDataStatus.CONFIRMED,
								false, Optional.of(GeneralDate.ymd(2019, 11, 15)), 1.0, Optional.of(new AttendanceTime(240)),
								OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(240)))
						);
	}

}
