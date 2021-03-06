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
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

@RunWith(JMockit.class)
public class GetTemporaryDataTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private GetTemporaryData.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testProcess() {

		/*List<InterimDayOffMng> dayOffMng = Arrays.asList(DaikyuFurikyuHelper.createDayOff("d1", // ???????????????????????????ID
				0, 1.0), // ?????????
				DaikyuFurikyuHelper.createDayOff("d2", // ???????????????????????????ID
						0, 1.0), // ?????????
				DaikyuFurikyuHelper.createDayOff("d3", // ???????????????????????????ID
						0, 1.0), // ?????????
				DaikyuFurikyuHelper.createDayOff("d4", // ???????????????????????????ID
						480, 1.0)); // ?????????

		List<InterimBreakMng> breakMng = Arrays.asList(DaikyuFurikyuHelper.createBreak("k1", // ???????????????????????????ID
				GeneralDate.ymd(2020, 6, 6), // ???????????????
				480, 1.0), // ????????????
				DaikyuFurikyuHelper.createBreak("k2", // ???????????????????????????ID
						GeneralDate.ymd(2019, 6, 6), // ???????????????
						480, 1.0));// ????????????

		List<InterimRemain> interimMng = Arrays.asList(
				DaikyuFurikyuHelper.createRemain("k1", GeneralDate.ymd(2019, 11, 5), CreateAtr.SCHEDULE,
						RemainType.BREAK),
				DaikyuFurikyuHelper.createRemain("k2", GeneralDate.ymd(2019, 11, 6), CreateAtr.RECORD,
						RemainType.BREAK),

				DaikyuFurikyuHelper.createRemain("d1", GeneralDate.ymd(2019, 11, 7), CreateAtr.RECORD,
						RemainType.SUBHOLIDAY),
				DaikyuFurikyuHelper.createRemain("d2", GeneralDate.ymd(2019, 11, 8), CreateAtr.RECORD,
						RemainType.SUBHOLIDAY),
				DaikyuFurikyuHelper.createRemain("d3", GeneralDate.ymd(2019, 11, 9), CreateAtr.RECORD,
						RemainType.SUBHOLIDAY),
				DaikyuFurikyuHelper.createRemain("d4", GeneralDate.ymd(2019, 11, 10), CreateAtr.RECORD,
						RemainType.SUBHOLIDAY));

		BreakDayOffRemainMngRefactParam inputParam = createInput(true, // ????????? : ?????????
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), // ???????????????, ???????????????
				GeneralDate.ymd(2019, 11, 30), // ???????????????
				dayOffMng, // ???????????????????????????
				breakMng, // ???????????????????????????
				interimMng); // ???????????????????????????
		new Expectations() {
			{

				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				// ?????????????????????????????????
//				require.getBreakDayOffMng("d4", anyBoolean, (DataManagementAtr) any);
//				result = Arrays.asList(new InterimBreakDayOffMng("", DataManagementAtr.INTERIM, "d4",
//						DataManagementAtr.INTERIM, new UseTime(240), new UseDay(0.5), SelectedAtr.AUTOMATIC));
				require.getBycomDayOffID(anyString, GeneralDate.ymd(2019, 11, 10));
				result = Arrays.asList(DaikyuFurikyuHelper.createLeavComDayOff(GeneralDate.ymd(2019, 11, 10),
						GeneralDate.ymd(2019, 11, 19), 0.5));

				require.findComLeavEmpSet(CID, anyString);
				result = NumberRemainVacationLeaveRangeQueryTest.createComLeav(ManageDistinct.YES, ManageDistinct.YES,
						"02");

			}

		};*/
		/*List<AccumulationAbsenceDetail> resultActual = GetTemporaryData.process(require, inputParam);

		assertThat(resultActual).extracting(x -> x.getManageId(), x -> x.getDataAtr(), // ??????
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(), // ?????????
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(), // ?????????
				x -> x.getOccurrentClass(), // ??????????????????
				x -> x.getUnbalanceNumber().getDay().v(), x -> x.getUnbalanceNumber().getTime(), // ????????????
				x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE ? ((UnbalanceVacation) x).getDeadline()// ?????????
						: Optional.empty())
				.containsExactly(
						Tuple.tuple("d1", MngDataStatus.RECORD, false, Optional.of(GeneralDate.ymd(2019, 11, 7)), 1.0,
								Optional.of(new AttendanceTime(0)), OccurrenceDigClass.DIGESTION, 1.0,
								Optional.of(new AttendanceTime(0)), Optional.empty()),
						Tuple.tuple("d2", MngDataStatus.RECORD, false, Optional.of(GeneralDate.ymd(2019, 11, 8)), 1.0,
								Optional.of(new AttendanceTime(0)), OccurrenceDigClass.DIGESTION, 1.0,
								Optional.of(new AttendanceTime(0)), Optional.empty()),
						Tuple.tuple("d3", MngDataStatus.RECORD, false, Optional.of(GeneralDate.ymd(2019, 11, 9)), 1.0,
								Optional.of(new AttendanceTime(0)), OccurrenceDigClass.DIGESTION, 1.0,
								Optional.of(new AttendanceTime(0)), Optional.empty()),
						Tuple.tuple("d4", MngDataStatus.RECORD, false, Optional.of(GeneralDate.ymd(2019, 11, 10)), 1.0,
								Optional.of(new AttendanceTime(480)), OccurrenceDigClass.DIGESTION, 0.5,
								Optional.of(new AttendanceTime(480)), Optional.empty()),
						Tuple.tuple("k1", MngDataStatus.SCHEDULE, false, Optional.of(GeneralDate.ymd(2019, 11, 05)),
								1.0, Optional.of(new AttendanceTime(480)), OccurrenceDigClass.OCCURRENCE, 1.0,
								Optional.of(new AttendanceTime(480)), GeneralDate.ymd(2020, 06, 06)),
						Tuple.tuple("k2", MngDataStatus.RECORD, false, Optional.of(GeneralDate.ymd(2019, 11, 6)), 1.0,
								Optional.of(new AttendanceTime(480)), OccurrenceDigClass.OCCURRENCE, 1.0,
								Optional.of(new AttendanceTime(480)), GeneralDate.ymd(2019, 06, 06)));*/
	}

	private BreakDayOffRemainMngRefactParam createInput(boolean mode, DatePeriod dateData,
			GeneralDate screenDisplayDate, List<InterimDayOffMng> dayOffMng, List<InterimBreakMng> breakMng,
			List<InterimRemain> interimMng) {
		return new BreakDayOffRemainMngRefactParam(CID, SID, dateData, mode, screenDisplayDate, false, interimMng,
				Optional.empty(), Optional.empty(), breakMng, dayOffMng, Optional.empty(),
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));
	}
}
