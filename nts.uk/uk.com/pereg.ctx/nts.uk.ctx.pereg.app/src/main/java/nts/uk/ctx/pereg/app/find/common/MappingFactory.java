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
			if ( !optionItemNoValue ) {
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
		
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
				valueItem.setValue(itemCodeValueMap.get(valueItem.getItemCode()));
			}
		}

		// map record ID
		String recordId = peregDto.getDomainDto().getRecordId();
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
				boolean optionItemNoValue = !itemCodeValueMap.containsKey(valueItem.getItemCode())
						&& valueItem.getItemCode().charAt(1) == 'O';
				if ( !optionItemNoValue ) {
					valueItem.setRecordId(recordId);
				}
			}
		}

	}

	public static Map<String, Object> getFullDtoValue(PeregDto peregDto) {
		// Map<itemcode, Object: value of field>
		Map<String, Object> itemCodeValueMap = new HashMap<String, Object>();
		
		//	map from domain data
		PeregDomainDto domainDto = peregDto.getDomainDto();
		if ( domainDto == null){
			return itemCodeValueMap;
		}
		
		FieldsWorkerStream lstField = AnnotationUtil.getStreamOfFieldsAnnotated(peregDto.getDtoClass(), PeregItem.class);
		lstField.forEach(field -> {
			String itemCode = field.getAnnotation(PeregItem.class).value();
			Object obj = ReflectionUtil.getFieldValue(field, domainDto);
			itemCodeValueMap.put(itemCode, obj);
		});
		
		// map from option data
		if ( peregDto.getDataType() == DataClassification.EMPLOYEE) {
			peregDto.getEmpOptionalData().forEach( empData -> itemCodeValueMap.put( empData.getItemCode(), empData.getValue()));
		} else {
			peregDto.getPerOptionalData().forEach( perData -> itemCodeValueMap.put( perData.getItemCode(), perData.getValue()));
		}
		
		return itemCodeValueMap;
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
				LayoutPersonInfoClsDto layoutPerInfoClsDto = newClsDtoInstanceForTypeItem(item);
				if (item.getItemDefType() == 2) {
					setPerClsItemValue(lstCtgItemOptionalDto, layoutPerInfoClsDto, item);
				} else {
					setPerClsItemValue(lstCtgItemOptionalDto, layoutPerInfoClsDto, item);
					item.getLstChildItemDef()
							.forEach(x -> setPerClsItemValue(lstCtgItemOptionalDto, layoutPerInfoClsDto, x));
				}
				empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
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
				LayoutPersonInfoClsDto layoutPerInfoClsDto = newClsDtoInstanceForTypeItem(item);
				if (item.getItemDefType() == 2) {
					setEmpClsItemValue(lstCtgItemOptionalDto, layoutPerInfoClsDto, item);
				} else {
					setEmpClsItemValue(lstCtgItemOptionalDto, layoutPerInfoClsDto, item);
					item.getLstChildItemDef()
							.forEach(x -> setEmpClsItemValue(lstCtgItemOptionalDto, layoutPerInfoClsDto, x));
				}
				empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
			}
		});
	}

	private static void setPerClsItemValue(List<PersonOptionalDto> lstCtgItemOptionalDto,
			LayoutPersonInfoClsDto layoutPerInfoClsDto, PerInfoItemDefForLayoutDto item) {
		Optional<PersonOptionalDto> perOptionalDto = lstCtgItemOptionalDto.stream().filter(data -> {
			return data.getItemCode().equals(item.getItemCode());
		}).findFirst();
		if(!perOptionalDto.isPresent()) item.setRecordId(null);
		Object value = perOptionalDto.isPresent() ? perOptionalDto.get().getValue() : null;
		setOptionValueToClsDto(layoutPerInfoClsDto, item, value);
	}

	private static void setEmpClsItemValue(List<EmpOptionalDto> lstCtgItemOptionalDto,
			LayoutPersonInfoClsDto layoutPerInfoClsDto, PerInfoItemDefForLayoutDto item) {
		Optional<EmpOptionalDto> empOptionalDto = lstCtgItemOptionalDto.stream().filter(data -> {
			return data.getItemCode().equals(item.getItemCode());
		}).findFirst();
		if(!empOptionalDto.isPresent())item.setRecordId(null);
		Object value = empOptionalDto.isPresent() ? empOptionalDto.get().getValue() : null;
		setOptionValueToClsDto(layoutPerInfoClsDto, item, value);
	}

	private static void setOptionValueToClsDto(LayoutPersonInfoClsDto layoutPerInfoClsDto,
			PerInfoItemDefForLayoutDto item, Object value) {
		layoutPerInfoClsDto.getListItemDf().add(item);
		layoutPerInfoClsDto.setDispOrder(item.getDispOrder());
		layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item, value));

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
