package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.Optional;

public interface VerticalSettingRepository {
	/**
	 * Find all Vertical Calculator Setting
	 * @param companyId
	 * @return
	 */
	List<VerticalCalSet> findAllVerticalCalSet(String companyId);
	
	/**
	 * Get Vertical Calculator Setting by Code
	 * @param companyId
	 * @param verticalCalCd
	 * @return
	 */
	Optional<VerticalCalSet> getVerticalCalSetByCode(String companyId, String verticalCalCd);
	
	/**
	 * Add Vertical Calculator Setting  
	 * @param verticalCalSet
	 */
	void addVerticalCalSet(VerticalCalSet verticalCalSet);
	
	/**
	 * Update Vertical Calculator Setting  
	 * @param verticalCalSet
	 */
	void updateVerticalCalSet(VerticalCalSet verticalCalSet);
	
	/**
	 * Delete Vertical Calculator Setting  
	 * @param companyId
	 * @param verticalCalCd
	 */
	void deleteVerticalCalSet(String companyId, String verticalCalCd);
}
