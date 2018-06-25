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
	private String anyPeriodAggrLogId;
	
	/** 社員ID*/
	private String memberId;
	
	/** 状態*/
	private State state;

	/**
	 * 
	 * @param anyPeriodAggrLogId
	 * @param memberId
	 * @param state
	 */
	public AggrPeriodTarget(String anyPeriodAggrLogId, String memberId, State state) {
		super();
		this.anyPeriodAggrLogId = anyPeriodAggrLogId;
		this.memberId = memberId;
		this.state = state;
	}
	
	/**
	 * 
	 * @param anyPeriodAggrLogId
	 * @param memberId
	 * @param state
	 * @return
	 */
	public static AggrPeriodTarget createFromJavaType(String anyPeriodAggrLogId, String memberId, int state){
		return new AggrPeriodTarget(anyPeriodAggrLogId, memberId, EnumAdaptor.valueOf(state, State.class));
	}
	
}
