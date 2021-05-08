package nts.uk.screen.at.app.query.kdp.kdp003.r;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
			List<String> sIdList = new ArrayList<>();
			messageNotices.stream().forEach(x -> {
				if (!sIdList.contains(x.getCreatorID())) {
					sIdList.add(x.getCreatorID());
				}
			});
			
			// <call> 社員ID（List）から社員コードと表示名を取得
			employeeInfos = msgNoticeAdapter.getByListSID(sIdList);
			
			Map<String, String> listNameMap = employeeInfos.isEmpty()
					? new HashMap<>()
					: employeeInfos.stream().collect(Collectors.toMap(EmployeeInfoImport::getSid, EmployeeInfoImport::getBussinessName));
			
			Map<String, String> listCodeMap = employeeInfos.isEmpty()
					? new HashMap<>()
					: employeeInfos.stream().collect(Collectors.toMap(EmployeeInfoImport::getSid, EmployeeInfoImport::getScd));
			
			// Map<お知らせメッセージ、作成者コード、作成者>の作成者コードに社員コード、作成者にビジネスネームをセットする（Listの並び順はそのままとする)
			msgNoticeDtos = messageNotices.stream()
				.map(m -> MsgNoticeDto.builder()
					.scd(listCodeMap.get(m.getCreatorID()))
					.bussinessName(listNameMap.get(m.getCreatorID()))
					.message(MessageNoticeDto.toDto(m))
					.build()).distinct()
					.collect(Collectors.toList());
		}

		return msgNoticeDtos;
	}

}
