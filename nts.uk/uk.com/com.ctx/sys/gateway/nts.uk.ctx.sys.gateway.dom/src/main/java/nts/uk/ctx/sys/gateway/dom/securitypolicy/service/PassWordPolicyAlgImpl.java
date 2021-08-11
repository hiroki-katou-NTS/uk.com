package nts.uk.ctx.sys.gateway.dom.securitypolicy.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.adapter.changelog.PassWordChangeLogAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.changelog.PassWordChangeLogImport;

@Stateless
public class PassWordPolicyAlgImpl implements PassWordPolicyAlgorithm{

	@Inject
	private PassWordChangeLogAdapter pwChangeLogAd;

	/**
	 * パスワードの期限切れチェック
	 * @param ユーザID userId
	 * @param パスワード有効期限 validityPeriod (day)
	 * @return true: エラーあり; false: エラーなし
	 * @author hoatt
	 */
	@Override
	public boolean checkExpiredPass(String userId, int validityPeriod) {
		//INPUT.パスワード有効期限 (validityPeriod)　＝　０　をチェックする
		if(validityPeriod == 0){//パスワード有効期限 (validityPeriod)　＝　０
			//終了状態：エラーなし (Trạng thái finish: no error)
			return false;
		}
		//パスワード有効期限 (validityPeriod)　＞　０
		//ドメインモデル「パスワード変更ログ」を取得する (Lấy domain  「PasswordChangeLog」)
		List<PassWordChangeLogImport> lstPwChangeLog = pwChangeLogAd.getListPwChangeLog(userId);
		if(lstPwChangeLog.isEmpty()){//先頭の１件を取得を取得できない(Không thể lấy bản ghi đầu tiên)
			//終了状態：エラーなし (Trạng thái finish: no error)
			return false;
		}
		//先頭の１件を取得できる(Lấy được data đầu tiên)
		PassWordChangeLogImport logFirst = lstPwChangeLog.get(0);
		//前回の変更からシステム日付までの日数(Days)を計算する (Tính toán số ngày từ lần thay đổi trước đến ngày hiện tại systemDate)
		int spanDays = logFirst.getModifiedDate().daysTo(GeneralDateTime.now());
		//期限が切れているかをチェックする (Check xem đã hết hạn chưa)
//		①【変更からの日数】 >= INPUT.パスワード有効期限　をチェックする
		if(spanDays >= validityPeriod){//span.Days >= INPUT.パスワード有効期限(span.Days  >= INPUT.validityPeriod)
			return true;
		}
		//span.Days < INPUT.パスワード有効期限(span.Days < INPUT.validityPeriod)
		return false;
	}

	/**
	 * パスワード変更通知チェック
	 * @param userId
	 * @param パスワード有効期限 validityPeriod (day)
	 * @param 期限切れ通知日数 notiPwChange (day)
	 * @return 終了状態  and 【残り何日】
	 * @author hoatt
	 */
	@Override
	public NotifyResult checkNotifyChangePass(String userId, int validityPeriod, int notiPwChange) {
		//INPUT.パスワード有効期限 (validityPeriod)　＝　０　をチェックする (Check INPUT.validityPeriod　＝　０)
		if(validityPeriod == 0){//パスワード有効期限 (validityPeriod)　＝　０
			//終了状態：通知しない
//			③【残り何日】＝0
			return new NotifyResult(false, 0);
		}
		//パスワード有効期限 (validityPeriod)　>　０
		//INPUT.期限切れ通知日数 (notificationPasswordChange)　＝　０　をチェックする (Check INPUT.notificationPasswordChange　＝　０)
		if(notiPwChange == 0){//期限切れ通知日数 (notificationPasswordChange)　＝　０
			//終了状態：通知しない
//			③【残り何日】＝0
			return new NotifyResult(false, 0);
		}
		//期限切れ通知日数 (notificationPasswordChange)　>　０
		//ドメインモデル「パスワード変更ログ」を取得する (Lấy domain 「PasswordChangeLog)
		List<PassWordChangeLogImport> lstPwChangeLog = pwChangeLogAd.getListPwChangeLog(userId);
		if(lstPwChangeLog.isEmpty()){//先頭の１件を取得を取得できない(ko lấy được)
			//終了状態：通知しない
//			③【残り何日】＝0
			return new NotifyResult(false, 0);
		}
		//先頭の１件を取得できる(Lấy được 1 dữ liệu đầu tiên)
		PassWordChangeLogImport logFirst = lstPwChangeLog.get(0);
		//①【通知不要期間】(Days)を計算する (Tính toán thời gian chưa cần thông báo ①【通知不要期間】)
//		①【通知不要期間】(Days)　＝INPUT.パスワード有効期限 － INPUT.期限切れ通知日数
		int spanDays1 = validityPeriod - notiPwChange;
		//「パスワード変更ログ.変更日時」に①【通知不要期間】(Days)を加算する (Cộng số ngày ①【通知不要期間】vào 「PasswordChangeLog.ChangeDateTime」)
		GeneralDateTime modifiedDate2 = logFirst.getModifiedDate().addDays(spanDays1);
		//システム日付が②【変更通知日】を超えているかをチェックする (Check systemDate đã vượt quá ② chưa)
//		DateTime.Now >= ②【変更通知日】をチェックする
		if(GeneralDateTime.now().before(modifiedDate2)){//DateTime.Now < ②【変更通知日】
			//終了状態：通知しない
//			③【残り何日】＝0
			return new NotifyResult(false, 0);
		}
		//DateTime.Now >= ②【変更通知日】
		//パスワード変更まで③【残り何日】を計算する (Tính toán ③【số ngày còn lại】 đến khi thay đổi pas)
//		　変更からシステム日付までの日数(Days)
//		　　TimeSpan span = DateTime.Now - パスワード変更ログ.変更日時
//		　　　↓
//		　　span.Days
//		　　　↓
//		　③【残り何日】＝ INPUT.パスワード有効期限 － span.Days;
		int span = logFirst.getModifiedDate().daysTo(GeneralDateTime.now());
		//終了状態：通知する (trạng thái: thông báo)
		return new NotifyResult(true, validityPeriod - span);
	}

}
