package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;

import java.util.List;

@Data
@AllArgsConstructor
public class SalIndAmountHisCommand {


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
    private List<GenericHistYMPeriodCommand> yearMonthHistoryItem;

    /**
     * 給与賞与区分
     */
    private int salBonusCate;


    private String lastHistoryId;

}
