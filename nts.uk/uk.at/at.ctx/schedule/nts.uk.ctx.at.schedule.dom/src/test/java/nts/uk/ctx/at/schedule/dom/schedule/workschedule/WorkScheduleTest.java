package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

public class WorkScheduleTest {
	
	@Injectable
	WorkSchedule.Require require;

	@Test
	public void getters() {
		WorkSchedule data = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, new ArrayList<>(),
				new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty());
		NtsAssert.invokeGetters(data);
	}
	
	@Test
	public void testCreate_throwException(@Injectable WorkInformation workInformation) {
		
		new Expectations() {{
			
			workInformation.checkNormalCondition(require);
			result = false;
			
		}};
		
		NtsAssert.businessException("xxx", 
				() -> WorkSchedule.create(require, "empId", GeneralDate.ymd(2020, 11, 1), workInformation));
		
	}
	
	@Test
	public void testCreate(
			@Injectable WorkInformation workInformation,
			@Mocked AffiliationInforOfDailyAttd affInfo,
			@Mocked WorkInfoOfDailyAttendance workInfo,
			@Mocked TimeLeavingOfDailyAttd timeLeaving
			) {
		
		new Expectations() {{
			
			workInformation.checkNormalCondition(require);
			result = true;
			
		}};
		
		WorkSchedule result = WorkSchedule.create(require, "empId", GeneralDate.ymd(2020, 11, 1), workInformation);
		
		assertThat( result.getEmployeeID() ).isEqualTo( "empId" );
		assertThat ( result.getYmd() ).isEqualTo( GeneralDate.ymd(2020, 11, 1) );
		assertThat ( result.getConfirmedATR() ).isEqualTo( ConfirmedATR.UNSETTLED );
		assertThat ( result.getLstBreakTime() ).isEmpty();
		assertThat ( result.getLstEditState() ).isEmpty();
		assertThat ( result.getOptAttendanceTime() ).isEmpty();
		assertThat ( result.getOptSortTimeWork() ).isEmpty();
		
		// TODO affInfo, workInfo, timeLeavingをどうやってテストすればいいなのまだ微妙
		assertThat ( result.getAffInfo() ).isEqualTo( affInfo );
		assertThat ( result.getWorkInfo() ).isEqualTo( workInfo );
		assertThat ( result.getOptTimeLeaving().get() ).isEqualTo( timeLeaving );
		
	}
}
