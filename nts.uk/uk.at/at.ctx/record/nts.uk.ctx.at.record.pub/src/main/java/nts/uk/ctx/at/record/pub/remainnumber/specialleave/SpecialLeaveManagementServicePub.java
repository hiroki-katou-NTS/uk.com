package nts.uk.ctx.at.record.pub.remainnumber.specialleave;

import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.InPeriodOfSpecialLeaveResultInforExport;

/**
 *
 * @author masaaki_jinno
 *
 */
public interface SpecialLeaveManagementServicePub {

	/**
	 * 期間内の特別休暇残を集計する
	 * @param param
	 * @return
	 */
	public InPeriodOfSpecialLeaveResultInforExport complileInPeriodOfSpecialLeave(
			ComplileInPeriodOfSpecialLeavePubParam param);
}
