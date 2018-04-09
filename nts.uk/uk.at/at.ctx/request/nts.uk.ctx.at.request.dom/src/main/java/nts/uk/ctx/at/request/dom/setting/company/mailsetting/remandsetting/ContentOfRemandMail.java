package nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 
*/
@AllArgsConstructor
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
    private String mailTitle;
    
    /**
    * 差し戻しメール本文
    */
    private String mailBody;
    
    public static ContentOfRemandMail createFromJavaType(String cid, String mailTitle, String mailBody)
    {
        ContentOfRemandMail  remandMail =  new ContentOfRemandMail(cid, mailTitle,  mailBody);
        return remandMail;
    }
    
}
