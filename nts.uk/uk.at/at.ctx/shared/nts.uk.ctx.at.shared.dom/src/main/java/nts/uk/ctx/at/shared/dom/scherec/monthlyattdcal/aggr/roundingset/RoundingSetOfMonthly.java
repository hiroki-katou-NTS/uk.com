package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit.Direction;

/**
 * 月別実績の丸め設定
 * @author shuichu_ishida
 */
@Getter
public class RoundingSetOfMonthly extends AggregateRoot {

	/** 会社ID */
	private String companyId;
	/** 項目丸め設定 */
	private Map<Integer, ItemRoundingSetOfMonthly> itemRoundingSet;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public RoundingSetOfMonthly(String companyId){
		
		super();
		this.companyId = companyId;
		this.itemRoundingSet = new HashMap<>();
	}

	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param itemRoundingSets 項目丸め設定
	 * @return 月別実績の丸め設定
	 */
	public static RoundingSetOfMonthly of(
			String companyId,
			List<ItemRoundingSetOfMonthly> itemRoundingSets){

		RoundingSetOfMonthly domain = new RoundingSetOfMonthly(companyId);
		for (val itemRoundingSet : itemRoundingSets){
			val attendanceItemId = itemRoundingSet.getAttendanceItemId();
			domain.itemRoundingSet.putIfAbsent(attendanceItemId, itemRoundingSet);
		}
		return domain;
	}


	/**
	 * 項目丸め
	 * @param attendanceItemId 勤怠項目ID
	 * @param attendanceTimeMonth 勤怠月間時間　（丸め前）
	 * @return 勤怠月間時間　（丸め後）
	 */
	public AttendanceTimeMonth itemRound(int attendanceItemId, AttendanceTimeMonth attendanceTimeMonth){

		int minutes = attendanceTimeMonth.v();
		if (!this.itemRoundingSet.containsKey(attendanceItemId)) return attendanceTimeMonth;
		val targetRoundingSet = this.itemRoundingSet.get(attendanceItemId);
		return new AttendanceTimeMonth(targetRoundingSet.getRoundingSet().round(minutes));
	}
}
