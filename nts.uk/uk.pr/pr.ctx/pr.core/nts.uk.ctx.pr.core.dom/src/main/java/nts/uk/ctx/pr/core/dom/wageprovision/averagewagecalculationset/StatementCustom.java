package nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementCustom {
    /**
     * 給与項目ID
     */
    private String salaryItemId;

    /**
     * カテゴリ区分
     */
    private int categoryAtr;

    /**
     * 項目名コード
     */
    private String itemNameCd;

    /**
     * 名称
     */
    private String name;

    public StatementCustom(String salaryItemId, String categoryAtr, String itemNameCd, String name) {
        this.salaryItemId = salaryItemId;
        this.categoryAtr = Integer.valueOf(categoryAtr).intValue();
        this.itemNameCd = itemNameCd;
        this.name = name;
    }
}
