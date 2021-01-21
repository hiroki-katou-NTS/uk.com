package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 申請促すエラーの設定
 * 
 * @author phongtq
 *
 */

@Value
@Getter
@AllArgsConstructor
public class StampRecordDis implements DomainValue {

	/** 利用区分 */
	private NotUseAtr useArt;

	/** チェックエラー種類 */
	private final CheckErrorType checkErrorType;

	/** 促すメッセージ */
	private Optional<PromptingMessage> promptingMssage;

	/**
	 * [1] エラー種類に対応する申請促すエラー情報を取得する
	 */
	
	public ErrorInformationApplication getErrornformation() {
		//	$エラーコードリスト = @チェックエラー種類.エラー種類に対応するエラーアラームを取得する()
		List<ErrorAlarmWorkRecordCode> list = this.checkErrorType.getErrorAlarm();
		
		//申請促すエラー情報#申請促すエラー情報(@チェックエラー種類,$エラーコードリスト,@促すメッセージ)
		return new ErrorInformationApplication(this.checkErrorType, list, this.promptingMssage );
	}
}
