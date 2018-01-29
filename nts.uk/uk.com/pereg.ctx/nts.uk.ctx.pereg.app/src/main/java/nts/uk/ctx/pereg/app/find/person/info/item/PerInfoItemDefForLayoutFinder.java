package nts.uk.ctx.pereg.app.find.person.info.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
	
	@Inject
	AffCompanyHistRepository achFinder;
	
	/***
	 * set value for item 
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
	public void setItemForLayout(PerInfoItemDefForLayoutDto itemForLayout, String empId, int ctgType, PersonInfoItemDefinition itemDef, String perInfoCd, 
			int dispOrder, boolean isCtgViewOnly, GeneralDate sDate, Map<Integer, List<ComboBoxObject>> mapListCombo){
		List<PerInfoItemDefForLayoutDto> lstChildren = getPerItemSet(empId,ctgType, itemDef.getItemTypeState(), perInfoCd, dispOrder, isCtgViewOnly, sDate, mapListCombo, itemForLayout.getActionRole());
		if(lstChildren.size() == 0 && itemDef.getItemTypeState().getItemType().value == 1) itemForLayout.setActionRole(ActionRole.HIDDEN);
		else {
			itemForLayout.setLstChildItemDef(lstChildren);
			itemForLayout.setCtgType(ctgType);
			if(isCtgViewOnly)
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
			List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
			itemForLayout.setSelectionItemRefTypes(selectionItemRefTypes);
			if(itemForLayout.getItemDefType() == 2){
				SingleItem singleItemDom = (SingleItem) itemDef.getItemTypeState();
				int dataTypeValue = singleItemDom.getDataTypeState().getDataTypeValue().value;
				if(dataTypeValue == 6){
					DataTypeStateDto dataTypeStateDto = createDataTypeStateDto(singleItemDom.getDataTypeState());
					SelectionItemDto selectionItemDto = (SelectionItemDto) dataTypeStateDto;
					int refType = selectionItemDto.getReferenceType().value;
					if(mapListCombo.containsKey(refType)) {
						itemForLayout.setLstComboxBoxValue(mapListCombo.get(refType));
					}else {					
						List<ComboBoxObject> lstCombo = getLstComboBoxValue(selectionItemDto, empId, sDate);			
						itemForLayout.setLstComboxBoxValue(lstCombo);
						mapListCombo.put(refType, lstCombo);
					}
					
				}
			}
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
	private List<PerInfoItemDefForLayoutDto> getPerItemSet(String empId, int ctgType, ItemTypeState item, String perInfoCd, int dispOrder, 
			boolean ctgIsViewOnly, GeneralDate sDate, Map<Integer, List<ComboBoxObject>> mapListCombo, ActionRole actionRole) {
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
			PerInfoItemDefForLayoutDto childItem;
			for (int i = 0; i < lstDomain.size(); i++) {
				childItem = new PerInfoItemDefForLayoutDto();
				childItem.setActionRole(actionRole);
				setItemForLayout(childItem, empId, ctgType, lstDomain.get(i), perInfoCd, dispOrder, ctgIsViewOnly, sDate, mapListCombo);
				lstResult.add(childItem);
			}
		}
		return lstResult;
	}
	
	
	
	public List<ComboBoxObject> getLstComboBoxValue(SelectionItemDto selectionItemDto, String empId, GeneralDate baseDate){
		
		GeneralDate standardDate = baseDate;
		if(baseDate == null) {
			AffCompanyHist affCompanyHist = achFinder.getAffCompanyHistoryOfEmployeeDesc(AppContexts.user().companyId(),
					empId);
			standardDate = affCompanyHist.getLstAffCompanyHistByEmployee().get(0).getLstAffCompanyHistoryItem().stream().collect(Collectors.toList()).get(0).start();
		}
		return comboBoxRetrieveFactory.getComboBox(selectionItemDto, empId, standardDate, true);
	}
}
