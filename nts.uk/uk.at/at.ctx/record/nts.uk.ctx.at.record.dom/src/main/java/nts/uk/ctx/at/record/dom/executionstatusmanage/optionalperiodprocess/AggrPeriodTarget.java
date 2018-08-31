package nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodtarget.State;

/**
 * 任意期間集計対象者
 * @author phongtq
 *
 */
@Getter
public class AggrPeriodTarget {

	/** 任意期間集計実行ログID*/
	private String aggrId;
	
	/** 社員ID*/
	private String employeeId;
	
	/** 状態*/
	private State state;

	/**
	 * 
	 * @param anyPeriodAggrLogId
	 * @param memberId
	 * @param state
	 */
	public AggrPeriodTarget(String aggrId, String employeeId, State state) {
		super();
		this.aggrId = aggrId;
		this.employeeId = employeeId;
		this.state = state;
	}
	
	/**
	 * 
	 * @param anyPeriodAggrLogId
	 * @param memberId
	 * @param state
	 * @return
	 */
	public static AggrPeriodTarget createFromJavaType(String anyPeriodAggrLogId, String employeeId, int state){
		return new AggrPeriodTarget(anyPeriodAggrLogId, employeeId, EnumAdaptor.valueOf(state, State.class));
	}
	
}
