package nts.uk.ctx.at.request.dom.adapter.employee;

import java.util.List;

public interface GetMngInfoAdapter {

	List<RQEmpDataImport> getEmpData(List<String> empIDList);

}
