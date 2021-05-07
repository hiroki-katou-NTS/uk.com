package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSettingRepository;
import nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion.KagmtCriterionMoneyUsage;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaCriterionAmountUsageSettingRepository extends JpaRepository
		implements CriterionAmountUsageSettingRepository {

	
	public  static final String SELECT;
	
	public static final String FIND_BY_CID;
	
	static {
		
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append(" FROM KagmtCriterionMoneyUsage a ");
        SELECT = builderString.toString();

        
        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId");
        FIND_BY_CID = builderString.toString();
        
	}
	@Override
	public void insert(String cid, CriterionAmountUsageSetting usageSetting) {
		
		this.commandProxy().insert(KagmtCriterionMoneyUsage.toEntity(usageSetting));

	}

	@Override
	public void update(String cid, CriterionAmountUsageSetting usageSetting) {
		KagmtCriterionMoneyUsage entity = this.queryProxy()
				.query(FIND_BY_CID, KagmtCriterionMoneyUsage.class)
				.setParameter("companyId", cid)
				.getSingle().orElse(null);
		this.commandProxy().remove(entity);
		this.getEntityManager().flush();
		this.commandProxy().insert(KagmtCriterionMoneyUsage.toEntity(usageSetting));

	}

	@Override
	public boolean exist(String cid) {
		
		return this.get(cid).isPresent();
	}

	@Override
	public Optional<CriterionAmountUsageSetting> get(String cid) {
		KagmtCriterionMoneyUsage entity = this.queryProxy()
			.query(FIND_BY_CID, KagmtCriterionMoneyUsage.class)
			.setParameter("companyId", cid)
			.getSingle().orElse(null);
				
		return Optional.ofNullable(KagmtCriterionMoneyUsage.toDomain(entity));
	}

}
