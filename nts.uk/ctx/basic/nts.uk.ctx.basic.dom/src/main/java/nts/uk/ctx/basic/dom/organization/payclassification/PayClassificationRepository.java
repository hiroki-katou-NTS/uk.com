package nts.uk.ctx.basic.dom.organization.payclassification;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface PayClassificationRepository {

	/**
	 * 
	 * 
	 * @param companyCode
	 * @return
	 */
	List<PayClassification> findAll(String companyCode);

	/**
	 * get All Item Master   
	 * 
	 * @param companyCode
	 * @param payClassificationCode
	 * @param startDate
	 * @return list PayClassification
	 */
	List<PayClassification> findAllByPayClassificationCode(String companyCode, PayClassificationCode payClassificationCode ,GeneralDate startDate);

	/**
	 * get Item Master
	 * 
	 * @param companyCode
	 * @param payClassificationCode
	 * @param startDate
	 * @return list PayClassification
	 */
	Optional<PayClassification> getPosition(String companyCode, PayClassificationCode payClassificationCode ,GeneralDate startDate);
	
	/**
	 * Find item master
	 * @param companyCode 
	 * @param payClassificationCode 
	 * @param startDate 
	 * @return PayClassification
	 */
	Optional<PayClassification> find(String companyCode, PayClassificationCode payClassificationCode ,GeneralDate startDate);
}
