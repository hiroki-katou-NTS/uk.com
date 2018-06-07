package nts.uk.ctx.at.request.dom.application.common.adapter.bs;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;

public interface AtEmployeeAdapter {

	List<String> getListSid(String sId , GeneralDate baseDate);
	List<EmployeeInfoImport> getByListSID(List<String> sIds);
}
