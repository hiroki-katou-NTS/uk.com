package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThanhNX
 *
 */
@Data
@NoArgsConstructor
public class NurseCategoryCommand {
	/**
	 * 看護区分コード
	 */
	private String nurseClassificationCode;

	/**
	 * 看護区分名称	
	 */
	private String nurseClassificationName;

	/**
	 * 	免許区分	
	 */
	private int license;

	/**
	 * 	事務的業務従事者か
	 */
	private boolean officeWorker;
	
	/**
	 * 看護管理者か
	 */
	private boolean nursingManager;
}
