package nts.uk.ctx.at.record.dom.divergencetime.service.attendancetype;

import java.util.List;

public interface AttendanceTypeDivergenceSevice {
	public List<AttendanceTypeDivergenceServiceDto> getItemByScreenUseAtr(String companyId, int screenUseAtr);
}
