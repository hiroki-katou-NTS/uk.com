package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

/**
 * 
 * @author chungnt 
 * VO: 申請促すエラー情報			
 *	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.申請促すエラー情報
 *
 */
@AllArgsConstructor
@Getter
public class ErrorInformationApplication {

	/**
	 * エラー種類
	 */
	private final CheckErrorType checkErrorType;

	/**
	 * エラーコードリスト
	 */
	private final List<ErrorAlarmWorkRecordCode> errorAlarmCode;
	/**
	 * 促すメッセージ
	 */
	private final Optional<PromptingMessage> promptingMessage;

}
