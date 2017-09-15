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
public class ApplicationStampOnlineRecord extends DomainObject {
	private StampCombinationAtr stampCombinationAtr;
	
	/**
	 * 勤怠時刻
	 */
	private Integer appTime;

	public ApplicationStampOnlineRecord(StampCombinationAtr stampCombinationAtr, Integer appTime) {
		super();
		this.stampCombinationAtr = stampCombinationAtr;
		this.appTime = appTime;
	}
}
