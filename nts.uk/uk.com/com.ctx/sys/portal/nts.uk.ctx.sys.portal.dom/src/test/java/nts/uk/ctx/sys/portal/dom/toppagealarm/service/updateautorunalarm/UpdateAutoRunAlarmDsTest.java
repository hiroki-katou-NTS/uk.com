package nts.uk.ctx.sys.portal.dom.toppagealarm.service.updateautorunalarm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.AtomTaskAssert;
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;
import nts.uk.ctx.sys.portal.dom.toppagealarm.service.updateautorunalarm.UpdateAutoRunAlarmDs.UpdateAutoRunAlarmRequire;

@RunWith(JMockit.class)
public class UpdateAutoRunAlarmDsTest {

	@Injectable
	UpdateAutoRunAlarmRequire require;
	
	@Test
	public void UpdateAutoRunAlarmDsTest1() {
		// given
		String cid = "cid";
		AlarmClassification alarmCls = AlarmClassification.ALARM_LIST;
		List<String> sids = new ArrayList<>();
		sids.add("sid");

		List<ToppageAlarmData> autoRunAlarm = UpdateAutoRunAlarmDsHelper.mockR1();

		new Expectations() {
			{
				require.getAutoRunAlarm(cid, alarmCls, sids);
				result = autoRunAlarm;
			}
		};

		// when
		Supplier<AtomTask> result = () -> UpdateAutoRunAlarmDs.create(require, cid, alarmCls, sids);

		// then
		AtomTaskAssert.atomTask(result);
	}
}
