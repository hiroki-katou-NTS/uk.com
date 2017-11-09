/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.JobTitleHistory;

/**
 * The Class JobTitle.
 */
//職位
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobTitle extends AggregateRoot {
	
	/** The company id. */
	//会社ID
	private CompanyId companyId;

	/** The job title id. */
	//職位ID
	private String jobTitleId;
	
	/** The job title history. */
	//履歴
	private List<JobTitleHistory> jobTitleHistory;

	/**
	 * Instantiates a new job title.
	 *
	 * @param memento the memento
	 */
	public JobTitle(JobTitleGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.jobTitleId = memento.getJobTitleId();
		this.jobTitleHistory = memento.getJobTitleHistory()
				.stream()
				// sort by start date desc 
				.sorted((obj1, obj2) -> obj2.span().start().compareTo(obj1.span().start()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(JobTitleSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setJobTitleId(this.jobTitleId);
		memento.setJobTitleHistory(this.jobTitleHistory);
	}
	
	/**
	 * Gets the lastest history.
	 *
	 * @return the lastest history
	 */
	public JobTitleHistory getLastestHistory() {
		final int indexLastestHistory = 0;
        return this.jobTitleHistory.get(indexLastestHistory);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((jobTitleHistory == null) ? 0 : jobTitleHistory.hashCode());
		result = prime * result + ((jobTitleId == null) ? 0 : jobTitleId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobTitle other = (JobTitle) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (jobTitleHistory == null) {
			if (other.jobTitleHistory != null)
				return false;
		} else if (!jobTitleHistory.equals(other.jobTitleHistory))
			return false;
		if (jobTitleId == null) {
			if (other.jobTitleId != null)
				return false;
		} else if (!jobTitleId.equals(other.jobTitleId))
			return false;
		return true;
	}
		
}
