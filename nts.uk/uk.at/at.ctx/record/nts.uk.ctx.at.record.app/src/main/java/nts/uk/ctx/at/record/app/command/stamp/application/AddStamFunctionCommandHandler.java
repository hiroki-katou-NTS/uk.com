package nts.uk.ctx.at.record.app.command.stamp.application;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplayRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 打刻の前準備(オプション)を登録する 
 * @author phongtq
 *
 */
@Stateless
public class AddStamFunctionCommandHandler extends CommandHandler<AddStamFunctionCommad>{

	@Inject
	private StampResultDisplayRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<AddStamFunctionCommad> context) {
		AddStamFunctionCommad command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<StampResultDisplay> stampPage = repo.getStampSet(companyId);
		if(stampPage.isPresent())
			// update 打刻ページレイアウト
			this.repo.update(command.toDomain());
		else
			// add 打刻ページレイアウト
			this.repo.insert(command.toDomain());
	}
}
