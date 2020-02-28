package nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

@Value
public class ProcessDateClassificationImport {
    /**
     * 給与現在処理年月
     */
    private YearMonth giveCurrTreatYear;
    /**
     * 社員抽出基準日
     */
    private GeneralDate empExtraRefeDate;
}