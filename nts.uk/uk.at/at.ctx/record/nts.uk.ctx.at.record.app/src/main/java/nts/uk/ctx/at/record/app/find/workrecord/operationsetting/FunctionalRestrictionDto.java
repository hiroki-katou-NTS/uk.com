/**
 * 
 */
package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ConfirmOfManagerOrYouself;

/**
 * @author danpv
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionalRestrictionDto {
	
	// * 総労働時間≠応援の総労働時間合計のときに登録することが出来る
	private Boolean registeredTotalTimeCheer;

	// * 一ヵ月分の完了表示を利用する
	private Boolean completeDisplayOneMonth;

	// * 作業の詳細を利用する
	private Boolean useWorkDetail;

	// * 実績超過時に登録できる
	private Boolean registerActualExceed;

	// * 提出されている申請を確認する
	private Boolean confirmSubmitApp;

	// * 期間の初期値設定を利用する
	private Boolean useInitialValueSet;

	// * 申請画面を起動する
	private Boolean startAppScreen;

	// * 確認メッセージを表示する
	private Boolean displayConfirmMessage;

	// * 上司確認を利用する
	private Boolean useSupervisorConfirm;
	
	// * エラーがある場合の上司確認
	private int supervisorConfirmError;

	// * 本人確認を利用する
	private Boolean useConfirmByYourself;

	// * エラーがある場合の本人確認
	private int yourselfConfirmError;

}
