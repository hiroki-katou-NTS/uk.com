package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput_Old;

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
	
	public static AppDispInfoStartupDto fromDomain(AppDispInfoStartupOutput_Old appDispInfoStartupOutput) {
		AppDispInfoStartupDto result = new AppDispInfoStartupDto();
		result.appDispInfoNoDateOutput = AppDispInfoNoDateDto.fromDomain(appDispInfoStartupOutput.getAppDispInfoNoDateOutput());
		result.appDispInfoWithDateOutput = AppDispInfoWithDateDto.fromDomain(appDispInfoStartupOutput.getAppDispInfoWithDateOutput());
		result.appDetailScreenInfo = appDispInfoStartupOutput.getAppDetailScreenInfo().map(x -> AppDetailScreenInfoDto.fromDomain(x)).orElse(null);
		return result;
	}
	
	public AppDispInfoStartupOutput_Old toDomain() {
		AppDispInfoStartupOutput_Old output = new AppDispInfoStartupOutput_Old();
		output.setAppDispInfoNoDateOutput(appDispInfoNoDateOutput.toDomain());
		
		return new AppDispInfoStartupOutput_Old(
				appDispInfoNoDateOutput.toDomain(), 
				appDispInfoWithDateOutput.toDomain(), 
				appDetailScreenInfo == null ? Optional.empty() : Optional.of(appDetailScreenInfo.toDomain()));
	}
}
