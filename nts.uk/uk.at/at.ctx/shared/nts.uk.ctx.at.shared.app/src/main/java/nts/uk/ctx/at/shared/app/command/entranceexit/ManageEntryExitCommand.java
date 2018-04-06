package nts.uk.ctx.at.shared.app.command.entranceexit;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitGetMemento;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author hoangdd
 *
 */
//@NoArgsConstructor
@Data
public class ManageEntryExitCommand implements ManageEntryExitGetMemento{
	
	/** The use classification. */
	private int useClassification1;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitGetMemento#getUseCls()
	 */
	@Override
	public NotUseAtr getUseCls() {
		return NotUseAtr.valueOf(this.useClassification1);
	}

//	public ManageEntryExitCommand(int useClassification1) {
//		super();
//		this.useClassification1 = useClassification1;
//	}
}

