package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneLateEarlyCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class WorkTimeCalcMethodDetailOfHolidayTest {

	@Test
	public void isDeductLateLeaveEarly_WorkSet1(){
	
		// 就業時間帯の共通設定
		WorkTimezoneCommonSet commonSetting = Helper.getCommonSetForLate(true);
		
		// 休暇の就業時間計算方法詳細
		WorkTimeCalcMethodDetailOfHoliday instance = Helper.getCalcMethodForLate(false, true);
		
		// Execute
		boolean result = instance.isDeductLateLeaveEarly(Optional.of(commonSetting));
		
		// Assertion
		assertThat(result).isTrue();
	}

	@Test
	public void isDeductLateLeaveEarly_WorkSet2(){
		
		// 就業時間帯の共通設定
		WorkTimezoneCommonSet commonSetting = Helper.getCommonSetForLate(false);
		
		// 休暇の就業時間計算方法詳細
		WorkTimeCalcMethodDetailOfHoliday instance = Helper.getCalcMethodForLate(true, true);
		
		// Execute
		boolean result = instance.isDeductLateLeaveEarly(Optional.of(commonSetting));
		
		// Assertion
		assertThat(result).isFalse();
	}

	@Test
	public void isDeductLateLeaveEarly_WorkSet3(){
		
		// 休暇の就業時間計算方法詳細
		WorkTimeCalcMethodDetailOfHoliday instance = Helper.getCalcMethodForLate(true, true);
		
		// Execute
		boolean result = instance.isDeductLateLeaveEarly(Optional.empty());
		
		// Assertion
		assertThat(result).isFalse();
	}

	@Test
	public void isDeductLateLeaveEarly_CalcSet1(){
		
		// 休暇の就業時間計算方法詳細
		WorkTimeCalcMethodDetailOfHoliday instance = Helper.getCalcMethodForLate(true, false);
		
		// Execute
		boolean result = instance.isDeductLateLeaveEarly(Optional.empty());
		
		// Assertion
		assertThat(result).isFalse();
	}

	@Test
	public void isDeductLateLeaveEarly_CalcSet2(){
		
		// 就業時間帯の共通設定
		WorkTimezoneCommonSet commonSetting = Helper.getCommonSetForLate(true);
		
		// 休暇の就業時間計算方法詳細
		WorkTimeCalcMethodDetailOfHoliday instance = Helper.getCalcMethodForLate(false, false);
		
		// Execute
		boolean result = instance.isDeductLateLeaveEarly(Optional.of(commonSetting));
		
		// Assertion
		assertThat(result).isTrue();
	}
	
	protected static class Helper {
		
		/**
		 * 就業時間帯の共通設定
		 * @param delFromEmTime 就業時間帯の遅刻・早退共通設定.就業時間から控除する
		 * @return 就業時間帯の共通設定
		 */
		public static WorkTimezoneCommonSet getCommonSetForLate(boolean delFromEmTime){
			return new WorkTimezoneCommonSet(
					false,
					new ArrayList<>(),
					new ArrayList<>(),
					null,
					null,
					null,
					null,
					null,
					new WorkTimezoneLateEarlySet(
							new EmTimezoneLateEarlyCommonSet(delFromEmTime),
							new ArrayList<>()),
					null,
					Optional.empty());
		}
		
		/**
		 * 休暇の就業時間計算方法詳細
		 * @param deduct 控除する
		 * @param enableSetPerWorkHour 就業時間帯毎の設定を可能とする
		 * @return 休暇の就業時間計算方法詳細
		 */
		public static WorkTimeCalcMethodDetailOfHoliday getCalcMethodForLate(
				boolean deduct, boolean enableSetPerWorkHour){
			
			return new WorkTimeCalcMethodDetailOfHoliday(
					null,
					NotUseAtr.NOT_USE,
					new DeductLeaveEarly(deduct?1:0, enableSetPerWorkHour?1:0),
					NotUseAtr.NOT_USE,
					Optional.empty());
		}
	}
}
