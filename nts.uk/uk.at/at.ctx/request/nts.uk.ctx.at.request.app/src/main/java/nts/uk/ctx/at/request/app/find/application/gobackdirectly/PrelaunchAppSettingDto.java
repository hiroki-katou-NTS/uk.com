package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.PrelaunchAppSetting;

@AllArgsConstructor
@Value
public class PrelaunchAppSettingDto {
	/** 申請共通設定 */
	ApplicationSettingDto appSetttingDto;

	/** 基準日 */
	String cacheDate;

//	public static PrelaunchAppSettingDto convertToDto(PrelaunchAppSetting domain) {
//		return new PrelaunchAppSettingDto(
//				ApplicationSettingDto.convertToDto(domain.getAppCommonSetting()),
//				domain.getCacheDate() == null ? null : domain.getCacheDate().toString("yyyy/MM/dd"));
//	}
}
