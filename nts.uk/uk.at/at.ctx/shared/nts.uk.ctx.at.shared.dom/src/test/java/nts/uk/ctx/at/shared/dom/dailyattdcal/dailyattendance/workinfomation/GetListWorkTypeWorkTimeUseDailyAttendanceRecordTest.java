package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;

public class GetListWorkTypeWorkTimeUseDailyAttendanceRecordTest {

	@Test
	public void testGetdata() {
		WorkInformation recordInfo1 = new WorkInformation("ti1", "ty1");
		WorkInformation recordInfo2 = new WorkInformation("ti2", "ty2");
		WorkInformation recordInfo3 = new WorkInformation(null, "ty3");
		List<WorkInfoOfDailyAttendance> lstWorkInfoOfDailyAttendance = Arrays.asList(
				new WorkInfoOfDailyAttendance(recordInfo1, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
						DayOfWeek.FRIDAY, new ArrayList<>()),
				new WorkInfoOfDailyAttendance(recordInfo2, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
						DayOfWeek.FRIDAY, new ArrayList<>()),
				new WorkInfoOfDailyAttendance(recordInfo3, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
						DayOfWeek.FRIDAY, new ArrayList<>()));
		WorkTypeWorkTimeUseDailyAttendanceRecord data = GetListWorkTypeWorkTimeUseDailyAttendanceRecord
				.getdata(lstWorkInfoOfDailyAttendance);
		assertThat(data.getLstWorkTimeCode().stream().sorted((x, y) -> x.v().compareTo(y.v()))
				.collect(Collectors.toList())).extracting(d -> d.v()).containsExactly("ti1", "ti2");

		assertThat(data.getLstWorkTypeCode().stream().sorted((x, y) -> x.v().compareTo(y.v()))
				.collect(Collectors.toList())).extracting(d -> d.v()).containsExactly("ty1", "ty2", "ty3");
	}

}
