package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompanyRepository;
import nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion.KagmtCriterionMoneyCmp;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaCriterionAmountForCompanyRepository extends JpaRepository
		implements CriterionAmountForCompanyRepository {
	
	public  static final String SELECT;
	
	public static final String FIND_BY_CID;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append(" FROM KagmtCriterionMoneyCmp a ");
        SELECT = builderString.toString();
        
        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId ");
        FIND_BY_CID = builderString.toString();
	}

	@Override
	public void insert(String cid, CriterionAmountForCompany criterion) {
		
		KagmtCriterionMoneyCmp.toEntity(criterion)
				.forEach(entity -> {
					this.commandProxy().insert(entity);
					this.getEntityManager().flush();
				});

	}

	@Override
	public void update(String cid, CriterionAmountForCompany criterion) {
		
		List<KagmtCriterionMoneyCmp> entities = this.queryProxy().query(FIND_BY_CID, KagmtCriterionMoneyCmp.class)
				.setParameter("companyId", cid)
				.getList();
		
		this.commandProxy().removeAll(entities);
		this.getEntityManager().flush();
		
		KagmtCriterionMoneyCmp.toEntity(criterion)
			.forEach(entity -> {
				this.commandProxy().insert(entity);
				this.getEntityManager().flush();
			});
	}

	@Override
	public boolean exist(String cid) {
		List<KagmtCriterionMoneyCmp> entities = this.queryProxy().query(FIND_BY_CID, KagmtCriterionMoneyCmp.class)
				.setParameter("companyId", cid)
				.getList();
		return !CollectionUtil.isEmpty(entities);
	}

	@Override
	public Optional<CriterionAmountForCompany> get(String cid) {
		
		List<KagmtCriterionMoneyCmp> entities = this.queryProxy().query(FIND_BY_CID, KagmtCriterionMoneyCmp.class)
			.setParameter("companyId", cid)
			.getList();
		
		return Optional.ofNullable(KagmtCriterionMoneyCmp.toDomain(entities));
	}

}
