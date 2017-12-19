/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.employee;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoDtoImport;

@Stateless
public class EmployeeAdapterImpliment implements EmployeeInfoAdapter {

	@Inject
	private EmployeeInfoPub employeeInfoPub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoAdapter#getEmployeesAtWorkByBaseDate(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<EmployeeInfoDtoImport> getEmployeesAtWorkByBaseDate(String companyId, GeneralDate baseDate) {

//		return this.employeeInfoPub.getEmployeesAtWorkByBaseDate(companyId, baseDate).stream()
//				.map(f -> {
//					return new EmployeeInfoDtoImport(f.getCompanyId(), f.getEmployeeCode(), f.getEmployeeId(), f.getPersonId());
//				})
//				.filter(EmployeeInfoDtoImport.distinctByKey(EmployeeInfoDtoImport::getEmployeeId))
//				.collect(Collectors.toList());
		
		
		//Mock data
		List<EmployeeInfoDtoImport> listEmployeeInfoDtoImport = new ArrayList<>();
		
		EmployeeInfoDtoImport employeeInfoDtoImport1 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD01",
				"EMID01", "00001");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport1);
		
		EmployeeInfoDtoImport employeeInfoDtoImport2 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD02",
				"EMID02", "00002");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport2);
		
		EmployeeInfoDtoImport employeeInfoDtoImport3 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD03",
				"EMID03", "00003");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport3);
		
		EmployeeInfoDtoImport employeeInfoDtoImport4 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD04",
				"EMID04", "00004");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport4);
		
		EmployeeInfoDtoImport employeeInfoDtoImport5 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD05",
				"EMID05", "00005");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport5);
		
		EmployeeInfoDtoImport employeeInfoDtoImport6 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD06",
				"EMID06", "00006");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport6);
		
		EmployeeInfoDtoImport employeeInfoDtoImport7 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD07",
				"EMID07", "00007");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport7);
		
		EmployeeInfoDtoImport employeeInfoDtoImport8 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD08",
				"EMID08", "00008");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport8);
		
		EmployeeInfoDtoImport employeeInfoDtoImport9 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD09",
				"EMID09", "00009");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport9);
		
		EmployeeInfoDtoImport employeeInfoDtoImport10 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD10",
				"EMID10", "00010");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport10);
		
		EmployeeInfoDtoImport employeeInfoDtoImport11 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD11",
				"EMID11", "00011");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport11);
		
		EmployeeInfoDtoImport employeeInfoDtoImport12 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD12",
				"EMID12", "00012");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport12);
		
		EmployeeInfoDtoImport employeeInfoDtoImport13 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD13",
				"EMID13", "00013");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport13);
		
		EmployeeInfoDtoImport employeeInfoDtoImport14 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD14",
				"EMID14", "00014");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport14);
		
		EmployeeInfoDtoImport employeeInfoDtoImport15 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD15",
				"EMID15", "00015");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport15);
		
		EmployeeInfoDtoImport employeeInfoDtoImport16 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD16",
				"EMID16", "00016");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport16);
		
		EmployeeInfoDtoImport employeeInfoDtoImport17 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD17",
				"EMID17", "00017");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport17);
		
		EmployeeInfoDtoImport employeeInfoDtoImport18 = new EmployeeInfoDtoImport("000000000000-0001", "EMCOD18",
				"EMID18", "00018");
		listEmployeeInfoDtoImport.add(employeeInfoDtoImport18);
		
		return listEmployeeInfoDtoImport;
	}
}
