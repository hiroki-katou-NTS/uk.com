package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import java.util.List;
import java.util.Optional;
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
	 * @param fixedItemAtr
	 * @return
	 */
	List<VerticalTime> findAllVerticalTime(String companyId, int fixedItemAtr);
	
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
	 * @param fixedItemAtr
	 */
	void deleteVerticalTime(String companyId, int fixedItemAtr);
	
	/**
	 * Find Vertical Time
	 * @param companyId
	 * @param fixedItemAtr
	 * @return
	 */
	Optional<FixedVertical> find(String companyId, int fixedItemAtr);

	/**
	 * Delete Vertical Count CNT
	 * @param companyId
	 * @param fixedItemAtr
	 */
	void deleteCount(String companyId, int fixedItemAtr);

	/**
	 * 
	 * @param verticalCnt
	 */
	void addVerticalCnt(VerticalCnt verticalCnt);

	List<VerticalCnt> findAllCnt(String companyId, int fixedItemAtr);

	

}
