package nts.uk.ctx.at.function.dom.adapter.monthlyremain;

import nts.arc.i18n.I18NText;

/**
 * 
 * @author Hoi1102
 *
 */
public enum TypeCheckVacationImport {
	/**年次有給休暇	 */
	ANNUAL_PAID_LEAVE(0, I18NText.getText("KAL003_112")),
	/**代休	 */
	SUB_HOLIDAY(1, I18NText.getText("KAL003_113")),
	/**	振休 */
	PAUSE(2, I18NText.getText("KAL003_114")),
	/**積立年休	 */
	YEARLY_RESERVED(3, I18NText.getText("KAL003_115")),
	/**特休	 */
	SPECIAL_HOLIDAY(6, I18NText.getText("KAL003_118"));
	
	public int value;
	
	public String nameId;

	private TypeCheckVacationImport(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
