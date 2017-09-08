package nts.uk.ctx.at.schedule.dom.budget.premium.service;

import java.util.List;


public interface AttendanceTypePrimiumSevice {
	public List<AttendanceTypePriServiceDto> getItemByScreenUseAtr(String companyId, int screenUseAtr);
}
