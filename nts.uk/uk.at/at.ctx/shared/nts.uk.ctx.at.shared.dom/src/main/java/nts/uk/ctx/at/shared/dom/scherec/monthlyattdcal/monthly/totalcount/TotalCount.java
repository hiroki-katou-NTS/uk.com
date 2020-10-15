package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.totalcount;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 回数集計
 * @author shuichi_ishida
 */
@Getter
public class TotalCount implements Cloneable, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 回数集計No */
	private int totalCountNo;
	
	/** 回数 */
	@Setter
	private AttendanceDaysMonth count;
	/** 時間 */
	@Setter
	private AttendanceTimeMonth time;
	
	/**
	 * コンストラクタ
	 * @param totalCountNo 回数集計No
	 */
	public TotalCount(int totalCountNo){
		
		this.totalCountNo = totalCountNo;
		
		this.count = new AttendanceDaysMonth(0.0);
		this.time = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param totalCountNo 回数集計No
	 * @param count 回数
	 * @param time 時間
	 * @return
	 */
	public static TotalCount of(
			int totalCountNo,
			AttendanceDaysMonth count,
			AttendanceTimeMonth time){
		
		TotalCount domain = new TotalCount(totalCountNo);
		domain.count = count;
		domain.time = time;
		return domain;
	}
	
	@Override
	public TotalCount clone() {
		TotalCount cloned = new TotalCount(this.totalCountNo);
		try {
			cloned.count = new AttendanceDaysMonth(this.count.v());
			cloned.time = new AttendanceTimeMonth(this.time.v());
		}
		catch (Exception e){
			throw new RuntimeException("TotalCount clone error.");
		}
		return cloned;
	}
	
	/**
	 * 合算する
	 * @param target　加算対象
	 */
	public void sum(TotalCount target){

		this.count = this.count.addDays(target.count.v());
		this.time = this.time.addMinutes(target.time.v());
	}
}
