package nts.uk.ctx.pr.core.app.command.wageprovision.averagewagecalculationset;

import lombok.Value;

@Value
public class StatementCustomCommand {

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
}
