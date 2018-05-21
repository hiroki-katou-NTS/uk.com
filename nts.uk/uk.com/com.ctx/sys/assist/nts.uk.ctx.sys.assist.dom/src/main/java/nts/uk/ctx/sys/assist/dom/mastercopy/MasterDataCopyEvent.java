package nts.uk.ctx.sys.assist.dom.mastercopy;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.event.DomainEvent;

@Value
@EqualsAndHashCode(callSuper = false)
public class MasterDataCopyEvent extends DomainEvent{

	/** The company id. */
	private String companyId;
	
	/** The copy method. */
	private int copyMethod;

}
