package nts.uk.ctx.basic.dom.organization.payclassification;

import java.util.List;
import java.util.Optional;


public interface PayClassificationRepository {

	/**
	 * 
	 * 
	 * @param companyCode
	 * @return
	 */
	List<PayClassification> getPayClassifications(String companyCode);



	/**
	 * get Item Master
	 * 
	 * @param companyCode
	 * @param payClassificationCode
	 * @return list PayClassification
	 */
	Optional<PayClassification> getPayClassification(String companyCode, String payClassificationCode);
	


	void add(PayClassification payClassification);

	void update(PayClassification payClassification);



	void remove(String companyCode);
}
