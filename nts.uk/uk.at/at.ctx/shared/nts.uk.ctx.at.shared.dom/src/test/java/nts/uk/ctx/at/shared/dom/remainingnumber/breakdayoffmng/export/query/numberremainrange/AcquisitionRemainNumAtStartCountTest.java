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
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.CarryForwardDayTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;

@RunWith(JMockit.class)
public class AcquisitionRemainNumAtStartCountTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private AcquisitionRemainNumAtStartCount.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * テストしたい内容
	 *　　確定データから「逐次発生の休暇明細」を作成
	 * 　　繰越数を計算する
	 * 準備するデータ
	 * 　　代休管理データがある
	 * 　　　未相殺数がなくて作成しない
	 * 
　	 *　　休出管理データがある 
	 * 　　　未使用数がなくて作成しない
	 * 
	 * 　　繰越数がない（代休日数　＝　休出日数）
	 */
	@Test
	public void test() {

		new Expectations() {
			{
				require.getBySidYmd(anyString, anyString, (GeneralDate) any);
				result = Arrays.asList(
						new CompensatoryDayOffManaData("adda6a46-2cbe-48c8-85f8-c04ca554e133", CID, SID, false,
								GeneralDate.ymd(2019, 11, 9), 1.0, 1, 1.0, 0),
						new CompensatoryDayOffManaData("adda6a46-2cbe-48c8-85f8-c04ca554e134", CID, SID, false,
								GeneralDate.ymd(2019, 11, 10), 1.0, 1, 1.0, 0),
						new CompensatoryDayOffManaData("adda6a46-2cbe-48c8-85f8-c04ca554e135", CID, SID, false,
								GeneralDate.ymd(2019, 11, 11), 1.0, 1, 0.0, 0));

				require.getBySidYmd(anyString, anyString, (GeneralDate) any, (DigestionAtr) any);
				result = Arrays.asList(
						new LeaveManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e136", CID, SID, false,
								GeneralDate.ymd(2019, 11, 12), GeneralDate.max(), 1.0, 0, 1.0, 0,
								DigestionAtr.UNUSED.value, 0, 0),
						new LeaveManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e137", CID, SID, false,
								GeneralDate.ymd(2019, 11, 13), GeneralDate.max(), 1.0, 0, 1.0, 0,
								DigestionAtr.UNUSED.value, 0, 0),
						new LeaveManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e138", CID, SID, false,
								GeneralDate.ymd(2019, 11, 14), GeneralDate.max(), 1.0, 0, 0.0, 0,
								DigestionAtr.UNUSED.value, 0, 0));
			}
		};

		List<AccumulationAbsenceDetail> lstAccuAbsenDetail = new ArrayList<>();
		CarryForwardDayTimes resultActual = AcquisitionRemainNumAtStartCount.acquisition(require, CID, SID,
				GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2019, 11, 30), true, lstAccuAbsenDetail,
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));
		assertThat(resultActual.getCarryForwardDays()).isEqualTo(0.0);
		assertThat(resultActual.getCarryForwardTime()).isEqualTo(0);

		assertThat(lstAccuAbsenDetail).extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
				x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
				x -> x.getUnbalanceNumber().getTime()).containsExactly(

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e133", SID, MngDataStatus.CONFIRMED, false,
								Optional.of(GeneralDate.ymd(2019, 11, 9)), 1.0, Optional.of(new AttendanceTime(1)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e134", SID, MngDataStatus.CONFIRMED, false,
								Optional.of(GeneralDate.ymd(2019, 11, 10)), 1.0, Optional.of(new AttendanceTime(1)),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, MngDataStatus.CONFIRMED, false,
								Optional.of(GeneralDate.ymd(2019, 11, 12)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e137", SID, MngDataStatus.CONFIRMED, false,
								Optional.of(GeneralDate.ymd(2019, 11, 13)), 1.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.OCCURRENCE, 1.0, Optional.of(new AttendanceTime(0))));

	}

}
