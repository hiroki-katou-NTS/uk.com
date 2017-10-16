package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.common.appapprovalphase.AppApprovalPhaseCmd;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertApplicationGoBackDirectlyCommand {
	
	InsertGoBackDirectlyCommand goBackCommand;
	
	CreateApplicationCommand appCommand;
	
	List<AppApprovalPhaseCmd> appApprovalPhaseCmds;

}
