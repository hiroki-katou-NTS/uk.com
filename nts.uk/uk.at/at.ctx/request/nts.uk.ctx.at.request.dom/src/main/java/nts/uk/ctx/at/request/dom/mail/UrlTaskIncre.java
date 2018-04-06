package nts.uk.ctx.at.request.dom.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 
*/
@AllArgsConstructor
@Getter
public class UrlTaskIncre extends AggregateRoot
{
    
    /**
    * 埋込URLID
    */
    private String embeddedId;
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 
    */
    private String taskIncreId;
    
    /**
    * キー
    */
    private String key;
    
    /**
    * 値
    */
    private String value;
    
    public static UrlTaskIncre createFromJavaType(String embeddedId, String cid, String taskIncreId, String key, String value)
    {
        UrlTaskIncre  urlTaskIncre =  new UrlTaskIncre(embeddedId, cid, taskIncreId, key,  value);
        return urlTaskIncre;
    }
    
}
