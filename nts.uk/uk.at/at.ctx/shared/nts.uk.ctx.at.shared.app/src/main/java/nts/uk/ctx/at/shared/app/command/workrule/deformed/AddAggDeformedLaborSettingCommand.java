package nts.uk.ctx.at.shared.app.command.workrule.deformed;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;

/**
 * Aggregate deformed labor setting data command
 * @author HoangNDH
 *
 */
@Data
@AllArgsConstructor
public class AddAggDeformedLaborSettingCommand {
	
	/** 変形労働を使用する */
	private int useDeformedSetting;
	
	/**
	 * Create domain aggregate deformed labor setting
	 * @param companyId
	 * @return
	 */
	public AggDeformedLaborSetting toDomain(String companyId) {
		return AggDeformedLaborSetting.createFromJavaType(companyId, useDeformedSetting);
	}
}
