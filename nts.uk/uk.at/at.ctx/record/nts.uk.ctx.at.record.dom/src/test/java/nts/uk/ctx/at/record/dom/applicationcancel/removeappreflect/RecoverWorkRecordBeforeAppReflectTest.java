package nts.uk.ctx.at.record.dom.applicationcancel.removeappreflect;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.application.state.RCReflectStatusResult;
import nts.uk.ctx.at.record.dom.adapter.request.application.state.RCReflectedState;
import nts.uk.ctx.at.record.dom.applicationcancel.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class RecoverWorkRecordBeforeAppReflectTest {

	@Injectable
	private RecoverWorkRecordBeforeAppReflect.Require require;

	/*
	 * テストしたい内容
	 * 
	 * → 反映状態を更新しない
	 * 
	 * 準備するデータ
	 * 
	 * →日別実績(work）がない
	 */
	@Test
	public void test() {

		ApplicationShare app = ReflectApplicationHelper.createAppRecord();

		new Expectations() {
			{
				require.findDaily(anyString, (GeneralDate) any);
				result = Optional.empty();
			}
		};

		RCRecoverAppReflectOutput result = RecoverWorkRecordBeforeAppReflect.process(require, app,
				GeneralDate.ymd(2021, 04, 21), createStatus(RCReflectedState.REFLECTED), // before state
				NotUseAtr.NOT_USE);
		
		assertThat(result.getWorkRecord()).isEmpty();
		assertThat(result.getReflectStatus().getReflectStatus()).isEqualTo(RCReflectedState.CANCELED);

	}

	/*
	 * テストしたい内容
	 * 
	 * → 反映状態を更新する
	 * 
	 * → データベースを登録しない
	 * 
	 * 準備するデータ
	 * 
	 * →日別実績(work）がある
	 * 
	 * → [input.DB登録するか区分]＝しない
	 */
	@Test
	public void test2(@Mocked WorkingConditionService ws) {

		ApplicationShare app = ReflectApplicationHelper.createAppStamp(PrePostAtrShare.POSTERIOR);

		new Expectations() {
			{
				require.findDaily(anyString, (GeneralDate) any);
				result = Optional.of(ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD));

			}
		};

		RCRecoverAppReflectOutput result = RecoverWorkRecordBeforeAppReflect.process(require, app,
				GeneralDate.ymd(2021, 04, 21), createStatus(RCReflectedState.REFLECTED), NotUseAtr.NOT_USE);
		result.getAtomTask().run();
		new Verifications() {
			{
				require.addAllDomain((IntegrationOfDaily) any);
				times = 0;

				require.updateAppReflectHist(anyString, anyString, (GeneralDate) any, (ScheduleRecordClassifi) any,
						anyBoolean);
				times = 0;
			}
		};

		assertThat(result.getWorkRecord()).isNotNull();
		assertThat(result.getReflectStatus().getReflectStatus()).isEqualTo(RCReflectedState.CANCELED);

	}
	
	/*
	 * テストしたい内容
	 * 
	 * → 反映状態を更新する
	 * 
	 * → データベースを登録する
	 * 
	 * 準備するデータ
	 * 
	 * → 日別実績(work）がある
	 * 
	 * → [input.DB登録するか区分]＝する
	 */
	@Test
	public void test3(@Mocked WorkingConditionService ws) {

		ApplicationShare app = ReflectApplicationHelper.createAppStamp(PrePostAtrShare.POSTERIOR);

		new Expectations() {
			{
				require.findDaily(anyString, (GeneralDate) any);
				result = Optional.of(ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD));
			}
		};

		RCRecoverAppReflectOutput result = RecoverWorkRecordBeforeAppReflect.process(require, app,
				GeneralDate.ymd(2021, 04, 21), createStatus(RCReflectedState.REFLECTED), NotUseAtr.USE);
		result.getAtomTask().run();
		new Verifications() {
			{
				require.addAllDomain((IntegrationOfDaily) any);
				times = 1;

				require.updateAppReflectHist(anyString, anyString, (GeneralDate) any, (ScheduleRecordClassifi) any,
						anyBoolean);
				times = 1;
			}
		};

		assertThat(result.getWorkRecord()).isNotNull();
		assertThat(result.getReflectStatus().getReflectStatus()).isEqualTo(RCReflectedState.CANCELED);

	}
	private RCReflectStatusResult createStatus(RCReflectedState status) {
		return new RCReflectStatusResult(status, null, null);
	}
}
