package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class SalIndAmountHisCommand {

    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 個人金額コード
     */
    private String perValCode;

    /**
     * 社員ID
     */
    private String empId;

    /**
     * カテゴリ区分
     */
    private int cateIndicator;

    /**
     * 期間
     */
    private Integer periodStartYM;

    /**
     * 期間
     */
    private Integer periodEndYM;

    /**
     * 給与賞与区分
     */
    private int salBonusCate;
}
