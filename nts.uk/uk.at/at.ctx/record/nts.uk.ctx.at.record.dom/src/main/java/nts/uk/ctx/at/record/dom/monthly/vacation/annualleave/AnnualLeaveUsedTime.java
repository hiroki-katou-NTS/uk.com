package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.io.Serializable;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;

@Getter
public class AnnualLeaveUsedTime implements Cloneable, Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/** 使用時間 */
	private UsedMinutes usedTime;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUsedTime(){
		this.usedTime = new UsedMinutes(0);
	}

	/**
	 * ファクトリー
	 * @param usedTime 使用時間
	 * @return 時間年休使用時間
	 */
	public static AnnualLeaveUsedTime of(
			UsedMinutes usedTime){
		
		AnnualLeaveUsedTime domain = new AnnualLeaveUsedTime();
		domain.usedTime = usedTime;
		return domain;
	}
	
	@Override
	protected AnnualLeaveUsedTime clone() {
		AnnualLeaveUsedTime cloned = new AnnualLeaveUsedTime();
		try {
			cloned.usedTime = new UsedMinutes(this.usedTime.v());
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveUsedTime clone error.");
		}
		return cloned;
	}
}
