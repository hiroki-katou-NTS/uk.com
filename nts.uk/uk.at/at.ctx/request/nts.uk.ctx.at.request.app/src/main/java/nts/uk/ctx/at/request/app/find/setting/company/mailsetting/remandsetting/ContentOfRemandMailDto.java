package nts.uk.ctx.at.request.app.find.setting.company.mailsetting.remandsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMail;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ContentOfRemandMailDto {
	// 件名
	private String mailTitle;
	// 本文
	private String mailBody;
	
	public static ContentOfRemandMailDto convertDto(ContentOfRemandMail domain){
		return new ContentOfRemandMailDto(domain.getMailTitle() == null ? null : domain.getMailTitle().v(), 
										  domain.getMailBody() == null ? null : domain.getMailBody().v());
	}
}
