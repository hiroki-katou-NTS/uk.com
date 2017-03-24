package nts.uk.ctx.basic.dom.system.bank.linebank;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author sonnh1
 *
 */
public interface LineBankRepository {
	/**
	 * find all lineBank base on companyCode
	 * 
	 * @param companyCode
	 * @param lineBankCode
	 * @return List line bank
	 */
	List<LineBank> findAll(String companyCode);

	/**
	 * find one lineBank base on companyCode and lineBankCode
	 * 
	 * @param companyCode
	 * @param lineBankCode
	 * @return a line bank
	 */
	Optional<LineBank> find(String companyCode, String lineBankCode);

	/**
	 * addition lineBank
	 * 
	 * @param lineBank
	 */
	void add(LineBank lineBank);

	/**
	 * update lineBank
	 * 
	 * @param lineBank
	 */
	void update(LineBank lineBank);

	/**
	 * remove lineBank base on companyCode and lineBankCode
	 * 
	 * @param companyCode
	 * @param lineBankCode
	 */
	void remove(String companyCode, String lineBankCode);

}
