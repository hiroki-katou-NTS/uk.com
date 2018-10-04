package nts.uk.ctx.at.schedule.ac.employment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.employment.EmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.employment.ScEmploymentAdapter;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

/**
 * The Class SCEmployeeAdapterImpl.
 */
@Stateless
public class ScEmploymentAdapterImpl implements ScEmploymentAdapter {

	/** The emp pub. */
	@Inject
	private SyEmploymentPub empPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.adapter.employment.ScEmploymentAdapter#
	 * getEmpHistBySid(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<EmploymentHistoryImported> getEmpHistBySid(String companyId, String employeeId,
			GeneralDate baseDate) {
		return this.empPub.findSEmpHistBySid(companyId, employeeId, baseDate)
				.map(f -> new EmploymentHistoryImported(f.getEmployeeId(), f.getEmploymentCode(), f.getPeriod()));
	}

	@Override
	public Map<String, String> getEmploymentMapCodeName(String companyId, List<String> empCodes) {
		return this.empPub.getEmploymentMapCodeName(companyId, empCodes);
	}
}
