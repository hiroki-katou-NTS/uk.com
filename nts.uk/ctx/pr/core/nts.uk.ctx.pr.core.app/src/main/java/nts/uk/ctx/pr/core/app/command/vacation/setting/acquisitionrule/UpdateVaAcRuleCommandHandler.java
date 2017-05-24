package nts.uk.ctx.pr.core.app.command.vacation.setting.acquisitionrule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRule;
@Stateless
public class UpdateVaAcRuleCommandHandler extends CommandHandler<UpdateVaAcRuleCommand> {
	
	@Inject
	public AcquisitionRuleRepository vaRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateVaAcRuleCommand> context) {
		
		// Get command.
		UpdateVaAcRuleCommand command = context.getCommand();
		
		//Get CompanyId
		
		//Update VacationAcquisitionRule
	    Optional<AcquisitionRule> optVaAcRule = this.vaRepo.findById(command.getCompanyId());
	    AcquisitionRule vaAcRule = optVaAcRule.get();
	    vaAcRule.setSettingClassification(command.getSettingclassification());
	    vaAcRule.setAcquisitionOrder(command.getAcquisitionOrder());
	    
	    // Update to db.
	    this.vaRepo.update(vaAcRule);
	}
	

}
