package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriCalDaysSet;
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
		UpdateHoriTotalCategoryCommand data = context.getCommand();
		Optional<HoriTotalCategory> horiOld = horiRep.findCateByCode(companyId, context.getCommand().getCategoryCode());
		List<TotalEvalOrder> totalEvalOrders = new ArrayList<>();
		HoriCalDaysSet horiCalDaysSet =null;
		if(!horiOld.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		if(data.getTotalEvalOrders() != null){
			totalEvalOrders = context.getCommand().getTotalEvalOrders().stream()
									.map(x -> x.toDomainOrder(companyId, context.getCommand().getCategoryCode()))
									.collect(Collectors.toList());
		}
		if(data.getHoriCalDaysSet() != null){
			horiCalDaysSet = data.getHoriCalDaysSet()
								.toDomainCalSet(companyId, context.getCommand().getCategoryCode());
		}
		HoriTotalCategory horiNew = HoriTotalCategory.createFromJavaType(companyId, data.getCategoryCode(), data.getCategoryName(), data.getMemo(), horiCalDaysSet, totalEvalOrders);
		horiNew.validate();
		horiRep.updateCate(horiNew);
	}

}
