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
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "";
	
	/**
	 * Instantiates a new kshst overtime frame data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyCd the company cd
	 */
	public KshstOvertimeFrameDataCopyHandler(CopyMethod copyMethod, String companyId) {
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

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
