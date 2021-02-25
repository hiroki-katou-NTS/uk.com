package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.AppDetailScreenInfo;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.起動時の申請表示情報を取得する.申請表示情報
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppDispInfoStartupOutput {
	
	/**
	 * 申請設定（基準日関係なし）
	 */
	private AppDispInfoNoDateOutput appDispInfoNoDateOutput;
	
	/**
	 * 申請設定（基準日関係あり）
	 */
	@Setter
	private AppDispInfoWithDateOutput appDispInfoWithDateOutput;
	
	/**
	 * 申請詳細画面情報
	 */
	@Setter
	private Optional<AppDetailScreenInfo> appDetailScreenInfo;
	
	public AppDispInfoStartupOutput(AppDispInfoNoDateOutput appDispInfoNoDateOutput, AppDispInfoWithDateOutput appDispInfoWithDateOutput) {
		this.appDispInfoNoDateOutput = appDispInfoNoDateOutput;
		this.appDispInfoWithDateOutput = appDispInfoWithDateOutput;
		this.appDetailScreenInfo = Optional.empty();
	}
	
}
