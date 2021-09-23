package nts.uk.ctx.exio.dom.exi.canonicalize.specialedit;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;

@AllArgsConstructor
public abstract class SpecialEdit{
	protected boolean chkError;
	protected String editedItemValue;
	protected Optional<AcceptMode> accMode;
	
	public abstract SpecialEditValue edit();
}
