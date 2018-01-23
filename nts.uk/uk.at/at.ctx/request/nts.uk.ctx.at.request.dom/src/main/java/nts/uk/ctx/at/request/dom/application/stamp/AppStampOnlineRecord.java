package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
@Getter
@AllArgsConstructor
public class AppStampOnlineRecord extends DomainObject {
	private AppStampCombinationAtr stampCombinationAtr;
	
	// 勤怠時刻
	private Integer appTime;

}
