/**
 * 
 */
package nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.ConfirmOfManagerOrYouself;

/**
 * @author danpv
 *
 */
@Getter
public class FunctionalRestrictionCommand {

	// * 総労働時間≠応援の総労働時間合計のときに登録することが出来る
	private boolean registeredTotalTimeCheer;

	// * 一ヵ月分の完了表示を利用する
	private boolean completeDisplayOneMonth;

	// * 作業の詳細を利用する
	private boolean useWorkDetail;

	// * 実績超過時に登録できる
	private boolean registerActualExceed;

	// * 提出されている申請を確認する
	private boolean confirmSubmitApp;

	// * 期間の初期値設定を利用する
	private boolean useInitialValueSet;

	// * 申請画面を起動する
	private boolean startAppScreen;

	// * 確認メッセージを表示する
	private boolean displayConfirmMessage;

	// * 上司確認を利用する
	private boolean useSupervisorConfirm;

	// * エラーがある場合の上司確認
	private ConfirmOfManagerOrYouself supervisorConfirmError;

	// * 本人確認を利用する
	private boolean useConfirmByYourself;

	// * エラーがある場合の本人確認
	private ConfirmOfManagerOrYouself yourselfConfirmError;

}
