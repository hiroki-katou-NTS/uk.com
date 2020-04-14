package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * VO : 打刻する方法
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻する方法
 * @author tutk
 *
 */
public class Relieve implements DomainValue {

	/**
	 * 認証方法
	 * 打刻方法 1 old
	 */
	@Getter
	private final AuthcMethod authcMethod;
	
	/**
	 * 打刻手段
	 * 打刻方法 2 old
	 */
	@Getter
	private final StampMeans stampMeans;

	public Relieve(AuthcMethod authcMethod, StampMeans stampMeans) {
		super();
		this.authcMethod = authcMethod;
		this.stampMeans = stampMeans;
	}
	
}
