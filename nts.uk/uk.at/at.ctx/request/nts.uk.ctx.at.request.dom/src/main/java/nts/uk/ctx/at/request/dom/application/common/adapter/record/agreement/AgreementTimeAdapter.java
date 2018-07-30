package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AgreementTimeAdapter {
	
	public List<AgreementTimeImport> getAgreementTime(String companyId, List<String> employeeIds, YearMonth yearMonth, ClosureId closureId);
	
}
