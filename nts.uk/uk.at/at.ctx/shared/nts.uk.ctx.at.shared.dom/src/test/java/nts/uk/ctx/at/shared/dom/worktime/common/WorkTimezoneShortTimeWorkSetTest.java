package nts.uk.ctx.at.shared.dom.worktime.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.childcareset.ShortTimeWorkGetRange;

public class WorkTimezoneShortTimeWorkSetTest {

	@Test
	public void checkGetRangeByWorkUse_ChildToWork(){
		
		// 就業時間帯の短時間勤務設定
		// ※　引数は、介護を勤務とする、就業時間から控除、育児を勤務とする　の順。
		WorkTimezoneShortTimeWorkSet instance = new WorkTimezoneShortTimeWorkSet(false, true,
				new TimeRoundingSetting(0, 0));
		
		// Execute
		ShortTimeWorkGetRange result = instance.checkGetRangeByWorkUse(ChildCareAtr.CHILD_CARE);
		
		// Assertion
		assertThat(result).isEqualTo(ShortTimeWorkGetRange.WITHOUT_ATTENDANCE_LEAVE);
	}

	@Test
	public void checkGetRangeByWorkUse_ChildToCare(){
		
		// 就業時間帯の短時間勤務設定
		// ※　引数は、介護を勤務とする、就業時間から控除、育児を勤務とする　の順。
		WorkTimezoneShortTimeWorkSet instance = new WorkTimezoneShortTimeWorkSet(true, false,
				new TimeRoundingSetting(0, 0));
		
		// Execute
		ShortTimeWorkGetRange result = instance.checkGetRangeByWorkUse(ChildCareAtr.CHILD_CARE);
		
		// Assertion
		assertThat(result).isEqualTo(ShortTimeWorkGetRange.NORMAL_GET);
	}

	@Test
	public void checkGetRangeByWorkUse_CareToWork(){
		
		// 就業時間帯の短時間勤務設定
		// ※　引数は、介護を勤務とする、就業時間から控除、育児を勤務とする　の順。
		WorkTimezoneShortTimeWorkSet instance = new WorkTimezoneShortTimeWorkSet(true, false,
				new TimeRoundingSetting(0, 0));
		
		// Execute
		ShortTimeWorkGetRange result = instance.checkGetRangeByWorkUse(ChildCareAtr.CARE);
		
		// Assertion
		assertThat(result).isEqualTo(ShortTimeWorkGetRange.WITHOUT_ATTENDANCE_LEAVE);
	}

	@Test
	public void checkGetRangeByWorkUse_CareToCare(){
		
		// 就業時間帯の短時間勤務設定
		// ※　引数は、介護を勤務とする、就業時間から控除、育児を勤務とする　の順。
		WorkTimezoneShortTimeWorkSet instance = new WorkTimezoneShortTimeWorkSet(false, true,
				new TimeRoundingSetting(0, 0));
		
		// Execute
		ShortTimeWorkGetRange result = instance.checkGetRangeByWorkUse(ChildCareAtr.CARE);
		
		// Assertion
		assertThat(result).isEqualTo(ShortTimeWorkGetRange.NORMAL_GET);
	}
}
