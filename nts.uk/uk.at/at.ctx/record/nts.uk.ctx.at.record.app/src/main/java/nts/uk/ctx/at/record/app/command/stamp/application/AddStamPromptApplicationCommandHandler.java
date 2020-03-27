package nts.uk.ctx.at.record.app.command.stamp.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.application.StamPromptAppRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StampRecordDis;
import nts.uk.shr.com.context.AppContexts;
/**
 * 打刻の前準備(オプション)を登録する of AddStamFunctionCommandHandler
 * @author phongtq
 *
 */
@Stateless
public class AddStamPromptApplicationCommandHandler extends CommandHandler<AddStamPromptApplicationCommad>{

	@Inject
	private StamPromptAppRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<AddStamPromptApplicationCommad> context) {
		AddStamPromptApplicationCommad command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		List<StampRecordDis> stampPage = repo.getAllStampSetPage(companyId).stream().collect(Collectors.toList());
		if(stampPage.size() > 0)
			// update 打刻ページレイアウト
			this.repo.update(command.toDomain());
		else
			// add 打刻ページレイアウト
			this.repo.insert(command.toDomain());
	}
}
