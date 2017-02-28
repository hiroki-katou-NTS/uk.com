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



	boolean isExisted(String companyCode, PayClassificationCode payClassificationCode);



	List<PayClassification> findAll(String companyCode);



	Optional<PayClassification> findSinglePayClassification(String companyCode,
			PayClassificationCode payClassificationCode);

	void remove(String companyCode, PayClassificationCode payClassificationCode);
}
