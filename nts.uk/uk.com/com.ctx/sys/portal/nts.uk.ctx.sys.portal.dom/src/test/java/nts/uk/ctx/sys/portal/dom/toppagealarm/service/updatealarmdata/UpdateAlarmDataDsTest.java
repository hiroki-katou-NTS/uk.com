package nts.uk.ctx.sys.portal.dom.toppagealarm.service.updatealarmdata;

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
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmListPatternCode;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;
import nts.uk.ctx.sys.portal.dom.toppagealarm.service.updatealarmdata.UpdateAlarmDataDs.UpdateAlarmDataRequire;

@RunWith(JMockit.class)
public class UpdateAlarmDataDsTest {

	@Injectable
	private UpdateAlarmDataRequire require;
	
	@Test
	public void updateAlarmDataDsTest1() {
		
		//given
		String cid = "fakeCid";
		List<String> sids = new ArrayList<>();
		sids.add("sid");
		DisplayAtr displayAtr = DisplayAtr.SUPERIOR;
		String patternCode = "patternCode";
		AlarmListPatternCode alarmListPatternCode = new AlarmListPatternCode(patternCode);
		
		//[R-1]
		List<ToppageAlarmData> toppageAlarmDatas = UpdateAlarmDataDsHelper.mockR1();
		
		new Expectations() {
			{
				require.getAlarmList(cid, sids, displayAtr, alarmListPatternCode);
				result = toppageAlarmDatas;
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> UpdateAlarmDataDs.create(require, cid, sids, patternCode, displayAtr.value);
		
		 //then
		AtomTaskAssert.atomTask(result);
	}
}
