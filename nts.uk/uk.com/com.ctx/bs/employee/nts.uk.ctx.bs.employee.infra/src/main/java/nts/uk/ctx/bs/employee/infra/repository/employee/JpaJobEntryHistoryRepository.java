package nts.uk.ctx.bs.employee.infra.repository.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistoryRepository;
import nts.uk.ctx.bs.employee.infra.entity.employee.jobentryhistory.BsymtJobEntryHistory;
import nts.uk.ctx.bs.employee.infra.entity.employee.jobentryhistory.BsymtJobEntryHistoryPk;

@Stateless
public class JpaJobEntryHistoryRepository extends JpaRepository implements JobEntryHistoryRepository{

	/**
	 * Convert from domain to entity
	 * @param domain
	 * @return
	 */
	private BsymtJobEntryHistory toEntity(JobEntryHistory domain){
		BsymtJobEntryHistoryPk key = new BsymtJobEntryHistoryPk(domain.getSId(), domain.getJoinDate());
		return new BsymtJobEntryHistory(key, domain.getCompanyId(), domain.getHiringType().v().toString(), domain.getRetirementDate(), domain.getAdoptDate());
	}
	
	/**
	 * Update entity
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(JobEntryHistory domain, BsymtJobEntryHistory entity){
		entity.companyId = domain.getCompanyId();
		entity.hiringType = domain.getHiringType().v().toString();
		entity.retireDate = domain.getRetirementDate();
		entity.adoptDate = domain.getAdoptDate();
	}
	@Override
	public void addJobEntryHistory(JobEntryHistory domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void updateJobEntryHistory(JobEntryHistory domain) {
		BsymtJobEntryHistoryPk key = new BsymtJobEntryHistoryPk(domain.getSId(), domain.getJoinDate());
		Optional<BsymtJobEntryHistory> existItem = this.queryProxy().find(key, BsymtJobEntryHistory.class);
		
		if (!existItem.isPresent()){
			throw new RuntimeException("invalid JobEntryHistory");
		}
		updateEntity(domain, existItem.get());
		this.commandProxy().update(existItem.get());
	}

}
