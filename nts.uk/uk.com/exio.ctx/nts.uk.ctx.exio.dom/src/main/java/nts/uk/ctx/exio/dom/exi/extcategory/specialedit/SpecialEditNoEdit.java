package nts.uk.ctx.exio.dom.exi.extcategory.specialedit;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;

public class SpecialEditNoEdit extends SpecialEdit{
	public SpecialEditNoEdit(boolean chkError, Object itemValue, Optional<AcceptMode> accMode,
			List<List<String>> lstLineData) {
		super(chkError, itemValue, accMode, lstLineData);
	}

	public SpecialEditValue edit() {
		SpecialEditValue result = new SpecialEditValue();
		result.setChkError(this.chkError);
		result.setEditValue(this.itemValue);
		return result;
	}
}
