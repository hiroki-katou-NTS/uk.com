/**
 * 
 */
package nts.uk.ctx.pereg.app.find.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefDto;
import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;
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

	public static void map(PeregDto peregDto, LayoutPersonInfoClsDto classItem) {

		// map record ID
		AnnotationUtil.getFieldAnnotated(peregDto.getClass(), PeregRecordId.class).ifPresent(field -> {
			String recordId = ReflectionUtil.getFieldValue(field, peregDto.getDomainDto());
			classItem.setRecordId(recordId);
		});

		// map fix value
		mapFixDto(peregDto.getDomainDto(), classItem, peregDto.getClass());

		// map option value
		if (peregDto.getDataType() == DataClassification.EMPLOYEE) {
			mapEmployeeOptionData(peregDto.getEmpOptionalData(), classItem);
		} else {
			mapPersonOptionData(peregDto.getPerOptionalData(), classItem);
		}

	}

	/**
	 * match domain DTO with item-definition to create data object
	 * 
	 * @param domainDto
	 * @param classItem
	 * @param dtoClass
	 */
	public static void mapFixDto(PeregDomainDto domainDto, LayoutPersonInfoClsDto classItem, Class<?> dtoClass) {
		// Map<itemcode, Object: value of field>
		Map<String, Object> itemCodeValueMap = new HashMap<String, Object>();
		AnnotationUtil.getStreamOfFieldsAnnotated(dtoClass, PeregItem.class).forEach(field -> {
			String itemCode = field.getAnnotation(PeregItem.class).value();
			Object obj = ReflectionUtil.getFieldValue(field, domainDto);
			itemCodeValueMap.put(itemCode, obj);
		});

		// add to value-list
		classItem.getListItemDf().forEach(itemDef -> {
			LayoutPersonInfoValueDto valueItem = LayoutPersonInfoValueDto.initData(itemDef,
					itemCodeValueMap.get(itemDef.getItemCode()));
			classItem.getItems().add(valueItem);

		});

	}

	/**
	 * 
	 * @param empOptionalData
	 * @param classItem
	 */
	public static void mapEmployeeOptionData(List<EmpOptionalDto> empOptionalData, LayoutPersonInfoClsDto classItem) {
		for (PerInfoItemDefDto itemDef : classItem.getListItemDf()) {
			Optional<EmpOptionalDto> data = empOptionalData.stream()
					.filter(optionData -> optionData.getItemCode().equals(itemDef.getItemCode())).findFirst();
			Object value = data.isPresent() ? data.get().getValue() : null;
			LayoutPersonInfoValueDto valueItem = LayoutPersonInfoValueDto.initData(itemDef, value);
			classItem.getItems().add(valueItem);
		}
	}

	/**
	 * @param perOptionalData
	 * @param classItem
	 */
	public static void mapPersonOptionData(List<PersonOptionalDto> perOptionalData, LayoutPersonInfoClsDto classItem) {
		for (PerInfoItemDefDto itemDef : classItem.getListItemDf()) {
			Optional<PersonOptionalDto> data = perOptionalData.stream()
					.filter(optionData -> optionData.getItemCode().equals(itemDef.getItemCode())).findFirst();
			Object value = data.isPresent() ? data.get().getValue() : null;
			LayoutPersonInfoValueDto valueItem = LayoutPersonInfoValueDto.initData(itemDef, value);
			classItem.getItems().add(valueItem);
		}
	}

}
