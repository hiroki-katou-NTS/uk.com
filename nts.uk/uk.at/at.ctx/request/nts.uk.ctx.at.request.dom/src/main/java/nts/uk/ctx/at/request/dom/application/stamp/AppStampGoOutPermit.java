package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 外出許可申請
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AppStampGoOutPermit extends DomainObject {
	
	private AppStampAtr stampAtr;
	
	// 打刻枠No
	private Integer stampFrameNo;
	
	private AppStampGoOutAtr stampGoOutAtr;
	
	// 開始時刻
	private Optional<TimeWithDayAttr> startTime;
	
	// 開始場所
	private Optional<String> startLocation;
	
	// 終了時刻
	private Optional<TimeWithDayAttr> endTime;
	
	// 終了場所
	private Optional<String> endLocation;
}
