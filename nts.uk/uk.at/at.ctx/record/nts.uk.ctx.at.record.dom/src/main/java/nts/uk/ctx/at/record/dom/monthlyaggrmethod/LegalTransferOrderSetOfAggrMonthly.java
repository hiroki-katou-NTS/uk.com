package nts.uk.ctx.at.record.dom.monthlyaggrmethod;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalHolidayWorkTransferOrderOfAggrMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalOverTimeTransferOrderOfAggrMonthly;

/**
 * 月次集計の法定内振替順設定
 * @author shuichu_ishida
 */
@Getter
public class LegalTransferOrderSetOfAggrMonthly extends AggregateRoot {

	/** 会社ID */
	private final String companyId;

	/** 法定内残業振替順 */
	private LegalOverTimeTransferOrderOfAggrMonthly legalOverTimeTransferOrder;
	/** 法定内休出振替順 */
	private LegalHolidayWorkTransferOrderOfAggrMonthly legalHolidayWorkTransferOrder;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public LegalTransferOrderSetOfAggrMonthly(String companyId){
		
		super();
		this.companyId = companyId;
		this.legalOverTimeTransferOrder = new LegalOverTimeTransferOrderOfAggrMonthly();
		this.legalHolidayWorkTransferOrder = new LegalHolidayWorkTransferOrderOfAggrMonthly();
	}

	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param legalOverTimeTransferOrder 法定内残業振替順
	 * @param legalHolidayWorkTransferOrder 法定内休出振替順
	 * @return 月次集計の法定内振替順設定
	 */
	public static LegalTransferOrderSetOfAggrMonthly of(
			String companyId,
			LegalOverTimeTransferOrderOfAggrMonthly legalOverTimeTransferOrder,
			LegalHolidayWorkTransferOrderOfAggrMonthly legalHolidayWorkTransferOrder,
			AggrSettingMonthlyOfFlx aggrSettingMonthlyOfFlx){
		
		val domain = new LegalTransferOrderSetOfAggrMonthly(companyId);
		domain.legalOverTimeTransferOrder = legalOverTimeTransferOrder;
		domain.legalHolidayWorkTransferOrder = legalHolidayWorkTransferOrder;
		return domain;
	}
}
