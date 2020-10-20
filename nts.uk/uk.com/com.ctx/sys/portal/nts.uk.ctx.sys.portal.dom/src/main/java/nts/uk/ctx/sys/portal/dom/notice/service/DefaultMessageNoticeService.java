package nts.uk.ctx.sys.portal.dom.notice.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.MessageNoticeRepository;
import nts.uk.ctx.sys.portal.dom.notice.adapter.MessageNoticeAdapter;
import nts.uk.ctx.sys.portal.dom.notice.service.MessageNoticeService;
import nts.uk.shr.com.context.AppContexts;

public class DefaultMessageNoticeService implements MessageNoticeService {
	
	@Inject
	private MessageNoticeAdapter messageNoticeAdapter;
	
	@Inject
	private MessageNoticeRepository messageNoticeRepository;

	@Override
	public Boolean isNewMsg() {
		MessageNoticeRequireImpl require = new MessageNoticeRequireImpl(messageNoticeAdapter, messageNoticeRepository);
		String sid = AppContexts.user().employeeId();
		GeneralDate baseDate = GeneralDate.today();
		// $職場ID = require.社員IDから職場IDを取得する(ログイン社員ID、年月日.今日)
		Optional<String> wpId = require.getWpId(sid, baseDate);
		// $List<お知らせメッセージ>　＝　 require.参照できるメッセージを取得する($職場ID)
		List<MessageNotice> listMsg = require.getNewMsgForDay(wpId);
		// return　!$List<お知らせメッセージ>.isEmpty()
		return !listMsg.isEmpty();
	}

	@Override
	public List<MessageNotice> getAllMsgInPeriod(DatePeriod period) {
		MessageNoticeRequireImpl require = new MessageNoticeRequireImpl(messageNoticeAdapter, messageNoticeRepository);
		String sid = AppContexts.user().employeeId();
		GeneralDate baseDate = GeneralDate.today();
		// $職場ID = require.社員IDから職場IDを取得する(ログイン社員ID、年月日.今日)
		Optional<String> wpId = require.getWpId(sid, baseDate);
		// return　 require.期間で参照できるメッセージを取得する(期間、$職場ID)
		return require.getMsgRefByPeriod(period, wpId, sid);
	}
	
	@AllArgsConstructor
	private class MessageNoticeRequireImpl implements MessageNoticeService.Require {
		
		@Inject
		private MessageNoticeAdapter messageNoticeAdapter;
		
		@Inject
		private MessageNoticeRepository messageNoticeRepository;
		
		@Override
		public Optional<String> getWpId(String sid, GeneralDate baseDate) {
			return messageNoticeAdapter.getWpId(sid, baseDate);
		}

		@Override
		public List<MessageNotice> getNewMsgForDay(Optional<String> wpId) {
			return messageNoticeRepository.getNewMsgForDay(wpId);
		}

		@Override
		public List<MessageNotice> getMsgRefByPeriod(DatePeriod period, Optional<String> wpId, String sid) {
			return messageNoticeRepository.getMsgRefByPeriod(period, wpId, sid);
		}
		
	}

}
