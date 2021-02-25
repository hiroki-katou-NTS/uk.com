package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author phongtq
 *
 */
public interface ZeroTimeRepository {

	/**
	 * Find by CompanyId
	 */
	List<ZeroTime> findByCompanyId(String companyId);

	/**
	 * Add Overday Calc
	 * 
	 * @param overdayCalc
	 */
	void add(ZeroTime overdayCalc);

	/**
	 * Update Overday Calc
	 * 
	 * @param overdayCalc
	 */
	void update(ZeroTime overdayCalc);

	/**
	 * Check by CID
	 * 
	 * @param companyId
	 * @return
	 */
	Optional<ZeroTime> findByCId(String companyId);

}
