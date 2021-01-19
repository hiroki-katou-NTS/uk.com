package nts.uk.ctx.at.request.dom.application.common.service.print;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrintContentOfApplyForLeave {

    /**
             * 休暇申請起動時の表示情報
     */
    private AppAbsenceStartInfoOutput appAbsenceStartInfoOutput;
    
    /**
             * 休暇申請
     */
    private ApplyForLeave applyForLeave;
}
