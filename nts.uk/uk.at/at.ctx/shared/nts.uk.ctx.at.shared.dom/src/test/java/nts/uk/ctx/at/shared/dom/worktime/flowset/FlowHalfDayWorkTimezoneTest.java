package nts.uk.ctx.at.shared.dom.worktime.flowset;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;

public class FlowHalfDayWorkTimezoneTest {

	@Test
	public void getters() {
		FlowHalfDayWorkTimezone target = new FlowHalfDayWorkTimezone(
				new FlowWorkTimezoneSetting(new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN), new ArrayList<>()),
				new FlowWorkRestTimezone(true, new TimezoneOfFixedRestTimeSet(), new FlowRestTimezone()));
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void getInLegalOverTimesTest() {
		FlowHalfDayWorkTimezone target = new FlowHalfDayWorkTimezone(FlowWorkTimezoneSettingHelper.createInLegal(
				FlowOTTimezoneHelper.createInLegal(			//残業枠No									法定内残業枠No
						new OvertimeWorkFrameNo(BigDecimal.valueOf(1)), new OvertimeWorkFrameNo(BigDecimal.valueOf(1))),
				FlowOTTimezoneHelper.createInLegal(
						new OvertimeWorkFrameNo(BigDecimal.valueOf(2)), new OvertimeWorkFrameNo(BigDecimal.valueOf(5))),
				FlowOTTimezoneHelper.createInLegal(
						new OvertimeWorkFrameNo(BigDecimal.valueOf(3)), new OvertimeWorkFrameNo(BigDecimal.valueOf(1))),
				FlowOTTimezoneHelper.createInLegal(
						new OvertimeWorkFrameNo(BigDecimal.valueOf(4)), new OvertimeWorkFrameNo(BigDecimal.valueOf(3))),
				FlowOTTimezoneHelper.createInLegal(
						new OvertimeWorkFrameNo(BigDecimal.valueOf(5)), new OvertimeWorkFrameNo(BigDecimal.valueOf(3))),
				FlowOTTimezoneHelper.createInLegal(
						new OvertimeWorkFrameNo(BigDecimal.valueOf(6)), new OvertimeWorkFrameNo(BigDecimal.valueOf(1))),
				FlowOTTimezoneHelper.createInLegal(
						new OvertimeWorkFrameNo(BigDecimal.valueOf(7)), new OvertimeWorkFrameNo(BigDecimal.valueOf(1))),
				FlowOTTimezoneHelper.createInLegal(
						new OvertimeWorkFrameNo(BigDecimal.valueOf(8)), new OvertimeWorkFrameNo(BigDecimal.valueOf(1))),
				FlowOTTimezoneHelper.createInLegal(
						new OvertimeWorkFrameNo(BigDecimal.valueOf(9)), new OvertimeWorkFrameNo(BigDecimal.valueOf(1))),
				FlowOTTimezoneHelper.createInLegal(
						new OvertimeWorkFrameNo(BigDecimal.valueOf(10)), new OvertimeWorkFrameNo(BigDecimal.valueOf(1)))
				),
				new FlowWorkRestTimezone());
		assertThat(target.getInLegalOverTimes())
				.extracting(o -> o.v())
				.containsExactly(1, 3, 5); //法内残業枠No = 1、3、5
	}
}
