package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;

public class EditStateOfDailyAttdTest {
	
	@Injectable
	EditStateOfDailyAttd.Require require;
	
	@Test
	public void testCreateByHandCorrection_myself() {
		
		// Mock
		new Expectations() {{
			
			require.getLoginEmployeeId();
			result = "Id1";
		}};
		
		// Action
		EditStateOfDailyAttd target = EditStateOfDailyAttd.createByHandCorrection(require, 1, "Id1");
		
		// Assert
		assertThat(target.getAttendanceItemId()).isEqualTo(1);
		assertThat(target.getEditStateSetting()).isEqualTo(EditStateSetting.HAND_CORRECTION_MYSELF);
	}
	
	@Test
	public void testCreateByHandCorrection_other() {
		
		// Mock
		new Expectations() {{
			
			require.getLoginEmployeeId();
			result = "Id2";
		}};
		
		// Action
		EditStateOfDailyAttd target = EditStateOfDailyAttd.createByHandCorrection(require, 1, "Id1");
		
		// Assert
		assertThat(target.getAttendanceItemId()).isEqualTo(1);
		assertThat(target.getEditStateSetting()).isEqualTo(EditStateSetting.HAND_CORRECTION_OTHER);
	}

}
