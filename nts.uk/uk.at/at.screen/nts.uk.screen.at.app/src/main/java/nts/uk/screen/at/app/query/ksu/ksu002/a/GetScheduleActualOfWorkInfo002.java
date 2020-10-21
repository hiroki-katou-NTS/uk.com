package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.DisplayInWorkInfoInput;

/**
 * 
 * @author chungnt ScreenQuery : 予定・実績を取得する
 *
 */

@Stateless
public class GetScheduleActualOfWorkInfo002 {

	@Inject
	private GetScheduleOfWorkInfo002 getScheduleOfWorkInfo002;
	@Inject
	private GetWorkRecord getWorkRecord;

	public List<WorkScheduleWorkInforDto> getDataScheduleAndAactualOfWorkInfo(DisplayInWorkInfoInput param) {

		// lay data Schedule
		List<WorkScheduleWorkInforDto> listDataSchedule = getScheduleOfWorkInfo002.getDataScheduleOfWorkInfo(param);

		if (param.getActualData()) {
			// lay data Daily
			List<WorkScheduleWorkInforDto> listDataDaily = getWorkRecord.get(param);
			// merge
			List<WorkScheduleWorkInforDto> listToRemove = new ArrayList<WorkScheduleWorkInforDto>();
			List<WorkScheduleWorkInforDto> listToAdd = new ArrayList<WorkScheduleWorkInforDto>();
			
			for (WorkScheduleWorkInforDto dataSchedule : listDataSchedule) {
				String sid = dataSchedule.employeeId;
				GeneralDate date = dataSchedule.date;
				
				Optional<WorkScheduleWorkInforDto> dataDaily = listDataDaily.stream().filter(data -> {
					if (data.employeeId.equals(sid) && data.date.equals(date))
						return true;
					return false;
				}).findFirst();
				
				if (dataDaily.isPresent()) {
					listToRemove.add(dataSchedule);
					listToAdd.add(dataDaily.get());
				}
			}
			
			listDataSchedule.removeAll(listToRemove);
			listDataSchedule.addAll(listToAdd);
		}

		return listDataSchedule;
	}

}
