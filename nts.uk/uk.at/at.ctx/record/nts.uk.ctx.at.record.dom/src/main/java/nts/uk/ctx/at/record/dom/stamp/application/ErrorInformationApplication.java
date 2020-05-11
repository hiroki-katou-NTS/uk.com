package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author chungnt 
 * VO: 申請促すエラー情報			
 *	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.申請促すエラー情報
 *
 */
@AllArgsConstructor
public class ErrorInformationApplication {

	/**
	 * エラー種類
	 */
	@Getter
	private final CheckErrorType checkErrorType;

	/**
	 * エラーコードリスト
	 */
	@Getter
	private final List<String> errorAlarmCode;
	/**
	 * 促すメッセージ
	 */
	@Getter
	private final PromptingMessage promptingMessage;

}
