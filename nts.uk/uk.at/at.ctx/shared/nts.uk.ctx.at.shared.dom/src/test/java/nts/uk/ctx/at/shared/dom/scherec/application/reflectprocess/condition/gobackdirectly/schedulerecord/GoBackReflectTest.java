package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.gobackdirectly.schedulerecord;

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
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.testing.assertion.NtsAssert.Invoke;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback.ApplicationStatus;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class GoBackReflectTest {

	@Injectable

	private GoBackReflect.Require require;

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

		GoBackReflect reflect = new GoBackReflect("", ApplicationStatus.DO_NOT_REFLECT_1);
		new Expectations() {
			{
				require.workType(anyString, (WorkTypeCode)any);
				result = Optional.of(createWorkTypeReflect());
			}
		};

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);// 日別勤怠(申請反映用Work)
		GoBackDirectlyShare appGoback = createGoBackApp(NotUseAtr.USE);// 勤務を変更する

		List<Integer> actualResult = new ArrayList<>();

		actualResult.addAll(reflect.reflect(require, appGoback, dailyApp));

		assertThat(actualResult).isEqualTo(Arrays.asList(28, 1292, 1293, 29));

		// case [勤務情報を反映する]が「1:反映する」
		GoBackReflect reflect2 = new GoBackReflect("", ApplicationStatus.DO_REFLECT);
		new Expectations() {
			{
				require.workType(anyString, (WorkTypeCode)any);
				result = Optional.of(createWorkTypeReflect());
			}
		};

		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);// 日別勤怠(申請反映用Work)
		GoBackDirectlyShare appGoback2 = createGoBackApp(NotUseAtr.NOT_USE);// 勤務を変更する

		List<Integer> actualResult2 = new ArrayList<>();

		actualResult2.addAll(reflect2.reflect(require, appGoback2, dailyApp2));

		assertThat(actualResult2).isEqualTo(Arrays.asList(28, 1292, 1293, 29));
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
		GoBackReflect reflect = new GoBackReflect("", ApplicationStatus.DO_NOT_REFLECT);

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);// 日別勤怠(申請反映用Work)
		GoBackDirectlyShare appGoback = createGoBackApp(NotUseAtr.USE);// 勤務を変更する

		List<Integer> actualResult = new ArrayList<>();

		actualResult.addAll(reflect.reflect(require, appGoback, dailyApp));

		assertThat(actualResult).isEmpty();

		// case [勤務情報を反映する]が「1:反映する」と 「勤務種類コード = 休日出勤 or =振出」
		GoBackReflect reflect2 = new GoBackReflect("", ApplicationStatus.DO_REFLECT);
		new Expectations() {
			{
				require.workType(anyString, (WorkTypeCode)any);
				result = Optional.of(createWorkTypeNoReflect());
			}
		};

		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);// 日別勤怠(申請反映用Work)
		GoBackDirectlyShare appGoback2 = createGoBackApp(NotUseAtr.NOT_USE);// 勤務を変更する

		List<Integer> actualResult2 = new ArrayList<>();

		actualResult2.addAll(reflect2.reflect(require, appGoback2, dailyApp2));

		assertThat(actualResult2).isEmpty();

		// case [勤務情報を反映する]が「申請時に決めると 「勤務種類コード = 休日出勤 or =振出」
		GoBackReflect reflect3 = new GoBackReflect("", ApplicationStatus.DO_NOT_REFLECT_1);

		DailyRecordOfApplication dailyApp3 = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);// 日別勤怠(申請反映用Work)
		GoBackDirectlyShare appGoback3 = createGoBackApp(NotUseAtr.NOT_USE);// 勤務を変更する

		List<Integer> actualResult3 = new ArrayList<>();

		actualResult3.addAll(reflect3.reflect(require, appGoback3, dailyApp3));

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

		boolean check = NtsAssert.Invoke.privateMethod(new GoBackReflect(), "checkWorkType", workTypeOpt);

		assertThat(check).isTrue();

		// 勤務種類コードが振出
		// 勤務の単位 = 午前と午後
		Optional<WorkType> workTypeOpt2 = Optional.of(createWorkType("001", // 勤務種類コード
				WorkTypeUnit.MonringAndAfternoon.value, // 午前と午後
				WorkTypeClassification.Shooting.value, WorkTypeClassification.Shooting.value, //
				WorkTypeClassification.Absence.value));// 1日 - 振出

		boolean check2 = Invoke.privateMethod(new GoBackReflect(), "checkWorkType", workTypeOpt2);

		assertThat(check2).isTrue();

		// 勤務種類コード != 休日出勤 と !=振出
		Optional<WorkType> workTypeOpt3 = Optional.of(createWorkType("001", // 勤務種類コード
				WorkTypeUnit.OneDay.value, // 勤務の単位1日, 午前と午後
				WorkTypeClassification.Absence.value, WorkTypeClassification.Absence.value, //
				WorkTypeClassification.Absence.value));// 1日 - 振出

		boolean check3 = Invoke.privateMethod(new GoBackReflect(), "checkWorkType", workTypeOpt3);

		assertThat(check3).isFalse();

	}

	/*
	 * テストしたい内容
	 * 
	 * →直行直帰区分の反映
	 *    　①直行区分＝する→直行の反映
	 *    　②直帰区分＝する→直帰の反映
	 * 
	 * 準備するデータ
	 * 
	 * →直行直帰区分
	 * 
	 */

	@Test
	public void testGoOutBack() {
		
		//①直行区分＝する→直行の反映
		GoBackDirectlyShare setting = new GoBackDirectlyShare(NotUseAtr.USE, // 直行区分 = する
				NotUseAtr.NOT_USE, //直帰区分＝しない
				Optional.empty(),
				Optional.empty());
		GoBackReflect reflect = new GoBackReflect("", ApplicationStatus.DO_NOT_REFLECT);

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);
		//check before 
		assertThat(dailyApp.getWorkInformation().getGoStraightAtr()).isEqualTo(NotUseAttribute.Not_use);//直行区分
		assertThat(dailyApp.getWorkInformation().getBackStraightAtr()).isEqualTo(NotUseAttribute.Not_use);//直帰区分
		
		reflect.reflect(require, setting, dailyApp);
		//NotUseAttribute
		assertThat(dailyApp.getWorkInformation().getGoStraightAtr()).isEqualTo(NotUseAttribute.Use);//直行区分
		assertThat(dailyApp.getWorkInformation().getBackStraightAtr()).isEqualTo(NotUseAttribute.Not_use);//直帰区分
		
		//②直帰区分＝する→直帰の反映
		setting = new GoBackDirectlyShare(NotUseAtr.NOT_USE, // 直行区分 = しない
				NotUseAtr.USE, //直帰区分＝する
				Optional.empty(),
				Optional.empty());

		dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD);
		//check before 
		assertThat(dailyApp.getWorkInformation().getGoStraightAtr()).isEqualTo(NotUseAttribute.Not_use);//直行区分
		assertThat(dailyApp.getWorkInformation().getBackStraightAtr()).isEqualTo(NotUseAttribute.Not_use);//直帰区分
		
		reflect.reflect(require, setting, dailyApp);
		//NotUseAttribute
		assertThat(dailyApp.getWorkInformation().getGoStraightAtr()).isEqualTo(NotUseAttribute.Not_use);//直行区分
		assertThat(dailyApp.getWorkInformation().getBackStraightAtr()).isEqualTo(NotUseAttribute.Use);//直帰区分
		
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
