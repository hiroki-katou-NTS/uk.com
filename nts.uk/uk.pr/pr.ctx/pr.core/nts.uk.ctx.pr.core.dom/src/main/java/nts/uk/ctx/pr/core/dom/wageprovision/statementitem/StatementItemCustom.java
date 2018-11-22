package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementItemCustom {

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

    /**
     * 既定区分
     */
    private Integer defaultAtr;

    public StatementItemCustom(String categoryAtr, String itemNameCd, String name, String deprecatedAtr) {
        this.categoryAtr = Integer.valueOf(categoryAtr).intValue();
        this.itemNameCd = itemNameCd;
        this.name = name;
        this.deprecatedAtr = Integer.valueOf(deprecatedAtr);
    }

    public StatementItemCustom(String categoryAtr, String itemNameCd, String name, String deprecatedAtr, String defaultAtr) {
        this.categoryAtr = Integer.valueOf(categoryAtr).intValue();
        this.itemNameCd = itemNameCd;
        this.name = name;
        this.deprecatedAtr = Integer.valueOf(deprecatedAtr);
        this.defaultAtr = Integer.valueOf(defaultAtr);
    }
}
