package nts.uk.ctx.pereg.app.find.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefForLayoutDto;
import find.person.info.item.SingleItemDto;
import lombok.val;
import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.shr.pereg.app.PeregItem;

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
	}
	
	public static void mapPerOptionalDto(EmpMaintLayoutDto empMaintLayoutDto, List<PersonInfoItemData> lstCtgItemOptionalDto, 
			List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef,  int startOptionDtoPos){
		if(lstCtgItemOptionalDto.size() > 0){
			for(PersonInfoItemData data : lstCtgItemOptionalDto) {
				Optional<PerInfoItemDefForLayoutDto> item = lstPerInfoItemDef.stream().filter(x -> {
					return x.getItemCode().equals(data.getItemCode());
					}).findFirst();
				if(item.isPresent()){
					LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
					layoutPerInfoClsDto.setDispOrder(startOptionDtoPos);
					layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item.get(), getOptionalDataValue(item.get(), data)));
					empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
					startOptionDtoPos ++;
				}
			}
		}
	}
	
	public static void mapEmpOptionalDto(EmpMaintLayoutDto empMaintLayoutDto, List<EmpInfoItemData> lstCtgItemOptionalDto, 
			List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef, int startOptionDtoPos){
		if(lstCtgItemOptionalDto.size() > 0){
			for(EmpInfoItemData data : lstCtgItemOptionalDto) {
				Optional<PerInfoItemDefForLayoutDto> item = lstPerInfoItemDef.stream().filter(x -> {
					return x.getItemCode().equals(data.getItemCode());
					}).findFirst();
				if(item.isPresent()){
					LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
					layoutPerInfoClsDto.setDispOrder(startOptionDtoPos);
					layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item.get(), getOptionalDataValue(item.get(), data)));
					empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
					startOptionDtoPos ++;
				}
			}
		}
	}
	
	private static void setEmpMaintLayoutDto(EmpMaintLayoutDto empMaintLayoutDto ,
			Map<String, Object> dtoFieldValue, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef){
		
		lstPerInfoItemDef.forEach(item -> {
			if(item.getItemDefType() == 2){
				setLayoutPersonInfoClsDto(empMaintLayoutDto, item, dtoFieldValue);			
			}
			else{
				setLayoutPersonInfoClsDto(empMaintLayoutDto, item, dtoFieldValue);	
				setEmpMaintLayoutDto(empMaintLayoutDto, dtoFieldValue, item.getLstChildItemDef());
			}
		});
		
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
	private static Object getOptionalDataValue(PerInfoItemDefForLayoutDto item, PersonInfoItemData data){
		SingleItemDto singleItemDto = (SingleItemDto)item.getItemTypeState();
		switch(singleItemDto.getDataTypeState().getDataTypeValue()){
		case 1:
			return data.getDataState().getStringValue();
		case 2: 
			return data.getDataState().getNumberValue();
		case 3:  
			return data.getDataState().getDateValue();
		}	
		return null;
	}
	
	private static Object getOptionalDataValue(PerInfoItemDefForLayoutDto item, EmpInfoItemData data){
		SingleItemDto singleItemDto = (SingleItemDto)item.getItemTypeState();
		switch(singleItemDto.getDataTypeState().getDataTypeValue()){
		case 1:
			return data.getDataState().getStringValue();
		case 2: 
			return data.getDataState().getNumberValue();
		case 3:  
			return data.getDataState().getDateValue();
		}	
		return null;
	}
}
