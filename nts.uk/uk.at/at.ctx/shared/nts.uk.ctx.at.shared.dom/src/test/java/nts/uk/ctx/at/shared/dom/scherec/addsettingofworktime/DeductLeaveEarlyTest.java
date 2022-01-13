package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.worktime.common.TreatLateEarlyTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;

public class DeductLeaveEarlyTest {

	@Test
	public void isIncludeLateEarly_WorkSet1(){
	
		// 就業時間帯の共通設定　（→　遅刻を含める）
		WorkTimezoneCommonSet commonSetting = Helper.getCommonSetForLate(true, false);
		
		// 遅刻早退を控除する　（→　遅刻を含めない、就業時間帯毎に設定する）
		TreatLateEarlyTimeSetUnit instance = Helper.getDeductLeaveEarly(false, false, true);
		
		// Execute
		boolean result = instance.isIncludeLateEarlyInWorkTime(Optional.of(commonSetting.getLateEarlySet()));
		
		// Assertion　（遅刻を含める）
		assertThat(result).isTrue();
	}

	@Test
	public void isIncludeLateLeaveEarly_WorkSet2(){
		
		// 就業時間帯の共通設定　（→　遅刻を含めない）
		WorkTimezoneCommonSet commonSetting = Helper.getCommonSetForLate(false, false);
		
		// 遅刻早退を控除する　（→　遅刻を含める、就業時間帯毎に設定する）
		TreatLateEarlyTimeSetUnit instance = Helper.getDeductLeaveEarly(true, false, true);
		
		// Execute
		boolean result = instance.isIncludeLateEarlyInWorkTime(Optional.of(commonSetting.getLateEarlySet()));
		
		// Assertion　（遅刻を含めない）
		assertThat(result).isFalse();
	}

	@Test
	public void isIncludeLateLeaveEarly_WorkSet3(){
		
		// 遅刻早退を控除する　（→　遅刻を含める、就業時間帯毎に設定する）
		TreatLateEarlyTimeSetUnit instance = Helper.getDeductLeaveEarly(true, false, true);
		
		// Execute　（就業時間帯設定なし）
		boolean result = instance.isIncludeLateEarlyInWorkTime(Optional.empty());
		
		// Assertion　（遅刻を含めない）
		assertThat(result).isFalse();
	}

	@Test
	public void isIncludeLateLeaveEarly_CalcSet1(){
		
		// 遅刻早退を控除する　（→　遅刻を含める、就業時間帯毎に設定しない）
		TreatLateEarlyTimeSetUnit instance = Helper.getDeductLeaveEarly(true, false, false);
		
		// Execute　（就業時間帯設定なし）
		boolean result = instance.isIncludeLateEarlyInWorkTime(Optional.empty());
		
		// Assertion　（遅刻を含める）
		assertThat(result).isTrue();
	}

	@Test
	public void isIncludeLateLeaveEarly_CalcSet2(){
		
		// 就業時間帯の共通設定　（→　遅刻を含める）
		WorkTimezoneCommonSet commonSetting = Helper.getCommonSetForLate(true, false);
		
		// 遅刻早退を控除する　（→　遅刻を含めない、就業時間帯毎に設定しない）
		TreatLateEarlyTimeSetUnit instance = Helper.getDeductLeaveEarly(false, false, false);
		
		// Execute
		boolean result = instance.isIncludeLateEarlyInWorkTime(Optional.of(commonSetting.getLateEarlySet()));
		
		// Assertion　（遅刻を含めない）
		assertThat(result).isFalse();
	}
	
	protected static class Helper {
		
		/**
		 * 就業時間帯の共通設定
		 * @param include 就業時間帯の遅刻・早退共通設定.就業時間に含める
		 * @param deductByApp 申請で取り消した場合も控除する
		 * @return 就業時間帯の共通設定
		 */
		public static WorkTimezoneCommonSet getCommonSetForLate(boolean include, boolean deductByApp){
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
							new TreatLateEarlyTime(include, deductByApp),
							new ArrayList<>()),
					null,
					Optional.empty());
		}
		
		/**
		 * 遅刻早退を控除する
		 * @param include 含める
		 * @param deductByApp 申請で取り消した場合も控除する
		 * @param enableSetPerWorkHour 就業時間帯毎の設定を可能とする
		 * @return 遅刻早退を控除する
		 */
		public static TreatLateEarlyTimeSetUnit getDeductLeaveEarly(
				boolean include, boolean deductByApp, boolean enableSetPerWorkHour){
			
			return new TreatLateEarlyTimeSetUnit(
					new TreatLateEarlyTime(include, deductByApp),
					enableSetPerWorkHour);
		}
	}
}
