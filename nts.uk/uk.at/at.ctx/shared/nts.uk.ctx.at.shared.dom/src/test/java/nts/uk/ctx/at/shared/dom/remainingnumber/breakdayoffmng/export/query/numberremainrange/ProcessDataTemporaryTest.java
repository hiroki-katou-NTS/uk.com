package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ProcessDataTemporary;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;

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

	/*
	 * 　テストしたい内容
	 *     　対象期間のドメインモデル「暫定振休管理データ」を上書き用の暫定管理データに置き換える
	 * 
	 * 　準備するデータ
	 * */
	@Test
	public void test() {

		// INPUT．対象期間：2019/09/01～2019/09/30
		// INPUT．上書き用の暫定管理データ：2019/09/05、2019/09/20
		List<InterimDayOffMng> dayOffMngParam = new ArrayList<>();
		dayOffMngParam.add(new InterimDayOffMng("a1", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));
		dayOffMngParam.add(new InterimDayOffMng("a2", new RequiredTime(480),
				new RequiredDay(1.0), new UnOffsetTime(480), new UnOffsetDay(1.0)));

		List<InterimRemain> interimMngParam = new ArrayList<>();
		interimMngParam.add(new InterimRemain("a1", SID, GeneralDate.ymd(2019, 9, 5),
				CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngParam.add(new InterimRemain("a2", SID, GeneralDate.ymd(2019, 9, 20),
				CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));

		BreakDayOffRemainMngRefactParam inputParam = new BreakDayOffRemainMngRefactParam(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 9, 01), GeneralDate.ymd(2020, 9, 30)), false,
				GeneralDate.ymd(2019, 9, 30), true, interimMngParam, Optional.of(CreateAtr.RECORD),
				Optional.of(new DatePeriod(GeneralDate.ymd(2019, 9, 01), GeneralDate.ymd(2019, 9, 30))),
				new ArrayList<>(), dayOffMngParam, Optional.empty(),
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		// 取得したドメインモデル「暫定代休管理データ」
		// 2019/09/01、2019/09/02、2019/09/17、2019/09/20, 2019/10/3
		List<InterimDayOffMng> dayOffMngResult = new ArrayList<>();

		dayOffMngResult.add(new InterimDayOffMng("a0", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));
		dayOffMngResult.add(new InterimDayOffMng("a3", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));
		dayOffMngResult.add(new InterimDayOffMng("a4", new RequiredTime(480),
				new RequiredDay(1.0), new UnOffsetTime(480), new UnOffsetDay(1.0)));
		dayOffMngResult.add(new InterimDayOffMng("a5", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));
		dayOffMngResult.add(new InterimDayOffMng("a6", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));
		dayOffMngResult.add(new InterimDayOffMng("a7", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));
		dayOffMngResult.add(new InterimDayOffMng("a9", new RequiredTime(0),
				new RequiredDay(1.0), new UnOffsetTime(0), new UnOffsetDay(1.0)));

		List<InterimRemain> interimMngResult = new ArrayList<>();

		interimMngResult.add(new InterimRemain("a3", SID,
				GeneralDate.ymd(2019, 8, 30), CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngResult.add(new InterimRemain("a3", SID,
				GeneralDate.ymd(2019, 9, 01), CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngResult.add(new InterimRemain("a4", SID, GeneralDate.ymd(2019, 9, 2),
				CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngResult.add(new InterimRemain("a5", SID,
				GeneralDate.ymd(2019, 9, 17), CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngResult.add(new InterimRemain("a6", SID,
				GeneralDate.ymd(2019, 9, 20), CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngResult.add(new InterimRemain("a7", SID,
				GeneralDate.ymd(2019, 9, 30), CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));
		interimMngResult.add(new InterimRemain("a9", SID,
				GeneralDate.ymd(2019, 10, 01), CreateAtr.RECORD, RemainType.SUBHOLIDAY, RemainAtr.SINGLE));

		ProcessDataTemporary.processOverride(inputParam, inputParam.getDayOffMng(), interimMngResult, dayOffMngResult);

		// 置き換える後の「暫定代休管理データ」：2019/08/25、2019/09/05、2019/09/20、2019/10/03

		assertThat(interimMngResult).extracting(x -> x.getYmd()).containsExactly(
				GeneralDate.ymd(2019, 8, 30),
				GeneralDate.ymd(2019, 10, 01), 
				GeneralDate.ymd(2019, 9, 5),
				GeneralDate.ymd(2019, 9, 20));

	}
}
