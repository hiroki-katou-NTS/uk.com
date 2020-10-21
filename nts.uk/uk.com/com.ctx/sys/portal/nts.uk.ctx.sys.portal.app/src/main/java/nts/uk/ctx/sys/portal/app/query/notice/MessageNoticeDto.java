package nts.uk.ctx.sys.portal.app.query.notice;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.TargetInformation;
import nts.uk.ctx.sys.portal.dom.notice.adapter.DatePeriodDto;
import nts.uk.ctx.sys.portal.dom.notice.adapter.TargetInformationDto;

/**
 * Dto お知らせメッセージ
 */
@Data
@Builder
public class MessageNoticeDto implements MessageNotice.MementoSetter {
	
	/** 作成者ID */
	private String creatorID;
	
	/**	入力日 */
	private GeneralDate inputDate;
	
	/**	変更日 */
	private GeneralDate modifiedDate;
	
	/**	対象情報 */
	private TargetInformationDto targetInformation;
	
	/**	期間 */
	private DatePeriodDto datePeriod;
	
	/**	見た社員ID */
	private List<String> employeeIdSeen;
	
	/**	メッセージの内容 */
	private String notificationMessage;
	
	/**
	 * Convert to MessageNoticeDto
	 * @param msg
	 * @return
	 */
	public static MessageNoticeDto fromDomain(MessageNotice msg) {
		return MessageNoticeDto.builder()
				.creatorID(msg.getCreatorID())
				.inputDate(msg.getInputDate())
				.modifiedDate(msg.getModifiedDate())
				.targetInformation(fromObject(msg.getTargetInformation()))
				.datePeriod(fromObject(msg.getDatePeriod()))
				.employeeIdSeen(msg.getEmployeeIdSeen())
				.notificationMessage(msg.getNotificationMessage().v())
				.build();
	}
	
	/**
	 * Convert to TargetInformationDto
	 * @param target
	 * @return
	 */
	public static TargetInformationDto fromObject(TargetInformation target) {
		return TargetInformationDto.builder()
					.targetSIDs(target.getTargetSIDs())
					.targetWpids(target.getTargetWpids())
					.destination(target.getDestination().value)
					.build();
	}
	
	/**
	 * Convert to DatePeriodDto
	 * @param period
	 * @return
	 */
	public static DatePeriodDto fromObject(DatePeriod period) {
		return DatePeriodDto.builder()
				.startDate(period.start())
				.endDate(period.end())
				.build();
	}

	@Override
	public void setDatePeriod(DatePeriod period) {
		this.datePeriod = DatePeriodDto.builder()
				.startDate(period.start())
				.endDate(period.end())
				.build();
	}

	@Override
	public void setTargetInformation(TargetInformation target) {
		this.targetInformation = TargetInformationDto.builder()
				.destination(target.getDestination().value)
				.targetSIDs(target.getTargetSIDs())
				.targetWpids(target.getTargetWpids())
				.build();
	}
}
