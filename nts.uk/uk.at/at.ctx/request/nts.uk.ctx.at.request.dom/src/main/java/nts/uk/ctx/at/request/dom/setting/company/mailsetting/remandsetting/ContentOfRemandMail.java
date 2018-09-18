package nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 差し戻しのメール内容
*/
@Getter
public class ContentOfRemandMail extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 差し戻しメール件名
    */
    private MailTitle mailTitle;
    
    /**
    * 差し戻しメール本文
    */
    private MailBody mailBody;

	public ContentOfRemandMail(String cid, String mailTitle, String mailBody) {
		this.cid = cid;
		this.mailTitle = mailTitle == null ? null : new MailTitle(mailTitle);
		this.mailBody = mailBody == null ? null : new MailBody(mailBody);
	}
    
}
