package nts.uk.ctx.at.request.app.command.setting.workplace.appuseset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUseSetCommand {
	/**申請種類*/
	private int appType;
	/**
	 * 備考
	 */
	private String memo;
	/**
	 * 利用区分
	 */
	private int useAtr;

	public ApplicationUseSetting toDomain() {
		return ApplicationUseSetting.createNew(useAtr, appType, memo);
	}
}
