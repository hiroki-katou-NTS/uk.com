package nts.uk.ctx.sys.portal.app.query.notice;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.DestinationClassification;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.TargetInformation;
import nts.uk.ctx.sys.portal.dom.notice.adapter.DatePeriodDto;
import nts.uk.ctx.sys.portal.dom.notice.adapter.TargetInformationDto;

/**
 * Dto お知らせメッセージ
 */
@Data
public class MessageNoticeDto implements MessageNotice.MementoSetter, MessageNotice.MementoGetter {
	
	/** 作成者ID */
	@SuppressWarnings("unused")
	private String creatorID;
	
	/**	入力日 */
	private GeneralDateTime inputDate;
	
	/**	変更日 */
	private GeneralDateTime modifiedDate;
	
	/**	対象情報 */
	private TargetInformationDto targetInformation;
	
	/**	期間 */
	private DatePeriodDto datePeriod;
	
	/**	見た社員ID */
	private List<String> employeeIdSeen;
	
	/**	メッセージの内容 */
	private String notificationMessage;
	
	public static MessageNoticeDto toDto(MessageNotice domain) {
		MessageNoticeDto dto = new MessageNoticeDto();
		domain.setMemento(dto);
		return dto;
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

	@Override
	public String getCreatorID() {
		return this.getCreatorID();
	}

	@Override
	public GeneralDateTime getInputDate() {
		return this.inputDate;
	}

	@Override
	public GeneralDateTime getModifiedDate() {
		return this.modifiedDate;
	}

	@Override
	public DatePeriod getDatePeriod() {
		return new DatePeriod(this.datePeriod.getStartDate(), this.datePeriod.getEndDate());
	}

	@Override
	public List<String> getEmployeeIdSeen() {
		return this.employeeIdSeen;
	}

	@Override
	public String getNotificationMessage() {
		return this.notificationMessage;
	}

	@Override
	public TargetInformation getTargetInformation() {
		TargetInformation info = new TargetInformation();
		info.setDestination(DestinationClassification.valueOf(this.targetInformation.getDestination()));
		info.setTargetSIDs(this.targetInformation.getTargetSIDs());
		info.setTargetWpids(this.targetInformation.getTargetWpids());
		return info;
	}

	@Override
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}

	@Override
	public void setInputDate(GeneralDateTime inputDate) {
		this.inputDate = inputDate;
	}

	@Override
	public void setModifiedDate(GeneralDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public void setEmployeeIdSeen(List<String> employeeIdSeen) {
		this.employeeIdSeen = employeeIdSeen;
	}

	@Override
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
}
