package nts.uk.ctx.at.record.dom.monthly.vacation.holidayover60h;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedTime;

/**
 * 60H超休
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class HolidayOver60h implements Cloneable, Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 使用時間
	*/
	private AnnualLeaveUsedTime usedTime;

	/**
	 * 残時間 */
	private AnnualLeaveRemainingTime remainingTime;

	/**
	 * コンストラクタ
	 */
	public HolidayOver60h(){
		this.usedTime = new AnnualLeaveUsedTime(0);
		this.remainingTime = new AnnualLeaveRemainingTime(0);
	}

}
