package nts.uk.ctx.at.shared.app.command.workrule.workuse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;

/**
 * Aggregate deformed labor setting data command
 * @author HoangNDH
 *
 */
@Data
@AllArgsConstructor
public class AddTemporaryWorkUseMntCommand {
	
	/** 変形労働を使用する */
	private int useClassification;
	
	/**
	 * Create domain aggregate deformed labor setting
	 * @param companyId
	 * @return
	 */
	public TemporaryWorkUseManage toDomain(String companyId) {
		return TemporaryWorkUseManage.createFromJavaType(companyId, useClassification);
	}
}
