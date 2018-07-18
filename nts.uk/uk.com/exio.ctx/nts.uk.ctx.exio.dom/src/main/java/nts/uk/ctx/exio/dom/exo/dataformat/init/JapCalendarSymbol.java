package nts.uk.ctx.exio.dom.exo.dataformat.init;


import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
* 和暦記号
*/
@Getter
public class JapCalendarSymbol extends DomainObject
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 元号ID
    */
    private String eraId;
    
    /**
    * 元号名
    */
    private EraName eraName;

	public JapCalendarSymbol(String cid, String eraId, String eraName) {
		
		this.cid = cid;
		this.eraId = eraId;
		this.eraName  = new EraName(eraName);
	}
  
    
}
