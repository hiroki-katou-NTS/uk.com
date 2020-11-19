package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 回数集計結果
 * @author shuichi_ishida
 */
@Getter
public class TotalCount implements Cloneable, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 大塚カスタマイズ：集計対象勤務種別 */
	public static String WORKTYPE_A = "0000000110";
	/** 大塚カスタマイズ：集計対象勤務種別 */
	public static String WORKTYPE_B = "0000000210";

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
	
	/**
	 * 大塚カスタマイズの条件判断
	 * @param totalCountNo 回数集計No
	 * @param workTypeCode 勤務種別コード
	 * @return true:対象、false:対象外
	 */
	public boolean checkOotsukaCustomize(int totalCountNo, String workTypeCode){
		
		// 回数集計NOの判断
		if (totalCountNo != 25 && totalCountNo != 29) return true;
		
		// 勤務種別コードの判断
		if (workTypeCode.compareTo(WORKTYPE_A) == 0) return true;
		if (workTypeCode.compareTo(WORKTYPE_B) == 0) return true;
		
		return false;
	}
}
