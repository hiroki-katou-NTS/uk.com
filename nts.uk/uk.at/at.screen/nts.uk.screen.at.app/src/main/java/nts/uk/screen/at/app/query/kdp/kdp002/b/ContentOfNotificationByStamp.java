package nts.uk.screen.at.app.query.kdp.kdp002.b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;
import nts.uk.ctx.sys.portal.app.query.notice.MsgNoticesDto;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.MessageNoticeRepository;
import nts.uk.ctx.sys.portal.dom.notice.adapter.EmployeeInfoImport;
import nts.uk.ctx.sys.portal.dom.notice.adapter.MessageNoticeAdapter;

/**
 * SQ: 打刻入力でお知らせの内容を取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP002_打刻入力(個人打刻).B:打刻結果確認.メニュー別OCD.打刻入力でお知らせの内容を取得する.打刻入力でお知らせの内容を取得する
 * @author chungnt
 *
 */

@Stateless
public class ContentOfNotificationByStamp {

	@Inject
	private MessageNoticeRepository messageNoticeRepo;
	
	@Inject
	private MessageNoticeAdapter messageNoticeAdapter;
	
	public ContentOfNotificationByStampDto get (ContentOfNotificationByStampInput param) {
		
		List<MsgNoticesDto> msgNotices = new ArrayList<MsgNoticesDto>();
		DatePeriod period = new DatePeriod(param.startDate, param.endDate);
		ContentOfNotificationByStampDto result = new ContentOfNotificationByStampDto();
		
		List<MessageNotice> messageNotices = this.messageNoticeRepo.getMsgByPeriodAndSid(period, param.sid);
		
		if (!messageNotices.isEmpty()) {
			List<EmployeeInfoImport> listEmp = messageNoticeAdapter
					.getByListSID(messageNotices
							.stream()
							.map(m -> m.getCreatorID())
							.collect(Collectors.toList()));
			
			Map<String, String> listEmpMap = listEmp.isEmpty()
					? new HashMap<>()
					: listEmp.stream().collect(Collectors.toMap(EmployeeInfoImport::getSid, EmployeeInfoImport::getBussinessName));
			
			msgNotices = messageNotices.stream()
					.map(m -> MsgNoticesDto.builder()
							.message(MessageNoticeDto.toDto(m))
							.creator(listEmpMap.get(m.getCreatorID()))
							.flag(isNewMessage(param.sid, m.getEmployeeIdSeen()))
							.build()).collect(Collectors.toList());

		}
		
		result.setMsgNotices(msgNotices);
		
		return result;
	}
	
	private Boolean isNewMessage(String sid, List<String> employeeIdSeen) {
		return !employeeIdSeen.contains(sid);
	}
}
