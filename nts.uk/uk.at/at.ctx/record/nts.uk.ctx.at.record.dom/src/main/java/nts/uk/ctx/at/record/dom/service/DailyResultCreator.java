package nts.uk.ctx.at.record.dom.service;

import java.util.List;

import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyResult;

public class DailyResultCreator {
	public static List<DailyResult> create(
			List<WorkInfoOfDailyPerformance> lstWorkInfor,
			List<AttendanceTimeOfDailyPerformance> atdTime){

		return lstWorkInfor.stream()
				.map(c->
					DailyResult.builder()
							.ymd(c.getYmd())
							.workInfo(c.getWorkInformation())
							.attendanceTime(atdTime.stream().filter(atd->atd.getYmd()==c.getYmd()).map(atd->atd.getTime()).findFirst())
							.build())
				.collect(java.util.stream.Collectors.toList());
	}

}
