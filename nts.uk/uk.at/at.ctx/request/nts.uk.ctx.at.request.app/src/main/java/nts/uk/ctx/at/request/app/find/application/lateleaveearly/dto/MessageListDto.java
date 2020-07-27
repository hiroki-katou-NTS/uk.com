package nts.uk.ctx.at.request.app.find.application.lateleaveearly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoDto;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class MessageListDto {
	private boolean agentAtr;
	
	private boolean isNew;
	
	private ArrivedLateLeaveEarlyInfoDto infoOutput;
	
	private ApplicationDto application;
}
