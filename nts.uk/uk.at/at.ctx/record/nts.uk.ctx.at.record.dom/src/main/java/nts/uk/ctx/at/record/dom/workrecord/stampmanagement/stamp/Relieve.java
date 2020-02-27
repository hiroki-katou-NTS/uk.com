package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * VO : 打刻する方法
 * @author tutk
 *
 */
@Value
public class Relieve implements DomainObject {

	/**
	 * 認証方法
	 */
	private final int authcMethod;//
	
	/**
	 * 打刻手段
	 */
	private final int stampMeans;//

	
	
}
