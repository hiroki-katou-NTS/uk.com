package nts.uk.ctx.at.function.app.command.supportworklist.aggregationsetting;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.StandardWorkplaceHierarchy;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportAggregationUnit;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSetting;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.WorkplaceSupportJudgmentMethod;

import java.util.Optional;

@Data
public class SupportWorkAggregationSettingCommand {
    private int aggregateUnit;
    private Integer judgmentAtr;
    private Integer standardHierarchy;

    public SupportWorkAggregationSetting toDomain() {
        return new SupportWorkAggregationSetting(
                EnumAdaptor.valueOf(aggregateUnit, SupportAggregationUnit.class),
                Optional.ofNullable(judgmentAtr == null ? null : EnumAdaptor.valueOf(judgmentAtr, WorkplaceSupportJudgmentMethod.class)),
                Optional.ofNullable(standardHierarchy == null ? null : new StandardWorkplaceHierarchy(standardHierarchy))
        );
    }
}
