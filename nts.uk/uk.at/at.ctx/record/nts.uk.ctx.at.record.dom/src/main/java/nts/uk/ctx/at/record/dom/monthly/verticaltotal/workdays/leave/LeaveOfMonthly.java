package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;

/**
 * 月別実績の休業
 * @author shuichu_ishida
 */
@Getter
public class LeaveOfMonthly {

	/** 固定休業日数 */
	private Map<CloseAtr, AggregateLeaveDays> fixLeaveDays;
	/** 任意休業日数 */
	private Map<Integer, AnyLeave> anyLeaveDays;
	
	/**
	 * コンストラクタ
	 */
	public LeaveOfMonthly(){
		
		this.fixLeaveDays = new HashMap<>();
		this.anyLeaveDays = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param fixLeaveDaysList 固定休業日数リスト
	 * @param anyLeaveDaysList 任意休業日数リスト
	 * @return 月別実績の休業
	 */
	public static LeaveOfMonthly of(
			List<AggregateLeaveDays> fixLeaveDaysList,
			List<AnyLeave> anyLeaveDaysList){
		
		val domain = new LeaveOfMonthly();
		for (val fixLeaveDays : fixLeaveDaysList){
			val leaveAtr = fixLeaveDays.getLeaveAtr();
			domain.fixLeaveDays.putIfAbsent(leaveAtr, AggregateLeaveDays.of(leaveAtr, fixLeaveDays.getDays()));
		}
		for (val anyLeaveDays : anyLeaveDaysList){
			val anyLeaveNo = Integer.valueOf(anyLeaveDays.getAnyLeaveNo());
			domain.anyLeaveDays.putIfAbsent(anyLeaveNo, AnyLeave.of(anyLeaveNo, anyLeaveDays.getDays()));
		}
		return domain;
	}
	
	/**
	 * 集計
	 * @param workTypeDaysCountTable 勤務種類の日数カウント表
	 */
	public void aggregate(WorkTypeDaysCountTable workTypeDaysCountTable){

		if (workTypeDaysCountTable == null) return;
		
		for (val leaveDays : workTypeDaysCountTable.getLeaveDays().entrySet()){
			val closeAtr = leaveDays.getKey();
			switch (closeAtr){
			case PRENATAL:
			case POSTPARTUM:
			case CHILD_CARE:
			case CARE:
			case INJURY_OR_ILLNESS:
				this.fixLeaveDays.putIfAbsent(closeAtr, new AggregateLeaveDays(closeAtr));
				this.fixLeaveDays.get(closeAtr).addDays(leaveDays.getValue().v());
				break;
			default:
				int anyLeaveNo = closeAtr.value - CloseAtr.INJURY_OR_ILLNESS.value;
				if (anyLeaveNo > 0){
					this.anyLeaveDays.putIfAbsent(anyLeaveNo, new AnyLeave(anyLeaveNo));
					this.anyLeaveDays.get(anyLeaveNo).addDays(leaveDays.getValue().v());
				}
				break;
			}
		}
	}
}
