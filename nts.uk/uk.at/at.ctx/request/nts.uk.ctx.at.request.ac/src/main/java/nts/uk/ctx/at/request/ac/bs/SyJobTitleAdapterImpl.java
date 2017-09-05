/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.ac.bs;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.JobTitleImport;
import nts.uk.ctx.bs.employee.pub.employee.jobtitle.JobTitleExport;
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
	public JobTitleImport findJobTitleBySid(String employeeId, GeneralDate baseDate) {
		Optional<JobTitleExport> export = this.syJobTitlePub.findJobTitleBySid(employeeId, baseDate);
		return export.map(x -> this.toImport(x)).orElse(null);
	}

	@Override
	public JobTitleImport findJobTitleByPositionId(String companyId, String positionId, GeneralDate baseDate) {
		return this.syJobTitlePub.findJobTitleByPositionId(companyId, positionId, baseDate)
				.map(x-> this.toImport(x)).orElse(null);
	}

	
	/**
	 * convert Export to Import data
	 * @param ex
	 * @return
	 */
	private JobTitleImport toImport(JobTitleExport ex) {
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
