/**
 * 
 */
package nts.uk.screen.at.app.ksu001.orderemployee;

import java.util.Comparator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.ksu001.extracttargetemployees.EmployeeInformationDto;
import nts.uk.screen.at.app.ksu001.start.OrderEmployeeParam;

/**
 * @author laitv
 *
 */
@Stateless
public class GetDataAfterSortEmp {
	
	@Inject
	private SortEmployees sortEmployees;
	
	public  List<EmployeeInformationDto> getData(OrderEmployeeParam param) {
		List<EmployeeInformationDto> listEmpInfo = param.listEmpInfo;
		List<String> listSidOrder = sortEmployees.getListEmp(param);
		
		listEmpInfo.sort(Comparator.comparing( v -> listSidOrder.indexOf(v.employeeId)));
		
		return listEmpInfo;
	}

}
