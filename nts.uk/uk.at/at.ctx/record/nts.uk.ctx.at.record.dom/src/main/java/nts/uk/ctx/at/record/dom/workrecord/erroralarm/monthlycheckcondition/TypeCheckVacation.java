package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;
/**
 * 残数チェックの休暇の種類
 * @author tutk
 *
 */
public enum TypeCheckVacation {
	
	ANNUAL_PAID_LEAVE(0,"Enum_TypeCheckVacation_ANNUAL_PAID_LEAVE"),
	
	SUB_HOLIDAY(1,"Enum_TypeCheckVacation_SUB_HOLIDAY"),
	
	PAUSE(2,"Enum_TypeCheckVacation_PAUSE"),
	
	YEARLY_RESERVED(3,"Enum_TypeCheckVacation_YEARLY_RESERVED"),
	
	_64H_SUPER_HOLIDAY(4,"Enum_TypeCheckVacation_64H_SUPER_HOLIDAY"),
	
	PUBLIC_HOLIDAY(5,"Enum_TypeCheckVacation_PUBLIC_HOLIDAY"),
	
	SPECIAL_HOLIDAY(6,"Enum_TypeCheckVacation_SPECIAL_HOLIDAY"),
	
	NURSING_CARE_LEAVE(7,"Enum_TypeCheckVacation_NURSING_CARE_LEAVE");
	
	public int value;
	
	public String nameId;

	private TypeCheckVacation(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
