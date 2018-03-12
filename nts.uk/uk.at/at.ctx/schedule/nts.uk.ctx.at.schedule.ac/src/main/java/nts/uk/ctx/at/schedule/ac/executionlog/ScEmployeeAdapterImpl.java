package nts.uk.ctx.at.schedule.ac.executionlog;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;

/**
 * The Class SCEmployeeAdapterImpl.
 */
@Stateless
public class ScEmployeeAdapterImpl implements SCEmployeeAdapter {

	/** The pub. */
	@Inject
	private IPersonInfoPub pub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter#findByEmployeeId(java.lang.String)
	 */
	@Override
	public EmployeeDto findByEmployeeId(String sid) {
		PersonInfoExport per = pub.getPersonInfo(sid);
		// covert to dto
		EmployeeDto dto = new EmployeeDto();
		dto.setEmployeeCode(per.getEmployeeCode());
		dto.setEmployeeName(per.getBusinessName());
		return dto;
	}
}
