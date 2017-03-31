package nts.uk.ctx.pr.core.dom.rule.employment.unitprice.personal;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author sonnh
 *
 */
public interface PersonalUnitPriceRepository {
	/**
	 * 
	 * @param companyCode
	 * @param personalUnitPriceCode
	 * @return
	 */
	Optional<PersonalUnitPrice> find(String companyCode, String personalUnitPriceCode);

	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	List<PersonalUnitPrice> findAll(String companyCode);

	/**
	 * Find all personal unit price
	 * 
	 * @param companyCode
	 * @param unitPriceCodeList
	 * @return
	 */
	List<PersonalUnitPrice> findAll(String companyCode, List<String> unitPriceCodeList);

	/**
	 * Insert personal unit price
	 * 
	 * @param personalUnitPrice
	 */
	void add(PersonalUnitPrice personalUnitPrice);

	/**
	 * Update personal unit price
	 * 
	 * @param personalUnitPrice
	 */
	void update(PersonalUnitPrice personalUnitPrice);

	/**
	 * Remove personal unit price
	 * 
	 * @param companyCode
	 * @param code
	 */
	void remove(String companyCode, String code);

}
