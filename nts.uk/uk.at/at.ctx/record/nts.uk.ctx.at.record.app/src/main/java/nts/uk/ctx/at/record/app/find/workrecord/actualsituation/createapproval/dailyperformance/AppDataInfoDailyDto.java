package nts.uk.ctx.at.record.app.find.workrecord.actualsituation.createapproval.dailyperformance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDaily;

@Getter
@Setter
@NoArgsConstructor
public class AppDataInfoDailyDto {
	/**社員ID*/
	private String employeeId;
	
	/**実行ID*/
	private String	executionId;
	
	/**エラーメッセージ*/
	private String errorMessage;
	
	private String employeeCode;
	
	private String pname;
	
	private String date;


	public AppDataInfoDailyDto(String employeeId, String executionId, String errorMessage) {
		super();
		this.employeeId = employeeId;
		this.executionId = executionId;
		this.errorMessage = errorMessage;
	}
	
	public static AppDataInfoDailyDto fromDomain(AppDataInfoDaily domain) {
		return new  AppDataInfoDailyDto(
				domain.getEmployeeId(),
				domain.getExecutionId(),
				domain.getErrorMessage().v()
				);
	}
}
