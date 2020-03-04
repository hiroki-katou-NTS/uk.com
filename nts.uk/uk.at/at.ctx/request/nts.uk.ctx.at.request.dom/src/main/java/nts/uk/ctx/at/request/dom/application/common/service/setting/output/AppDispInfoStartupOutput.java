package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申請表示情報
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppDispInfoStartupOutput {
	
	/**
	 * 申請設定（基準日関係なし）
	 */
	private AppDispInfoNoDateOutput appDispInfoNoDateOutput;
	
	/**
	 * 申請設定（基準日関係あり）
	 */
	private AppDispInfoWithDateOutput appDispInfoWithDateOutput;
	
}
