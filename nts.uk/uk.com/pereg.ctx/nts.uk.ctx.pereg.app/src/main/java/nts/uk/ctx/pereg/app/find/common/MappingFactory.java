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
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
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

	public static void mapSingleClsDto(PeregDto peregDto, LayoutPersonInfoClsDto classItem) {
		// map record ID
		AnnotationUtil.getFieldAnnotated(peregDto.getDtoClass(), PeregRecordId.class).ifPresent(field -> {
			String recordId = ReflectionUtil.getFieldValue(field, peregDto.getDomainDto());
			classItem.setRecordId(recordId);
		});

		// get dto value
		Map<String, Object> dtoValue = getDtoValue(peregDto.getDomainDto(), peregDto.getDtoClass());

		// map fix value
		mapFixDto(dtoValue, classItem);

		// set option value
		setOptionData(peregDto, classItem);

	}

	public static void mapListClsDto(EmpMaintLayoutDto empMaintLayoutDto, PeregDto peregDto,
			List<PerInfoItemDefForLayoutDto> lstClsItem) {
		// get dto value
		Map<String, Object> dtoValue = getDtoValue(peregDto.getDomainDto(), peregDto.getDtoClass());
		setEmpMaintLayoutDto(empMaintLayoutDto, dtoValue, lstClsItem, peregDto.getDomainDto().getRecordId());
	}

	private static void setEmpMaintLayoutDto(EmpMaintLayoutDto empMaintLayoutDto, Map<String, Object> dtoFieldValue,
			List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef, String recordId) {
		
		lstPerInfoItemDef.forEach(item -> {
			LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
			layoutPerInfoClsDto.setRecordId(recordId);
			if(item.getItemDefType() == 2)
				setLayoutPersonInfoClsDto(layoutPerInfoClsDto, item, dtoFieldValue);
			else{
				setLayoutPersonInfoClsDto(layoutPerInfoClsDto, item, dtoFieldValue);
				item.getLstChildItemDef().forEach(x -> setLayoutPersonInfoClsDto(layoutPerInfoClsDto, x, dtoFieldValue));
			}
			empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
		});
		
	}

	private static void setLayoutPersonInfoClsDto(LayoutPersonInfoClsDto layoutPerInfoClsDto, PerInfoItemDefForLayoutDto item, Map<String, Object> dtoFieldValue){
		Object value = dtoFieldValue.get(item.getItemCode());		
		layoutPerInfoClsDto.setDispOrder(item.getDispOrder());
		layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item, value));
	}
//	private static void setLayoutPersonInfoClsDto(EmpMaintLayoutDto empMaintLayoutDto, PerInfoItemDefForLayoutDto item,
//			Map<String, Object> dtoFieldValue) {
//		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
//		Object value = dtoFieldValue.get(item.getItemCode());		
//		layoutPerInfoClsDto.setDispOrder(item.getDispOrder());
//		layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item, value));
//		empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
//	}

	/**
	 * get dto value
	 * 
	 * @param domainDto
	 * @param dtoClass
	 * @return Map<itemCode, value>
	 */
	private static Map<String, Object> getDtoValue(PeregDomainDto domainDto, Class<?> dtoClass) {
		// Map<itemcode, Object: value of field>
		Map<String, Object> itemCodeValueMap = new HashMap<String, Object>();
		FieldsWorkerStream lstField = AnnotationUtil.getStreamOfFieldsAnnotated(dtoClass, PeregItem.class);
		lstField.forEach(field -> {
			String itemCode = field.getAnnotation(PeregItem.class).value();
			Object obj = ReflectionUtil.getFieldValue(field, domainDto);
			itemCodeValueMap.put(itemCode, obj);
		});
		return itemCodeValueMap;
	}

	/**
	 * set optional data
	 * 
	 * @param peregDto
	 * @param classItem
	 */
	private static void setOptionData(PeregDto peregDto, LayoutPersonInfoClsDto classItem) {
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
	public static void mapFixDto(Map<String, Object> itemCodeValueMap, LayoutPersonInfoClsDto classItem) {
		
		for (Object item : classItem.getItems()) {
			LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
			valueItem.setValue(itemCodeValueMap.get(valueItem.getItemCode()));
		}

	}

	/**
	 * 
	 * @param empOptionalData
	 * @param classItem
	 */
	public static void mapEmployeeOptionData(List<EmpOptionalDto> empOptionalData, LayoutPersonInfoClsDto classItem) {
		for (PerInfoItemDefDto itemDef : classItem.getListItemDf()) {
			for (EmpOptionalDto data : empOptionalData) {
				if (data.getItemCode().equals(itemDef.getItemCode())) {
					LayoutPersonInfoValueDto valueItem = LayoutPersonInfoValueDto.initData(itemDef, data.getValue());
					classItem.getItems().add(valueItem);
				}
			}

		}
	}

	/**
	 * @param perOptionalData
	 * @param classItem
	 */
	public static void mapPersonOptionData(List<PersonOptionalDto> perOptionalData, LayoutPersonInfoClsDto classItem) {
		for (PerInfoItemDefDto itemDef : classItem.getListItemDf()) {
			for (PersonOptionalDto data : perOptionalData) {
				if (data.getItemCode().equals(itemDef.getItemCode())) {
					LayoutPersonInfoValueDto valueItem = LayoutPersonInfoValueDto.initData(itemDef, data.getValue());
					classItem.getItems().add(valueItem);
				}

			}

		}
	}

	public static Map<String, Object> getAllItem(PeregDto peregDto) {

		Map<String, Object> dtoValue = getDtoValue(peregDto.getDomainDto(), peregDto.getClass());

		if (peregDto.getDataType() == DataClassification.EMPLOYEE) {
			peregDto.getEmpOptionalData().forEach(x -> {
				dtoValue.put(x.getItemCode(), x.getValue());
			});
		} else {
			peregDto.getPerOptionalData().forEach(x -> {
				dtoValue.put(x.getItemCode(), x.getValue());
			});
		}
		return dtoValue;

	}

}
