package nts.uk.ctx.sys.portal.pubimp.notice;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.MessageNoticeRepository;
import nts.uk.ctx.sys.portal.dom.notice.adapter.MessageNoticeAdapter;
import nts.uk.ctx.sys.portal.dom.notice.service.MessageNoticeService;
import nts.uk.ctx.sys.portal.pub.notice.MessageNoticeServicePub;
import nts.uk.ctx.sys.portal.pub.notice.NoticeMessageExport;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanh_nx
 *
 *         期間で参照できるメッセージを取得する
 */
@Stateless
public class MessageNoticeServicePubImpl implements MessageNoticeServicePub {

	@Inject
	private MessageNoticeAdapter messageNoticeAdapter;

	@Inject
	private MessageNoticeRepository messageNoticeRepository;

	@Override
	public List<NoticeMessageExport> getMessage(String cid, String sid, DatePeriod period) {
		RequireImpl impl = new RequireImpl(cid);
		List<MessageNotice> lstMessage = MessageNoticeService.getAllMsgInPeriod(impl, period, sid);
		AtomicInteger index = new AtomicInteger(1);
		return lstMessage.stream().map(x -> {
			return new NoticeMessageExport(index.getAndIncrement(), x.getNotificationMessage().v());
		}).collect(Collectors.toList());
	}

	@AllArgsConstructor
	public class RequireImpl implements MessageNoticeService.MessageNoticeRequire {

		private final String cid;

		@Override
		public Optional<String> getWpId(String sid, GeneralDate baseDate) {
			return messageNoticeAdapter.getWpId(sid, baseDate);
		}

		@Override
		public List<MessageNotice> getNewMsgForDay(Optional<String> wpId) {
			String cid = AppContexts.user().companyId();
			return messageNoticeRepository.getNewMsgForDay(cid, wpId);
		}

		@Override
		public List<MessageNotice> getMsgRefByPeriod(DatePeriod period, Optional<String> wpId, String sid) {
			return messageNoticeRepository.getMsgRefByPeriod(cid, period, wpId, sid);
		}
	}
}
