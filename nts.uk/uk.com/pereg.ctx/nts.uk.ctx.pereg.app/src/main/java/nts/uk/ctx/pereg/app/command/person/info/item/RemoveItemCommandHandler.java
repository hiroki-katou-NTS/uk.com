package nts.uk.ctx.pereg.app.command.person.info.item;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pereg.app.command.person.info.category.GetListCompanyOfContract;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;

@Stateless
public class RemoveItemCommandHandler extends CommandHandlerWithResult<RemoveItemCommand, String> {

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

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
		if (checkQuantityItemData()) {
			return "Msg_214";
		}
		PersonInfoCategory category = this.perInfoCtgRep.getPerInfoCategory(itemDef.getPerInfoCategoryId(), contractCd)
				.orElse(null);
		if (category == null) {
			return null;
		}
		List<String> perInfoCtgIds = this.perInfoCtgRep.getPerInfoCtgIdList(companyIdList,
				category.getCategoryCode().v());
		perInfoCtgIds.add(itemDef.getPerInfoCategoryId());
		this.pernfoItemDefRep.removePerInfoItemDefRoot(perInfoCtgIds, category.getCategoryCode().v(), contractCd,
				itemDef.getItemCode().v());
		return null;
	}

	private boolean checkQuantityItemData() {
		// TODO-TuongVC: sau nay khi lam den domain [PersonInfoItemData] can
		// hoan thien not
		/*
		 * activity lien quan: [PersonInfoItemData] ở đây lấy như thế nào nhỉ
		 * Đứclần giải thích tiếp theo sẽ có giải thích về bảng này anh cứ viết
		 * method check để return true là mặc định sau khi có bảng rồi thì viết
		 * logic sau cũng được
		 */
		// Hiện tại trả về true ~ số lượng > 1
		return false;
	}

}
