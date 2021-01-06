/**
 * 
 */
package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.DisplayInWorkInfoInput;

/**
 * @author chungnt
 *
 */
@Stateless
public class GetDataDaily {

	@Inject
	private GetWorkActualOfWorkInfo002 getWorkRecord;

	public List<WorkScheduleWorkInforDto.Achievement> getDataDaily(DisplayInWorkInfoInput param) {

		// lay data Daily
		List<WorkScheduleWorkInforDto> listDataDaily = getWorkRecord.getDataActualOfWorkInfo(param);
		List<WorkScheduleWorkInforDto.Achievement> result = new ArrayList<>();

		result = listDataDaily.stream().map(m -> {
			WorkScheduleWorkInforDto.Achievement arch = WorkScheduleWorkInforDto
					.Achievement
					.builder()
					.employeeId(m.getEmployeeId())
					.workTypeCode(m.getWorkTypeCode())
					.workTypeName(m.getWorkTypeName())
					.workTimeCode(m.getWorkTimeCode())
					.workTimeName(m.getWorkTimeName())
					.startTime(m.getStartTime())
					.endTime(m.getEndTime())
					.build();
			return arch;
		}).collect(Collectors.toList());
		
		return result;
	}
}
