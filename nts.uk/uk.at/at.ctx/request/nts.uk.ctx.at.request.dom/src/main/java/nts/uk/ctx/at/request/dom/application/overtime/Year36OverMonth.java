package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;

/**
 * 36年間超過月
 */
@Getter
@Setter
public class Year36OverMonth extends DomainObject {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 申請ID
	 */
	private String appId;

	/**
	 * 36年間超過月
	 */
	private YearMonth overMonth;

	public Year36OverMonth(String cid, String appId, int overMonth) {
		super();
		this.cid = cid;
		this.appId = appId;
		this.overMonth = new YearMonth(overMonth);
	}

}
