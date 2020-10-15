package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;

/**
 * 月別実績の項目丸め設定
 * @author shuichu_ishida
 */
@Getter
public class ItemRoundingSetOfMonthly {

	/** 会社ID */
	private String companyId;
	/** 勤怠項目ID */
	private int attendanceItemId;
	/** 丸め設定 */
	private TimeRoundingSetting roundingSet;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 * @param attendanceItemId 勤怠項目ID
	 */
	public ItemRoundingSetOfMonthly(String companyId, int attendanceItemId){
		
		this.companyId = companyId;
		this.attendanceItemId = attendanceItemId;
		this.roundingSet = new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN);
	}
	
	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param attendanceItemId 勤怠項目ID
	 * @param roundingSet 丸め設定
	 * @return 月別実績の項目丸め設定
	 */
	public static ItemRoundingSetOfMonthly of(
			String companyId, int attendanceItemId, TimeRoundingSetting roundingSet){
		
		ItemRoundingSetOfMonthly domain = new ItemRoundingSetOfMonthly(companyId, attendanceItemId);
		domain.roundingSet = roundingSet;
		return domain;
	}
}
