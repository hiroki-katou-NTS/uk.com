package nts.uk.ctx.at.record.dom.daily.ouen;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

/**
 * 
 * @author chungnt
 *
 */
@RunWith(JMockit.class)
public class RegisterOuenWorkTimeSheetOfDailyServiceTest {

	@Injectable
	private RegisterOuenWorkTimeSheetOfDailyService.Require require;
	private String empId = "empId";
	private GeneralDate ymd = GeneralDate.today();
	private List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailys = new ArrayList<>();
	private List<EditStateOfDailyPerformance> editStateOfDailyPerformance = new ArrayList<>();
	private EditStateOfDailyPerformance editStateOfDaily = new EditStateOfDailyPerformance(empId,
			ymd,
			new EditStateOfDailyAttd());
	
	// $実績の作業時間帯 notPresent
	@Test
	public void test1() {
		this.editStateOfDailyPerformance.add(editStateOfDaily);
		this.editStateOfDailyPerformance.add(editStateOfDaily);
		
		new Expectations() {
			{
				require.find(empId, ymd);
				
				require.get(empId, ymd);
				result = editStateOfDailyPerformance;
			}
		};
		
		OuenWorkTimeSheetOfDaily domain = new OuenWorkTimeSheetOfDaily(empId, ymd, ouenWorkTimeSheetOfDailys);
		
		AtomTask atomtask = RegisterOuenWorkTimeSheetOfDailyService.register(require, empId, ymd,
				ouenWorkTimeSheetOfDailys,
				EnumAdaptor.valueOf(1, EditStateSetting.class));
		
		NtsAssert.atomTask(() -> atomtask, any -> require.insert(domain));
	}

}
