package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemService;

@Stateless
public class ReflectWorkInformationDomainServiceImpl implements ReflectWorkInformationDomainService{
	@Inject
	private WorkingConditionItemService workingConditionItemService;

	public boolean changeWorkInformation(IntegrationOfDaily integrationOfDaily, String companyId, String employeeId,
			GeneralDate ymd) {
		// ドメインモデル「日別実績の勤務情報．勤務情報」を取得する (Lấy dữ liệu)
		WorkInformation recordWorkInformation = integrationOfDaily.getWorkInformation().getRecordInfo();
		//休日出勤時の勤務情報を取得する (Lấy 休日出勤時の勤務情報)
		Optional<WorkInformation> singleDaySchedule = workingConditionItemService.getHolidayWorkSchedule(companyId,
				employeeId, ymd, recordWorkInformation.getWorkTypeCode().v());
		if (!singleDaySchedule.isPresent()) {
			return false;
		}
		integrationOfDaily.getWorkInformation().setRecordInfo(singleDaySchedule.get());
		//日別勤怠の編集状態を追加する
		Optional<EditStateOfDailyAttd> editState28 = integrationOfDaily.getEditState().stream()
				.filter(c->c.getAttendanceItemId() == 28).findFirst();
		Optional<EditStateOfDailyAttd> editState29 = integrationOfDaily.getEditState().stream()
				.filter(c->c.getAttendanceItemId() == 29).findFirst();
		if(!editState28.isPresent()) {
			integrationOfDaily.getEditState().add(new EditStateOfDailyAttd(28, EditStateSetting.IMPRINT));
		}
		if(!editState29.isPresent()) {
			integrationOfDaily.getEditState().add(new EditStateOfDailyAttd(29, EditStateSetting.IMPRINT));
		}
		return true;
	}

}
