package nts.uk.ctx.at.schedule.infra.repository.budget.exportexcel;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.app.export.budgetexcel.BudgetExcelRepo;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.KscmtExtBudget;
/**
 * 
 * @author Hoidd
 *
 */
@Stateless
public class JpaExternalBudgetRepositoryExcel extends JpaRepository implements BudgetExcelRepo {

	private static final String SELECT_NO_WHERE = "SELECT c FROM KscmtExtBudget c ";

	private static final String SELECT_ALL_DETAILS = SELECT_NO_WHERE 
			+ " WHERE c.kscmtExtBudgetPk.companyId = :companyId";

	private static ExternalBudget toDomain(KscmtExtBudget entity) {
		ExternalBudget domain = ExternalBudget.createFromJavaType(entity.kscmtExtBudgetPk.companyId,
				entity.kscmtExtBudgetPk.externalBudgetCd, 
				entity.externalBudgetName, 
				entity.budgetAtr,
				entity.unitAtr);
		return domain;
	}


	@Override
	public List<ExternalBudget> findAll(String companyId) {
		return this.queryProxy().query(SELECT_ALL_DETAILS, KscmtExtBudget.class)
				.setParameter("companyId", companyId).getList(c -> toDomain(c));
	}

}
