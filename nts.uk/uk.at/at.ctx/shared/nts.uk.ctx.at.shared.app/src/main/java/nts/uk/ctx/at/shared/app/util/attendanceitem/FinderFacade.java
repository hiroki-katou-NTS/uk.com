package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.arc.time.calendar.period.DatePeriod;

public abstract class FinderFacade implements ItemConst{

	public abstract <T extends ConvertibleAttendanceItem> T find(String employeeId, GeneralDate baseDate);

	public <T extends ConvertibleAttendanceItem> List<T> finds(String employeeId, GeneralDate baseDate) {
		return Collections.emptyList();
	}

	public <T extends ConvertibleAttendanceItem> List<T> find(List<String> employeeId, DatePeriod baseDate) {
		return Collections.emptyList();
	}
	
	public <T extends ConvertibleAttendanceItem> List<T> find(Map<String, List<GeneralDate>> param){
		return Collections.emptyList();
	}

	public FinderFacade getFinder(String layout) {
		return this;
	}
	
	public Object getDomain(String employeeId, GeneralDate baseDate) {
		return null;
	}
}
