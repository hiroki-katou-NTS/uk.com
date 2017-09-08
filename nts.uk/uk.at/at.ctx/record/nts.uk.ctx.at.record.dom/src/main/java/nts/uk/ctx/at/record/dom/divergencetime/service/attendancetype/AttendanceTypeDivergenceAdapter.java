package nts.uk.ctx.at.record.dom.divergencetime.service.attendancetype;

import java.util.List;

public interface AttendanceTypeDivergenceAdapter {
	public List<AttendanceTypeDivergenceAdapterDto> getItemByScreenUseAtr(String companyId, int screenUseAtr);
}
