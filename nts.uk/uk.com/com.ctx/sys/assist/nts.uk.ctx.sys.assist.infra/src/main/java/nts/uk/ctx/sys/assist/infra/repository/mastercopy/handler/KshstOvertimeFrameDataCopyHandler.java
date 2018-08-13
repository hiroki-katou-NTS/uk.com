package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;

/**
 * The Class KshstOvertimeFrameDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KshstOvertimeFrameDataCopyHandler implements DataCopyHandler {
	
	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company cd. */
	private String companyCd;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		
		// TODO: Get all company zero data
		
		switch (copyMethod) {
			case REPLACE_ALL:
				// Delete all old data
			case ADD_NEW:
				// Insert Data
			case DO_NOTHING:
				// Do nothing
			default: 
				break;
		}
	}

}
