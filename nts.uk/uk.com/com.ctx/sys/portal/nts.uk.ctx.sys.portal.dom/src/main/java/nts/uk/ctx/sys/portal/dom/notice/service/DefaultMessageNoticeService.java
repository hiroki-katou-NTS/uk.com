package nts.uk.ctx.sys.portal.dom.notice.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.service.MessageNoticeService;

@Stateless
public class DefaultMessageNoticeService implements MessageNoticeService {

	@Override
	public Boolean isNewMsg(MessageNoticeRequire require, String sid) {
		GeneralDate baseDate = GeneralDate.today();
		// $職場ID = require.社員IDから職場IDを取得する(ログイン社員ID、年月日.今日)
		Optional<String> wpId = require.getWpId(sid, baseDate);
		// $List<お知らせメッセージ>　＝　 require.参照できるメッセージを取得する($職場ID)
		List<MessageNotice> listMsg = require.getNewMsgForDay(wpId);
		// return　!$List<お知らせメッセージ>.isEmpty()
		return !listMsg.isEmpty();
	}

	@Override
	public List<MessageNotice> getAllMsgInPeriod(MessageNoticeRequire require, DatePeriod period, String sid) {
		GeneralDate baseDate = GeneralDate.today();
		// $職場ID = require.社員IDから職場IDを取得する(ログイン社員ID、年月日.今日)
		Optional<String> wpId = require.getWpId(sid, baseDate);
		// return　 require.期間で参照できるメッセージを取得する(期間、$職場ID)
		return require.getMsgRefByPeriod(period, wpId, sid);
	}
}
