package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ReflectDirectBounceClassifiTest {

	/*
	 * テストしたい内容
	 * 
	 * →直行直帰区分の反映
	 * 
	 * → 勤怠項目IDを反映した
	 * 
	 * 準備するデータ
	 * 
	 * →直行区分：する, しない
	 * 
	 * → 直帰区分：する, しない
	 * 
	 */
	@Test
	public void test() {

		// 直行区分：する, 直帰区分：しない
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE);
		List<Integer> actualResult = ReflectDirectBounceClassifi.reflect(dailyApp, NotUseAtr.NOT_USE, NotUseAtr.USE);

		List<Integer> expectedResult = Arrays.asList(859);
		assertThat(actualResult).isEqualTo(expectedResult);
		assertThat(dailyApp.getWorkInformation().getGoStraightAtr()).isEqualTo(NotUseAttribute.Use);
		assertThat(dailyApp.getWorkInformation().getBackStraightAtr()).isEqualTo(NotUseAttribute.Not_use);

		// 直行区分：しない, 直帰区分：する
		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE);
		List<Integer> actualResult2 = ReflectDirectBounceClassifi.reflect(dailyApp2, NotUseAtr.USE, NotUseAtr.NOT_USE);

		List<Integer> expectedResult2= Arrays.asList(860);
		assertThat(actualResult2).isEqualTo(expectedResult2);
		assertThat(dailyApp2.getWorkInformation().getGoStraightAtr()).isEqualTo(NotUseAttribute.Not_use);
		assertThat(dailyApp2.getWorkInformation().getBackStraightAtr()).isEqualTo(NotUseAttribute.Use);

		// 直行区分：する, 直帰区分：する
		DailyRecordOfApplication dailyApp3 = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE);
		List<Integer> actualResult3 = ReflectDirectBounceClassifi.reflect(dailyApp3, NotUseAtr.USE, NotUseAtr.USE);

		List<Integer> expectedResult3 = Arrays.asList(859, 860);
		assertThat(actualResult3).isEqualTo(expectedResult3);
		assertThat(dailyApp3.getWorkInformation().getGoStraightAtr()).isEqualTo(NotUseAttribute.Use);
		assertThat(dailyApp3.getWorkInformation().getBackStraightAtr()).isEqualTo(NotUseAttribute.Use);

		// 直行区分：しない, 直帰区分：しない
		DailyRecordOfApplication dailyApp4 = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE);
		List<Integer> actualResult4 = ReflectDirectBounceClassifi.reflect(dailyApp4, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);

		List<Integer> expectedResult4 = Arrays.asList();
		assertThat(actualResult4).isEqualTo(expectedResult4);
		assertThat(dailyApp4.getWorkInformation().getGoStraightAtr()).isEqualTo(NotUseAttribute.Not_use);
		assertThat(dailyApp4.getWorkInformation().getBackStraightAtr()).isEqualTo(NotUseAttribute.Not_use);

	}

}
