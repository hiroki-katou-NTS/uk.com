package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.CategoryIndicator;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalBonusCate;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;

import java.util.List;

/**
* 給与個人別金額履歴: DTO
*/
@AllArgsConstructor
@Value
public class SalIndAmountHisDto
{

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
    private CategoryIndicator cateIndicator;

    /**
     * 期間
     */
    private List<GenericHistYMPeriod> period;

    /**
     * 給与賞与区分
     */
    private SalBonusCate salBonusCate;

    
}
