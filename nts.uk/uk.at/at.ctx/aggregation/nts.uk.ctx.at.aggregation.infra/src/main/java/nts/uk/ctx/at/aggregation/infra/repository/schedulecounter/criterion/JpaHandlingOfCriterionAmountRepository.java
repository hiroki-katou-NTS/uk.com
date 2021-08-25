package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaHandlingOfCriterionAmountRepository extends JpaRepository implements HandlingOfCriterionAmountRepository {
    @Override
    public void insert(String cid, HandlingOfCriterionAmount handling) {

    }

    @Override
    public void update(String cid, HandlingOfCriterionAmount handling) {

    }

    @Override
    public boolean exist(String cid) {
        return false;
    }

    @Override
    public Optional<HandlingOfCriterionAmount> get(String cid) {
        return Optional.empty();
    }
}
