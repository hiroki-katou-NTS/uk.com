package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * 法定内休出振替順
 * @author shuichu_ishida
 */
@Getter
public class LegalHolidayWorkTransferOrder {

	/** 休出枠NO */
	private HolidayWorkFrameNo holidayWorkFrameNo;
	/** 並び順 */
	private int sortOrder;

	/**
	 * ファクトリー
	 * @param holidayWorkFrameNo 休出枠NO
	 * @param sortOrder 並び順
	 * @return 法定内休出振替順
	 */
	public static LegalHolidayWorkTransferOrder of(
			HolidayWorkFrameNo holidayWorkFrameNo,
			int sortOrder){
		
		val domain = new LegalHolidayWorkTransferOrder();
		domain.holidayWorkFrameNo = holidayWorkFrameNo;
		domain.sortOrder = sortOrder;
		return domain;
	}
}
