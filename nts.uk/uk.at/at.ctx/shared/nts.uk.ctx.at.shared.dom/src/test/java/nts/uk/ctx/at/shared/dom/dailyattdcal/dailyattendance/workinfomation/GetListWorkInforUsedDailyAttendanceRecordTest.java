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

public class GetListWorkInforUsedDailyAttendanceRecordTest {

	@Test
	public void testGetListWorkInfo() {
		WorkInformation recordInfo1 = new WorkInformation("ti1", "ty1");
		WorkInformation recordInfo2 = new WorkInformation("ti2", "ty2");
		WorkInformation recordInfo3 = new WorkInformation(null, "ty3");
		WorkInformation recordInfo4 = new WorkInformation("ti3", "ty2");
		WorkInformation recordInfo5 = new WorkInformation("ti2", "ty2");
		List<WorkInfoOfDailyAttendance> lstWorkInfoOfDailyAttendance = Arrays.asList(
				new WorkInfoOfDailyAttendance(recordInfo1, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
						DayOfWeek.FRIDAY, new ArrayList<>()),
				new WorkInfoOfDailyAttendance(recordInfo2, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
						DayOfWeek.FRIDAY, new ArrayList<>()),
				new WorkInfoOfDailyAttendance(recordInfo3, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
						DayOfWeek.FRIDAY, new ArrayList<>()),
				new WorkInfoOfDailyAttendance(recordInfo4, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
						DayOfWeek.FRIDAY, new ArrayList<>()),
				new WorkInfoOfDailyAttendance(recordInfo5, null, null, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
						DayOfWeek.FRIDAY, new ArrayList<>()));
		
		List<WorkInformation> listWorkInformation= GetWorkInforUsedDailyAttenRecordService.getListWorkInfo(lstWorkInfoOfDailyAttendance);
		assertThat(
				listWorkInformation.stream().sorted(
						(x, y) -> x.getWorkTypeCode().v().compareTo(y.getWorkTypeCode().v())).collect(Collectors.toList()))
				.extracting(d->d.getWorkTypeCode().v())
				.containsExactly("ty1", "ty2","ty2","ty3");

		assertThat(
				listWorkInformation.stream().sorted(
						(x, y) -> x.getWorkTypeCode().v().compareTo(y.getWorkTypeCode().v())).collect(Collectors.toList()))
				.extracting(d->d.getWorkTimeCode())
				.containsExactly(new WorkTimeCode("ti1") ,new WorkTimeCode("ti2"),new WorkTimeCode("ti3"),null);
	}
	
}
