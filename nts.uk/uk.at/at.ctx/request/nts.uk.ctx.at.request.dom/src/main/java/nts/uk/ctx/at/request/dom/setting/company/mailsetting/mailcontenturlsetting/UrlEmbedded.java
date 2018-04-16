package nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author hiep.ld
 *
 */
@Getter
public class UrlEmbedded extends AggregateRoot{
	/**
    * 会社ID
    */
    private String cid;
    
    /**
    * URL埋込
    */

	@Setter
    private NotUseAtr urlEmbedded;
    
    public static UrlEmbedded createFromJavaType(String cid, int urlEmbedded)
    {
        return new UrlEmbedded(cid,  NotUseAtr.valueOf(urlEmbedded));
    }
	public UrlEmbedded(String cid, NotUseAtr urlEmbedded) {
		super();
		this.cid = cid;
		this.urlEmbedded = urlEmbedded;
	}
}
