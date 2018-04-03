package nts.uk.ctx.at.shared.app.find.entranceexit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitSetMemento;

/**
 * @author hoangdd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ManageEntryExitDto implements ManageEntryExitSetMemento {
	
	int useClassification;

	@Override
	public void setCompanyID(String companyID) {
		// Do nothing
	}

	@Override
	public void setUseCls(int useCls) {
		this.useClassification = useCls;
	}

}

