package nts.uk.ctx.exio.dom.exi.extcategory.specialedit;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;

@AllArgsConstructor
public abstract class SpecialEdit{
	protected boolean chkError;
	protected Object itemValue;
	protected Optional<AcceptMode> accMode;
	protected List<List<String>> lstLineData;
	
	public abstract SpecialEditValue edit();
}
