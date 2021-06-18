package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthly;

@Getter
@Setter
@NoArgsConstructor
public class AppDataInfoMonthlyDto {
	/**社員ID*/
	private String employeeId;
	
	/**実行ID*/
	private String	executionId;
	
	/**エラーメッセージ*/
	private String errorMessage;
	
	private GeneralDateTime date;

	public AppDataInfoMonthlyDto(String employeeId, String executionId, String errorMessage) {
		super();
		this.employeeId = employeeId;
		this.executionId = executionId;
		this.errorMessage = errorMessage;
	}
	
	public static AppDataInfoMonthlyDto fromDomain(AppDataInfoMonthly domain) {
		return new  AppDataInfoMonthlyDto(
				domain.getEmployeeId(),
				domain.getExecutionId(),
				domain.getErrorMessage().v()
				);
	}
}