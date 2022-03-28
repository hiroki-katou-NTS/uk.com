package nts.uk.ctx.at.function.app.find.supportworklist.aggregationsetting;

import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SupportWorkAggregationSettingFinder {
    @Inject
    private SupportWorkAggregationSettingRepository repository;

    public SupportWorkAggregationSettingDto getSetting() {
        return repository.get(AppContexts.user().companyId()).map(i -> new SupportWorkAggregationSettingDto(
                i.getAggregationUnit().value,
                i.getJudgmentMethod().map(j -> j.value).orElse(null),
                i.getStandardWorkplaceHierarchy().map(PrimitiveValueBase::v).orElse(null))
        ).orElse(null);
    }
}
