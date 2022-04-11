package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.furiapp.AbsenceLeaveAppShare;
import nts.uk.ctx.at.shared.dom.scherec.application.furiapp.TypeApplicationHolidaysShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class RCSubstituteLeaveAppReflectTest {

	@Injectable
	private SubstituteLeaveAppReflect.RequireRC require;

	/*
	 * テストしたい内容
	 * 
	 * →休暇系申請の反映を呼ぶ
	 * 
	 * ①勤務情報の反映
	 * 
	 * ②始業終業の反映
	 * 
	 * ③ 出退勤の反映
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →休暇系申請の反映の設定
	 * 
	 */
	@Test
	public void test() {

		/// 勤務情報、出退勤を反映する
		VacationAppReflectOption workInfoAttendanceReflect = new VacationAppReflectOption(NotUseAtr.USE, // 1日休暇の場合は出退勤を削除
				ReflectWorkHourCondition.REFLECT);// 就業時間帯を反映する

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1);// no = 1, 就業時間帯コード = 001

		val absenceLeavApp = new AbsenceLeaveAppShare(Arrays.asList(new TimeZoneWithWorkNo(1, 777, 999)), // 勤務時間帯
				new WorkInformation("003", "005"), // 勤務情報
				NotUseAtr.USE, // するしない区分
				Optional.empty(), // 変更元の振休日
				TypeApplicationHolidaysShare.Abs, //
				ReflectApplicationHelper.createAppShare(ApplicationTypeShare.COMPLEMENT_LEAVE_APPLICATION,
						PrePostAtrShare.PREDICT));
		new SubstituteLeaveAppReflect("", workInfoAttendanceReflect).process(require, absenceLeavApp, dailyApp);

		// ①勤務情報の反映
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("003");

		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("005");

		// ②始業終業の反映
		assertThat(dailyApp.getWorkInformation().getScheduleTimeSheets())
				.extracting(x -> x.getWorkNo().v(), x -> x.getAttendance().v(), x -> x.getLeaveWork().v())
				.contains(Tuple.tuple(1, 777, 999));

	}

}
