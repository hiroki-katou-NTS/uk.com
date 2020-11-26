package nts.uk.ctx.at.shared.app.command.statutory.worktime.app;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;

/**
 * 
 * @author sonnlb
 * 
 *         労働時間と日数の設定の利用単位の設定を更新する
 */

@Value
public class UpdateUsageUnitSettingCommand {

	// 職場の労働時間と日数の管理をする
	private boolean workPlace;
	// 雇用の労働時間と日数の管理をする
	boolean employment;
	// 社員の労働時間と日数の管理をする
	private boolean employee;

	public UsageUnitSetting toDomain(String companyId) {
		return new UsageUnitSetting(new CompanyId(companyId), employee, workPlace, employment);
	}
}
