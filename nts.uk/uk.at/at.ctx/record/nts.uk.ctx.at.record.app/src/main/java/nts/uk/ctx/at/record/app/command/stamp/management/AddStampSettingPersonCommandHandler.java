package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class AddStampSettingPersonCommandHandler extends CommandHandler<AddStampSettingPersonCommand>{

	@Inject
	private StampSetPerRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<AddStampSettingPersonCommand> context) {
		
		AddStampSettingPersonCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<StampSettingPerson> checkUpdate = repo.getStampSet(companyId);
		
		if(checkUpdate.isPresent())
			// update 個人利用の打刻設定
			repo.update(command.toDomain());
		else
			// add 個人利用の打刻設定
			repo.insert(command.toDomain());
	}
}
