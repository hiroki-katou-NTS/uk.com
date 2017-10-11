package nts.uk.ctx.bs.employee.app.find.jobtitle.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleId;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleSetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.JobTitleHistory;

/**
 * Instantiates a new job title find dto.
 */
@Data
public class JobTitleFindDto implements JobTitleSetMemento {
	
	/** The company id. */
	public String companyId;

	/** The job title id. */
	public String jobTitleId;

	/** The job title history. */
	public List<JobTitleHistoryFindDto> jobTitleHistory;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleSetMemento#setCompanyId(nts.uk.ctx.bs.employee.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleSetMemento#setJobTitleId(nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleId)
	 */
	@Override
	public void setJobTitleId(JobTitleId jobTitleId) {
		this.jobTitleId = jobTitleId.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleSetMemento#setJobTitleHistory(java.util.List)
	 */
	@Override
	public void setJobTitleHistory(List<JobTitleHistory> jobTitleHistory) {
		this.jobTitleHistory = jobTitleHistory.stream().map(item -> {
			JobTitleHistoryFindDto dto = new JobTitleHistoryFindDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
