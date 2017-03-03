package nts.uk.ctx.pr.core.dom.retirement.payment;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.shr.com.primitive.PersonId;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface RetirementPaymentRepository {
	
	void add(RetirementPayment retirementPayment);
	
	List<RetirementPayment> findByCompanyCodeandPersonId(CompanyCode companyCode, PersonId personId);

	Optional<RetirementPayment> findRetirementPaymentInfo(CompanyCode companyCode, PersonId personId, GeneralDate dateTime);
	
	void update(RetirementPayment retirementPayment);
	
	void remove(RetirementPayment retirementPayment);
}
