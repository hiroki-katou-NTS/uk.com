package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 法定内残業振替順
 * @author shuichu_ishida
 */
@Getter
public class LegalOverTimeTransferOrder {
	
	/** 残業枠NO */
	private OverTimeFrameNo overTimeFrameNo;
	/** 並び順 */
	private int sortOrder;
	
	/**
	 * ファクトリー
	 * @param overTimeFrameNo 残業枠NO
	 * @param sortOrder 並び順
	 * @return 法定内残業振替順
	 */
	public static LegalOverTimeTransferOrder of(
			OverTimeFrameNo overTimeFrameNo,
			int sortOrder){
		
		val domain = new LegalOverTimeTransferOrder();
		domain.overTimeFrameNo = overTimeFrameNo;
		domain.sortOrder = sortOrder;
		return domain;
	}
}
