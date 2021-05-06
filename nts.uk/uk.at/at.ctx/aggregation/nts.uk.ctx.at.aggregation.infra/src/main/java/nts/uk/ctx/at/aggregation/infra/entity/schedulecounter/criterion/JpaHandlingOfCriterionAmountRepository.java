package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaHandlingOfCriterionAmountRepository extends JpaRepository
		implements HandlingOfCriterionAmountRepository {

	@Override
	public void insert(String cid, HandlingOfCriterionAmount handling) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String cid, HandlingOfCriterionAmount handling) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exist(String cid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<HandlingOfCriterionAmount> get(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

}
