package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;

/**
 * 育児外出
 * @author shuichu_ishida
 */
@Getter
public class GoOutForChildCare {

	/** 育児介護区分 */
	private ChildCareAtr childCareAtr;
	/** 回数 */
	private AttendanceTimesMonth times;
	/** 時間 */
	private AttendanceTimeMonth time;
	
	/**
	 * コンストラクタ
	 */
	public GoOutForChildCare(){
		
		this.childCareAtr = ChildCareAtr.CHILD_CARE;
		this.times = new AttendanceTimesMonth(0);
		this.time = new AttendanceTimeMonth(0);
	}

	/**
	 * ファクトリー
	 * @param childCareAtr 育児介護区分
	 * @param times 回数
	 * @param time 時間
	 * @return 育児外出
	 */
	public static GoOutForChildCare of(
			ChildCareAtr childCareAtr,
			AttendanceTimesMonth times,
			AttendanceTimeMonth time){
		
		val domain = new GoOutForChildCare();
		domain.childCareAtr = childCareAtr;
		domain.times = times;
		domain.time = time;
		return domain;
	}
}
