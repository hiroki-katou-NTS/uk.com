package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementItemCustom {
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

    /**
     * 廃止区分
     */
    private Integer deprecatedAtr;

    public StatementItemCustom(String salaryItemId, String categoryAtr, String itemNameCd, String name, String deprecatedAtr) {
        this.salaryItemId = salaryItemId;
        this.categoryAtr = Integer.valueOf(categoryAtr).intValue();
        this.itemNameCd = itemNameCd;
        this.name = name;
        this.deprecatedAtr = Integer.valueOf(deprecatedAtr);
    }
}
