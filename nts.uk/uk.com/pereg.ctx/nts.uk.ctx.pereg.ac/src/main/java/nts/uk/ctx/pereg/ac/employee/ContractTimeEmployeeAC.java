/**
 * 
 */
package nts.uk.ctx.pereg.ac.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.adapter.ContractTimeEmployeeAdapter;
import nts.uk.ctx.pereg.dom.adapter.ContractTimeEmployeeImport;

/**
 * @author hieult
 *
 */
@Stateless
public class ContractTimeEmployeeAC implements ContractTimeEmployeeAdapter {
//
//	@Inject
//	private ContractTimeEmployeePub contractTimeEmployeePub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pereg.dom.adapter.ContractTimeEmployeeAdapter#getData(java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<ContractTimeEmployeeImport> getData(List<String> listEmpID, GeneralDate baseDate) {
//		List<ContractTimeEmployeeExport> listEx = contractTimeEmployeePub.getData(listEmpID, baseDate);
//		List<ContractTimeEmployeeImport> listIm = listEx.stream().map(x ->this.toImport(x)).collect(Collectors.toList());
		return null;
	}
	private ContractTimeEmployeeImport toImport(){
		return null;
	}
}
