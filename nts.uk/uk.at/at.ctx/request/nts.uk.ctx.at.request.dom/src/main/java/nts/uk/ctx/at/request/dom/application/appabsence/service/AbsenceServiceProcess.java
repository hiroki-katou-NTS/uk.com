package nts.uk.ctx.at.request.dom.application.appabsence.service;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;

public interface AbsenceServiceProcess {
	/**
	 * @param workTypeCode
	 * @return
	 */
	public SpecialLeaveInfor getSpecialLeaveInfor(String workTypeCode);
	
	void CreateAbsence(AppAbsence domain, Application_New newApp);
}
