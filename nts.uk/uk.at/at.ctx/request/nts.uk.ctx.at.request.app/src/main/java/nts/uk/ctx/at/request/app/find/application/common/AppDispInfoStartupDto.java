package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppDispInfoStartupDto {
	/**
	 * 申請設定（基準日関係なし）
	 */
	private AppDispInfoNoDateDto appDispInfoNoDateOutput;
	
	/**
	 * 申請設定（基準日関係あり）
	 */
	private AppDispInfoWithDateDto appDispInfoWithDateOutput;
	
	/**
	 * 申請詳細画面情報
	 */
	private AppDetailScreenInfoDto appDetailScreenInfo;
	
	public static AppDispInfoStartupDto fromDomain(AppDispInfoStartupOutput appDispInfoStartupOutput) {
		return new AppDispInfoStartupDto(
				AppDispInfoNoDateDto.fromDomain(appDispInfoStartupOutput.getAppDispInfoNoDateOutput()), 
				AppDispInfoWithDateDto.fromDomain(appDispInfoStartupOutput.getAppDispInfoWithDateOutput()), 
				appDispInfoStartupOutput.getAppDetailScreenInfo().map(x -> AppDetailScreenInfoDto.fromDomain(x)).orElse(null));
	}
	
	public AppDispInfoStartupOutput toDomain() {
		AppDispInfoStartupOutput appDispInfoStartupOutput = new AppDispInfoStartupOutput(
				appDispInfoNoDateOutput.toDomain(), 
				appDispInfoWithDateOutput.toDomain());
		if(appDetailScreenInfo != null) {
			appDispInfoStartupOutput.setAppDetailScreenInfo(Optional.of(appDetailScreenInfo.toDomain()));
		}
		return appDispInfoStartupOutput;
	}
}
