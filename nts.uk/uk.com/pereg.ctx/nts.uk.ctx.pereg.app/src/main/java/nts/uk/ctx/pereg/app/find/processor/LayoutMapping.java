package nts.uk.ctx.pereg.app.find.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.shr.pereg.app.PeregItem;

/**
 * the class mapping dto object with item def
 * @author xuan vinh
 *
 */

public class LayoutMapping {

	public static EmpMaintLayoutDto map(Object dto, Class<?> dtoClass, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef){
		//Map<itemcode, Object: value of field>
		Map<String, Object> dtoFieldValue = new HashMap<String, Object>();
		
		AnnotationUtil.getStreamOfFieldsAnnotated(dtoClass, PeregItem.class).forEach( field -> {
			String itemCode = field.getAnnotation(PeregItem.class).value();
			Object obj = ReflectionUtil.getFieldValue(field, dto);
			dtoFieldValue.put(itemCode, obj);
		});
		EmpMaintLayoutDto empMaintLayoutDto = new EmpMaintLayoutDto();
		setLayoutPersonInfoClsDto(empMaintLayoutDto, dtoFieldValue, lstPerInfoItemDef);
		return empMaintLayoutDto;
	}
	
	private static void setLayoutPersonInfoClsDto(EmpMaintLayoutDto empMaintLayoutDto ,
			Map<String, Object> dtoFieldValue, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef){
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		lstPerInfoItemDef.forEach(item -> {
			if(item.getItemDefType() == 2){
				Object value = dtoFieldValue.get(item.getItemCode());
				if(value != null){
					layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item, value));
					empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
				}
			}
			else{
				layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item, dtoFieldValue.get(item.getItemCode())));
				empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
				setLayoutPersonInfoClsDto(empMaintLayoutDto, dtoFieldValue, item.getLstChildItemDef());
			}
		});
		
	}
}
