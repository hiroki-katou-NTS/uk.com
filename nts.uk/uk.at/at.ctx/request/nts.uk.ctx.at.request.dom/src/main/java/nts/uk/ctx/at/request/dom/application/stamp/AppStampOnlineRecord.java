package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author Doan Duy Hung
 *
 */
/**
 * 
 * レコーダイメージ申請
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class AppStampOnlineRecord extends DomainObject {
	private AppStampCombinationAtr stampCombinationAtr;
	
	/**
	 * 勤怠時刻
	 */
	private Integer appTime;

	public AppStampOnlineRecord(AppStampCombinationAtr stampCombinationAtr, Integer appTime) {
		super();
		this.stampCombinationAtr = stampCombinationAtr;
		this.appTime = appTime;
	}
}
