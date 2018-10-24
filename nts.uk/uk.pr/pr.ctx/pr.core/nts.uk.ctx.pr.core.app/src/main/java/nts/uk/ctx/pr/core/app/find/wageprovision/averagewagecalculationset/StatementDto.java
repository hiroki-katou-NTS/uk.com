package nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.StatementCustom;

@Value
public class StatementDto {
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

    public StatementDto(StatementCustom domain) {
        this.salaryItemId = domain.getSalaryItemId();
        this.categoryAtr = domain.getCategoryAtr();
        this.itemNameCd = domain.getItemNameCd();
        this.name = domain.getName();
    }
}
