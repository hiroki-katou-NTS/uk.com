package nts.uk.ctx.sys.portal.dom.layout;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.layout.enums.PGType;

@Value
@EqualsAndHashCode(callSuper=false)
public class Layout extends AggregateRoot {
	
	/** Company ID */
	String companyID;
	
	/** Layout ID */
	String layoutID;
	
	/** PG Type */
	PGType pgType;
	
	
}