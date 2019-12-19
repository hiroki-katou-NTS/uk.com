package nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.YearMonth;

@Data
@Builder
public class CurrentProcessDateImport {
    /**
     * 会社ID
     */
    private String cid;

    /**
     * 処理区分NO
     */
    private int processCateNo;

    /**
     * 給与現在処理年月
     */
    private YearMonth giveCurrTreatYear;
}
