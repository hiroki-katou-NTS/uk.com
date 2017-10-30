package nts.uk.ctx.at.record.app.find.log.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TargetPersonDto {
	/**
	 * 社員ID
	 */
	private String employeeId;
	/**
	 * 就業計算と集計実行ログID
	 */
	private String empCalAndSumExecLogId;
	/**
	 * 状態
	 */
	private ComplStateOfExeContentsDto state;
	
	public static TargetPersonDto fromDomain(TargetPerson domain) {
		return new TargetPersonDto(
				domain.getEmployeeId(),
				domain.getEmpCalAndSumExecLogId(),
				new ComplStateOfExeContentsDto(
						domain.getState().getExecutionContent().value,
						domain.getState().getStatus().value
					)
				);
	}
}
