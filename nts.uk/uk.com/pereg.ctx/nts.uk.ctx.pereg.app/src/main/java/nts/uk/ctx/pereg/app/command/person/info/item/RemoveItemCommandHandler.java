package nts.uk.ctx.pereg.app.command.person.info.item;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pereg.app.command.person.info.category.GetListCompanyOfContract;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;

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

	@Override
	protected String handle(CommandHandlerContext<RemoveItemCommand> context) {
		RemoveItemCommand removeCommand = context.getCommand();
		String contractCd = PersonInfoItemDefinition.ROOT_CONTRACT_CODE;
		List<String> companyIdList = GetListCompanyOfContract.LIST_COMPANY_OF_CONTRACT;
		PersonInfoItemDefinition itemDef = this.pernfoItemDefRep
				.getPerInfoItemDefById(removeCommand.getPerInfoItemDefId(), contractCd).orElse(null);

		if (itemDef == null || itemDef.getIsFixed() == IsFixed.FIXED) {
			return null;
		}

		PersonInfoCategory category = this.perInfoCtgRep.getPerInfoCategory(itemDef.getPerInfoCategoryId(), contractCd)
				.orElse(null);
		if (category == null) {
			return null;
		}
		List<String> perInfoCtgIds = this.perInfoCtgRep.getPerInfoCtgIdList(companyIdList,
				category.getCategoryCode().v());
		if (this.empInfoRepo.getAllInfoItem(itemDef.getItemCode().toString(), perInfoCtgIds) || this.perItemRepo.isExitedItem(perInfoCtgIds, itemDef.getItemCode().toString())) {
			throw  new BusinessException("Msg_214");
		}
		perInfoCtgIds.add(itemDef.getPerInfoCategoryId());
		this.pernfoItemDefRep.removePerInfoItemDefRoot(perInfoCtgIds, category.getCategoryCode().v(), contractCd,
				itemDef.getItemCode().v());
		return null;
	}

}
