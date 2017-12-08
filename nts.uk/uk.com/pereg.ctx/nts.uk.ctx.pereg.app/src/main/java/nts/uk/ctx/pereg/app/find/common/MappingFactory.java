/**
 * 
 */
package nts.uk.ctx.pereg.app.find.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.FieldsWorkerStream;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
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

	@Inject
	I18NResourcesForUK ukResouce;

	public static void mapItemClass(PeregDto peregDto, LayoutPersonInfoClsDto classItem) {
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

	public static void mapListItemClass(PeregDto peregDto, List<LayoutPersonInfoClsDto> classItemList) {
		// map record ID
		AnnotationUtil.getFieldAnnotated(peregDto.getDtoClass(), PeregRecordId.class).ifPresent(field -> {
			String recordId = ReflectionUtil.getFieldValue(field, peregDto.getDomainDto());
			classItemList.forEach(classItem -> classItem.setRecordId(recordId));
		});

		// get DTO value
		Map<String, Object> dtoValue = getDtoValue(peregDto.getDomainDto(), peregDto.getDtoClass());

		// map fix value
		mapFixDto(dtoValue, classItemList);

		// set option value
		setOptionData(peregDto, classItemList);

	}

	public static void mapListClsDto(EmpMaintLayoutDto empMaintLayoutDto, PeregDto peregDto,
			List<PerInfoItemDefForLayoutDto> lstClsItem) {
		// get dto value
		Map<String, Object> dtoValue = peregDto == null ? new HashMap<String, Object>()
				: getDtoValue(peregDto.getDomainDto(), peregDto.getDtoClass());
		setEmpMaintLayoutDto(empMaintLayoutDto, dtoValue, lstClsItem,
				peregDto == null ? null : peregDto.getDomainDto().getRecordId());
	}

	private static void setEmpMaintLayoutDto(EmpMaintLayoutDto empMaintLayoutDto, Map<String, Object> dtoFieldValue,
			List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef, String recordId) {

		lstPerInfoItemDef.forEach(item -> {
			LayoutPersonInfoClsDto layoutPerInfoClsDto = newClsDtoInstanceForTypeItem(item);
			layoutPerInfoClsDto.setRecordId(recordId);
			if (item.getItemDefType() == 2) {
				setLayoutPersonInfoClsDto(layoutPerInfoClsDto, item, dtoFieldValue);
			} else {
				setLayoutPersonInfoClsDto(layoutPerInfoClsDto, item, dtoFieldValue);
				item.getLstChildItemDef().forEach(x -> {
					setLayoutPersonInfoClsDto(layoutPerInfoClsDto, x, dtoFieldValue);
				});
			}
			empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
		});

	}

	private static void setLayoutPersonInfoClsDto(LayoutPersonInfoClsDto layoutPerInfoClsDto,
			PerInfoItemDefForLayoutDto item, Map<String, Object> dtoFieldValue) {
		Object value = dtoFieldValue.get(item.getItemCode());
		layoutPerInfoClsDto.setDispOrder(item.getDispOrder());
		layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item, value));
		layoutPerInfoClsDto.getListItemDf().add(item);
	}

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
		if (domainDto == null)
			return itemCodeValueMap;
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
	private static void setOptionData(PeregDto peregDto, List<LayoutPersonInfoClsDto> classItemList) {
		if (peregDto.getDataType() == DataClassification.EMPLOYEE) {
			mapEmployeeOptionData(peregDto.getEmpOptionalData(), classItemList);
		} else {
			mapPersonOptionData(peregDto.getPerOptionalData(), classItemList);
		}
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
	 * match domain DTO with classItem list
	 * 
	 * @param domainDto
	 * @param classItem
	 * @param dtoClass
	 */
	public static void mapFixDto(Map<String, Object> itemCodeValueMap, List<LayoutPersonInfoClsDto> classItemList) {
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
				valueItem.setValue(itemCodeValueMap.get(valueItem.getItemCode()));
			}
		}
	}

	/**
	 * match domain DTO with a classItem
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
	 * map opitionData with classItems which in same category
	 * 
	 * @param empOptionalData
	 * @param classItemList
	 */
	public static void mapEmployeeOptionData(List<EmpOptionalDto> empOptionalData,
			List<LayoutPersonInfoClsDto> classItemList) {
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
				for (EmpOptionalDto data : empOptionalData) {
					if (data.getItemCode().equals(valueItem.getItemCode())) {
						valueItem.setValue(data.getValue());
					}
				}

			}
		}
	}

	/**
	 * map opitionData with classItems which in same category
	 * 
	 * @param empOptionalData
	 * @param classItem
	 */
	public static void mapEmployeeOptionData(List<EmpOptionalDto> empOptionalData, LayoutPersonInfoClsDto classItem) {
		for (Object item : classItem.getItems()) {
			LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
			for (EmpOptionalDto data : empOptionalData) {
				if (data.getItemCode().equals(valueItem.getItemCode())) {
					valueItem.setValue(data.getValue());
				}
			}

		}
	}

	/**
	 * 
	 * @param perOptionalData
	 * @param classItem
	 */
	public static void mapPersonOptionData(List<PersonOptionalDto> perOptionalData,
			List<LayoutPersonInfoClsDto> classItemList) {
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
				for (PersonOptionalDto data : perOptionalData) {
					if (data.getItemCode().equals(valueItem.getItemCode())) {
						valueItem.setValue(data.getValue());
					}
				}
			}
		}
	}

	/**
	 * @param perOptionalData
	 * @param classItem
	 */
	public static void mapPersonOptionData(List<PersonOptionalDto> perOptionalData, LayoutPersonInfoClsDto classItem) {
		for (Object item : classItem.getItems()) {
			LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
			for (PersonOptionalDto data : perOptionalData) {
				if (data.getItemCode().equals(valueItem.getItemCode())) {
					valueItem.setValue(data.getValue());
				}

			}

		}
	}

	public static Map<String, Object> getAllItem(PeregDto peregDto) {

		Map<String, Object> dtoValue = new HashMap<String, Object>();
		if (peregDto != null) {

			dtoValue.putAll(getDtoValue(peregDto.getDomainDto(), peregDto.getClass()));

			if (peregDto.getDataType() == DataClassification.EMPLOYEE) {
				peregDto.getEmpOptionalData().forEach(x -> {

					dtoValue.put(x.getItemCode(), x.getValue());

				});
			} else {
				peregDto.getPerOptionalData().forEach(x -> {
					dtoValue.put(x.getItemCode(), x.getValue());
				});
			}
		}
		return dtoValue;
	}

	/**
	 * Mapping data between PerInfoItemDef and PerOptionalDto
	 * 
	 * @param empMaintLayoutDto
	 * @param lstCtgItemOptionalDto
	 * @param lstPerInfoItemDef
	 */
	public static void mapPerOptionalDto(EmpMaintLayoutDto empMaintLayoutDto,
			List<PersonOptionalDto> lstCtgItemOptionalDto, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef) {
		lstPerInfoItemDef.forEach(item -> {
			if (item.getItemCode().charAt(1) == 'O') {
				Optional<PersonOptionalDto> empOptionalDto = lstCtgItemOptionalDto.stream().filter(data -> {
					return data.getItemCode().equals(item.getItemCode());
				}).findFirst();
				Object value = empOptionalDto.isPresent() ? empOptionalDto.get().getValue() : null;
				setOptionValueToClsDto(empMaintLayoutDto, item, value);
			}
		});
	}

	/**
	 * Mapping data between PerInfoItemDef and EmpOptionalDto
	 * 
	 * @param empMaintLayoutDto
	 * @param lstCtgItemOptionalDto
	 * @param lstPerInfoItemDef
	 */
	public static void mapEmpOptionalDto(EmpMaintLayoutDto empMaintLayoutDto,
			List<EmpOptionalDto> lstCtgItemOptionalDto, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef) {
		lstPerInfoItemDef.forEach(item -> {
			if (item.getItemCode().charAt(1) == 'O') {
				Optional<EmpOptionalDto> empOptionalDto = lstCtgItemOptionalDto.stream().filter(data -> {
					return data.getItemCode().equals(item.getItemCode());
				}).findFirst();
				Object value = empOptionalDto.isPresent() ? empOptionalDto.get().getValue() : null;
				setOptionValueToClsDto(empMaintLayoutDto, item, value);
			}
		});
	}

	private static void setOptionValueToClsDto(EmpMaintLayoutDto empMaintLayoutDto, PerInfoItemDefForLayoutDto item,
			Object value) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = newClsDtoInstanceForTypeItem(item);
		layoutPerInfoClsDto.getListItemDf().add(item);
		layoutPerInfoClsDto.setDispOrder(item.getDispOrder());
		layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item, value));
		empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
	}

	private static LayoutPersonInfoClsDto newClsDtoInstanceForTypeItem(PerInfoItemDefForLayoutDto item) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(new ArrayList<>());
		layoutPerInfoClsDto.setPersonInfoCategoryID(item.getPerInfoCtgId());
		layoutPerInfoClsDto.setLayoutItemType(LayoutItemType.ITEM);
		layoutPerInfoClsDto.setClassName(item.getItemName());
		return layoutPerInfoClsDto;
	}
}
