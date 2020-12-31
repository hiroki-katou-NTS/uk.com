package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import nts.arc.i18n.I18NText;

/**
 * 残数チェックの休暇の種類
 * @author tutk
 *
 */
public enum TypeCheckVacation {
	/**年次有給休暇	 */
	ANNUAL_PAID_LEAVE(0, I18NText.getText("KAL003_112")),
	
	/**代休	 */
	SUB_HOLIDAY(1, I18NText.getText("KAL003_113")),
	
	/**振休	 */
	PAUSE(2, I18NText.getText("KAL003_114")),
	/**積立年休	 */
	YEARLY_RESERVED(3, I18NText.getText("KAL003_115")),
	/**特休	 */
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
