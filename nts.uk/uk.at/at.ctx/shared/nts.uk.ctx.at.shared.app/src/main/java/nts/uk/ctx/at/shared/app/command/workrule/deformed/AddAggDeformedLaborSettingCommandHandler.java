package nts.uk.ctx.at.shared.app.command.workrule.deformed;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AddAggDeformedLaborSettingCommandHandler.
 *
 * @author HoangNDH
 */
@Stateless
public class AddAggDeformedLaborSettingCommandHandler extends CommandHandler<AddAggDeformedLaborSettingCommand> {
	
	/** The repository. */
	@Inject
	AggDeformedLaborSettingRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AddAggDeformedLaborSettingCommand> context) {
		String companyId = AppContexts.user().companyId();
		AddAggDeformedLaborSettingCommand command = context.getCommand();
		
		Optional<AggDeformedLaborSetting> optAggSetting = repository.findByCid(companyId);
		AggDeformedLaborSetting setting = AggDeformedLaborSetting.createFromJavaType(companyId, command.getUseDeformedSetting());
		if (optAggSetting.isPresent()) {
			repository.update(setting);
		}
		else {
			repository.insert(setting);
		}
	}

}
