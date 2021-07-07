package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.shr.com.context.AppContexts;
/**
 * 打刻の前準備(個人)を登録する
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
			repo.update(command.toDomain(checkUpdate.get().getLstStampPageLayout()));
		else
			// add 個人利用の打刻設定
			repo.insert(command.toDomain(new ArrayList<>()));
	}
}
