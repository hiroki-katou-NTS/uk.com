package nts.uk.ctx.pereg.app.command.person.info.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.command.person.info.category.CheckNameSpace;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;

@Stateless
public class UpdateItemCommandHandler extends CommandHandlerWithResult<UpdateItemCommand, String> {

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private SelectionRepository selectionRepo;

	@Override
	protected String handle(CommandHandlerContext<UpdateItemCommand> context) {
		UpdateItemCommand itemCommand = context.getCommand();
		// String mess = "Msg_233";
		String contractCd = PersonInfoItemDefinition.ROOT_CONTRACT_CODE;
		String itemName = itemCommand.getItemName();
		if (CheckNameSpace.checkName(itemName)){
			throw new BusinessException(new RawErrorMessage("Msg_928"));
		}
		if (itemCommand.getSingleItem().getDataType() == 6) {

			List<Selection> selection = new ArrayList<>();
			if (itemCommand.getPersonEmployeeType() == 1) {
				selection = this.selectionRepo.getAllSelectionByHistoryId(
						itemCommand.getSingleItem().getSelectionItemId(), GeneralDate.today(), 0);
			} else if (itemCommand.getPersonEmployeeType() == 2) {
				selection = this.selectionRepo.getAllSelectionByHistoryId(
						itemCommand.getSingleItem().getSelectionItemId(), GeneralDate.today(), 1);
			}
			if (selection == null || selection.size() == 0) {

				throw new BusinessException(new RawErrorMessage("Msg_587"));

			}

		} else if (itemCommand.getSingleItem().getDataType() == 2) {

			SingleItemCommand number = itemCommand.getSingleItem();
			BigDecimal max = new BigDecimal(Math.pow(10, number.getIntegerPart().doubleValue())
					- Math.pow(10, number.getDecimalPart() == null ? 0 : -number.getDecimalPart().intValue()));
			BigDecimal min = new BigDecimal(0);
			// if (number.getNumericItemMin() != null && number.getNumericItemMax() != null)
			// {
			if (number.getNumericItemMinus() == 0) {
				if (number.getNumericItemMin() != null) {
					if (number.getNumericItemMin().compareTo(min) < 0) {

						throw new BusinessException(new RawErrorMessage("Msg_596"));

					}

				}

				if (number.getNumericItemMax() != null) {
					if (number.getNumericItemMax().compareTo(min) < 0) {
						throw new BusinessException(new RawErrorMessage("Msg_596"));
					}

				}

			} else {
				min = max.negate();
			}

			if (number.getNumericItemMin() != null && number.getNumericItemMax() != null) {
				if (number.getNumericItemMin().compareTo(number.getNumericItemMax()) > 0) {
					throw new BusinessException(new RawErrorMessage("Msg_598"));
				}
			}

			if (number.getNumericItemMin() != null) {

				if (number.getNumericItemMin().compareTo(max) > 0 || number.getNumericItemMin().compareTo(min) < 0) {
					throw new BusinessException(new RawErrorMessage("Msg_599"));
				}
			}

			if (number.getNumericItemMax() != null) {

				if (number.getNumericItemMax().compareTo(max) > 0 || number.getNumericItemMax().compareTo(min) < 0) {
					throw new BusinessException(new RawErrorMessage("Msg_600"));
				}
			}

			// }

		}

		if (itemCommand.getItemName().trim().equals("")) {
			throw new BusinessException("");
		}
		if (!this.pernfoItemDefRep.checkItemNameIsUnique(itemCommand.getPerInfoCtgId(), itemCommand.getItemName(),
				itemCommand.getPerInfoItemDefId())) {
			throw new BusinessException(new RawErrorMessage("Msg_358"));
		}
		PersonInfoItemDefinition oldItem = this.pernfoItemDefRep
				.getPerInfoItemDefById(itemCommand.getPerInfoItemDefId(), contractCd).orElse(null);
		if (oldItem == null || oldItem.getIsFixed() == IsFixed.FIXED) {
			return null;
		}
		oldItem.setItemName(itemCommand.getItemName());
		PersonInfoItemDefinition newItem = MappingDtoToDomain.mappingFromDomaintoCommandForUpdate(itemCommand, oldItem);
		// if (!checkQuantityItemData()) {
		// newItem =
		// MappingDtoToDomain.mappingFromDomaintoCommandForUpdate(itemCommand,
		// oldItem);
		// mess = null;
		// }
		this.pernfoItemDefRep.updatePerInfoItemDefRoot(newItem, contractCd);
		return null;
	}

	// private boolean checkQuantityItemData() {
	// // TODO-TuongVC: sau nay khi lam den domain [PersonInfoItemData] can
	// // hoan thien not
	// /*
	// * activity lien quan: [PersonInfoItemData] ở đây lấy như thế nào nhỉ
	// * Đứclần giải thích tiếp theo sẽ có giải thích về bảng này anh cứ viết
	// * method check để return true là mặc định sau khi có bảng rồi thì viết
	// * logic sau cũng được
	// */
	// // Hiện tại trả về true ~ số lượng > 1
	// return true;
	// }

}
