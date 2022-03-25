package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.shorttimework;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneShortTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.common.childcareset.ShortTimeWorkGetRange;

public class CalcOfShortTimeWorkTest {

	@Test
	public void checkGetRangeByWithinOut_Within(){
		
		// 就業時間帯の共通設定
		WorkTimezoneCommonSet commonSet = WorkTimezoneCommonSet.generateDefault();
		
		// 短時間勤務の計算
		CalcOfShortTimeWork instance = CalcOfShortTimeWork.of("companyId", CalcMethodOfShortTimeWork.WITHIN);

		// Execute
		ShortTimeWorkGetRange result = instance.checkGetRangeByWithinOut(ChildCareAtr.CHILD_CARE, commonSet);
		
		// Assertion
		assertThat(result).isEqualTo(ShortTimeWorkGetRange.NORMAL_GET);
	}

	@Test
	public void checkGetRangeByWithinOut_Without(){
		
		// 就業時間帯の共通設定
		WorkTimezoneCommonSet commonSet = Helper.getCommonSetForShortTime();
		
		// 短時間勤務の計算
		CalcOfShortTimeWork instance = CalcOfShortTimeWork.of("companyId", CalcMethodOfShortTimeWork.WITHOUT);

		// Execute
		ShortTimeWorkGetRange result = instance.checkGetRangeByWithinOut(ChildCareAtr.CHILD_CARE, commonSet);
		
		// Assertion
		assertThat(result).isEqualTo(ShortTimeWorkGetRange.WITHOUT_ATTENDANCE_LEAVE);
	}
	
	protected static class Helper {
		
		/**
		 * 就業時間帯の共通設定
		 * @return 就業時間帯の共通設定
		 */
		public static WorkTimezoneCommonSet getCommonSetForShortTime(){
			return new WorkTimezoneCommonSet(
					false,
					new ArrayList<>(),
					new ArrayList<>(),
					null,
					null,
					null,
					new WorkTimezoneShortTimeWorkSet(false, true, new TimeRoundingSetting(0, 0)),
					null,
					null,
					null,
					Optional.empty());
		}
	}
}
