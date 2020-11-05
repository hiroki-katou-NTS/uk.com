package nts.uk.ctx.at.schedule.infra.repository.budget.external;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.KscstExternalBudget;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.KstscExternalBudgetPK;

@Stateless
public class JpaExternalBudgetRepository extends JpaRepository implements ExternalBudgetRepository {

	private static final String SELECT_NO_WHERE = "SELECT c FROM KscstExternalBudget c ";

	private static final String SELECT_ALL_DETAILS = SELECT_NO_WHERE 
			+ " WHERE c.kscstExternalBudgetPk.companyId = :companyId";

	private static final String SELECTED_ITEM = SELECT_NO_WHERE 
			+ " WHERE c.kscstExternalBudgetPk.companyId = :companyId "
			+ " AND c.kscstExternalBudgetPk.externalBudgetCd = :externalBudgetCd ";
	
	private static final String SELECTED_ITEM_ATR = SELECT_ALL_DETAILS + " AND c.budgetAtr = :budgetAtr AND c.unitAtr = :unitAtr";
	

	private static ExternalBudget toDomain(KscstExternalBudget entity) {
		ExternalBudget domain = ExternalBudget.createFromJavaType(entity.kscstExternalBudgetPk.companyId,
				entity.kscstExternalBudgetPk.externalBudgetCd, 
				entity.externalBudgetName, 
				entity.budgetAtr);
		return domain;
	}

	private static KscstExternalBudget toEntity(ExternalBudget domain) {
		val entity = new KscstExternalBudget();
		entity.kscstExternalBudgetPk = new KstscExternalBudgetPK();
		entity.kscstExternalBudgetPk.companyId = domain.getCompanyId();
		entity.kscstExternalBudgetPk.externalBudgetCd = domain.getExternalBudgetCd().v();
		entity.externalBudgetName = domain.getExternalBudgetName().v();
		entity.budgetAtr = domain.getBudgetAtr().value;
		entity.unitAtr = domain.getUnitAtr().value;
		return entity;
	}

	@Override
	public List<ExternalBudget> findAll(String companyId) {
		return this.queryProxy().query(SELECT_ALL_DETAILS, KscstExternalBudget.class)
				.setParameter("companyId", companyId).getList(c -> toDomain(c));
	}

	@Override
	public void insert(ExternalBudget externalBudgetResult) {
		// insert
		this.commandProxy().insert(toEntity(externalBudgetResult));
	}

	@Override
	public void update(ExternalBudget externalBudgetResult) {
		KscstExternalBudget entity = this.queryProxy()
				.find(new KstscExternalBudgetPK(externalBudgetResult.getCompanyId(),
						externalBudgetResult.getExternalBudgetCd().v()), KscstExternalBudget.class)
				.get();
		entity.setExternalBudgetName(externalBudgetResult.getExternalBudgetName().v());
		entity.setUnitAtr(externalBudgetResult.getUnitAtr().value);
		entity.setBudgetAtr(externalBudgetResult.getBudgetAtr().value);
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(String companyId, String externalBudgetCode) {
		val objectKey = new KstscExternalBudgetPK();
		objectKey.companyId = companyId;
		objectKey.externalBudgetCd = externalBudgetCode;
		this.commandProxy().remove(KscstExternalBudget.class, objectKey);

	}

	@Override
	public Optional<ExternalBudget> find(String companyId, String externalBudgetCd) {
		return this.queryProxy().query(SELECTED_ITEM, KscstExternalBudget.class).setParameter("companyId", companyId)
				.setParameter("externalBudgetCd", externalBudgetCd).getSingle(c -> toDomain(c));
	}

	/**
	 * find list ExternalBudget by budgetAtr and unitAtr
	 * author: Hoang Yen
	 */
	@Override
	public List<ExternalBudget> findByAtr(String companyId, int budgetAtr, int unitAtr) {
		return this.queryProxy().query(SELECTED_ITEM_ATR, KscstExternalBudget.class)
				.setParameter("companyId", companyId)
				.setParameter("budgetAtr", budgetAtr)
				.setParameter("unitAtr", unitAtr)
				.getList(c -> toDomain(c));
	}

}
