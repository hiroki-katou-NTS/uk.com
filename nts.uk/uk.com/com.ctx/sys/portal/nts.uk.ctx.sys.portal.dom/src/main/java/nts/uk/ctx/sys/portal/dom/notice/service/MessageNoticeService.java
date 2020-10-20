package nts.uk.ctx.sys.portal.dom.notice.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;

/**
 * DomainService お知らせメッセージ
 * @author DungDV
 *
 */
public interface MessageNoticeService {
	
	/**
	 * [1] 新メッセージがあるか
	 * @return
	 */
	Boolean isNewMsg();
	
	/**
	 * [1]期間で全て参照できるメッセージを取得する
	 * @return
	 */
	List<MessageNotice> getAllMsgInPeriod(DatePeriod period);
	
	public static interface Require {
		
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
