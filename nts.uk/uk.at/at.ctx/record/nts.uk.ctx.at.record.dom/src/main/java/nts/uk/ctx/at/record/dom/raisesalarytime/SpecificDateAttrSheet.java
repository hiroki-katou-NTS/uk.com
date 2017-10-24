package nts.uk.ctx.at.record.dom.raisesalarytime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.raisesalarytime.enums.SpecificDateAttr;

/**
 * 
 * @author nampt
 * 特定日区分
 *
 */
@Getter
public class SpecificDateAttrSheet {
	
	//特定日項目NO - primitive value
	private String specificDateItemNo;

	private SpecificDateAttr specificDateAttr;
}
