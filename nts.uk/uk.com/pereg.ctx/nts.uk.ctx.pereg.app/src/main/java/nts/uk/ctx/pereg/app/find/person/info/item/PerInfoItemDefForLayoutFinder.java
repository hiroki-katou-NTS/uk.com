package nts.uk.ctx.pereg.app.find.person.info.item;

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
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.IsRequired;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Stateless
public class PerInfoItemDefForLayoutFinder {

	@Inject
	I18NResourcesForUK ukResouce;

	@Inject
	private ComboBoxRetrieveFactory comboBoxRetrieveFactory;

	@Inject
	AffCompanyHistRepository achFinder;

	public List<PerInfoItemDefForLayoutDto> getChildrenItems(List<PersonInfoItemDefinition> fullItemDefinitionList,
			PersonInfoCategory category, PersonInfoItemDefinition parentItem, String empId, int dispOrder,
			boolean isCtgViewOnly, GeneralDate sDate, Map<String, Map<Boolean, List<ComboBoxObject>>> combobox,
			ActionRole role) {

		List<PerInfoItemDefForLayoutDto> childLayoutitems = new ArrayList<>();

		// get children by itemId list
		ItemType itemType = parentItem.getItemTypeState().getItemType();
		if (itemType == ItemType.SET_ITEM || itemType == ItemType.TABLE_ITEM) {

			List<PersonInfoItemDefinition> childItemDefinitionList = getChildItems(fullItemDefinitionList,
					parentItem.getItemCode().v());

			for (int i = 0; i < childItemDefinitionList.size(); i++) {

				PersonInfoItemDefinition childItemDefinition = childItemDefinitionList.get(i);

				PerInfoItemDefForLayoutDto itemForLayout = createItemLayoutDto(category, childItemDefinition, dispOrder,
						role, isCtgViewOnly, combobox, empId, sDate);

				childLayoutitems.add(itemForLayout);

				List<PerInfoItemDefForLayoutDto> grandChildItemForLayouts = getChildrenItems(fullItemDefinitionList,
						category, childItemDefinition, empId, i, isCtgViewOnly, sDate, combobox, role);
				childLayoutitems.addAll(grandChildItemForLayouts);

			}

		}

		return childLayoutitems;
	}

	private List<PersonInfoItemDefinition> getChildItems(List<PersonInfoItemDefinition> fullItemDefinitionList,
			String itemCode) {
		return fullItemDefinitionList.stream()
				.filter(itemDef -> itemDef.getItemParentCode() != null && itemDef.getItemParentCode().equals(itemCode))
				.collect(Collectors.toList());
	}

	public PerInfoItemDefForLayoutDto createItemLayoutDto(PersonInfoCategory category,
			PersonInfoItemDefinition itemDefinition, int dispOrder, ActionRole role, boolean isCtgViewOnly,
			Map<String, Map<Boolean, List<ComboBoxObject>>> combobox, String employeeId, GeneralDate startDate) {
		PerInfoItemDefForLayoutDto itemForLayout = new PerInfoItemDefForLayoutDto(itemDefinition);

		itemForLayout.setDispOrder(dispOrder);
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
		itemForLayout.setSelectionItemRefTypes(selectionItemRefTypes);

		itemForLayout.setPerInfoCtgCd(category.getCategoryCode().v());
		itemForLayout.setCtgType(category.getCategoryType().value);
		itemForLayout.setActionRole(role);
		if (isCtgViewOnly)
			itemForLayout.setActionRole(ActionRole.VIEW_ONLY);

		// get combo-box list
		if (itemForLayout.getItemTypeState().getItemType() == ItemType.SINGLE_ITEM.value) {
			SingleItem singleItemDom = (SingleItem) itemDefinition.getItemTypeState();
			DataTypeValue dataTypeValue = singleItemDom.getDataTypeState().getDataTypeValue();
			if (dataTypeValue == DataTypeValue.SELECTION || dataTypeValue == DataTypeValue.SELECTION_RADIO
					|| dataTypeValue == DataTypeValue.SELECTION_BUTTON) {
				DataTypeStateDto dataTypeStateDto = DataTypeStateDto.createDto(singleItemDom.getDataTypeState());
				SelectionItemDto selectionItemDto = (SelectionItemDto) dataTypeStateDto;

				boolean isDataType6 = dataTypeValue == DataTypeValue.SELECTION;
				List<ComboBoxObject> lstCombo = getCombo(selectionItemDto, combobox, employeeId, startDate,
						itemDefinition.getIsRequired() == IsRequired.REQUIRED, category.getPersonEmployeeType(),
						isDataType6, category.getCategoryCode().v());
				itemForLayout.setLstComboxBoxValue(lstCombo);

			}
		}
		return itemForLayout;
	}

	private List<ComboBoxObject> getCombo(SelectionItemDto selectionItemDto,
			Map<String, Map<Boolean, List<ComboBoxObject>>> combobox, String empId, GeneralDate sDate,
			boolean isRequired, PersonEmployeeType perEmplType, boolean isDataType6, String categoryCode) {
		String referenceCode = null;
		switch (selectionItemDto.getReferenceType()) {
		case DESIGNATED_MASTER:
			MasterRefConditionDto master = (MasterRefConditionDto) selectionItemDto;
			referenceCode = master.getMasterType();
			break;
		case CODE_NAME:
			CodeNameRefTypeDto code = (CodeNameRefTypeDto) selectionItemDto;
			referenceCode = code.getTypeCode();
			break;
		case ENUM:
			EnumRefConditionDto enu = (EnumRefConditionDto) selectionItemDto;
			referenceCode = enu.getEnumName();
			break;
		}
		if (combobox.containsKey(referenceCode)) {
			Map<Boolean, List<ComboBoxObject>> mapComboInRefCode = combobox.get(referenceCode);
			if (mapComboInRefCode.containsKey(isRequired)) {
				return mapComboInRefCode.get(isRequired);
			} else {
				List<ComboBoxObject> returnList = getLstComboBoxValue(selectionItemDto, empId, sDate, isRequired,
						perEmplType, isDataType6, categoryCode);
				mapComboInRefCode.put(isRequired, returnList);
				combobox.put(referenceCode, mapComboInRefCode);
				return returnList;
			}
		} else {
			List<ComboBoxObject> returnList = getLstComboBoxValue(selectionItemDto, empId, sDate, isRequired,
					perEmplType, isDataType6, categoryCode);
			Map<Boolean, List<ComboBoxObject>> mapComboInRefCode = new HashMap<>();
			mapComboInRefCode.put(isRequired, returnList);
			combobox.put(referenceCode, mapComboInRefCode);
			return returnList;
		}
	}

	public List<ComboBoxObject> getLstComboBoxValue(SelectionItemDto selectionItemDto, String empId,
			GeneralDate baseDate, boolean isRequired, PersonEmployeeType perEmplType, boolean isDataType6,
			String categoryCode) {

		GeneralDate standardDate = baseDate;
		if (baseDate == null) {
			AffCompanyHist affCompanyHist = achFinder.getAffCompanyHistoryOfEmployeeDesc(AppContexts.user().companyId(),
					empId);
			standardDate = affCompanyHist.getLstAffCompanyHistByEmployee().get(0).getLstAffCompanyHistoryItem().stream()
					.collect(Collectors.toList()).get(0).start();
		}
		return comboBoxRetrieveFactory.getComboBox(selectionItemDto, empId, standardDate, isRequired, perEmplType,
				isDataType6, categoryCode);
	}
}