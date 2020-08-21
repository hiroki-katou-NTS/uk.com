package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import static org.assertj.core.api.Assertions.tuple;

public class GetListWorkInforUsedDailyAttendanceRecordTest {

	@Test
	public void testGetListWorkInfo() {
		WorkInformation recordInfo1 = new WorkInformation("ti1", "ty1");
		WorkInformation recordInfo2 = new WorkInformation("ti2", "ty2");
		WorkInformation recordInfo3 = new WorkInformation(null, "ty3");
		WorkInformation recordInfo4 = new WorkInformation("ti3", "ty2");
		WorkInformation recordInfo5 = new WorkInformation("ti2", "ty2");
		WorkInformation recordInfo6 = new WorkInformation("ti2", "ty3");
		List<WorkInfoOfDailyAttendance> lstWorkInfoOfDailyAttendance = Arrays.asList(
				WorkInfoOfDailyAttendanceHelper.getData(recordInfo1),
				WorkInfoOfDailyAttendanceHelper.getData(recordInfo2),
				WorkInfoOfDailyAttendanceHelper.getData(recordInfo3),
				WorkInfoOfDailyAttendanceHelper.getData(recordInfo4),
				WorkInfoOfDailyAttendanceHelper.getData(recordInfo5),
				WorkInfoOfDailyAttendanceHelper.getData(recordInfo6));
		
		List<WorkInformation> listWorkInformation= GetWorkInforUsedDailyAttenRecordService.getListWorkInfo(lstWorkInfoOfDailyAttendance);
		assertThat(listWorkInformation).extracting(d->d.getWorkTimeCode(), d->d.getWorkTypeCode())
				.containsExactly(
						tuple( new WorkTimeCode("ti1"), new WorkTypeCode("ty1")),
						tuple( new WorkTimeCode("ti2"), new WorkTypeCode("ty2")),
						tuple( null, new WorkTypeCode("ty3")),
						tuple( new WorkTimeCode("ti3"), new WorkTypeCode("ty2")),
						tuple( new WorkTimeCode("ti2"), new WorkTypeCode("ty3")));

	}
	
}
