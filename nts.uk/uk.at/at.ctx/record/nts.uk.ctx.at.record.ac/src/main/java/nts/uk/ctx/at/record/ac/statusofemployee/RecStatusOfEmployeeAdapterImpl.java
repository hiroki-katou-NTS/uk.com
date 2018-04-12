package nts.uk.ctx.at.record.ac.statusofemployee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.statusofemployee.RecStatusOfEmployeeAdapter;
import nts.uk.ctx.at.record.dom.adapter.statusofemployee.RecStatusOfEmployeeImport;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmploymentExport;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmploymentPub;

@Stateless
public class RecStatusOfEmployeeAdapterImpl implements RecStatusOfEmployeeAdapter {

	@Inject
	private StatusOfEmploymentPub statusOfEmploymentPub;

	@Override
	public RecStatusOfEmployeeImport getStatusOfEmployeeService(String employeeId, GeneralDate day) {

		StatusOfEmploymentExport statusOfEmploymentExport = this.statusOfEmploymentPub.getStatusOfEmployment(employeeId,
				day);

		if (statusOfEmploymentExport == null) {
			return null;
		}
		RecStatusOfEmployeeImport recStatusOfEmployeeImport = new RecStatusOfEmployeeImport(
				statusOfEmploymentExport.getEmployeeId(), statusOfEmploymentExport.getRefereneDate(),
				statusOfEmploymentExport.getStatusOfEmployment(), statusOfEmploymentExport.getTempAbsenceFrNo());
		return recStatusOfEmployeeImport;
	}

}
