package nts.uk.ctx.at.schedule.infra.repository.budget.external;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.KscstExternalBudget;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.KstscExternalBudgetPK;

@Stateless
public class JpaExternalBudgetRepository extends JpaRepository implements ExternalBudgetRepository {

	private final String SELECT_NO_WHERE = "SELECT c FROM KscstExternalBudget c ";

	private final String SELECT_ALL_DETAILS = SELECT_NO_WHERE + " WHERE c.KscstExternalBudgetPK.companyId = :companyId";

//	private final String SELECTED_ITEM = SELECT_NO_WHERE + " WHERE c.KscstExternalBudgetPK.companyId = :companyId "
//			+ " AND c.KscstExternalBudgetPK.externalBudgetCd = :externalBudgetCd ";

	private static ExternalBudget toDomain(KscstExternalBudget entity) {
		ExternalBudget domain = ExternalBudget.createFromJavaType(entity.kscstExternalBudgetPk.companyId,
				entity.kscstExternalBudgetPk.externalBudgetCd, entity.externalBudgetName, entity.budgetAtr,
				entity.unitAtr);
		return domain;
	}

	private static KscstExternalBudget toEntity(ExternalBudget domain) {
		val entity = new KscstExternalBudget();

		entity.kscstExternalBudgetPk = new KstscExternalBudgetPK();
		entity.externalBudgetName = domain.getExternalBudgetName().v();
		entity.budgetAtr = domain.getBudgetAtr().value;
		entity.unitAtr = domain.getUnitAtr().value;

		return entity;
	}

	@Override
	public List<ExternalBudget> findAll(String companyId) {
		try {
			return this.queryProxy().query(SELECT_ALL_DETAILS,KscstExternalBudget.class)
					.setParameter("companyId", companyId)
					.getList(c-> toDomain(c));
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void insert(ExternalBudget externalBudgetResult) {
		//
		this.commandProxy().insert(toEntity(externalBudgetResult));
	}

	@Override
	public void update(ExternalBudget externalBudgetResult) {
		//
		this.commandProxy().update(toEntity(externalBudgetResult));

	}

	@Override
	public void delete(String companyId, String externalBudgetId) {
		val objectKey = new KstscExternalBudgetPK();
		objectKey.companyId = companyId;
		objectKey.externalBudgetCd = externalBudgetId;
		this.commandProxy().remove(KscstExternalBudget.class, objectKey);

	}

}
