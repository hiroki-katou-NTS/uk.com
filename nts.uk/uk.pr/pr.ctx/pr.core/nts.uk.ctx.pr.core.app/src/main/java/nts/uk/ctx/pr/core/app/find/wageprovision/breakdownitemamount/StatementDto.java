package nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.StatementCustom;

@Value
@AllArgsConstructor
public class StatementDto {

    private String key;

    /**
     * カテゴリ区分
     */
    private Integer categoryAtr;

    /**
     * 項目名コード
     */
    private String itemNameCd;

    /**
     * 名称
     */
    private String name;

    public StatementDto(StatementCustom domain) {
        this.key = String.valueOf(domain.getCategoryAtr()) + domain.getItemNameCd();
        this.categoryAtr = domain.getCategoryAtr();
        this.itemNameCd = domain.getItemNameCd();
        this.name = domain.getName();
    }
}
