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
	 * 
	 * @param companyCode
	 * @param lineBankCode
	 * @return List line bank
	 */
	List<LineBank>findAll(String companyCode);
	/**
	 * 
	 * @param companyCode
	 * @param lineBankCode
	 * @return list a line bank
	 */
	Optional<LineBank> find(String companyCode, String lineBankCode);
	
	/**
	 * 
	 * @param lineBank
	 */
	void add(LineBank lineBank);
	/**
	 * 
	 * @param lineBank
	 */
	void update(LineBank lineBank);
	/**
	 * 
	 * @param companyCode
	 * @param lineBankCode
	 */
	void remove(String companyCode, String lineBankCode);
	
}
