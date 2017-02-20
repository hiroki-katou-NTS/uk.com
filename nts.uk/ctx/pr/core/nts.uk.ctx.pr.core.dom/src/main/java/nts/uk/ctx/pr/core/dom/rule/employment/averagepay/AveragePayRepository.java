package nts.uk.ctx.pr.core.dom.rule.employment.averagepay;

import java.util.Optional;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AveragePayRepository {
	/**
	 * 
	 */
	
	void register(AveragePay averagePay);
	
	Optional<AveragePay> findByCompanyCode(String companyCode);
	
	void update(AveragePay averagePay);
	
}
