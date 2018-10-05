package nts.uk.ctx.pereg.app.find.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.CodeNameRefTypeDto;
import nts.uk.ctx.pereg.app.find.person.info.item.EnumRefConditionDto;
import nts.uk.ctx.pereg.app.find.person.info.item.MasterRefConditionDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Stateless
public class LayoutControlComBoBox {

	@Inject
	private ComboBoxRetrieveFactory comboBoxFactory;

	public void getComboBoxListForSelectionItems(String employeeId, PersonInfoCategory perInfoCategory,
			List<LayoutPersonInfoClsDto> classItemList, GeneralDate comboBoxStandardDate, String workPlaceId) {

		Map<String, Map<Boolean, List<ComboBoxObject>>> comboBoxMap = new HashMap<>();

		// For each classification item to get combo box list
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (LayoutPersonInfoValueDto valueItem : classItem.getItems()) {
				if (valueItem.isComboBoxItem()) {
					SelectionItemDto selectionItemDto = (SelectionItemDto) valueItem.getItem();
					boolean isDataType6 = valueItem.isSelectionItem();

					List<ComboBoxObject> comboBoxList = getCombo(selectionItemDto, comboBoxMap, employeeId,
							comboBoxStandardDate, valueItem.isRequired(), perInfoCategory.getPersonEmployeeType(),
							isDataType6, perInfoCategory.getCategoryCode().v(), workPlaceId);

					valueItem.setLstComboBoxValue(comboBoxList);
				}

			}
		}
	}

	private List<ComboBoxObject> getCombo(SelectionItemDto selectionItemDto,
			Map<String, Map<Boolean, List<ComboBoxObject>>> combobox, String employeeId,
			GeneralDate comboBoxStandardDate, boolean isRequired, PersonEmployeeType perEmplType, boolean isDataType6,
			String categoryCode, String workPlaceId) {
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
				List<ComboBoxObject> returnList = comboBoxFactory.getComboBox(selectionItemDto, employeeId,
						comboBoxStandardDate, isRequired, perEmplType, isDataType6, categoryCode,workPlaceId);
				mapComboInRefCode.put(isRequired, returnList);
				combobox.put(referenceCode, mapComboInRefCode);
				return returnList;
			}
		} else {
			List<ComboBoxObject> returnList = comboBoxFactory.getComboBox(selectionItemDto, employeeId,
					comboBoxStandardDate, isRequired, perEmplType, isDataType6, categoryCode,workPlaceId);
			Map<Boolean, List<ComboBoxObject>> mapComboInRefCode = new HashMap<>();
			mapComboInRefCode.put(isRequired, returnList);
			combobox.put(referenceCode, mapComboInRefCode);
			return returnList;
		}
	}
}
