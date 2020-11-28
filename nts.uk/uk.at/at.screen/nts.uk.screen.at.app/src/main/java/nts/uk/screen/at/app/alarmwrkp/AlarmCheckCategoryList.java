package nts.uk.screen.at.app.alarmwrkp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;


@Getter
@NoArgsConstructor
public class AlarmCheckCategoryList {

    // カテゴリ
    private int category;

    private String categoryName;

    // アラームチェック条件コード
    private String code;

    // 名称
    private String name;

    public AlarmCheckCategoryList(int category, String code, String name) {
        this.category = category;
        this.categoryName = EnumAdaptor.valueOf(category, WorkplaceCategory.class).name();
        this.code = code;
        this.name = name;
    }
}
