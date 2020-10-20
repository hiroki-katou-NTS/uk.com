package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remain;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;

/**
 * 年休付与残数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveGrantRemaining extends AnnualLeaveGrantRemainingData {

	/** 年休不足ダミーフラグ */
	@Setter
	private boolean dummyAtr = false;
	
	public AnnualLeaveGrantRemaining(AnnualLeaveGrantRemainingData parent){
		super(parent.getAnnLeavID(),
				parent.getCid(),
				parent.getEmployeeId(),
				parent.getGrantDate(),
				parent.getDeadline(),
				parent.getExpirationStatus(),
				parent.getRegisterType(),
				parent.getDetails(),
				parent.getAnnualLeaveConditionInfo());
		this.dummyAtr = false;
	}
}
