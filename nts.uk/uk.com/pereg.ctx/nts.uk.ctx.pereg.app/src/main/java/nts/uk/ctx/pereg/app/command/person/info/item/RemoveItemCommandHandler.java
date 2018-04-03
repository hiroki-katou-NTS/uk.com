package nts.uk.ctx.pereg.app.command.person.info.item;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pereg.dom.company.ICompanyRepo;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemoveItemCommandHandler extends CommandHandlerWithResult<RemoveItemCommand, String> {

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Inject
	private EmpInfoItemDataRepository empInfoRepo;

	@Inject
	private PerInfoItemDataRepository perItemRepo;

	@Inject
	private PersonInfoItemAuthRepository itemAuthRepo;

	@Inject
	private PerInfoInitValueSetItemRepository itemInitRepo;
	
	@Inject
	private ICompanyRepo companyRepo;

	@Override
	protected String handle(CommandHandlerContext<RemoveItemCommand> context) {
		RemoveItemCommand removeCommand = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		List<String> companyIdList = companyRepo.acquireAllCompany();

		PersonInfoItemDefinition itemDef = this.pernfoItemDefRep
				.getPerInfoItemDefById(removeCommand.getPerInfoItemDefId(), contractCd).orElse(null);

		if (itemDef == null || itemDef.getIsFixed() == IsFixed.FIXED) {
			return null;
		}

		String categoryId = itemDef.getPerInfoCategoryId();
		PersonInfoCategory category = this.perInfoCtgRep.getPerInfoCategory(categoryId, contractCd).orElse(null);
		if (category == null) {
			return null;
		}
		String categoryCode = category.getCategoryCode().v();

		List<String> categoryIdList = this.perInfoCtgRep.getPerInfoCtgIdList(companyIdList, categoryCode);

		String itemCode = itemDef.getItemCode().toString();
		if (this.isCheckData(itemCode, categoryIdList)) {
			throw new BusinessException("Msg_214");
		}

		categoryIdList.add(categoryId);
		this.pernfoItemDefRep.removePerInfoItemDef(categoryIdList, categoryCode, contractCd, itemCode);
		return null;
	}

	private boolean isCheckData(String itemCode, List<String> ctgLst) {
		if (itemAuthRepo.hasItemData(itemCode, ctgLst)) {
			return true;
		}
		if (itemInitRepo.hasItemData(itemCode, ctgLst)) {
			return true;
		}
		if (empInfoRepo.hasItemData(itemCode, ctgLst)) {
			return true;
		}
		if (perItemRepo.hasItemData(ctgLst, itemCode)) {
			return true;
		}
		return false;

	}
}
