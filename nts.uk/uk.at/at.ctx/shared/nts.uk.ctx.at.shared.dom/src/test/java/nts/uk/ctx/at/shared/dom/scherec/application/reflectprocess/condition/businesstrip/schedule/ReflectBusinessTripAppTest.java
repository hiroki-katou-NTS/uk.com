package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.businesstrip.schedule;

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
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.BusinessTripInfoShare;
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.WorkingTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author thanh_nx
 *
 *出張申請の反映（勤務予定）
 */
@RunWith(JMockit.class)
public class ReflectBusinessTripAppTest {

	@Injectable
	private ReflectBusinessTripApp.Require require;

	/*
	 * テストしたい内容
	 * 
	 *  →勤務情報の反映がない
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

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE,
				2);

		List<Integer> actualResult = new ArrayList<>();
		actualResult.addAll((new ReflectBusinessTripApp("1")).reflectSchedule(require, createTripInfo(Collections.emptyList()), dailyApp,
				 GeneralDate.ymd(2020, 10, 11)));

		assertThat(actualResult).isEmpty();

	}
	
	/*
	 * テストしたい内容
	 * 
	 *  →勤務情報の反映がある
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

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE,
				2);

		List<Integer> actualResult = new ArrayList<>();
		actualResult.addAll((new ReflectBusinessTripApp("1")).reflectSchedule(require, createTripInfo(Collections.emptyList()), dailyApp,
				GeneralDate.ymd(2020, 10, 10)));

		assertThat(actualResult).isEqualTo(Arrays.asList(28, 1292, 1293, 29));

	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →出退勤の反映があります
	 * 
	 * →直行直帰区分の反映があります
	 * 
	 * 準備するデータ
	 * 
	 * →[出張勤務情報. 勤務時間帯] があります
	 */
	@Test
	public void testReflectInfo() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE,
				2);

		List<Integer> actualResult = new ArrayList<>();
		actualResult.addAll((new ReflectBusinessTripApp("1")).reflectSchedule(require, createTripInfoWorkHour(), dailyApp,
				GeneralDate.ymd(2020, 10, 10)));

		assertThat(actualResult).isEqualTo(Arrays.asList(28, 1292, 1293, 29, 31, 34, 41, 44, 859, 860));

	}

	private BusinessTripShare createTripInfoWorkHour() {

	    List<WorkingTimeShare> workingHours = new ArrayList<>();
        workingHours.add(new WorkingTimeShare(new WorkNo(1), Optional.of(new TimeWithDayAttr(1)), Optional.of(new TimeWithDayAttr(1))));
        workingHours.add(new WorkingTimeShare(new WorkNo(2), Optional.of(new TimeWithDayAttr(2)), Optional.of(new TimeWithDayAttr(2))));

        return createTripInfo(workingHours);
	}

	private BusinessTripShare createTripInfo(List<WorkingTimeShare> workingHours) {
		List<BusinessTripInfoShare> lstResult = new ArrayList<>();
		BusinessTripInfoShare info = new BusinessTripInfoShare(new WorkInformation("1", "2"),
				GeneralDate.ymd(2020, 10, 10), Optional.of(workingHours));
		lstResult.add(info);

		return new BusinessTripShare(lstResult, null, null,
				ReflectApplicationHelper.createAppShare(PrePostAtrShare.PREDICT));
	}
}
