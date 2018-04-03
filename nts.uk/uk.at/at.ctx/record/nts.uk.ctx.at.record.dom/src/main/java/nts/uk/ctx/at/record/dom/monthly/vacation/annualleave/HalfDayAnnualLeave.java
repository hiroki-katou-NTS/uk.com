package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import lombok.Getter;

/**
 * 半日年休
 * @author shuichu_ishida
 */
@Getter
public class HalfDayAnnualLeave {

	/** 残日数 */
	private HalfDayAnnLeaRemainingNum remainingDays;
	/** 使用日数 */
	private HalfDayAnnLeaUsedNum usedDays;
	
	/**
	 * コンストラクタ
	 */
	public HalfDayAnnualLeave(){
		
		this.remainingDays = new HalfDayAnnLeaRemainingNum(0.0);
		this.usedDays = new HalfDayAnnLeaUsedNum(0.0);
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
