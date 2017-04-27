package nts.uk.ctx.sys.portal.dom.toppage;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class Placement.
 */
@Getter
public class Placement extends DomainObject {

	/** The column. */
	private Integer column;
	
	/** The row. */
	private Integer row;
	
	/** The top page part. */
	private TopPagePart topPagePart;
	
	/** The external URL. */
	private String externalURL;
}
