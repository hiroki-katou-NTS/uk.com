package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountRepository;
import nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion.KagmtCriterionMoneyFrame;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaHandlingOfCriterionAmountRepository extends JpaRepository
		implements HandlingOfCriterionAmountRepository {

	
	public  static final String SELECT;
	
	public static final String FIND_BY_CID;
	
	static {
		
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append(" FROM KagmtCriterionMoneyFrame a ");
        SELECT = builderString.toString();

        
        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId");
        FIND_BY_CID = builderString.toString();
        
	}
	@Override
	public void insert(String cid, HandlingOfCriterionAmount handling) {
		this.commandProxy().insert(KagmtCriterionMoneyFrame.toEntity(handling));

	}

	@Override
	public void update(String cid, HandlingOfCriterionAmount handling) {
		List<KagmtCriterionMoneyFrame> entities = this.queryProxy()
				.query(FIND_BY_CID, KagmtCriterionMoneyFrame.class)
				.setParameter("companyId", cid)
				.getList();
		this.commandProxy().removeAll(entities);
		this.getEntityManager().flush();
		
		KagmtCriterionMoneyFrame.toEntity(handling).forEach(entity -> {
			this.commandProxy().insert(entity);
			this.getEntityManager().flush();
		});
		
	}

	@Override
	public boolean exist(String cid) {
		
		return this.get(cid).isPresent();
	}

	@Override
	public Optional<HandlingOfCriterionAmount> get(String cid) {
		
		List<KagmtCriterionMoneyFrame> entities = this.queryProxy()
			.query(FIND_BY_CID, KagmtCriterionMoneyFrame.class)
			.setParameter("companyId", cid)
			.getList();
		return Optional.ofNullable(KagmtCriterionMoneyFrame.toDomain(entities));
	}

}
