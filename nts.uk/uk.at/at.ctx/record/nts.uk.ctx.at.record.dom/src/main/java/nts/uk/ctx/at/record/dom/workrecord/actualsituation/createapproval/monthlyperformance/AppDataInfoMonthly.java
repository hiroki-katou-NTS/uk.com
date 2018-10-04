package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.ErrorMessageRC;
/**
 * 承認中間データエラーメッセージ情報（月別実績）
 * @author tutk
 *
 */
@Getter
public class AppDataInfoMonthly extends AggregateRoot {
	
	/**社員ID*/
	private String employeeId;
	
	/**実行ID*/
	private String	executionId;
	
	/**エラーメッセージ*/
	private ErrorMessageRC errorMessage;

	public AppDataInfoMonthly(String employeeId, String executionId, ErrorMessageRC errorMessage) {
		super();
		this.employeeId = employeeId;
		this.executionId = executionId;
		this.errorMessage = errorMessage;
	}

}
