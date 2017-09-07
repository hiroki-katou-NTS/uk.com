package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateCom;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateSet;

public interface GrantRegularRepository {

	/**
	 * Find all Grant Regular
	 * 
	 * @param companyId
	 * @param specialHolidayCode
	 * @return
	 */
	List<GrantRegular> findAll(String companyId, String specialHolidayCode);

	List<GrantDateCom> findAllCom(String companyId, String specialHolidayCode);
	
	Optional<GrantDateCom> getComByCode(String companyId, String specialHolidayCode);
	
	List<GrantDateSet> getSetByCode(String companyId, String specialHolidayCode);

	void add(GrantDateCom grantDateCom);

}
