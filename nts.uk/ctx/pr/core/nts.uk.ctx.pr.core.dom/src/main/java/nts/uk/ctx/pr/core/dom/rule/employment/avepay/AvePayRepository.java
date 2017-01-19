package nts.uk.ctx.pr.core.dom.rule.employment.avepay;

import java.util.List;
import java.util.Optional;

public interface AvePayRepository {
	/**
	 * 
	 */
	
	void add(String companyCode, AvePay avePay);
	
	//Optional<AvePay> find(String companyCode);
	
	List<AvePay> findAll();
	
	void update(String companyCode, AvePay avePay);
	
	void remove(String companyCode, AvePay avePay);
	
}
