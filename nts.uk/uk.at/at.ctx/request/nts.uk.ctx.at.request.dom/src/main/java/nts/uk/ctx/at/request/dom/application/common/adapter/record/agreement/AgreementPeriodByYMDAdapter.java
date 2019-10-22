package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public interface AgreementPeriodByYMDAdapter {
	
	public AgreePeriodYMDExport getAgreementPeriod(String companyID, GeneralDate ymd, ClosureId closureID);
	
}
