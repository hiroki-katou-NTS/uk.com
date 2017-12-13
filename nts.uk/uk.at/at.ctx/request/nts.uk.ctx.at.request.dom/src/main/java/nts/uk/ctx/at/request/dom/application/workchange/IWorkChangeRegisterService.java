package nts.uk.ctx.at.request.dom.application.workchange;
import nts.uk.ctx.at.request.dom.application.Application;
/**
 * 勤務変更申請の登録を実行する
 */
public interface IWorkChangeRegisterService {
	
	/**
	 * ドメインモデル「勤務変更申請設定」の新規登録をする
	 * @param workChange: 勤務変更申請設定
	 * @param app: 申請
	 * @return List approval email.
	 */
	String registerData(AppWorkChange workChange, Application app);
	/**
	 * アルゴリズム「勤務変更申請就業時間チェックの内容」を実行する
	 * 就業時間
	 * @param AppWorkChange:  勤務変更申請
	 */	
	void checkWorkHour(AppWorkChange workChange);
	/**
	 * アルゴリズム「勤務変更申請休憩時間１チェックの内容」を実行する
	 * 休憩時間
	 * @param AppWorkChange:  勤務変更申請
	 */
	void checkBreakTime1(AppWorkChange workChange);
	
}
