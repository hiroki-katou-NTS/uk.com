package nts.uk.ctx.pr.file.app.core.wageprovision.taxexemptionlimit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemption;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionName;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxLimitAmountCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxExemptLimitSetExportData {
    /**
     * 会社ID
     */
    private String cid;

    /**
     * 非課税限度額コード
     */
    private TaxLimitAmountCode taxFreeAmountCode;

    /**
     * 非課税限度額名称
     */
    private TaxExemptionName taxExemptionName;

    /**
     * 非課税限度額
     */
    private TaxExemption taxExemption;
}
