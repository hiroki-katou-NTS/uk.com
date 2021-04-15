package nts.uk.ctx.at.function.dom.adapter.holidayover60h;

import nts.arc.time.GeneralDate;

import java.util.List;

public class HolidayOver60hInfoImport {
    /** 年月日 */
    private GeneralDate ymd;

    /** 残数 */
    private HolidayOver60hRemainingNumberImport remainingNumber;

    /** 付与残数データ */
    private List<HolidayOver60hGrantRemainingImport> grantRemainingList;
}
