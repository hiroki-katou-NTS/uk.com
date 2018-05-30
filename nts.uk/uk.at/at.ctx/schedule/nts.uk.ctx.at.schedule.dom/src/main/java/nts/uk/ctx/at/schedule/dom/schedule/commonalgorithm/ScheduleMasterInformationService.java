package nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;

/**
 * 
 * @author sonnh1
 *
 */
public interface ScheduleMasterInformationService {
	/**
	 * 勤務予定マスタ情報を取得する(lấy các thông tin master 勤務予定マスタ情報)
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @param exeId
	 * * @param empGeneralInfo
	 * @return ScheduleMasterInformationDto
	 */

	public Optional<ScheduleMasterInformationDto> getScheduleMasterInformationDto(String employeeId,
			GeneralDate baseDate, String exeId, EmployeeGeneralInfoImported empGeneralInfo);
}
