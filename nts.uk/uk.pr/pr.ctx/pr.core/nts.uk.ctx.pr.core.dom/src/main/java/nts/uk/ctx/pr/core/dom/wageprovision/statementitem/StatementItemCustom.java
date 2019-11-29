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
     * 略名
     */
    private String shortName;

    /**
     * 廃止区分
     */
    private Integer deprecatedAtr;

    /**
     * 既定区分
     */
    private Integer defaultAtr;

    public StatementItemCustom(String categoryAtr, String itemNameCd, String name, String deprecatedAtr,
                               String defaultAtr, String shortName) {
        this.categoryAtr = Integer.valueOf(categoryAtr);
        this.itemNameCd = itemNameCd;
        this.name = name;
        this.deprecatedAtr = Integer.valueOf(deprecatedAtr);
        this.defaultAtr = Integer.valueOf(defaultAtr);
        this.shortName = shortName;
    }
}
