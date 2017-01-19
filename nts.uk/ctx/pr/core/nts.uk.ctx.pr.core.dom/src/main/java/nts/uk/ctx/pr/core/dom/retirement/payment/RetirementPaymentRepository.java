package nts.uk.ctx.pr.core.dom.retirement.payment;

import java.util.List;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface RetirementPaymentRepository {
	
	void add(RetirementPayment retirementPayment);

	List<RetirementPayment> findAll();
	
	void update(RetirementPayment retirementPayment);
	
	void remove(RetirementPayment retirementPayment);
}
