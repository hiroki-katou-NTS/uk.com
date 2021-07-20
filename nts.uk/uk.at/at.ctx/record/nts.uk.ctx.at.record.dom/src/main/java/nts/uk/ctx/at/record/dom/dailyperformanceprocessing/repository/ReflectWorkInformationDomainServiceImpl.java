package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemService;

@Stateless
public class ReflectWorkInformationDomainServiceImpl implements ReflectWorkInformationDomainService{
	@Inject
	private WorkingConditionItemService workingConditionItemService;

	public boolean changeWorkInformation(WorkInfoOfDailyAttendance workInfo, String companyId, String employeeId,
			GeneralDate ymd) {
		// ドメインモデル「日別実績の勤務情報．勤務情報」を取得する (Lấy dữ liệu)
		WorkInformation recordWorkInformation = workInfo.getRecordInfo();
		//休日出勤時の勤務情報を取得する (Lấy 休日出勤時の勤務情報)
		Optional<WorkInformation> singleDaySchedule = workingConditionItemService.getHolidayWorkSchedule(companyId,
				employeeId, ymd, recordWorkInformation.getWorkTypeCode().v());
		if (!singleDaySchedule.isPresent()) {
			return false;
		}
		workInfo.setRecordInfo(singleDaySchedule.get());
		return true;
	}

}
