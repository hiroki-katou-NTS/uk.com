package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditMethod;

/**
 * @author nws_namnv2
 *
 */
public class SupportCardEditHelper {
	
	public static SupportCardEdit getDataDefault(){
		return new SupportCardEdit(EnumAdaptor.valueOf(1, StampCardEditMethod.class));
	}

}
