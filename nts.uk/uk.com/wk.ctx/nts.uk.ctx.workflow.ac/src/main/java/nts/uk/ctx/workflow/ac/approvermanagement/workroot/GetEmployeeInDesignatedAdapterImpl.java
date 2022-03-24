package nts.uk.ctx.workflow.ac.approvermanagement.workroot;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.employeeindesignated.EmpInDesignatedPub;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.adapter.EmployeeInDesignatedImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.adapter.GetEmployeeInDesignatedAdapter;

@Stateless
public class GetEmployeeInDesignatedAdapterImpl implements GetEmployeeInDesignatedAdapter {

	@Inject
	private EmpInDesignatedPub empInDesignatedPub;

	@Override
	public List<EmployeeInDesignatedImport> findEmployees(List<String> workplaceIds, GeneralDate baseDate,
			List<Integer> empStatus) {
		return workplaceIds.stream()
				.map(workplaceId -> this.empInDesignatedPub.getEmpInDesignated(workplaceId, baseDate, empStatus))
				.flatMap(List::stream)
				.map(data -> new EmployeeInDesignatedImport(data.getEmployeeId(), data.getStatusOfEmp()))
				.collect(Collectors.toList());
	}
}
