package nts.uk.ctx.pr.core.dom.retirement.payment;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface RetirementPaymentRepository {
	
	void add(RetirementPayment retirementPayment);

	Optional<RetirementPayment> findByCompanyCode(String companyCode, String personId, GeneralDate dateTime);
	
	void update(RetirementPayment retirementPayment);
	
	void remove(RetirementPayment retirementPayment);
}
