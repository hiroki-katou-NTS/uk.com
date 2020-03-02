package nts.uk.ctx.at.request.app.find.setting.request.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppUseAtrDto {
	/** 申請種類 */
	private int appType;
	/** 利用区分 */
	private int useAtr;
}
