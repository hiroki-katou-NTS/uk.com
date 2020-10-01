package nts.uk.ctx.at.request.dom.application.applist.service.content;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ArrivedLateLeaveEarlyItemContent {
	
	/**
	 * 項目名
	 */
	private String itemName;
	
	/**
	 * 勤務NO
	 */
	private int workNo;
	
	/**
	 * 区分
	 */
	private LateOrEarlyAtr lateOrEarlyAtr;
	
	/**
	 * 時刻
	 */
	private Optional<TimeWithDayAttr> opTimeWithDayAttr;
	
	/**
	 * 取消
	 */
	private boolean cancellation;
}
