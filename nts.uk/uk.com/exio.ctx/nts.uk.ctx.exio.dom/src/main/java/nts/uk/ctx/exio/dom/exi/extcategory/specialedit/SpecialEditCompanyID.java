package nts.uk.ctx.exio.dom.exi.extcategory.specialedit;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;
import nts.uk.shr.com.context.AppContexts;

public class SpecialEditCompanyID extends SpecialEdit{
	public SpecialEditCompanyID(boolean chkError, Object itemValue, Optional<AcceptMode> accMode,
			List<List<String>> lstLineData) {
		super(chkError, itemValue, accMode, lstLineData);
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
		result.setEditValue(this.itemValue);
		
		if(itemValue == null || itemValue == "") {
			String columnName = "CID";
			for(List<String> lstData : lstLineData) {
				if(lstData.get(0).equals(columnName)) {
					itemValue = lstData.get(1);
				}
			}
			if(itemValue == null || itemValue == "") {
				result.setChkError(true);
				result.setErrorContent("Msg_2128");
				return result;
			}
		}
		String contractCode =  AppContexts.user().contractCode();
		if(contractCode.isEmpty()) {
			result.setChkError(true);
			result.setErrorContent("Msg_2127");
		} else {
			result.setEditValue(contractCode + "-" + itemValue);
		}
		return result;
	}
}