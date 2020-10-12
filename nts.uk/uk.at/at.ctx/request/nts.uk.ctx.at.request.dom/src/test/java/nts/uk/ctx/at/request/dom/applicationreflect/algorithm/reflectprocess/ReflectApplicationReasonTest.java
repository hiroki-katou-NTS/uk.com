package nts.uk.ctx.at.request.dom.applicationreflect.algorithm.reflectprocess;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.testing.assertion.NtsAssert.Invoke;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.applicationreflect.algorithm.common.ReflectApplicationHelper;
import nts.uk.ctx.at.request.dom.reasonappdaily.ApplicationReasonInfo;
import nts.uk.ctx.at.request.dom.reasonappdaily.ApplicationTypeReason;
import nts.uk.ctx.at.request.dom.reasonappdaily.ReasonApplicationDailyResult;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

@RunWith(JMockit.class)
public class ReflectApplicationReasonTest {

	@Injectable
	private ReflectApplicationReason.Require require;

	/*
	 * テストしたい内容
	 * 
	 * → 申請理由の反映ができない
	 * 
	 * 準備するデータ
	 * 
	 * → 理由の反映がない
	 * 
	 */
	@Test
	public void test() {

		Application application = ReflectApplicationHelper.createApp(PrePostAtr.POSTERIOR);

		val actualResult = ReflectApplicationReason.reflectReason(require, application, GeneralDate.today());

		assertThat(actualResult).isEmpty();
	}

	/*
	 * テストしたい内容
	 * 
	 * → 申請理由の反映ができる（新規追加）
	 * 
	 * 準備するデータ
	 * 
	 * → 理由の反映がある
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testAdd() {

		Application application = ReflectApplicationHelper.createAppWithReason(ApplicationType.ABSENCE_APPLICATION, 1,
				"AAAA");

		new Expectations() {
			{
				require.findReasonAppDaily(anyString, GeneralDate.today(), (PrePostAtr) any, (ApplicationType) any,
						(Optional<OvertimeAppAtr>) any);
				result = Arrays.asList();

				// addメソッドを呼び出すかどうかを確認します
				new MockUp<ReflectApplicationReason>() {
					@Mock(invocations = 1)
					public ReasonApplicationDailyResult createReason(Application application, GeneralDate date) {
						return null;
					}
				};
			}
		};

		val actualResult = ReflectApplicationReason.reflectReason(require, application, GeneralDate.today());
		NtsAssert.atomTask(() -> actualResult.get(), any -> require.addUpdateReason(any.get()),
				any -> require.processCreateHist(any.get(), any.get(), any.get(), any.get(), any.get()));

	}

	/*
	 * テストしたい内容
	 * 
	 * → 申請理由の反映ができる（更新）
	 * 
	 * 準備するデータ
	 * 
	 * → 理由の反映がある
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {

		Application application = ReflectApplicationHelper.createAppWithReason(ApplicationType.ABSENCE_APPLICATION, 1,
				"AAAA");

		new Expectations() {
			{
				require.findReasonAppDaily(anyString, GeneralDate.today(), (PrePostAtr) any, (ApplicationType) any,
						(Optional<OvertimeAppAtr>) any);
				result = Arrays.asList(new ReasonApplicationDailyResult("1", GeneralDate.today(),
						new ApplicationTypeReason(ApplicationType.ABSENCE_APPLICATION, Optional.empty()),
						PrePostAtr.POSTERIOR,
						new ApplicationReasonInfo(new AppStandardReasonCode(1), new AppReason("AAAA"))));
				// addメソッドを呼び出すかどうかを確認します
				new MockUp<ReflectApplicationReason>() {
					@Mock(invocations = 0)
					public ReasonApplicationDailyResult createReason(Application application, GeneralDate date) {
						return null;
					}
				};

			}
		};

		val actualResult = ReflectApplicationReason.reflectReason(require, application, GeneralDate.today());
		NtsAssert.atomTask(() -> actualResult.get(), any -> require.addUpdateReason(any.get()),
				any -> require.processCreateHist(any.get(), any.get(), any.get(), any.get(), any.get()));

	}

	//AとBから値と勤怠項目IDを作成します
	@Test
	public void testCreateId() {
		// private static Map<Integer, String>
		// createMap(List<ReasonApplicationDailyResult> apps, Application application) {

		List<ReasonApplicationDailyResult> apps = Arrays.asList(new ReasonApplicationDailyResult("1",
				GeneralDate.ymd(2020, 05, 01),
				new ApplicationTypeReason(ApplicationType.ABSENCE_APPLICATION, Optional.empty()), PrePostAtr.POSTERIOR,
				new ApplicationReasonInfo(new AppStandardReasonCode(1), new AppReason("AA"))));
		Invoke.staticMethod(ReflectApplicationReason.class, "createMap",  null);
	}

	private ReasonApplicationDailyResult create(GeneralDate date, ApplicationType appType, PrePostAtr prePostAtr,
			Integer code, String appReason) {
		return new ReasonApplicationDailyResult("1", date, new ApplicationTypeReason(appType, Optional.empty()),
				prePostAtr, new ApplicationReasonInfo(new AppStandardReasonCode(code), new AppReason(appReason)));
	}
}
