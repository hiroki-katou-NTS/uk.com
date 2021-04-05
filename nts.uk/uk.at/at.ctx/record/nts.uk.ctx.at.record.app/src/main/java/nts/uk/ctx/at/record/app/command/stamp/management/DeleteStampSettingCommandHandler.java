package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopier;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopierRepository;
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
	
	@Inject
	private StampSettingOfRICOHCopierRepository stampSettingOfRICOHCopierRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteStampSettingCommand> context) {
		String companyId = AppContexts.user().companyId();
		// get command
		DeleteStampSettingCommand command = context.getCommand();
		if(command.getMode() == 1) {
			// delete process
			repository.delete(companyId,command.getPageNo());
		}else if(command.getMode() == 0) {
			Optional<StampSetCommunal> domain = stampSetCommunalRepo.gets(companyId);
			if (domain.isPresent()) {
				domain.get().getLstStampPageLayout().removeIf(c->c.getPageNo().v() == command.getPageNo());
				stampSetCommunalRepo.save(domain.get());
			}
		}else if(command.getMode() == 5) {
			Optional<StampSettingOfRICOHCopier> domain = stampSettingOfRICOHCopierRepo.get(companyId);
			if (domain.isPresent()) {
				domain.get().getPageLayoutSettings().removeIf(c->c.getPageNo().v() == command.getPageNo());
				stampSettingOfRICOHCopierRepo.update(domain.get());
			}
		}
	}

}
