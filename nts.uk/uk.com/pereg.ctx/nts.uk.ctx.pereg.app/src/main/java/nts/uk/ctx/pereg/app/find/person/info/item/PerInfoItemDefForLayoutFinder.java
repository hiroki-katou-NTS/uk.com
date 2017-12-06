package nts.uk.ctx.pereg.app.find.person.info.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetItem;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.pereg.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.pereg.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.pereg.dom.person.info.timepointitem.TimePointItem;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Stateless
public class PerInfoItemDefForLayoutFinder {
	
	@Inject
	private PersonInfoItemAuthRepository personInfoItemAuthRepository;
	
	@Inject
	I18NResourcesForUK ukResouce;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;
	
	/**
	 * create object from domain
	 * 
	 * @param empId
	 * @param itemDef
	 * @param perInfoCd
	 * @param dispOrder
	 * @return
	 */
	public PerInfoItemDefForLayoutDto createFromDomain(String empId, PersonInfoItemDefinition itemDef, String perInfoCd, int dispOrder){
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
		PerInfoItemDefForLayoutDto perInfoItemDefForLayoutDto = new PerInfoItemDefForLayoutDto();
		perInfoItemDefForLayoutDto.setItemDefId(itemDef.getPerInfoItemDefId());
		perInfoItemDefForLayoutDto.setPerInfoCtgId(itemDef.getPerInfoCategoryId());
		perInfoItemDefForLayoutDto.setPerInfoCtgCd(perInfoCd);
		perInfoItemDefForLayoutDto.setItemCode(itemDef.getItemCode().v());
		perInfoItemDefForLayoutDto.setItemName(itemDef.getItemName().v());
		perInfoItemDefForLayoutDto.setItemDefType(itemDef.getItemTypeState().getItemType().value);
		perInfoItemDefForLayoutDto.setLstChildItemDef(getPerItemSet(empId, itemDef.getItemTypeState(), perInfoCd, dispOrder));
		perInfoItemDefForLayoutDto.setIsRequired(itemDef.getIsRequired().value);
		perInfoItemDefForLayoutDto.setDispOrder(dispOrder);
		perInfoItemDefForLayoutDto.setActionRole(getActionRole(empId, itemDef.getPerInfoCategoryId(), itemDef.getPerInfoItemDefId()));
		perInfoItemDefForLayoutDto.setSelectionItemRefType(itemDef.getSelectionItemRefType());
		perInfoItemDefForLayoutDto.setItemTypeState(createItemTypeStateDto(itemDef.getItemTypeState()));
		perInfoItemDefForLayoutDto.setSelectionItemRefTypes(selectionItemRefTypes);
		return perInfoItemDefForLayoutDto;
	}
	
	public PerInfoItemDefForLayoutDto createFromItemDefDto(String empId, PerInfoItemDefDto itemDef, String perInfoCd, int dispOrder){
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
		PerInfoItemDefForLayoutDto perInfoItemDefForLayoutDto = new PerInfoItemDefForLayoutDto();
		perInfoItemDefForLayoutDto.setItemDefId(itemDef.getId());
		perInfoItemDefForLayoutDto.setPerInfoCtgId(itemDef.getPerInfoCtgId());
		perInfoItemDefForLayoutDto.setPerInfoCtgCd(perInfoCd);
		perInfoItemDefForLayoutDto.setItemCode(itemDef.getItemCode());
		perInfoItemDefForLayoutDto.setItemName(itemDef.getItemName());
		perInfoItemDefForLayoutDto.setItemDefType(itemDef.getItemTypeState().getItemType());
		perInfoItemDefForLayoutDto.setLstChildItemDef(getPerItemSet(empId, itemDef.getItemTypeState(), perInfoCd, dispOrder));
		perInfoItemDefForLayoutDto.setIsRequired(itemDef.getIsRequired());
		perInfoItemDefForLayoutDto.setDispOrder(dispOrder);
		perInfoItemDefForLayoutDto.setActionRole(getActionRole(empId, itemDef.getPerInfoCtgId(), itemDef.getId()));
		perInfoItemDefForLayoutDto.setSelectionItemRefType(itemDef.getSelectionItemRefType());
		perInfoItemDefForLayoutDto.setItemTypeState(itemDef.getItemTypeState());
		perInfoItemDefForLayoutDto.setSelectionItemRefTypes(selectionItemRefTypes);
		return perInfoItemDefForLayoutDto;
	}
	
	
	/**
	 * create ItemTypeStateDto
	 * 
	 * @param itemTypeState
	 * @return
	 */

	private static ItemTypeStateDto createItemTypeStateDto(ItemTypeState itemTypeState) {
		ItemType itemType = itemTypeState.getItemType();
		if (itemType == ItemType.SINGLE_ITEM) {
			SingleItem singleItemDom = (SingleItem) itemTypeState;
			return ItemTypeStateDto.createSingleItemDto(createDataTypeStateDto(singleItemDom.getDataTypeState()));
		} else {
			SetItem setItemDom = (SetItem) itemTypeState;
			return ItemTypeStateDto.createSetItemDto(setItemDom.getItems());
		}
	}

