package nts.uk.ctx.at.record.pubimp.dailyprocess.attendancetime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyLateAndLeaveEarlyTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyLateAndLeaveEarlyTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyLateAndLeaveEarlyTimePubImport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.exportparam.LateLeaveEarlyAtr;

/**
 * RequestListNo197
 * @author keisuke_hoshina
 *
 */
@Stateless
public class DailyLateAndLeaveEarlyTimePubImpl implements DailyLateAndLeaveEarlyTimePub{

	@Inject
	private AttendanceTimeRepository attendanceTimeRepository; 
	
	@Override
	public DailyLateAndLeaveEarlyTimePubExport getLateLeaveEarly(DailyLateAndLeaveEarlyTimePubImport imp) {
		val domains = attendanceTimeRepository.findByPeriodOrderByYmd(imp.getEmployeeId(), imp.getDaterange());
		Map<GeneralDate,List<LateLeaveEarlyAtr>> returnMap = new HashMap<>();
		for(AttendanceTimeOfDailyPerformance nowDomain : domains) {
			List<LateLeaveEarlyAtr> returnList = new ArrayList<>();
			if(nowDomain != null
				&& nowDomain.getActualWorkingTimeOfDaily() != null
				&& nowDomain.getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {
				returnList = getLateList(nowDomain.getActualWorkingTimeOfDaily().getTotalWorkingTime().getLateTimeOfDaily());
				returnList = getLeaveEarlyList(nowDomain.getActualWorkingTimeOfDaily().getTotalWorkingTime().getLeaveEarlyTimeOfDaily(),
											   returnList);
			}
			returnMap.put(nowDomain.getYmd(), returnList);
		}
		return new DailyLateAndLeaveEarlyTimePubExport(returnMap);
	}

	private List<LateLeaveEarlyAtr> getLeaveEarlyList(List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily,
			List<LateLeaveEarlyAtr> returnList) {
		
		leaveEarlyTimeOfDaily.forEach(tc ->{
			val test = returnList.stream()
								 .filter(ts -> ts.getWorkNo().equals(tc.getWorkNo()))
								 .findFirst();
			if(test.isPresent()) {
				int index = returnList.indexOf(test.get());
				returnList.get(index).setLeaveEarly(true);
			}
			else {
				returnList.add(new LateLeaveEarlyAtr(test.get().getWorkNo(),false,true));
			}
		});
		return returnList;
	}

	private List<LateLeaveEarlyAtr> getLateList(List<LateTimeOfDaily> lateTimeOfDaily) {
		List<LateLeaveEarlyAtr> returnList = new ArrayList<>();
		for(LateTimeOfDaily a : lateTimeOfDaily) {
			returnList.add(new LateLeaveEarlyAtr(a.getWorkNo(),true,false));
		}
		return returnList;
	}

}
