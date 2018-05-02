package nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm;

import nts.arc.time.GeneralDate;

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
	 * @return ScheduleMasterInformationDto
	 */
	public ScheduleMasterInformationDto getScheduleMasterInformationDto(String employeeId, GeneralDate baseDate,
			String exeId);
}
