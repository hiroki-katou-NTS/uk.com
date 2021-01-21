package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * 休暇加算設定
 * @author shuichu_ishida
 */
@Getter
public class VacationAddSet {

	/** 年休 */
	private boolean annualLeave;
	/** 積立年休 */
	private boolean retentionYearly;
	/** 特別休暇 */
	private List<String> specialHolidays;
	
	/**
	 * コンストラクタ
	 */
	public VacationAddSet(){
		
		this.annualLeave = false;
		this.retentionYearly = false;
		this.specialHolidays = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param annualLeave 年休
	 * @param retentionYearly 積立年休
	 * @param specialHolidays 特別休暇
	 * @return 休暇加算設定
	 */
	public static VacationAddSet of(
			boolean annualLeave,
			boolean retentionYearly,
			List<String> specialHolidays){

		VacationAddSet domain = new VacationAddSet();
		domain.annualLeave = annualLeave;
		domain.retentionYearly = retentionYearly;
		domain.specialHolidays = specialHolidays;
		return domain;
	}
}
