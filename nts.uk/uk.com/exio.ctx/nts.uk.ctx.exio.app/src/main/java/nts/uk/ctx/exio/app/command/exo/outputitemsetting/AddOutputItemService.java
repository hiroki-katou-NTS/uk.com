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
		String userId = AppContexts.user().userId();
		int count = 1;
		for (AddOutputItemCommand addCommand : listCommand) {
			List<CategoryItem> listCategoryItem = new ArrayList<>(Arrays.asList(new CategoryItem(addCommand.getItemNo(), addCommand.getCategoryId(), null, 1)));
			StandardOutputItem domain = new StandardOutputItem(
					companyId, 
					(String.format("%04d", (addCommand.getOutItemCd() + count))),
					addCommand.getCondSetCd(), 
					addCommand.getOutItemName(), 
					addCommand.getItemType(),
					listCategoryItem);
			repository.add(domain);
			int order = getMaximumOrder(companyId, userId, StandardClassification.STANDARD, addCommand.getCondSetCd());
			addItemOrder(
					companyId, 
					addCommand.getCondSetCd(), 
					String.format("%04d", (addCommand.getOutItemCd() + count)), 
					order+1, 
					StandardClassification.STANDARD);
			count++;
		}
	}
	
	/**アルゴリズム「外部出力取得項目並順最大順序」を実行する */
	private int getMaximumOrder(String cId, String userId, StandardClassification standardType, String conditionSettingCode) {
		int maximumOrder = 0;
		if(standardType == StandardClassification.STANDARD) {
			maximumOrder = standardOutputItemOrderRepo.getMaxOrder(cId, conditionSettingCode);
		}else if(standardType == StandardClassification.USER){
			/**ドメインモデル「出力項目並び順(ユーザ)」を取得する  --  pending*/
		}
		return maximumOrder;
	}
	
	/**アルゴリズム「外部出力登録出力項目並び順更新」を実行する */
	private void addItemOrder(String cId, String condSetCd, String outItemCd, int order, StandardClassification standardType) {
		if(standardType == StandardClassification.STANDARD) {
			StandardOutputItemOrder item = new StandardOutputItemOrder(cId, outItemCd, condSetCd, order);
			standardOutputItemOrderRepo.add(item);
		}else if(standardType == StandardClassification.USER){
			/**アルゴリズム「外部出力登録出力項目並び順_ユーザ」を実行する  --  pending*/
		}
	}
}
