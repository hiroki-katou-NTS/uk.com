package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.DisplayInWorkInfoInput;

/**
 * @author chungnt 
 * ScreenQuery: 予定・実績を取得する
 */

@Stateless
public class GetScheduleActualOfWorkInfo002 {
	@Inject
	private GetWorkRecord getWorkRecord;

	@Inject
	private GetScheduleOfWorkInfo002 getScheduleOfWorkInfo002;

	public List<WorkScheduleWorkInforDto> getDataScheduleAndAactualOfWorkInfo(DisplayInWorkInfoInput param) {
		// lay data Schedule
		List<WorkScheduleWorkInforDto> listDataSchedule = getScheduleOfWorkInfo002.getDataScheduleOfWorkInfo(param);

		if (param.getActualData()) {
			// lay data Daily
			List<WorkScheduleWorkInforDto> listDataDaily = getWorkRecord.get(param);
			
			for (WorkScheduleWorkInforDto ds : listDataSchedule) {				
				if (!listDataDaily.stream().anyMatch(c -> c.employeeId.equals(ds.employeeId) && c.date.equals(ds.date))) {
					listDataDaily.add(ds);
				}
			}
			
			return listDataDaily;
		}

		return listDataSchedule;
	}
}
