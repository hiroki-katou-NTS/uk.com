package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.HalfdayAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

/**
 * 半日年休
 * @author shuichu_ishida
 */
@Getter
@Setter
@AllArgsConstructor
public class HalfDayAnnualLeave implements Cloneable, Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/** 残数 */
	private HalfDayAnnLeaRemainingNum remainingNum;
	/** 使用数 */
	private HalfDayAnnLeaUsedNum usedNum;
	
	/**
	 * コンストラクタ
	 */
	public HalfDayAnnualLeave(){
		
		this.remainingNum = new HalfDayAnnLeaRemainingNum();
		this.usedNum = new HalfDayAnnLeaUsedNum();
	}
	
	/**
	 * ファクトリー
	 * @param remainingNum 残数
	 * @param usedNum 使用数
	 * @return 半日年休
	 */
	public static HalfDayAnnualLeave of(
			HalfDayAnnLeaRemainingNum remainingNum,
			HalfDayAnnLeaUsedNum usedNum){
		
		HalfDayAnnualLeave domain = new HalfDayAnnualLeave();
		domain.remainingNum = remainingNum;
		domain.usedNum = usedNum;
		return domain;
	}
	
	@Override
	public HalfDayAnnualLeave clone() {
		HalfDayAnnualLeave cloned = new HalfDayAnnualLeave();
		try {
			cloned.remainingNum = this.remainingNum.clone();
			cloned.usedNum = this.usedNum.clone();
		}
		catch (Exception e){
			throw new RuntimeException("HalfDayAnnualLeave clone error.");
		}
		return cloned;
	}
	
	//[1]更新する
	public HalfDayAnnualLeave update(HalfdayAnnualLeaveMax maxData, GrantBeforeAfterAtr grantPeriodAtr){
		HalfDayAnnLeaUsedNum usedNum = this.usedNum.update(maxData, grantPeriodAtr) ;
		HalfDayAnnLeaRemainingNum remainNum = this.remainingNum.update(maxData, grantPeriodAtr) ;
		
		return new HalfDayAnnualLeave(remainNum, usedNum);
		
	}
	//[2]残数超過分を補正する
	public HalfDayAnnualLeave correctTheExcess(){
		HalfDayAnnLeaUsedNum usedNum = this.usedNum.correctTheExcess(remainingNum);
		HalfDayAnnLeaRemainingNum remainNum = this.remainingNum.correctTheExcess();
		
		return new HalfDayAnnualLeave(remainNum, usedNum);
	}
}
