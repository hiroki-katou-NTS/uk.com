package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;

/**
 * 月次集計の法定内残業振替順
 * @author shuichu_ishida
 */
@Getter
public class LegalOverTimeTransferOrderOfAggrMonthly {

	/** 法定内残業振替順 */
	private List<LegalOverTimeTransferOrder> legalOverTimeTransferOrders;
	
	/**
	 * コンストラクタ
	 */
	public LegalOverTimeTransferOrderOfAggrMonthly(){
		
		this.legalOverTimeTransferOrders = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param legalOverTimeTransferOrders 法定内残業時間順
	 * @return 月次集計の法定内残業振替順
	 */
	public static LegalOverTimeTransferOrderOfAggrMonthly of(
			List<LegalOverTimeTransferOrder> legalOverTimeTransferOrders){
		
		val domain = new LegalOverTimeTransferOrderOfAggrMonthly();
		domain.legalOverTimeTransferOrders = legalOverTimeTransferOrders;
		domain.legalOverTimeTransferOrders.sort((a, b)-> a.getSortOrder() - b.getSortOrder());
		return domain;
	}
}
