package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 承認中間データ中断管理（日別実績）
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class AppInterrupDaily extends AggregateRoot {

	/**実行ID*/
	private String	executionId;
	
	/**中断状態*/
	private boolean suspendedState;

	public AppInterrupDaily(String executionId, boolean suspendedState) {
		super();
		this.executionId = executionId;
		this.suspendedState = suspendedState;
	}
	
	
}
