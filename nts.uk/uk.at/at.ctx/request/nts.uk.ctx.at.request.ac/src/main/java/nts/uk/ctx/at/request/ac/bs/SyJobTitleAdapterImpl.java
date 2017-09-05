/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.ac.bs;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.JobTitleImport;
import nts.uk.ctx.bs.employee.pub.employee.jobtitle.PubJobTitleDto;
import nts.uk.ctx.bs.employee.pub.employee.jobtitle.SyJobTitlePub;

/**
 * @author vunv
 */
public class SyJobTitleAdapterImpl implements SyJobTitleAdapter{

	@Inject
	private SyJobTitlePub syJobTitlePub;
	
	
	@Override
	public List<JobTitleImport> findJobTitleBySid(String employeeId) {
		return this.syJobTitlePub.findJobTitleBySid(employeeId).stream().map(x-> this.toImport(x)).collect(Collectors.toList());
	}

	@Override
	public List<JobTitleImport> findJobTitleBySid(String employeeId, GeneralDate baseDate) {
		return this.syJobTitlePub.findJobTitleBySid(employeeId, baseDate).stream()
				.map(x-> this.toImport(x)).collect(Collectors.toList());
	}

	@Override
	public List<JobTitleImport> findJobTitleByPositionId(String companyId, String positionId, GeneralDate baseDate) {
		return this.syJobTitlePub.findJobTitleByPositionId(companyId, positionId, baseDate).stream()
				.map(x-> this.toImport(x)).collect(Collectors.toList());
	}

	
	/**
	 * convert Export to Import data
	 * @param ex
	 * @return
	 */
	private JobTitleImport toImport(PubJobTitleDto ex) {
		return new JobTitleImport(
				ex.getCompanyId(), 
				ex.getPositionId(), 
				ex.getPositionCode(), 
				ex.getPositionName(), 
				ex.getSequenceCode(), 
				ex.getStartDate(), 
				ex.getEndDate());
	}
}
