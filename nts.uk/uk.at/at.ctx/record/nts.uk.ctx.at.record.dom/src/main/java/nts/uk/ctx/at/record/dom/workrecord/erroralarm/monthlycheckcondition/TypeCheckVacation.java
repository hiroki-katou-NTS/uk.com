package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import nts.arc.i18n.I18NText;

/**
 * 残数チェックの休暇の種類
 * @author tutk
 *
 */
public enum TypeCheckVacation {
	
//	ANNUAL_PAID_LEAVE(0,"Enum_TypeCheckVacation_ANNUAL_PAID_LEAVE"),
	ANNUAL_PAID_LEAVE(0, I18NText.getText("KAL003_112")),
	
//	SUB_HOLIDAY(1,"Enum_TypeCheckVacation_SUB_HOLIDAY"),
	SUB_HOLIDAY(1, I18NText.getText("KAL003_113")),
	
//	PAUSE(2,"Enum_TypeCheckVacation_PAUSE"),
	PAUSE(2, I18NText.getText("KAL003_114")),
	
//	YEARLY_RESERVED(3,"Enum_TypeCheckVacation_YEARLY_RESERVED"),
	YEARLY_RESERVED(3, I18NText.getText("KAL003_115")),
	
//	_64H_SUPER_HOLIDAY(4,"Enum_TypeCheckVacation_64H_SUPER_HOLIDAY"),
//	_64H_SUPER_HOLIDAY(4, I18NText.getText("KAL003_116")),
	
//	PUBLIC_HOLIDAY(5,"Enum_TypeCheckVacation_PUBLIC_HOLIDAY"),
//	PUBLIC_HOLIDAY(5, I18NText.getText("KAL003_117")),
	
//	SPECIAL_HOLIDAY(6,"Enum_TypeCheckVacation_SPECIAL_HOLIDAY"),
	SPECIAL_HOLIDAY(6, I18NText.getText("KAL003_118"));
	
//	NURSING_CARE_LEAVE(7,"Enum_TypeCheckVacation_NURSING_CARE_LEAVE");
//	NURSING_CARE_LEAVE(7, I18NText.getText("KAL003_119"));
	
	public int value;
	
	public String nameId;

	private TypeCheckVacation(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
