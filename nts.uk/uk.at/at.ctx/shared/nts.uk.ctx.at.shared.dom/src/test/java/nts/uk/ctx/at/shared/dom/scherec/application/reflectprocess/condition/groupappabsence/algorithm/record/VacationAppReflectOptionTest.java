package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.groupappabsence.algorithm.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 * 
 *休暇系申請の反映（勤務実績）
 */
@RunWith(JMockit.class)
public class VacationAppReflectOptionTest {

	@Injectable
	private VacationAppReflectOption.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →就業時間帯を反映する
	 * 
	 * 準備するデータ
	 * 
	 * →休暇系申請の反映.就業時間帯を反映する = する;
	 * 
	 * →input.就業時間帯を変更する＝する
	 */
	@Test
	public void test1() {
		WorkInformation workInfo = new WorkInformation("003", // 勤務種類コード
				"004");// 就業時間帯コード **/
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1);// no = 1, 就業時間帯コード = 001
		VacationAppReflectOption option = new VacationAppReflectOption(NotUseAtr.NOT_USE, 
				NotUseAtr.NOT_USE,
				ReflectWorkHourCondition.REFLECT);// 出退勤を反映する=反映する

		option.process(require, "", workInfo, new ArrayList<>(), PrePostAtrShare.POSTERIOR,
				NotUseAtr.USE, dailyApp);
		
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v())
				.isEqualTo("004");// 就業時間帯コード
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v())
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
	 * →input.就業時間帯を変更する＝する
	 */
	@Test
	public void test2() {
		WorkInformation workInfo = new WorkInformation("003", // 勤務種類コード
				"004");// 就業時間帯コード **/

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1);// no = 1, 就業時間帯コード = 001
		String workTimeBefore = dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v();// 前就業時間帯コード

		VacationAppReflectOption option = new VacationAppReflectOption(NotUseAtr.NOT_USE, 
				NotUseAtr.NOT_USE,
				ReflectWorkHourCondition.NOT_REFLECT);// 出退勤を反映する=反映しない

		val resultActual = option.process(require, "", workInfo, new ArrayList<>(),
				PrePostAtrShare.POSTERIOR, NotUseAtr.USE, dailyApp);

		assertThat(resultActual.getDomainDaily().getWorkInformation().getRecordInfo().getWorkTimeCode().v())
				.isEqualTo(workTimeBefore);// 就業時間帯コード
		assertThat(resultActual.getDomainDaily().getWorkInformation().getRecordInfo().getWorkTypeCode().v())
				.isEqualTo("003");// 勤務種類コード
	}


	/*
	 * テストしたい内容
	 * 
	 * →出退勤の削除
	 * 
	 * 準備するデータ
	 * 
	 * →休暇系申請の反映.1日休暇の場合は出退勤を削除 = する;
	 * 
	 * →出勤日区分＝1日休日系
	 */
	@Test
	public void test5() {
		WorkInformation workInfo = new WorkInformation("003", // 勤務種類コード
				"004");// 就業時間帯コード **/

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1);// no = 1, 就業時間帯コード = 001

		List<TimeZoneWithWorkNo> workingHours = Arrays.asList(new TimeZoneWithWorkNo(1, 488, 1028));

		VacationAppReflectOption option = new VacationAppReflectOption(NotUseAtr.USE, // 1日休暇の場合は出退勤を削除=する
				NotUseAtr.NOT_USE,
				ReflectWorkHourCondition.REFLECT);

		new Expectations() {
			{
				require.workType(anyString, (WorkTypeCode)any);
				result = Optional.of(WorkType.createSimpleFromJavaType("003", "", "", "", "", 0, 1, // 休日
						0, 0));
				;
			}
		};
		DailyAfterAppReflectResult resultActual = option.process(require, "", workInfo,
				workingHours, PrePostAtrShare.POSTERIOR, NotUseAtr.USE, dailyApp);

		assertThat(resultActual.getDomainDaily().getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getWorkNo().v(), // No
						x -> x.getStampOfAttendance().get().getTimeDay().getTimeWithDay(), // 出勤 .時刻
						x -> x.getStampOfAttendance().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans(), // 出勤.時刻変更手段
						x -> x.getStampOfLeave().get().getTimeDay().getTimeWithDay(), // 退勤 .時刻
						x -> x.getStampOfLeave().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())// 退勤.時刻変更手段
				.contains(Tuple.tuple(1, Optional.empty(), TimeChangeMeans.APPLICATION, Optional.empty(),
						TimeChangeMeans.APPLICATION));
	}

	/*
	 * テストしたい内容
	 * 
	 * →出退勤の削除がない
	 * 
	 * 準備するデータ
	 * 
	 * →休暇系申請の反映.1日休暇の場合は出退勤を削除 = しない;
	 * 
	 */
	@Test
	public void test6() {
		WorkInformation workInfo = new WorkInformation("003", // 勤務種類コード
				"004");// 就業時間帯コード **/

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1);// no = 1, 就業時間帯コード = 001
		int startBefore = dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getStampOfAttendance().get()
				.getTimeDay().getTimeWithDay().get().v();

		int endBefore = dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getStampOfLeave().get()
				.getTimeDay().getTimeWithDay().get().v();

		List<TimeZoneWithWorkNo> workingHours = Arrays.asList(new TimeZoneWithWorkNo(1, 488, 1028));

		VacationAppReflectOption option = new VacationAppReflectOption(NotUseAtr.USE,  // 1日休暇の場合は出退勤を削除=する
				NotUseAtr.NOT_USE,
				ReflectWorkHourCondition.REFLECT);
		new Expectations() {
			{
				require.workType(anyString, (WorkTypeCode)any);
				result = Optional.of(WorkType.createSimpleFromJavaType("003", "", "", "", "", 0, 0, // 出勤
						0, 0));
				;
			}
		};
		DailyAfterAppReflectResult resultActual = option.process(require, "", workInfo,
				workingHours, PrePostAtrShare.POSTERIOR, NotUseAtr.USE, dailyApp);

		assertThat(resultActual.getDomainDaily().getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getWorkNo().v(), // No
						x -> x.getStampOfAttendance().get().getTimeDay().getTimeWithDay().get().v(), // 出勤 .時刻
						x -> x.getStampOfAttendance().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans(), // 出勤.時刻変更手段
						x -> x.getStampOfLeave().get().getTimeDay().getTimeWithDay().get().v(), // 退勤 .時刻
						x -> x.getStampOfLeave().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())// 退勤.時刻変更手段
				.contains(Tuple.tuple(1, startBefore, TimeChangeMeans.AUTOMATIC_SET, endBefore,
						TimeChangeMeans.AUTOMATIC_SET));
	}
}
