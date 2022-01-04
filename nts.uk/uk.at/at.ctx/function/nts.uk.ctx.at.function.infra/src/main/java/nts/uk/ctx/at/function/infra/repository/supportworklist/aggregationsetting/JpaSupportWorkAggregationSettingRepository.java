package nts.uk.ctx.at.function.infra.repository.supportworklist.aggregationsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSetting;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSettingRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaSupportWorkAggregationSettingRepository extends JpaRepository implements SupportWorkAggregationSettingRepository {
    @Override
    public void insert(SupportWorkAggregationSetting domain) {

    }

    @Override
    public void update(SupportWorkAggregationSetting domain) {

    }

    @Override
    public Optional<SupportWorkAggregationSetting> get(String companyId) {
        return Optional.empty();
    }
}
