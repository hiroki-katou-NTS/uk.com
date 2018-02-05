package nts.uk.ctx.pereg.app.find.person.info.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionRadio;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetItem;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.pereg.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.pereg.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.pereg.dom.person.info.timepointitem.TimePointItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Stateless
public class PerInfoItemDefForLayoutFinder {

	@Inject
	I18NResourcesForUK ukResouce;

	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;

	@Inject
	private ComboBoxRetrieveFactory comboBoxRetrieveFactory;

	@Inject
	AffCompanyHistRepository achFinder;

	/***
	 * set value for item
	 * 
	 * @param itemForLayout
	 * @param empId
	 * @param ctgType
	 * @param itemDef
	 * @param perInfoCd
	 * @param dispOrder
	 * @param isCtgViewOnly
	 * @param sDate
	 * @param mapListCombo
	 */
	public void setItemForLayout(PerInfoItemDefForLayoutDto itemForLayout, String empId, int ctgType,
			PersonInfoItemDefinition itemDef, String perInfoCd, int dispOrder, boolean isCtgViewOnly, GeneralDate sDate, Map<Integer, Map<String,  List<ComboBoxObject>>> combobox) {
		List<PerInfoItemDefForLayoutDto> lstChildren = getPerItemSet(empId, ctgType, itemDef.getItemCode().v(), itemDef.getItemTypeState(),
				perInfoCd, dispOrder, isCtgViewOnly, sDate, itemForLayout.getActionRole(), combobox);
		if (lstChildren.size() == 0 && itemDef.getItemTypeState().getItemType().value == 1)
			itemForLayout.setActionRole(ActionRole.HIDDEN);
		else {
			itemForLayout.setLstChildItemDef(lstChildren);
			itemForLayout.setCtgType(ctgType);
			if (isCtgViewOnly)
				itemForLayout.setActionRole(ActionRole.VIEW_ONLY);
			itemForLayout.setId(itemDef.getPerInfoItemDefId());
			itemForLayout.setPerInfoCtgId(itemDef.getPerInfoCategoryId());
			itemForLayout.setPerInfoCtgCd(perInfoCd);
			itemForLayout.setItemCode(itemDef.getItemCode().v());
			itemForLayout.setItemName(itemDef.getItemName().v());
			itemForLayout.setItemDefType(itemDef.getItemTypeState().getItemType().value);

			itemForLayout.setIsRequired(itemDef.getIsRequired().value);
			itemForLayout.setDispOrder(dispOrder);

			itemForLayout.setSelectionItemRefType(itemDef.getSelectionItemRefType());
			itemForLayout.setItemTypeState(createItemTypeStateDto(itemDef.getItemTypeState()));
			List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class,
					ukResouce);
			itemForLayout.setSelectionItemRefTypes(selectionItemRefTypes);
			if (itemForLayout.getItemDefType() == 2) {
				SingleItem singleItemDom = (SingleItem) itemDef.getItemTypeState();
				int dataTypeValue = singleItemDom.getDataTypeState().getDataTypeValue().value;
				if (dataTypeValue == 6 || dataTypeValue == 7) {
					DataTypeStateDto dataTypeStateDto = createDataTypeStateDto(singleItemDom.getDataTypeState());
					SelectionItemDto selectionItemDto = (SelectionItemDto) dataTypeStateDto;
					
					List<ComboBoxObject> lstCombo = getCombo(selectionItemDto, combobox, empId, sDate, itemForLayout.getIsRequired() == 1);
					itemForLayout.setLstComboxBoxValue(lstCombo);

				}
			}
		}
	}
	
	private List<ComboBoxObject> getCombo(SelectionItemDto selectionItemDto, Map<Integer, Map<String, List<ComboBoxObject>>> combobox, String empId, GeneralDate sDate, boolean isRequired){
		List<Object> key = new ArrayList<Object>();
		int dataType = selectionItemDto.getReferenceType().value;
		key.add(dataType);
		switch(dataType) {
			case 1:
				MasterRefConditionDto master = (MasterRefConditionDto)selectionItemDto;
				key.add(master.getMasterType());
				break;
			case 2: 
				CodeNameRefTypeDto code = (CodeNameRefTypeDto)selectionItemDto;
				key.add(code.getTypeCode());
				break;
			case 3:
				EnumRefConditionDto enu = (EnumRefConditionDto)selectionItemDto;
				key.add(enu.getEnumName());
				break;
		}
		if(combobox.containsKey(key.get(0))) {
			Map<String, List<ComboBoxObject>> mapComboValue = combobox.get(key.get(0));
			if(mapComboValue.containsKey(key.get(1))) {
				return mapComboValue.get(key.get(1));
			}else {
				 List<ComboBoxObject> returnList =  getLstComboBoxValue(selectionItemDto, empId, sDate,
						isRequired);
				 mapComboValue.put((String) key.get(1), returnList);
				 combobox.put((Integer) key.get(0), mapComboValue);
				 return returnList;
			}
		}else {
			List<ComboBoxObject> returnList =  getLstComboBoxValue(selectionItemDto, empId, sDate,
					isRequired);
			Map<String, List<ComboBoxObject>> mapComboValue = new HashMap<>();
			mapComboValue.put((String) key.get(1), returnList);
			combobox.put((Integer) key.get(0), mapComboValue);
			return returnList;
		}
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
		case 7:
			SelectionRadio rItem = (SelectionRadio) dataTypeState;
			return DataTypeStateDto.createSelectionRadioDto(rItem.getReferenceTypeState());

		case 8:
			SelectionButton bItem = (SelectionButton) dataTypeState;
			return DataTypeStateDto.createSelectionButtonDto();
		default:
			return null;
		}
	}

	/**
	 * get per item set from domain
	 * 
	 * @param item
	 * @return
	 */
	private List<PerInfoItemDefForLayoutDto> getPerItemSet(String empId, int ctgType, String itemParentCode, ItemTypeState item,
			String perInfoCd, int dispOrder, boolean ctgIsViewOnly, GeneralDate sDate, ActionRole actionRole, Map<Integer, Map<String,  List<ComboBoxObject>>> combobox) {
		// 1 set - 2 Single
		List<PerInfoItemDefForLayoutDto> lstResult = new ArrayList<>();
		if (item.getItemType().value == 1) {
			String contractCode = AppContexts.user().contractCode();
			// String contractCode = "000000000001";

			// get itemId list of children
			SetItem setItem = (SetItem) item;
			List<String> items = setItem.getItems();
			if (items.size() == 0)
				return lstResult;
			// get children by itemId list
			List<PersonInfoItemDefinition> lstDomain = perInfoItemDefRepositoty.getPerInfoItemDefByListId(items,
					contractCode);
			PerInfoItemDefForLayoutDto childItem;
			for (int i = 0; i < lstDomain.size(); i++) {
				childItem = new PerInfoItemDefForLayoutDto();
				childItem.setActionRole(actionRole);
				childItem.setItemParentCode(itemParentCode);
				setItemForLayout(childItem, empId, ctgType, lstDomain.get(i), perInfoCd, dispOrder, ctgIsViewOnly,
						sDate,combobox) ;
				lstResult.add(childItem);
			}
		}
		return lstResult;
	}

	public List<ComboBoxObject> getLstComboBoxValue(SelectionItemDto selectionItemDto, String empId,
			GeneralDate baseDate, boolean isRequired) {

		GeneralDate standardDate = baseDate;
		if (baseDate == null) {
			AffCompanyHist affCompanyHist = achFinder.getAffCompanyHistoryOfEmployeeDesc(AppContexts.user().companyId(),
					empId);
			standardDate = affCompanyHist.getLstAffCompanyHistByEmployee().get(0).getLstAffCompanyHistoryItem().stream()
					.collect(Collectors.toList()).get(0).start();
		}
		return comboBoxRetrieveFactory.getComboBox(selectionItemDto, empId, standardDate, isRequired);
	}
}
