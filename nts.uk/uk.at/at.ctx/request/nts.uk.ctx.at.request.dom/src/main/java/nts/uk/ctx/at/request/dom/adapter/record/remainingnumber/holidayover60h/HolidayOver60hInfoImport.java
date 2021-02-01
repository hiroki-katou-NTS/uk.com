package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.holidayover60h;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HolidayOver60hInfoImport {
    /** 年月日 */
    private GeneralDate ymd;

    /** 残数 */
    private HolidayOver60hRemainingNumberImport remainingNumber;

    /** 付与残数データ */
    private List<HolidayOver60hGrantRemainingImport> grantRemainingList;
}
