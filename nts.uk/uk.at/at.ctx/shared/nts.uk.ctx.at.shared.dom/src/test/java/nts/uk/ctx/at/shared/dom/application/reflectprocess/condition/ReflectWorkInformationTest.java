package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp.WorkInfoDto;
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

				// 所定時間帯を取得する
				require.getPredeterminedTimezone(anyString, anyString, anyInt);
				result = ReflectApplicationHelper.createPredeteTimeSet(510, // 所定時間設定.時間帯.開始
						1080, // 所定時間設定.時間帯. 終了
						1);// 所定時間設定.時間帯.勤務NO

			}

		};

		DailyRecordOfApplication domaindaily = ReflectApplicationHelper
				.createDailyRecord(ScheduleRecordClassifi.SCHEDULE);
		List<Integer> resultActual = ReflectWorkInformation.reflectInfo(require, workInfoDto, // 勤務情報DTO
				domaindaily, // 日別勤怠(work）- 予定
				Optional.of(true), // 勤務種類を変更する＝する
				Optional.of(false));// 就業時間帯を変更する＝しない

		List<Integer> resultExpected = Arrays.asList(1, 1292, 1293);

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

		WorkInfoDto workInfoDto = new WorkInfoDto(Optional.empty(), Optional.of(new WorkTimeCode("002")));

		new Expectations() {
			{

				// 所定時間帯を取得する
				require.getPredeterminedTimezone(anyString, anyString, anyInt);
				result = ReflectApplicationHelper.createPredeteTimeSet(510, // 所定時間設定.時間帯.開始
						1080, // 所定時間設定.時間帯. 終了
						1);// 所定時間設定.時間帯.勤務NO

			}

		};

		DailyRecordOfApplication domaindaily = ReflectApplicationHelper
				.createDailyRecord(ScheduleRecordClassifi.SCHEDULE);
		List<Integer> resultActual = ReflectWorkInformation.reflectInfo(require, workInfoDto, // 勤務情報DTO
				domaindaily, // 日別勤怠(work）- 予定
				Optional.of(false), // 勤務種類を変更する＝しない
				Optional.of(true));// 就業時間帯を変更する＝する

		List<Integer> resultExpected = Arrays.asList(2);

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

				// 所定時間帯を取得する
				require.getPredeterminedTimezone("002", "002", anyInt);
				result = ReflectApplicationHelper.createPredeteTimeSet(510, // 所定時間設定.時間帯.開始
						1080, // 所定時間設定.時間帯. 終了
						1);// 所定時間設定.時間帯.勤務NO
			}

		};

		DailyRecordOfApplication domaindaily = ReflectApplicationHelper
				.createDailyRecord(ScheduleRecordClassifi.SCHEDULE, 1);
		List<Integer> resultActual = ReflectWorkInformation.reflectInfo(require, workInfoDto, // 勤務情報DTO
				domaindaily, // 日別勤怠(work）- 予定
				Optional.of(true), // 勤務種類を変更する＝する
				Optional.of(true));// 就業時間帯を変更する＝する

		List<Integer> resultExpected = Arrays.asList(1, 1292, 1293, 2);

		assertThat(resultActual).isEqualTo(resultExpected);

		assertThat(domaindaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("002");
		assertThat(domaindaily.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("002");

		assertThat(domaindaily.getWorkInformation().getScheduleTimeSheets())
				.extracting(x -> x.getWorkNo().v(), x -> x.getAttendance().v(), x -> x.getLeaveWork().v())
				.containsExactly(Tuple.tuple(1, 510, 1080));
	}

}
