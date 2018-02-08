/**
 * 
 */
package nts.uk.ctx.pereg.app.find.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.FieldsWorkerStream;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.EmpOptionalDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDto;
import nts.uk.shr.pereg.app.find.dto.PersonOptionalDto;

/**
 * @author danpv
 *
 */
public class MappingFactory {

	@Inject
	I18NResourcesForUK ukResouce;

	public static void mapItemClass(PeregDto peregDto, LayoutPersonInfoClsDto classItem) {

		// map data
		Map<String, Object> itemCodeValueMap = getFullDtoValue(peregDto);

		for (Object item : classItem.getItems()) {
			LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
			valueItem.setValue(itemCodeValueMap.get(valueItem.getItemCode()));
		}

		// map record ID
		String recordId = peregDto.getDomainDto().getRecordId();
		for (Object item : classItem.getItems()) {
			LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
			boolean optionItemNoValue = !itemCodeValueMap.containsKey(valueItem.getItemCode())
					&& valueItem.getItemCode().charAt(1) == 'O';
			if (!optionItemNoValue) {
				valueItem.setRecordId(recordId);
			}

		}

	}

	/**
	 * map peregDto to classItemList which is same category
	 * 
	 * @param peregDto
	 * @param classItemList
	 */
	public static void mapListItemClass(PeregDto peregDto, List<LayoutPersonInfoClsDto> classItemList) {

		// map data
		Map<String, Object> itemCodeValueMap = getFullDtoValue(peregDto);
		String recordId = peregDto.getDomainDto().getRecordId();
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
				Object value = itemCodeValueMap.get(valueItem.getItemCode());
				if (valueItem.getItem() != null) {
					if(valueItem.getItem().getDataTypeValue() == 6
							|| valueItem.getItem().getDataTypeValue() == 7
							|| valueItem.getItem().getDataTypeValue() == 8) {
						value = value.toString();
					}
				}
				valueItem.setValue(value);
				boolean optionItemNoValue = itemCodeValueMap.containsKey(valueItem.getItemCode());
				if (optionItemNoValue) {
					valueItem.setRecordId(recordId);
				}
			}
		}

		// map record ID
		// String recordId = peregDto.getDomainDto().getRecordId();
		// for (LayoutPersonInfoClsDto classItem : classItemList) {
		// for (Object item : classItem.getItems()) {
		// LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
		// boolean optionItemNoValue =
		// itemCodeValueMap.containsKey(valueItem.getItemCode());
		// if (optionItemNoValue ) {
		// valueItem.setRecordId(recordId);
		// }
		// }
		// }

	}

	public static Map<String, Object> getFullDtoValue(PeregDto peregDto) {
		// Map<itemcode, Object: value of field>
		Map<String, Object> itemCodeValueMap = new HashMap<String, Object>();

		// map from domain data
		PeregDomainDto domainDto = peregDto.getDomainDto();
		if (domainDto == null) {
			return itemCodeValueMap;
		}

		FieldsWorkerStream lstField = AnnotationUtil.getStreamOfFieldsAnnotated(peregDto.getDtoClass(),
				PeregItem.class);
		lstField.forEach(field -> {
			String itemCode = field.getAnnotation(PeregItem.class).value();
			Object obj = ReflectionUtil.getFieldValue(field, domainDto);
			itemCodeValueMap.put(itemCode, obj);
		});

		// map from option data
		if (peregDto.getDataType() == DataClassification.EMPLOYEE) {
			peregDto.getEmpOptionalData()
					.forEach(empData -> itemCodeValueMap.put(empData.getItemCode(), empData.getValue()));
		} else {
			peregDto.getPerOptionalData()
					.forEach(perData -> itemCodeValueMap.put(perData.getItemCode(), perData.getValue()));
		}

		return itemCodeValueMap;
	}

	public static void matchPerOptionData(String recordId, List<LayoutPersonInfoClsDto> classItemList,
			List<PersonOptionalDto> dataItems) {
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;

				// recordId
				valueItem.setRecordId(recordId);

				// data
				for (PersonOptionalDto dataItem : dataItems) {
					if (valueItem.getItemCode().equals(dataItem.getItemCode())) {
						valueItem.setValue(dataItem.getValue());
					}
				}
			}
		}

	}

	public static void matchEmpOptionData(String recordId, List<LayoutPersonInfoClsDto> classItemList,
			List<EmpOptionalDto> dataItems) {
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;

				// recordId
				valueItem.setRecordId(recordId);

				// data
				for (EmpOptionalDto dataItem : dataItems) {
					if (valueItem.getItemCode().equals(dataItem.getItemCode())) {
						valueItem.setValue(dataItem.getValue());
					}
				}
			}
		}
	}

	public static void setDefaultValueRadio(List<LayoutPersonInfoClsDto> classItemList) {
		List<String> firstReqLstItems = new ArrayList<String>();
		firstReqLstItems.add("IS00130");
		firstReqLstItems.add("IS00128");
		firstReqLstItems.add("IS00139");
		firstReqLstItems.add("IS00157");
		firstReqLstItems.add("IS00166");
		firstReqLstItems.add("IS00175");
		firstReqLstItems.add("IS00148");
		firstReqLstItems.add("IS00193");
		firstReqLstItems.add("IS00202");
		firstReqLstItems.add("IS00211");
		firstReqLstItems.add("IS00220");
		firstReqLstItems.add("IS00229");
		firstReqLstItems.add("IS00238");
		firstReqLstItems.add("IS00184");
		List<String> secReqLstItems = new ArrayList<String>();
		secReqLstItems.add("IS00131");
		secReqLstItems.add("IS00140");
		secReqLstItems.add("IS00158");
		secReqLstItems.add("IS00167");
		secReqLstItems.add("IS00176");
		secReqLstItems.add("IS00149");
		secReqLstItems.add("IS00194");
		secReqLstItems.add("IS00203");
		secReqLstItems.add("IS00212");
		secReqLstItems.add("IS00221");
		secReqLstItems.add("IS00230");
		secReqLstItems.add("IS00239");
		secReqLstItems.add("IS00185");
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
				if (valueItem.getValue() == null) {
					switch (valueItem.getItemCode()) {
					case "IS00248":
					case "IS00247":
					case "IS00258":
						valueItem.setValue("0");
						break;
					case "IS00121":
						valueItem.setValue("1");
						break;
					default:
						break;
					}

				}else {
					if(firstReqLstItems.contains(valueItem.getItemCode())) {
						valueItem.setTextValue(getFirstValueText(valueItem.getItemCode()));
					} else if(secReqLstItems.contains(valueItem.getItemCode())) {
						valueItem.setTextValue(getSecValueText(valueItem.getItemCode()));
					}
				}
			}
		}
	}
	//Wait request list
	private static String getFirstValueText(String itemCode) {
		return "First button";
	}
	
	private static String getSecValueText(String itemCode) {
		return "Second button";
	}

}
