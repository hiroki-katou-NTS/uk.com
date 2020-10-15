package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HoangNDH
 *
 */
public interface InsufficientFlexHolidayMntRepository {
	
	/**
	 * Find by company ID
	 * @param companyId
	 * @return
	 */
	List<InsufficientFlexHolidayMnt> findByCompanyId(String companyId);
	
	/**
	 * Add Insufficient Flex Holiday
	 * @param refreshInsuffFlex 
	 */
	void add(InsufficientFlexHolidayMnt refreshInsuffFlex);
	
	/**
	 * Update Insufficient Flex Holiday
	 * @param refreshInsuffFlex
	 */
	void update(InsufficientFlexHolidayMnt refreshInsuffFlex);
	
	/**
	 * Find by CID
	 * @param companyId
	 * @return
	 */
	Optional<InsufficientFlexHolidayMnt> findByCId(String cid);
}
