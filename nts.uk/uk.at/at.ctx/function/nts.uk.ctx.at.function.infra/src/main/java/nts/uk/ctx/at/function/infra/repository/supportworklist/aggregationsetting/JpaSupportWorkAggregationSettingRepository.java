package nts.uk.ctx.at.function.infra.repository.supportworklist.aggregationsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSetting;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSettingRepository;
import nts.uk.ctx.at.function.infra.entity.supportworklist.aggregationsetting.KfnmtWplSupJudge;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaSupportWorkAggregationSettingRepository extends JpaRepository implements SupportWorkAggregationSettingRepository {
    @Override
    public void insert(SupportWorkAggregationSetting domain) {
        this.commandProxy().insert(new KfnmtWplSupJudge(
                AppContexts.user().companyId(),
                domain.getAggregationUnit().value,
                domain.getJudgmentMethod().map(i -> i.value).orElse(null),
                domain.getStandardWorkplaceHierarchy().map(PrimitiveValueBase::v).orElse(null)
        ));
    }

    @Override
    public void update(SupportWorkAggregationSetting domain) {
        this.queryProxy().find(AppContexts.user().companyId(), KfnmtWplSupJudge.class).ifPresent(entity -> {
            entity.aggregationUnit = domain.getAggregationUnit().value;
            entity.judgmentMethod = domain.getJudgmentMethod().map(i -> i.value).orElse(null);
            entity.standardWorkplaceHierarchy = domain.getStandardWorkplaceHierarchy().map(PrimitiveValueBase::v).orElse(null);
            this.commandProxy().update(entity);
        });
    }

    @Override
    public Optional<SupportWorkAggregationSetting> get(String companyId) {
        return this.queryProxy().find(companyId, KfnmtWplSupJudge.class).map(KfnmtWplSupJudge::toDomain);
    }
}
