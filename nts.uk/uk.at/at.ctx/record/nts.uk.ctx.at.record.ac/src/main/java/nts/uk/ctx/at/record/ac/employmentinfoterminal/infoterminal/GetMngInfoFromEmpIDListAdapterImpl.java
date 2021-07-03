package nts.uk.ctx.at.record.ac.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpDataImport;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.GetMngInfoFromEmpIDListAdapter;
import nts.uk.ctx.bs.employee.pub.employee.EmpDataExport;
import nts.uk.ctx.bs.employee.pub.employee.GetMngInfoFromEmpIDListPub;

/**
 * 
 * @author xuannt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetMngInfoFromEmpIDListAdapterImpl implements GetMngInfoFromEmpIDListAdapter {

	@Inject
	private GetMngInfoFromEmpIDListPub pub;

	@Override
	public List<EmpDataImport> getEmpData(List<String> empIDList) {
		return this.pub.getEmpData(empIDList).stream()
											 .map(e -> convertToImport(e))
											 .collect(Collectors.toList());
	}

	private EmpDataImport convertToImport(EmpDataExport data) {
		return new EmpDataImport(data.getCompanyId(),
								 data.getPersonId(),
								 data.getEmployeeId(),
								 data.getEmployeeCode(),
								 data.getExternalCode());
	}
}
