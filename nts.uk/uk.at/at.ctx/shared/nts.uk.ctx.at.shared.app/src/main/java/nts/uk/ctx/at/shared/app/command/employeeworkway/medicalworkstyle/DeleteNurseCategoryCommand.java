package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThanhNX
 *
 */
@Data
@NoArgsConstructor
public class DeleteNurseCategoryCommand {
	
	/**
	 * 看護区分コード
	 */
	private String nurseClassificationCode;
	
}
