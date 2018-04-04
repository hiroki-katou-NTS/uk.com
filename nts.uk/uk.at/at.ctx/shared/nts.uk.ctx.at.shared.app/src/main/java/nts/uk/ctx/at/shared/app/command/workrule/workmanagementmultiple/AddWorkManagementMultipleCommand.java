package nts.uk.ctx.at.shared.app.command.workrule.workmanagementmultiple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;

/**
 * The Class AddWorkManagementMultipleCommand.
 *
 * @author HoangNDH
 */
@Getter
@AllArgsConstructor
public class AddWorkManagementMultipleCommand {
	
	/** The use atr. */
	private int useAtr;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the work management multiple
	 */
	public WorkManagementMultiple toDomain(String companyId) {
		return WorkManagementMultiple.createFromJavaType(companyId, useAtr);
	}
}
