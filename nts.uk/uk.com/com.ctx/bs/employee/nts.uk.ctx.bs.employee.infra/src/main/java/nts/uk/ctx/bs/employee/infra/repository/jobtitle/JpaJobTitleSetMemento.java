package nts.uk.ctx.bs.employee.infra.repository.jobtitle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleId;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleSetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.JobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.Period;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobHist;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobHistPK;

/**
 * The Class JpaJobTitleSetMemento.
 */
public class JpaJobTitleSetMemento implements JobTitleSetMemento {

	 /** The list entity. */
    private List<BsymtJobHist> listEntity;
    
    /**
     * Instantiates a new jpa job title set memento.
     *
     * @param listEntity the list entity
     */
    public JpaJobTitleSetMemento(List<BsymtJobHist> listEntity) {
    	listEntity.forEach(entity -> {
            if (entity != null && entity.getBsymtJobHistPK() == null) {
                entity.setBsymtJobHistPK(new BsymtJobHistPK());
            }
        });
        this.listEntity = listEntity;
    }
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.listEntity.forEach(entity -> {
            entity.getBsymtJobHistPK().setCid(companyId.v());
        });
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleSetMemento#setJobTitleId(nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleId)
	 */
	@Override
	public void setJobTitleId(JobTitleId jobTitleId) {
		this.listEntity.forEach(entity -> {
            entity.getBsymtJobHistPK().setJobId(jobTitleId.v());
        });
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleSetMemento#setJobTitleHistory(java.util.List)
	 */
	@Override
	public void setJobTitleHistory(List<JobTitleHistory> jobTitleHistory) {
		// convert list workplace history to map by key historyId
        Map<String, Period> mapJobHist = jobTitleHistory.stream()
        		.collect(Collectors.toMap(
        				item -> ((JobTitleHistory) item).getHistoryId().v(), 
        				item -> ((JobTitleHistory) item).getPeriod()));

        // set period
        this.listEntity.forEach(entity -> {
            Period period = mapJobHist.get(entity.getBsymtJobHistPK().getHistId());
            entity.setStartDate(period.getStartDate());
            entity.setEndDate(period.getEndDate());
        });
	}

}
