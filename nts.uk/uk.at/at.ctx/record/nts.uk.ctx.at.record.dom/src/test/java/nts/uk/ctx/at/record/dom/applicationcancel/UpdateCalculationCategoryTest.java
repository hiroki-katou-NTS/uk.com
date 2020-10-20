package nts.uk.ctx.at.record.dom.applicationcancel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.stamp.EngraveShareAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@RunWith(JMockit.class)
public class UpdateCalculationCategoryTest {

	/*
	 * テストしたい内容
	 * 
	 * → 計算区分の更新
	 * 
	 * 準備するデータ
	 * 
	 * → 打刻区分 = 退勤, 早出, 休出
	 * 
	 */
	@Test
	public void test() {

		IntegrationOfDaily daily = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD)
				.getDomain();
		daily.setCalAttr(CalAttrOfDailyAttd.defaultData());
		// 退勤（残業）
		UpdateCalculationCategory.updateCalc(daily, EngraveShareAtr.OVERTIME);
		assertThat(daily.getCalAttr().getOvertimeSetting().getNormalOtTime().getCalAtr())
				.isEqualTo(AutoCalAtrOvertime.CALCULATEMBOSS);
		assertThat(daily.getCalAttr().getOvertimeSetting().getLegalMidOtTime().getCalAtr())
				.isEqualTo(AutoCalAtrOvertime.CALCULATEMBOSS);
		assertThat(daily.getCalAttr().getOvertimeSetting().getLegalOtTime().getCalAtr())
				.isEqualTo(AutoCalAtrOvertime.CALCULATEMBOSS);
		assertThat(daily.getCalAttr().getOvertimeSetting().getNormalMidOtTime().getCalAtr())
				.isEqualTo(AutoCalAtrOvertime.CALCULATEMBOSS);

		// 早出
		daily.setCalAttr(CalAttrOfDailyAttd.defaultData());
		UpdateCalculationCategory.updateCalc(daily, EngraveShareAtr.EARLY);
		assertThat(daily.getCalAttr().getOvertimeSetting().getEarlyOtTime().getCalAtr())
				.isEqualTo(AutoCalAtrOvertime.CALCULATEMBOSS);
		assertThat(daily.getCalAttr().getOvertimeSetting().getEarlyMidOtTime().getCalAtr())
				.isEqualTo(AutoCalAtrOvertime.CALCULATEMBOSS);

		// 休出
		daily.setCalAttr(CalAttrOfDailyAttd.defaultData());
		UpdateCalculationCategory.updateCalc(daily, EngraveShareAtr.HOLIDAY);
		assertThat(daily.getCalAttr().getHolidayTimeSetting().getRestTime().getCalAtr())
				.isEqualTo(AutoCalAtrOvertime.CALCULATEMBOSS);
		assertThat(daily.getCalAttr().getHolidayTimeSetting().getLateNightTime().getCalAtr())
				.isEqualTo(AutoCalAtrOvertime.CALCULATEMBOSS);

	}

}
