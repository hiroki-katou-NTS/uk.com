package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCategory;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.shr.com.context.AppContexts;
@Transactional
@Stateless
public class DeleteHoriTotalCategoryCommandHandler extends CommandHandler<DeleteHoriTotalCategoryCommand>{
	@Inject
	private HoriTotalCategoryRepository horiRep;

	@Override
	protected void handle(CommandHandlerContext<DeleteHoriTotalCategoryCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<HoriTotalCategory> horiOld = horiRep.findCateByCode(companyId, context.getCommand().getCategoryCode());
		if(!horiOld.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		horiRep.deleteCate(companyId, context.getCommand().getCategoryCode());
	}
	
}
