package nts.uk.ctx.sys.portal.dom.notice.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;

/**
 * DomainService お知らせメッセージ
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.お知らせ.新メッセージがあるか
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.お知らせ.期間で参照できるメッセージを取得する
 * @author DungDV
 *
 */
public interface MessageNoticeService {
	
	/**
	 * [1] 新メッセージがあるか
	 * @param require @Require
	 * @param sid 社員ID
	 * @return 新メッセージがあるか
	 */
	Boolean isNewMsg(MessageNoticeRequire require, String sid);
	
	/**
	 * [1]期間で全て参照できるメッセージを取得する
	 * @param require @Require
	 * @param period 期間
	 * @param sid 社員ID
	 * @return List<お知らせメッセージ>
	 */
	List<MessageNotice> getAllMsgInPeriod(MessageNoticeRequire require, DatePeriod period, String sid);
	
	public static interface MessageNoticeRequire {
		
		/**
		 * [R-1] 社員IDから職場IDを取得する
		 * @param sid 社員ID
		 * @param baseDate 基準日
		 * @return 職場ID
		 */
		Optional<String> getWpId(String sid, GeneralDate baseDate);

		/**
		 * [R-2] 参照できるメッセージを取得する
		 * @param wpId 職場ID
		 * @return List<MessageNotice> List<お知らせメッセージ>
		 */
		List<MessageNotice> getNewMsgForDay(Optional<String> wpId);
		
		/**
		 * [R-2]期間で参照できるメッセージを取得する
		 * @param period 期間
		 * @param wpId 職場ID
		 * @param sid 社員ID
		 * @return List<MessageNotice> List<お知らせメッセージ>
		 */
		List<MessageNotice> getMsgRefByPeriod(DatePeriod period, Optional<String> wpId, String sid);
	}
}
