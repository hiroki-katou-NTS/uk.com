package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;

/**
 * 月別実績の休業
 * @author shuichu_ishida
 */
@Getter
public class LeaveOfMonthly {

	/** 固定休業日数 */
	private List<AggregateLeaveDays> fixLeaveDays;
	/** 任意休業日数 */
	private List<AnyLeave> anyLeaveDays;
	
	/**
	 * コンストラクタ
	 */
	public LeaveOfMonthly(){
		
		this.fixLeaveDays = new ArrayList<>();
		this.anyLeaveDays = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param fixLeaveDays 固定休業日数
	 * @param anyLeaveDays 任意休業日数
	 * @return 月別実績の休業
	 */
	public static LeaveOfMonthly of(
			List<AggregateLeaveDays> fixLeaveDays,
			List<AnyLeave> anyLeaveDays){
		
		val domain = new LeaveOfMonthly();
		domain.fixLeaveDays = fixLeaveDays;
		domain.anyLeaveDays = anyLeaveDays;
		return domain;
	}
}
