package command.person.info.category;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;

@Stateless
public class UpdatePerInfoCtgCommandHandler extends CommandHandlerWithResult<UpdatePerInfoCtgCommand, String> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Override
	protected String handle(CommandHandlerContext<UpdatePerInfoCtgCommand> context) {
		UpdatePerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		if (!this.perInfoCtgRep.checkCtgNameIsUnique(PersonInfoCategory.ROOT_COMPANY_ID,
				perInfoCtgCommand.getCategoryName())) {
			throw new BusinessException(new RawErrorMessage("Msg_215"));
		}
		PersonInfoCategory perInfoCtg = this.perInfoCtgRep
				.getPerInfoCategory(perInfoCtgCommand.getId(), PersonInfoItemDefinition.ROOT_CONTRACT_CODE)
				.orElse(null);
		if (perInfoCtg == null || perInfoCtg.getIsFixed() == IsFixed.FIXED) {
			return null;
		}
		perInfoCtg.setCategoryName(perInfoCtgCommand.getCategoryName());
		if (checkQuantityCtgData()) {
			this.perInfoCtgRep.updatePerInfoCtg(perInfoCtg, PersonInfoItemDefinition.ROOT_CONTRACT_CODE);
			return "Msg_232";
		}
		perInfoCtg.setCategoryType(perInfoCtgCommand.getCategoryType());
		this.perInfoCtgRep.updatePerInfoCtg(perInfoCtg, PersonInfoItemDefinition.ROOT_CONTRACT_CODE);
		return null;
			
	}

	private boolean checkQuantityCtgData() {
		// TODO-TuongVC: sau nay khi lam den domain PersonInfoCategoryData can
		// hoan thien not
		/*
		 * activity lien quan: カテゴリを更新する [PersonInfoCategoryData] ở đây lấy như
		 * thế nào nhỉ Đứclần giải thích tiếp theo sẽ có giải thích về bảng này
		 * anh cứ viết method check để return true là mặc định sau khi có bảng
		 * rồi thì viết logic sau cũng được
		 */
		return true;

	}

}
