package nts.uk.ctx.at.schedule.app.command.employeeinfo.medicalworkstyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteNurseCategoryCommand {
	
	/**
	 * 看護区分コード
	 */
	private String nurseClassificationCode;
	
}
