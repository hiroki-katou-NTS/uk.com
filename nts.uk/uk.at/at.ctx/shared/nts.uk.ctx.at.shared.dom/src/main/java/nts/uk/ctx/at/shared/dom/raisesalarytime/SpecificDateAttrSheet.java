package nts.uk.ctx.at.record.dom.raisesalarytime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.raisesalarytime.enums.SpecificDateAttr;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;

/**
 * 
 * @author nampt
 * 特定日区分
 *
 */
@Getter
@AllArgsConstructor
public class SpecificDateAttrSheet {
	
	//特定日項目NO
	private SpecificDateItemNo specificDateItemNo;

	private SpecificDateAttr specificDateAttr;
}
