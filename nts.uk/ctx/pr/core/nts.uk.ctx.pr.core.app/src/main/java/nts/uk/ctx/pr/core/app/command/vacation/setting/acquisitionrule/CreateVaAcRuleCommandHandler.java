package nts.uk.ctx.pr.core.app.command.vacation.setting.acquisitionrule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRule;


@Stateless
public class CreateVaAcRuleCommandHandler extends CommandHandler<CreateVaAcRuleCommand>{
	
	@Inject
	private AcquisitionRuleRepository vaAcRuleRepo;
	
	@Override
	protected void handle(CommandHandlerContext<CreateVaAcRuleCommand> context) {
		
		CreateVaAcRuleCommand command = context.getCommand();
		//GET COMPANY ID
		command.setCompanyId(""); 
		
		AcquisitionRule vaAcRule = new AcquisitionRule(command);
		
		this.vaAcRuleRepo.create(vaAcRule);
		
	}

}
