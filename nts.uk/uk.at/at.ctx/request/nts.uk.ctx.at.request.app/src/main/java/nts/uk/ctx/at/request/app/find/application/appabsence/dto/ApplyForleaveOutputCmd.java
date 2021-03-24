package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApplyForLeave;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyForleaveOutputCmd {

    /**
             * 休暇申請起動時の表示情報
     */
    private AppAbsenceStartInfoDto appAbsenceStartInfoOutput;
    
    /**
             * 休暇申請
     */
    private ApplyForLeaveDto applyForLeave;
    
    public PrintContentOfApplyForLeave toDomain() {
        String companyID = AppContexts.user().companyId();
        
        return new PrintContentOfApplyForLeave(
                this.appAbsenceStartInfoOutput.toDomain(companyID), 
                this.applyForLeave.toDomain());
    }
}
