package nts.uk.ctx.at.record.dom.workrecord.operationsetting.old;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@Getter
public class FunctionalRestriction {

	/**
	 * 総労働時間≠応援の総労働時間合計のときに登録することが出来る
	 */
	private Boolean registeredTotalTimeCheer;

	/**
	 * 一ヵ月分の完了表示を利用する
	 */
	private Boolean completeDisplayOneMonth;

	/**
	 * 作業の詳細を利用する
	 */
	private Boolean useWorkDetail;

	/**
	 * 実績超過時に登録できる
	 */
	private Boolean registerActualExceed;

	/**
	 * 提出されている申請を確認する
	 */
	private Boolean confirmSubmitApp;

	/**
	 * 期間の初期値設定を利用する
	 */
	private Boolean useInitialValueSet;

	/**
	 * 申請画面を起動する
	 */
	private Boolean startAppScreen;

	/**
	 * 確認メッセージを表示する
	 */
	private Boolean displayConfirmMessage;

	/**
	 * 上司確認を利用する
	 */
	private Boolean useSupervisorConfirm;

	/**
	 * エラーがある場合の上司確認
	 */
	private ConfirmOfManagerOrYouself supervisorConfirmError;

	/**
	 * 本人確認を利用する
	 */
	private Boolean useConfirmByYourself;

	/**
	 * エラーがある場合の本人確認
	 */
	private ConfirmOfManagerOrYouself yourselfConfirmError;

	/**
	 * @param registeredTotalTimeCheer
	 * @param completeDisplayOneMonth
	 * @param useWorkDetail
	 * @param registerActualExceed
	 * @param confirmSubmitApp
	 * @param useInitialValueSet
	 * @param startAppScreen
	 * @param displayConfirmMessage
	 * @param useSupervisorConfirm
	 * @param supervisorConfirmError
	 * @param useConfirmByYourself
	 * @param yourselfConfirmError
	 */
	public FunctionalRestriction(boolean registeredTotalTimeCheer, boolean completeDisplayOneMonth,
			boolean useWorkDetail, boolean registerActualExceed, boolean confirmSubmitApp, boolean useInitialValueSet,
			boolean startAppScreen, boolean displayConfirmMessage, boolean useSupervisorConfirm,
			ConfirmOfManagerOrYouself supervisorConfirmError, boolean useConfirmByYourself,
			ConfirmOfManagerOrYouself yourselfConfirmError) {
		super();
		this.registeredTotalTimeCheer = registeredTotalTimeCheer;
		this.completeDisplayOneMonth = completeDisplayOneMonth;
		this.useWorkDetail = useWorkDetail;
		this.registerActualExceed = registerActualExceed;
		this.confirmSubmitApp = confirmSubmitApp;
		this.useInitialValueSet = useInitialValueSet;
		this.startAppScreen = startAppScreen;
		this.displayConfirmMessage = displayConfirmMessage;
		this.useSupervisorConfirm = useSupervisorConfirm;
		this.supervisorConfirmError = supervisorConfirmError;
		this.useConfirmByYourself = useConfirmByYourself;
		this.yourselfConfirmError = yourselfConfirmError;
	}

	/**
	 * @param registeredTotalTimeCheer
	 * @param completeDisplayOneMonth
	 * @param useWorkDetail
	 * @param registerActualExceed
	 * @param confirmSubmitApp
	 * @param useInitialValueSet
	 * @param startAppScreen
	 * @param displayConfirmMessage
	 * @param useSupervisorConfirm
	 * @param supervisorConfirmError
	 * @param useConfirmByYourself
	 * @param yourselfConfirmError
	 */
	public FunctionalRestriction(int registeredTotalTimeCheer, int completeDisplayOneMonth, int useWorkDetail,
			int registerActualExceed, int confirmSubmitApp, int useInitialValueSet, int startAppScreen,
			int displayConfirmMessage, int useSupervisorConfirm, int supervisorConfirmError, int useConfirmByYourself,
			int yourselfConfirmError) {
		super();
		this.registeredTotalTimeCheer = toBooleanValue(registeredTotalTimeCheer);
		this.completeDisplayOneMonth = toBooleanValue(completeDisplayOneMonth);
		this.useWorkDetail = toBooleanValue(useWorkDetail);
		this.registerActualExceed = toBooleanValue(registerActualExceed);
		this.confirmSubmitApp = toBooleanValue(confirmSubmitApp);
		this.useInitialValueSet = toBooleanValue(useInitialValueSet);
		this.startAppScreen = toBooleanValue(startAppScreen);
		this.displayConfirmMessage = toBooleanValue(displayConfirmMessage);
		this.useSupervisorConfirm = toBooleanValue(useSupervisorConfirm);
		this.supervisorConfirmError = EnumAdaptor.valueOf(supervisorConfirmError, ConfirmOfManagerOrYouself.class);
		this.useConfirmByYourself = toBooleanValue(useConfirmByYourself);
		this.yourselfConfirmError = EnumAdaptor.valueOf(yourselfConfirmError, ConfirmOfManagerOrYouself.class);
	}

	private Boolean toBooleanValue(int decimalNumber) {
		if (decimalNumber == 0) {
			return false;
		} else if (decimalNumber == 1) {
			return true;
		} else {
			return null;
		}
	}
}
