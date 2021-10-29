package nts.uk.ctx.at.shared.dom.worktime.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

public class WorkTimezoneGoOutSetTest {

	@Test
	public void getters() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(GoOutTimeRoundingMethod.AFTER_TOTAL, TimeRoundingSetting.oneMinDown());
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void getAfterTotalTest_reasonEmpty() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.AFTER_TOTAL, //実働時間帯ごとに合計して丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getAfterTotal(ActualWorkTimeSheetAtr.WithinWorkTime, ConditionAtr.BREAK, DeductionAtr.Appropriate); //休憩
		
		//result == empty
		assertThat(result).isNotPresent();
	}
	
	@Test
	public void getAfterTotalTest_deduction() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.AFTER_TOTAL, //実働時間帯ごとに合計して丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getAfterTotal(ActualWorkTimeSheetAtr.WithinWorkTime, ConditionAtr.PrivateGoOut, DeductionAtr.Deduction); //控除
		
		//result == 1分切り捨て
		assertThat(result.get().getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_1MIN);
		assertThat(result.get().getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}
	
	@Test
	public void getAfterTotalTest_afterTotal() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.AFTER_TOTAL, //実働時間帯ごとに合計して丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getAfterTotal(ActualWorkTimeSheetAtr.WithinWorkTime, ConditionAtr.PrivateGoOut, DeductionAtr.Appropriate);
		
		//result == 5分切り上げ
		assertThat(result.get().getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_5MIN);
		assertThat(result.get().getRounding()).isEqualTo(Rounding.ROUNDING_UP);
	}
	
	@Test
	public void getAfterTotalTest_afterTotalInFrame() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.AFTER_TOTAL_IN_FRAME, //実働時間帯の枠ごとに合計してから丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getAfterTotal(ActualWorkTimeSheetAtr.WithinWorkTime, ConditionAtr.PrivateGoOut, DeductionAtr.Appropriate);
		
		//result == 1分切り捨て
		assertThat(result.get().getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_1MIN);
		assertThat(result.get().getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}
	
	@Test
	public void getAfterTotalTest_inFrame() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.IN_FRAME, //外出時間帯ごとに丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getAfterTotal(ActualWorkTimeSheetAtr.WithinWorkTime, ConditionAtr.PrivateGoOut, DeductionAtr.Appropriate);
		
		//result == 1分切り捨て
		assertThat(result.get().getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_1MIN);
		assertThat(result.get().getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}

	@Test
	public void getAfterTotalInFrameTest_reasonEmpty() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.AFTER_TOTAL_IN_FRAME, //実働時間帯の枠ごとに合計してから丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getAfterTotalInFrame(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				ConditionAtr.BREAK, //休憩
				DeductionAtr.Appropriate,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN));
		
		//result == empty
		assertThat(result).isNotPresent();
	}

	@Test
	public void getAfterTotalInFrameTest_afterTotalInFrame() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.AFTER_TOTAL_IN_FRAME, //実働時間帯の枠ごとに合計してから丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getAfterTotalInFrame(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				ConditionAtr.PrivateGoOut,
				DeductionAtr.Appropriate,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN));
		
		//result == 5分切り上げ
		assertThat(result.get().getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_5MIN);
		assertThat(result.get().getRounding()).isEqualTo(Rounding.ROUNDING_UP);
	}
	
	@Test
	public void getAfterTotalInFrameTest_afterTotal() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.AFTER_TOTAL, //実働時間帯ごとに合計して丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getAfterTotalInFrame(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				ConditionAtr.PrivateGoOut,
				DeductionAtr.Appropriate,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN));
		
		//result == 1分切り捨て
		assertThat(result.get().getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_1MIN);
		assertThat(result.get().getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}

	@Test
	public void getAfterTotalInFrameTest_inFrame() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.IN_FRAME, //外出時間帯ごとに丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getAfterTotalInFrame(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				ConditionAtr.PrivateGoOut,
				DeductionAtr.Appropriate,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN));
		
		//result == 1分切り捨て
		assertThat(result.get().getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_1MIN);
		assertThat(result.get().getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}

	@Test
	public void getInFrameTest_inFrame() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.IN_FRAME, //外出時間帯ごとに丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getInFrame(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				GoingOutReason.PRIVATE,
				DeductionAtr.Appropriate,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN));
		
		//result == 5分切り上げ
		assertThat(result.get().getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_5MIN);
		assertThat(result.get().getRounding()).isEqualTo(Rounding.ROUNDING_UP);
	}
	
	@Test
	public void getInFrameTest_afterTotal() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.AFTER_TOTAL, //実働時間帯ごとに合計して丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getInFrame(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				GoingOutReason.PRIVATE,
				DeductionAtr.Appropriate,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN));
		
		//result == 1分切り捨て
		assertThat(result.get().getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_1MIN);
		assertThat(result.get().getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}
	
	@Test
	public void getInFrameTest_afterTotalInFrame() {
		WorkTimezoneGoOutSet target = GoOutSetHelper.createWorkTimezoneGoOutSet(
				GoOutTimeRoundingMethod.AFTER_TOTAL_IN_FRAME, //実働時間帯の枠ごとに合計してから丸める
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP)); //5分切り上げ
		
		Optional<TimeRoundingSetting> result = target.getInFrame(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				GoingOutReason.PRIVATE,
				DeductionAtr.Appropriate,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN));
		
		//result == 1分切り捨て
		assertThat(result.get().getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_1MIN);
		assertThat(result.get().getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}
}
