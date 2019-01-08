package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime;

import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;

/**
 * 加算設定　（割増計算用）
 * @author shuichi_ishida
 */
@Getter
public class AddSet {

	/** 年休 */
	private boolean annualLeave;
	/** 積立年休 */
	private boolean retentionYearly;
	/** 特別休暇 */
	private boolean specialHoliday;
	/** エラー情報 */
	private Optional<MonthlyAggregationErrorInfo> errorInfo;
	
	/**
	 * コンストラクタ
	 */
	public AddSet(){
		
		this.annualLeave = false;
		this.retentionYearly = false;
		this.specialHoliday = false;
		this.errorInfo = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param annualLeave 年休
	 * @param retentionYearly 積立休暇
	 * @param specialHoliday 特別休暇
	 * @param errorInfo エラー情報
	 * @return 加算設定
	 */
	public static AddSet of(boolean annualLeave, boolean retentionYearly, boolean specialHoliday,
			Optional<MonthlyAggregationErrorInfo> errorInfo){
		
		val domain = new AddSet();
		domain.annualLeave = annualLeave;
		domain.retentionYearly = retentionYearly;
		domain.specialHoliday = specialHoliday;
		domain.errorInfo = errorInfo;
		return domain;
	}
}
