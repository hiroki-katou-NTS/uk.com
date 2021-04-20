package nts.uk.ctx.at.record.app.command.workrecord.workmanagement;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tutt
 *
 */
@Getter
public class AddWorkRecodConfirmationCommand {

	// 対象者
	private String employeeId;

	// 対象日
	private GeneralDate date;

	// 確認者
	private String confirmerId;
}
