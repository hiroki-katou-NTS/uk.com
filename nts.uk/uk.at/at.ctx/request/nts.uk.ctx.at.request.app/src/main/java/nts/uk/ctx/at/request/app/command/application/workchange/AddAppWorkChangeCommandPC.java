package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@Value
public class AddAppWorkChangeCommandPC {
	/**
	 * 勤務変更申請
	 */
	private AppWorkChangeCommand workChange;
		
	/**
	 * 申請
	 */
	private CreateApplicationCommand application;
	
	private AppDispInfoStartupDto appDispInfoStartupOutput;
    
    private List<String> holidayDateLst;
    
}
