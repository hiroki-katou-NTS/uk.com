package nts.uk.ctx.exio.dom.exo.dataformat.init;


import lombok.Getter;

/**
* 和暦記号
*/
@Getter
public class JapCalendarSymbol extends DateFormatSet
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

	public JapCalendarSymbol(int itemType, String cid, int nullValueSubstitution, int fixedValue,
			String valueOfFixedValue, String valueOfNullValueSubs, int formatSelection, String eraId, String eraName) {
		super(itemType, cid, nullValueSubstitution, fixedValue, valueOfFixedValue, valueOfNullValueSubs, formatSelection);
		this.cid = cid;
		this.eraId = eraId;
		this.eraName  = new EraName(eraName);
	}
    
    
}
