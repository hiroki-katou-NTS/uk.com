/**
 * 
 */
package nts.uk.ctx.pereg.app.find.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.FieldsWorkerStream;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.OptionalItemDataDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author danpv
 *
 */
public class MappingFactory {

	
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
		peregDto.getOptionalItemData()
				.forEach(empData -> itemCodeValueMap.put(empData.getItemCode(), empData.getValue()));

		return itemCodeValueMap;
	}
	
	   // Map<itemDfId, Object: value of field>
	public static Map<String, Object> getFullDtoValue2(PeregDto peregDto) {
		// Map<itemDfId, Object: value of field>
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
		peregDto.getOptionalItemData()
				.forEach(empData -> itemCodeValueMap.put(empData.getPerInfoItemDefId(), empData.getValue()));

		return itemCodeValueMap;
	}
	
	/**
	 * map peregDto to classItemList which is same category
	 * 
	 * @param peregDto
	 * @param classItemsOfCategory
	 */
	public static void mapListItemClass(PeregDto peregDto, List<LayoutPersonInfoClsDto> classItemsOfCategory) {

		// map data
		Map<String, Object> itemCodeValueMap = getFullDtoValue(peregDto);
		String recordId = peregDto.getDomainDto().getRecordId();
		for (LayoutPersonInfoClsDto classItem : classItemsOfCategory) {
			for (LayoutPersonInfoValueDto valueItem : classItem.getItems()) {
				
				Object value = getValue(itemCodeValueMap, valueItem);
				valueItem.setValue(value);
				
				// update 2018/02/22 bug 87560
				valueItem.setShowColor(false);
				
				// trong 1 category, hoặc là tất cả các classItem đều có recordId hoặc là tất cả đều không có recordId
				valueItem.setRecordId(recordId);
			}
		}

	}
	
	private static Object getValue(Map<String, Object> itemCodeValueMap, LayoutPersonInfoValueDto valueItem) {
		Object value = itemCodeValueMap.get(valueItem.getItemCode());
		if (valueItem.getItem() != null) {
			int itemType = valueItem.getItem().getDataTypeValue() ;
			if(itemType == DataTypeValue.SELECTION.value || 
					itemType == DataTypeValue.SELECTION_BUTTON.value || 
					itemType == DataTypeValue.SELECTION_RADIO.value) {
				value = value == null ? null : value.toString();
			}
		}
		return value;
	}

	public static void matchOptionalItemData(String recordId, List<LayoutPersonInfoClsDto> classItemList,
			List<OptionalItemDataDto> dataItems) {
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			matchDataToValueItems(recordId, classItem.getItems(), dataItems);
		}

	}
	
	public static void matchDataToValueItems(String recordId, List<LayoutPersonInfoValueDto> valueItems,
			List<OptionalItemDataDto> dataItems) {
		for (LayoutPersonInfoValueDto valueItem : valueItems) {
			
			// recordId
			valueItem.setRecordId(recordId);

			// update 2018/02/22 bug 87560
			valueItem.setShowColor(false);

			// data
			for (OptionalItemDataDto dataItem : dataItems) {
				if (valueItem.getItemCode().equals(dataItem.getItemCode())) {
					
					valueItem.setValue(dataItem.getValue());
				}
			}
		}
	}

}
