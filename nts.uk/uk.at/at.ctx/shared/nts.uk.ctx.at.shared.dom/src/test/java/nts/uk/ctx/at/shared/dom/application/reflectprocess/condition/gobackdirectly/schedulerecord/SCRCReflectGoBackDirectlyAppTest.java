package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.schedulerecord;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert.Invoke;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.ApplicationStatusShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.ReflectGoBackDirectly;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class SCRCReflectGoBackDirectlyAppTest {

	@Injectable

	private SCRCReflectGoBackDirectlyApp.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →勤務情報の反映がある
	 * 
	 * 準備するデータ
	 * 
	 * →[勤務情報を反映する]が「1:反映する」or「2：申請時に決める、3：申請時に決める」
	 * 
	 * → １日の勤務.１日!＝休日出勤or 振出
	 */

	@Test
	public void testReflectWorkType() {

		// case [勤務情報を反映する]が「2：申請時に決める」

		ReflectGoBackDirectly reflect = new ReflectGoBackDirectly("", ApplicationStatusShare.DO_NOT_REFLECT_1);
		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.of(createWorkTypeReflect());
			}
		};

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);// 日別勤怠(申請反映用Work)
		GoBackDirectlyShare appGoback = createGoBackApp(NotUseAtr.USE);// 勤務を変更する

		List<Integer> actualResult = new ArrayList<>();

		actualResult.addAll(SCRCReflectGoBackDirectlyApp.reflect(require, "", appGoback, dailyApp, reflect));

		assertThat(actualResult).isEqualTo(Arrays.asList(1, 2, 1292, 1293));

		// case [勤務情報を反映する]が「1:反映する」
		ReflectGoBackDirectly reflect2 = new ReflectGoBackDirectly("", ApplicationStatusShare.DO_REFLECT);
		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.of(createWorkTypeReflect());
			}
		};

		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);// 日別勤怠(申請反映用Work)
		GoBackDirectlyShare appGoback2 = createGoBackApp(NotUseAtr.NOT_USE);// 勤務を変更する

		List<Integer> actualResult2 = new ArrayList<>();

		actualResult2.addAll(SCRCReflectGoBackDirectlyApp.reflect(require, "", appGoback2, dailyApp2, reflect2));

		assertThat(actualResult2).isEqualTo(Arrays.asList(1, 2, 1292, 1293));
	}

	/*
	 * テストしたい内容
	 * 
	 * →勤務情報の反映がない
	 * 
	 * 準備するデータ
	 * 
	 * →[勤務情報を反映する]が「反映しない」
	 * 
	 * → or [勤務情報を反映する]!＝「反映しない」 と 「勤務種類コード = 休日出勤 or =振出」
	 */

	@Test
	public void testNoReflectWorkType() {

		// case [勤務情報を反映する]が「反映しない」
		ReflectGoBackDirectly reflect = new ReflectGoBackDirectly("", ApplicationStatusShare.DO_NOT_REFLECT);

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);// 日別勤怠(申請反映用Work)
		GoBackDirectlyShare appGoback = createGoBackApp(NotUseAtr.USE);// 勤務を変更する

		List<Integer> actualResult = new ArrayList<>();

		actualResult.addAll(SCRCReflectGoBackDirectlyApp.reflect(require, "", appGoback, dailyApp, reflect));

		assertThat(actualResult).isEmpty();

		// case [勤務情報を反映する]が「1:反映する」と 「勤務種類コード = 休日出勤 or =振出」
		ReflectGoBackDirectly reflect2 = new ReflectGoBackDirectly("", ApplicationStatusShare.DO_REFLECT);
		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.of(createWorkTypeNoReflect());
			}
		};

		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);// 日別勤怠(申請反映用Work)
		GoBackDirectlyShare appGoback2 = createGoBackApp(NotUseAtr.NOT_USE);// 勤務を変更する

		List<Integer> actualResult2 = new ArrayList<>();

		actualResult2.addAll(SCRCReflectGoBackDirectlyApp.reflect(require, "", appGoback2, dailyApp2, reflect2));

		assertThat(actualResult2).isEmpty();

		// case [勤務情報を反映する]が「申請時に決めると 「勤務種類コード = 休日出勤 or =振出」
		ReflectGoBackDirectly reflect3 = new ReflectGoBackDirectly("", ApplicationStatusShare.DO_NOT_REFLECT_1);

		DailyRecordOfApplication dailyApp3 = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);// 日別勤怠(申請反映用Work)
		GoBackDirectlyShare appGoback3 = createGoBackApp(NotUseAtr.NOT_USE);// 勤務を変更する

		List<Integer> actualResult3 = new ArrayList<>();

		actualResult3.addAll(SCRCReflectGoBackDirectlyApp.reflect(require, "", appGoback3, dailyApp3, reflect3));

		assertThat(actualResult3).isEmpty();

	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →１日の勤務.１日＝休日出勤or振出
	 * 
	 * → true or １日の勤務.午前＝振出
	 * 
	 * → true or １日の勤務.午後＝振出-> true
	 * 
	 * 準備するデータ
	 * 
	 * →勤務種類の分類 =休日出勤, 勤務種類の分類 = 振出;
	 * 
	 * →勤務の単位 = 1日 or 午前と午後
	 */

	@Test
	public void testWorkType() {

		// 勤務種類コードが休日出勤
		// 勤務の単位 = 1日
		Optional<WorkType> workTypeOpt = Optional.of(createWorkType("001", // 勤務種類コード
				WorkTypeUnit.OneDay.value, // 勤務の単位1日, 午前と午後
				WorkTypeClassification.Absence.value, WorkTypeClassification.Absence.value, //
				WorkTypeClassification.HolidayWork.value));// 1日 - 振出

		boolean check = Invoke.staticMethod(SCRCReflectGoBackDirectlyApp.class, "checkWorkType", workTypeOpt);

		assertThat(check).isTrue();

		// 勤務種類コードが振出
		// 勤務の単位 = 午前と午後
		Optional<WorkType> workTypeOpt2 = Optional.of(createWorkType("001", // 勤務種類コード
				WorkTypeUnit.MonringAndAfternoon.value, // 午前と午後
				WorkTypeClassification.Shooting.value, WorkTypeClassification.Shooting.value, //
				WorkTypeClassification.Absence.value));// 1日 - 振出

		boolean check2 = Invoke.staticMethod(SCRCReflectGoBackDirectlyApp.class, "checkWorkType", workTypeOpt2);

		assertThat(check2).isTrue();

		// 勤務種類コード != 休日出勤 と !=振出
		Optional<WorkType> workTypeOpt3 = Optional.of(createWorkType("001", // 勤務種類コード
				WorkTypeUnit.OneDay.value, // 勤務の単位1日, 午前と午後
				WorkTypeClassification.Absence.value, WorkTypeClassification.Absence.value, //
				WorkTypeClassification.Absence.value));// 1日 - 振出

		boolean check3 = Invoke.staticMethod(SCRCReflectGoBackDirectlyApp.class, "checkWorkType", workTypeOpt3);

		assertThat(check3).isFalse();

	}

	private WorkType createWorkTypeReflect() {
		return createWorkType("AAAA", WorkTypeUnit.OneDay.value, WorkTypeClassification.Absence.value,
				WorkTypeClassification.Absence.value, WorkTypeClassification.Absence.value);
	}

	private WorkType createWorkTypeNoReflect() {
		return createWorkType("AAAA", WorkTypeUnit.OneDay.value, WorkTypeClassification.Shooting.value,
				WorkTypeClassification.Shooting.value, WorkTypeClassification.Shooting.value);
	}

	private WorkType createWorkType(String workTypeCode, int workTypeUnit, int morning, int afternoon, int oneDay) {

		return WorkType.createSimpleFromJavaType(workTypeCode, "A", "AA", "A", "b", workTypeUnit, oneDay, morning,
				afternoon);
	}

	private GoBackDirectlyShare createGoBackApp(NotUseAtr changedWork) {
		Optional<WorkInformation> dataWork = Optional.of(new WorkInformation("001", "001"));
		return new GoBackDirectlyShare(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, Optional.ofNullable(changedWork),
				dataWork);

	}

}
