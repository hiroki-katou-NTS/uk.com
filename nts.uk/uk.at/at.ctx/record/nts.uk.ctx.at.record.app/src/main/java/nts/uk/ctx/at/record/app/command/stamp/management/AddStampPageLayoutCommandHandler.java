package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageLayout;
import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class AddStampPageLayoutCommandHandler extends CommandHandler<StampPageLayoutCommand>{

	@Inject
	private StampSetPerRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<StampPageLayoutCommand> context) {
		StampPageLayoutCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<StampPageLayout> stampPage = repo.getStampSetPage(companyId, command.getPageNo());
		if(stampPage.isPresent()){
			// update 打刻ページレイアウト
			this.repo.updatePage(command.toDomain());
		}else{
			// add 打刻ページレイアウト
			this.repo.insertPage(command.toDomain());
		}
	}
}
