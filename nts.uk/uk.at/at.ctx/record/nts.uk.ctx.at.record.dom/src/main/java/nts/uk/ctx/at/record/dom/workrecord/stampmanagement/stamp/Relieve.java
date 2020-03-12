package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * VO : 打刻する方法
 * @author tutk
 *
 */
@Value
public class Relieve implements DomainValue {

	/**
	 * 認証方法
	 */
	private final AuthcMethod authcMethod;//
	
	/**
	 * 打刻手段
	 */
	private final StampMeans stampMeans;//

	public Relieve(AuthcMethod authcMethod, StampMeans stampMeans) {
		super();
		this.authcMethod = authcMethod;
		this.stampMeans = stampMeans;
	}
	
}
