/**
 * 
 */
package nts.uk.ctx.pereg.app.find.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employment.history.SalarySegment;
import nts.uk.ctx.bs.person.dom.person.info.BloodType;
import nts.uk.ctx.bs.person.dom.person.info.GenderPerson;
import nts.uk.ctx.pereg.app.find.person.info.item.CodeNameRefTypeDto;
import nts.uk.ctx.pereg.app.find.person.info.item.EnumRefConditionDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.item.SelectionInitDto;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection.SelectionFinder;
import nts.uk.shr.pereg.app.ComboBoxObject;

/**
 * @author danpv
 * @param <E>
 *
 */
@Stateless
public class ComboBoxRetrieveFactory<E> {

	@Inject
	private static SelectionFinder selectionFinder;
	
	private static Map<String, Class<?>> enumMap;
	static  {
		Map<String, Class<?>> aMap = new HashMap<>();
		// 性別
		aMap.put("E00001", GenderPerson.class);
		// 血液型
		aMap.put("E00002", BloodType.class);
		// 給与区分
		aMap.put("E00003", SalarySegment.class);
		// 育児介護区分
		//aMap.put("E00004", 4);
		// するしない区分
		//aMap.put("E00005", 5);
		// 労働制
		//aMap.put("E00006", 6);
		// 勤務予定基本作成方法
		//aMap.put("E00007", 7);
		// 勤務予定作成マスタ参照区分
		//aMap.put("E00008", 8);
		// 勤務予定の時間帯マスタ参照区分
		//aMap.put("E00009", 9);
		enumMap = Collections.unmodifiableMap(aMap);
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Enum<?>> List<ComboBoxObject> getComboBox(SelectionItemDto selectionItemDto, GeneralDate standardDate) {
		switch (selectionItemDto.getReferenceType()) {
		case ENUM:
			EnumRefConditionDto enumTypeDto = (EnumRefConditionDto) selectionItemDto;
			List<ComboBoxObject> listcombo =  EnumAdaptor.convertToValueNameList((Class<E>) enumMap.get(enumTypeDto.getEnumName())).stream()
					.map(enumElement -> new ComboBoxObject(enumElement.getValue() + "", enumElement.getFieldName()))
					.collect(Collectors.toList());
			return listcombo;
		case CODE_NAME:
			CodeNameRefTypeDto codeNameTypeDto = (CodeNameRefTypeDto) selectionItemDto;
			List<SelectionInitDto> selectionList = selectionFinder
					.getAllSelectionByCompanyId(codeNameTypeDto.getTypeCode(), standardDate);
			List<ComboBoxObject> lstComboBoxValue = new ArrayList<>();
			for (SelectionInitDto selection : selectionList) {
				lstComboBoxValue.add(new ComboBoxObject(selection.getSelectionName(), selection.getSelectionId()));
			}
			return lstComboBoxValue;
		case DESIGNATED_MASTER:
			
			return null;

		}
		return null;
	}

}
