package nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementCustom {

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

    public StatementCustom(String categoryAtr, String itemNameCd, String name) {
        this.categoryAtr = Integer.valueOf(categoryAtr).intValue();
        this.itemNameCd = itemNameCd;
        this.name = name;
    }
}
