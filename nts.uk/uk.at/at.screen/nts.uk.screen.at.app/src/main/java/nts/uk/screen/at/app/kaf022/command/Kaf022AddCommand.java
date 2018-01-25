package nts.uk.screen.at.app.kaf022.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.request.ApplicationDeadlineCommand;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kaf022AddCommand {
	private ApplicationDeadlineCommand appDead;
	private ApplicationSettingCommand appSet; 
	private JobAssignSettingCommand df;
}
