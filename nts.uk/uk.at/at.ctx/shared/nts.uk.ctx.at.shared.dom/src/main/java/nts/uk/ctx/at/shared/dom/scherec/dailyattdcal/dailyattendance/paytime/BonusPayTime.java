package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;

/**
 * 加給時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class BonusPayTime {
	//加給項目ＮＯ
	private int BonusPayTimeItemNo;
	//加給時間
	@Setter
	private AttendanceTime bonusPayTime;
	//所定内加給
	private TimeWithCalculation withinBonusPay;
	//所定外加給
	private TimeWithCalculation excessBonusPayTime;
	
	/**
	 * Constructor 
	 */
	public BonusPayTime(int bonusPayTimeItemNo, AttendanceTime bonusPayTime, TimeWithCalculation withinBonusPay,
			TimeWithCalculation excessBonusPayTime) {
		super();
		BonusPayTimeItemNo = bonusPayTimeItemNo;
		this.bonusPayTime = bonusPayTime;
		this.withinBonusPay = withinBonusPay;
		this.excessBonusPayTime = excessBonusPayTime;
	}
	
	public void controlUpperTime(AttendanceTime upperTime) {
		this.bonusPayTime = this.bonusPayTime.greaterThan(upperTime)?upperTime:this.bonusPayTime;
		this.withinBonusPay = TimeWithCalculation.createTimeWithCalculation(this.withinBonusPay.getTime().greaterThan(upperTime)?upperTime:this.withinBonusPay.getTime(),
																			this.withinBonusPay.getCalcTime().greaterThan(upperTime)?upperTime:this.withinBonusPay.getCalcTime());
		this.excessBonusPayTime = TimeWithCalculation.createTimeWithCalculation(this.excessBonusPayTime.getTime().greaterThan(upperTime)?upperTime:this.excessBonusPayTime.getTime(),
																				this.excessBonusPayTime.getCalcTime().greaterThan(upperTime)?upperTime:this.excessBonusPayTime.getCalcTime());
	}

	public void replaceValueByPClogInfo(AttendanceTime bonusPayTime,AttendanceTime withinCalcTime, AttendanceTime excessCalcTime) {
		//加給(属性が変更されたら対応)
		this.withinBonusPay.replaceCalcTime(withinCalcTime);
		this.excessBonusPayTime.replaceCalcTime(excessCalcTime);
	}

	public static BonusPayTime createDefaultWithNo(int no) {
		return new BonusPayTime(no, new AttendanceTime(0), TimeWithCalculation.sameTime(new AttendanceTime(0)),
				TimeWithCalculation.sameTime(new AttendanceTime(0)));
	}

	/**
	 * 加算する
	 * @param other 他の加給時間
	 * @return 加算後の加給時間
	 */
	public BonusPayTime add(BonusPayTime other) {
		return new BonusPayTime(
				this.BonusPayTimeItemNo,
				this.bonusPayTime.addMinutes(other.getBonusPayTime().valueAsMinutes()),
				this.withinBonusPay.addMinutes(
						other.getWithinBonusPay().getTime(),
						other.getWithinBonusPay().getCalcTime()),
				this.excessBonusPayTime.addMinutes(
						other.getExcessBonusPayTime().getTime(),
						other.getExcessBonusPayTime().getCalcTime()));
	}
	
	/**
	 * 加給時間Listの累計
	 * @param bonusPayTimeList 加給時間List
	 * @return 累計後の加給時間List
	 */
	public static List<BonusPayTime> sumBonusPayTimeList(List<BonusPayTime> bonusPayTimeList) {
		
		// 累計後Map
		Map<Integer, BonusPayTime> sumBonusPayTimeMap = new HashMap<>();
		
		for (BonusPayTime bonusPayTime : bonusPayTimeList) {
			int itemNo = bonusPayTime.BonusPayTimeItemNo;
			if (sumBonusPayTimeMap.containsKey(itemNo)) {
				BonusPayTime source = sumBonusPayTimeMap.get(itemNo);
				sumBonusPayTimeMap.replace(itemNo, source.add(bonusPayTime));
			}
			else {
				sumBonusPayTimeMap.put(bonusPayTime.BonusPayTimeItemNo, bonusPayTime);
			}
		}
		return sumBonusPayTimeMap.values().stream()
				.sorted((a, b) -> a.BonusPayTimeItemNo - b.BonusPayTimeItemNo)
				.collect(Collectors.toList());
	}
}
