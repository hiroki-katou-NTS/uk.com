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
	/** 時間外超過の時間丸め */
	private Optional<TimeRoundingOfExcessOutsideTime> timeRoundingOfExcessOutsideTime;
	/** 項目丸め設定 */
	private Map<Integer, ItemRoundingSetOfMonthly> itemRoundingSet;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public RoundingSetOfMonthly(String companyId){
		
		super();
		this.companyId = companyId;
		this.timeRoundingOfExcessOutsideTime = Optional.empty();
		this.itemRoundingSet = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param timeRoundingOfExcessOutsideTime 時間外超過の時間丸め
	 * @param itemRoundingSets 項目丸め設定
	 * @return 月別実績の丸め設定
	 */
	public static RoundingSetOfMonthly of(
			String companyId,
			Optional<TimeRoundingOfExcessOutsideTime> timeRoundingOfExcessOutsideTime,
			List<ItemRoundingSetOfMonthly> itemRoundingSets){

		RoundingSetOfMonthly domain = new RoundingSetOfMonthly(companyId);
		domain.timeRoundingOfExcessOutsideTime = timeRoundingOfExcessOutsideTime;
		for (val itemRoundingSet : itemRoundingSets){
			val attendanceItemId = itemRoundingSet.getAttendanceItemId();
			domain.itemRoundingSet.putIfAbsent(attendanceItemId, itemRoundingSet);
		}
		return domain;
	}

	/**
	 * 時間外超過丸め
	 * @param attendanceItemId 勤怠項目ID
	 * @param attendanceTimeMonth 勤怠月間時間　（丸め前）
	 * @return 勤怠月間時間　（丸め後）
	 */
	public AttendanceTimeMonth excessOutsideRound(int attendanceItemId, AttendanceTimeMonth attendanceTimeMonth){

		int minutes = attendanceTimeMonth.v();
		
		// 時間外超過の時間丸めが設定されていない時、項目丸め設定を参照する
		if (!this.timeRoundingOfExcessOutsideTime.isPresent()){
			return this.itemRound(attendanceItemId, attendanceTimeMonth);
		}

		val excessOutsideRoundSet = this.timeRoundingOfExcessOutsideTime.get();
		switch (excessOutsideRoundSet.getRoundingProcess()) {
		case ROUNDING_DOWN:
			minutes = excessOutsideRoundSet.getRoundingUnit().round(minutes, Direction.TO_BACK);
			break;
		case ROUNDING_UP:
			minutes = excessOutsideRoundSet.getRoundingUnit().round(minutes, Direction.TO_FORWARD);
			break;
		case FOLLOW_ELEMENTS:
			return this.itemRound(attendanceItemId, attendanceTimeMonth);
		}
		return new AttendanceTimeMonth(minutes);
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
