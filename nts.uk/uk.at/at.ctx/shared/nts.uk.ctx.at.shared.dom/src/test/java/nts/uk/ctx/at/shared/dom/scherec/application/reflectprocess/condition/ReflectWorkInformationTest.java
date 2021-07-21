package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.WorkInformation.Require;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@RunWith(JMockit.class)
public class ReflectWorkInformationTest {

	@Injectable
	private ReflectWorkInformation.Require require;

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →勤務種類を反映するできます
	 * 
	 * →勤務情報.勤務予定時間帯を反映する
	 * 
	 * 準備するデータ
	 * 
	 * →勤務種類を反映するできます
	 * 
	 * →「勤務種類」データがある
	 */
	@Test
	public void testChangeWorkType() {

		WorkInfoDto workInfoDto = new WorkInfoDto(Optional.of(new WorkTypeCode("002")), Optional.empty());

		new Expectations() {
			{
				new MockUp<WorkInformation>() {
					@Mock
					public Optional<WorkInfoAndTimeZone> getWorkInfoAndTimeZone(Require require) {
						return Optional.of(ReflectApplicationHelper.createPredeteTimeSet(510, // 所定時間設定.時間帯.開始
								1080, // 所定時間設定.時間帯. 終了
								1));// 所定時間設定.時間帯.勤務NO
					}
				};

			}

		};

		DailyRecordOfApplication domaindaily = ReflectApplicationHelper
				.createDailyRecord(ScheduleRecordClassifi.SCHEDULE);
		List<Integer> resultActual = ReflectWorkInformation.reflectInfo(require, "", workInfoDto, // 勤務情報DTO
				domaindaily, // 日別勤怠(work）- 予定
				Optional.of(true), // 勤務種類を変更する＝する
				Optional.of(false));// 就業時間帯を変更する＝しない

		List<Integer> resultExpected = Arrays.asList(28, 1292, 1293, 3, 4, 31, 34);

		assertThat(resultActual).isEqualTo(resultExpected);

		assertThat(domaindaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("002");
		assertThat(domaindaily.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("001");

		assertThat(domaindaily.getWorkInformation().getScheduleTimeSheets())
				.extracting(x -> x.getWorkNo().v(), x -> x.getAttendance().v(), x -> x.getLeaveWork().v())
				.containsExactly(Tuple.tuple(1, 510, 1080));

	}

	/*
	 * テストしたい内容
	 * 
	 * →勤務情報の反映
	 * 
	 * →就業時間帯を反映する
	 * 
	 * →勤務情報.勤務予定時間帯を反映する
	 * 
	 * 準備するデータ
	 * 
	 * →就業時間帯を反映するできます
	 * 
	 * →反映する「就業時間帯」データがある
	 */
	@Test
	public void testChangeWorkTime() {

		WorkInfoDto workInfoDto = new WorkInfoDto(Optional.of(new WorkTypeCode("001")), Optional.of(new WorkTimeCode("002")));

		new Expectations() {
			{

				new MockUp<WorkInformation>() {
					@Mock
					public Optional<WorkInfoAndTimeZone> getWorkInfoAndTimeZone(Require require) {
						return Optional.of(ReflectApplicationHelper.createPredeteTimeSet(510, // 所定時間設定.時間帯.開始
								1080, // 所定時間設定.時間帯. 終了
								1));// 所定時間設定.時間帯.勤務NO
					}
				};
			}

		};

		DailyRecordOfApplication domaindaily = ReflectApplicationHelper
				.createDailyRecord(ScheduleRecordClassifi.SCHEDULE);
		List<Integer> resultActual = ReflectWorkInformation.reflectInfo(require, "", workInfoDto, // 勤務情報DTO
				domaindaily, // 日別勤怠(work）- 予定
				Optional.of(false), // 勤務種類を変更する＝しない
				Optional.of(true));// 就業時間帯を変更する＝する

		List<Integer> resultExpected = Arrays.asList(29, 3, 4, 31, 34);

		assertThat(resultActual).isEqualTo(resultExpected);

		assertThat(domaindaily.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("002");
		assertThat(domaindaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("001");

		assertThat(domaindaily.getWorkInformation().getScheduleTimeSheets())
				.extracting(x -> x.getWorkNo().v(), x -> x.getAttendance().v(), x -> x.getLeaveWork().v())
				.containsExactly(Tuple.tuple(1, 510, 1080));
	}

	/*
	 * テストしたい内容 →勤務情報の反映
	 * 
	 * →勤務種類と就業時間帯を反映する
	 * 
	 * →勤務情報.勤務予定時間帯を反映する
	 * 
	 * 準備するデータ
	 * 
	 * →勤務種類と就業時間帯を反映するできます
	 * 
	 * →反映する「勤務種類と就業時間帯」データがある
	 */

	@Test
	public void testChageAll() {

		WorkInfoDto workInfoDto = new WorkInfoDto(Optional.of(new WorkTypeCode("002")),
				Optional.of(new WorkTimeCode("002")));

		new Expectations() {
			{
				new MockUp<WorkInformation>() {
					@Mock
					public Optional<WorkInfoAndTimeZone> getWorkInfoAndTimeZone(Require require) {
						return Optional.of(ReflectApplicationHelper.createPredeteTimeSet(510, // 所定時間設定.時間帯.開始
								1080, // 所定時間設定.時間帯. 終了
								1));// 所定時間設定.時間帯.勤務NO
					}
				};
			}

		};

		DailyRecordOfApplication domaindaily = ReflectApplicationHelper
				.createDailyRecord(ScheduleRecordClassifi.SCHEDULE, 1);
		List<Integer> resultActual = ReflectWorkInformation.reflectInfo(require, "", workInfoDto, // 勤務情報DTO
				domaindaily, // 日別勤怠(work）- 予定
				Optional.of(true), // 勤務種類を変更する＝する
				Optional.of(true));// 就業時間帯を変更する＝する

		List<Integer> resultExpected = Arrays.asList(28, 1292, 1293, 29, 3, 4, 31, 34);

		assertThat(resultActual).isEqualTo(resultExpected);

		assertThat(domaindaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("002");
		assertThat(domaindaily.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("002");

		assertThat(domaindaily.getWorkInformation().getScheduleTimeSheets())
				.extracting(x -> x.getWorkNo().v(), x -> x.getAttendance().v(), x -> x.getLeaveWork().v())
				.containsExactly(Tuple.tuple(1, 510, 1080));
	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →予定に出退勤の反映
	 * 　①所定時間がない→出退勤のクリアー
	 * 　②所定時間がない→出退勤に反映
	 * 準備するデータ
	 * 
	 * →[ 勤務種類を反映する]=true or [就業時間帯を反映する] = true
	 * →申請の反映先＝予定
	 * 
	 */
	@Test
	public void testChangeTimeLeav(@Mocked WorkInformation recordInfo) {

		WorkInfoDto workInfoDto = new WorkInfoDto(Optional.of(new WorkTypeCode("002")), Optional.empty());
		//②所定時間がない→出退勤に反映
		new Expectations() {
			{
				recordInfo.getWorkInfoAndTimeZone(require);
				result = Optional.of(ReflectApplicationHelper.createPredeteTimeSet(510, // 所定時間設定.時間帯.開始
						1080, // 所定時間設定.時間帯. 終了
						1));// 所定時間設定.時間帯.勤務NO

			}
		};

		DailyRecordOfApplication domaindaily = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1);
		ReflectWorkInformation.reflectInfo(require, "", workInfoDto, // 勤務情報DTO
				domaindaily, // 日別勤怠(work）- 予定
				Optional.of(true), // 勤務種類を変更する＝する
				Optional.of(true));// 就業時間帯を変更する＝しない

		assertThat(domaindaily.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getWorkNo().v(),
						x -> x.getStampOfAttendance().get().getTimeDay().getTimeWithDay().get().v(),
						x -> x.getStampOfAttendance().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans(),
						x -> x.getStampOfLeave().get().getTimeDay().getTimeWithDay().get().v(),
						x -> x.getStampOfLeave().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
				.containsExactly(Tuple.tuple(1, 510, TimeChangeMeans.APPLICATION, 1080, TimeChangeMeans.APPLICATION));
		
		//①所定時間がない→出退勤のクリアー
		new Expectations() {
			{
				recordInfo.getWorkInfoAndTimeZone(require);
				result = Optional.empty();

			}
		};

		domaindaily = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1);
		ReflectWorkInformation.reflectInfo(require, "", workInfoDto, // 勤務情報DTO
				domaindaily, // 日別勤怠(work）- 予定
				Optional.of(true), // 勤務種類を変更する＝する
				Optional.of(true));// 就業時間帯を変更する＝しない

		assertThat(domaindaily.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getWorkNo().v(),
						x -> x.getStampOfAttendance().get().getTimeDay().getTimeWithDay(),
						x -> x.getStampOfAttendance().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans(),
						x -> x.getStampOfLeave().get().getTimeDay().getTimeWithDay(),
						x -> x.getStampOfLeave().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
				.containsExactly(Tuple.tuple(1, Optional.empty(), TimeChangeMeans.APPLICATION, Optional.empty(), TimeChangeMeans.APPLICATION));

	}
	
	/*
	 * テストしたい内容
	 * 
	 * 
	 * →予定に出退勤を反映しない
	 * 準備するデータ
	 * 
	 * →[ 勤務種類を反映する]=true or [就業時間帯を反映する] = true
	 * →申請の反映先＝実績
	 * 
	 */
	@Test
	public void testNoChangeTimeLeav(@Mocked WorkInformation recordInfo) {

		WorkInfoDto workInfoDto = new WorkInfoDto(Optional.of(new WorkTypeCode("002")), Optional.empty());
		//②所定時間がない→出退勤に反映
		DailyRecordOfApplication domaindaily = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1);
		   int attBefore = domaindaily.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getStampOfAttendance().get().getTimeDay().getTimeWithDay().get().v();
		   int leavBefore = domaindaily.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getStampOfLeave().get().getTimeDay().getTimeWithDay().get().v();
		   
		ReflectWorkInformation.reflectInfo(require, "", workInfoDto, // 勤務情報DTO
				domaindaily, // 日別勤怠(work）- 予定
				Optional.of(true), // 勤務種類を変更する＝する
				Optional.of(true));// 就業時間帯を変更する＝しない
		
		assertThat(domaindaily.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getWorkNo().v(),
						x -> x.getStampOfAttendance().get().getTimeDay().getTimeWithDay().get().v(),
						x -> x.getStampOfAttendance().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans(),
						x -> x.getStampOfLeave().get().getTimeDay().getTimeWithDay().get().v(),
						x -> x.getStampOfLeave().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
				.containsExactly(Tuple.tuple(1, attBefore, TimeChangeMeans.AUTOMATIC_SET, leavBefore, TimeChangeMeans.AUTOMATIC_SET));

	}

}
