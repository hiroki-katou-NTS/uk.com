package nts.uk.ctx.at.record.pubimp.workrecord.closurestatus;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.export.GetCalcStartForNextLeaveGrant;
import nts.uk.ctx.at.record.pub.workrecord.closurestatus.GetCalcStartForNextLeaveGrantPub;

/**
 * 次回年休付与を計算する開始日を取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetCalcStartForNextLeaveGrantPubImpl implements GetCalcStartForNextLeaveGrantPub {

	/** 次回年休付与を計算する開始日を取得する */
	private GetCalcStartForNextLeaveGrant getCalcStart;
	
	/** 次回年休付与を計算する開始日を取得する */
	@Override
	public GeneralDate algorithm(String employeeId) {
		return this.getCalcStart.algorithm(employeeId);
	}
}
