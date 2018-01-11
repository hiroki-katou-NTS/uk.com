package nts.uk.ctx.pereg.app.find.person.info.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
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
import nts.uk.shr.pereg.app.ComboBoxObject;

@Stateless
public class PerInfoItemDefForLayoutFinder {
	
	@Inject
	private PersonInfoItemAuthRepository personInfoItemAuthRepository;
	
	@Inject
	I18NResourcesForUK ukResouce;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;
	
	@Inject 
	private ComboBoxRetrieveFactory comboBoxRetrieveFactory;
	
	
	/**
	 * create object from domain
	 * 
	 * @param empId
	 * @param itemDef
	 * @param perInfoCd
	 * @param dispOrder
	 * @return
	 */
	public PerInfoItemDefForLayoutDto createFromDomain(String empId, int ctgType, PersonInfoItemDefinition itemDef, String perInfoCd, int dispOrder, String roleId){
		ActionRole actionRole = getActionRole(empId, itemDef.getPerInfoCategoryId(), itemDef.getPerInfoItemDefId(), roleId);
		if(actionRole == ActionRole.HIDDEN) return null;
		List<PerInfoItemDefForLayoutDto> lstChildren = getPerItemSet(empId,ctgType, itemDef.getItemTypeState(), perInfoCd, dispOrder, roleId);
		if(lstChildren.size() == 0 && itemDef.getItemTypeState().getItemType().value == 1) return null;
		PerInfoItemDefForLayoutDto perInfoItemDefForLayoutDto = new PerInfoItemDefForLayoutDto();
		perInfoItemDefForLayoutDto.setLstChildItemDef(lstChildren);
		perInfoItemDefForLayoutDto.setCtgType(ctgType);
		perInfoItemDefForLayoutDto.setActionRole(actionRole);
		perInfoItemDefForLayoutDto.setId(itemDef.getPerInfoItemDefId());
		perInfoItemDefForLayoutDto.setPerInfoCtgId(itemDef.getPerInfoCategoryId());
		perInfoItemDefForLayoutDto.setPerInfoCtgCd(perInfoCd);
		perInfoItemDefForLayoutDto.setItemCode(itemDef.getItemCode().v());
		perInfoItemDefForLayoutDto.setItemName(itemDef.getItemName().v());
		perInfoItemDefForLayoutDto.setItemDefType(itemDef.getItemTypeState().getItemType().value);
		
		perInfoItemDefForLayoutDto.setIsRequired(itemDef.getIsRequired().value);
		perInfoItemDefForLayoutDto.setDispOrder(dispOrder);
		
		perInfoItemDefForLayoutDto.setSelectionItemRefType(itemDef.getSelectionItemRefType());
		perInfoItemDefForLayoutDto.setItemTypeState(createItemTypeStateDto(itemDef.getItemTypeState()));
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
		perInfoItemDefForLayoutDto.setSelectionItemRefTypes(selectionItemRefTypes);
		if(perInfoItemDefForLayoutDto.getItemDefType() == 2){
			SingleItem singleItemDom = (SingleItem) itemDef.getItemTypeState();
			int dataTypeValue = singleItemDom.getDataTypeState().getDataTypeValue().value;
			if(dataTypeValue == 6){
				DataTypeStateDto dataTypeStateDto = createDataTypeStateDto(singleItemDom.getDataTypeState());
				perInfoItemDefForLayoutDto.setLstComboxBoxValue(getLstComboBoxValue(dataTypeStateDto, empId));
			}
		}
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
	private ActionRole getActionRole(String empId, String ctgId, String perInfoItemId, String roleId) {
		String loginEmpId = AppContexts.user().employeeId();
		//String roleId = AppContexts.user().roles().forPersonalInfo();
		boolean isSelfAuth = empId.equals(loginEmpId);
		Optional<PersonInfoItemAuth> perItemAuth =  personInfoItemAuthRepository.getItemDetai(roleId, ctgId, perInfoItemId);
		if(!perItemAuth.isPresent()) return ActionRole.HIDDEN;
		if (isSelfAuth)
			return personInfoItemAuthRepository.getItemDetai(roleId, ctgId, perInfoItemId).get()
					.getSelfAuth() == PersonInfoAuthType.UPDATE ? ActionRole.EDIT : ActionRole.VIEW_ONLY;
		else
			return personInfoItemAuthRepository.getItemDetai(roleId, ctgId, perInfoItemId).get()
					.getOtherAuth() == PersonInfoAuthType.UPDATE ? ActionRole.EDIT : ActionRole.VIEW_ONLY;
	}
	
	/**
	 * get per item set from domain
	 * 
	 * @param item
	 * @return
	 */
	private List<PerInfoItemDefForLayoutDto> getPerItemSet(String empId, int ctgType, ItemTypeState item, String perInfoCd, int dispOrder, String roleId) {
		// 1 set - 2 Single
		List<PerInfoItemDefForLayoutDto> lstResult = new ArrayList<>();
		if (item.getItemType().value == 1) {
			String contractCode = AppContexts.user().contractCode();
			//String contractCode = "000000000001";
			
			// get itemId list of children
			SetItem setItem = (SetItem) item;
			List<String> items = setItem.getItems();
			if(items.size() == 0) return lstResult;
			// get children by itemId list
			List<PersonInfoItemDefinition> lstDomain = perInfoItemDefRepositoty
					.getPerInfoItemDefByListId(items, contractCode);
			for (int i = 0; i < lstDomain.size(); i++)
				lstResult.add(createFromDomain(empId, ctgType, lstDomain.get(i), perInfoCd, dispOrder, roleId));
		}
		return lstResult;
	}
	
	
	
	public List<ComboBoxObject> getLstComboBoxValue(DataTypeStateDto dataTypeStateDto, String empId){
		SelectionItemDto selectionItemDto = (SelectionItemDto) dataTypeStateDto;
		return comboBoxRetrieveFactory.getComboBox(selectionItemDto, empId, GeneralDate.today(), true);
	}
}
