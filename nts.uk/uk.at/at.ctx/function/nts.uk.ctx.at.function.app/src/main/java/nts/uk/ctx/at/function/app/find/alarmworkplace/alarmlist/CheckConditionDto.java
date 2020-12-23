package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import lombok.Getter;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
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
        this.periodType = PeriodType.DATE.value;
    }

    public CheckConditionDto(WorkplaceCategory workplaceCategory, YearMonth startYm, YearMonth endYm) {
        this.category = workplaceCategory.value;
        this.categoryName = TextResource.localize(workplaceCategory.nameId);
        this.startYm = startYm == null ? null : startYm.v();
        this.endYm = endYm == null ? null : endYm.v();
        this.periodType = PeriodType.YM.value;
    }

    public CheckConditionDto(WorkplaceCategory workplaceCategory, YearMonth yearMonth) {
        this.category = workplaceCategory.value;
        this.categoryName = TextResource.localize(workplaceCategory.nameId);
        this.yearMonth = yearMonth == null ? null : yearMonth.v();
        this.periodType = PeriodType.MONTH.value;
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

    private int periodType;

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
