package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.AppDetailScreenInfo;

/**
 * 申請表示情報
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AppDispInfoStartupOutput_Old {
	
	/**
	 * 申請設定（基準日関係なし）
	 */
	private AppDispInfoNoDateOutput_Old appDispInfoNoDateOutput;
	
	/**
	 * 申請設定（基準日関係あり）
	 */
	private AppDispInfoWithDateOutput_Old appDispInfoWithDateOutput;
	
	/**
	 * 申請詳細画面情報
	 */
	private Optional<AppDetailScreenInfo> appDetailScreenInfo;
	
}
