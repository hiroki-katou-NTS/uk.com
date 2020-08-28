package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class DeleteStampSettingCommandHandler extends CommandHandler<DeleteStampSettingCommand>{

	@Inject
	private StampSetPerRepository repository;
	
	@Inject
	private StampSetCommunalRepository stampSetCommunalRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteStampSettingCommand> context) {
		String companyId = AppContexts.user().companyId();
		// get command
		DeleteStampSettingCommand command = context.getCommand();
		if(command.getMode() == 1) {
			// delete process
			repository.delete(companyId,command.getPageNo());
		}else if(command.getMode() == 0) {
			Optional<StampSetCommunal> domainPre = stampSetCommunalRepo.gets(companyId);
			if (domainPre.isPresent()) {
				domainPre.get().getLstStampPageLayout().removeIf(c->c.getPageNo().v() == command.getPageNo());
				stampSetCommunalRepo.save(domainPre.get());
			}
		}
	}

}
