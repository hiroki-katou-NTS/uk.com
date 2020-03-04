package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemCustom;

@Value
public class StatementItemCustomDto {

    private String key;

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

    public StatementItemCustomDto(StatementItemCustom domain) {
        this.key = String.valueOf(domain.getCategoryAtr()) + domain.getItemNameCd();
        this.categoryAtr = domain.getCategoryAtr();
        this.itemNameCd = domain.getItemNameCd();
        this.name = domain.getName();
        this.deprecatedAtr = domain.getDeprecatedAtr();
    }
}
