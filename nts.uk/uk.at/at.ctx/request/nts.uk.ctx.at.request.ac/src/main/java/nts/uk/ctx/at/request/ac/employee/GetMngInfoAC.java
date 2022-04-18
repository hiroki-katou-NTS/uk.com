package nts.uk.ctx.at.request.ac.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.adapter.employee.GetMngInfoAdapter;
import nts.uk.ctx.at.request.dom.adapter.employee.RQEmpDataImport;
import nts.uk.ctx.bs.employee.pub.employee.GetMngInfoFromEmpIDListPub;

@Stateless
public class GetMngInfoAC implements GetMngInfoAdapter {

	@Inject
	private GetMngInfoFromEmpIDListPub getMngInfoFromEmpIDListPub;

	@Override
	public List<RQEmpDataImport> getEmpData(List<String> empIDList) {
		return getMngInfoFromEmpIDListPub.getEmpData(empIDList).stream().map(x -> new RQEmpDataImport(x.getCompanyId(),
				x.getPersonId(), x.getEmployeeId(), x.getEmployeeCode(), x.getExternalCode()))
				.collect(Collectors.toList());
	}

}
