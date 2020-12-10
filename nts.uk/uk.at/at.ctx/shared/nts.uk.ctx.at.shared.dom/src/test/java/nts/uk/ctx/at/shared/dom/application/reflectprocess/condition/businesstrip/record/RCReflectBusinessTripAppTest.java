package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.application.bussinesstrip.BusinessTripInfoShare;
import nts.uk.ctx.at.shared.dom.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;

@RunWith(JMockit.class)
public class RCReflectBusinessTripAppTest {

	@Injectable
	private RCReflectBusinessTripApp.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →勤務情報の反映がない
	 * 
	 * →出退勤の反映がない
	 * 
	 * →直行直帰区分の反映がない
	 * 
	 * 準備するデータ
	 * 
	 * →[出張勤務情報. 勤務時間帯] がない
	 * 
	 * →対象となる日に出張勤務情報を持っている
	 */
	@Test
	public void testNoReflectAll() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD,
				2);

		List<Integer> actualResult = new ArrayList<>();
		actualResult.addAll(RCReflectBusinessTripApp.reflect(require, createTripInfo(Collections.emptyList()), dailyApp,
				new ReflectBusinessTripApp("1"), GeneralDate.ymd(2020, 10, 11)));

		assertThat(actualResult).isEmpty();

	}

	/*
	 * テストしたい内容
	 * 
	 * →勤務情報の反映がある
	 * 
	 * →出退勤の反映がない
	 * 
	 * →直行直帰区分の反映がない
	 * 
	 * 準備するデータ
	 * 
	 * →[出張勤務情報. 勤務時間帯] がない
	 * 
	 * →対象となる日に出張勤務情報を持っている
	 */
	@Test
	public void testNoReflectInfo() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD,
				2);

		List<Integer> actualResult = new ArrayList<>();
		actualResult.addAll(RCReflectBusinessTripApp.reflect(require, createTripInfo(Collections.emptyList()), dailyApp,
				new ReflectBusinessTripApp("1"), GeneralDate.ymd(2020, 10, 10)));

		assertThat(actualResult).isEqualTo(Arrays.asList(1, 1292, 1293, 2));

	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →出退勤の反映があります
	 * 
	 * →直行直帰区分の反映があります
	 * 
	 * →始業終業の反映があります
	 * 
	 * 準備するデータ
	 * 
	 * →[出張勤務情報. 勤務時間帯] があります
	 */
	@Test
	public void testReflectInfo() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD,
				2);

		List<Integer> actualResult = new ArrayList<>();
		actualResult.addAll(RCReflectBusinessTripApp.reflect(require, createTripInfoWorkHour(), dailyApp,
				new ReflectBusinessTripApp("1"), GeneralDate.ymd(2020, 10, 10)));

		assertThat(actualResult).isEqualTo(Arrays.asList(1, 1292, 1293, 2, 3, 4, 5, 6, 31, 34, 41, 44, 859, 860));

	}

	private BusinessTripShare createTripInfoWorkHour() {

		List<TimeZoneWithWorkNo> workingHours = new ArrayList<>();
		workingHours.add(new TimeZoneWithWorkNo(1, 1, 1));
		workingHours.add(new TimeZoneWithWorkNo(2, 2, 2));

		return createTripInfo(workingHours);
	}

	private BusinessTripShare createTripInfo(List<TimeZoneWithWorkNo> workingHours) {
		List<BusinessTripInfoShare> lstResult = new ArrayList<>();
		BusinessTripInfoShare info = new BusinessTripInfoShare(new WorkInformation("1", "2"),
				GeneralDate.ymd(2020, 10, 10), Optional.of(workingHours));
		lstResult.add(info);

		return new BusinessTripShare(lstResult, null, null,
				ReflectApplicationHelper.createAppShare(PrePostAtrShare.PREDICT));
	}

}
