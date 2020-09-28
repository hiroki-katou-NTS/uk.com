package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remain;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;

/**
 * 積立年休付与残数
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveGrantRemaining extends ReserveLeaveGrantRemainingData {

	/** 積立年休不足ダミーフラグ */
	@Setter
	private boolean dummyAtr = false;
	
	public ReserveLeaveGrantRemaining(ReserveLeaveGrantRemainingData parent){
		super(parent.getRsvLeaID(),
				parent.getEmployeeId(),
				parent.getGrantDate(),
				parent.getDeadline(),
				parent.getExpirationStatus(),
				parent.getRegisterType(),
				parent.getDetails());
		this.dummyAtr = false;
	}
}
