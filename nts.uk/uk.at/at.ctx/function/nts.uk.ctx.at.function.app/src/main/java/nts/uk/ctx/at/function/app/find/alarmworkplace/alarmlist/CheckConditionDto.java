package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import lombok.Getter;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CheckConditionDto {
    public CheckConditionDto(CheckCondition domain) {
        this.workplaceCategory = domain.getWorkplaceCategory().value;
        this.checkConditionLis = domain.getCheckConditionLis().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());

    }

    /**
     * カテゴリ
     */
    private int workplaceCategory;

    /**
     * チェック条件コード一覧
     */
    private List<String> checkConditionLis;

    /**
     * 抽出期間: Start date
     */
    private GeneralDate startDate; //TODO

    /**
     * 抽出期間: End date
     */
    private GeneralDate endDate; //TODO
}
