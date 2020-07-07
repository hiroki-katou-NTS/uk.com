package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;

@Value
public class AddAppWorkChangeCommand_Old {
	/**
	 * 勤務変更申請
	 */
	AppWorkChangeCommand workChange;
		
	/**
	 * 申請
	 */
	CreateApplicationCommand application;
	
	AppWorkChangeDispInfoCmd appWorkChangeDispInfoCmd;
    
    public List<String> holidayDateLst;
	
	/*String employeeID;
	
	Integer user;
    Integer reflectPerState;

    boolean checkOver1Year;*/
    
}
