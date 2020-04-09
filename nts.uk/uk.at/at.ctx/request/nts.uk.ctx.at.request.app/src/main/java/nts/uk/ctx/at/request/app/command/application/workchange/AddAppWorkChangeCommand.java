package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto;

@Value
public class AddAppWorkChangeCommand {
	/**
	 * 勤務変更申請
	 */
	AppWorkChangeCommand workChange;
		
	/**
	 * 申請
	 */
	CreateApplicationCommand application;
	
	AppWorkChangeDispInfoDto appWorkChangeDispInfoDto;
    
    public List<String> holidayDateLst;
	
	/*String employeeID;
	
	Integer user;
    Integer reflectPerState;

    boolean checkOver1Year;*/
    
}
