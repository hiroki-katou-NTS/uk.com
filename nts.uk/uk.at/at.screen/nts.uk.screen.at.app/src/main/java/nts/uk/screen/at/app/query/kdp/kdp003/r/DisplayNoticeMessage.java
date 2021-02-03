package nts.uk.screen.at.app.query.kdp.kdp003.r;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.MessageNoticeRepository;
import nts.uk.ctx.sys.portal.dom.notice.adapter.EmployeeInfoImport;
import nts.uk.ctx.sys.portal.dom.notice.adapter.MessageNoticeAdapter;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KDP003_打刻入力(氏名選択).R:メッセージ表示.メニュー別OCD.打刻入力(共有)でお知らせメッセージを表示する
 * 
 * @author tutt
 *
 */
@Stateless
public class DisplayNoticeMessage {

	@Inject
	private MessageNoticeRepository msgNoticeRepo;

	@Inject
	private MessageNoticeAdapter msgNoticeAdapter;

	/**
	 * 打刻入力(共有)でお知らせメッセージを表示する
	 * @param period 期間
	 * @param wkpIds 職場IDs
	 * @return Map<お知らせメッセージ, 作成者コード, 作成者>(List)
	 */
	public List<MsgNoticeDto> displayNoticeMessage(DatePeriod period, List<String> wkpIds) {
		List<MsgNoticeDto> msgNoticeDtos = new ArrayList<>();
		List<EmployeeInfoImport> employeeInfos =  new ArrayList<>(); 
		
		// 1. 職場IDListからメッセージを取得する
		final List<MessageNotice> messageNotices = msgNoticeRepo.getMsgFromWpIdList(period, wkpIds);
		
		//2. Not　お知らせメッセージ(List)　Is Empty
		if (!messageNotices.isEmpty()) {
			List<String> sIdList = messageNotices.stream().map(m -> m.getCreatorID()).distinct().collect(Collectors.toList());
			
			// <call> 社員ID（List）から社員コードと表示名を取得
			employeeInfos = msgNoticeAdapter.getByListSID(sIdList);
			
			// Map<お知らせメッセージ、作成者コード、作成者>の作成者コードに社員コード、作成者にビジネスネームをセットする（Listの並び順はそのままとする)
			msgNoticeDtos = employeeInfos.stream().map(
					m -> MsgNoticeDto.builder().scd(m.getScd())
					.bussinessName(m.getBussinessName())
					.message(MessageNoticeDto.toDto(messageNotices.stream()
							.filter(f -> f.getCreatorID() == m.getSid())
							.collect(Collectors.toList())
							.get(0))).build())
					.collect(Collectors.toList());
		}

		return msgNoticeDtos;
	}

}
