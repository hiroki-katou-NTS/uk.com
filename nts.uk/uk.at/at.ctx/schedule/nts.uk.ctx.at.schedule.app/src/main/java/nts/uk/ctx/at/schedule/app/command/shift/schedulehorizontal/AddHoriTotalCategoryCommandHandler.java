package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCategory;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalOrder;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.shr.com.context.AppContexts;
@Transactional
@Stateless
public class AddHoriTotalCategoryCommandHandler extends CommandHandler<AddHoriTotalCategoryCommand> {
	@Inject
	private HoriTotalCategoryRepository horiRep;

	@Override
	protected void handle(CommandHandlerContext<AddHoriTotalCategoryCommand> context) {
		AddHoriTotalCategoryCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		List<TotalEvalOrder> totalEvalOrders = new ArrayList<>();
//		List<HoriTotalCNTSet> horiCntSets = new ArrayList<>();
		Optional<HoriTotalCategory> horiOld = horiRep.findCateByCode(companyId, data.getCategoryCode());
		// check duplicate code
		if(horiOld.isPresent()){
			throw new BusinessException("Msg_3");
		}
		// check list 集計項目一覧 exsisted or not
		if(data.getTotalEvalOrders() == null || data.getTotalEvalOrders().size() == 0){
			throw new BusinessException("Msg_363");
		}
		// get total eval order list
		if(data.getTotalEvalOrders() != null){
			totalEvalOrders = data.getTotalEvalOrders().stream()
					.map(x -> x.toDomainOrder(companyId, data.getCategoryCode()))
					.collect(Collectors.toList());
		}
		HoriTotalCategory hori = HoriTotalCategory.createFromJavaType(companyId, 
																	data.getCategoryCode(), 
																	data.getCategoryName(), 
																	data.getMemo(), 
																	totalEvalOrders);
		hori.validate();
		horiRep.insertCate(hori);
	}
}
