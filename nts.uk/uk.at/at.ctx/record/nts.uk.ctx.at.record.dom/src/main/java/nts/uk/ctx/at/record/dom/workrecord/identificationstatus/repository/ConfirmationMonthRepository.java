package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.shared.dom.common.Day;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public interface ConfirmationMonthRepository {

	Optional<ConfirmationMonth> findByKey(String companyID, String employeeID, ClosureId closureId, Day closureDay, YearMonth processYM);

	void insert(ConfirmationMonth confirmationMonth);
	
	void delete(String companyId, String employeeId, int closureId, int closureDay, int processYM);
	
	List<ConfirmationMonth> findBySidAndYM(String employeeId, int processYM);
	
	List<ConfirmationMonth> findBySomeProperty(List<String> employeeIds, int processYM, int closureDate, int closureId);
	
}