	/**
	 * create DataTypeStateDto
	 * 
	 * @param dataTypeState
	 * @return
	 */
	private static DataTypeStateDto createDataTypeStateDto(DataTypeState dataTypeState) {
		int dataTypeValue = dataTypeState.getDataTypeValue().value;
		switch (dataTypeValue) {
		case 1:
			StringItem strItem = (StringItem) dataTypeState;
			return DataTypeStateDto.createStringItemDto(strItem.getStringItemLength().v(),
					strItem.getStringItemType().value, strItem.getStringItemDataType().value);
		case 2:
			NumericItem numItem = (NumericItem) dataTypeState;
			BigDecimal numericItemMin = numItem.getNumericItemMin() != null ? numItem.getNumericItemMin().v() : null;
			BigDecimal numericItemMax = numItem.getNumericItemMax() != null ? numItem.getNumericItemMax().v() : null;
			return DataTypeStateDto.createNumericItemDto(numItem.getNumericItemMinus().value,
					numItem.getNumericItemAmount().value, numItem.getIntegerPart().v(), numItem.getDecimalPart().v(),
					numericItemMin, numericItemMax);
		case 3:
			DateItem dItem = (DateItem) dataTypeState;
			return DataTypeStateDto.createDateItemDto(dItem.getDateItemType().value);
		case 4:
			TimeItem tItem = (TimeItem) dataTypeState;
			return DataTypeStateDto.createTimeItemDto(tItem.getMax().v(), tItem.getMin().v());
		case 5:
			TimePointItem tPointItem = (TimePointItem) dataTypeState;
			return DataTypeStateDto.createTimePointItemDto(tPointItem.getTimePointItemMin().v(),
					tPointItem.getTimePointItemMax().v());
		case 6:
			SelectionItem sItem = (SelectionItem) dataTypeState;
			return DataTypeStateDto.createSelectionItemDto(sItem.getReferenceTypeState());
		default:
			return null;
		}
	}

	/**
	 * Set actionRole for each item
	 * 
	 * @Effect:
	 * 
	 * 			each item if read only then action role is read only else if
	 *          edit then action role is edit
	 * 
	 * @param empId
	 * @param ctgId
	 * @param perInfoItemId
	 * @return
	 */
	private ActionRole getActionRole(String empId, String ctgId, String perInfoItemId) {
		String loginEmpId = AppContexts.user().employeeId();
		//String roleId = AppContexts.user().roles().forPersonalInfo();
		String roleId = "99900000-0000-0000-0000-000000000001";
		boolean isSelfAuth = empId.equals(loginEmpId);
		Optional<PersonInfoItemAuth> perItemAuth =  personInfoItemAuthRepository.getItemDetai(roleId, ctgId, perInfoItemId);
		if(!perItemAuth.isPresent()) return ActionRole.HIDDEN;
		if (isSelfAuth)
			return personInfoItemAuthRepository.getItemDetai(roleId, ctgId, perInfoItemId).get()
					.getSelfAuth() != PersonInfoAuthType.UPDATE ? ActionRole.EDIT : ActionRole.VIEW_ONLY;
		else
			return personInfoItemAuthRepository.getItemDetai(roleId, ctgId, perInfoItemId).get()
					.getOtherAuth() != PersonInfoAuthType.UPDATE ? ActionRole.EDIT : ActionRole.VIEW_ONLY;
	}
	
	/**
	 * get per item set from domain
	 * 
	 * @param item
	 * @return
	 */
	private List<PerInfoItemDefForLayoutDto> getPerItemSet(String empId, ItemTypeState item, String perInfoCd, int dispOrder) {
		// 1 set - 2 Single
		List<PerInfoItemDefForLayoutDto> lstResult = new ArrayList<>();
		if (item.getItemType().value == 1) {
			String contractCode = AppContexts.user().contractCode();
			//String contractCode = "000000000001";
			
			// get itemId list of children
			SetItem setItem = (SetItem) item;
			// get children by itemId list
			List<PersonInfoItemDefinition> lstDomain = perInfoItemDefRepositoty
					.getPerInfoItemDefByListId(setItem.getItems(), contractCode);
			for (int i = 0; i < lstDomain.size(); i++)
				lstResult.add(createFromDomain(empId, lstDomain.get(i), perInfoCd, dispOrder));
		}
		return lstResult;
	}
	
	/**
	 * get per item set from dto
	 * 
	 * @param item
	 * @return
	 */
	private List<PerInfoItemDefForLayoutDto> getPerItemSet(String empId, ItemTypeStateDto item, String perInfoCd, int dispOrder) {
		// 1 set - 2 Single
		List<PerInfoItemDefForLayoutDto> lstResult = new ArrayList<>();
		if (item.getItemType() == 1) {
			// get itemId list of children
			SetItemDto setItem = (SetItemDto) item;
			// get children by itemId list
			List<PersonInfoItemDefinition> lstDomain = perInfoItemDefRepositoty
					.getPerInfoItemDefByListId(setItem.getItems(), AppContexts.user().contractCode());
			for (int i = 0; i < lstDomain.size(); i++)
				lstResult.add(createFromDomain(empId, lstDomain.get(i), perInfoCd, dispOrder));
		}
		return lstResult;
	}
}
