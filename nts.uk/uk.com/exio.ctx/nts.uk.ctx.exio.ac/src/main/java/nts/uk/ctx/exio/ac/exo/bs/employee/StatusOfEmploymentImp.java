package nts.uk.ctx.exio.ac.exo.bs.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmploymentExport;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmploymentPub;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.StatusOfEmploymentAdapter;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.StatusOfEmploymentResult;

@Stateless
public class StatusOfEmploymentImp implements StatusOfEmploymentAdapter{

	@Inject
	private StatusOfEmploymentPub employmentStatusPub;
	
	@Override
	public Optional<StatusOfEmploymentResult> getStatusOfEmployment(String employeeId, GeneralDate referenceDate) {
		StatusOfEmploymentExport employmentExport = employmentStatusPub.getStatusOfEmployment(employeeId, referenceDate);
		return (employmentExport == null) ? Optional.empty() : Optional.of(new StatusOfEmploymentResult(employmentExport.getEmployeeId(), 
				employmentExport.getRefereneDate(), employmentExport.getStatusOfEmployment(), employmentExport.getTempAbsenceFrNo()));
	}

}
