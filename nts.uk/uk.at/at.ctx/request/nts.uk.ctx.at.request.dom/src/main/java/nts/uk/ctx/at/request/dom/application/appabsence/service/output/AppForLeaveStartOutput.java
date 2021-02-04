package nts.uk.ctx.at.request.dom.application.appabsence.service.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class AppForLeaveStartOutput {

    private AppAbsenceStartInfoOutput appAbsenceStartInfoOutput;
    
    private ApplyForLeave applyForLeave;
}
