package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class AbsenceStartScreenBOutput {

    public AppAbsenceStartInfoDto appAbsenceStartInfo;
    
    public ApplyForLeaveDto applyForLeave;
}
