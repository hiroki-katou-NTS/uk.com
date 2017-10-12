package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import java.util.List;
/**
 * 
 * @author phongtq
 *
 */
public interface FixedVerticalSettingRepository {

	/**
	 * Find all Fixed Vertical
	 * @param companyId
	 * @return
	 */
	List<FixedVertical> findAll(String companyId);
	
	/**
	 * Add new Fixed Vertical
	 * @param fixedVertical
	 */
	void addFixedVertical(FixedVertical fixedVertical);
	
	/**
	 * Update Fixed Vertical
	 * @param fixedVertical
	 */
	void updateFixedVertical(FixedVertical fixedVertical);
	
	/**
	 * Find all Vertical Time
	 * @param companyId
	 * @param fixedVerticalNo
	 * @return
	 */
	List<VerticalTime> findAllVerticalTime(String companyId, int fixedVerticalNo);
	
	/**
	 * Add new Vertical Time
	 * @param verticalTime
	 */
	void addVerticalTime(VerticalTime verticalTime);
	
	/**
	 * Update Vertical Time
	 * @param verticalTime
	 */
	void updateVerticalTime(VerticalTime verticalTime);
	
	/**
	 * Delete Vertical Time
	 * @param companyId
	 * @param fixedVerticalNo
	 */
	void deleteVerticalTime(String companyId, int fixedVerticalNo);
}
