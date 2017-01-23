package nts.uk.ctx.pr.core.dom.rule.employment.averagepay;

import java.util.List;
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
	
	void add(AveragePay averagePay);
	
	//Optional<AvePay> find(String companyCode);
	
	List<AveragePay> findAll();
	
	void update(AveragePay averagePay);

	void remove(AveragePay averagePay);
	
}
