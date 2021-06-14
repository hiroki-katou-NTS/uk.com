package nts.uk.screen.at.app.query.kdp.kdp003.p;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;
import nts.uk.ctx.sys.portal.dom.notice.DestinationClassification;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.MessageNoticeRepository;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KDP003_打刻入力(氏名選択).P:メッセージ一覧.メニュー別OCD.打刻入力で作成したお知らせを取得する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetNoticeByStamping {

	@Inject
	private MessageNoticeRepository msgNoticeRepo;

	/**
	 * 
	 * @param period 期間
	 * @param sid    社員ID
	 * @return <List>お知らせメッセージ
	 */
	public List<MessageNoticeDto> getNoticeByStamping(DatePeriod period, String sid) {

		// 1. 宛先区分で作成したメッセージを取得する(期間、職場選択、社員ID): <List>お知らせメッセージ
		List<MessageNotice> listMsg = msgNoticeRepo.getMsgInDestinationCategory(period, DestinationClassification.WORKPLACE, sid);

		if (listMsg.isEmpty()) {
			return new ArrayList<MessageNoticeDto>();
		}
		
		return listMsg.stream().map(msg -> MessageNoticeDto.toDto(msg)).collect(Collectors.toList());
	}
}
