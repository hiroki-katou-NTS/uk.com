package nts.uk.ctx.at.function.ac.notice;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.notice.MessageNoticeServiceAdapter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.message.NoticeMessageImport;
import nts.uk.ctx.sys.portal.pub.notice.MessageNoticeServicePub;

/**
 * @author thanh_nx
 *
 *         期間で参照できるメッセージを取得する
 */
@Stateless
public class MessageNoticeServiceAc implements MessageNoticeServiceAdapter {

	@Inject
	private MessageNoticeServicePub pub;

	// 取得する
	@Override
	public List<NoticeMessageImport> getMessage(String cid, String sid, DatePeriod period) {
		return pub.getMessage(cid, sid, period).stream()
				.map(x -> new NoticeMessageImport(x.getDisplayOrder(), x.getContent())).collect(Collectors.toList());
	}

}
