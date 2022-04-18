package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.groupappabsence.algorithm.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *
 *  休暇系申請の反映（勤務予定）
 */
@RunWith(JMockit.class)
public class VacationAppReflectOptionTest {

	@Injectable
	private VacationAppReflectOption.RequireSC require;

	/*
	 * テストしたい内容
	 * 
	 * →就業時間帯を反映する
	 * 
	 * 準備するデータ
	 * 
	 * →休暇系申請の反映.就業時間帯を反映する = する;
	 * 
	 * →就業時間帯を変更する＝する
	 */
	@Test
	public void test1() {
		WorkInformation workInfo = new WorkInformation("003", // 勤務種類コード
				"004");// 就業時間帯コード **/
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1);// no = 1, 就業時間帯コード = 001
		VacationAppReflectOption option = new VacationAppReflectOption(NotUseAtr.NOT_USE,
				ReflectWorkHourCondition.REFLECT);// 出退勤を反映する=反映する
		DailyAfterAppReflectResult resultActual = option.processSC(require, "", workInfo,
				new ArrayList<>(), NotUseAtr.USE, dailyApp);
		assertThat(resultActual.getDomainDaily().getWorkInformation().getRecordInfo().getWorkTimeCode().v())
				.isEqualTo("004");// 就業時間帯コード
		assertThat(resultActual.getDomainDaily().getWorkInformation().getRecordInfo().getWorkTypeCode().v())
				.isEqualTo("003");// 勤務種類コード
	}

	/*
	 * テストしたい内容
	 * 
	 * →就業時間帯を反映しない
	 * 
	 * 準備するデータ
	 * 
	 * →休暇系申請の反映.就業時間帯を反映する = しない;
	 * 
	 * →就業時間帯を変更する＝する
	 */
	@Test
	public void test2() {
		WorkInformation workInfo = new WorkInformation("003", // 勤務種類コード
				"004");// 就業時間帯コード **/

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1);// no = 1, 就業時間帯コード = 001
		String workTimeBefore = dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v();// 前就業時間帯コード

		VacationAppReflectOption option = new VacationAppReflectOption(NotUseAtr.NOT_USE,
				ReflectWorkHourCondition.NOT_REFLECT);// 出退勤を反映する=反映しない

		DailyAfterAppReflectResult resultActual = option.processSC(require, "", workInfo,
				new ArrayList<>(), NotUseAtr.USE, dailyApp);

		assertThat(resultActual.getDomainDaily().getWorkInformation().getRecordInfo().getWorkTimeCode().v())
				.isEqualTo(workTimeBefore);// 就業時間帯コード
		assertThat(resultActual.getDomainDaily().getWorkInformation().getRecordInfo().getWorkTypeCode().v())
				.isEqualTo("003");// 勤務種類コード
	}

	/*
	 * テストしたい内容
	 * 
	 * →出退勤を反映する
	 * 
	 * 準備するデータ
	 * 
	 * 
	 */
	@Test
	public void test3() {
		WorkInformation workInfo = new WorkInformation("003", // 勤務種類コード
				"004");// 就業時間帯コード **/

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1);// no = 1, 就業時間帯コード = 001

		List<TimeZoneWithWorkNo> workingHours = Arrays.asList(new TimeZoneWithWorkNo(1, 488, 1028));

		VacationAppReflectOption option = new VacationAppReflectOption(NotUseAtr.NOT_USE, // 出退勤を反映する=する
				ReflectWorkHourCondition.REFLECT);

		DailyAfterAppReflectResult resultActual = option.processSC(require, "", workInfo,
				workingHours, NotUseAtr.USE, dailyApp);

		assertThat(resultActual.getDomainDaily().getAttendanceLeave().get().getTimeLeavingWorks())
		.extracting(x -> x.getWorkNo().v(), // No
				x -> x.getStampOfAttendance().get().getTimeDay().getTimeWithDay().get().v(), // 出勤 .時刻
				x -> x.getStampOfAttendance().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans(), // 出勤.時刻変更手段
				x -> x.getStampOfLeave().get().getTimeDay().getTimeWithDay().get().v(), // 退勤 .時刻
				x -> x.getStampOfLeave().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())// 退勤.時刻変更手段
		.contains(Tuple.tuple(1, 488, TimeChangeMeans.APPLICATION, 1028,
				TimeChangeMeans.APPLICATION));
	}
}
