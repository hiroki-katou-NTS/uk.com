package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 出退勤申請
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AppStampWork extends DomainObject {
	private AppStampAtr stampAtr;
	
	// 打刻枠No
	private Integer stampFrameNo;
	
	private AppStampGoOutAtr stampGoOutAtr;
	
	// 応援カード
	private Optional<String> supportCard;
		
	// 応援場所
	private Optional<String> supportLocationCD;
	
	// 開始時刻
	private Optional<TimeWithDayAttr> startTime;
	
	// 開始場所
	private Optional<String> startLocation;
	
	// 終了時刻
	private Optional<TimeWithDayAttr> endTime;
	
	// 終了場所
	private Optional<String> endLocation;
	
}
