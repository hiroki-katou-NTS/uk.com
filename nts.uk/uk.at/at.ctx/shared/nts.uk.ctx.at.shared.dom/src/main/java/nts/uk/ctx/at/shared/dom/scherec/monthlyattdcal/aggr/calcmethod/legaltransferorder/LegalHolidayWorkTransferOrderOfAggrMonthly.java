package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;

/**
 * 月次集計の法定内休出振替順
 * @author shuichu_ishida
 */
@Getter
public class LegalHolidayWorkTransferOrderOfAggrMonthly {

	/** 法定内休出振替順 */
	private List<LegalHolidayWorkTransferOrder> legalHolidayWorkTransferOrders;
	
	/**
	 * コンストラクタ
	 */
	public LegalHolidayWorkTransferOrderOfAggrMonthly(){
		
		this.legalHolidayWorkTransferOrders = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param legalHolidayWorkTransferOrders 法定内休出振替順
	 * @return 月次集計の法定内休出振替順
	 */
	public static LegalHolidayWorkTransferOrderOfAggrMonthly of(
			List<LegalHolidayWorkTransferOrder> legalHolidayWorkTransferOrders){
		
		val domain = new LegalHolidayWorkTransferOrderOfAggrMonthly();
		domain.legalHolidayWorkTransferOrders = legalHolidayWorkTransferOrders;
		domain.legalHolidayWorkTransferOrders.sort((a, b)-> a.getSortOrder() - b.getSortOrder());
		return domain;
	}
}
