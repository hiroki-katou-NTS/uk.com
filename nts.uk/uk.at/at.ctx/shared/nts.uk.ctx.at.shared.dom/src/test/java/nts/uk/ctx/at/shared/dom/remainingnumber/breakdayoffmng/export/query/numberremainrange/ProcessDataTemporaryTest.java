package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ProcessDataTemporary;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

@RunWith(JMockit.class)
public class ProcessDataTemporaryTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void test() {

		// INPUT．対象期間：2019/09/01～2019/09/30
		// INPUT．上書き用の暫定管理データ：2019/09/05、2019/09/20
		List<InterimDayOffMng> dayOffMngParam = new ArrayList<>();
		dayOffMngParam.add(new InterimDayOffMng("P76caf30-5a4d-47b7-8147-d646f74be08a", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));
		dayOffMngParam.add(new InterimDayOffMng("P77a8929-3df0-4fd6-859e-29e615a921ea", new RequiredTime(480),
				new RequiredDay(1.0), new UnOffsetTime(480), new UnOffsetDay(1.0)));

		List<InterimRemain> interimMngParam = new ArrayList<>();
		interimMngParam.add(new InterimRemain("P76caf30-5a4d-47b7-8147-d646f74be08a", SID, GeneralDate.ymd(2019, 9, 5),
				CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngParam.add(new InterimRemain("P77a8929-3df0-4fd6-859e-29e615a921ea", SID, GeneralDate.ymd(2019, 9, 20),
				CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));

		Optional<SubstituteHolidayAggrResult> holidayAggrResult = Optional.of(new SubstituteHolidayAggrResult(
				new VacationDetails(Collections.emptyList()), new ReserveLeaveRemainingDayNumber(0d),
				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(0d), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0d), new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(0d),
				new RemainingMinutes(0), new ReserveLeaveRemainingDayNumber(0d), new RemainingMinutes(0),
				Collections.emptyList(), Finally.of(GeneralDate.ymd(2019, 12, 01)), Collections.emptyList()));

		BreakDayOffRemainMngRefactParam inputParam = new BreakDayOffRemainMngRefactParam(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 9, 01), GeneralDate.ymd(2020, 9, 30)), false,
				GeneralDate.ymd(2019, 9, 30), true, interimMngParam, Optional.of(CreateAtr.RECORD),
				Optional.of(new DatePeriod(GeneralDate.ymd(2019, 9, 01), GeneralDate.ymd(2019, 9, 30))),
				new ArrayList<>(), dayOffMngParam, holidayAggrResult,
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		// 取得したドメインモデル「暫定代休管理データ」
		// 2019/08/25、2019/09/02、2019/09/17、2019/09/20
		List<InterimDayOffMng> dayOffMngResult = new ArrayList<>();

		dayOffMngResult.add(new InterimDayOffMng("R76caf30-5a4d-47b7-8147-d646f74be08a", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));
		dayOffMngResult.add(new InterimDayOffMng("R77a8929-3df0-4fd6-859e-29e615a921ea", new RequiredTime(480),
				new RequiredDay(1.0), new UnOffsetTime(480), new UnOffsetDay(1.0)));
		dayOffMngResult.add(new InterimDayOffMng("R76caf30-5a4d-47b7-8147-d646f74be08b", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));
		dayOffMngResult.add(new InterimDayOffMng("R76caf30-5a4d-47b7-8147-d646f74be08c", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));
		dayOffMngResult.add(new InterimDayOffMng("R76caf30-5a4d-47b7-8147-d646f74be08d", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));

		List<InterimRemain> interimMngResult = new ArrayList<>();

		interimMngResult.add(new InterimRemain("R76caf30-5a4d-47b7-8147-d646f74be08a", SID,
				GeneralDate.ymd(2019, 8, 25), CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngResult.add(new InterimRemain("R77a8929-3df0-4fd6-859e-29e615a921ea", SID, GeneralDate.ymd(2019, 9, 2),
				CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngResult.add(new InterimRemain("R76caf30-5a4d-47b7-8147-d646f74be08b", SID,
				GeneralDate.ymd(2019, 9, 17), CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngResult.add(new InterimRemain("R76caf30-5a4d-47b7-8147-d646f74be08c", SID,
				GeneralDate.ymd(2019, 9, 20), CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngResult.add(new InterimRemain("R76caf30-5a4d-47b7-8147-d646f74be08d", SID,
				GeneralDate.ymd(2019, 10, 3), CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));

		ProcessDataTemporary.processOverride(inputParam, inputParam.getDayOffMng(), interimMngResult, dayOffMngResult);

		// 置き換える後の「暫定代休管理データ」：2019/08/25、2019/09/05、2019/09/20、2019/10/03

		assertThat(interimMngResult).extracting(x -> x.getYmd()).containsExactly(GeneralDate.ymd(2019, 8, 25),
				GeneralDate.ymd(2019, 10, 3), GeneralDate.ymd(2019, 9, 5), GeneralDate.ymd(2019, 9, 20));

	}
}
