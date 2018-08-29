package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 承認中間データエラーメッセージ情報（日別実績）
 * @author tutk
 *
 */
@Getter
public class AppDataInfoDaily extends AggregateRoot {
	
	/**社員ID*/
	private String employeeId;
	
	/**実行ID*/
	private String	executionId;
	
	/**エラーメッセージ*/
	private ErrorMessageRC errorMessage;

	public AppDataInfoDaily(String employeeId, String executionId, ErrorMessageRC errorMessage) {
		super();
		this.employeeId = employeeId;
		this.executionId = executionId;
		this.errorMessage = errorMessage;
	}

}
