package nts.uk.ctx.exio.dom.exi.extcategory.specialedit;

import java.util.Optional;

import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;
import nts.uk.shr.com.context.AppContexts;

public class SpecialEditCompanyID extends SpecialEdit{
	private String originalValue;
	
	public SpecialEditCompanyID(boolean chkError, String editedItemValue, String originalValue, Optional<AcceptMode> accMode) {
		super(chkError, editedItemValue, accMode);
		this.originalValue = originalValue;
	}
	
	/**
	 * 【１】会社CD⇒会社ID（CID）
	 * @param itemValue
	 * @param accMode
	 * @param result
	 */
	public SpecialEditValue edit() {
		SpecialEditValue result = new SpecialEditValue();
		result.setChkError(this.chkError);
		result.setEditValue(this.editedItemValue);
		
		if(editedItemValue == null || editedItemValue == "") {
			if(this.originalValue == null || this.originalValue == "") {
				result.setChkError(true);
				result.setErrorContent("Msg_2128");
				return result;
			}
			editedItemValue = originalValue;
		}
		String contractCode =  AppContexts.user().contractCode();
		if(contractCode.isEmpty()) {
			result.setChkError(true);
			result.setErrorContent("Msg_2127");
		} else {
			result.setEditValue(contractCode + "-" + editedItemValue);
		}
		return result;
	}
}