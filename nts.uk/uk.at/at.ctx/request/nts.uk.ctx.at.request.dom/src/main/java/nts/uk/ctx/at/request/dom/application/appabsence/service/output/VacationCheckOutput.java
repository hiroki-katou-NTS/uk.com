package nts.uk.ctx.at.request.dom.application.appabsence.service.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacationCheckOutput {

    // 代休紐付管理をクリアする
    public boolean clearManageSubsHoliday;
    
    // 振休紐付管理をクリアする
    public boolean clearManageHolidayString;
}
