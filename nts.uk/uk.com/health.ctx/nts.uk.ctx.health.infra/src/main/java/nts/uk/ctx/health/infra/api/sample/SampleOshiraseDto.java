package nts.uk.ctx.health.infra.api.sample;

import java.util.Date;

import lombok.Data;
import nts.uk.ctx.health.dom.sample.SampleOshirase;

@Data
public class SampleOshiraseDto {
	
	private String integrationId;
	private String noticeId;
	private String title;
	private String body;
	private Date notificationDate;
	private String senderName;
	private String linkUrl;

	
	public SampleOshirase toDomain() {
		return null;
	}
}
