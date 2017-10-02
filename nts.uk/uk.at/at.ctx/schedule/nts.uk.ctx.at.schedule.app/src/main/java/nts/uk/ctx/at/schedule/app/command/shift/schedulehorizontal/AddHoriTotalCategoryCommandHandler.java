package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.swing.text.html.Option;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCategory;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalOrder;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AddHoriTotalCategoryCommandHandler extends CommandHandler<AddHoriTotalCategoryCommand> {
	@Inject
	private HoriTotalCategoryRepository horiRep;

	@Override
	protected void handle(CommandHandlerContext<AddHoriTotalCategoryCommand> context) {
		String companyId = AppContexts.user().companyId();
		List<TotalEvalOrder> totalEvalOrders = null;
		if(context.getCommand().getTotalEvalOrders() != null){
			totalEvalOrders = context.getCommand().getTotalEvalOrders()
								.stream()
								.map(x -> x.toDomainOrder(companyId, context.getCommand().getCategoryCode()))
								.collect(Collectors.toList());
		}
		HoriTotalCategory hori = HoriTotalCategory.createFromJavaType(companyId, 
																	context.getCommand().getCategoryCode(), 
																	context.getCommand().getCategoryName(), 
																	context.getCommand().getMemo(), 
																	totalEvalOrders);
		Optional<HoriTotalCategory> horiOld = horiRep.findCateByCode(companyId, 
																	context.getCommand().getCategoryCode());
		if(horiOld.isPresent()){
			throw new RuntimeException("入力したコードは、既に登録されています。");
		}else{
			horiRep.insertCate(hori);
		}
	}
}
