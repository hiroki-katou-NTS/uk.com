package nts.uk.ctx.at.shared.ac.employment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSyEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSyEmploymentImport;
import nts.uk.ctx.bs.employee.pub.employment.SEmpHistExport;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

@Stateless
public class SharedSyEmploymentAdapterImpl implements SharedSyEmploymentAdapter {

	@Inject
	private SyEmploymentPub syEmploymentPub;

	@Override
	public Optional<SharedSyEmploymentImport> findByEmployeeId(String companyId, String employeeId, GeneralDate baseDate) {

		Optional<SEmpHistExport> empHist = this.syEmploymentPub.findSEmpHistBySid(companyId, employeeId, baseDate);
		
		if (!empHist.isPresent()) {
			return Optional.empty();
		}

		SharedSyEmploymentImport syEmploymentImport = new SharedSyEmploymentImport(empHist.get().getEmployeeId(),
				empHist.get().getEmploymentCode(), empHist.get().getEmploymentName(), empHist.get().getPeriod());

		return Optional.of(syEmploymentImport);
	}

}
