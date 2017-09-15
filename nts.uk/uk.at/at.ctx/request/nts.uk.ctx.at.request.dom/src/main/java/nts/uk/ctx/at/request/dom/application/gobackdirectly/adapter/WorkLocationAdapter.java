package nts.uk.ctx.at.request.dom.application.gobackdirectly.adapter;

import nts.uk.ctx.at.request.dom.application.gobackdirectly.adapter.dto.WorkLocationImport;

public interface WorkLocationAdapter {
	/**
	 * get Work Location Name
	 * @param companyID
	 * @param workLocationCD
	 * @return
	 */
	WorkLocationImport getByWorkLocationCD(String companyID,String workLocationCD);
	
}
