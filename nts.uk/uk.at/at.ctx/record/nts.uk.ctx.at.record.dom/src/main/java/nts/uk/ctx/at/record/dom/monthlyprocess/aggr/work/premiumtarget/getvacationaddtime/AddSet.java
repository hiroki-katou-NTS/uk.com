package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime;

import lombok.Getter;
import lombok.val;

/**
 * 加算設定　（割増計算用）
 * @author shuichu_ishida
 */
@Getter
public class AddSet {

	/** 年休 */
	private boolean annualLeave;
	/** 積立年休 */
	private boolean retentionYearly;
	/** 特別休暇 */
	private boolean specialHoliday;
	
	/**
	 * コンストラクタ
	 */
	public AddSet(){
		
		this.annualLeave = false;
		this.retentionYearly = false;
		this.specialHoliday = false;
	}

	/**
	 * ファクトリー
	 * @param annualLeave 年休
	 * @param retentionYearly 積立休暇
	 * @param specialHoliday 特別休暇
	 * @return 加算設定
	 */
	public static AddSet of(boolean annualLeave, boolean retentionYearly, boolean specialHoliday){
		
		val domain = new AddSet();
		domain.annualLeave = annualLeave;
		domain.retentionYearly = retentionYearly;
		domain.specialHoliday = specialHoliday;
		return domain;
	}
}
