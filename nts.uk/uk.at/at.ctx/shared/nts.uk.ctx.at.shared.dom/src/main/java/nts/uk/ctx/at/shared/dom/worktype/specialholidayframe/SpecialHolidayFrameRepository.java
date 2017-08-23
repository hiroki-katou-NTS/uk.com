package nts.uk.ctx.at.shared.dom.worktype.specialholidayframe;

import java.util.List;

public interface SpecialHolidayFrameRepository {
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<SpecialHolidayFrame> findSpecialHolidayFrame(String companyId);
}
