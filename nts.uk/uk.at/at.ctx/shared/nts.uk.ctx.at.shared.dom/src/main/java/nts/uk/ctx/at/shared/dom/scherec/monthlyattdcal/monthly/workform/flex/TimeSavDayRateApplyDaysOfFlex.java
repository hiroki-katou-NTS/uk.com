package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * フレックス勤務の時短日割適用日数
 * @author shuichu_ishida
 */
@Getter
public class TimeSavDayRateApplyDaysOfFlex extends AggregateRoot {

	/** 会社ID */
	private final String companyId;
	
	/** 日数 */
	private TimeSavDayRateApplyDays days;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public TimeSavDayRateApplyDaysOfFlex(String companyId){
		
		super();
		this.companyId = companyId;
		this.days = new TimeSavDayRateApplyDays(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param days 日数
	 * @return フレックス勤務の時短日割適用日数
	 */
	public static TimeSavDayRateApplyDaysOfFlex of(String companyId, TimeSavDayRateApplyDays days){
		
		TimeSavDayRateApplyDaysOfFlex domain = new TimeSavDayRateApplyDaysOfFlex(companyId);
		domain.days = days;
		return domain;
	}
}
