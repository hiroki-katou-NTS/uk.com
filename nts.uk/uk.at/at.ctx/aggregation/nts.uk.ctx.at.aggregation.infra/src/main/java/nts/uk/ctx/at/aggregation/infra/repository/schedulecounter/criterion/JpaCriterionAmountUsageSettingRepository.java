package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSettingRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaCriterionAmountUsageSettingRepository extends JpaRepository implements CriterionAmountUsageSettingRepository {
    @Override
    public void insert(String cid, CriterionAmountUsageSetting usageSetting) {

    }

    @Override
    public void update(String cid, CriterionAmountUsageSetting usageSetting) {

    }

    @Override
    public boolean exist(String cid) {
        return false;
    }

    @Override
    public Optional<CriterionAmountUsageSetting> get(String cid) {
        return Optional.empty();
    }
}
