package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class AppForLeaveStartBCommand {

    public String appID;
    
    public AppDispInfoStartupDto appDispInfoStartupOutput;
}
