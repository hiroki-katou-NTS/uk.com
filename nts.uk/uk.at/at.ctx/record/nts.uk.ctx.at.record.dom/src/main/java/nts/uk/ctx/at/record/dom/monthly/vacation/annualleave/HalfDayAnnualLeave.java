package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import lombok.Getter;
import lombok.Setter;

/**
 * 半日年休
 * @author shuichu_ishida
 */
@Getter
@Setter
public class HalfDayAnnualLeave {

	/** 残日数 */
	private HalfDayAnnLeaRemainingNum remainingDays;
	/** 使用日数 */
	private HalfDayAnnLeaUsedNum usedDays;
	
	/**
	 * コンストラクタ
	 */
	public HalfDayAnnualLeave(){
		
		this.remainingDays = new HalfDayAnnLeaRemainingNum();
		this.usedDays = new HalfDayAnnLeaUsedNum();
	}
	
	/**
	 * ファクトリー
	 * @param remainingDays 残日数
	 * @param usedDays 使用日数
	 * @return 半日年休
	 */
	public static HalfDayAnnualLeave of(
			HalfDayAnnLeaRemainingNum remainingDays,
			HalfDayAnnLeaUsedNum usedDays){
		
		HalfDayAnnualLeave domain = new HalfDayAnnualLeave();
		domain.remainingDays = remainingDays;
		domain.usedDays = usedDays;
		return domain;
	}
}
