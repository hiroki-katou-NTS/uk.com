package nts.uk.ctx.at.request.app.command.application.lateleaveearly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ArrivedLateLeaveEarlyDto;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class LateEarlyUpdateCommand {
	private ApplicationDto application;
	
	private ArrivedLateLeaveEarlyDto arrivedLateLeaveEarlyDto;
	
	private AppDispInfoStartupDto appDispInfoStartupDto;
}
