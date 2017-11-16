package nts.uk.ctx.at.shared.dom.worktype.specialholidayframe;

import java.util.List;
import java.util.Optional;

public interface SpecialHolidayFrameRepository {
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<SpecialHolidayFrame> findSpecialHolidayFrame(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<SpecialHolidayFrame> findAll(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @param frameNo
	 * @return
	 */
	Optional<SpecialHolidayFrame> findHolidayFrameByCode(String companyId, int frameNo);
	
	/**
	 * Update the Special Holiday Frame.
	 *
	 * @param specialHolidayFrame the Special Holiday Frame
	 */
	void update(SpecialHolidayFrame specialHolidayFrame);
}
