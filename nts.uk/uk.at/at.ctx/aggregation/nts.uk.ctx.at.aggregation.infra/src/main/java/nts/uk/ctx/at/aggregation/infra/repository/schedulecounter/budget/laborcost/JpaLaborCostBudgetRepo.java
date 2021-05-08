package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.budget.laborcost;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudget;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudgetRepository;
import nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.budget.laborcost.KagdtLaborCostBudget;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 
 * @author TU-TK
 *
 */
@Stateless
public class JpaLaborCostBudgetRepo extends JpaRepository implements LaborCostBudgetRepository  {
	
	private static final String GET_BY_ID_PERIOD = "SELECT a FROM KagdtLaborCostBudget a "
			+ " WHERE a.companyId = :companyId "
			+ " AND a.pk.targetUnit = :targetUnit "
			+ " AND a.pk.targetId = :targetId "
			+ " AND a.pk.ymd >= :startDate "
			+ " AND a.pk.ymd <= :endDate ";
	private static final String GET_BY_ID = "SELECT a FROM KagdtLaborCostBudget a "
			+ " WHERE a.companyId = :companyId "
			+ " AND a.pk.targetUnit = :targetUnit "
			+ " AND a.pk.targetId = :targetId "
			+ " AND a.pk.ymd = :ymd ";

	@Override
	public void insert(String companyId, LaborCostBudget domain) {
		this.commandProxy().insert(KagdtLaborCostBudget.toEntity(companyId, domain));
	}

	@Override
	public void delete(String companyId, TargetOrgIdenInfor targetOrg, GeneralDate ymd) {
		KagdtLaborCostBudget data = this.queryProxy()
				.query(GET_BY_ID, KagdtLaborCostBudget.class)
				.setParameter("companyId", companyId)
				.setParameter("targetUnit", targetOrg.getUnit().value)
				.setParameter("targetId", targetOrg.getTargetId())
				.setParameter("ymd", ymd)
				.getSingle().get();
		this.commandProxy().remove(data);
		
	}

	@Override
	public List<LaborCostBudget> get(String companyId, TargetOrgIdenInfor targetOrg, DatePeriod period) {
		List<LaborCostBudget> data = this.queryProxy()
				.query(GET_BY_ID_PERIOD, KagdtLaborCostBudget.class)
				.setParameter("companyId", companyId)
				.setParameter("targetUnit", targetOrg.getUnit().value)
				.setParameter("targetId", targetOrg.getTargetId())
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.getList(c->c.toDomain());
		return data;
	}

}
