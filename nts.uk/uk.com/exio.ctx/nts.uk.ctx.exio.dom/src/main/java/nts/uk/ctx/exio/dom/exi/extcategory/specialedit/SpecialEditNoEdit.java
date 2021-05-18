package nts.uk.ctx.exio.dom.exi.extcategory.specialedit;

import java.util.Optional;

import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;

public class SpecialEditNoEdit extends SpecialEdit{
	public SpecialEditNoEdit(boolean chkError, String editedItemValue, Optional<AcceptMode> accMode) {
		super(chkError, editedItemValue, accMode);
	}

	public SpecialEditValue edit() {
		SpecialEditValue result = new SpecialEditValue();
		result.setChkError(this.chkError);
		result.setEditValue(this.editedItemValue);
		return result;
	}
}
