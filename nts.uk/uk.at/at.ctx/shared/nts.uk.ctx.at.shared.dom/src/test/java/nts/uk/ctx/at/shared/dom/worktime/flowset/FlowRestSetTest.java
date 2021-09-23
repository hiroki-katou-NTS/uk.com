package nts.uk.ctx.at.shared.dom.worktime.flowset;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class FlowRestSetTest {

	@Test
	public void getters() {
		FlowRestSet target = new FlowRestSet(true, FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE, RestClockManageAtr.IS_CLOCK_MANAGE);
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void isConvertGoOutToBreak_true() {
		FlowRestSet target = new FlowRestSet(
				true, //打刻を併用する(外出を休憩として扱う）
				FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE,//休憩として扱う外出を休憩時間として計算する
				RestClockManageAtr.IS_CLOCK_MANAGE);
		
		assertThat(target.isConvertGoOutToBreak(GoingOutReason.PRIVATE)).isTrue();
	}

	@Test
	public void isConvertGoOutToBreak_useStampFalse() {
		FlowRestSet target = new FlowRestSet(
				false, //打刻を併用しない(外出を休憩として扱わない）
				FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE,//休憩として扱う外出を休憩時間として計算する
				RestClockManageAtr.IS_CLOCK_MANAGE);
		
		assertThat(target.isConvertGoOutToBreak(GoingOutReason.PRIVATE)).isFalse();
	}

	@Test
	public void isConvertGoOutToBreak_flowRestClockCalcMethodFalse() {
		FlowRestSet target = new FlowRestSet(
				true, //打刻を併用する(外出を休憩として扱う）
				FlowRestClockCalcMethod.USE_GOOUT_TIME_TO_CALCULATE,//休憩として扱う外出を外出時間として計算する
				RestClockManageAtr.IS_CLOCK_MANAGE);
		
		assertThat(target.isConvertGoOutToBreak(GoingOutReason.PRIVATE)).isFalse();
	}

	@Test
	public void isConvertGoOutToBreak_goingOutReasonFalse() {
		FlowRestSet target = new FlowRestSet(
				true, //打刻を併用する(外出を休憩として扱う）
				FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE,//休憩として扱う外出を休憩時間として計算する
				RestClockManageAtr.IS_CLOCK_MANAGE);
		
		assertThat(target.isConvertGoOutToBreak(GoingOutReason.PUBLIC)).isFalse();
	}

	@Test
	public void isGoOutBeforeBreak_isClockManageTrue() {
		FlowRestSet target = new FlowRestSet(
				true,
				FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE,
				RestClockManageAtr.IS_CLOCK_MANAGE); //時刻管理する（休憩優先）
		
		TimeWithDayAttr goOutStart = TimeWithDayAttr.hourMinute(11, 59); //外出11:59
		TimeWithDayAttr breakStart = TimeWithDayAttr.hourMinute(12, 0);  //休憩12:00
		
		assertThat(target.isGoOutBeforeBreak(goOutStart, breakStart)).isTrue();
	}
	
	@Test
	public void isGoOutBeforeBreak_isClockManagefalse() {
		FlowRestSet target = new FlowRestSet(
				true,
				FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE,
				RestClockManageAtr.IS_CLOCK_MANAGE); //時刻管理する（休憩優先）
		
		TimeWithDayAttr goOutStart = TimeWithDayAttr.hourMinute(12, 0); //外出12:00
		TimeWithDayAttr breakStart = TimeWithDayAttr.hourMinute(12, 0); //休憩12:00
		
		assertThat(target.isGoOutBeforeBreak(goOutStart, breakStart)).isFalse();
	}
	
	@Test
	public void isGoOutBeforeBreak_notClockManageTrue() {
		FlowRestSet target = new FlowRestSet(
				true,
				FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE,
				RestClockManageAtr.NOT_CLOCK_MANAGE); //時刻管理しない（外出優先）
		
		TimeWithDayAttr goOutStart = TimeWithDayAttr.hourMinute(12, 0); //外出12:00
		TimeWithDayAttr breakStart = TimeWithDayAttr.hourMinute(12, 0); //休憩12:00
		
		assertThat(target.isGoOutBeforeBreak(goOutStart, breakStart)).isTrue();
	}
	
	@Test
	public void isGoOutBeforeBreak_notClockManageFalse() {
		FlowRestSet target = new FlowRestSet(
				true,
				FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE,
				RestClockManageAtr.NOT_CLOCK_MANAGE); //時刻管理しない（外出優先）
		
		TimeWithDayAttr goOutStart = TimeWithDayAttr.hourMinute(12, 1); //外出12:01
		TimeWithDayAttr breakStart = TimeWithDayAttr.hourMinute(12, 0); //休憩12:00
		
		assertThat(target.isGoOutBeforeBreak(goOutStart, breakStart)).isFalse();
	}
}
