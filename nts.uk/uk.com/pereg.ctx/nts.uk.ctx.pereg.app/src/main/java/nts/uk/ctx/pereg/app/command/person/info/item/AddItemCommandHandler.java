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
import nts.uk.ctx.pereg.app.command.person.info.category.GetListCompanyOfContract;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;

@Stateless
public class AddItemCommandHandler extends CommandHandlerWithResult<AddItemCommand, String> {

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Inject
	private SelectionRepository selectionRepo;

	private final static String SPECIAL_ITEM_CODE = "IO";
	private final static int ITEM_CODE_DEFAUT_NUMBER = 0;

	@Override
	protected String handle(CommandHandlerContext<AddItemCommand> context) {
		String perInfoItemId = null;
		AddItemCommand addItemCommand = context.getCommand();
		String contractCd = PersonInfoItemDefinition.ROOT_CONTRACT_CODE;
		if (addItemCommand.getItemName().trim().equals("")) {
			throw new BusinessException(new RawErrorMessage("Msg_928"));
		}
		if (addItemCommand.getSingleItem().getDataType() == 6) {
			List<Selection> selection = new ArrayList<>();
			if (addItemCommand.getPersonEmployeeType() == 1) {
				selection = this.selectionRepo.getAllSelectionByHistoryId(
						addItemCommand.getSingleItem().getSelectionItemId(), GeneralDate.today(), 0);
			} else if (addItemCommand.getPersonEmployeeType() == 2) {
				selection = this.selectionRepo.getAllSelectionByHistoryId(
						addItemCommand.getSingleItem().getSelectionItemId(), GeneralDate.today(), 1);
			}
			if (selection == null || selection.size() == 0) {

				throw new BusinessException(new RawErrorMessage("Msg_587"));

			}
		} else if (addItemCommand.getSingleItem().getDataType() == 2) {
			SingleItemCommand number = addItemCommand.getSingleItem();
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

		}

		if (addItemCommand.getItemName().trim().equals("")) {
			throw new BusinessException(new RawErrorMessage(""));
		}

		// need perInfoItemDefId = ' ' becase sql oracle server can't query ''
		if (!this.pernfoItemDefRep.checkItemNameIsUnique(addItemCommand.getPerInfoCtgId(), addItemCommand.getItemName(),
				" ")) {
			throw new BusinessException(new RawErrorMessage("Msg_358"));
		}
		PersonInfoCategory perInfoCtg = this.perInfoCtgRep
				.getPerInfoCategory(addItemCommand.getPerInfoCtgId(), contractCd).orElse(null);
		if (perInfoCtg == null) {
			return null;
		}
		String categoryCd = perInfoCtg.getCategoryCode().v();
		String itemCodeLastes = this.pernfoItemDefRep.getPerInfoItemCodeLastest(contractCd, categoryCd);
		String newItemCode = createNewCode(itemCodeLastes, SPECIAL_ITEM_CODE);
		AddItemCommand newItemCommand = new AddItemCommand(context.getCommand().getPerInfoCtgId(), newItemCode, null,
				context.getCommand().getItemName(), context.getCommand().getSingleItem(),
				context.getCommand().getPersonEmployeeType());
		PersonInfoItemDefinition perInfoItemDef = MappingDtoToDomain.mappingFromDomaintoCommand(newItemCommand);
		perInfoItemId = this.pernfoItemDefRep.addPerInfoItemDefRoot(perInfoItemDef, contractCd, categoryCd);
		// get List PerInfoCtgId.
		List<String> companyIdList = GetListCompanyOfContract.LIST_COMPANY_OF_CONTRACT;
		List<String> ctgIdList = this.perInfoCtgRep.getPerInfoCtgIdList(companyIdList, categoryCd);
		if (ctgIdList == null || ctgIdList.isEmpty()) {
			return null;
		}
		this.pernfoItemDefRep.addPerInfoItemDefByCtgIdList(perInfoItemDef, ctgIdList);
		return perInfoItemId;
	}

	private String createNewCode(String codeLastest, String strSpecial) {
		String numberCode = String.valueOf(ITEM_CODE_DEFAUT_NUMBER + 1);
		if (codeLastest != null) {
			numberCode = String.valueOf(Integer.parseInt(codeLastest.substring(2, 7)) + 1);
		}
		for (int i = 5; i > 0; i--) {
			if (i == numberCode.length()) {
				break;
			}
			strSpecial += "0";
		}
		return strSpecial + numberCode;
	}

}
