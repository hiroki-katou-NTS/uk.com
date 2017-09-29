package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCategory;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalOrder;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateHoriTotalCategoryCommandHandler extends CommandHandler<UpdateHoriTotalCategoryCommand>{
	@Inject
	private HoriTotalCategoryRepository	horiRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateHoriTotalCategoryCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<HoriTotalCategory> horiOld = horiRep.findCateByCode(companyId, context.getCommand().getCategoryCode());
		List<TotalEvalOrder> totalEvalOrders = new ArrayList<>();
		if(context.getCommand().getTotalEvalOrders() != null){
			totalEvalOrders = context.getCommand().getTotalEvalOrders().stream()
									.map(x -> x.toDomainOrder(companyId, context.getCommand().getCategoryCode()))
									.collect(Collectors.toList());
		}
	}

}
