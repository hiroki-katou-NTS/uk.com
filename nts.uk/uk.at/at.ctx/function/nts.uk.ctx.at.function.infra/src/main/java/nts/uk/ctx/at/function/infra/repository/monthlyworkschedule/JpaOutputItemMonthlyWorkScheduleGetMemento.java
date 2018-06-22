package nts.uk.ctx.at.function.infra.repository.monthlyworkschedule;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyAttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingCode;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingName;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleGetMemento;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.PrintSettingRemarksColumn;

public class JpaOutputItemMonthlyWorkScheduleGetMemento implements OutputItemMonthlyWorkScheduleGetMemento{

	@Override
	public String getCompanyID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonthlyOutputItemSettingCode getItemCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonthlyOutputItemSettingName getItemName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonthlyAttendanceItemsDisplay> getLstDisplayedAttendance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintSettingRemarksColumn getPrintSettingRemarksColumn() {
		// TODO Auto-generated method stub
		return null;
	}


}
