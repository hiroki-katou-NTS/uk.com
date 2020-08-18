package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

public class CheckErrorDuringHolidayTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	 /* 　テストしたい内容
	 * 　　残日数, 残時間 <0の場合はエラー
	 *        残日数 <0 →日単位代休残数不足エラー
	 *        残時間 <0→　 相殺できないエラー
	 */
	@Test
	public void test() {

		List<DayOffError> lstError = new ArrayList<>();
		SubstituteHolidayAggrResult param = new SubstituteHolidayAggrResult(new VacationDetails(new ArrayList<>()),
				new ReserveLeaveRemainingDayNumber(-1.0), new RemainingMinutes(-480),
				new ReserveLeaveRemainingDayNumber(1.0), new RemainingMinutes(480),
				new ReserveLeaveRemainingDayNumber(4.0), new RemainingMinutes(960),
				new ReserveLeaveRemainingDayNumber(1.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(2.0), new RemainingMinutes(960), lstError,
				Finally.of(GeneralDate.ymd(2020, 11, 01)), new ArrayList<>());

		CheckErrorDuringHoliday.check(param);

		assertThat(param.getDayOffErrors()).isEqualTo(Arrays.asList(DayOffError.DAYERROR, DayOffError.TIMEERROR));
	}

}
