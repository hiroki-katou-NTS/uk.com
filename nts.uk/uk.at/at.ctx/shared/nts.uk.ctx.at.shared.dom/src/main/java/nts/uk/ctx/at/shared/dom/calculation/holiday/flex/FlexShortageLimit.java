package nts.uk.ctx.at.shared.dom.calculation.holiday.flex;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * フレックス不足の繰越上限管理
 * @author shuichi_ishida
 */
@Getter
public class FlexShortageLimit extends AggregateRoot {

	/** 会社ID */
	private String companyId;
	/** 繰越上限時間 */
	private AttendanceTime limitTime;
	
	public FlexShortageLimit(String companyId){
		this.companyId = companyId;
		this.limitTime = new AttendanceTime(0);
	}

	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param limitTime 繰越上限時間
	 * @return フレックス不足の繰越上限時間
	 */
	public static FlexShortageLimit of(
			String companyId,
			AttendanceTime limitTime){
		
		FlexShortageLimit domain = new FlexShortageLimit(companyId);
		domain.limitTime = limitTime;
		return domain;
	}
}
