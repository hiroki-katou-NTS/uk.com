package nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm;

import nts.arc.time.GeneralDate;
/**
 * 勤務予定マスタ情報を取得する(lấy các thông tin master 勤務予定マスタ情報)
 * @author Trung Tran
 *
 */
public interface ScheduleMasterInformationRepository {
	public ScheduleMasterInformationDto getScheduleMasterInformationDto(String employeeId,GeneralDate baseDate);
}
