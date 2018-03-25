package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public interface ConfirmationMonthRepository {

	Optional<ConfirmationMonth> findByKey(String companyID, String employeeID, ClosureId closureId);

	void insert(ConfirmationMonth confirmationMonth);
	
	void delete(String companyId, String employeeId, int closureId);
}
