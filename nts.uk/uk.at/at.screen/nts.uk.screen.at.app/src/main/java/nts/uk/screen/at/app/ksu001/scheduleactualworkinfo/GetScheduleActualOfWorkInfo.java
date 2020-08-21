/**
 * 
 */
package nts.uk.screen.at.app.ksu001.scheduleactualworkinfo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInfoParam;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.WorkScheduleWorkInforDto;

/**
 * @author laitv
 * ScreenQuery : 予定・実績を勤務情報で取得する
 */
@Stateless
public class GetScheduleActualOfWorkInfo {
	
	@Inject
	private GetScheduleOfWorkInfo getScheduleOfWorkInfo;
	@Inject
	private GetWorkActualOfWorkInfo getWorkActualOfWorkInfo;
	
	public List<WorkScheduleWorkInforDto> getDataScheduleAndAactualOfWorkInfo(DisplayInWorkInfoParam param) {
		
		List<WorkScheduleWorkInforDto> listDataSchedule = getScheduleOfWorkInfo.getDataScheduleOfWorkInfo(param);
		
		if (param.getActualData) {
			List<WorkScheduleWorkInforDto> listDataDayli = getWorkActualOfWorkInfo.GetDataActualOfWorkInfo(param);
		}
		
		
		
		// merge 2 list
		return listDataSchedule;
	}
}
