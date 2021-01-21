package nts.uk.ctx.at.function.dom.alarm.checkcondition.daily;

import nts.arc.i18n.I18NText;

/**
 * 
 * @author tutk
 *
 */
public enum ConExtractedDaily {
	
	ALL(0, I18NText.getText("KAL003_328")),
	
	CONFIRMED_DATA(1, I18NText.getText("KAL003_329")),
	
	UNCONFIRMER_DATA(2, I18NText.getText("KAL003_330"));

	
	public int value;
	
	public String nameId;
	
	private ConExtractedDaily (int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
