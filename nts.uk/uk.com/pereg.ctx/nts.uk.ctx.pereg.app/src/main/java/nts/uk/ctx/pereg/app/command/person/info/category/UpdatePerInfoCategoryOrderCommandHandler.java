package nts.uk.ctx.pereg.app.command.person.info.category;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCtgOrder;
import nts.uk.shr.com.context.AppContexts;

/**
 * The class UpdatePerInfoCategoryOrderCommandHandler
 * 
 * @author lanlt
 *
 */
@Stateless
public class UpdatePerInfoCategoryOrderCommandHandler extends CommandHandler<List<UpdatePerInfoCategoryOrderCommand>> {
	@Inject
	private PerInfoCtgByCompanyRepositoty perInfoCtgRepo;

	@Override
	protected void handle(CommandHandlerContext<List<UpdatePerInfoCategoryOrderCommand>> context) {
		List<UpdatePerInfoCategoryOrderCommand> update = context.getCommand();
		String companyId = AppContexts.user().companyId();
		if (update.size() == 0) {
			return;
		}
		Map<String, Integer> map = update.stream().collect(Collectors.toMap(x -> x.getId(), x -> x.getOrder()));

		// get all from database
		List<PersonInfoCtgOrder> orders = this.perInfoCtgRepo.getOrderList(companyId);
		// update order
		orders.forEach(order -> order.updateDisOrder(map.get(order.getCategoryId())));
		this.perInfoCtgRepo.updatePerCtgOrder(orders);
	}

}
