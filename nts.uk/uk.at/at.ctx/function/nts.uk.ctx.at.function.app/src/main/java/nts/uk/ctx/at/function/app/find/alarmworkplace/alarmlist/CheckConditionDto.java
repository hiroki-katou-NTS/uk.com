package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import lombok.Getter;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.shr.com.i18n.TextResource;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CheckConditionDto {
    public CheckConditionDto(WorkplaceCategory workplaceCategory, GeneralDate startDate, GeneralDate endDate) {
        this.category = workplaceCategory.value;
        this.categoryName = TextResource.localize(workplaceCategory.nameId);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CheckConditionDto(WorkplaceCategory workplaceCategory, Integer startYm,  Integer endYm) {
        this.category = workplaceCategory.value;
        this.categoryName = TextResource.localize(workplaceCategory.nameId);
        this.startYm = startYm;
        this.endYm = endYm;
    }

    public CheckConditionDto(WorkplaceCategory workplaceCategory, Integer yearMonth) {
        this.category = workplaceCategory.value;
        this.categoryName = TextResource.localize(workplaceCategory.nameId);
        this.yearMonth = yearMonth;
    }

    /**
     * カテゴリ
     */
    private int category;

    /**
     * カテゴリ: Name
     */
    private String categoryName;

    /**
     * チェック条件コード一覧
     */
    private List<String> checkConditionLis;

    /**
     * 抽出期間: Start date
     */
    private GeneralDate startDate;

    /**
     * 抽出期間: End date
     */
    private GeneralDate endDate;

    /**
     * 抽出期間: Start date
     */
    private Integer startYm;

    /**
     * 抽出期間: End date
     */
    private Integer endYm;

    /**
     * 抽出期間
     */
    private Integer yearMonth;
}
