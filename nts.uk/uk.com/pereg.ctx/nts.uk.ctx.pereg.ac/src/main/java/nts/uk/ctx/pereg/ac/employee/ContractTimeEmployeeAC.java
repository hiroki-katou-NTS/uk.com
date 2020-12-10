/**
 *
 */
package nts.uk.ctx.pereg.ac.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.pub.employee.ContractTimeEmployeePub;
import nts.uk.ctx.pereg.dom.adapter.ContractTimeEmployeeAdapter;
import nts.uk.ctx.pereg.dom.adapter.ContractTimeEmployeeImport;

/**
 * @author hieult
 *
 */
@Stateless
public class ContractTimeEmployeeAC implements ContractTimeEmployeeAdapter {

	@Inject
	private ContractTimeEmployeePub contractTimeEmployeePub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pereg.dom.adapter.ContractTimeEmployeeAdapter#getData(java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<ContractTimeEmployeeImport> getData(List<String> listEmpID, GeneralDate baseDate) {
		List<ContractTimeEmployeeImport> listEx = contractTimeEmployeePub.getData(listEmpID, baseDate).stream()
				.map(c -> new ContractTimeEmployeeImport(c.getEmployeeID(), c.getContractTime())).collect(Collectors.toList());
		return listEx;
	}
}