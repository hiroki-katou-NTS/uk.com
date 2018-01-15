package nts.uk.ctx.bs.employee.infra.repository.jobtitle;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleGetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.JobTitleHistory;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobHist;

/**
 * The Class JpaJobTitleGetMemento.
 */
public class JpaJobTitleGetMemento implements JobTitleGetMemento {

	/** The list job title hist. */
	private List<BsymtJobHist> listJobTitleHistory;

	/** The Constant ELEMENT_FIRST. */
	private static final Integer ELEMENT_FIRST = 0;

	/**
	 * Instantiates a new jpa job title get memento.
	 *
	 * @param listJobTitleHistory
	 *            the list job title history
	 */
	public JpaJobTitleGetMemento(List<BsymtJobHist> listJobTitleHistory) {
		this.listJobTitleHistory = listJobTitleHistory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.listJobTitleHistory.get(ELEMENT_FIRST).getBsymtJobHistPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleGetMemento#getJobTitleId()
	 */
	@Override
	public String getJobTitleId() {
		return this.listJobTitleHistory.get(ELEMENT_FIRST).getBsymtJobHistPK().getJobId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleGetMemento#getJobTitleHistory
	 * ()
	 */
	@Override
	public List<JobTitleHistory> getJobTitleHistory() {
		return this.listJobTitleHistory.stream()
				.map(item -> new JobTitleHistory(new JpaJobTitleHistoryGetMemento(item)))
				.collect(Collectors.toList());
	}

}
