package nts.uk.ctx.at.function.dom.adapter.annualworkschedule;

import java.util.List;

public interface EmployeeInformationAdapter {
	
	List<EmployeeInformationImport> getEmployeeInfo(EmployeeInformationQueryDtoImport param);

}
