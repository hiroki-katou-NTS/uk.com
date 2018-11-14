package nts.uk.ctx.exio.app.command.exo.outputitemsetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.execlog.StandardClassification;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddOutputItemService extends CommandHandler<List<AddOutputItemCommand>> {

	@Inject
	private StandardOutputItemRepository repository;
	
	@Inject
	private StandardOutputItemOrderRepository standardOutputItemOrderRepo;

	@Override
	protected void handle(CommandHandlerContext<List<AddOutputItemCommand>> context) {
		List<AddOutputItemCommand> listCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		int count = 1;
		for (AddOutputItemCommand addCommand : listCommand) {
			List<CategoryItem> listCategoryItem = new ArrayList<>(Arrays.asList(new CategoryItem(addCommand.getItemNo(), addCommand.getCategoryId(), null, 1)));
			StandardOutputItem domain = new StandardOutputItem(companyId, (String.format("%03d", (addCommand.getOutItemCd() + count))),
					addCommand.getCondSetCd(), addCommand.getOutItemName(), addCommand.getItemType(),
					listCategoryItem);
			count++;
			repository.add(domain);
		}
	}
	
	/**アルゴリズム「外部出力取得項目並順最大順序」を実行する -- liên quan đến bug: 102531 tạm thời pending*/
	private int getMaximumOrder(String cId, String userId, StandardClassification standardType, String conditionSettingCode) {
		int maximumOrder = 0;
		if(standardType == StandardClassification.STANDARD) {
			List<StandardOutputItemOrder> listStandard = standardOutputItemOrderRepo.getStandardOutputItemOrderByCidAndSetCd(cId, conditionSettingCode);
			for (StandardOutputItemOrder Item : listStandard) {
				if(Item.getDisplayOrder() > maximumOrder) {
					maximumOrder = Item.getDisplayOrder();
				}
			}
		}
		return maximumOrder;
	}
}
