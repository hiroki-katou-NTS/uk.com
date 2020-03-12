package nts.uk.ctx.at.request.app.find.application.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;

@AllArgsConstructor
@NoArgsConstructor
public class AppDispInfoStartupDto {
	
	/**
	 * 申請設定（基準日関係なし）
	 */
	public AppDispInfoNoDateDto appDispInfoNoDateOutput;
	
	/**
	 * 申請設定（基準日関係あり）
	 */
	public AppDispInfoWithDateDto appDispInfoWithDateOutput;
	
	/**
	 * 申請詳細画面情報
	 */
	public AppDetailScreenInfoDto appDetailScreenInfo;
	
	public static AppDispInfoStartupDto fromDomain(AppDispInfoStartupOutput appDispInfoStartupOutput) {
		AppDispInfoStartupDto result = new AppDispInfoStartupDto();
		result.appDispInfoNoDateOutput = AppDispInfoNoDateDto.fromDomain(appDispInfoStartupOutput.getAppDispInfoNoDateOutput());
		result.appDispInfoWithDateOutput = AppDispInfoWithDateDto.fromDomain(appDispInfoStartupOutput.getAppDispInfoWithDateOutput());
		result.appDetailScreenInfo = appDispInfoStartupOutput.getAppDetailScreenInfo().map(x -> AppDetailScreenInfoDto.fromDomain(x)).orElse(null);
		return result;
	}
}
