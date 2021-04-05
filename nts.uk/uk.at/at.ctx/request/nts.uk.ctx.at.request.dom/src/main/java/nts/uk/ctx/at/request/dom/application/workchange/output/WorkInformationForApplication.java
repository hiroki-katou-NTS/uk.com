package nts.uk.ctx.at.request.dom.application.workchange.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author anhnm
 * 申請中の勤務情報
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkInformationForApplication {

    /*
     * 就業時間帯
     */
    private WorkTimeCode workTimeCode;
    
    /*
     * 勤務種類
     */
    private WorkTypeCode workTypeCode;
}
