package nts.uk.ctx.at.schedule.dom.appreflectprocess.change;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectedStateShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation.AddDataBeforeApplicationReflect;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation.AttendanceBeforeApplicationReflect;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.GetDomainReflectModelApp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@RunWith(JMockit.class)
public class ReflectApplicationWorkScheduleTest {

	@Injectable
	private ReflectApplicationWorkSchedule.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →[勤務予定の反映状態]が「未反映 」
	 * 
	 * 準備するデータ
	 * 
	 * →勤務予定から日別実績(work）を取得しない
	 */
	@Test
	public void testNotFoundSchedule() {

		val actualResult = ReflectApplicationWorkSchedule.process(require, "1", // CID
				SCReflectApplicationHelper.createApp(PrePostAtrShare.POSTERIOR), // 申請
				GeneralDate.today(), // 処理対象日
				SCReflectApplicationHelper.createReflectStatusResult());// 勤務予定の反映状態

		assertThat(actualResult.getLeft().getReflectStatus()).isEqualTo(ReflectedStateShare.NOTREFLECTED);

	}

	/*
	 * テストしたい内容
	 * 
	 * →[勤務予定の反映状態]が「反映済み」
	 * 
	 * →勤務予定が登録
	 * 
	 * →申請反映履歴が登録
	 * 
	 * 準備するデータ
	 * 
	 * →勤務予定から日別実績(work）がある
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testHasSchedule(@Mocked GetDomainReflectModelApp getReflect,
			@Mocked AddDataBeforeApplicationReflect addData) {

		new Expectations() {
			{
				require.get(anyString, (GeneralDate) any);
				result = Optional.of(SCReflectApplicationHelper.createWorkSchedule());

				GetDomainReflectModelApp.process(require, anyString, (ApplicationTypeShare) any,
						((Optional<Object>) any));
				result = SCReflectApplicationHelper.createReflectAppSet();

				AddDataBeforeApplicationReflect.process(require, (List<AttendanceBeforeApplicationReflect>) any,
						(IntegrationOfDaily) any);
				result = null;
			}
		};

		val actualResult = ReflectApplicationWorkSchedule.process(require, "1", // CID
				SCReflectApplicationHelper.createAppStamp(), // 申請
				GeneralDate.today(), // 処理対象日
				SCReflectApplicationHelper.createReflectStatusResult());// 勤務予定の反映状態

		assertThat(actualResult.getLeft().getReflectStatus()).isEqualTo(ReflectedStateShare.REFLECTED);

		NtsAssert.atomTask(() -> actualResult.getRight(), any -> require.insertSchedule(any.get()),
				any -> require.insertAppReflectHist(any.get()));

	}
}
