package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeAbbreviationName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeMemo;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSymbolicName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

@RunWith(JMockit.class)
public class CorrectDailyAttendanceServiceTest {

	@Injectable
	private CorrectDailyAttendanceService.Require require;

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →振休振出として扱う日数がない
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →勤務種類 != 振出, 振休
	 * 
	 * 
	 */
	@Test
	public void test() {

		// 振休振出として扱う日数
		WorkInfoOfDailyAttendance after = ReflectApplicationHelper.createWorkInfoDefault("002");

		WorkInfoOfDailyAttendance before = ReflectApplicationHelper.createWorkInfoDefault("001");

		new Expectations() {
			{

				require.getWorkType("001");
				result = Optional.of(createWorkType("001", WorkTypeUnit.OneDay, WorkTypeClassification.Absence));

				require.getWorkType("002");
				result = Optional.of(createWorkType("002", WorkTypeUnit.OneDay, WorkTypeClassification.Absence));
			}
		};

		WorkInfoOfDailyAttendance actualResult = CorrectDailyAttendanceService.correctFurikyu(require, before, after);

		assertThat(actualResult.getNumberDaySuspension()).isEmpty();

	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →振出として扱う日数がある
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →勤務種類 = 振出, 勤務種類の分類 !=休日;
	 * 
	 * 
	 */
	@Test
	public void testFuriS() {

		// 振休振出として扱う日数
		WorkInfoOfDailyAttendance after = ReflectApplicationHelper.createWorkInfoDefault("002");

		WorkInfoOfDailyAttendance before = ReflectApplicationHelper.createWorkInfoDefault("001");

		new Expectations() {
			{

				require.getWorkType("001");
				result = Optional.of(createWorkType("001", WorkTypeUnit.OneDay, //勤務の単位
						WorkTypeClassification.Shooting));// 振出

				require.getWorkType("002");
				result = Optional.of(createWorkType("002", WorkTypeUnit.OneDay, WorkTypeClassification.Attendance));
			}
		};

		WorkInfoOfDailyAttendance actualResult = CorrectDailyAttendanceService.correctFurikyu(require, before, after);

		assertThat(actualResult.getNumberDaySuspension().get().getDays().v()).isEqualTo(1.0);
		assertThat(actualResult.getNumberDaySuspension().get().getClassifiction()).isEqualTo(FuriClassifi.DRAWER);

	}

	
	/*
	 * テストしたい内容
	 * 
	 * 
	 * →振出として扱う日数がない
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →勤務種類 = 振出, 勤務種類の分類 ==休日;
	 * 
	 * 
	 */
	@Test
	public void testFuriNoS() {

		// 振休振出として扱う日数
		WorkInfoOfDailyAttendance after = ReflectApplicationHelper.createWorkInfoDefault("002");

		WorkInfoOfDailyAttendance before = ReflectApplicationHelper.createWorkInfoDefault("001");

		new Expectations() {
			{

				require.getWorkType("001");
				result = Optional.of(createWorkType("001", WorkTypeUnit.OneDay, //勤務の単位
						WorkTypeClassification.Shooting));// 振出

				require.getWorkType("002");
				result = Optional.of(createWorkType("002", WorkTypeUnit.OneDay, WorkTypeClassification.Holiday));
			}
		};

		WorkInfoOfDailyAttendance actualResult = CorrectDailyAttendanceService.correctFurikyu(require, before, after);

		assertThat(actualResult.getNumberDaySuspension()).isEmpty();

	}
	
	/*
	 * テストしたい内容
	 * 
	 * 
	 * →振休として扱う日数がある
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →勤務種類 = 振出, 反映後の勤務種類 !=出勤;
	 * 
	 * 
	 */
	@Test
	public void testFuriK() {

		// 振休振出として扱う日数
		WorkInfoOfDailyAttendance after = ReflectApplicationHelper.createWorkInfoDefault("002");

		WorkInfoOfDailyAttendance before = ReflectApplicationHelper.createWorkInfoDefault("001");

		new Expectations() {
			{

				require.getWorkType("001");
				result = Optional.of(createWorkType("001", WorkTypeUnit.OneDay, //勤務の単位
						WorkTypeClassification.Pause));// 振休

				require.getWorkType("002");
				result = Optional.of(createWorkType("002", WorkTypeUnit.OneDay, WorkTypeClassification.Absence));
			}
		};

		WorkInfoOfDailyAttendance actualResult = CorrectDailyAttendanceService.correctFurikyu(require, before, after);

		assertThat(actualResult.getNumberDaySuspension().get().getDays().v()).isEqualTo(1.0);
		assertThat(actualResult.getNumberDaySuspension().get().getClassifiction()).isEqualTo(FuriClassifi.SUSPENSION);

	}
	
	/*
	 * テストしたい内容
	 * 
	 * 
	 * →振休として扱う日数がない
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →勤務種類 = 振出, 反映後の勤務種類 =出勤;
	 * 
	 * 
	 */
	@Test
	public void testFuriNoK() {

		// 振休振出として扱う日数
		WorkInfoOfDailyAttendance after = ReflectApplicationHelper.createWorkInfoDefault("002");

		WorkInfoOfDailyAttendance before = ReflectApplicationHelper.createWorkInfoDefault("001");

		new Expectations() {
			{

				require.getWorkType("001");
				result = Optional.of(createWorkType("001", WorkTypeUnit.OneDay, //勤務の単位
						WorkTypeClassification.Pause));// 振休

				require.getWorkType("002");
				result = Optional.of(createWorkType("002", WorkTypeUnit.OneDay, WorkTypeClassification.Attendance));
			}
		};

		WorkInfoOfDailyAttendance actualResult = CorrectDailyAttendanceService.correctFurikyu(require, before, after);

		assertThat(actualResult.getNumberDaySuspension()).isEmpty();

	}
	
	public WorkType createWorkType(String workTypeCode, WorkTypeUnit unit, WorkTypeClassification classCifi) {

		return new WorkType(new WorkTypeCode(workTypeCode), new WorkTypeSymbolicName(""), new WorkTypeName(""),
				new WorkTypeAbbreviationName(""), new WorkTypeMemo(""),
				new DailyWork(unit, classCifi, classCifi, classCifi));
	}
}
