package nts.uk.ctx.at.request.dom.application.gobackdirectly.adapter;

import nts.uk.ctx.at.request.dom.application.gobackdirectly.adapter.dto.WorkLocationImport;

public interface WorkLocationAdapter {
	WorkLocationImport getByWorkLocationCD(String companyID,String workLocationCD);
}
