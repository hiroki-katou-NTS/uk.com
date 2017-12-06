package nts.uk.ctx.pereg.app.find.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.PeregComboList;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.EmpOptionalDto;
import nts.uk.shr.pereg.app.find.dto.PersonOptionalDto;

/**
 * the class mapping dto object with item def
 * @author xuan vinh
 *
 */

public class LayoutMapping {

	public static void mapFixDto(EmpMaintLayoutDto empMaintLayoutDto, Object dto, Class<?> dtoClass, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef){
		//Map<itemcode, Object: value of field>
		Map<String, Object> dtoFieldValue = new HashMap<String, Object>();
		
		AnnotationUtil.getStreamOfFieldsAnnotated(dtoClass, PeregItem.class).forEach( field -> {
			String itemCode = field.getAnnotation(PeregItem.class).value();
			Object obj = ReflectionUtil.getFieldValue(field, dto);
			dtoFieldValue.put(itemCode, obj);
		});
		setEmpMaintLayoutDto(empMaintLayoutDto, dtoFieldValue, lstPerInfoItemDef);
		
		AnnotationUtil.getFieldAnnotated(dtoClass, PeregComboList.class).ifPresent(field ->{
			@SuppressWarnings("unchecked")
			List<ComboBoxObject> lstComboBox = (List<ComboBoxObject>)ReflectionUtil.getFieldValue(field, dto);
			setComboBoxList(empMaintLayoutDto, lstComboBox);
		});
	}
	
	public static void mapPerOptionalDto(EmpMaintLayoutDto empMaintLayoutDto, List<PersonOptionalDto> lstCtgItemOptionalDto, 
			List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef){
		if(lstCtgItemOptionalDto.size() > 0){
			for(PersonOptionalDto data : lstCtgItemOptionalDto) {
				Optional<PerInfoItemDefForLayoutDto> item = lstPerInfoItemDef.stream().filter(x -> {
					return x.getItemCode().equals(data.getItemCode());
					}).findFirst();
				if(item.isPresent()){
					LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
					layoutPerInfoClsDto.setDispOrder(item.get().getDispOrder());
					layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item.get(), data.getValue()));
					empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
				}
			}
		}
	}
	
	public static void mapEmpOptionalDto(EmpMaintLayoutDto empMaintLayoutDto, List<EmpOptionalDto> lstCtgItemOptionalDto, 
			List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef){
		if(lstCtgItemOptionalDto.size() > 0){
			for(EmpOptionalDto data : lstCtgItemOptionalDto) {
				Optional<PerInfoItemDefForLayoutDto> item = lstPerInfoItemDef.stream().filter(x -> {
					return x.getItemCode().equals(data.getItemCode());
					}).findFirst();
				if(item.isPresent()){
					LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
					layoutPerInfoClsDto.setDispOrder(item.get().getDispOrder());
					layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item.get(), data.getValue()));
					empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
				}
			}
		}
	}
	
	private static void setEmpMaintLayoutDto(EmpMaintLayoutDto empMaintLayoutDto ,
			Map<String, Object> dtoFieldValue, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef){
		
		lstPerInfoItemDef.forEach(item -> {
			setLayoutPersonInfoClsDto(empMaintLayoutDto, item, dtoFieldValue);
		});
		
	}
	
	private static void setComboBoxList(EmpMaintLayoutDto empMaintLayoutDto, List<ComboBoxObject> lstComboBox){
		LayoutPersonInfoClsDto cls = new LayoutPersonInfoClsDto();
		cls.getItems().add(lstComboBox);
		empMaintLayoutDto.getClassificationItems().add(cls);
	}
	
	private static void setLayoutPersonInfoClsDto(EmpMaintLayoutDto empMaintLayoutDto, PerInfoItemDefForLayoutDto item, Map<String, Object> dtoFieldValue){
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		Object value = dtoFieldValue.get(item.getItemCode());
		if(value != null){		
			layoutPerInfoClsDto.setDispOrder(item.getDispOrder());
			layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item, value));
			empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
		}
	}
//	private static Object getOptionalDataValue(PerInfoItemDefForLayoutDto item, PersonInfoItemData data){
//		SingleItemDto singleItemDto = (SingleItemDto)item.getItemTypeState();
//		switch(singleItemDto.getDataTypeState().getDataTypeValue()){
//		case 1:
//			return data.getDataState().getStringValue();
//		case 2: 
//			return data.getDataState().getNumberValue();
//		case 3:  
//			return data.getDataState().getDateValue();
//		}	
//		return null;
//	}
//	
//	private static Object getOptionalDataValue(PerInfoItemDefForLayoutDto item, EmpInfoItemData data){
//		SingleItemDto singleItemDto = (SingleItemDto)item.getItemTypeState();
//		switch(singleItemDto.getDataTypeState().getDataTypeValue()){
//		case 1:
//			return data.getDataState().getStringValue();
//		case 2: 
//			return data.getDataState().getNumberValue();
//		case 3:  
//			return data.getDataState().getDateValue();
//		}	
//		return null;
//	}
}
