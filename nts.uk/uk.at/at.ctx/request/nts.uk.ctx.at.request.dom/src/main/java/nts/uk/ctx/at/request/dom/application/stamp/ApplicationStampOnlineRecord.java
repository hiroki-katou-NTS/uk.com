package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.EqualsAndHashCode;
import lombok.Value;

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
public class ApplicationStampOnlineRecord {
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
