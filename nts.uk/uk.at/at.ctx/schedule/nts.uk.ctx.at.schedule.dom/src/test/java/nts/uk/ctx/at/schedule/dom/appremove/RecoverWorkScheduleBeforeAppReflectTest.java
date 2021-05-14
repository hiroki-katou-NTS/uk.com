package nts.uk.ctx.at.schedule.dom.appremove;

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
import nts.uk.ctx.at.schedule.dom.appreflectprocess.change.ReflectApplicationHelper;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.change.state.SCReflectStatusResult;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.change.state.SCReflectedState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class RecoverWorkScheduleBeforeAppReflectTest {

	@Injectable
	private RecoverWorkScheduleBeforeAppReflect.Require require;

	/*
	 * テストしたい内容
	 * 
	 * → 反映状態を更新しない
	 * 
	 *  → データベースを登録しない
	 * 
	 * 準備するデータ
	 * 
	 * →勤務予定がない
	 */
	@Test
	public void test() {

		ApplicationShare app = ReflectApplicationHelper.createAppStamp(PrePostAtrShare.POSTERIOR);

		new Expectations() {
			{
				require.get(anyString, (GeneralDate) any);
				result = Optional.empty();
			}
		};

		SCRecoverAppReflectOutput result = RecoverWorkScheduleBeforeAppReflect.process(require, app,
				GeneralDate.ymd(2021, 04, 21), createStatus(SCReflectedState.REFLECTED), NotUseAtr.NOT_USE);
		assertThat(result.getSchedule()).isEmpty();
		assertThat(result.getReflectStatus().getReflectStatus()).isEqualTo(SCReflectedState.REFLECTED);
		new Verifications() {
			{
				require.insertSchedule((WorkSchedule) any);
				times = 0;

				require.updateAppReflectHist(anyString, anyString, (GeneralDate) any, (ScheduleRecordClassifi) any,
						anyBoolean);
				times = 0;
			}
		};
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
	 * → 勤務予定がある
	 * 
	 * → [input.DB登録するか区分]＝しない
	 */
	@Test
	public void test2(@Mocked WorkingConditionService ws) {

		ApplicationShare app = ReflectApplicationHelper.createAppStamp(PrePostAtrShare.POSTERIOR);

		new Expectations() {
			{
				require.get(anyString, (GeneralDate) any);
				result = Optional.of(ReflectApplicationHelper.createWorkSchedule());

				WorkingConditionService.findWorkConditionByEmployee(require, anyString, (GeneralDate) any);
				result = Optional.of(new WorkingConditionItem("", ManageAtr.USE, "1"));
			}
		};

		SCRecoverAppReflectOutput result = RecoverWorkScheduleBeforeAppReflect.process(require, app,
				GeneralDate.ymd(2021, 04, 21), createStatus(SCReflectedState.REFLECTED), NotUseAtr.NOT_USE);
		result.getAtomTask().run();
		new Verifications() {
			{
				require.insertSchedule((WorkSchedule) any);
				times = 0;

				require.updateAppReflectHist(anyString, anyString, (GeneralDate) any, (ScheduleRecordClassifi) any,
						anyBoolean);
				times = 0;
			}
		};

		assertThat(result.getSchedule()).isNotNull();
		assertThat(result.getReflectStatus().getReflectStatus()).isEqualTo(SCReflectedState.CANCELED);

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
	 * → 勤務予定がある
	 * 
	 * → [input.DB登録するか区分]＝する
	 */
	@Test
	public void test3(@Mocked WorkingConditionService ws) {

		ApplicationShare app = ReflectApplicationHelper.createAppStamp(PrePostAtrShare.POSTERIOR);

		new Expectations() {
			{
				require.get(anyString, (GeneralDate) any);
				result = Optional.of(ReflectApplicationHelper.createWorkSchedule());

				WorkingConditionService.findWorkConditionByEmployee(require, anyString, (GeneralDate) any);
				result = Optional.of(new WorkingConditionItem("", ManageAtr.USE, "1"));
			}
		};

		SCRecoverAppReflectOutput result = RecoverWorkScheduleBeforeAppReflect.process(require, app,
				GeneralDate.ymd(2021, 04, 21), createStatus(SCReflectedState.REFLECTED), NotUseAtr.USE);
		result.getAtomTask().run();
		new Verifications() {
			{
				require.insertSchedule((WorkSchedule) any);
				times = 1;

				require.updateAppReflectHist(anyString, anyString, (GeneralDate) any, (ScheduleRecordClassifi) any,
						anyBoolean);
				times = 1;
			}
		};

		assertThat(result.getSchedule()).isNotNull();
		assertThat(result.getReflectStatus().getReflectStatus()).isEqualTo(SCReflectedState.CANCELED);
	}

	private SCReflectStatusResult createStatus(SCReflectedState status) {
		return new SCReflectStatusResult(status, null, null);
	}
}
