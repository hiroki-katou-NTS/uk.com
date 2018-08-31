package nts.uk.ctx.at.request.dom.application.common.service.other;

import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;

/**
 * 17.３６時間の表示
 * @author Doan Duy Hung
 *
 */
public interface AgreementTimeService {
	
	public AgreeOverTimeOutput getAgreementTime(String companyID, String employeeID);
	
}
