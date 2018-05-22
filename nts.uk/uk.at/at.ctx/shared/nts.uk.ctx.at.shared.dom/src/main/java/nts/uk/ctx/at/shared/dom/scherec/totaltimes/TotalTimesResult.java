package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 回数集計結果情報
 * @author shuichu_ishida
 */
@Getter
@Setter
public class TotalTimesResult {

	/** 回数 */
	private AttendanceDaysMonth count;
	/** 時間 */
	private AttendanceTimeMonth time;

	/**
	 * コンストラクタ
	 */
	public TotalTimesResult(){
		
		this.count = new AttendanceDaysMonth(0.0);
		this.time = new AttendanceTimeMonth(0);
	}

	/**
	 * ファクトリー
	 * @param count 回数
	 * @param time　時間
	 * @return 回数集計結果情報
	 */
	public static TotalTimesResult of(
			AttendanceDaysMonth count,
			AttendanceTimeMonth time){
		
		TotalTimesResult domain = new TotalTimesResult();
		domain.count = count;
		domain.time = time;
		return domain;
	}
	
	/**
	 * 回数を加算する
	 * @param count 回数
	 */
	public void addCount(double count){
		this.count = this.count.addDays(count);
	}
	
	/**
	 * 時間を加算する
	 * @param minutes 時間（分）
	 */
	public void addTime(int minutes){
		this.time = this.time.addMinutes(minutes);
	}
}
