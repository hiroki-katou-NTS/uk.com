package nts.uk.ctx.at.shared.app.command.flex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;

/**
 * Add フレックス勤務の設定 data command
 * @author HoangNDH
 *
 */
@Getter
@AllArgsConstructor
public class AddFlexWorkSettingCommand {
	/** フレックス勤務を管理する */
	private int managingFlexWork;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the flex work set
	 */
	public FlexWorkSet toDomain(String companyId) {
		return FlexWorkSet.createFromJavaType(companyId, managingFlexWork);
	}
}
