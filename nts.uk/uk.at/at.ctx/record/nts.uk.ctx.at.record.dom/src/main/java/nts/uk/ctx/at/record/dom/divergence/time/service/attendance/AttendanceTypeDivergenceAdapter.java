package nts.uk.ctx.at.record.dom.divergence.time.service.attendance;

import java.util.List;

public interface AttendanceTypeDivergenceAdapter {
	public List<AttendanceTypeDivergenceAdapterDto> getItemByScreenUseAtr(String companyId, int screenUseAtr);
}
